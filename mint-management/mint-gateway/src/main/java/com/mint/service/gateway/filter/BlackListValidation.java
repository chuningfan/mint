package com.mint.service.gateway.filter;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.context.UserContext;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.exception.LocalCacheException;
import com.mint.service.cache.support.CacheOperator;
import com.mint.service.cache.support.local.LocalCacheFactory;
import com.mint.service.cache.support.local.LocalCacheType;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.exception.MintServiceException;
import com.mint.service.pipeline.ServicePipelineMember;
import com.mint.service.security.exception.ViciousRequestException;
import com.mint.service.security.guard.BlackListConcierge;

@Component
public class BlackListValidation implements ServicePipelineMember {

	private static final Logger LOG = LoggerFactory.getLogger(BlackListValidation.class);
	
	private static CacheOperator<String, Object> cache;
	
	static {
		try {
			cache = LocalCacheFactory.create("test", LocalCacheType.MEMORY, 15L, TimeUnit.MINUTES, 10240L);
		} catch (LocalCacheException e) {
			LOG.error("Preparing local cache for black list validator error: {}", e.getMessage());
		}
	}
	
	public static final String ID = "mint-blacklist-validator";
	
	@Autowired
	private BlackListConcierge blackListConcierge;
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public String id() {
		return ID;
	}

	@Override
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintServiceException {
		try {
			blackListConcierge.validate(req, resp, context);
		} catch (Throwable e) {
			String ip = HttpUtil.getIpAddress(req);
			if (e instanceof ViciousRequestException) {
				LOG.error("received vicious request from {}", ip);
				throw new MintServiceException(e);
			} else {
				LOG.error("When filtering black list, an error was encountered {}.", e.getMessage());
				// TODO need a buffer to process vicious request if REDIS cluster crashed
			}
		}
	}

	private static class IpAndReqTime {
		private String ip;
		private volatile long requestedTimes;
		private volatile long previousReqTime;
		public IpAndReqTime(String ip, int requestedTimes, long previousReqTime) {
			this.ip = ip;
			this.requestedTimes = requestedTimes;
			this.previousReqTime = previousReqTime;
		}
		public void incr() {
			requestedTimes ++;
			previousReqTime = System.currentTimeMillis();
		}
	}
	
}
