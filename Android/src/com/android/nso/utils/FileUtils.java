package com.android.nso.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.util.Log;

public class FileUtils {
	public static String DirPath = "data/data/itp.android.educationapp/DataLocal";
	public static String readFile(String foldername, String filename) {
		String ret = "", line = "";

		File folder = new File(foldername);
		if (!folder.exists())
			folder.mkdirs();

		try {
			File file = new File(foldername, "/" + filename);
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(fileInputStream));
			try {
				while ((line = bufferedReader.readLine()) != null) {
					ret = ret + line;
				}
				Log.d("ToanNM", "Return call readFile(): " + ret);
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return ret;
	}
	/**
	 * This method create a new file and write the content to file.
	 * Overwrite if existed
	 * */
	public static boolean createFile(String foldername, String filename, String content) {
		boolean created = false;

		File folder = new File(foldername);
		if (!folder.exists())
			folder.mkdirs();

		try {
			File file = new File(foldername, "/" + filename);
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
			writer.append(content);
			writer.flush();
			writer.close();
			fileOutputStream.close();

			created = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return created;

	}
	/**
	 * This method create a new file and write the content to file.
	 * Overwrite if existed
	 * */
	public static boolean createFile(String folderName, String fileName, String content, boolean overwrite){
		boolean created = false;

		File folder = new File(folderName);
		if (!folder.exists())
			folder.mkdirs();
		try {
			File file = new File(folderName, "/" + fileName);
			if(!overwrite && file.exists()) return false;
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
			writer.append(content);
			writer.flush();
			writer.close();
			fileOutputStream.close();

			created = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return created;
	}
}
