/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000001/PST-DRG-R000001
 * Module          :SP SPEC
 * Process Outline :Validating the interface fields with Master and generating the error report
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z002548(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.writer;

import static com.nissangroups.pd.util.PDConstants.ABOLISH_MONTH;
import static com.nissangroups.pd.util.PDConstants.ADDITION_SPEC_CODE;
import static com.nissangroups.pd.util.PDConstants.ADOPT_MONTH;
import static com.nissangroups.pd.util.PDConstants.BACK_SLASH;
import static com.nissangroups.pd.util.PDConstants.BUYER_CODE;
import static com.nissangroups.pd.util.PDConstants.COLOR_CODE;
import static com.nissangroups.pd.util.PDConstants.COMMENTS;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.END_ITEM_MODEL_CODE;
import static com.nissangroups.pd.util.PDConstants.ERROR_REPORT_ALL;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.POR_CD;
import static com.nissangroups.pd.util.PDConstants.PRODUCTION_FAMILY_CODE;
import static com.nissangroups.pd.util.PDConstants.PRODUCTION_STAGE_CODE;
import static com.nissangroups.pd.util.PDConstants.SPEC_DESTINATION_CODE;
import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.B000001_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.REPORT_SUFFIX;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.B1_ERROR_TYPE;
import static com.nissangroups.pd.util.PDConstants.B1_POR_CODE;
import static com.nissangroups.pd.util.PDConstants.B1_PROD_FAM_CODE;
import static com.nissangroups.pd.util.PDConstants.B1_ABOLISH_MONTH;
import static com.nissangroups.pd.util.PDConstants.B1_ADDITION_SPEC_CODE;
import static com.nissangroups.pd.util.PDConstants.B1_ADOPT_MONTH;
import static com.nissangroups.pd.util.PDConstants.B1_BUYER_CODE;
import static com.nissangroups.pd.util.PDConstants.B1_COLOR_CODE;
import static com.nissangroups.pd.util.PDConstants.B1_COMMENTS;
import static com.nissangroups.pd.util.PDConstants.B1_END_ITEM_MODEL_CODE;
import static com.nissangroups.pd.util.PDConstants.B1_PROD_STG_CODE;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.B1_SPEC_DEST_CODE;

import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.WRITER_NOT_OPEN_MSG;
import static com.nissangroups.pd.util.PDConstants.FILE_CORRUPT_MSG;
import static com.nissangroups.pd.util.PDConstants.FLAT_FILE_WRITE_ERROR_MSG;
import static org.springframework.batch.item.file.transform.DelimitedLineTokenizer.DELIMITER_TAB;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.WriterNotOpenException;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.nissangroups.pd.dao.B000001ReportDao;
import com.nissangroups.pd.header.B000001ErrorReportHeader;
import com.nissangroups.pd.util.CommonExcelItemWriter;


/**
 * The Class B000001ErrorWriter.
 *
 * @author z002548
 */
@Configuration
@PropertySource(BATCH_CONFIG_CLASSPATH)
 class B000001ErrorWriter implements ItemWriter<B000001ReportDao> {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000001ErrorWriter.class);

	/** Variable resource. */
	private Resource resource;
	
	/** Variable error path. */
	private String errorPath;
	
	/** Variable por key all. */
	private String porKeyAll;

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	/** Variable callback. */
	FlatFileHeaderCallback callback;
	
	/** Variable delegate. */
	FlatFileItemWriter<B000001ReportDao> delegate;
	
	/** Variable custom excel item writer. */
	CommonExcelItemWriter customExcelItemWriter;

	/** Variable por base map. */
	private Map<String, List<B000001ReportDao>> porBaseMap = new HashMap<String, List<B000001ReportDao>>();
	
	/** Variable all pors. */
	private List<B000001ReportDao> allPors = new ArrayList<B000001ReportDao>();

	/**
	 * Sets the delegate.
	 *
	 * @param delegate the new delegate
	 */
	public void setDelegate(FlatFileItemWriter<B000001ReportDao> delegate) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.delegate = delegate;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Open.
	 *
	 * @param executionContext the execution context
	 */
	public void open(ExecutionContext executionContext) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.delegate.open(executionContext);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Update.
	 *
	 * @param executionContext the execution context
	 */
	public void update(ExecutionContext executionContext) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.delegate.update(executionContext);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Close.
	 */
	public void close(){
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.delegate.close();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Before write method to Set the Error Report Path.
	 */
	@BeforeWrite
	public void beforeWrite() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		errorPath = environment.getProperty(B000001_REPORT_PATH);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

	/**
	 *  
	 * To Write the Report Reader data to tsv format.
	 *
	 * @param items the items
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends B000001ReportDao> items) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		for (B000001ReportDao reportDao : items) {
			
			String porStr = reportDao.getPorCode();
			/** To Check Map have same  or different POR Code  */
			if (porBaseMap.containsKey(porStr)) {
				/** In the Report DAO have same POR, then add to List  */ 
				List<B000001ReportDao> reportDaos = porBaseMap.get(porStr);
				reportDaos.add(reportDao);
			} else {
				/** In the Report DAO have different POR then create new List and add to Map  */
				List<B000001ReportDao> reportDaos = new ArrayList<B000001ReportDao>();
				reportDaos.add(reportDao);
				porBaseMap.put(porStr, reportDaos);
			}

		}
	
		/** Error Report based on POR    */
		for (Map.Entry<String, List<B000001ReportDao>> entry : porBaseMap.entrySet()) {
			String porKey= entry.getKey();
			List<B000001ReportDao> errorList = entry.getValue();
			/** Process ID : P0008.2 */
			allPors.addAll(errorList);
			/** Process ID : P0008.2 */
			doReport(porKey, errorList);
 			/*doExcelReport(porKey, errorList);*/
		
		}

		/** Full Error Report */
		doReport(porKeyAll, allPors);
		/*doExcelReport(porKeyAll, allPors);*/
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	
	/**
	 * TSV format Error Report
	 * To Create Error Report based on POR
	 * And also create Over ALL Report.
	 *
	 * @param porstr1 the porstr1
	 * @param items the items
	 */
	public void doReport(String porstr1, List<B000001ReportDao> items) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String porstr = porstr1;
		
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		if(porstr1 == null){
			porstr = ERROR_REPORT_ALL;
		}
		String dirPath = errorPath+porstr;
		String fileName = porstr.trim()+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_TSV;
		
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
			File file = new File(dir,fileName);
			resource = new FileSystemResource(file);
			callback = new B000001ErrorReportHeader();
			delegate = new FlatFileItemWriter<B000001ReportDao>();
			delegate.setResource(resource);
			/** Error Report Header Value */
			delegate.setHeaderCallback(callback);
			DelimitedLineAggregator<B000001ReportDao> delimited = new DelimitedLineAggregator<B000001ReportDao>();
			delimited.setDelimiter(DELIMITER_TAB);
			BeanWrapperFieldExtractor<B000001ReportDao> extractor = new BeanWrapperFieldExtractor<B000001ReportDao>();
			/**  Error Report value */
			extractor.setNames(new String[] {B1_ERROR_TYPE,B1_POR_CODE,
					B1_PROD_FAM_CODE, B1_PROD_STG_CODE,
					B1_BUYER_CODE, B1_END_ITEM_MODEL_CODE, B1_COLOR_CODE,
					B1_ADDITION_SPEC_CODE, B1_SPEC_DEST_CODE, B1_ADOPT_MONTH,
					B1_ABOLISH_MONTH, B1_COMMENTS });
			delimited.setFieldExtractor(extractor);
			delegate.setLineAggregator(delimited);
			delegate.open(new ExecutionContext());
			delegate.write(items);
		}
		catch(WriterNotOpenException we) {
			LOG.error(WRITER_NOT_OPEN_MSG,we);
		}
		catch(IOException ie) {
			LOG.error(FILE_CORRUPT_MSG,ie);
		}
		catch (Exception e) {
			LOG.error(FLAT_FILE_WRITE_ERROR_MSG, e);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	
	/**
	 * Excel based Error Report.
	 *
	 * @param porstr1 the porstr1
	 * @param items the items
	 */
	public void doExcelReport(String porstr1, List<B000001ReportDao> items)  {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
	    String porstr = porstr1;
        
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        if(porstr1 == null){
            porstr = ERROR_REPORT_ALL;
        }
        String dirPath = errorPath+porstr;
        String fileName = porstr+REPORT_SUFFIX+dateFormat.format(new Date())+FILE_EXT_XLS;
        
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        customExcelItemWriter = new CommonExcelItemWriter();
        customExcelItemWriter.setFilePath(dirPath+BACK_SLASH+fileName);
        customExcelItemWriter.setHeaders(new String[] {B1_ERROR_TYPE,POR_CD,PRODUCTION_STAGE_CODE, PRODUCTION_FAMILY_CODE,
             BUYER_CODE, END_ITEM_MODEL_CODE,COLOR_CODE,
             ADDITION_SPEC_CODE, SPEC_DESTINATION_CODE, ADOPT_MONTH,
             ABOLISH_MONTH, COMMENTS });
        try {
            customExcelItemWriter.write(items);
        } catch (IOException e) {
            LOG.error(EXCEPTION+e);
            
        }
        
        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
