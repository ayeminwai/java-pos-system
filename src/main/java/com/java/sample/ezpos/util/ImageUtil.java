package com.java.sample.ezpos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class ImageUtil {
	public static String getImageToBase64(String imageName, String image_path) throws IOException {
		String imageDataString = "";
		String path = image_path + imageName;
		File img = new File(path);
		if (img.exists()) {
			try {
				byte[] fileContent = FileUtils.readFileToByteArray(img);
				imageDataString = Base64.getEncoder().encodeToString(fileContent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return imageDataString;
	}
}
