package com.duoduo.crawler.service;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.duoduo.crawler.entity.PageInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceTest {

	@Autowired
	private PageService pageService;

//	@Test
	public void testSave() {
		PageInfo entity = new PageInfo();
		entity.setCreateTime(new Date());
		entity.setIsUpload(1);
		entity.setIsZip(0);
		entity.setPageUrl("testq");
		pageService.save(entity);
	}

	@Test
	public void crawlerAiRenTi() {
		pageService.crawlerAiRenTi("http://www.airenti88.net/yazhourenti/201801/8336.html", 5);
	}

}
