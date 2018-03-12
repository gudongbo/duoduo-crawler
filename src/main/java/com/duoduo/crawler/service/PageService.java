package com.duoduo.crawler.service;

import com.duoduo.crawler.entity.PageInfo;


public interface PageService extends IService<PageInfo> {
	public void crawlerAiRenTi(String url, int count);
	
}
