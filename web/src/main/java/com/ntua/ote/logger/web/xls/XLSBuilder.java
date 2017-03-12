package com.ntua.ote.logger.web.xls;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;

/**
 * Constructs a Excel document
 */
public class XLSBuilder {

	/** The wb. */
	private static Workbook wb;

	/** The sheet. */
	private static Sheet sheet;

	/** The headercs. */
	private static CellStyle headercs;

	/** The labelcs. */
	private static CellStyle labelcs;

	/** The valuecs. */
	private static CellStyle valuecs;
	
	/** The column num. */
	private static short columnNum;
	
	/** The resource bundles. */
	private static Properties resourceBundles = new Properties();

	/**
	 * Configure excel styles.
	 */
	private static void configureStyles() {
		headercs = wb.createCellStyle();
		headercs.setAlignment(CellStyle.ALIGN_CENTER);
		headercs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		headercs.setFillForegroundColor(HSSFColor.GREEN.index);
		headercs.setFillPattern((short) 1);

		labelcs = wb.createCellStyle();
		labelcs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		labelcs.setBorderBottom(CellStyle.BORDER_THIN);

		valuecs = wb.createCellStyle();
		valuecs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		valuecs.setAlignment(CellStyle.ALIGN_RIGHT);

		// create 2 fonts objects
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(HSSFColor.WHITE.index);

		Font labelFont = wb.createFont();
		labelFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Font valueFont = wb.createFont();

		// set cell stlye
		headercs.setFont(headerFont);
		labelcs.setFont(labelFont);
		valuecs.setFont(valueFont);

	}
	
	/**
	 * Draw cell.
	 *
	 * @param r the row
	 * @param value the cell value
	 * @param cellStyle the cell style
	 */
	private static void drawCell(Row r, String value, CellStyle cellStyle) {
		Cell c = r.createCell(columnNum);
		c.setCellStyle(cellStyle);
		c.setCellValue(value);
		columnNum++;
	}

	/**
	 * Draw results.
	 *
	 * @param results the results
	 * @param mappings the mappings
	 * @param header the header
	 */
	private static void drawResults(List<? extends Serializable> results, XLSColumn[] mappings, String header) {
		// declare a row object reference
		Row r = null;
		short rownum = 0;
		
		// header
		r = sheet.createRow(rownum);
		drawCell(r, resourceBundles.getProperty(header), headercs);
		
		// column names
		columnNum = 0;
		rownum++;
		r = sheet.createRow(rownum);
		for(XLSColumn mapping : mappings){
			drawCell(r, resourceBundles.getProperty(mapping.getLabel()), labelcs);
		}
		
		// data
		for (Serializable result : results) {
			columnNum = 0;
			rownum++;
			r = sheet.createRow(rownum);
			BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(result);

	        for (XLSColumn mapping : mappings) {
	        	//get the property descriptor for each field
	        	PropertyDescriptor pd = beanWrapper.getPropertyDescriptor(mapping.getField());
	            if (pd.getWriteMethod() != null) {
	                Class<?> propClazz = pd.getPropertyType();
	                Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
	                if (String.class.isAssignableFrom(propClazz)) {
	                	if (StringUtils.hasText((String) propertyValue)) {
	                		drawCell(r, propertyValue.toString(), valuecs);
	                	} else {
	                		drawCell(r, "", valuecs);
	                	}
	                } else if (Date.class.isAssignableFrom(propClazz)) {
	                	if ((propertyValue != null)) {
	                		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	                    	String date = new SimpleDateFormat("EEE, d MMM YYY HH:mm:ss", locale).format(((Date)propertyValue));
	            			drawCell(r, date, valuecs);
	                	} else {
	                		drawCell(r, "", valuecs);
	                	}
	                } else if (int.class.isAssignableFrom(propClazz)) {
	                	if ((propertyValue != null)) {
	                		drawCell(r, propertyValue.toString(), valuecs);
	                	} else {
	                		drawCell(r, "", valuecs);
	                	}
	                } else if (double.class.isAssignableFrom(propClazz)) {
	                	if ((propertyValue != null)) {
	                		drawCell(r, propertyValue.toString(), valuecs);
	                	} else {
	                		drawCell(r, "", valuecs);
	                	}
	                } else if (Enum.class.isAssignableFrom(propClazz)) {
	                	if ((propertyValue != null)) {
	                		drawCell(r, ((Enum)propertyValue).name(), valuecs);
	                	} else {
	                		drawCell(r, "", valuecs);
	                	}
	                }
	            }
	        }
		}

	}
	

	/**
	 * Creates a Excel Document which includes the results of a search.
	 *
	 * @param results the List of results must extend Serializable
	 * @param mappings the columns mapping
	 * @param header the header label
	 * @return the excel file in byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] exportXLS(List<? extends Serializable> results, XLSColumn[] mappings, String header) throws IOException {
		
		String language = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("CommonMessages_"+language.toLowerCase()+".properties");
		resourceBundles.load(input);
		
		// create a new workbook
		wb = new HSSFWorkbook();
		// create a new sheet
		sheet = wb.createSheet();
		// create 3 cell styles
		wb.setSheetName(0, resourceBundles.getProperty(header));
		
		columnNum = 0;
		configureStyles();
		//create a merged region for the header
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, mappings.length - 1));
		
		drawResults(results, mappings, header);
		
		//autosize each column
		for (int i = 0; i < mappings.length; i++)
			sheet.autoSizeColumn(i);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
		    wb.write(bos);
		} finally {
		    bos.close();
		}
		byte[] bytes = bos.toByteArray();
		return  bytes;
	}
	
}
