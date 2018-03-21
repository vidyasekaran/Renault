/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-I000083
 * Module          :OR ORDERING
 * Process Outline :Send Weekly Production Order to Plant
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.i000083.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.READ_COUNT;
import static com.nissangroups.pd.util.PDConstants.READ_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDConstants.STAR;
import static com.nissangroups.pd.util.PDConstants.STEP_ID;
import static com.nissangroups.pd.util.PDConstants.WRITE_COUNT;
import static com.nissangroups.pd.util.PDConstants.WRITE_SKIPPED_COUNT;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;
import static com.nissangroups.pd.util.PDMessageConsants.M00043;
import static com.nissangroups.pd.util.PDMessageConsants.M00076;
import static com.nissangroups.pd.util.PDMessageConsants.M00113;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.i000083.output.I000083OutputBean;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnFileHdrPK;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.CmnInterfaceDataPK;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class is used to process and do the business logic for the interface I000083.
 * 
 * @author z015895
 *
 */
public class I000083Processor implements
		ItemProcessor<I000083OutputBean, CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(I000083Processor.class);

	/** Variable commonu tility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable jobExec. */
	private JobExecution jobExec;

	/** Variable interface file id. */
	private String ifFileId;

	/** Variable sequence no. */
	private long seqNo;

	/**
	 * This method is used to get stepExecution and assign into instance
	 * variable
	 * 
	 * @param stepExecution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (commonutility.isErrFlg()) {
			LOG.error(commonutility.getRemarks());
			CommonUtil.stopBatch();	
		}
		jobExec = stepExecution.getJobExecution();
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		Object sequenceNo = jobExec.getExecutionContext().get(
				IFConstants.SEQ_NO);
		this.ifFileId = ifFileId;
		this.seqNo = Long.valueOf(sequenceNo + "");
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * This method is to Send Weekly Production Order to Plant.
	 * 
	 *  (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public CmnInterfaceData process(I000083OutputBean item) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		long rowCount = commonutility.getRowCount() + 1;

		CmnInterfaceData cmnIfData = new CmnInterfaceData();
		cmnIfData.setId(new CmnInterfaceDataPK());
		cmnIfData.getId().setSeqNo(seqNo);
		cmnIfData.getId().setRowNo(rowCount);
		cmnIfData.getId().setIfFileId(ifFileId);
		CmnFileHdr fileHdr = new CmnFileHdr();
		fileHdr.setId(new CmnFileHdrPK());
		fileHdr.getId().setIfFileId(ifFileId);
		fileHdr.getId().setSeqNo(seqNo);
		cmnIfData.setCmnFileHdr(fileHdr);
		
		//cmnIfData.setCol1((item.getPlntCd() == null) ? " " : item.getPlntCd());
		cmnIfData.setCol1(" ");
		cmnIfData.setCol2(item.getCarSeries());
		cmnIfData.setCol3(item.getProdFmyCd());
		cmnIfData.setCol4(item.getAppliedModelCd() + "" + item.getPackCD());
		if (cmnIfData.getCol4().length() >= 18) {
			cmnIfData.setCol5(cmnIfData.getCol4().substring(0, 7));
			cmnIfData.setCol6(cmnIfData.getCol4().substring(7, 10));
			cmnIfData.setCol7(cmnIfData.getCol4().substring(10, 18));
		}

		cmnIfData.setCol8(item.getExteriorColor());
		cmnIfData.setCol9(item.getInteriorColor());
		cmnIfData.setCol10(item.getExNo());
		cmnIfData.setCol11(item.getSpecDestination());
		cmnIfData.setCol12(" ");		
		//cmnIfData.setCol13((item.getLineClass() == null) ? " " : item.getLineClass());
		cmnIfData.setCol13(" ");
		cmnIfData.setCol14(item.getProdMethodCd());
		cmnIfData.setCol15(item.getOrdTakBaseMon());
		cmnIfData.setCol16(item.getOcfRegionCd());
		cmnIfData.setCol17(item.getTyreMkrCd() + "" + item.getDealerLst() + ""
				+ item.getOwnrMnl() + "" + item.getWrntyBklt() + ""
				+ item.getBdyPrtctnCd());
		cmnIfData.setCol18(item.getTyreMkrCd());
		cmnIfData.setCol19(item.getDealerLst());
		cmnIfData.setCol20(item.getOwnrMnl());
		cmnIfData.setCol21(item.getWrntyBklt());
		cmnIfData.setCol22(item.getBdyPrtctnCd());
		String[] ordrDailyQty = getDailyQty(item.getProdWkNo(),
				item.getProdDayNo(), item.getOrdrQty());
		cmnIfData.setCol23(ordrDailyQty[0]);
		cmnIfData.setCol24(ordrDailyQty[1]);
		//cmnIfData.setCol25(item.getProdOrdrNo());
		cmnIfData.setCol25(" ");
		cmnIfData.setCol26(item.getSlsNoteNo());
		cmnIfData.setCol27(item.getAdditionalSpecCd());
		cmnIfData.setCol28(item.getBuyerCD());
		cmnIfData.setCol29(item.getOrdrFxdFlag());
		cmnIfData.setCol30(" ");

		commonutility.setRowCount(rowCount);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return cmnIfData;
	}

	/**
	 * This method executed Each step Execution To get the count of Reader,
	 * Writer Based on the count values and write the Log.
	 *
	 * @param stepExecution
	 *            the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00076.replace(
					PDConstants.ERROR_MESSAGE_1, ""));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));
		} else if (stepExecution.getReadCount() == 0) {
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00003);
			LOG.info(M00003);
		}

		else if (stepExecution.getReadCount() == stepExecution.getWriteCount()) {
			// write count in header
			commonutility.setWriteCount(stepExecution.getWriteCount());
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00113);
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_FAILURE_STATUS);
			commonutility.setRemarks(M00043);
			LOG.info(M00043);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/**
	 * This method used to summarize daily quantity based production week no, production day no and order quantity
	 * 
	 * @param prodWkNo
	 * @param prodDayNo
	 * @param ordrQty
	 * @return String[] production week no, production day no and order quantity
	 */
	private String[] getDailyQty(String prodWkNo, String prodDayNo,
			String ordrQty) {

		String dailyQty = "";
		int finalOrdrQty = 0;
		String[] returnData = new String[2];

		try {

			for (int i = 0; i < 175; i++) {
				dailyQty = dailyQty + "0";
			}
			List<String> prodWkNoLst = Arrays.asList(prodWkNo.split(","));
			List<String> prodDayNoLst = Arrays.asList(prodDayNo.split(","));
			List<String> ordrQtyLst = Arrays.asList(ordrQty.split(","));

			Map<String, Object[]> dailyQtyMap = calDailyQty(prodWkNoLst,
					prodDayNoLst, ordrQtyLst);

			for (Map.Entry<String, Object[]> entry : dailyQtyMap.entrySet()) {

				Object[] dailyQtyObj = entry.getValue();
				int prodWk = Integer.parseInt(dailyQtyObj[0] + "");
				int prodDay = Integer.parseInt(dailyQtyObj[1] + "");
				int dayNo = ((prodWk - 1) * 7) + prodDay;
				String finalVal = formDailyQty(Integer.parseInt(dailyQtyObj[2]
						+ ""));
				dailyQty = dailyQty.substring(0, (dayNo * 5) - 5) + finalVal
						+ dailyQty.substring(dayNo * 5);
				finalOrdrQty = finalOrdrQty
						+ Integer.parseInt(dailyQtyObj[2] + "");
			}

			returnData[0] = dailyQty;
			returnData[1] = finalOrdrQty + "";

		} catch (Exception e) {
			LOG.error("*** Exception in dailyQty calculation ***");
			LOG.error("prodWkNo : " + prodWkNo + "prodDayNo : " + prodDayNo
					+ "ordrQty : " + ordrQty);
			LOG.error(""+e);			
		}

		return returnData;

	}

	/**
	 * This method used to add order quantity based on production week no and production day no
	 * 
	 * @param prodWkNoLst
	 * @param prodDayNoLst
	 * @param ordrQtyLst
	 * @return summarized daily quantity map data
	 */
	private Map<String, Object[]> calDailyQty(List<String> prodWkNoLst,
			List<String> prodDayNoLst, List<String> ordrQtyLst) {

		Map<String, Object[]> daiyQtyMap = new HashMap<String, Object[]>();
		if (prodWkNoLst.size() == prodDayNoLst.size()
				&& prodWkNoLst.size() == ordrQtyLst.size()) {

			for (int i = 0; i < prodWkNoLst.size(); i++) {

				int prodWk = Integer.parseInt(prodWkNoLst.get(i));
				int prodDay = Integer.parseInt(prodDayNoLst.get(i));
				int ordrQty = Integer.parseInt(ordrQtyLst.get(i));
				Object[] dailyQty = daiyQtyMap.get(prodWk + "" + prodDay);

				if (dailyQty != null) {
					ordrQty = Integer.parseInt(dailyQty[2] + "") + ordrQty;
					dailyQty[2] = ordrQty;
				} else {
					dailyQty = new Object[3];
					dailyQty[0] = prodWk;
					dailyQty[1] = prodDay;
					dailyQty[2] = ordrQty;
				}
				daiyQtyMap.put(prodWk + "" + prodDay, dailyQty);
			}
		}

		return daiyQtyMap;
	}

	/**
	 * Format daily quantity to 5 fixed length char
	 * 
	 * @param dailyQty
	 * @return daily quantity String
	 */
	private String formDailyQty(int dailyQty) {

		String dailyQtyStr = dailyQty + "";
		int length = dailyQtyStr.length();
		String qty = "";

		switch (length) {
		case 1:
			qty = "0000" + dailyQty;
			break;
		case 2:
			qty = "000" + dailyQty;
			break;
		case 3:
			qty = "00" + dailyQty;
			break;
		case 4:
			qty = "0" + dailyQty;
			break;
		case 5:
			qty = "" + dailyQty;
			break;

		default:
			qty = "00000";
			break;
		}

		return qty;
	}

}
