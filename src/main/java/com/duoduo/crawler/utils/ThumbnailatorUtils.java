package com.duoduo.crawler.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;

public class ThumbnailatorUtils {

	private static Logger logger = LoggerFactory.getLogger(ThumbnailatorUtils.class);

	/**
	 * 指定大小进行缩放 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
	 * 若图片横比width大，高比height小，横缩小到width，图片比例不变
	 * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public static void imgThumb(String source, String output, int width, int height) {
		try {
			Thumbnails.of(source).size(width, height).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 指定大小进行缩放
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public static void imgThumb(File source, String output, int width, int height) {
		try {
			Thumbnails.of(source).size(width, height).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 按照比例进行缩放
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public static void imgScale(String source, String output, double scale) {
		try {
			Thumbnails.of(source).scale(scale).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgScale(File source, String output, double scale) {
		try {
			Thumbnails.of(source).scale(scale).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 不按照比例，指定大小进行缩放
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param keepAspectRatio
	 *            默认是按照比例缩放的,值为false 时不按比例缩放
	 */
	public static void imgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) {
		try {
			Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgNoScale(File source, String output, int width, int height, boolean keepAspectRatio) {
		try {
			Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 旋转 ,正数：顺时针 负数：逆时针
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param rotate
	 *            角度
	 */
	public static void imgRotate(String source, String output, int width, int height, double rotate) {
		try {
			Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgRotate(File source, String output, int width, int height, double rotate) {
		try {
			Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 水印
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输入源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param position
	 *            水印位置 Positions.BOTTOM_RIGHT o.5f
	 * @param watermark
	 *            水印图片地址
	 * @param transparency
	 *            透明度 0.5f
	 * @param quality
	 *            图片质量 0.8f
	 */
	public static void imgWatermark(String source, String output, int width, int height, Position position,
			String watermark, float transparency, float quality) {
		try {
			Thumbnails.of(source).size(width, height)
					.watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f)
					.toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgWatermark(File source, String output, int width, int height, Position position,
			String watermark, float transparency, float quality) {
		try {
			Thumbnails.of(source).size(width, height)
					.watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f)
					.toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 裁剪图片
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param position
	 *            裁剪位置
	 * @param x
	 *            裁剪区域x
	 * @param y
	 *            裁剪区域y
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param keepAspectRatio
	 *            默认是按照比例缩放的,值为false 时不按比例缩放
	 */
	public static void imgSourceRegion(String source, String output, Position position, int x, int y, int width,
			int height, boolean keepAspectRatio) {
		try {
			Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio)
					.toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgSourceRegion(File source, String output, Position position, int x, int y, int width,
			int height, boolean keepAspectRatio) {
		try {
			Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio)
					.toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 按坐标裁剪
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param x
	 *            起始x坐标
	 * @param y
	 *            起始y坐标
	 * @param w
	 *            要裁切的宽
	 * @param h
	 *            要裁切的高
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param keepAspectRatio
	 *            默认是按照比例缩放的,值为false 时不按比例缩放
	 */
	public static void imgSourceRegion(String source, String output, int x, int y, int w, int h, int width, int height,
			boolean keepAspectRatio) {
		try {
			Thumbnails.of(source).sourceRegion(x, y, w, h).size(width, height).keepAspectRatio(keepAspectRatio)
					.toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgSourceRegion(File source, String output, int x, int y, int w, int h, int width, int height,
			boolean keepAspectRatio) {
		try {
			Thumbnails.of(source).sourceRegion(x, y, w, h).size(width, height).keepAspectRatio(keepAspectRatio)
					.toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 转化图像格式
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param format
	 *            图片类型，gif、png、jpg
	 */
	public static void imgFormat(String source, String output, int width, int height, String format) {
		try {
			Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void imgFormat(File source, String output, int width, int height, String format) {
		try {
			Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 输出到OutputStream
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return toOutputStream(流对象)
	 */
	public static OutputStream imgOutputStream(String source, String output, int width, int height) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(output);
			Thumbnails.of(source).size(width, height).toOutputStream(os);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return os;
	}

	public static OutputStream imgOutputStream(File source, String output, int width, int height) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(output);
			Thumbnails.of(source).size(width, height).toOutputStream(os);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return os;
	}

	/**
	 * 输出到BufferedImage
	 * 
	 * @param source
	 *            输入源
	 * @param output
	 *            输出源
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param format
	 *            图片类型，gif、png、jpg
	 * @return BufferedImage
	 */
	public static BufferedImage imgBufferedImage(String source, String output, int width, int height, String format) {
		BufferedImage buf = null;
		try {
			buf = Thumbnails.of(source).size(width, height).asBufferedImage();
			ImageIO.write(buf, format, new File(output));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return buf;
	}

	public static BufferedImage imgBufferedImage(File source, String output, int width, int height, String format) {
		BufferedImage buf = null;
		try {
			buf = Thumbnails.of(source).size(width, height).asBufferedImage();
			ImageIO.write(buf, format, new File(output));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return buf;
	}

	public static void main(String[] args) throws IOException {
		File file = FileUtils.getFile("D:\\tmp\\test.JPEG");
		BufferedImage image = ImageIO.read(file);
		int width = image.getWidth();
		int height = image.getHeight();
		File output = new File(file.getParent(), "test-" + file.getName());
		ThumbnailatorUtils.imgSourceRegion(file, output.getAbsolutePath(), 0, 60, width, height - 60 - 50, width,
				height - 60 - 50, Boolean.FALSE);
	}
}
