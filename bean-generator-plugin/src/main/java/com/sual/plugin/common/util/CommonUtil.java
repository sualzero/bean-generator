package com.sual.plugin.common.util;

public class CommonUtil {

	public static String toCamelCase(String target) {

		String dest = null;

		if (target.contains("-")) {

			String str[] = target.split("-");

			for (int i = 0; i < str.length; i++) {

				if (i == 0) {
					dest = str[i].substring(0, 1).toLowerCase() + str[i].substring(1);
				} else {
					dest = dest + str[i].substring(0, 1).toUpperCase() + str[i].substring(1);
				}
			}
			return dest;
		} else if (target.contains("_")) {
			String str[] = target.split("_");

			for (int i = 0; i < str.length; i++) {

				if (i == 0) {
					dest = str[i].substring(0, 1).toLowerCase() + str[i].substring(1);
				} else {
					dest = dest + str[i].substring(0, 1).toUpperCase() + str[i].substring(1);
				}
			}
			return dest;
		} else {
			return target.substring(0, 1).toLowerCase() + target.substring(1);
		}
	}
}
