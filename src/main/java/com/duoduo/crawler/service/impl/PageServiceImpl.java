package com.duoduo.crawler.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import com.duoduo.crawler.crawler.airenti.AiRenTiMpCrawler;
import com.duoduo.crawler.dao.PageInfoDao;
import com.duoduo.crawler.entity.PageInfo;
import com.duoduo.crawler.service.BaseService;
import com.duoduo.crawler.service.PageService;
import com.geccocrawler.gecco.request.HttpRequest;

@Service
public class PageServiceImpl extends BaseService<PageInfo> implements PageService {

	private static Logger logger = LoggerFactory.getLogger(PageService.class);

	@Autowired
	private PageInfoDao pageInfoDao;

	@Override
	public int save(PageInfo entity) {
		return pageInfoDao.add(entity);
	}

	public void crawlerAiRenTi(String url, int count) {
		String baseUrl = url;
		HttpRequest httpRequest = null;
		for (int i = 1; i <= count; i++) {
			long time = System.currentTimeMillis();
			System.out.println("crawlerAiRenTi in " + i + " model start !");
			AiRenTiMpCrawler crawler = null;
			if (httpRequest != null) {
				crawler = this.crawler(baseUrl, httpRequest.subRequest(baseUrl), 5000l);
			} else {
				crawler = this.crawler(baseUrl, baseUrl, 5000l);
			}

			System.out.println(
					"crawlerAiRenTi in " + i + " model end time = " + (System.currentTimeMillis() - time) / 1000 + "秒");
			if (crawler == null) {
				System.out.println("crawlerAiRenTi in " + i + " model error  crawler == null !");
				System.out.println("crawlerAiRenTi in " + i + " model error  baseUrl = " + baseUrl);
				return;
			} else {
				baseUrl = crawler.getNextModel();
				httpRequest = crawler.getRequest();
			}
		}
	}

	private AiRenTiMpCrawler crawler(String baseUrl, String url, long sleepTime) {
		AiRenTiMpCrawler crawler = AiRenTiMpCrawler.crawler(url);
		if (crawler == null) {
			logger.error("crawler error ... url = " + url + " sleep time = " + sleepTime);
			try {
				if (sleepTime < 100000l) {
					Thread.sleep(sleepTime);
					return this.crawler(baseUrl, url, sleepTime + 3000l);
				} else {
					return null;
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		if (StringUtils.isNotBlank(crawler.getNextPage())) {
			return this.crawler(baseUrl, crawler.getRequest().subRequest(crawler.getNextPage()), sleepTime);
		} else {
			// DB
			PageInfo info = new PageInfo();
			info.setCreateTime(new Date());
			info.setIsUpload(1);
			info.setIsZip(1);
			info.setPageUrl(baseUrl);
			int id = save(info);
			System.out.println("save id = " + id);

			// zip
			long time = System.currentTimeMillis();
			System.out.println("crawlerAiRenTi in zip model start !");
			String zipFileName = crawler.getDay() + crawler.getNo() + ".zip";
			ZipUtil.pack(new File(crawler.getAuthor()), new File("E:\\资源\\airenti", zipFileName));
			System.out.println(
					"crawlerAiRenTi in zip model end time = " + (System.currentTimeMillis() - time) / 1000 + "秒");
			return crawler;
		}
	}

	private AiRenTiMpCrawler crawler(String baseUrl, HttpRequest request, long sleepTime) {
		AiRenTiMpCrawler crawler = AiRenTiMpCrawler.crawler(request);
		if (crawler == null) {
			logger.error("crawler error ... url = " + request.getUrl() + " sleep time = " + sleepTime);
			try {
				if (sleepTime < 100000l) {
					Thread.sleep(sleepTime);
					return this.crawler(baseUrl, request, sleepTime + 3000l);
				} else {
					return null;
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		if (StringUtils.isNotBlank(crawler.getNextPage())) {
			return this.crawler(baseUrl, crawler.getNextPage(), sleepTime);
		} else {
			// DB
			PageInfo info = new PageInfo();
			info.setCreateTime(new Date());
			info.setIsUpload(1);
			info.setIsZip(1);
			info.setPageUrl(baseUrl);
			int id = save(info);
			System.out.println("save id = " + id);

			// zip
			long time = System.currentTimeMillis();
			System.out.println("crawlerAiRenTi in zip model start !");
			String zipFileName = crawler.getDay() + crawler.getNo() + ".zip";
			ZipUtil.pack(new File(crawler.getAuthor()), new File("E:\\资源\\airenti", zipFileName));
			System.out.println(
					"crawlerAiRenTi in zip model end time = " + (System.currentTimeMillis() - time) / 1000 + "秒");
			return crawler;
		}
	}

}
