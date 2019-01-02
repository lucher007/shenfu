package com.sykj.shenfu.common;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcel {

	public static String resultSetToExcel(List<HashMap<String,Object>> objectList, String[] columntitle, String reportName, String fileName, HttpServletResponse response, List<HashMap<String, String>> conditionlist) throws Exception {
		XSSFWorkbook workbook = reportTypeHandler(objectList, columntitle, reportName, fileName, conditionlist);
		return workbook != null ? downloadExcel(workbook, fileName, response) : "fail";
	}

	// 需要在此方法中根据ReportNmae把传入的OBJECT转化相应的PO对象
	// 比如在CONTROLLER中传入的reportName为StoreReport_Storeid15,以"_"分割后的数组[0]就是StoreReport
	// 则在本方法中OBJECT应该转化为StoreReport对应的BusinessReport对象后再进行填表处理
	// 当需要导出不同的Report时需在本方法中增加IF条件来处理
	public static XSSFWorkbook reportTypeHandler(List<HashMap<String,Object>> objectList, String[] columntitle, String reportName, String fileName, List<HashMap<String, String>> conditionlist) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fileName);
		// EXCEL 主SHEET 名称
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = null;
		if("materialinout".equals(reportName) 
		|| "productinout".equals(reportName)
		|| "materialinoutstat".equals(reportName)
		|| "productinoutstat".equals(reportName)
		){
			int nColumn = columntitle.length;
			int rowIndex = 0;
			//填写条件
			for(HashMap<String, String> conditionmap : conditionlist){
				cell = row.createCell((int)(0));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("title"));
				cell = row.createCell((int)(1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("content"));
				rowIndex++;
				row = sheet.createRow((int) rowIndex);
			}
			// 填写表头
			for (int i = 0; i < nColumn; i++) {
				cell = row.createCell((int)(i));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(columntitle[i]);
			}
			rowIndex++;
			// 向EXCEL中逐行填表
			for (HashMap<String,Object> objectMp : objectList) {
				row = sheet.createRow((int) rowIndex);
				for (int j = 0; j < nColumn; j++) {
					cell = row.createCell(j);
					// 如果可以转化为数字则填入数字，如果不行则填入String方便Excel后续处理数据
					try {
						Double number = Double.parseDouble(String.valueOf(objectMp.get(columntitle[j])));
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(number);
					} catch (Exception e) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(String.valueOf(objectMp.get(columntitle[j])));
					}
				}
				rowIndex++;
			}
		}else if("salegaininfostat".equals(reportName)){
			int nColumn = columntitle.length;
			int rowIndex = 0;
			//填写条件
			for(HashMap<String, String> conditionmap : conditionlist){
				cell = row.createCell((int)(0));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("title"));
				cell = row.createCell((int)(1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("content"));
				rowIndex++;
				row = sheet.createRow((int) rowIndex);
			}
			// 填写表头
			for (int i = 0; i < nColumn; i++) {
				cell = row.createCell((int)(i));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(columntitle[i]);
			}
			rowIndex++;
			// 向EXCEL中逐行填表
			for (HashMap<String,Object> objectMp : objectList) {
				row = sheet.createRow((int) rowIndex);
				for (int j = 0; j < nColumn; j++) {
					cell = row.createCell(j);
					if(j==0 || j==2){//数字字符串
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(String.valueOf(objectMp.get(columntitle[j])));
					}else{
						// 如果可以转化为数字则填入数字，如果不行则填入String方便Excel后续处理数据
						try {
							Double number = Double.parseDouble(String.valueOf(objectMp.get(columntitle[j])));
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellValue(number);
						} catch (Exception e) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(String.valueOf(objectMp.get(columntitle[j])));
						}
					}
				}
				rowIndex++;
			}
		}else if("USERDETAILSTAT".equals(reportName)){
			//前端用户明细统计导出excel特殊处理
			int nColumn = columntitle.length;
			int rowIndex = 0;
			int conditionlength;
			//填写条件
			for(HashMap<String, String> conditionmap : conditionlist){
				row = sheet.createRow((int) rowIndex);
				cell = row.createCell((int)(0));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("title"));
				cell = row.createCell((int)(1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("content"));
				rowIndex++;
			}
			conditionlength = rowIndex;
			// 向EXCEL中逐行填表
			for (HashMap<String,Object> objectMp : objectList) {
				row = sheet.createRow((int) rowIndex);
				for (int j = 0; j < nColumn; j++) {
					cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(String.valueOf(objectMp.get(columntitle[j])));
				}
				rowIndex++;
			}
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(0 + conditionlength,0 + conditionlength,0,1));
			sheet.addMergedRegion(new CellRangeAddress(1 + conditionlength,3 + conditionlength,0,0));
			sheet.addMergedRegion(new CellRangeAddress(4 + conditionlength,4 + conditionlength,0,1));
			sheet.addMergedRegion(new CellRangeAddress(5 + conditionlength,8 + conditionlength,0,0));
			sheet.addMergedRegion(new CellRangeAddress(9 + conditionlength,9 + conditionlength,0,1));
			sheet.addMergedRegion(new CellRangeAddress(10 + conditionlength,rowIndex,0,0));
		}else if("Invoiceprint".equals(reportName)){//发票打印
			int nColumn = columntitle.length;
			int rowIndex = 0;
			//填写条件
			for(HashMap<String, String> conditionmap : conditionlist){
				cell = row.createCell((int)(0));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("title"));
				cell = row.createCell((int)(1));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(conditionmap.get("content"));
				rowIndex++;
				row = sheet.createRow((int) rowIndex);
			}
			// 填写表头
			for (int i = 0; i < nColumn; i++) {
				cell = row.createCell((int)(i));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(columntitle[i]);
			}
			rowIndex++;
			// 向EXCEL中逐行填表
			for (HashMap<String,Object> objectMp : objectList) {
				row = sheet.createRow((int) rowIndex);
				for (int j = 0; j < nColumn; j++) {
					cell = row.createCell(j);
					if(j==1){//金额列，需要导出数值
						Double number = Double.parseDouble(String.valueOf(objectMp.get(columntitle[j])));
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						XSSFCellStyle cellStyle = workbook.createCellStyle();
						XSSFDataFormat format = workbook.createDataFormat();
						cellStyle.setDataFormat(format.getFormat("0.00"));//设置单元类型
						cell.setCellStyle(cellStyle);
						cell.setCellValue(number);
					}else{
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(String.valueOf(objectMp.get(columntitle[j])));
					}
					// 如果可以转化为数字则填入数字，如果不行则填入String方便Excel后续处理数据
					/*try {
						Double number = Double.parseDouble(businessReport.getExcelMap().get(columntitle[j]));
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(number);
					} catch (Exception e) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(businessReport.getExcelMap().get(columntitle[j]));
					}*/
				}
				rowIndex++;
			}
		}
		return workbook;// 返回数据填写完毕的workbook
	}
	
	public static String downloadExcel(XSSFWorkbook workbook, String reportName, HttpServletResponse response) throws Exception {
		String filename = reportName + ".xlsx";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
		return "success";
	}

}
