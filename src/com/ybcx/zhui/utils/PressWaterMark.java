package com.ybcx.zhui.utils;

import java.awt.AlphaComposite;  
import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.awt.image.ImageFilter;
import java.io.File;  
import java.io.FileFilter;  
import java.io.FilenameFilter;
  
import javax.imageio.ImageIO;  
  
public class PressWaterMark {  
  
    /** 
     * @param srcFile 
     *            要加水印的源文件 
     * @param waterMarkFile 
     *            水印文件，为保证质量，最好使用Gif格式，背景设为透明 
     * @param alpha 
     *            透明度，0~1之间，0透明，1不透明 
     * @param degree 
     *            水印旋转角度 
     * @throws Exception 
     *             使用一个水印文件为源文件加上水印，水印铺满全部图片 
     */  
    public final static void pressImage(File srcFile, File waterMarkFile,  
            float alpha, double degree) throws Exception {  
        // 加载目标图片  
        Image srcImg = ImageIO.read(srcFile);  
        int src_width = srcImg.getWidth(null);  
        int src_height = srcImg.getHeight(null);  
        // 将图片加载到内存  
        BufferedImage bufImg = new BufferedImage(src_width, src_height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = bufImg.createGraphics();  
        g.drawImage(srcImg, 0, 0, src_width, src_height, null);  
        // 加载水印图片  
        Image waterMarkImage = ImageIO.read(waterMarkFile);  
        int w_width = waterMarkImage.getWidth(null);  
        int w_height = waterMarkImage.getHeight(null);  
        // 设置透明度  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  
                alpha));  
        g.rotate(-Math.PI / 180 * degree, (double) bufImg.getWidth() / 2,  
                (double) bufImg.getHeight() / 2);  
  
        // 设置水印图片位置  
  
//        for (int x = 0; x < src_width; x += 3 * w_width)  
//            for (int y = 0; y < src_height; y += 3 * w_height)  
        int x=100;
        int y=200;
        // 将水印图片“画”在目标图片的指定位置  
        g.drawImage(waterMarkImage, x, y, w_width, w_height, null);  
        g.dispose();  
        ImageIO.write(bufImg, "png", srcFile);  
    }  
  
    /** 
     * @param waterText 
     *            水印字符串 
     * @param font 
     *            字符串字体 
     * @param alpha 
     *            透明度，0~1之间，0透明，1不透明 
     * @param degree 
     *            字符串旋转角度 
     * @throws Exception 
     *             ImageIO读取失败时，抛出异常 
     */  
    public static void pressText(File scrFile, String waterText, Font font,  
            float alpha, double degree) throws Exception {  
        // 加载目标图片  
        Image srcImg = ImageIO.read(scrFile);  
        int src_width = srcImg.getWidth(null);  
        int src_height = srcImg.getHeight(null);  
  
        // 将目标图片加载到内存  
        BufferedImage bufImg = new BufferedImage(src_width, src_height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = bufImg.createGraphics();  
        g.drawImage(srcImg, 0, 0, src_width, src_height, null);  
  
        // 设置水印文字透明度  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  
                alpha));  
        g.rotate(-Math.PI / 180 * degree, (double) bufImg.getWidth() / 2,  
                (double) bufImg.getHeight() / 2);  
        g.setFont(font);  
        g.setColor(Color.black);  
  
        // 设置水印文字的位置  
        int t_width = font.getSize() * waterText.length();  
        int t_height = font.getSize();  
        // 写水印文字  
//        for (int x = 0; x < src_width; x += t_width + 150)  
//            for (int y = 0; y < src_height; y += 3 * t_height)  
        int x=200;
        int y=300;
                // 将水印图片“画”在目标图片的指定位置  
                g.drawString(waterText, x, y);  
        g.dispose();  
        // 保存目标图片  
        ImageIO.write(bufImg, "JPG", scrFile);  
    }  
  
    public static void main(String args[]) throws Exception {  
        File f = new File("F:/test");  
        File imgFiles[] = f.listFiles();  
        for (File x : imgFiles) {  
            System.out.println(x.getName() + "正在加水印...");  
            //pressImage(x, new File("F:/test.png"), 1f, 0);  
            Font font = new Font("华康少女文字W5", Font.BOLD, 15);
            pressText(x,"测试",font,1f,0);
        }  
        System.out.println("谢谢使用！");  
    }  
} 