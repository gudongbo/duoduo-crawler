package com.duoduo.crawler.crawler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.FieldRenderName;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * 微信爬虫
 * 
 * @author GDB
 *
 */
@Gecco(matchUrl = { "http://mp.weixin.qq.com/s/{key}",
		"https://mp.weixin.qq.com/s/{key}" }, pipelines = "wechatMpPipeline")
public class WechatMpCrawler implements HtmlBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatMpCrawler.class);

	private static final long serialVersionUID = 8635180228671479999L;

	private static Map<String, WechatMpCrawler> successMap = new ConcurrentHashMap<>();

	/**
	 * 根据解析的url获得解析对象
	 * 
	 * @param url
	 *            用户传入的url
	 * @return
	 */
	public static WechatMpCrawler getWechatMpCrawlerByUrl(String url) {
		if (StringUtils.isBlank(url)) {
			LOGGER.error("getWechatMpCrawlerByUrl error ... url is null");
			return null;
		}
		if (successMap.containsKey(url)) {
			return successMap.remove(url);
		} else {
			LOGGER.error("getWechatMpCrawlerByUrl error ... url is not exist url = " + url);
		}
		return null;
	}

	/**
	 * 根据解析的url获得解析对象
	 * 
	 * @param url
	 *            用户传入的url
	 * @return
	 */
	public static void addWechatMpCrawlerByUrl(WechatMpCrawler crawler) {
		if (crawler == null || crawler.getRequest() == null) {
			LOGGER.error("addWechatMpCrawlerByUrl error ... crawler is null");
			return;
		}
		if (successMap.containsKey(crawler.getRequest().getUrl())) {
			LOGGER.error("addWechatMpCrawlerByUrl error ... url is exist  url = " + crawler.getRequest().getUrl());
			return;
		}
		successMap.put(crawler.getRequest().getUrl(), crawler);
	}

	public static WechatMpCrawler crawler(String url) {
		if (StringUtils.isBlank(url) || !url.contains("mp.weixin.qq.com/s/")) {
			LOGGER.error("crawler error url is null  url : " + url);
			return null;
		}
		HttpGetRequest start = new HttpGetRequest(url);
		try {
			GeccoEngine.create().classpath("com.duoduo.crawler.crawler").start(start).run();
			WechatMpCrawler clientMain = WechatMpCrawler.getWechatMpCrawlerByUrl(url);
			if (clientMain != null && clientMain.getTitle() != null) {
				// TODO 处理相应的接口吧,调用dubbo，返回编辑发布页面
				System.out.println("main : " + clientMain.getAuthor());
				System.out.println("main : " + clientMain.getTitle());
				System.out.println("main : " + clientMain.getImgs());
				// System.out.println("main : " + clientMain.getBody());

				return clientMain;
			} else {
				// 解析异常
				LOGGER.error("addWechatMpCrawlerByUrl error ... crawler error  url = " + url);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@FieldRenderName("wechatMpFieldRander")
	private Document document;
	// private String url;
	private String title;
	private String author;
	private Elements body;
	private Elements imgs;

	@Request
	private HttpRequest request;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Elements getBody() {
		return body;
	}

	public void setBody(Elements body) {
		this.body = body;
	}

	public Elements getImgs() {
		return imgs;
	}

	public void setImgs(Elements imgs) {
		this.imgs = imgs;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public static void main(String[] args) {
		String url = "http://mp.weixin.qq.com/s/IX7eMlBZYW7Np4qU8P-1dQ";
		WechatMpCrawler.crawler(url);
	}
}
