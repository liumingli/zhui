package com.ybcx.zhui.tools;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImgDataProcessor {
	
	private ExecutorService pool;

	// 线程多少由Spring配置
	public ImgDataProcessor(int threadNumber) {
		pool = Executors.newFixedThreadPool(threadNumber);
	}

	// 只管往里扔数据就行了，任务队列自动会排队执行
	public void createImageFile(File oldFile,File newFile,File waterMarkFile,int x,int y){
		
		ImageFileCreationTask imgTask = new ImageFileCreationTask();
		imgTask.setOldFile(oldFile);
		imgTask.setNewFile(newFile);
		imgTask.setWaterMarkFile(waterMarkFile);
		imgTask.setX(x);
		imgTask.setY(y);
		pool.execute(imgTask);
		
	}

	public void shutdownProcess() {
		pool.shutdown();
	}

}

