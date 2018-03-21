/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-R000020
 * Module          :MONTHLY ORDERING
 * Process Outline :Check whether the duplicate records exist in the Monthly Order Interface TRN 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z011479(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.r000020.processor;

import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.r000020.bean.R000020ExNoBean;
import com.nissangroups.pd.r000020.bean.R000020InputParamBean;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class ExNoVldnProcessor implements
		ItemProcessor<R000020InputParamBean, R000020InputParamBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ExNoVldnProcessor.class);

	@Autowired(required = false)
	private VldnRepository vldnRepositoryObj;

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepositoryObj;

	private String errorCd;

	List<MstPrmtr> chckExNoExistsFrDiffCombInMnthly;

	List<MstPrmtr> exNoRngList;

	CommonUtil commonUtil = new CommonUtil();
	String warnCd = PDConstants.PRMTR_R20_WRN_CD;

	@Override
	public R000020InputParamBean process(R000020InputParamBean input)
			throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		QueryParamBean qryParamBn = new QueryParamBean();
		qryParamBn.setOrdrTkBsMnth(input.getOrdrTkBsMnth());
		qryParamBn.setOcfRgnCd(input.getOcfRgnCd());

		qryParamBn.setPrmtrCd(PDConstants.PRMTR_CD_EX_NO_VALIDATION);
		qryParamBn.setKey1(input.getPorCd());
		qryParamBn.setOrdrTkBsMnth(input.getOrdrTkBsMnth());
		List<MstPrmtr> exFlg = mnthlySchdlIfTrnRepositoryObj
				.getExNoVldnFlg(qryParamBn);

		if (exFlg.isEmpty() || exFlg.get(0).getVal1() == null) {
			String errMsg = PDMessageConsants.M00160;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", PDConstants.R000020);
			errPrm.put("2", PDConstants.REPORT_EX_NO);
			errPrm.put("3", input.getPorCd());
			errPrm.put("4", PDConstants.PRMTR_CD_MST_EX_NO);

			LOG.error(commonUtil.getlogErrorMessage(errMsg, errPrm));
			CommonUtil.stopBatch();
		}
		if (exFlg.get(0).getVal1().equalsIgnoreCase("1")) {

			List<R000020ExNoBean> invldExNoList = vldnRepositoryObj
					.fetchInVldExNoR00020(input.getPorCd(),
							input.getTableName(), qryParamBn);
			List<Integer> invldExNoListDiffCombNw = vldnRepositoryObj
					.fetchInVldExNoWtDiffCombn(input.getPorCd(),
							input.getTableName(), qryParamBn);
			extractExNoRng();
			boolean chckExnoExistsFrDiffCombFlg = false;
			boolean chckSameCombtExistsInMnthPrdOrdFlg = false;
			boolean chckExNoExistsFrDiffCombInMnthlyFlg = false;
			Set<String> UniqSet = new HashSet<String>();
			for (R000020ExNoBean invldExNo : invldExNoList) {
				boolean instFlg = false;
				String unqKy = invldExNo.getPorCd() + invldExNo.getPrdMnth()
						+ invldExNo.getOeiByrId() + invldExNo.getPotCd();
				boolean chckSameCombnExistsFlg = invldExNoListDiffCombNw
						.contains(Integer.valueOf(invldExNo.getSeqId().trim()));
				if (!chckSameCombnExistsFlg) {
					chckExnoExistsFrDiffCombFlg = chckExnoExistsFrDiffComb(invldExNo);
				}
				chckSameCombtExistsInMnthPrdOrdFlg = chckSameCombtExistsInMnthPrdOrd(invldExNo);
				if (!chckSameCombtExistsInMnthPrdOrdFlg) {
					chckExNoExistsFrDiffCombInMnthlyFlg = chckExNoExistsFrDiffCombInMnthly(invldExNo);
				}
				if (chckSameCombtExistsInMnthPrdOrdFlg
						|| chckExNoExistsFrDiffCombInMnthlyFlg) {
					// Error data already exists in the Monthly Order Trn.

					mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
							CommonUtil.stringtoInt(invldExNo.getSeqId()),
							warnCd, PDMessageConsants.M00270, input);
					String errMsg = PDMessageConsants.M00270;

					LOG.error(errMsg);
					CommonUtil.stopBatch();
				} else if (chckSameCombnExistsFlg) {
					updateExNo(invldExNo, input);
					updateMxIndicator(invldExNo);
				} else if (chckExnoExistsFrDiffCombFlg) {
					// Delete and insert the new combination
					
					if (UniqSet.add(unqKy)) {
						instFlg = true;
						dltOldData(invldExNo);
					}
					insertExNo(invldExNo, input, instFlg);
					updateMxIndicator(invldExNo);
				}else{
					if (UniqSet.add(unqKy)) {
						instFlg = true;
						dltOldData(invldExNo);
					}
					insertExNo(invldExNo, input, instFlg);
					updateMxIndicator(invldExNo);
				}

			}
		}
		LOG.info(STEP_AFTER_END);

		return input;
	}

	public void updateExNo(R000020ExNoBean invldExNo,
			R000020InputParamBean input) throws PdApplicationException {
		LOG.info(STEP_AFTER_START);
		boolean exNoVldnFlg = mnthlySchdlIfTrnRepositoryObj
				.validateExNo(invldExNo);

		String purposeCd = null;
		int exNo = CommonUtil.stringtoInt(invldExNo.getExNo().substring(1, 5));
		if (exNoVldnFlg) {
			for (MstPrmtr mstdata : exNoRngList) {
				int key1 = CommonUtil.stringtoInt(mstdata.getVal1().toString());
				int key2 = CommonUtil.stringtoInt(mstdata.getVal2().toString());
				if (exNo >= key1 && exNo <= key2) {
					switch (mstdata.getId().getKey2()) {
					case PDConstants.MONTHLY:
						purposeCd = "1";
						break;
					case PDConstants.WEEKLY:
						purposeCd = "2";
						break;
					default:
						purposeCd = "3";
						break;

					}
				}
			}

			String errorMessage = PDMessageConsants.M00253;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", invldExNo.getExNo());
			errorMessage = commonUtil.getlogErrorMessage(errorMessage, errPrm);
			LOG.info(":::::::Purpose Code" + purposeCd);
			vldnRepositoryObj.updateExNo(invldExNo, purposeCd);
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
					CommonUtil.stringtoInt(invldExNo.getSeqId()), warnCd,
					errorMessage, input);

		} else {
			String M00254 = "M00254 : Order has been eliminated because  "
					+ invldExNo.getExNo() + " does not exist in PARAMETER MST ";
			LOG.error(M00254);
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
					CommonUtil.stringtoInt(invldExNo.getSeqId()), errorCd,
					M00254, input);

		}
		LOG.info(STEP_AFTER_END);
	}

	public boolean chckExnoExistsFrDiffComb(R000020ExNoBean invldExNo)
			throws PdApplicationException {
		LOG.info(STEP_AFTER_START);
		List<Object[]> invldExNoListDiffComb = vldnRepositoryObj
				.fetchInVldExNoFrDiffCombn(invldExNo);
		if (invldExNoListDiffComb.isEmpty()) {
			return false;
		}
		LOG.info(STEP_AFTER_END);
		return true;
	}

	public boolean chckSameCombtExistsInMnthPrdOrd(R000020ExNoBean invldExNo)
			throws PdApplicationNonFatalException {
		LOG.info(STEP_AFTER_START);
		List<Object[]> chckSameCombtExistsInMnthPrdOrd = vldnRepositoryObj
				.chckSameCombtExistsInMnthPrdOrd(invldExNo);
		if (chckSameCombtExistsInMnthPrdOrd.isEmpty()) {
			return false;
		}
		LOG.info(STEP_AFTER_END);
		return true;
	}

	public boolean chckExNoExistsFrDiffCombInMnthly(R000020ExNoBean invldExNo)
			throws PdApplicationNonFatalException {
		LOG.info(STEP_AFTER_START);
		List<Object[]> chckExNoExistsFrDiffCombInMnthlyData = vldnRepositoryObj
				.chckExNoExistsFrDiffCombInMnthly(invldExNo);
		if (chckExNoExistsFrDiffCombInMnthlyData.isEmpty()) {
			return false;
		}
		LOG.info(STEP_AFTER_END);
		return false;
	}

	public void dltOldData(R000020ExNoBean r000020ExNoBean) {
		LOG.info(STEP_AFTER_START);
		vldnRepositoryObj.deleteOldExNoData(r000020ExNoBean);
		LOG.info(STEP_AFTER_END);
	}

	public void insertExNo(R000020ExNoBean invldExNo,
			R000020InputParamBean input, boolean insrtFlg)
			throws PdApplicationException {
		LOG.info(STEP_AFTER_START);
		boolean exNoVldnFlg = mnthlySchdlIfTrnRepositoryObj
				.validateExNo(invldExNo);

		String purposeCd = null;
		int exNo = CommonUtil.stringtoInt(invldExNo.getExNo().substring(1, 5));
		if (exNoVldnFlg) {
			for (MstPrmtr mstdata : exNoRngList) {
				int key1 = CommonUtil.stringtoInt(mstdata.getVal1().toString());
				int key2 = CommonUtil.stringtoInt(mstdata.getVal2().toString());
				if (exNo >= key1 && exNo <= key2) {
					switch (mstdata.getId().getKey2()) {
					case PDConstants.MONTHLY:
						purposeCd = "1";
						break;
					case PDConstants.WEEKLY:
						purposeCd = "2";
						break;
					default:
						purposeCd = "3";
						break;

					}
				}
			}
			String errorMessage = PDMessageConsants.M00252;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", invldExNo.getExNo());
			errorMessage = commonUtil.getlogErrorMessage(errorMessage, errPrm);
			if (insrtFlg) {
				vldnRepositoryObj.insrtExNoData(invldExNo, purposeCd);
			}
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
					CommonUtil.stringtoInt(invldExNo.getSeqId()), warnCd,
					errorMessage, input);
		} else {
			String errMsg = PDMessageConsants.M00254;
			Map<String, String> errPrm = new HashMap<String, String>();
			errPrm.put("1", PDConstants.REPORT_EX_NO);
			errMsg = commonUtil.getlogErrorMessage(errMsg, errPrm);
			mnthlySchdlIfTrnRepositoryObj.updateErrorCd(
					CommonUtil.stringtoInt(invldExNo.getSeqId()), errorCd,
					errMsg, input);

		}
		LOG.info(STEP_AFTER_END);
	}

	/**
	 * This method is used to extract the Ex no Range.
	 */
	public void extractExNoRng() {
		LOG.info(STEP_AFTER_START);
		exNoRngList = vldnRepositoryObj.extractExNoRng();
		LOG.info(STEP_AFTER_END);
	}

	public void updateMxIndicator(R000020ExNoBean exNoBean) {
		 LOG.info(STEP_AFTER_START);
		vldnRepositoryObj.updateMxIndicator(exNoBean);
		LOG.info(STEP_AFTER_END);
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

}
