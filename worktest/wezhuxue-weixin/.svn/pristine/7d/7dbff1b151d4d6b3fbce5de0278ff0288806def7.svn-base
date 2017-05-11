package com.zhuxueup.common.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TwoImageCompose {

	public static void createPicTwo2(String filePath1, String filePath2,
			String outFilePath, int x, int y) {
		try {
			// 读取第一张图
			File fileOne = new File(filePath1);
			BufferedImage ImageOne = ImageIO.read(fileOne);
			int width = ImageOne.getWidth();// 图片宽度
			int height = ImageOne.getHeight();// 图片高度

			System.out.println("image 1 width:" + width + "height :" + height);
			// 从图片中读取RGB
			int[] ImageArrayOne = new int[width * height];
			ImageArrayOne = ImageOne.getRGB(0, 0, width, height, ImageArrayOne,
					0, width);
			// 对第二张图片做相同的处理
			File fileTwo = new File(filePath2);
			BufferedImage ImageTwo = ImageIO.read(fileTwo);
			int widthTwo = ImageTwo.getWidth();// 图片宽度
			int heightTwo = ImageTwo.getHeight();// 图片高度
			System.out.println("image 1 widthTwo:" + widthTwo + "heightTwo :"
					+ heightTwo);

			int[] ImageArrayTwo = new int[widthTwo * heightTwo];
			ImageArrayTwo = ImageTwo.getRGB(0, 0, widthTwo, heightTwo,
					ImageArrayTwo, 0, widthTwo);

			// 生成新图片
			BufferedImage ImageNew = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			ImageNew.setRGB(0, 0, width, height, ImageArrayOne, 0, width);// 设置左半部分的RGB
			ImageNew.setRGB(x, y, widthTwo, heightTwo, ImageArrayTwo, 0,
					widthTwo);// 设置右半部分的RGB
			// ImageNew.setRGB(x*2,y,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);//设置右半部分的RGB
			File outFile = new File(outFilePath);
			ImageIO.write(ImageNew, "png", outFile);// 写图片

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { TwoImageCompose he = new
	 * TwoImageCompose(); he.createPicTwo2(264, 1044); }
	 */

}