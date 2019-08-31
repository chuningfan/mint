package com.mint.common.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.mint.common.context.TokenThreadLocal;

public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor {
	
	private String token;
	
	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		token = TokenThreadLocal.get();
	}

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		token = TokenThreadLocal.get();
	}

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
		token = TokenThreadLocal.get();
	}

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
		token = TokenThreadLocal.get();
	}
	
	private void setAccountIdForCurrentThread() {
		if (token != null) {
			TokenThreadLocal.set(token);
		}
	}
	
	@Override
	public void execute(Runnable runnable) {
		super.execute(() -> {
			setAccountIdForCurrentThread();
			runnable.run();
		});
	}
	
	@Override
	public Future<?> submit(Runnable runnable) {
		return super.submit(() -> {
			setAccountIdForCurrentThread();
			runnable.run();
		});
	}
	
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return super.submit(new Callable<T>() {
			@Override
			public T call() throws Exception {
				setAccountIdForCurrentThread();
				return task.call();
			}
		});
	}
	
	@Override
	public <T> Future<T> submit(Runnable runnable, T result) {
		return super.submit(() -> {
			setAccountIdForCurrentThread();
			runnable.run();
		}, result);
	}
	
}
