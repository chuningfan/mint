package com.mint.common.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;

public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor {
	
	private UserContext context;
	
	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		context = UserContextThreadLocal.get();
	}

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		context = UserContextThreadLocal.get();
	}

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
		context = UserContextThreadLocal.get();
	}

	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
		context = UserContextThreadLocal.get();
	}
	
	private void setContextForCurrentThread() {
		if (context != null) {
			UserContextThreadLocal.set(context);
		}
	}
	
	@Override
	public void execute(Runnable runnable) {
		super.execute(() -> {
			setContextForCurrentThread();
			runnable.run();
		});
	}
	
	@Override
	public Future<?> submit(Runnable runnable) {
		return super.submit(() -> {
			setContextForCurrentThread();
			runnable.run();
		});
	}
	
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return super.submit(new Callable<T>() {
			@Override
			public T call() throws Exception {
				setContextForCurrentThread();
				return task.call();
			}
		});
	}
	
	@Override
	public <T> Future<T> submit(Runnable runnable, T result) {
		return super.submit(() -> {
			setContextForCurrentThread();
			runnable.run();
		}, result);
	}
	
}
