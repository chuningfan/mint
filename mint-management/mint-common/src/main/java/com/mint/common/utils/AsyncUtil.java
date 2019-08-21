package com.mint.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncUtil {
	
	private static ThreadPoolExecutor LOCAL_THREAD_POOL;
	
	static {
		int corePoolSize = Runtime.getRuntime().availableProcessors();
	    int maximumPoolSize = corePoolSize * 2;
	    BlockingQueue<Runnable> queue = new  ArrayBlockingQueue<Runnable>(1000);
	    LOCAL_THREAD_POOL = new ThreadPoolExecutor(corePoolSize,  maximumPoolSize, 
	            0, TimeUnit.SECONDS, queue ) ;
	    LOCAL_THREAD_POOL.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
	}
	
	public static final <T> T getResult(Callable<T> callable, Long timeout, TimeUnit unit) throws Exception {
		Future<T> f = LOCAL_THREAD_POOL.submit(callable);
		T result = null;
		try {
			result = f.get(timeout, unit);
		} catch (TimeoutException e) {
			f.cancel(true);
		}
		return result;
	}
	
}
