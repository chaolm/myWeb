/**
 * 文件名: ExcelUtil.java<BR>
 * 包名: com.iamly.test<BR>
 * 描述: TODO(用一句话描述该文件做什么)<BR>
 * 作者: Liyou<BR>
 * 版本信息:<BR>
 * 日期: 2016年6月29日 上午9:32:38<BR>
 * 版本: V1.0<BR>
 *
 * copyright 银承库 2016 版权所有
 */
package com.dripop.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 类名: ExcelUtil<BR>
 * 描述: TODO(这里用一句话描述这个类的作用)<BR>
 * 作者: Liyou<BR>
 * 版本信息:<BR>
 * 日期: 2016年6月29日 上午9:32:38<BR>
 * 版本: V1.0<BR>
 * modify 2016年6月29日 上午9:32:38<BR>
 *
 * copyright 银承库.
 */
public class ExcelUtil {

	public static List<Object[]> readExcel(InputStream is, int rowNum) throws InvalidFormatException, IOException {
		List<Object[]> objList = new ArrayList<Object[]>();
		Object[] objs = null;
		
    	Workbook workbook = WorkbookFactory.create(is);
//    	Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = null;
        Integer cellNum = rowNum;
        for (int i = 0; i <= sheet.getPhysicalNumberOfRows(); i++)
        {
        	row = sheet.getRow(i);
        	if(row == null) {
        		continue;
        	}
        	if(row.getPhysicalNumberOfCells() < cellNum) {
                cellNum = row.getPhysicalNumberOfCells();
            }
            objs = new Object[rowNum];
            for (int j = 0; j < cellNum; j++) {
            	objs[j] = getCellValue(row.getCell(j));
			}
            objList.add(objs);
        }
        return objList;
	}
	
	public static HSSFWorkbook buildExcel(String[] header, List<Object[]> objList) {
		HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        HSSFCellStyle style = wb.createCellStyle();
        
        HSSFFont font = wb.createFont();  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        HSSFCellStyle rowstyle = wb.createCellStyle();
        rowstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
        Object[] objs = null;
        HSSFCell cell = null;
        HSSFRow row = sheet.createRow((int) 0);
        for (int i = 0; i < header.length; i++) {    
            cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 32 * 150);
        }   
        
        for (int i = 0; i < objList.size(); i++) {    
            row = sheet.createRow(i + 1);
            objs = objList.get(i);
            for (int j = 0; j < objs.length; j++) {
                if(objs[j] == null) {
                    objs[j] = "";
                }
            	row.createCell(j).setCellValue(objs[j].toString());
            	row.getCell(j).setCellStyle(rowstyle);
			}
        }
        
        return wb;
	}
	
	public static String getCellValue(Cell cell) {
		String value = null;
        if(cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {  
            // 返回布尔类型的值  
        	value = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
            // 返回数值类型的值  
        	value = new DecimalFormat("#").format(cell.getNumericCellValue());
        } else {  
            // 返回字符串类型的值  
            value = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
        }
//        return StringEscapeUtils.escapeHtml4(value).trim();
        return value.trim();
    }
	
}
