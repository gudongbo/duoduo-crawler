package com.duoduo.crawler.crawler;

import java.lang.reflect.Field;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.geccocrawler.gecco.annotation.FieldRenderName;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.CustomFieldRender;

import net.sf.cglib.beans.BeanMap;

/**
 * 自定义渲染
 * 
 * @author GDB
 *
 */
@FieldRenderName("wechatMpFieldRander")
public class WechatMpFieldRander implements CustomFieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean, Field field) {
		String content = response.getContent();
		Document document = Jsoup.parse(content);
		beanMap.put(field.getName(), document);
	}

}
