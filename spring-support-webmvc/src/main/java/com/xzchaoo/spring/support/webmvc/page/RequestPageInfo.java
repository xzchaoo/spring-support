package com.xzchaoo.spring.support.webmvc.page;

/**
 * 描述了请求的分页信息
 * Created by zcxu on 2017/2/27.
 */
public class RequestPageInfo {
	private int page;
	private int pageSize;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
