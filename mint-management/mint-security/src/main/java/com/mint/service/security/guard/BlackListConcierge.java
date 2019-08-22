package com.mint.service.security.guard;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mint.common.context.UserContext;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.exception.LocalCacheException;
import com.mint.service.cache.support.CacheOperator;
import com.mint.service.cache.support.local.LocalCacheFactory;
import com.mint.service.cache.support.local.LocalCacheType;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.security.exception.ViciousRequestException;

@Component
public class BlackListConcierge {

	private static final String rdsSetKey = "viciousIPs";
	
	private static final Logger LOG = LoggerFactory.getLogger(BlackListConcierge.class);
	
	private static CacheOperator<String, Object> cache;
	
	private static final long testSec = 2L;
	
	static {
		try {
			cache = LocalCacheFactory.create("test", LocalCacheType.MEMORY, testSec, TimeUnit.SECONDS, 10240L);
		} catch (LocalCacheException e) {
			LOG.error("Preparing local cache for black list validator error: {}", e.getMessage());
		}
	}
	
	@Autowired
	private RedisHelper redisHelper;
	
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws ViciousRequestException, Exception {
		String ip = HttpUtil.getIpAddress(req);
		Set<Object> viciousIpSet = redisHelper.sGet(rdsSetKey);
		if (!CollectionUtils.isEmpty(viciousIpSet)) {
			if (redisHelper.sHasKey(rdsSetKey, ip)) {
				throw new ViciousRequestException();
			}
		}
		Long times = (Long) cache.getByKey(ip);
		if (times == null) {
			cache.store(ip, 1, null, null);
		} else {
			if(times >= 10) { // 2s 10 + 次请求 算恶意请求直接入redis
				redisHelper.sSet(rdsSetKey, ip);
				throw new ViciousRequestException();
			} else {
				cache.store(ip, times + 1, null, null);
			}
		}
	}
	
}
