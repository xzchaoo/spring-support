package com.xzchaoo.spring.support.webmvc.page;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by zcxu on 2017/2/27.
 */
public class PageInfoHandlerMethodArgumentResolverTest {

	private PageInfoHandlerMethodArgumentResolver r;

	@Before
	public void before() {
		r = new PageInfoHandlerMethodArgumentResolver();
		r.setMinPageSize(1);
		r.setMaxPageSize(10);
		r.setDefaultPageSize(5);
		r.setPageNames(Arrays.asList("page", "pn"));
		r.setPageSizeNames(Arrays.asList("pageSize", "ps"));
	}

	@Test
	public void test_resolveArgument_1() throws Exception {
		MethodParameter mp = mock(MethodParameter.class);
		when(mp.getMethodAnnotation(RequestPageInfoOptions.class)).thenReturn(null);
		NativeWebRequest request = mock(NativeWebRequest.class);
		RequestPageInfo rpi = (RequestPageInfo) r.resolveArgument(mp, null, request, null);
		assertEquals(1, rpi.getPage());
		assertEquals(5, rpi.getPageSize());
	}

	@Test
	public void test_resolveArgument_2() throws Exception {
		MethodParameter mp = mock(MethodParameter.class);
		when(mp.getMethodAnnotation(RequestPageInfoOptions.class)).thenReturn(null);
		NativeWebRequest request = mock(NativeWebRequest.class);
		when(request.getParameter("page")).thenReturn("2");
		when(request.getParameter("ps")).thenReturn("3");
		RequestPageInfo rpi = (RequestPageInfo) r.resolveArgument(mp, null, request, null);
		assertEquals(2, rpi.getPage());
		assertEquals(3, rpi.getPageSize());
	}

	@Test
	public void test_resolveArgument_3() throws Exception {
		MethodParameter mp = mock(MethodParameter.class);
		RequestPageInfoOptions rpio = mock(RequestPageInfoOptions.class);
		when(rpio.pageNames()).thenReturn(new String[]{"page3"});
		when(rpio.minPageSize()).thenReturn(100);
		when(rpio.maxPageSize()).thenReturn(200);
		when(mp.getMethodAnnotation(RequestPageInfoOptions.class)).thenReturn(rpio);
		NativeWebRequest request = mock(NativeWebRequest.class);
		when(request.getParameter("page")).thenReturn("2");
		when(request.getParameter("page3")).thenReturn("3");
		when(request.getParameter("ps")).thenReturn("3");
		RequestPageInfo rpi = (RequestPageInfo) r.resolveArgument(mp, null, request, null);
		assertEquals(3, rpi.getPage());
		assertEquals(100, rpi.getPageSize());
	}
}
