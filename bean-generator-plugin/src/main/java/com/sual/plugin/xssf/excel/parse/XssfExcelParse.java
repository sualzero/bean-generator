package com.sual.plugin.xssf.excel.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.plexus.util.StringUtils;

import com.sual.plugin.bean.BeanDefinition;
import com.sual.plugin.bean.field.FieldDefinition;
import com.sual.plugin.bean.field.type.CommonType;
import com.sual.plugin.common.util.BeanContext;
import com.sual.plugin.common.util.CommonUtil;

public class XssfExcelParse {

	private static final String DEFAULT_CONFIG_FILE = "generator.properties";

	private String default_data_sheet_path;
	private String base_path;
	private String class_path;

	private Properties properties = new Properties();

	private Log log;

	private Map<String, FieldDefinition> fieldMap = new HashMap<String, FieldDefinition>();
	private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
	private void init() {
		base_path = System.getProperty("user.dir");
		default_data_sheet_path = base_path + ("/bean/sheet/BeanDefinition.xlsx");
//		class_path = this.getClass().getResource("/").getPath();
//		class_path = 
		log = new SystemStreamLog();
	}

	public void parse(BeanContext context) {

		init();
		
		loadProperties();

		if (Files.notExists(Paths.get(default_data_sheet_path))) {
			log.error("データ定義シートがみつからない！");
		}

		FileInputStream is = null;
		XSSFWorkbook book = null;

		try {
			is = new FileInputStream(default_data_sheet_path);
			book = new XSSFWorkbook(is);

			XSSFSheet sheet = book.getSheetAt(0);

			loadFieldDefinition(sheet);

			loadBeanDefinition(sheet);

			context.setBeanDefinitions(beanDefinitions);
		} catch (IOException e) {
			log.error("データ定義シートがみつからない！");
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	private void loadProperties() {
		try {
//			InputStream in = new FileInputStream(class_path + DEFAULT_CONFIG_FILE);
//			properties.load(in);
		} catch (Exception e) {
			log.error("properties初期化失敗しました！", e);
		}
	}

	private void loadFieldDefinition(XSSFSheet sheet) {

		int rowCount;

		for (Row row : sheet) {

			rowCount = row.getRowNum();
			FieldDefinition fieldDefinition = new FieldDefinition();
			List<String> anotations = new ArrayList<String>();

			if (rowCount < 5) {
				continue;
			}
			String fieldName = getValue(row.getCell(2));
			if (StringUtils.isEmpty(fieldName)) {
				break;
			}

			String camelCaseFieldName = CommonUtil.toCamelCase(fieldName);
			String comment = getValue(row.getCell(1));
			String type = getValue(row.getCell(3));

			String required = getValue(row.getCell(6));
//			String digits = row.getCell(7).getStringCellValue();
//			String max = row.getCell(8).getStringCellValue();
//			String min = row.getCell(9).getStringCellValue();
//			String negative = row.getCell(10).getStringCellValue();
//			String negativeOrZero = row.getCell(11).getStringCellValue();
//			String positive = row.getCell(12).getStringCellValue();
//			String positiveOrZero = row.getCell(13).getStringCellValue();
//			String size = row.getCell(14).getStringCellValue();

			fieldDefinition.setFieldName(fieldName);
			fieldDefinition.setCamelCaseFieldName(camelCaseFieldName);
			fieldDefinition.setComment(comment);

			if ("String".equals(type)) {
				fieldDefinition.setType(CommonType.STRING);
			} else if ("BigDecimal".equals(type)) {
				fieldDefinition.setType(CommonType.BIGDECIMAL);
			} else if ("Date_yyyymmdd".equals(type)) {
				fieldDefinition.setType(CommonType.DATE_YYYYMMDD);
			} else if ("Date_yyyymmddhhmmss".equals(type)) {
				fieldDefinition.setType(CommonType.DATE_YYYYMMDDHHMMSS);
			} else if ("List".equals(type)) {
				String t = getValue(row.getCell(4));
				fieldDefinition.setType("java.util.List<" + t + ">");
			} else {
				fieldDefinition.setType(type);
			}

			if ("○".equals(required)) {
				anotations.add(properties.getProperty("required"));
			}
			fieldDefinition.setAnotations(anotations);

			if (fieldMap.containsKey(fieldName)) {
				log.error(fieldName + "は重複しています！");
				throw new RuntimeException();
			}

			fieldMap.put(fieldName, fieldDefinition);
		}
	}

	private void loadBeanDefinition(XSSFSheet sheet) {
		for (int i = 15; i <= sheet.getRow(0).getLastCellNum() - 1; i++) {
			BeanDefinition beanDefinition = new BeanDefinition();
			beanDefinition.setFields(new ArrayList<FieldDefinition>());
			for (int j = 0; j < sheet.getLastRowNum(); j++) {
				if (j == 0) {
					String apiName = getValue(sheet.getRow(j).getCell(i));
					beanDefinition.setApiName(apiName);
				} else if (j == 1) {
					String httpMethod = getValue(sheet.getRow(j).getCell(i));
					beanDefinition.setHttpMethod(httpMethod);
				} else if (j == 2) {
					String dtoType = getValue(sheet.getRow(j).getCell(i));
					beanDefinition.setDtoType(dtoType);
				} else {
					String containField = getValue(sheet.getRow(j).getCell(i));
					if ("○".equals(containField)) {
						String fieldName = getValue(sheet.getRow(j).getCell(2));
						beanDefinition.getFields().add(fieldMap.get(fieldName));
					}
				}
			}
			beanDefinitions.add(beanDefinition);
		}
	}

	private String getValue(Cell cell) {

		if (cell == null) {
			return "";
		} else {
			if (cell.getCellType().equals(CellType.BOOLEAN)) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (CellType.NUMERIC.equals(cell.getCellType())) {
				return String.valueOf(cell.getNumericCellValue());
			} else if (CellType.FORMULA.equals(cell.getCellType())) {
				return cell.getCellFormula();
			} else {
				return cell.getStringCellValue();
			}
		}
	}
}
