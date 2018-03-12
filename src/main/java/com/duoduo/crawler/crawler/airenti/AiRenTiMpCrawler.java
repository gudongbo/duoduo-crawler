package com.duoduo.crawler.crawler.airenti;

import java.util.HashMap;
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
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * airenti爬虫
 * 
 * @author GDB
 *
 */
@Gecco(matchUrl = { "http://{address}/{diyu}/{day}/{no}.html" }, pipelines = "aiRenTiMpPipeline")
public class AiRenTiMpCrawler implements HtmlBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(AiRenTiMpCrawler.class);

	private static final long serialVersionUID = 8635180228671479999L;

	private static Map<String, AiRenTiMpCrawler> successMap = new ConcurrentHashMap<>();

	/**
	 * 根据解析的url获得解析对象
	 * 
	 * @param url
	 *            用户传入的url
	 * @return
	 */
	public static AiRenTiMpCrawler getWechatMpCrawlerByUrl(String url) {
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
	public static void addWechatMpCrawlerByUrl(AiRenTiMpCrawler crawler) {
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

	public static AiRenTiMpCrawler crawler(HttpRequest start) {
		// if (StringUtils.isBlank(url)) {
		// LOGGER.error("crawler error url is null url : " + url);
		// return null;
		// }
		// HttpGetRequest start = new HttpGetRequest(url);
		// start.setCharset("gb2312");
		// start.refer(url);
		// Map<String, String> cookies = new HashMap<String, String>();
		// cookies.put("CNZZDATA1000147852",
		// "1851388856-1516023424-http%253A%252F%252Fwww.airenti88.net%252F%7C1516023424");
		// cookies.put("UM_distinctid",
		// "160fa14ceda6c8-04195349e834ff-7047503f-144000-160fa14cedbe16");

		// start.setCookies(cookies);
		try {
			GeccoEngine.create().classpath("com.duoduo.crawler.crawler.airenti").start(start).run();
			AiRenTiMpCrawler clientMain = AiRenTiMpCrawler.getWechatMpCrawlerByUrl(start.getUrl());
			if (clientMain != null) {
				// TODO 处理相应的接口吧,调用dubbo，返回编辑发布页面
				System.out.println("main : " + clientMain.getRequest().getUrl() + " end !");
				// System.out.println("main : " + clientMain.getTitle());
				// System.out.println("main : " + clientMain.getImgs());
				// System.out.println("main : " + clientMain.getBody());

				return clientMain;
			} else {
				// 解析异常
				LOGGER.error("addWechatMpCrawlerByUrl error ... crawler error  url = " + start.getUrl());
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	public static AiRenTiMpCrawler crawler(String url) {
		if (StringUtils.isBlank(url)) {
			LOGGER.error("crawler error url is null  url : " + url);
			return null;
		}
		HttpGetRequest start = new HttpGetRequest(url);
		start.setCharset("gb2312");
		start.refer(url);
		// Map<String, String> cookies = new HashMap<String, String>();
		// cookies.put("CNZZDATA1000147852",
		// "1851388856-1516023424-http%253A%252F%252Fwww.airenti88.net%252F%7C1516023424");
		// cookies.put("UM_distinctid",
		// "160fa14ceda6c8-04195349e834ff-7047503f-144000-160fa14cedbe16");

		// start.setCookies(cookies);
		try {
			GeccoEngine.create().classpath("com.duoduo.crawler.crawler.airenti").start(start).run();
			AiRenTiMpCrawler clientMain = AiRenTiMpCrawler.getWechatMpCrawlerByUrl(url);
			if (clientMain != null) {
				// TODO 处理相应的接口吧,调用dubbo，返回编辑发布页面
				System.out.println("main : " + clientMain.getRequest().getUrl() + " end !");
				// System.out.println("main : " + clientMain.getTitle());
				// System.out.println("main : " + clientMain.getImgs());
				// System.out.println("main : " + clientMain.getBody());

				return clientMain;
			} else {
				// 解析异常
				LOGGER.error("addWechatMpCrawlerByUrl error ... crawler error  url = " + url);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	@FieldRenderName("aiRenTiMpFieldRander")
	private Document document;
	// private String url;
	private String title;
	private String author;
	private Elements body;
	private Elements imgs;
	private String nextPage;
	private String nextModel;
	@RequestParameter("address")
	private String address;
	@RequestParameter("diyu")
	private String diyu;
	@RequestParameter("day")
	private String day;
	@RequestParameter("no")
	private String no;

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

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getNextModel() {
		return nextModel;
	}

	public void setNextModel(String nextModel) {
		this.nextModel = nextModel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDiyu() {
		return diyu;
	}

	public void setDiyu(String diyu) {
		this.diyu = diyu;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public static void main(String[] args) {
		String url = "http://www.airenti88.net/yazhourenti/201801/8340.html";
		AiRenTiMpCrawler.crawler(url);
	}
}
