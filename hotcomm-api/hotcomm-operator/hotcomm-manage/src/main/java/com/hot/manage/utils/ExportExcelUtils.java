package com.hot.manage.utils;

import java.awt.Color;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import com.hot.manage.entity.common.patrol.SignLogInfoVO;
import com.hot.manage.entity.common.patrol.SignLogPageInfoVO;

/**
 * Created by zouLu on 2017-12-14.
 */
public class ExportExcelUtils {

	public static void exportExcel(HttpServletResponse response, String fileName, SignLogInfoVO data) throws Exception {
		exportExcel(data, response.getOutputStream());
	}

	public static void exportExcel(SignLogInfoVO data, OutputStream out) throws Exception {

		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			String sheetName = data.getName();
			if (null == sheetName) {
				sheetName = "Sheet1";
			}
			XSSFSheet sheet = wb.createSheet(sheetName);
			writeExcel(wb, sheet, data);

			wb.write(out);
		} finally {
			// wb.close();
		}
	}

	private static void writeExcel(XSSFWorkbook wb, Sheet sheet, SignLogInfoVO data) {

		int rowIndex = 0;
		String [] headers = {"id","devnum","groupname","address","patName","addtime","signstate","stateDetail","stateName","picaddr","signID"};
		data.setTitles(Arrays.asList(headers));
		rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
		SignLogInfoVO test=new SignLogInfoVO();
		test=data;
		List<SignLogInfoVO> list=new ArrayList<>();
		List<List<Object>> rows=new ArrayList<>();
		List<Object> rowss=new ArrayList<>();
		List<SignLogPageInfoVO> s1=new ArrayList<>();
		SignLogPageInfoVO vo=new SignLogPageInfoVO();
//		vo.setAddress("testsetAddress");
//		vo.setAddtime("testsetAddtime");
//		vo.setDevnum("testsetDevnum");
//		vo.setGroupname("testsetGroupname");
//		vo.setId(1);
//		vo.setPatName("testsetPatName");
//		vo.setPicaddr("testsetPicaddr");
//		vo.setSignID(1);
//		vo.setSignstate(1);
//		vo.setStateDetail("testsetStateDetail");
//		vo.setStateName("testsetStateName");
		vo.setAddress("1");
		vo.setAddtime("1");
		vo.setDevnum("1");
		vo.setGroupname("1");
		vo.setId(1);
		vo.setPatName("1");
		vo.setPicaddr("1");
		vo.setSignID(1);
		vo.setSignstate(1);
		vo.setStateDetail("1");
		vo.setStateName("1");
		s1.add(vo);
		data.setInfo(s1);
		rowss.addAll((Collection<? extends Object>) data.getInfo());
		rows.add(rowss);
		List<List<SignLogPageInfoVO>> s2=new ArrayList<>();
		s2.add(s1);
		writeRowsToExcel(wb, sheet, s1, rowIndex);
		autoSizeColumns(sheet, data.getTitles().size() + 1);

	}

	@SuppressWarnings("deprecation")
	private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
		int rowIndex = 0;
		int colIndex = 0;

		Font titleFont = wb.createFont();
		titleFont.setFontName("simsun");
		// titleFont.setBold(true);
		// titleFont.setFontHeightInPoints((short) 14);
		titleFont.setColor(IndexedColors.BLACK.index);

		XSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));
		titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFont(titleFont);
		setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

		Row titleRow = sheet.createRow(rowIndex);
		// titleRow.setHeightInPoints(25);
		colIndex = 0;

		for (String field : titles) {
			Cell cell = titleRow.createCell(colIndex);
			cell.setCellValue(field);
			cell.setCellStyle(titleStyle);
			colIndex++;
		}

		rowIndex++;
		return rowIndex;
	}

	@SuppressWarnings("deprecation")
	private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<SignLogPageInfoVO> s1, int rowIndex) {
		int colIndex = 0;

		Font dataFont = wb.createFont();
		dataFont.setFontName("simsun");
		// dataFont.setFontHeightInPoints((short) 14);
		dataFont.setColor(IndexedColors.BLACK.index);

		XSSFCellStyle dataStyle = wb.createCellStyle();
		dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		dataStyle.setFont(dataFont);
		setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

//		for (List<SignLogPageInfoVO> rowData : s1) {
//			Row dataRow = sheet.createRow(rowIndex);
//			colIndex = 0;
//
//			for (SignLogPageInfoVO cellData : rowData) {
//				Cell cell = dataRow.createCell(colIndex);
//				if (cellData != null) {
//					cell.setCellValue(cellData.toString());
//				} else {
//					cell.setCellValue("");
//				}
//
//				cell.setCellStyle(dataStyle);
//				colIndex++;
//			}
//			rowIndex++;
//		}
		for (int i = 0; i < s1.size(); i++) {
			Row dataRow = sheet.createRow(rowIndex);
			colIndex = 0;
			for (int j = 0; j < 11; j++) {
				
			}
		}
		return rowIndex;
	}

	private static void autoSizeColumns(Sheet sheet, int columnNumber) {

		for (int i = 0; i < columnNumber; i++) {
			int orgWidth = sheet.getColumnWidth(i);
			sheet.autoSizeColumn(i, true);
			int newWidth = (int) (sheet.getColumnWidth(i) + 100);
			if (newWidth > orgWidth) {
				sheet.setColumnWidth(i, newWidth);
			} else {
				sheet.setColumnWidth(i, orgWidth);
			}
		}
	}

	private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
		style.setBorderTop(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
		style.setBorderBottom(border);
		style.setBorderColor(BorderSide.TOP, color);
		style.setBorderColor(BorderSide.LEFT, color);
		style.setBorderColor(BorderSide.RIGHT, color);
		style.setBorderColor(BorderSide.BOTTOM, color);
	}
}
