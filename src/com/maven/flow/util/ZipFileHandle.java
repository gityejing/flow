package com.maven.flow.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipFileHandle {

    private static Log log = LogFactory.getLog(ZipFileHandle.class);
    static final int BUFFER = 2048;


    public static String createdZipFile(String dir, String fileName) throws
            IOException {

        String zipFileName = (fileName.replaceAll(dir, "")).replaceAll("\\.xml",
                ".zip");

        try {
            FileOutputStream dest = new FileOutputStream(dir + zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            addZipFile(dir, fileName, out);
            addZipFile(dir,
                       fileName.replaceAll("\\.xml", "_BQItemPriceAnalysis.xml"),
                       out);
            addZipFile(dir, fileName.replaceAll("\\.xml",
                                                "_BQNormItemPriceAnalysis.xml"),
                       out);
            out.close();

        } catch (IOException zip) {
            log.debug("Zip", zip.getCause());
        }
        return zipFileName;
    }

    /**
     * 创建ZIP文件
     * @param zipFileName String
     * @param fileName String
     * @throws IOException
     */
    public static String createdZipFile(String dir, String zipFileName,
                                        String fileName) throws
            IOException {
        try {
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            addZipFile(dir, fileName, out);
            addZipFile(dir,
                       fileName.replaceAll("\\.xml", "_BQItemPriceAnalysis.xml"),
                       out);
            addZipFile(dir, fileName.replaceAll("\\.xml",
                                                "_BQNormItemPriceAnalysis.xml"),
                       out);
            out.close();
        } catch (IOException zip) {
            log.debug("Zip", zip.getCause());
        }
        return zipFileName;
    }

    /**
     * 添加文件到Zip文档
     * @param fileName String
     * @param out ZipOutputStream
     * @throws IOException
     */
    public static void addZipFile(String dir, String fileName,
                                  ZipOutputStream out) throws
            IOException {
        try {
            BufferedInputStream origin = null;
            FileInputStream fi = new FileInputStream(dir + "/" + fileName);
            fileName = fileName.replaceAll("\\\\", "/");
            int pos = fileName.lastIndexOf("/");
            String zipFileName = fileName.substring(pos + 1);//java.net.URLEncoder.encode(fileName, "UTF-8");
            // encoding for Chinese
            zipFileName=java.net.URLEncoder.encode(zipFileName, "UTF-8");
            zipFileName = new String(zipFileName.getBytes("UTF-8"));

            ZipEntry entry = new ZipEntry(zipFileName);

            out.putNextEntry(entry);

            byte data[] = new byte[BUFFER];
            origin = new BufferedInputStream(fi, BUFFER);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
        } catch (IOException zip) {
            log.debug("Zip", zip.getCause());
        }

    }

    public static void main(String argv[]) {
        try {

            ZipFileHandle.createdZipFile("E:/costagency/files/","\\xml\\780\\华南师范大学石牌校区化学楼工程.xml");



//            String fileName = "/xml/780/华南师范大学石牌校区化学楼工程.xml";
//            int pos = fileName.lastIndexOf("/");
//            String zipFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//            // encoding for Chinese
//            zipFileName = new String(zipFileName.getBytes("UTF-8"), "gb2312");
//
//            ZipEntry entry = new ZipEntry(fileName.substring(pos + 1));
//
//            System.out.println(entry.getName());
        } catch (IOException zip) {
            log.debug("Zip", zip.getCause());
        }

    }

}
