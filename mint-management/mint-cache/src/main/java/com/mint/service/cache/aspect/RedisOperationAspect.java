package com.mint.service.cache.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mint.service.cache.annotation.RedisOps;
import com.mint.service.cache.annotation.enums.RedisDataType;
import com.mint.service.cache.annotation.enums.RedisOpsType;
import com.mint.service.cache.exception.CacheOperationException;
import com.mint.service.cache.support.redis.RedisHelper;

@Aspect
@Component
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
public class RedisOperationAspect {

	private static final Logger LOG = LoggerFactory.getLogger(RedisOperationAspect.class);

	@Autowired
	private RedisHelper redisHelper;

	private Long expireTime = 30 * 60 * 1000L;

	private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

	@Around("@annotation(com.mint.service.cache.annotation.RedisOps)")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method m = ms.getMethod();
		Object[] args = pjp.getArgs();
		if (m.isAnnotationPresent(RedisOps.class)) {
			RedisOps ro = m.getAnnotation(RedisOps.class);
			RedisOpsType type = ro.type();
			RedisDataType dataType = ro.dataType();
			boolean failFast = ro.failFast();
			redisHelper.getRedisTemplate().setEnableTransactionSupport(true);
			try {
				if (type == RedisOpsType.SAVE) {
					result = pjp.proceed(args);
					processSave(result, ro, failFast, dataType);
				} else {
					if (type == RedisOpsType.FIND) {
						result = processFind(pjp, m, args, ro, failFast, dataType);
					} else if (type == RedisOpsType.DELETE) {
						result = pjp.proceed(args);
						processDelete(pjp, m, args, ro, failFast, dataType);
					}
				}
			} catch (Exception e) {
				redisHelper.getRedisTemplate().discard();
				throw new CacheOperationException(e);
			}
		} else {
			return pjp.proceed();
		}
		return result;
	}

	private Object processSave(Object storedDataObject, RedisOps ro, boolean failFast, RedisDataType dataType)
			throws Exception {
		try {
			String key = getKey(ro.key(), storedDataObject);
			if (key == null) {
				throw new CacheOperationException(
						String.format("%s in %s is null, could not be a key for REDIS", ro.key(), ro.keyIn()));
			} else {
				storeInRedis(key, storedDataObject);
			}
		} catch (Exception e) {
			if (failFast) {
				throw new CacheOperationException(e);
			} else {
				LOG.info("When saving data in redis occurred an error: {}", e.getMessage());
			}
		}
		return storedDataObject;
	}

	private String getKey(Method m, Object[] args, RedisOps ro, boolean failFast) throws Exception {
		String key = null;
		try {
			if (StringUtils.isEmpty(ro.keyIn().trim())) {
				Object keyContainer = getArgByName(args, ro.keyIn(), m);
				if (keyContainer == null) {
					throw new CacheOperationException(String.format("Cannot find %s in %s", ro.key(), ro.keyIn()));
				}
				key = getKey(ro.key(), keyContainer);
			} else {
				key = getArgByName(args, ro.key(), m) == null ? null : getArgByName(args, ro.key(), m).toString();
			}
			if (key == null) {
				throw new CacheOperationException(
						String.format("%s in %s is null, could not be a key for REDIS", ro.key(), ro.keyIn()));
			}
		} catch (Exception e) {
			if (failFast) {
				throw e;
			} else {
				LOG.info("When finding data in redis occurred an error: {}", e.getMessage());
			}
		}
		return null;
	}

	private void processDelete(ProceedingJoinPoint pjp, Method m, Object[] args, RedisOps ro, boolean failFast,
			RedisDataType dataType) throws Throwable {
		String key = getKey(m, args, ro, failFast);
		if (key != null) {
			try {
				redisHelper.removeIfPresent(key);
			} catch (Exception e) {
				if (failFast) {
					throw new CacheOperationException(e);
				} else {
					LOG.info("When finding data in redis occurred an error: {}", e.getMessage());
				}
			}
		}

	}

	private Object processFind(ProceedingJoinPoint pjp, Method m, Object[] args, RedisOps ro, boolean failFast,
			RedisDataType dataType) throws Throwable {
		Object result = null;
		String key = getKey(m, args, ro, failFast);
		if (key != null) {
			try {
				result = redisHelper.getByKey(key);
			} catch (Exception e) {
				if (failFast) {
					throw new CacheOperationException(e);
				} else {
					LOG.info("When finding data in redis occurred an error: {}", e.getMessage());
				}
			}
		}
		if (result == null) {
			result = pjp.proceed(args);
			try {
				storeInRedis(key, result);
			} catch (Exception e) {
				if (failFast) {
					throw new CacheOperationException(e);
				} else {
					LOG.info("When saving data in redis occurred an error: {}", e.getMessage());
				}
			}
		}
		return result;
	}

	private void storeInRedis(String key, Object data) {
		redisHelper.store(key, data, expireTime, TimeUnit.MILLISECONDS);
	}

	private Object getArgByName(Object[] args, String name, Method mehtod) {
		String[] paramNames = discoverer.getParameterNames(mehtod);
		for (int i = 0; i < paramNames.length; i++) {
			if (name.equals(paramNames[i])) {
				return args[i];
			}
		}
		return null;
	}

	private String getKey(String keyName, Object keyContainer) {
		if (keyContainer != null) {
			try {
				Field f = keyContainer.getClass().getDeclaredField(keyName);
				f.setAccessible(true);
				return f.get(keyContainer).toString();
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new CacheOperationException(e);
			}
		}
		return null;
	}

}
