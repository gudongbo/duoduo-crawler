package com.duoduo.crawler.crawler.airenti;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

/**
 * 页面解析
 * 
 * @author GDB
 *
 */
@PipelineName("aiRenTiMpPipeline")
public class AiRenTiMpPipeline implements Pipeline<SpiderBean> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AiRenTiMpPipeline.class);

	public void process(SpiderBean bean) {
		AiRenTiMpCrawler mpCrawler = (AiRenTiMpCrawler) bean;
		// System.out.println(mpCrawler.getAddress());
		// System.out.println(mpCrawler.getDiyu());
		// System.out.println(mpCrawler.getDay());
		// System.out.println(mpCrawler.getNo());
		Document document = mpCrawler.getDocument();
		if (document == null) {
			LOGGER.error("AiRenTiMpPipeline error ... document == null url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		// 获取标题
		// 获取作者
		// 获取图片集合
		Elements file = document.getElementsByAttributeValue("class", "file");
		if (file == null) {
			LOGGER.error("AiRenTiMpPipeline error ... file == null url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		Elements imgs = file.select("img");
		if (imgs == null) {
			LOGGER.error("AiRenTiMpPipeline error ... imgs == null url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		mpCrawler.setImgs(imgs);
		// 下一页链接
		Elements photo = document.getElementsByAttributeValue("class", "photo");
		if (photo == null) {
			LOGGER.error("AiRenTiMpPipeline error ... photo == null url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		Elements strong = photo.select("strong");
		if (!CollectionUtils.isEmpty(strong) && strong.size() > 1) {
			if (!strong.get(0).text().equals(strong.get(1).text())) {
				Elements a = photo.select("a");
				if (!CollectionUtils.isEmpty(a)) {
					mpCrawler.setNextPage("http://" + mpCrawler.getAddress() + "/" + mpCrawler.getDiyu() + "/"
							+ mpCrawler.getDay() + "/" + a.get(a.size() - 1).attr("href"));
					System.out.println("next page : " + mpCrawler.getNextPage());
				}
			} else {
				System.out.println("this last page : " + mpCrawler.getRequest().getUrl());
				mpCrawler.setNextPage("");
			}
		} else {
			LOGGER.error("AiRenTiMpPipeline error ... strong == null url = " + mpCrawler.getRequest().getUrl());
		}
		// 下一个
		Elements page = document.getElementsByAttributeValue("class", "page");
		if (page == null) {
			LOGGER.error("AiRenTiMpPipeline error ... page == null url = " + mpCrawler.getRequest().getUrl());
			return;
		}
		Elements a = page.select("a");
		if (!CollectionUtils.isEmpty(a)) {
			String gb = a.get(0).attr("href");
			String[] tmp = gb.split("/");
			mpCrawler.setNextModel(
					"http://" + mpCrawler.getAddress() + "/" + mpCrawler.getDiyu() + "/" + tmp[1] + "/" + tmp[2]);
			System.out.println("next model : " + mpCrawler.getNextModel());
		} else {
			LOGGER.error("AiRenTiMpPipeline error ... a == null url = " + mpCrawler.getRequest().getUrl());
		}
		// 解析完毕，调用处理方法
		AiRenTiMpProcessor processor = new AiRenTiMpProcessor(mpCrawler);
		processor.processor();
	}
}
