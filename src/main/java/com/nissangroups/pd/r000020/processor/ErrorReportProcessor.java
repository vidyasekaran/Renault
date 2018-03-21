package com.nissangroups.pd.r000020.processor;

import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.EXCEPTION;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_XLS;
import static com.nissangroups.pd.util.PDConstants.R000020_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.REPORT_COLOR_CODE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ADD_SPEC_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_BYR_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_CAR_SRS;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_END_ITEM;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ERROR_MSG;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_EX_NO;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_ORDR_TK_BS_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_POT;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PRDN_FMLY_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_PROD_PRD;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_SALES_NOTE;
import static com.nissangroups.pd.util.PDConstants.REPORT_HEADER_SPEC_DEST;
import static com.nissangroups.pd.util.PDConstants.REPORT_OCF_RGN_CD;
import static com.nissangroups.pd.util.PDConstants.REPORT_PROD_WEEK_NO;
import static com.nissangroups.pd.util.PDConstants.REPORT_QUANTITY;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.util.CommonExcelItemWriter;
import com.nissangroups.pd.util.PDConstants;

public class ErrorReportProcessor implements
		ItemProcessor<String, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ErrorReportProcessor.class);

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepository;


	private String porCd;
	private String ocfRgnCd;
	List<Object[]> errorRprtLst = new ArrayList<>();

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;

	@Override
	public R000020InputParamBean process(String item)
			throws PdApplicationException {
		R000020InputParamBean r000020InputParamBean = new R000020InputParamBean();
		writeDataToErrorReport();
		return r000020InputParamBean;
	}

	public void writeDataToErrorReport() throws PdApplicationException {
		List<Object[]> errorList = getErrorData();
		for (Object[] errorData : errorList) {
			Object[] errorArr = new Object[16];
			errorArr[0] = (String) errorData[0];// Order Take Base Month
			errorArr[1] = String.valueOf(errorData[1]);// Ocf Region Code
			errorArr[2] = (String) errorData[2];// Production Month
			errorArr[3] = (String) errorData[3];// Buyer Code
			errorArr[4] = (String) errorData[4];// Car Series
			errorArr[5] = (String) errorData[5];// Production Family Code
			errorArr[6] = (String) errorData[6];// Spec Destination code
			errorArr[7] = (String) errorData[7] + (String) errorData[8];// Applied
																		// Mdl
																		// Cd
																		// and
																		// Pck
																		// Cd
			errorArr[8] = (String) errorData[9];// Additional Spec Code
			errorArr[9] = String.valueOf(errorData[10]);// Pot Cd
			errorArr[10] = (String) errorData[11] + (String) errorData[12];// Ext
																			// Color
																			// and
																			// Int
																			// Color
			errorArr[11] = (String) errorData[13];// Sales Note
			errorArr[12] = (String) errorData[14];// Ex no
			errorArr[13] = String.valueOf(errorData[15]);// Production week no
			errorArr[14] = String.valueOf(errorData[16]);// Sum of order qty
			errorArr[15] = (String) errorData[17];// Error message
			errorRprtLst.add(errorArr);
		}
		createErrorReport();
		
		if (!getErrorDataCount().isEmpty()) {
			throw new PdApplicationException("Error data exists");
		}
	}
	
	public List<Object[]> getErrorDataCount() {
		QueryParamBean qryPrmBean = new QueryParamBean();
		qryPrmBean.setPorCd(porCd);
		return mnthlySchdlIfTrnRepository.getErrorDataCount(qryPrmBean);
	}
	public List<Object[]> getErrorData() {
		QueryParamBean qryPrmBean = new QueryParamBean();
		qryPrmBean.setPorCd(porCd);
		return mnthlySchdlIfTrnRepository.getErrorData(qryPrmBean);
	}

	/**
	 * This method is used to create error report.
	 * 
	 */
	public void createErrorReport() {
		QueryParamBean qryPrmBn = new QueryParamBean();
		String fileName = null;
		String errorPath = environment.getProperty(R000020_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		qryPrmBn.setPorCd(porCd);
		qryPrmBn.setOcfRgnCd(ocfRgnCd);
		List<String> ocfRgnLst = extractVldOcfCd(qryPrmBn);
		if (ocfRgnCd != null && !ocfRgnLst.isEmpty()) {
			String ocfRgnFileNm = "-";

			for (String ocfRgnCdErr : ocfRgnLst) {
				ocfRgnFileNm += ocfRgnCdErr + "-";
			}
			fileName = errorPath.trim() + PDConstants.REPORT_SUFFIX_R20
					+ ocfRgnFileNm + dateFormat.format(new Date())
					+ FILE_EXT_XLS;
		} else {
			fileName = errorPath.trim() + PDConstants.REPORT_SUFFIX_R20
					+ dateFormat.format(new Date()) + FILE_EXT_XLS;
		}

		CommonExcelItemWriter excelItemWriter = new CommonExcelItemWriter();
		excelItemWriter.setFilePath(fileName);

		excelItemWriter.setHeaders(new String[] { REPORT_HEADER_ORDR_TK_BS_PRD,
				REPORT_OCF_RGN_CD, REPORT_HEADER_PROD_PRD,
				REPORT_HEADER_BYR_CD, REPORT_HEADER_CAR_SRS,
				REPORT_HEADER_PRDN_FMLY_CD, REPORT_HEADER_SPEC_DEST,
				REPORT_HEADER_END_ITEM, REPORT_HEADER_ADD_SPEC_CD,
				REPORT_HEADER_POT, REPORT_COLOR_CODE, REPORT_HEADER_SALES_NOTE,
				REPORT_HEADER_EX_NO, REPORT_PROD_WEEK_NO, REPORT_QUANTITY,
				REPORT_HEADER_ERROR_MSG });

		try {
			Map<String, String> formatMap = new HashMap<String, String>();
			formatMap.put("0", "YYYY-MM");
			formatMap.put("2", "YYYY-MM");
			excelItemWriter.createReport(errorRprtLst, formatMap,
					"Error Report");
		} catch (IOException e) {
			LOG.error(EXCEPTION + e);

		}

	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	public List<String> extractVldOcfCd(QueryParamBean qryPrmBn) {
		return mnthlySchdlIfTrnRepository.extractVldOcfCd(qryPrmBn);
	}

}
