/**
 * @(#)LoginCheckFilter.java 2007-06-22
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 登录验证的Filter
 * 
 * @author Kinz
 * @version 1.0 2007-06-22
 * @since JDK1.5
 */

public class LoginCheckFilter implements Filter {

	// 登录页面
	private String loginPage = "/costagency/login.jsp";

	public void init(FilterConfig filterconfig) {
		// 获取配置中指定要使用的编码
		String tmp = filterconfig.getInitParameter("loginPage");
		if (!StringUtils.isEmpty(tmp)) {
			this.loginPage = tmp.trim();
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI();
		if (uri.endsWith(loginPage)) {
			chain.doFilter(request, response);
		} else if (uri.endsWith("costagency/employeeInfo.do")
				&& "login".equals(request.getParameter("method"))) {
			chain.doFilter(request, response);
		} else if (((HttpServletRequest) request).getSession().getAttribute(
				Constant.SESSION_EMPLOYEEINFO_KEY) != null) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher(loginPage).forward(request, response);
		}
	}

}