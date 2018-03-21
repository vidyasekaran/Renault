/*
 * System Name     :Post Dragon 
 * Sub system Name :I Interface
 * Function ID     :PST_DRG_I000087
 * Module          :Ordering
 * Process Outline :Interface To Receive Weekly Production Schedule from Plant	
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000087.processor;

import static com.nissangroups.pd.util.PDConstants.DATA_INSERTED_MSG;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.bean.WeeklyClndrBean;
import com.nissangroups.pd.dao.OrderQtyBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.i000087.output.I000087OutputBean;
import com.nissangroups.pd.i000087.util.I000087QueryConstants;
import com.nissangroups.pd.model.MstWkNoClndr;
import com.nissangroups.pd.model.TrnWklyProdShdlIf;
import com.nissangroups.pd.model.TrnWklyProdShdlIfPK;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 *  This class I000087Processor is to process extracted details of the Interface file with the given File Id, which has smallest Sequence No and is not yet processed
 *
 * @author z016127
 */
public class I000087Processor implements
		ItemProcessor<I000087OutputBean, List<TrnWklyProdShdlIf>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000087Processor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** Common utility service bean injection */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable interface id. */
	private String interfaceId = null;

	/** Variable production month list */
	private List<String> prdMnthLst = null;
	
	/** Variable sequence number */
	private long seqNum = 0L;

	/** Variable por code */
	private String porCd = null;

	/** Variable order quantity */
	String ordrQty = null;

	/** Variable production month */
	String prdMnth = null;

	/** Variable sequence id */
	int seqId = 0;

	/** CommonRepository service bean injection */
	@Autowired(required = false)
	private CommonRepository cmnRep;

	/** Variable I000087OutputBean */
	I000087OutputBean outputBeanTemp = new I000087OutputBean();
	
	/** Variable TrnWklyProdShdlIf list */
	List<TrnWklyProdShdlIf> trnWklyProdShdlIfLst = null;

	/** Variable weekFirstDate map */
	Map<String, String> weekFirstDtMp = null;

	/** Variable weekNoClMpTemp map */
	Map<String, WeeklyClndrBean> weekNoClMpTemp = null;

	/** Variable delete flag. */
	boolean dltFlg = false;

	/**
	 * This method will be called just before each step execution
	 * Get interface id and por code from context and assign into instance variable
	 * 
	 * @param stepExecution 
	 * 					the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		interfaceId = stepExecution.getJobParameters().getString(
				PDConstants.INTERFACE_FILE_ID);
		porCd = stepExecution.getJobParameters().getString(
				PDConstants.BATCH_POR_CODE);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method will be called just before each process execution.It is used to get the production month
	 * delete the old data in weekly production schedule if table and extract the sequence number
	 * 
	 * @throws ParseException
	 */
	@BeforeProcess
	public void beforeProcess() throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		if (!dltFlg) {
			getPrdMnth();
			extractWeekNoDtls();
			deleteOldData();
			seqNum = getSqNoWklyProdSchdl();
			dltFlg = true;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0002 Delete data in weekly production schedule if table.
	 */
	public void deleteOldData() {
		deleteWklySchdlIf(porCd);
	}

	/**
	 * P0002 Method to delete data in weekly production schedule if table.
	 * 
	 * @param porCd
	 * 			string
	 */
	public void deleteWklySchdlIf(String porCd){
		String deleteQryStr = I000087QueryConstants.deleteWklySchdlIf.toString();
		Query deleteQry = entityManager.createNativeQuery(deleteQryStr);
		deleteQry.setParameter(PDConstants.PRMTR_PORCD, porCd);
		deleteQry.executeUpdate();
	}
	
	/** 
	 * This method is to Insert the extracted List of data by iterating which id fetched from the common pool into the weekly production schedule if table
	 * P0003
	 * 
	 * @param item 
	 * 				I000087OutputBean
	 * @return the list of TrnWklyProdShdlIf 
	 * 						the class
	 * @throws Exception the exception
	 */
	@Override
	public List<TrnWklyProdShdlIf> process(I000087OutputBean outputBean)
			throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		trnWklyProdShdlIfLst = new ArrayList<>();
		outputBeanTemp = outputBean;
		LOG.info(DATA_INSERTED_MSG);

		prdMnth = outputBean.getCol1().substring(0, 6);
		ordrQty = outputBean.getCol16();
		List<OrderQtyBean> dateDtls = splitOrdQty();
		for (OrderQtyBean dateDtlsStr : dateDtls) {
			addDataToEntity(dateDtlsStr);

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return trnWklyProdShdlIfLst;
	}

	/**
	 * This method gets executed after each step Execution to get the count of Reader, Writer
	 * Based on the count values  and write the Log.
	 *
	 * @param stepExecution the step execution
	 */
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		LOG.info(READ_SKIPPED_COUNT + stepExecution.getReadSkipCount());
		LOG.info(WRITE_COUNT + stepExecution.getWriteCount());
		LOG.info(WRITE_SKIPPED_COUNT + stepExecution.getWriteSkipCount());

		if (stepExecution.getReadCount() == 0) {
			
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(PDMessageConsants.M00003);
			LOG.info(M00003);
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));
			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));

		} else if (stepExecution.getReadCount() == stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_PROCESSED_STATUS);
			commonutility.setRemarks(PDMessageConsants.M00113);
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			commonutility.setStatus(PDConstants.INTERFACE_UNPROCESSED_STATUS);
			commonutility.setRemarks(PDMessageConsants.M00043);
			LOG.info(M00043);
		}
		//Else condition will not occur as the execution falls in anyone of the above conditions
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0002.1 This method is used to split the order quantity and add the records to order quantity bean list
	 * 
	 * @return the list of order quantity bean
	 * @throws ParseException
	 * @throws PdApplicationException
	 */
	public List<OrderQtyBean> splitOrdQty() throws PdApplicationException {
		
		LOG.info(DOLLAR + "Split Order Quantity : Inside" + DOLLAR);
		List<OrderQtyBean> ordrQtyLst = new ArrayList<OrderQtyBean>();
		
		int spltLngth = IFConstants.MXQTYEND;
		int mxQtyStart = IFConstants.MXQTYSTART;
		int mxQtyend = IFConstants.MXQTYEND;
		int prdDyNo = IFConstants.COUNT;
		int nxtCount = IFConstants.MXQTYEND;
		int count = IFConstants.COUNT;
		
		String two = PDConstants.INTERFACE_42_HRZN;
		String firstOfflnDt = weekFirstDtMp.get(prdMnth);
		String errorMessage = PDMessageConsants.M00120.replace(
				PDConstants.AMPERSAND_ONE, prdMnth);
		if (firstOfflnDt != null) {
			for (int i = 1; i <= ordrQty.length() / spltLngth; i++) {
				OrderQtyBean orderQtyBean = new OrderQtyBean();
				String singleOrdrQty = ordrQty.substring(mxQtyStart, mxQtyend);
				if (count > 7) {
					prdDyNo = IFConstants.COUNT;
					count = IFConstants.COUNT;
				}
				if (CommonUtil.stringtoInt(singleOrdrQty) != 0) {

					WeeklyClndrBean weeklyClndrBean = weekNoClMpTemp
							.get(firstOfflnDt);
					if (weeklyClndrBean == null) {

						LOG.error(errorMessage);
						throw new PdApplicationException();
					}
					orderQtyBean.setPrdDyNo(prdDyNo);
					orderQtyBean.setPrdWeekNo(CommonUtil
							.stringtoInt(weeklyClndrBean.getProdWkNo()));
					orderQtyBean.setWeekNoOfYear(CommonUtil
							.stringtoInt(weeklyClndrBean.getWkNoYr().trim()));
					orderQtyBean.setMxQty(singleOrdrQty);
					orderQtyBean.setOfflnDt(firstOfflnDt);
					ordrQtyLst.add(orderQtyBean);
				}
				mxQtyStart = mxQtyStart + nxtCount;
				mxQtyend = mxQtyend + nxtCount;
				prdDyNo = prdDyNo + 1;
				try {
					firstOfflnDt = CommonUtil.offlnDtCal(firstOfflnDt, two);
				} catch (ParseException e) {
					LOG.error(e);
				}
				++count;
			}
		} else {
			LOG.error(errorMessage);
			throw new PdApplicationException();
		}
		LOG.info(DOLLAR + "Split Order Quantity : OutSide" + DOLLAR);
		return ordrQtyLst;
	}

	/**
	 * P0002.2 This method is used to fetch the week no calendar details.
	 * 
	 * @throws ParseException
	 */
	public void extractWeekNoDtls() throws ParseException {
		List<MstWkNoClndr> weekNoDtls = cmnRep.getWeekNODtls(porCd, prdMnthLst);

		weekFirstDtMp = new HashMap<>();
		weekNoClMpTemp = new HashMap<>();
		for (MstWkNoClndr wkNoClndrArry : weekNoDtls) {
			Map<String, WeeklyClndrBean> weekNoClMp = CommonUtil
					.getWeeklyOrdlsMap(wkNoClndrArry);
			weekNoClMpTemp.putAll(weekNoClMp);
			Set<String> setUniqMnth = new HashSet<>();
			// To get the first day of the month.
			if (wkNoClndrArry.getId().getProdWkNo()
					.equalsIgnoreCase(PDConstants.FIRST_WEEK)
					&& setUniqMnth.add(wkNoClndrArry.getWkStrtDate())) {
				weekFirstDtMp.put(wkNoClndrArry.getId().getProdMnth(),
						wkNoClndrArry.getWkStrtDate());
			}
		}
	}

	/**
	 * P0001 This method is used to get the List of valid production month.
	 * 
	 */
	public void getPrdMnth() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String prdMnthQuery = I000087QueryConstants.prdMnthQuery.toString();
		prdMnthLst = new ArrayList<>();
		Query query = entityManager.createQuery(prdMnthQuery);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, interfaceId);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_STATUS,
				PDConstants.INTERFACE_UNPROCESSED_STATUS);
		prdMnthLst = query.getResultList();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * P0003 This method is used to the split the orders in to single quantity and set
	 * in the entity object.
	 * 
	 * @param ordrDtls
	 * 				OrderQtyBean
	 */
	public void addDataToEntity(OrderQtyBean ordrDtls) {
		int maxQty = CommonUtil.stringtoInt(ordrDtls.getMxQty());
		
		
		for (int i = 1; i <= maxQty; i++) {
			seqNum ++;
			
			TrnWklyProdShdlIf trnWklyProdShdlIf = new TrnWklyProdShdlIf();
			TrnWklyProdShdlIfPK trnWklyProdShdlIfPk = new TrnWklyProdShdlIfPK();
			trnWklyProdShdlIfPk.setPorCd(porCd);
			trnWklyProdShdlIfPk.setSeqId(String.valueOf(seqNum));
			trnWklyProdShdlIfPk.setOrdrtkBaseMnth(outputBeanTemp
					.getCol19().substring(0, 6));
			trnWklyProdShdlIfPk.setOrdrtkBaseMnthWkNo(outputBeanTemp
					.getCol19().substring(6, 8));
			trnWklyProdShdlIfPk.setProdMnth(outputBeanTemp.getCol1()
					.substring(0, 6));
			trnWklyProdShdlIf.setProdFmlyCd(outputBeanTemp.getCol5());
			trnWklyProdShdlIf.setCarSrs(outputBeanTemp.getCol4());
			trnWklyProdShdlIf.setBuyerCd(outputBeanTemp.getCol26());
			trnWklyProdShdlIf.setAppldMdlCd(outputBeanTemp.getCol6()
					.substring(0, 13));
			trnWklyProdShdlIf.setPackCd(outputBeanTemp.getCol6()
					.substring(13, 18));
			trnWklyProdShdlIf.setSpecDestnCd(outputBeanTemp.getCol8());
			trnWklyProdShdlIf.setAddSpecCd(outputBeanTemp.getCol25());
			trnWklyProdShdlIf.setExtClrCd(outputBeanTemp.getCol6()
					.substring(18, 21));
			trnWklyProdShdlIf.setIntClrCd(outputBeanTemp.getCol6()
					.substring(21, 22));
			trnWklyProdShdlIf.setPotCd(outputBeanTemp.getVal1());
			trnWklyProdShdlIf.setOfflnPlanDate(ordrDtls.getOfflnDt());
			trnWklyProdShdlIf.setProdWkNo(String.valueOf(ordrDtls
					.getPrdWeekNo()));
			trnWklyProdShdlIf
					.setProdDayNo(String.valueOf(ordrDtls.getPrdDyNo()));
			trnWklyProdShdlIf.setOrdrQty(new BigDecimal(1));
			trnWklyProdShdlIf.setProdPlntCd(outputBeanTemp.getCol1()
					.substring(6, 8));
			trnWklyProdShdlIf.setLineClass(outputBeanTemp.getCol9());
			trnWklyProdShdlIf.setExNo(outputBeanTemp.getCol7());
			trnWklyProdShdlIf.setProdMthdCd(outputBeanTemp.getCol10());
			trnWklyProdShdlIf.setFrznTypeCd(outputBeanTemp.getCol18());
			trnWklyProdShdlIf.setSlsNoteNo(outputBeanTemp.getCol24());
			trnWklyProdShdlIf.setTyreMkrCd(outputBeanTemp.getCol14()
					.substring(0, 1));
			trnWklyProdShdlIf.setDealerLst(outputBeanTemp.getCol14()
					.substring(1, 2));
			trnWklyProdShdlIf.setOwnrMnl(outputBeanTemp.getCol14()
					.substring(2, 3));
			trnWklyProdShdlIf.setWrntyBklt(outputBeanTemp.getCol14()
					.substring(3, 4));
			trnWklyProdShdlIf.setBdyPrtctnCd(outputBeanTemp.getCol14()
					.substring(4, 5));
			trnWklyProdShdlIf.setOcfRegionCd(outputBeanTemp.getCol11());
			trnWklyProdShdlIf.setWkNoOfYear(String.valueOf(ordrDtls
					.getWeekNoOfYear()));
			trnWklyProdShdlIf.setFxdSymbl(outputBeanTemp.getCol23());
			trnWklyProdShdlIf.setWkFixWkNo(outputBeanTemp.getCol20()
					.substring(0, 2));
			trnWklyProdShdlIf.setWkFixSymbl(outputBeanTemp.getCol20()
					.substring(2, 3));
			trnWklyProdShdlIf
			.setIntrnlOrTrdFlag(outputBeanTemp.getCol3());
			trnWklyProdShdlIf.setOseiId(outputBeanTemp.getOselId());
			trnWklyProdShdlIf.setUpDtTm(outputBeanTemp.getCol30());
			trnWklyProdShdlIf.setCrtdBy(PDConstants.INTERFACE87);
			trnWklyProdShdlIf.setCrtdDt(CommonUtil.createTimeStamp());
			trnWklyProdShdlIf.setUpdtdBy(PDConstants.INTERFACE87);
			trnWklyProdShdlIf.setUpdtdDt(CommonUtil.createTimeStamp());
			trnWklyProdShdlIf.setId(trnWklyProdShdlIfPk);
			trnWklyProdShdlIfLst.add(trnWklyProdShdlIf);
		}
	}
	
	/**
	 * Method to get the maximum of sequence number from weekly production schedule if table
	 * 
	 * @return long
	 */
	public long getSqNoWklyProdSchdl(){
		String sqNOWklyProdStr = I000087QueryConstants.TRN_WKLY_PROD_SCHDL_SEQ_ID.toString();
		Query sqNOWklyProdQry = entityManager.createNativeQuery(sqNOWklyProdStr);
		List<Object> result = sqNOWklyProdQry.getResultList();
		return (result.get(0) == null) ? 0L : Long.parseLong(String.valueOf(result
				.get(0)).trim());
	}
}
