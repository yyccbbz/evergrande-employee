package com.baomidou.springwind.excel;


import com.baomidou.springwind.excel.config.ExcelDefinition;

import java.util.Map;

/**
 * Excel定义接口
 * @author lisuo
 *
 */
public interface ExcelDefinitionReader {
	/**
	 * 获取 ExcelDefinition注册信息
	 * @return
	 */
	Map<String, ExcelDefinition> getRegistry();
}
