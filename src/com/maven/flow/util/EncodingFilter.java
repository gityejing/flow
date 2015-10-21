/*
 * 创建日期 2005.10.04
 * 作者     kinz
 * 文件名   EncodingFilter.java
 * 版权     CopyRight (c) 2005
 * 功能说明 设置编码的filter
 */
package com.maven.flow.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

public class EncodingFilter implements Filter {

	//默认使用GB2312作为Request的编码
	private String encoding = "GB2312";

	public void init(FilterConfig filterconfig) {
		//获取配置中指定要使用的编码
		String tmp = filterconfig.getInitParameter("encoding");
		if (!StringUtils.isEmpty(tmp)) {
			this.encoding = tmp.trim();
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		try {
			if (servletrequest.getCharacterEncoding() == null
					|| !servletrequest.getCharacterEncoding().equals(
							this.encoding)) {
                            String url = ((javax.servlet.http.HttpServletRequest)servletrequest).getRequestURI();
                            String query=((javax.servlet.http.HttpServletRequest)servletrequest).getQueryString();
                            // xml not encode
                            // old oa system url will not encode
                            if (url!=null && url.indexOf("controller")<0
                                    && url.indexOf("ezoa")<0 ){
                                servletrequest.setCharacterEncoding(this.
                                        encoding);
                                if(query!=null){
                                    if(query.indexOf("method=xmlUpload&submission=true")!=-1){
                                        servletrequest.setCharacterEncoding("UTF-8");
                                    }
                                }
                            }else{
                               // System.out.println("url:" + url);
                            }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		filterchain.doFilter(servletrequest, servletresponse);
	}
}
