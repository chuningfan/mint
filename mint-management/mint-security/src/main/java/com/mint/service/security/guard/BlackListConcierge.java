package com.mint.service.security.guard;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.mint.common.context.UserContext;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.support.CacheOperator;
import com.mint.service.cache.support.local.LocalCacheFactory;
import com.mint.service.cache.support.local.LocalCacheType;
import com.mint.service.cache.support.redis.RedisHelper;

public class BlackListConcierge {

	private static final String rdsSetKey = "viciousIPs";
	
	private static final Logger LOG = LoggerFactory.getLogger(BlackListConcierge.class);
	
	private static CacheOperator<String, Object> cache;
	
	private static final long testSec = 1L;
	
	static {
		try {
			cache = LocalCacheFactory.create("test", LocalCacheType.MEMORY, testSec, TimeUnit.SECONDS, 10240L);
		} catch (MintException e) {
			LOG.error("Preparing local cache for black list validator error: {}", e.getMessage());
		}
	}
	
	private RedisHelper redisHelper;
	
	public BlackListConcierge(RedisHelper redisHelper) {
		this.redisHelper = redisHelper;
	}
	
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintException {
		String ip = HttpUtil.getIpAddress(req);
		String reqURI = req.getRequestURI();
		String cacheKey = new StringBuilder(ip).append(reqURI).toString();
		Set<Object> viciousIpSet = redisHelper.sGet(rdsSetKey);
		if (!CollectionUtils.isEmpty(viciousIpSet)) {
			if (redisHelper.sHasKey(rdsSetKey, ip)) {
				throw MintException.getException(Error.VICOUS_REQ_ERROR, null, null);
			}
		}
		boolean flag = cache.getByKey(cacheKey) == null ? false : (boolean) cache.getByKey(cacheKey);
		if (!flag) {
			cache.store(cacheKey, true, null, null);
		} else {
			redisHelper.sSet(rdsSetKey, ip);
			throw MintException.getException(Error.VICOUS_REQ_ERROR, null, null);
		}
	}
	
}
