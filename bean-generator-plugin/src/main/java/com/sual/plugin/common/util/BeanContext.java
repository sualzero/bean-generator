package com.sual.plugin.common.util;

import java.util.List;

import org.apache.maven.plugin.logging.Log;

import com.sual.plugin.bean.BeanDefinition;

public class BeanContext {

	private List<BeanDefinition> beanDefinitions;
	
	private Log log;

	public List<BeanDefinition> getBeanDefinitions() {
		return beanDefinitions;
	}

	public void setBeanDefinitions(List<BeanDefinition> beanDefinitions) {
		this.beanDefinitions = beanDefinitions;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}
	
	
}
