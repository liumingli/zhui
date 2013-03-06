package com.ybcx.zhui.tools;
/**
 * 删除文件夹，先删除文件夹下的内容，再删空文件夹
 */
import java.io.File;

public class DeleteFile {

	public Boolean delFolder(String folderPath) {
		Boolean result = false;
		try {
			result = delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new File(filePath);
			result =myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				try {
					temp.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}
