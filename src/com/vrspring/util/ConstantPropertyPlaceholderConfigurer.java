package com.vrspring.util;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ConstantPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	/**
	 * 属性前缀，可以使用表达式${}。如果使用表达式，表示从属性文件中的字段读取。 如果为空，表示不使用前缀。
	 */
	private String prefix;

	/**
	 * 需要注入的常量类名数组
	 */
	private String[] configureClasses;

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);

		if (configureClasses == null)
			return;

		String prefixValue = null;
		if (prefix != null && !prefix.isEmpty()) {
			// 如果前缀是表达式，需要从属性中读取
			Pattern p = Pattern.compile("\\$\\{(.*)\\}");
			Matcher matcher = p.matcher(prefix);
			prefixValue = prefix;
			if (matcher.find()) {
				String prefixKey = matcher.group(1);
				prefixValue = props.getProperty(prefixKey);
			}
		}

		// 遍历多个常量类
		for (int i = 0; i < configureClasses.length; i++) {
			Class<?> c = null;
			try {
				c = Class.forName(configureClasses[i]);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			}

			Field[] fields = c.getFields();

			// 遍历属性列表，注入到常量字段中
			for (Enumeration<?> k = props.propertyNames(); k.hasMoreElements();) {
				String key = (String) k.nextElement();

				// 遍历常量字段数组，找到与属性对应的常量字段
				for (int j = 0; fields != null && j < fields.length; j++) {
					// 取出字段名称，如果有前缀，需要加上前缀
					String keyStr = fields[j].getName();
					if (prefixValue != null && !prefixValue.isEmpty())
						keyStr = prefixValue + "." + fields[j].getName();

					// 判断常量字段是否有属性匹配，不区分大小写。
					if (keyStr.equalsIgnoreCase(key)) {
						// 从属性中取出字段值，并存到字段中
						String value = props.getProperty(key);
						if (value != null) {
							value = value.trim();
							// fields[j].setAccessible(true);
							try {
								if (Integer.TYPE.equals(fields[j].getType())) {
									fields[j].setInt(null,
											Integer.parseInt(value));
								} else if (Long.TYPE
										.equals(fields[j].getType())) {
									fields[j].setLong(null,
											Long.parseLong(value));
								} else if (Short.TYPE.equals(fields[j]
										.getType())) {
									fields[j].setShort(null,
											Short.parseShort(value));
								} else if (Double.TYPE.equals(fields[j]
										.getType())) {
									fields[j].setDouble(null,
											Double.parseDouble(value));
								} else if (Float.TYPE.equals(fields[j]
										.getType())) {
									fields[j].setFloat(null,
											Float.parseFloat(value));
								} else if (Boolean.TYPE.equals(fields[j]
										.getType())) {
									fields[j].setBoolean(null,
											Boolean.parseBoolean(value));
								} else {
									fields[j].set(null, value);
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}

						}
						break;
					}
				}
			}
		}

	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String[] getConfigureClasses() {
		return configureClasses;
	}

	public void setConfigureClasses(String[] configureClasses) {
		this.configureClasses = (configureClasses != null) ? configureClasses
				.clone() : null;
	}

	public void setConfigureClass(String configureClass) {
		this.configureClasses = new String[] { configureClass };
	}
}
