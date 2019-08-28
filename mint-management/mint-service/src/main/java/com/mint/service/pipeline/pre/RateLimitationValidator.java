package com.mint.service.pipeline.pre;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.util.concurrent.RateLimiter;
import com.mint.common.context.UserContext;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.service.pipeline.ServicePipelineMember;

/**
 * 本类仅在需要时放入pipeline中即可，需要预留后台接口。
 * @author ningfanchu
 *
 */
public class RateLimitationValidator implements ServicePipelineMember {

	public static final String ID = "mint-service-ratelimitation";
	
	private final RateLimiter limiter;
	
	private int retryTime;
	
	private long timeout;
	
	private long retryInterval = 30000;
	
	/**
	 * 令牌桶
	 * 
	 * @param permitsPerSecond 每秒生产领令牌数
	 * @param warmupPeriod 预热令牌数
	 * @param unit 时间单位
	 */
	public RateLimitationValidator(double permitsPerSecond, Long warmupPeriod, TimeUnit unit) {
		limiter = RateLimiter.create(permitsPerSecond, warmupPeriod, unit);
	} 
	
	/**
	 * 漏斗
	 * 
	 * @param permitsPerSecond 每秒生产领令牌数
	 */
	public RateLimitationValidator(double permitsPerSecond) {
		limiter = RateLimiter.create(permitsPerSecond);
	}
	
	public RateLimitationValidator retryWithInterval(long timeout, TimeUnit unit) {
		this.timeout = timeout <= 0 ? this.timeout : unit.toMillis(timeout);
		return this;
	}
	
	public RateLimitationValidator withRetry(int retryTime) {
		this.retryTime = retryTime <= 0 ? 0 : retryTime;
		return this;
	}
	
	public RateLimitationValidator withTimeout(long timeout, TimeUnit unit) {
		this.timeout = timeout <= 0 ? 0 : unit.toMillis(timeout);
		return this;
	}
	
	@Override
	public String id() {
		return ID;
	}

	@Override
	public void doValidate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintException {
		if (!limiter.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
			if (retryTime == 0) {
				throw MintException.getException(Error.UNSUPPORTED_ERROR, null, null);
			} else {
				int retried = 0;
				while (retried++ <= retryTime) {
					try {
						Thread.sleep(retryInterval);
					} catch (InterruptedException e) {
						throw MintException.getException(Error.INTER_ERROR, null, e);
					}
					if (limiter.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
						return;
					}
				}
				throw MintException.getException(Error.UNSUPPORTED_ERROR, null, null);
			}
		}
	}

}
