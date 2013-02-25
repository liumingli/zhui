package com.ybcx.zhui.facade;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.ybcx.zhui.dao.DBAccessInterface;

public class ComicServiceImplement implements ComicServiceInterface {

	private Logger log = Logger.getLogger(ComicServiceImplement.class);
	
	// 由Spring注入
	private DBAccessInterface dbVisitor;
	
	public void setDbVisitor(DBAccessInterface dbVisitor) {
		this.dbVisitor = dbVisitor;
	}

	private String imagePath;
	
	@Override
	public void saveImagePathToProcessor(String filePath) {
	//	this.imgProcessor.setImagePath(filePath);
		imagePath = filePath;
	}
	
	private Properties systemConfigurer;

	public void setSystemConfigurer(Properties systemConfigurer) {
		this.systemConfigurer = systemConfigurer;
	}
	
	// 设定输出的类型
	private static final String GIF = "image/gif;charset=UTF-8";

	private static final String JPG = "image/jpeg;charset=UTF-8";

	private static final String PNG = "image/png;charset=UTF-8";
	
	private static final String SWF = "application/x-shockwave-flash;charset=UTF-8";
		
	
	public String createAdImg(FileItem adData) {
		String type = "";
		if (adData != null) {
			String fileName = adData.getName();
			int dotPos = fileName.lastIndexOf(".");
			type = fileName.substring(dotPos);
		}

		Date date = new Date();//获取当前时间
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");
		String newfileName = sdfFileName.format(date);//文件名称
		
		String path = imagePath + File.separator + newfileName + type;
		try {
			BufferedInputStream in = new BufferedInputStream(adData.getInputStream());
			// 获得文件输入流
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(path)));// 获得文件输出流
			Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹
		} catch (IOException e) {
			e.printStackTrace();
		}
		//上传成功，则插入数据库
		if (new File(path).exists()) {
			//保存到数据库
			System.out.println("保存成功"+path);
		}
		return path;
	}


//	private String saveAnimationRaw(FileItem imgData) {
//		String fileName = imgData.getName();
//		int position = fileName.lastIndexOf(".");
//		String extend = fileName.substring(position);
//		String newName = fileName.substring(0,position)+"_Raw"+extend;
//		String filePath = imagePath + File.separator + newName;
//		File file = new File(filePath);
//		try {
//			imgData.write(file);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return filePath;
//	}

	

	@Override
	public void getThumbnailFile(String relativePath, HttpServletResponse res) {
		try {
			//默认
			File defaultImg = new File(imagePath + File.separator + "default.png");
			InputStream defaultIn = new FileInputStream(defaultImg);
			
			String type = relativePath.substring(relativePath.lastIndexOf(".") + 1);
			File file = new File(imagePath+File.separator+relativePath);
			
			if (file.exists()) {
				InputStream imageIn = new FileInputStream(file);
				if (type.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")) {
					writeJPGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("png")) {
					writePNGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("gif")) {
					writeGIFImage(imageIn, res, file);
				} else {
					writePNGImage(defaultIn, res, file);
				}
			} else {
				writePNGImage(defaultIn, res, defaultImg);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void writeJPGImage(InputStream imageIn, HttpServletResponse res, File file) {
		try {
//			res.addHeader("content-length",String.valueOf(file.length()));
			res.setContentType(JPG);
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
			// 得到编码后的图片对象
			BufferedImage image = decoder.decodeAsBufferedImage();
			// 得到输出的编码器
			OutputStream out = res.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			// 对图片进行输出编码
			imageIn.close();
			// 关闭文件流
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writePNGImage(InputStream imageIn, HttpServletResponse res, File file) {
//		res.addHeader("content-length",String.valueOf(file.length()));
		res.setContentType(PNG);
		getOutInfo(imageIn, res);
	}

	private void writeGIFImage(InputStream imageIn, HttpServletResponse res, File file) {
//		res.addHeader("content-length",String.valueOf(file.length()));
		res.setContentType(GIF);
		getOutInfo(imageIn, res);
	}

	private void getOutInfo(InputStream imageIn, HttpServletResponse res) {
		try {
			OutputStream out = res.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(imageIn);
			// 输入缓冲流
			BufferedOutputStream bos = new BufferedOutputStream(out);
			// 输出缓冲流
			byte data[] = new byte[4096];
			// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();
			// 清空输出缓冲流
			bos.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void getAssetFile(String relativePath, HttpServletResponse res) {
		try {
			//默认
			File defaultImg = new File(imagePath + File.separator + "default.png");
			InputStream defaultIn = new FileInputStream(defaultImg);
			
			String type = relativePath.substring(relativePath.lastIndexOf(".") + 1);
			File file = new File(imagePath+File.separator +relativePath);
			
			if (file.exists()) {
				InputStream imageIn = new FileInputStream(file);
				if(type.toLowerCase().equals("swf")){
					writeSWF(imageIn,res,file);
				}else if (type.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")) {
					writeJPGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("png")) {
					writePNGImage(imageIn, res, file);
				} else if (type.toLowerCase().equals("gif")) {
					writeGIFImage(imageIn, res, file);
				} else {
					writePNGImage(defaultIn, res, file);
				}
			} else {
				writePNGImage(defaultIn, res, defaultImg);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeSWF(InputStream imageIn, HttpServletResponse res, File file) {
//		res.addHeader("content-length",String.valueOf(file.length()));
		res.setContentType(SWF);
		getOutInfo(imageIn, res);
	}


	@Override
	public String createAnimation(FileItem shotData, String userId,
			String name, String content) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void dialogueToImage(String dialogue, String fontSize,
			String isBold, String width, String height,HttpServletResponse res) {
		// TODO Auto-generated method stub
		res.setContentType(PNG);
		String filePath = createDialogueImage(dialogue,Integer.parseInt(fontSize),Integer.parseInt(isBold),Integer.parseInt(width),Integer.parseInt(height));
		File file = new File(filePath);
		if (file.exists()) {
			try {
				InputStream imageIn = new FileInputStream(file);
				writeGIFImage(imageIn, res, file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
		}
	}

	
	private String createDialogueImage(String dialogue, int fontSize, int isBold, int width, int height ) {

		//命名新图片文件
		String fileName = System.currentTimeMillis() + ".png";
		String path = imagePath + File.separator + fileName;
		File file = new File(path);

		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 设置背景透明
		Graphics2D g2 = bi.createGraphics();
		bi = g2.getDeviceConfiguration().createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		g2.dispose();
		g2 = bi.createGraphics();

		/** 设置生成图片的文字样式 * */
		Font font = new Font("华康少女文字W5", isBold, fontSize);
		g2.setFont(font);
		g2.setPaint(Color.blue);
		
		/**文字拆分换行*/
		//一行最长宽度
		int maxWidth = width;
		//存放文字拆分后的数组
		List<String> strList = new ArrayList<String>();
		
		FontMetrics metrics = g2.getFontMetrics(g2.getFont()); 
	    int strHeight = metrics.getHeight(); 
		
		int currWidth  = 0;
		StringBuffer sb = new StringBuffer();
		//记录是否超过一行
		boolean hasSave = false;
		
		for(int i=0; i<dialogue.length(); i++) {
			char s = dialogue.charAt(i);
			int sWidth = metrics.stringWidth(String.valueOf(s));
			currWidth = currWidth+sWidth;
			sb.append(s);
			
			//如果超过了一行了，就保存 4是左右间距
			if(currWidth <= maxWidth-4 && currWidth >=maxWidth-4-sWidth ) {
				currWidth = 0;
				String rowStr = sb.toString();
				strList.add(rowStr);
				sb.setLength(0);
				hasSave = true;
			}
		}
		
		//如果不到一行，直接保存
		if(!hasSave){
			strList.add(dialogue);
		} else{ //把剩下的不够一行的保存下来
			String rowStr2 = sb.toString();
			strList.add(rowStr2);
		}
		
		/** 防止生成的文字带有锯齿 * */
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		/** 在图片上生成文字 * */
		for(int j=0; j<strList.size(); j++) {
			g2.drawString(strList.get(j), 4, (j+1)*strHeight);
		}

		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	
}
