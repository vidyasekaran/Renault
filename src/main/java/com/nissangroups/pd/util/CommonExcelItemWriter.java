/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-CommonExcelItemWriter
 * Module          :@Module
 * Process Outline :@Process_Outline	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000027.util.B000027Constants;
import com.nissangroups.pd.dao.B000001ReportDao;

/**
 * The Class CommonExcelItemWriter.
 *
 * @author z002548
 * @param <T>
 *            the generic type
 */
public class CommonExcelItemWriter {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(CommonExcelItemWriter.class);

	/** Variable list. */
	static List<List<String>> list = new ArrayList<List<String>>();

	/** Variable file path. */
	private String filePath;

	/** Variable headers. */
	private String[] headers;

	/** Variable second row headers. */
	private String[] headersScndRw;

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * Sets the headers.
	 *
	 * @param headers
	 *            the new headers
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	/**
	 * Gets the headersScndRw
	 *
	 * @return the headersScndRw
	 */

	public String[] getHeadersScndRw() {
		return headersScndRw;
	}

	/**
	 * Sets the headersScndRw
	 *
	 * @param headersScndRw
	 *            the headersScndRw to set
	 */

	public void setHeadersScndRw(String[] headersScndRw) {
		this.headersScndRw = headersScndRw;
	}

	/**
	 * Write.
	 *
	 * @param items
	 *            the items
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void write(List<B000001ReportDao> items) throws IOException {

		FileOutputStream outputStream = new FileOutputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Error Report");
		sheet.createFreezePane(0, 1, 0, 1);
		sheet.setDefaultColumnWidth(20);
		addSheetConfig(workbook, sheet);
		HSSFRow rowheader = sheet.createRow(0);
		int count = 0;
		for (String excelheader : headers) {
			HSSFCell cell = rowheader.createCell(count++);
			cell.setCellValue(excelheader);
		}

		report(items, sheet);

		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * Report.
	 *
	 * @param items
	 *            the items
	 * @param sheet
	 *            the sheet
	 */
	private void report(List<B000001ReportDao> items, HSSFSheet sheet) {
		int rowCount = 1;
		for (B000001ReportDao rowCell : items) {
			int cellCount = 0;
			HSSFRow row = sheet.createRow(rowCount++);
			createNewCell(row, rowCell.getPorCode(), cellCount++);
			createNewCell(row, rowCell.getProductionFamilyCode(), cellCount++);
			createNewCell(row, rowCell.getProductionStageCode(), cellCount++);
			createNewCell(row, rowCell.getBuyerCode(), cellCount++);
			createNewCell(row, rowCell.getEndItemModelCode(), cellCount++);
			createNewCell(row, rowCell.getColorCode(), cellCount++);
			createNewCell(row, rowCell.getAdditionSpecCode(), cellCount++);
			createNewCell(row, rowCell.getSpecDestinationCode(), cellCount++);
			createNewCell(row, rowCell.getAdoptMonth(), cellCount++);
			createNewCell(row, rowCell.getAbolistMonth(), cellCount++);
			createNewCell(row, rowCell.getComments(), cellCount++);

		}
	}

	/**
	 * Adds the sheet config.
	 *
	 * @param workbook
	 *            the workbook
	 * @param sheet
	 *            the sheet
	 */
	private void addSheetConfig(HSSFWorkbook workbook, HSSFSheet sheet) {
		workbook = sheet.getWorkbook();
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeight((short) 30);
		font.setFontName("Arial");

		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(16);

	}

	/**
	 * Creates the new cell.
	 *
	 * @param row
	 *            the row
	 * @param value
	 *            the value
	 * @param cellCount
	 *            the cell count
	 */
	public void createNewCell(HSSFRow row, String value, int cellCount) {
		HSSFCell cell = row.createCell(cellCount);
		cell.setCellValue(value);

	}

	/**
	 * Write.
	 *
	 * @param items
	 *            the items
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void createReport(List<Object[]> items,
			Map<String, String> formatMap, String sheetName) throws IOException {

		FileOutputStream outputStream = new FileOutputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		// HSSFSheet sheet = workbook.createSheet("Error Report");
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.createFreezePane(0, 1, 0, 1);
		sheet.setDefaultColumnWidth(20);
		addSheetConfig(workbook, sheet);
		HSSFRow rowheader = sheet.createRow(0);
		int count = 0;
		for (String excelheader : headers) {
			HSSFCell cell = rowheader.createCell(count++);
			cell.setCellValue(excelheader);
		}

		writeReport(items, sheet, formatMap);

		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * Write.
	 *
	 * @param items
	 *            the items
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void createTwoHdrReport(int aryLen, List<Object[]> items,
			Map<String, String> formatMap, String sheetName) throws IOException {

		FileOutputStream outputStream = new FileOutputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook();
		// HSSFSheet sheet = workbook.createSheet("Error Report");
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.createFreezePane(0, 1, 0, 1);
		sheet.setDefaultColumnWidth(20);
		addSheetConfig(workbook, sheet);
		HSSFRow rowheader = sheet.createRow(0);
		int count = 0;
		for (String excelheader : headers) {
			HSSFCell cell = rowheader.createCell(count++);
			cell.setCellValue(excelheader);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			cell.setCellStyle(cellStyle);
		}
		int mrgStrtIndx = B000027Constants.INT_EIGHT;
		int cellsToMrge = B000027Constants.INT_TWO;
		int mrgEndIndx = 0;
		while (mrgStrtIndx < aryLen) {
			mrgEndIndx = mrgStrtIndx + cellsToMrge;
			sheet.addMergedRegion(new CellRangeAddress(0, 0, mrgStrtIndx,
					mrgEndIndx));
			mrgStrtIndx = mrgStrtIndx + cellsToMrge + B000027Constants.INT_ONE;
		}
		HSSFRow scndRowheader = sheet.createRow(1);
		int scndRowcount = 0;
		for (String excelheaders : headersScndRw) {
			HSSFCell cell = scndRowheader.createCell(scndRowcount++);
			cell.setCellValue(excelheaders);
		}

		writeTwoHdrReport(items, sheet, formatMap);

		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * Report.
	 *
	 * @param items
	 *            the items
	 * @param sheet
	 *            the sheet
	 */
	private void writeReport(List<Object[]> items, HSSFSheet sheet,
			Map<String, String> formatMap) {
		int rowCount = 1;

		for (Object[] rowNumber : items) {
			int cellCount = 0;
			HSSFRow row = sheet.createRow(rowCount++);
			for (Object obj : rowNumber) {
				String value = CommonUtil.convertObjectToString(obj);
				if (formatMap != null
						&& formatMap.get(String.valueOf(cellCount)) != null) {
					String format = formatMap.get(String.valueOf(cellCount));
					if (!value.equalsIgnoreCase("")
							&& format
									.equalsIgnoreCase(PDConstants.DATE_FORMAT_YYYY_MM)) {
						value = CommonUtil.convertYearMonth(value);
					} else if (!value.equalsIgnoreCase("")
							&& format
									.equalsIgnoreCase(PDConstants.DATE_FORMAT_YYYY_MM_DD)) {
						value = CommonUtil.convertYearMonthDay(value);
					}
				}
				createNewCell(row, value, cellCount++);
			}

		}
	}

	/**
	 * Report.
	 *
	 * @param items
	 *            the items
	 * @param sheet
	 *            the sheet
	 */
	private void writeTwoHdrReport(List<Object[]> items, HSSFSheet sheet,
			Map<String, String> formatMap) {
		int rowCount = 2;

		for (Object[] rowNumber : items) {
			int cellCount = 0;
			HSSFRow row = sheet.createRow(rowCount++);
			for (Object obj : rowNumber) {
				String value = CommonUtil.convertObjectToString(obj);
				if (formatMap != null
						&& formatMap.get(String.valueOf(cellCount)) != null) {
					if (!value.equalsIgnoreCase("")) {
						value = CommonUtil.convertYearMonth(value);
					}
				}
				createNewCell(row, value, cellCount++);
			}

		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
