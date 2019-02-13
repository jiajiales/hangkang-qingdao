package com.hot.manage.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ValidateCodeUtil {
	/**
	 * 图片宽度
	 */
	private int w=100;
	
	/**
	 * 图片高度
	 */
	private int h=40;
	
	private Random ran=new Random();
	
	/**
	 * 字体
	 */
	private String[] fontNames={"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};
	
	/**
	 * 可选字符
	 */
	private String codes="23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	
	/**
	 * 背景色
	 */
	private Color bgColor=new Color(205, 200, 235);
	
	/**
	 * 验证码上的文本
	 */
	private String text;
	
	/**
	 * 生成随机的颜色
	 * @return Color
	 */
	private Color randomColor() {
		int red = ran.nextInt(150);
		int green = ran.nextInt(150);
		int blue = ran.nextInt(150);
		return new Color(red, green, blue);
	}
	/**
	 * 生成随机的字体
	 * @return Font
	 */
	private Font randomFont(){
		int index = ran.nextInt(fontNames.length);
		String fontName = fontNames[index];//生成随机的字体名称
		int style = ran.nextInt(4);//生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
		int size=ran.nextInt(5)+24;//生成随机字号, 24 ~ 28
		return new Font(fontName, style, size);	
	}
	
	/**
	 * 画干扰线
	 * @param image
	 */
	private void drawLine(BufferedImage image) {
		int num = 5;// 画5条
		Graphics2D grap = (Graphics2D) image.getGraphics();
		for (int i = 0; i < num; i++) {
			int x1 = ran.nextInt(w);
			int y1 = ran.nextInt(h);
			int x2 = ran.nextInt(w);
			int y2 = ran.nextInt(h);
			grap.setStroke(new BasicStroke(1.5F));
			grap.setColor(randomColor());// 随机生成干扰线颜色
			grap.drawLine(x1, y1, x2, y2);// 画线
		}
	}
	
	/**
	 * 随机生成字符
	 * @return
	 */
	private char randomChar() {
		int index = ran.nextInt(codes.length());
		return codes.charAt(index);
	}
	
	/**
	 * 创建BufferedImage
	 * @return
	 */
	private BufferedImage createImage() {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D grap = (Graphics2D) image.getGraphics();
		grap.setColor(this.bgColor);
		grap.fillRect(0, 0, w, h);
		return image;
	}
	
	/**
	 * 获取验证码
	 * @return
	 */
	public BufferedImage getImage(){
		BufferedImage image = createImage();//创建图片缓存区
		Graphics2D grap = (Graphics2D)image.getGraphics();//得到绘制环境
		StringBuilder sb = new StringBuilder();//装生成的验证码文本
		for (int i = 0; i < 4; i++) {
			String s=randomChar()+"";//随机生成字符
			sb.append(s);
			float x=i*1.0F*w/4;//设置当前字符的x轴坐标
			grap.setFont(randomFont());
			grap.setColor(randomColor());
			grap.drawString(s, x, h-5);
		}
		this.text=sb.toString();
		drawLine(image);//画图
		return image;	
	}
	
	/**
	 * 返回验证码图片上的文本
	 * @return
	 */
	public String getText() {
		return text;	
	}
	
	/**
	 * 保存图片到指定的输出流
	 * @param image
	 * @param out
	 * @throws IOException
	 */
	public static void output(BufferedImage image, OutputStream out) throws IOException {
		ImageIO.write(image, "JPEG", out);
	}
}
