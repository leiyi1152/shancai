package com.icloud.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcel {
  
	
	/**
	 * 
	 * @param file 读取的文件
	 * @param SheetAt 读取excel的第几张表格
	 * @param beginRow 开始的行
	 * @param firstCellNum 开始的列
	 * @param lastCellNum 结束的列
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readExcel(File file,int SheetAt,int beginRow, int firstCellNum,int lastCellNum )
			throws IOException {
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(new FileInputStream(file),SheetAt, beginRow,firstCellNum,lastCellNum);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(new FileInputStream(file),SheetAt, beginRow, firstCellNum,lastCellNum);
		} else {
			throw new IOException("不支持的文件类型");
		}
	}
	

	
	private static List<List<Object>> read2003Excel(FileInputStream in,int SheetAt, int n, int firstCellNum,int lastCellNum)
			throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(in);
		HSSFSheet sheet = hwb.getSheetAt(SheetAt);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = n; i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = firstCellNum; j <= lastCellNum; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					linked.add("");
					continue;
				}
				value = getValue(cell);
			
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}
	
	
	public static Object getValue(HSSFCell cell){
			Object value = null;
			DecimalFormat df = new DecimalFormat("0");// 格式化 number
			// String
			// 字符
			SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
			DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
			if(cell!=null)
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
			case XSSFCell.CELL_TYPE_NUMERIC:
			if ("@".equals(cell.getCellStyle().getDataFormatString())) {
				value = df.format(cell.getNumericCellValue());
			} else if ("General".equals(cell.getCellStyle()
			.getDataFormatString())) {
				value = nf.format(cell.getNumericCellValue());
			} else {
				value = sdf.format(HSSFDateUtil.getJavaDate(cell
			.getNumericCellValue()));
			}
			break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
			case XSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
			default:
			value = cell.toString();
			}
			return value;
	}

	/**
	 * 
	 * @Title: read2007Excel
	 * @Description: 读取Office 2007 excel
	 * @param @param file
	 * @param @param n 从第n行开始读取
	 * @param @param m 非空列号(此列为空则读取下一行)
	 * @param @throws IOException 设定文件
	 * @return List<List<Object>> 返回类型
	 * @author yr_xiezhy
	 * @date 2015-3-11 下午4:14:03
	 * @throws
	 */
	private static List<List<Object>> read2007Excel(FileInputStream in,int SheetAt, int n,int firstCellNum,int lastCellNum)
			throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(in);
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(SheetAt);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i =  n; i <= sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = firstCellNum; j <= lastCellNum; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					linked.add("");
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					// System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}
	
	
}
