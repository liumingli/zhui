package com.ybcx.zhui.tools;

import java.io.File;
import java.io.IOException;

public class FfmpegProcess {
	
	public static void imageToVideo(String ffmpegPath, String imgFolder,String videoPath,String videoSize){
		
	try {
			//空格问题，需要转译一下
			imgFolder = imgFolder.replaceAll(" ", "\" \"");
			videoPath = videoPath.replaceAll(" ", "\" \"");
			String cmdStr =" -r 4 -f image2 -i "+imgFolder+File.separator+"%d.png -s "+videoSize+" -aspect 4:3 -y "+videoPath;
			Process p =exec(ffmpegPath +cmdStr,false);
			System.out.println(p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * wrapper for Runtime.exec. No input/output. Optionally wait for child to
	 * finish.
	 * 
	 * @param command
	 *            fully qualified *.exe or *.com command
	 * @param wait
	 *            true if you want to wait for the child to finish.
	 */
	public static Process exec(String command, boolean wait) {
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			return null;
		}
		if (wait) {
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		// You must close these even if you never use them!
		try {
			p.getInputStream().close();
			p.getOutputStream().close();
			p.getErrorStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

}
