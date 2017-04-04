package com.xzchaoo.spring.support.core;

import org.springframework.context.SmartLifecycle;

/**
 * Created by Administrator on 2017/4/4.
 */
public abstract class BaseSmartLifeCycleBasedTaskRunner extends BaseSpringTaskRunner implements SmartLifecycle {
	private volatile boolean running;

	protected BaseSmartLifeCycleBasedTaskRunner(String name) {
		super(name);
	}

	public boolean isAutoStartup() {
		return true;
	}

	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

	public void start() {
		running = true;
		schedule(getFirstDelay());
	}

	public void stop() {
		running = false;
		if (future != null) {
			future.cancel(false);
			future = null;
		}
	}

	public boolean isRunning() {
		return running;
	}
}
