package io.gitee.mc_shd1.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
	public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();
        
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath  + file.separator  + filePath[i], newPath + file.separator + filePath[i]);
            }
            
            if (new File(sourcePath  + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }
            
        }
    }
	public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
    
        in.close();
    	out.close();
    }
	public static boolean dirExist(String path) {
		File file = new File(path);
		//if (!file.isDirectory()) {
			//Message.LOG.error("错误: 无效的路径");
			//return false;
		//}
		return file.exists(); 
	}
	public static boolean fileExist(String path) {
		File file = new File(path);
		//if (!file.isFile()) {
			//Message.LOG.error("错误: 无效的文件");
			//return false;
		//}
		return file.exists(); 
	}
	public static void renameFileAndDir(String path, String newName) {
		new File(path).renameTo(new File(newName));
	}
}
