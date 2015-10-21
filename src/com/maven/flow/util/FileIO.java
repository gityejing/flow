package com.maven.flow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;




public class FileIO {

    private static final Log logger = null;

    public static String readFile(String filePath) {
        String info = "";
        try {
            File f = new File(filePath);
            if (f.exists()) {
                if (!f.isDirectory()) {
                    FileInputStream bw = new FileInputStream(f);
                    int len = bw.available();
                    byte[] str = new byte[len];
                    if (bw.read(str) == -1) {
                        info = "";
                    } else {
                        info = new String(str);
                    }
                    bw.close();
                    bw = null;
                }
            }
            f = null;
        } catch (IOException e) {
            logger.error(e);
        }
        return info;
    }
    
    public static String readFile(String filePath, String charset) {
        String info = "";
        FileInputStream bw=null;
        byte[] str=null;
        try {
            File f = new File(filePath);
            if (f.exists()) {
                if (!f.isDirectory()) {
                    bw = new FileInputStream(f);
                    int len = bw.available();
                    str = new byte[len];
                    if (bw.read(str) == -1) {
                        info = "";
                    } else {
                        info = new String(str, charset);
                    }
                    str=null;
                    bw.close();
                    bw = null;
                } else {
                   
                }
            }
            f = null;
        } catch (IOException e) {
            //logger.error(e);
            if(bw!=null)
            {
                try{
                bw.close();
                bw=null;
                str=null;
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return info;
    }

    public static void writeFile(String msg, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream wf = new FileOutputStream(filePath);
            wf.write(msg.getBytes());
            wf.close();
            file = null;
            wf = null;
        } catch (IOException e) {
            //logger.error(e);
        }
    }

    public static void writeFile(String msg, String filePath, String charset) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream wf = new FileOutputStream(filePath);
            wf.write(msg.getBytes(charset));
            wf.close();
            file = null;
            wf = null;
        } catch (IOException e) {
            logger.error(e);
        }
    }

}