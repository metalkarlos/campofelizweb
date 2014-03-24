package com.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.web.cementerio.global.Parametro;

public class FileUtil {

	public void createFile(String path, byte[] bfile) throws Exception {
		FileOutputStream fos;
		
		try {
			fos = new FileOutputStream(new File(path));
			fos.write(bfile);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	public boolean deleteFile(String pathdirectory) throws Exception {
		boolean ok = true;
		
		try {
			File fileDir = new File(pathdirectory);
			if(fileDir.exists()){
				ok = fileDir.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
				
		return ok;
	}
	
	public boolean createDir(String pathdirectory) throws Exception {
		boolean ok = true;
		
		try {
			File fileDir = new File(pathdirectory);
			if(!fileDir.exists()){
				ok = fileDir.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
				
		return ok;
	}
	
	public InputStream getResourceAsStream(String name)
	{
		return this.getClass().getResourceAsStream(name);
	}
	
	public boolean existFile(String pathdirectory) throws Exception {
		boolean ok = true;
		
		try {
			File fileDir = new File(pathdirectory);
			ok = fileDir.exists();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
				
		return ok;
	}
	
	public Properties getPropertiesFile(String path) {
		Properties properties = new Properties();
		
		try {
			File file = new File(path);
			if(file.exists()){
				InputStream in = new FileInputStream(file);
				properties.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return properties;
	}
	
	public String getPropertyValue(String key) throws Exception {
		String value = null;
		
		try {
			Properties properties = getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			value = properties.getProperty(key);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return value;
	}
	
	public String getFileExtention(String fileName){
		String fileExtention = "";
		
		//Maner 1
		//fileExtention = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		
		//Maner 2
		String[] partes = fileName.split("\\.");
		fileExtention = partes[partes.length-1];
		
		//Maner 3
		/*char[] ch = fileName.toCharArray();
		for(int i = 0; i <ch.length; i++){
			if(ch[i] == '.'){
				fileExtention = ".";
			}else{
				fileExtention = fileExtention + ch[i];
			}
		}*/
		
		return fileExtention;
	}
}
