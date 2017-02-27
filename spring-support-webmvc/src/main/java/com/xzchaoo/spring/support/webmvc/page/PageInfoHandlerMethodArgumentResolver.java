package com.xzchaoo.spring.support.webmvc.page;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zcxu on 2017/2/27.
 */
public class PageInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	/**
	 * 默认的页大小
	 */
	private int defaultPageSize;
	/**
	 * 最小的页大小
	 */
	private int minPageSize;
	/**
	 * 最大的页大小
	 */
	private int maxPageSize;
	/**
	 * 页参数的名字
	 */
	private List<String> pageNames;
	/**
	 * 页大小参数的名字
	 */
	private List<String> pageSizeNames;

	public PageInfoHandlerMethodArgumentResolver() {
		setDefaultPageSize(20);
		setMinPageSize(1);
		setMaxPageSize(40);
		setPageNames(Arrays.asList("page"));
		setPageSizeNames(Arrays.asList("pageSize"));
	}

	public boolean supportsParameter(MethodParameter methodParameter) {
		return RequestPageInfo.class.isAssignableFrom(methodParameter.getParameterType());
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public int getMinPageSize() {
		return minPageSize;
	}

	public void setMinPageSize(int minPageSize) {
		this.minPageSize = minPageSize;
	}

	public int getMaxPageSize() {
		return maxPageSize;
	}

	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	public List<String> getPageNames() {
		return pageNames;
	}

	public void setPageNames(List<String> pageNames) {
		this.pageNames = pageNames;
	}

	public List<String> getPageSizeNames() {
		return pageSizeNames;
	}

	public void setPageSizeNames(List<String> pageSizeNames) {
		this.pageSizeNames = pageSizeNames;
	}

	private static int parseInt(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private static int between(int value, int min, int max) {
		return value < min ? min : value > max ? max : value;
	}

	private int parsePageSize(String[] names0, List<String> names, NativeWebRequest request, int minPageSize, int maxPageSize, int defaultPageSize) {
		if (names0 != null && names0.length > 0) {
			for (String name : names0) {
				String param = request.getParameter(name);
				if (param != null) {
					return between(parseInt(param, defaultPageSize), minPageSize, maxPageSize);
				}
			}
		}
		if (names != null && names.size() > 0) {
			for (String name : names) {
				String param = request.getParameter(name);
				if (param != null) {
					return between(parseInt(param, defaultPageSize), minPageSize, maxPageSize);
				}
			}
		}
		return defaultPageSize;
	}

	private int parsePage(String[] names0, List<String> names, NativeWebRequest request) {
		if (names0 != null && names0.length > 0) {
			for (String name : names0) {
				String param = request.getParameter(name);
				if (param != null) {
					return Math.max(1, parseInt(param, 1));
				}
			}
		}

		if (names != null && names.size() > 0) {
			for (String name : names) {
				String param = request.getParameter(name);
				if (param != null) {
					return Math.max(1, parseInt(param, 1));
				}
			}
		}
		return 1;
	}

	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		//RequestPageInfoOptions pp = methodParameter.getParameterAnnotation(RequestPageInfoOptions.class);
		RequestPageInfoOptions pp = methodParameter.getMethodAnnotation(RequestPageInfoOptions.class);
		RequestPageInfo pi = new RequestPageInfo();

		int page = parsePage(pp == null ? null : pp.pageNames(), this.pageNames, nativeWebRequest);
		pi.setPage(page);

		int minPageSize;
		int maxPageSize;
		int defaultPageSize;
		if (pp != null) {
			minPageSize = pp.minPageSize() == 0 ? this.minPageSize : pp.minPageSize();
			maxPageSize = pp.maxPageSize() == 0 ? this.maxPageSize : pp.maxPageSize();
			defaultPageSize = pp.defaultPageSize() == 0 ? this.defaultPageSize : pp.defaultPageSize();
		} else {
			minPageSize = this.minPageSize;
			maxPageSize = this.maxPageSize;
			defaultPageSize = this.defaultPageSize;
		}

		int pageSize = parsePageSize(pp == null ? null : pp.pageSizeNames(), this.pageSizeNames, nativeWebRequest, minPageSize, maxPageSize, defaultPageSize);
		pi.setPageSize(pageSize);

		return pi;
	}
}
