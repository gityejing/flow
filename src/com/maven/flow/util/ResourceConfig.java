/**
 * ��������    2006-8-2
 * ����            �Ž���
 * �ļ���        ResourceConfig.java
 * ��Ȩ            CopyRight (c) 2006
 * ����˵��    �ṩ����ClassPath�������ļ�������ֻ�ṩ��ȡ���õĹ��ܣ����ṩд���õĹ��ܡ��ṩ������Ĳ�����ȡ����Ķ������£�
 * ulink.a=Group ulink, item a
 * ulink.b=Group ulink, item b
 * �������������У�ulink����һ����
 * ע�⣺���������У����еĲ������ƶ������ִ�Сд��
 */
package com.maven.flow.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * @author kinz ������
 */
public class ResourceConfig {

    private static final String DEFAULT_CONFIG_NAME = "ulinktek.ini";

    /**
     * �������е����ƶ�Ӧ��Config
     */
    private static HashMap configs = new HashMap();

    private String name = "";

    private Properties properties = new Properties();

    /**
     * ˽�еĹ��췽��������ʹ�ù��췽����ʼ������
     *
     */
    private ResourceConfig(String name) {
        this.name = name;
        this.reLoadConfig();
    }

    /**
     * �����������ƻ�ȡ�������ļ�����Ϊ��***.properties�����������Ϊ***
     *
     * @param name
     *            ��������
     * @return
     */
    public static ResourceConfig getConfig(String name) {
        if (name == null || name.trim().length() == 0)
            return (ResourceConfig) configs.get(DEFAULT_CONFIG_NAME);

        name = name.trim();
        if (configs.containsKey(name))
            return (ResourceConfig) configs.get(name);

        // ʵ����һ���µ�����
        ResourceConfig pc = new ResourceConfig(name);
        configs.put(name, pc);
        return pc;
    }

    /**
     * ���´������ļ���װ������
     *
     */
    public void reLoadConfig() {
        // ���ԭ�в�������
        this.properties.clear();

        // ������Ʋ����ڣ���������
        if (this.name == null || this.name.trim().length() == 0)
            return;

        // ������Դ�ļ�����
        String res = "";
        // ��������Ѿ���һ��properties��β
        if (name.toLowerCase().endsWith(".properties")) {
            res = name;
        } else if (name.indexOf(".") != -1) {
            // ���ָ���˺�׺��
            res = name;
        } else {
            res = name + ".properties";
        }

        // ��ȡ��Դ
        try {
            // ��ȡ��Դ��
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(res);

            // ����װ����Դ
            this.properties.load(in);
            // �ر���Դ��
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ�����������ã�������������ڣ�����Ĭ��ֵ
     *
     * @param key
     *            ��������
     * @param defaultValue
     *            Ĭ��ֵ
     */
    public int getIntConfig(String key, int defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        if (!this.properties.containsKey(key)) {
            return defaultValue;
        }
        String value = this.properties.getProperty(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * ��ȡ�ַ������ã�������������ڣ�����Ĭ��ֵ
     *
     * @param key
     *            ��������
     * @param defaultValue
     *            Ĭ��ֵ
     */
    public String getStringConfig(String key, String defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        if (!this.properties.containsKey(key)) {
            return defaultValue;
        }
        String value = this.properties.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            return defaultValue;
        }
        return value;
    }

    /**
     * ��ȡ��������ֵ��������������ڣ�����Ĭ��ֵ
     *
     * @param key
     *            ��������
     * @param defaultValue
     *            Ĭ��ֵ
     */
    public boolean getBoolConfig(String key, boolean defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        if (!this.properties.containsKey(key)) {
            return defaultValue;
        }
        String value = this.properties.getProperty(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        }
        // return Boolean.getBoolean(value);
        return Boolean.valueOf(value).booleanValue();
    }

    /**
     * ��ȡDouble����ֵ���������ֵ�����ڣ�����Ĭ��ֵ
     *
     * @param key
     *            ��������
     * @param defaultValue
     *            Ĭ��ֵ
     */
    public double getDoubleConfig(String key, double defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        if (!this.properties.containsKey(key)) {
            return defaultValue;
        }
        String value = this.properties.getProperty(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * ��ȡFloat����ֵ��������������ڣ�����Ĭ��ֵ
     *
     * @param key
     *            ��������
     * @param defaultValue
     *            Ĭ��ֵ
     */
    public float getFloatConfig(String key, float defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        if (!this.properties.containsKey(key)) {
            return defaultValue;
        }
        String value = this.properties.getProperty(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * ��ȡһ����Ĳ���ֵ��������ķ�ʽ����
     *
     * @param groupName
     * @return
     */
    public String[] getStringGroupConfig(String groupName) {
        Enumeration keys = this.properties.keys();
        List tmp = new ArrayList();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(groupName + ".")) {
                tmp.add(this.properties.getProperty(key));
            }
        }

        return (String[]) tmp.toArray(new String[tmp.size()]);

    }

    /**
     * ��ȡָ�����һ������
     * @param groupName
     * @return
     */
    public ResourceConfig getGroupConfig(String groupName){
        ResourceConfig rsc = new ResourceConfig("");
        Enumeration keys = this.properties.keys();
        while(keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            if(key.startsWith(groupName+".")){
                String newKey = key.substring((groupName+".").length());
                rsc.properties.setProperty(newKey,this.properties.getProperty(key));
            }
        }
        return rsc;
    }

    /**
     * ��ȡ���еĲ�������
     * @return
     */
    public Iterator getParamKeys(){
        return this.properties.keySet().iterator();
    }
}