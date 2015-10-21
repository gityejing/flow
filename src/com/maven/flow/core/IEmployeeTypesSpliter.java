package com.maven.flow.core;

import java.util.List;
import java.util.Map;

import com.maven.flow.editor.bean.TaskBase;

public interface IEmployeeTypesSpliter {

	String getSpliterName();

	// public List splitEmployees(Process process,TaskBase task) throws
	// Exception;
	public List splitEmployees(Process process, TaskBase task, Map map)
			throws Exception;

	String getSpliterKey();

}