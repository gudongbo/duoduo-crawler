package com.duoduo.crawler.crawler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.geccocrawler.gecco.downloader.DownloaderContext;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.google.common.io.ByteStreams;


/**
 * 处理网页内容
 * 
 * @author GDB
 *
 */
public class WechatMpProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatMpProcessor.class);
	private WechatMpCrawler mpCrawler;

	public WechatMpProcessor(WechatMpCrawler mpCrawler) {
		this.mpCrawler = mpCrawler;
	}

	public void processor() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("mpCrawler : " + mpCrawler.getRequest().getUrl());
			LOGGER.debug("mpCrawler : " + mpCrawler.getTitle());
			LOGGER.debug("mpCrawler : " + mpCrawler.getAuthor());
			LOGGER.debug("mpCrawler : " + mpCrawler.getBody());
		}

		clearScript();
		clearStyle();
		imgsProcessor();
		// saveFile();

		WechatMpCrawler.addWechatMpCrawlerByUrl(mpCrawler);
	}

	private void clearStyle() {
		// 清掉class样式
		mpCrawler.getBody().attr("class", "");
		// 清除行级样式
		Elements styles = mpCrawler.getBody().select("[style]");
		if (styles.size() > 0) {
			for (int i = 0; i < styles.size(); i++) {
				Element element = styles.get(i);
				if (element.hasAttr("style")) {
					String tmp = element.attr("style");
					element.attr("yj-style", tmp);
					element.attr("style", "");
				}
			}
		}
		// 清除外链
		Elements hrefs = mpCrawler.getBody().select("[href]");
		if (hrefs.size() > 0) {
			for (int i = 0; i < hrefs.size(); i++) {
				Element element = hrefs.get(i);
				if (element.hasAttr("href")) {
					String tmp = element.attr("href");
					element.attr("yj-href", tmp);
					element.attr("href", "");
				}
			}
		}
	}

	private void clearScript() {
		Elements js = mpCrawler.getDocument().getElementsByTag("script");
		js.remove();
	}

	private void imgsProcessor() {
		for (int i = 0; i < mpCrawler.getImgs().size(); i++) {
			Element element = mpCrawler.getImgs().get(i);
			if (element.hasAttr("data-src")) {
				try {
					String src = element.attr("data-src");
					if (StringUtils.isBlank(src)) {
						continue;
					}
					HttpRequest subRequest = mpCrawler.getRequest().subRequest(src);
					HttpResponse subReponse = DownloaderContext.defaultDownload(subRequest);
					// TODO 上传阿里云服务器，然后改写src路径为全图片路径
//					byte[] bs = ByteStreams.toByteArray(subReponse.getRaw());
//					String imgBase64 = Base64Utils.encodeToString(bs);
					String type = getImgType(element);
					
//					saveFile("E:/img/", String.valueOf(new Date().getTime()), type, subReponse.getRaw());
					
//					MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(subReponse.getRaw());  
//					BufferedImage bi = ImageIO.read(mciis); 
//					ImageIO.write(bi, type, new File("E:/img/"+new Date().getTime()+"."+type));  
					
//					File file = new File("E:/img/"+new Date().getTime()+"."+type);
//					FileUtils.writeByteArrayToFile(file, bs);
					

//					String imgUrl = "http://yueju-test.oss-cn-beijing.aliyuncs.com/"+fileName;
					
//					String baseHeader = "data:image/" + type + ";base64,";
//					element.attr("src", baseHeader + imgBase64);
					element.attr("style", "margin: 15px 0;");
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	private void saveFile(String savePath,String fileName,String fileExt,InputStream is) {

		File file = new File(savePath);
		//如果没有路径的话创建指定路径
		if(!file.exists()){
		    file.mkdirs();
		 }
		fileName =  fileName +"." + fileExt;
		String filePath =savePath+ fileName;
		
		BufferedInputStream bis = new BufferedInputStream(is);
		try {
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1){
				fos.write(buf, 0, size);
				}
			
			
			fos.close();
			bis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String info = String.format("下载媒体文件成功，filePath=" + filePath);
	}

	private String getImgType(Element element) {
		String type = "jpeg";
		if (element.hasAttr("data-type")) {
			type = element.attr("data-type");
			if (StringUtils.isNotBlank(type)) {
				return type;
			}
		}
		if (element.hasAttr("data-src")) {
			String src = element.attr("data-src");
			if (src.contains("jpg") || src.contains("JPG")) {
				return "jpg";
			} else if (src.contains("jpeg") || src.contains("JPEG")) {
				return "jpeg";
			} else if (src.contains("gif") || src.contains("GIF")) {
				return "gif";
			} else if (src.contains("png") || src.contains("PNG")) {
				return "png";
			} else if (src.contains("bmp") || src.contains("BMP")) {
				return "bmp";
			} else if (src.contains("ico") || src.contains("ICO")) {
				return "ico";
			} else if (src.contains("tif") || src.contains("TIF")) {
				return "tif";
			} else if (src.contains("pdg") || src.contains("PDG")) {
				return "pdg";
			} else {
				return "jpeg";
			}
		}
		return "jpeg";
	}
}
