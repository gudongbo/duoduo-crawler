package com.duoduo.crawler.crawler.airenti;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geccocrawler.gecco.downloader.DownloaderContext;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;

/**
 * 处理网页内容
 * 
 * @author GDB
 *
 */
public class AiRenTiMpProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AiRenTiMpProcessor.class);
	private AiRenTiMpCrawler mpCrawler;

	public AiRenTiMpProcessor(AiRenTiMpCrawler mpCrawler) {
		this.mpCrawler = mpCrawler;
	}

	public void processor() {
		// if (LOGGER.isDebugEnabled()) {
		// LOGGER.debug("mpCrawler : " + mpCrawler.getRequest().getUrl());
		// LOGGER.debug("mpCrawler : " + mpCrawler.getTitle());
		// LOGGER.debug("mpCrawler : " + mpCrawler.getAuthor());
		// LOGGER.debug("mpCrawler : " + mpCrawler.getBody());
		// }
		imgsProcessor();
		AiRenTiMpCrawler.addWechatMpCrawlerByUrl(mpCrawler);
	}

	private void imgsProcessor() {
		// String url = mpCrawler.getRequest().getUrl();
		String baseFile = null;
		// File d = null;
		for (int i = 0; i < mpCrawler.getImgs().size(); i++) {
			Element element = mpCrawler.getImgs().get(i);

			if (baseFile == null) {
				if (element.hasAttr("alt")) {
					baseFile = element.attr("alt");
				}
				if (StringUtils.isBlank(baseFile)) {
					baseFile = String.valueOf(new Date().getTime());
				}
				baseFile = "E:\\资源\\airenti\\" + baseFile;
				// d = FileUtils.getFile("E:\\资源\\airenti", baseFile);
			}

			try {
				String src = element.attr("src");
				if (StringUtils.isBlank(src)) {
					continue;
				}
				src = src.replaceAll("\r", "");
				HttpRequest subRequest = mpCrawler.getRequest().subRequest(src);
				System.out.println("load img : " + src);
				HttpResponse subReponse = DownloaderContext.defaultDownload(subRequest);
				// TODO 上传阿里云服务器，然后改写src路径为全图片路径
				// byte[] bs = ByteStreams.toByteArray(subReponse.getRaw());
				// String imgBase64 = Base64Utils.encodeToString(bs);
				String type = getImgType(element);
				System.out.println("save img : " + src);
				saveFile(baseFile, String.valueOf(new Date().getTime()), type, subReponse.getRaw());

				// MemoryCacheImageInputStream mciis = new
				// MemoryCacheImageInputStream(subReponse.getRaw());
				// BufferedImage bi = ImageIO.read(mciis);
				// ImageIO.write(bi, type, new File(d, new Date().getTime() + "." + type));

				// File file = new File("E:/img/"+new Date().getTime()+"."+type);
				// FileUtils.writeByteArrayToFile(file, bs);

				// String imgUrl = "http://yueju-test.oss-cn-beijing.aliyuncs.com/"+fileName;

				// String baseHeader = "data:image/" + type + ";base64,";
				// element.attr("src", baseHeader + imgBase64);
				// element.attr("style", "margin: 15px 0;");
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
		mpCrawler.setAuthor(baseFile);
	}

	private void saveFile(String savePath, String fileName, String fileExt, InputStream is) throws IOException {

		File file = new File(savePath);
		// 如果没有路径的话创建指定路径
		if (!file.exists()) {
			file.mkdirs();
		}
		fileName = fileName + "." + fileExt;
		// String filePath = savePath + fileName;
		String filePath = fileName;

		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] buf = new byte[is.available()];
		is.read(buf);
		FileUtils.writeByteArrayToFile(new File(file, filePath), buf);
		// try {
		// FileOutputStream fos = new FileOutputStream(new File(file, filePath));
		// byte[] buf = new byte[8096];
		// int size = 0;
		// while ((size = bis.read(buf)) != -1) {
		// fos.write(buf, 0, size);
		// }
		//
		// fos.close();
		// bis.close();
		// } catch (Exception e) {
		// LOGGER.error(e.getMessage(), e);
		// }
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
