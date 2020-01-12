package com.sual.plugin.bean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.sual.plugin.bean.field.FieldDefinition;
import com.sual.plugin.common.util.BeanContext;

public class BeanWriter {

	private static final String PACKAGE = "package com.sual.api.%s.%s.dto;";

	private static List<String> list = new ArrayList<String>();

	public void write(BeanContext context) {

		List<BeanDefinition> beanDefinitions = context.getBeanDefinitions();

		beanDefinitions.forEach((beanDefinition) -> {

			String packagePath = System.getProperty("user.dir") + "/bean/com/sual/api/user/controller/dto/";

			if (Files.notExists(Paths.get(packagePath))) {
				try {
					Files.createDirectories(Paths.get(packagePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			transfer(beanDefinition);
			try {
				Files.write(Paths.get(packagePath + "/UserGetInDto.java"), list, StandardCharsets.UTF_8,
						StandardOpenOption.CREATE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.clear();
		});
	}

	private void transfer(BeanDefinition beanDefinition) {

		String[] dtoType = beanDefinition.getDtoType().split("[.]");

		if ("Controller".equals(dtoType[0])) {
			transgerControllerDto(beanDefinition, dtoType[1]);
		} else {
			transgerServiceDto(beanDefinition, dtoType[1]);
		}

	}

	private void transgerControllerDto(BeanDefinition beanDefinition, String string) {

		String apiName = beanDefinition.getApiName();
		String httpMethod = beanDefinition.getHttpMethod().toLowerCase();
		List<FieldDefinition> fields = beanDefinition.getFields();

		list.add(String.format(PACKAGE, apiName, "controller") + "\r");
		
		list.add("\r");

		list.add("import lombok.Setter;");
		list.add("import lombok.Getter;\r");

		list.add("@Getter@Setter");
		list.add("public class " + apiName.substring(0, 1).toUpperCase() + apiName.substring(1)
				+ httpMethod.substring(0, 1).toUpperCase() + httpMethod.substring(1) + string + "{\r\r");

		for (FieldDefinition field : fields) {
			field.getAnotations().forEach((anotation) -> {
				list.add(anotation);
			});
			list.add("	private " + field.getType() + " " + field.getCamelCaseFieldName() + ";\r\r");
		}
		list.add("}");
	}

	private void transgerServiceDto(BeanDefinition beanDefinition, String string) {
		// TODO Auto-generated method stub

	}

}
