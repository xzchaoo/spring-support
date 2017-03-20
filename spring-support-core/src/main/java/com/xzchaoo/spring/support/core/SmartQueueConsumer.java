package com.xzchaoo.spring.support.core;

import org.springframework.context.SmartLifecycle;

/**
 * 基于阻塞队列的消费者 spring 支持
 * Created by zcxu on 2017/3/9.
 */
public abstract class SmartQueueConsumer<T> extends com.xzchaoo.utils.thread.SmartQueueConsumer<T> implements SmartLifecycle {
	public SmartQueueConsumer() {
	}

	public SmartQueueConsumer(long pollInterval, boolean interruptWhenStop, long joinThreadMills) {
		super(pollInterval, interruptWhenStop, joinThreadMills);
	}

	public boolean isAutoStartup() {
		return true;
	}

	public void stop(Runnable callback) {
		stop();
		callback.run();
	}
}
