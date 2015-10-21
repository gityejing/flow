package com.maven.flow.core;

/**
 * 步骤自定义完成规则检查器
 * 
 * @author kinz
 * @version 1.0 2007-6-15
 * @since JDK1.5
 */

public interface ICustomizeProcessCompleteChecker extends IBaseLogic {

	/**
	 * 获取完成规则检查器的名称
	 */
	String getCheckerName();
}