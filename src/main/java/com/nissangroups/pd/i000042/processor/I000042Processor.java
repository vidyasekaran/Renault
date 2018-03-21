/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000042
 * Module          :Ordering
 * Process Outline :Interface To Receive Monthly Production Schedule from Plant
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015  	  z014029(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.i000042.processor;

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
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.MstWkNoClndr;
import com.nissangroups.pd.model.TrnMnthProdShdlIf;
import com.nissangroups.pd.model.TrnMnthProdShdlIfPK;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
import com.nissangroups.pd.util.QueryConstants;

/**
 * 
 * This is the processor class for interface I000042 using the extracted common
 * layer data. Data manipulation will be done.
 * 
 * @author z011479
 * 
 */
public class I000042Processor implements
		ItemProcessor<CmnInterfaceData, List<TrnMnthProdShdlIf>> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000042Processor.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable interface id. */
	private String interfaceId = null;

	private List<String> prdMnthLst = null;
	/** Variable seq no. */
	private String seqNo = null;

	/** Variable seq no. */
	private String porCd = null;

	/** Variable Feat Code. */
	private String featCd = null;

	private String crSrs = null;

	private String firstDayOfMnth = null;
	private String potCd = null;

	String ordrQty = null;

	String prdMnth = null;

	int seqId = 0;

	@Autowired(required = false)
	private CommonRepository cmnRep;

	CmnInterfaceData cmnInterfaceDataTemp = new CmnInterfaceData();
	QueryParamBean qryParamBean = new QueryParamBean();

	List<TrnMnthProdShdlIf> trnMnthProdShdlIfLst = null;

	Map<String, String> weekFirstDtMp = null;

	Map<String, WeeklyClndrBean> weekNoClMpTemp = null;

	boolean dltFlg = false;

	/**
	 * Before step.
	 * 
	 * @param stepExecution
	 *            the step execution
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
	 * This method is used to delete the old data in temp table and extract the
	 * pot cd for given por
	 * 
	 * @throws ParseException
	 */
	@BeforeProcess
	public void beforeProcess() throws ParseException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		if (!dltFlg) {
			getPrdMnth();
			extractWeekNoDtls();
			potCd = getpotCd();
			deleteOldData();
			dltFlg = true;
		}//Else condition will not occur above condition always true
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to extract the pot cd.
	 * 
	 * @return String pot cd
	 */
	public String getpotCd() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String potCd = null;
		potCd = cmnRep.getPotCd(porCd);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return potCd;
	}

	/**
	 * Delete data in temp table.
	 */
	public void deleteOldData() {
		cmnRep.deleteMnthlySchdlIf(porCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object) To
	 * Process the COMMON_INTERFACE data
	 */
	@Override
	public List<TrnMnthProdShdlIf> process(CmnInterfaceData cmnInterfaceData)
			throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		trnMnthProdShdlIfLst = new ArrayList<>();
		cmnInterfaceDataTemp = cmnInterfaceData;
		LOG.info(DATA_INSERTED_MSG);

		prdMnth = cmnInterfaceData.getCol1().substring(0, 6);
		ordrQty = cmnInterfaceData.getCol17();
		List<OrderQtyBean> dateDtls = splitOrdQty();
		for (OrderQtyBean dateDtlsStr : dateDtls) {
			addDataToEntity(dateDtlsStr);

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR + ":::::::::::"
				+ trnMnthProdShdlIfLst.size());
		return trnMnthProdShdlIfLst;
	}

	/**
	 * To get the count of row affected in Each Step.
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

		if (stepExecution.getReadCount() == 0) {
			LOG.info(M00003);
		} else if (stepExecution.getExitStatus().getExitCode().equals(FAILED)) {

			LOG.error(M00076.replace(PDConstants.ERROR_MESSAGE_1, stepExecution
					.getFailureExceptions().toString()));

		} else if (stepExecution.getReadCount() == stepExecution
				.getWriteCount()) {
			LOG.info(M00113);
		} else if (stepExecution.getReadCount() != stepExecution
				.getWriteCount()) {
			LOG.info(M00043);
		}
		//Else condition will not occur as the execution falls in anyone of the about condition
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to split the order quantity.
	 * 
	 * @return
	 * @throws ParseException
	 * @throws PdApplicationException
	 */
	public List<OrderQtyBean> splitOrdQty() throws ParseException,
			PdApplicationException {
		LOG.info(DOLLAR + "Split Order Quantity : Inside" + DOLLAR);
		List<OrderQtyBean> ordrQtyLst = new ArrayList<OrderQtyBean>();
		int spltLngth = 5;
		int mxQtyStart = 0;
		int mxQtyend = 5;
		int prdWeekNo = 1;
		int prdDyNo = 1;
		int nxtCount = 5;
		int count = 1;
		String two = PDConstants.INTERFACE_42_HRZN;
		String firstOfflnDt = weekFirstDtMp.get(prdMnth);
		String errorMessage = PDMessageConsants.M00120.replace(
				PDConstants.AMPERSAND_ONE, prdMnth);
		if (firstOfflnDt != null) {
			for (int i = 1; i <= ordrQty.length() / spltLngth; i++) {
				OrderQtyBean orderQtyBean = new OrderQtyBean();
				String singleOrdrQty = ordrQty.substring(mxQtyStart, mxQtyend);
				if (count > 7) {
					prdDyNo = 1;
					count = 1;
				}
				if (CommonUtil.stringtoInt(singleOrdrQty) != 0) {

					WeeklyClndrBean weeklyClndrBean = weekNoClMpTemp
							.get(firstOfflnDt);
					if (weeklyClndrBean == null) {

						LOG.error(errorMessage);
						throw new PdApplicationException(errorMessage);
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
				firstOfflnDt = CommonUtil.offlnDtCal(firstOfflnDt, two);
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
	 * This method is used to fetch the week no calendar details.
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
	 * This method is used to get the List of valid of production month.
	 * 
	 */
	public void getPrdMnth() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String prdMnthQuery = QueryConstants.I000042prdMnthQuery.toString();
		prdMnthLst = new ArrayList<>();
		Query query = entityManager.createQuery(prdMnthQuery);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_FILE_ID, interfaceId);
		query.setParameter(PDConstants.PRMTRT_INTERFACE_STATUS,
				PDConstants.INTERFACE_UNPROCESSED_STATUS);
		prdMnthLst = query.getResultList();
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to the split the orders in to single quantity and set
	 * in the entity object.
	 * 
	 * @param ordrDtls
	 */
	public void addDataToEntity(OrderQtyBean ordrDtls) {
		int ordrQty = CommonUtil.stringtoInt(ordrDtls.getMxQty());
		if (seqNo == null) {
			// get seq id from the oracle sequence
			seqNo = CommonUtil.bigDecimaltoString(cmnRep
					.getSqNoMnthlyProdSchdl());
		}
		for (int i = 1; i <= ordrQty; i++) {

			TrnMnthProdShdlIf trnMnthProdShdlIf = new TrnMnthProdShdlIf();
			TrnMnthProdShdlIfPK trnMnthProdShdlIfPk = new TrnMnthProdShdlIfPK();
			trnMnthProdShdlIfPk.setPorCd(porCd);
			trnMnthProdShdlIfPk.setSeqId(seqNo);
			trnMnthProdShdlIfPk.setProdMnth(cmnInterfaceDataTemp.getCol1()
					.substring(0, 6));
			trnMnthProdShdlIfPk.setOrdrtkBaseMnth(cmnInterfaceDataTemp
					.getCol20().substring(0, 6));
			trnMnthProdShdlIf.setProdPlntCd(cmnInterfaceDataTemp.getCol1()
					.substring(6, 8));
			trnMnthProdShdlIf.setPotCd(potCd);
			trnMnthProdShdlIf.setLineClass(cmnInterfaceDataTemp.getCol9());
			trnMnthProdShdlIf.setExNo(cmnInterfaceDataTemp.getCol7());
			trnMnthProdShdlIf.setProdMthdCd(cmnInterfaceDataTemp.getCol11());
			trnMnthProdShdlIf.setWkNoOfYear(String.valueOf(ordrDtls
					.getWeekNoOfYear()));
			trnMnthProdShdlIf.setFrznTypeCd(cmnInterfaceDataTemp.getCol19());
			trnMnthProdShdlIf.setSlsNoteNo(cmnInterfaceDataTemp.getCol25());
			trnMnthProdShdlIf.setTyreMkrCd(cmnInterfaceDataTemp.getCol15()
					.substring(0, 1));
			trnMnthProdShdlIf.setDealerLst(cmnInterfaceDataTemp.getCol15()
					.substring(1, 2));
			trnMnthProdShdlIf.setOwnrMnl(cmnInterfaceDataTemp.getCol15()
					.substring(2, 3));
			trnMnthProdShdlIf.setWrntyBklt(cmnInterfaceDataTemp.getCol15()
					.substring(3, 4));
			trnMnthProdShdlIf.setBdyPrtctnCd(cmnInterfaceDataTemp.getCol15()
					.substring(4, 5));
			trnMnthProdShdlIf.setOcfRegionCd(cmnInterfaceDataTemp.getCol12());
			trnMnthProdShdlIf.setOfflnPlanDate(ordrDtls.getOfflnDt());
			trnMnthProdShdlIf.setProdWkNo(String.valueOf(ordrDtls
					.getPrdWeekNo()));
			trnMnthProdShdlIf
					.setProdDayNo(String.valueOf(ordrDtls.getPrdDyNo()));
			trnMnthProdShdlIf.setFxdSymbl(cmnInterfaceDataTemp.getCol24());
			trnMnthProdShdlIf.setPrtypeFlag(cmnInterfaceDataTemp.getCol10());
			trnMnthProdShdlIf
					.setIntrnlOrTrdFlag(cmnInterfaceDataTemp.getCol3());
			trnMnthProdShdlIf.setProdFmlyCd(cmnInterfaceDataTemp.getCol5());
			trnMnthProdShdlIf.setCarSrs(cmnInterfaceDataTemp.getCol4());
			trnMnthProdShdlIf.setBuyerCd(cmnInterfaceDataTemp.getCol27());
			trnMnthProdShdlIf.setAppldMdlCd(cmnInterfaceDataTemp.getCol6()
					.substring(0, 13));
			trnMnthProdShdlIf.setPackCd(cmnInterfaceDataTemp.getCol6()
					.substring(13, 18));
			trnMnthProdShdlIf.setSpecDestnCd(cmnInterfaceDataTemp.getCol8());
			trnMnthProdShdlIf.setAddSpecCd(cmnInterfaceDataTemp.getCol26());
			trnMnthProdShdlIf.setExNo(cmnInterfaceDataTemp.getCol7());
			trnMnthProdShdlIf.setExtClrCd(cmnInterfaceDataTemp.getCol6()
					.substring(18, 21));
			trnMnthProdShdlIf.setIntClrCd(cmnInterfaceDataTemp.getCol6()
					.substring(21, 22));
			trnMnthProdShdlIf.setOrdrQty(new BigDecimal(1));
			trnMnthProdShdlIf.setCrtdBy(PDConstants.INTERFACE42);
			trnMnthProdShdlIf.setCrtdDt(CommonUtil.createTimeStamp());
			trnMnthProdShdlIf.setUpdtdBy(PDConstants.INTERFACE42);
			trnMnthProdShdlIf.setUpdtdDt(CommonUtil.createTimeStamp());
			trnMnthProdShdlIf.setId(trnMnthProdShdlIfPk);
			trnMnthProdShdlIfLst.add(trnMnthProdShdlIf);
			seqNo = String.valueOf(CommonUtil.stringtoInt(seqNo) + 1);
		}
	}
}
