package com.duoduo.crawler.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

/**
 * 页面解析
 * 
 * @author GDB
 *
 */
@PipelineName("wechatMpPipeline")
public class WechatMpPipeline implements Pipeline<SpiderBean> {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatMpPipeline.class);

	public void process(SpiderBean bean) {
		WechatMpCrawler mpCrawler = (WechatMpCrawler) bean;
		Document document = mpCrawler.getDocument();
		// 获取标题
		Elements title = document.getElementsByAttributeValue("id", "activity-name");
		if (title == null || StringUtils.isBlank(title.text())) {
			LOGGER.error("WechatMpPipeline  error ... title == null  url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		mpCrawler.setTitle(title.text());
		// 获取作者
		Elements author = document.getElementsByAttributeValue("class",
				"rich_media_meta rich_media_meta_text rich_media_meta_nickname");
		if (author == null || StringUtils.isBlank(author.text())) {
			LOGGER.error("WechatMpPipeline  error ... author == null  url = " + mpCrawler.getRequest().getUrl());
		}
		mpCrawler.setAuthor(author.text());
		// 获取body
		Elements body = document.getElementsByAttributeValue("id", "js_content");
		if (body == null) {
			LOGGER.error("WechatMpPipeline  error ... body == null  url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		mpCrawler.setBody(body);
		//获取图片集合
		Elements imgs = body.select("img");
		mpCrawler.setImgs(imgs);
		//解析完毕，调用处理方法 
		WechatMpProcessor processor = new WechatMpProcessor(mpCrawler);
		processor.processor();
	}
}
