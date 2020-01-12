package sual;

import org.junit.Test;

import com.sual.plugin.bean.BeanWriter;
import com.sual.plugin.common.util.BeanContext;
import com.sual.plugin.common.util.CommonUtil;
import com.sual.plugin.xssf.excel.parse.XssfExcelParse;

public class CommonUtilTest {

	@Test
	public void test1() {
		System.out.println(CommonUtil.toCamelCase("user_id"));
		System.out.println(CommonUtil.toCamelCase("user-id"));
		System.out.println(CommonUtil.toCamelCase("User_id"));
		System.out.println(CommonUtil.toCamelCase("userid"));
	}
	
	@Test
	public void test2() {
		String path = XssfExcelParse.class.getResource("/").getPath();
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("java.class.path").substring(0,System.getProperty("java.class.path").indexOf(":"))+"/");
		System.out.println(path);
	}
	
	@Test
	public void test3() {
//		BeanContext context = new BeanContext();
//		
//		XssfExcelParse.parse(context);
//		BeanWriter.write(context);
//		
	}
	
	@Test
	public void test4() {
		String[] split = "Controller.InDto".split("[.]");
		
		System.out.println(String.format("package com.sual.api.%s.%s.dto", "user","controller")+"\r");
	}
}
