package com.hol.folder.util;

import java.io.File;

import android.os.Environment;

public class FolderUtils {
	public static final String DIR_MNT = "/mnt";
	public static final String DIR_SD = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	public static File[] getCurrentDirFolders(String dir){
		File dirFile = new File(dir);
		return getCurrentDirFolders(dirFile);
	}
	
	public static File[] getCurrentDirFolders(File dirFile){
		File[] fileList = null;
		if (dirFile.isDirectory()){
			fileList = dirFile.listFiles();
		}
		return fileList;
	}
	
}
