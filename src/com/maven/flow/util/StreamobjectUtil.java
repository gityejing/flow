package com.maven.flow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class StreamobjectUtil {
	public static String filepath = "c:\\tmp\\";
	public static void main(String[] args) throws Exception {
		HashMap map = new HashMap();

		map.put("11", "22");
		File f = new File("c:\\a.txt");
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.flush();
		fos.close();

		//

		FileInputStream fis = new FileInputStream(f);
		int l = fis.available();
		byte[] body = new byte[l];
		fis.read(body, 0, l);
		fis.close();
		System.out.println(body.length);
		System.out.println(body);

		// HashMap fmap=getObject("c:\\a.txt");
		// System.out.println(fmap.get("11"));

	}

	public static byte[] ObjectToByte(Object obj) throws Exception {
		
		String tempPath=filepath;
		File f = new File(filepath);
		if (!f.exists()) {
			f.mkdirs();
		}
		tempPath = tempPath + "in.obj";
		
		
		f = new File(tempPath);
		if (f.exists()) {
			//f.delete();
		} else {
			if (!f.createNewFile()) {
				System.out.println("创建文件失败!!!!!!!!!!!!");
				//throw new Exception("无法创建文件：" + f.getName());
			}
		}
		
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		fos.close();

		//

		FileInputStream fis = new FileInputStream(f);
		int l = fis.available();
		byte[] body = new byte[l];
		fis.read(body, 0, l);
		fis.close();

		return body;
	}

	public static Object byteToObject(byte[] bytes)
			throws Exception {
		
		String tempPath=filepath;
		File f = new File(filepath);
		if (!f.exists()) {
			f.mkdirs();
		}
		tempPath = tempPath + "out.obj";
		f = new File(tempPath);
		
		f = new File(tempPath);
		if (f.exists()) {
			//f.delete();
		} else {
			if (!f.createNewFile()) {
				System.out.println("创建文件失败!!!!!!!!!!!!");
				//throw new Exception("无法创建文件：" + f.getName());
			}
		}
		
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bytes);
		fos.flush();
		fos.close();
		
		
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		fis.close();
		
		
		return obj;

	}
}
