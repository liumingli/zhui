package com.ybcx.zhui.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ybcx.zhui.beans.CountObj;

public class ImageFileCreationTask implements Runnable {
	
		public CountObj countObj;
		private File oldFile;
		
		private File newFile;
		
		private File markFile;
		
		private File dialogueFile;
	
		private int x;
		
		private int y;
		
		@Override
		public void run() {
			//复制图片
			try {
				createImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void createImage() throws IOException {
//			Image srcImg = ImageIO.read(oldFile);
//			int src_width = srcImg.getWidth(null);
//			int src_height = srcImg.getHeight(null);
//			// 将图片加载到内存
//			BufferedImage bufImg = new BufferedImage(src_width, src_height,
//					BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = bufImg.createGraphics();
//			g.drawImage(srcImg, 0, 0, src_width, src_height, null);
//			g.dispose();
//			
//			ImageIO.write(bufImg, "png", newFile);
			
			// 加载目标图片
			Image srcImg = ImageIO.read(oldFile);
			int src_width = srcImg.getWidth(null);
			int src_height = srcImg.getHeight(null);
			// 将图片加载到内存
			BufferedImage bufImg = new BufferedImage(src_width, src_height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, src_width, src_height, null);
			
			// 加载@水印图片
			Image markImage = ImageIO.read(markFile);
			int m_width = markImage.getWidth(null);
			int m_height = markImage.getHeight(null);
			// 将水印图片“画”在目标图片的指定位置
			g.drawImage(markImage, 420, 340, m_width, m_height, null);
			
		
			if(dialogueFile != null){
				// 加载水印图片
				Image dialogueImage = ImageIO.read(dialogueFile);
				int w_width = dialogueImage.getWidth(null);
				int w_height = dialogueImage.getHeight(null);
				// 将水印图片“画”在目标图片的指定位置
				g.drawImage(dialogueImage, x, y, w_width, w_height, null);
			}
			
			g.dispose();
			
			ImageIO.write(bufImg, "png", newFile);
			countObj.count -= 1;
		}

		public void setOldFile(File oldFile) {
			this.oldFile = oldFile;

		}
		
		public void setNewFile(File newFile) {
			this.newFile = newFile;
		}
		
		public void setDialogueFile(File dialogueFile) {
			this.dialogueFile = dialogueFile;
		}


		public void setMarkFile(File markFile) {
			this.markFile = markFile;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

}
