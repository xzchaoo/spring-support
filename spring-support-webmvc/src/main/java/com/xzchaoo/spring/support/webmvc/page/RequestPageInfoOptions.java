package com.xzchaoo.spring.support.webmvc.page;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于强制修改参数
 * Created by Administrator on 2016/11/30.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestPageInfoOptions {
	String[] pageNames() default "";

	String[] pageSizeNames() default "";

	int minPageSize() default 0;

	int maxPageSize() default 0;

	int defaultPageSize() default 0;
}
