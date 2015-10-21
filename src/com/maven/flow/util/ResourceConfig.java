/**
 * 创建日期    2006-8-2
 * 作者            张金凌
 * 文件名        ResourceConfig.java
 * 版权            CopyRight (c) 2006
 * 功能说明    提供基于ClassPath的配置文件操作，只提供读取配置的功能，不提供写配置的功能。提供基于组的参数获取。组的定义如下：
 * ulink.a=Group ulink, item a
 * ulink.b=Group ulink, item b
 * 上面两个参数中，ulink就是一个组
 * 注意：参数配置中，所有的参数名称都是区分大小写的
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
 * @author kinz 创建类
 */
public class ResourceConfig {

    private static final String DEFAULT_CONFIG_NAME = "ulinktek.ini";

    /**
     * 缓存所有的名称对应的Config
     */
    private static HashMap configs = new HashMap();

    private String name = "";

    private Properties properties = new Properties();

    /**
     * 私有的构造方法，不能使用构造方法初始化该类
     *
     */
    private ResourceConfig(String name) {
        this.name = name;
        this.reLoadConfig();
    }

    /**
     * 根据配置名称获取，配置文件名称为：***.properties，传入的名称为***
     *
     * @param name
     *            配置名称
     * @return
     */
    public static ResourceConfig getConfig(String name) {
        if (name == null || name.trim().length() == 0)
            return (ResourceConfig) configs.get(DEFAULT_CONFIG_NAME);

        name = name.trim();
        if (configs.containsKey(name))
            return (ResourceConfig) configs.get(name);

        // 实例化一个新的配置
        ResourceConfig pc = new ResourceConfig(name);
        configs.put(name, pc);
        return pc;
    }

    /**
     * 重新从配置文件中装载配置
     *
     */
    public void reLoadConfig() {
        // 清空原有参数配置
        this.properties.clear();

        // 如果名称不存在，则不做处理
        if (this.name == null || this.name.trim().length() == 0)
            return;

        // 配置资源文件名称
        String res = "";
        // 如果名称已经是一以properties结尾
        if (name.toLowerCase().endsWith(".properties")) {
            res = name;
        } else if (name.indexOf(".") != -1) {
            // 如果指定了后缀名
            res = name;
        } else {
            res = name + ".properties";
        }

        // 获取资源
        try {
            // 获取资源流
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(res);

            // 重新装载资源
            this.properties.load(in);
            // 关闭资源流
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取整数参数配置，如果参数不存在，返回默认值
     *
     * @param key
     *            参数名称
     * @param defaultValue
     *            默认值
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
     * 获取字符串配置，如果参数不存在，返回默认值
     *
     * @param key
     *            参数名称
     * @param defaultValue
     *            默认值
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
     * 获取布尔参数值，如果参数不存在，返回默认值
     *
     * @param key
     *            参数名称
     * @param defaultValue
     *            默认值
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
     * 获取Double参数值，如果参数值不存在，返回默认值
     *
     * @param key
     *            参数名称
     * @param defaultValue
     *            默认值
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
     * 获取Float参数值，如果参数不存在，返回默认值
     *
     * @param key
     *            参数名称
     * @param defaultValue
     *            默认值
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
     * 获取一个组的参数值，以数组的方式返回
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
     * 获取指定组的一个配置
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
     * 获取所有的参数名称
     * @return
     */
    public Iterator getParamKeys(){
        return this.properties.keySet().iterator();
    }
}