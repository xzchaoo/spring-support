package com.xzchaoo.spring.support.core;

import com.xzchaoo.utils.task.BaseTaskSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by Administrator on 2017/4/3.
 */
public abstract class BaseSpringTaskRunner extends BaseTaskSupport {
	private static final Logger LOG = LoggerFactory.getLogger(BaseSpringTaskRunner.class);
	protected volatile ScheduledFuture<?> future;

	@Autowired
	private TaskScheduler taskScheduler;

	public BaseSpringTaskRunner(String name) {
		super(name);
	}

	@Override
	protected void schedule(int delaySeconds) {
		try {
			future = taskScheduler.schedule(new Runnable() {
				public void run() {
					execute();
				}
			}, new Date(System.currentTimeMillis() + delaySeconds * 1000));
		} catch (Exception e) {
			LOG.error("[{}]schedule失败", name, e);
		}
	}

}
