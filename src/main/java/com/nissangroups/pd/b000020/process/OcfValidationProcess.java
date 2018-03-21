/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000020
 * Module          :O Ordering
 * Process Outline :Forecast Order Creation (N+3) Onwards (Draft & Final)
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 29-10-2015      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000020.process;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.B000020ReportDao;
import com.nissangroups.pd.repository.CommonRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class OcfValidationProcess {
	@Autowired(required = false)
	private CommonRepository cmnRep;

	@Autowired(required = false)
	private B000020ReportDao objB000020Report;

	Date date = new Date();
	Timestamp createDate = new Timestamp(date.getTime());
	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(OcfValidationProcess.class.getName());


	/**
	 * This method used to the execute the regional OCF limit update logic.
	 * 
	 * @param b000020ParamOutput
	 * @return
	 */
	public B000020ParamOutput executeProcess(
			B000020ParamOutput b000020ParamOutput) {
		LOG.info(DOLLAR +INSIDE_METHOD + DOLLAR);
		List<Object[]> dscntByrGrp = cmnRep.extractDistinctByrGrpCd();
		for (Object[] dscntByrGrpArry : dscntByrGrp) {
			if (b000020ParamOutput.getProductionStageCd().equalsIgnoreCase(
					PDConstants.TWENTY)) {

				Object[] byrGrpLvlOflmt = cmnRep.extractByrGrpMnthlyOcfLmtChck(
						dscntByrGrpArry, b000020ParamOutput);
				if (byrGrpLvlOflmt != null) {
					int byrGrpOcf=Integer.parseInt(byrGrpLvlOflmt[8].toString());
					if (byrGrpOcf == 0) {
						B000020ReportDao reportDao = new B000020ReportDao();
						String m236 = byrGrpLvlOflmt[0].toString() + "; "
								+ byrGrpLvlOflmt[4].toString() + " ;"
								+ byrGrpLvlOflmt[2].toString();
						reportDao.setPor(byrGrpLvlOflmt[0].toString());
						reportDao.setCarSrs(byrGrpLvlOflmt[3].toString());
						reportDao.setByrGrp(byrGrpLvlOflmt[4].toString());
						reportDao.setProdMnth(byrGrpLvlOflmt[2].toString());
						reportDao.setBatchId(PDConstants.BATCH_20_ID);
						reportDao.setOrdrStg(b000020ParamOutput
								.getProductionStageCd()+"-"+b000020ParamOutput.getStgCode());
						reportDao.setOrdrTkBSMnth(b000020ParamOutput
								.getOrdTkBsMnth());
						reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
						reportDao.setColorBrkdwnPrity(PDConstants.N_TO_N_PLUS_3);
						reportDao.setEiBrkPntPrity(PDConstants.N_TO_N_PLUS_3);
						reportDao.setErrId(PDConstants.ERR_M00236);
						reportDao.setErrType(PDConstants.PRMTR_WARNING);
						reportDao.setErrMsg(PDMessageConsants.M00235.replace(
								PDConstants.ERROR_MESSAGE_1,
								PDConstants.BATCH_20_ID).replace(
								PDConstants.ERROR_MESSAGE_2, m236));
						reportDao.setTime(createDate + "");
						objB000020Report.getReportList().add(reportDao);
					}
				}
			} else {
				Object[] nscFrcstOcflmt = cmnRep.extractNscFrcstLmtChck(
						dscntByrGrpArry, b000020ParamOutput);
				if (nscFrcstOcflmt != null) {
					String m236 = null;
					int nscFrcstOcf = Integer.parseInt(nscFrcstOcflmt[8]
							.toString());
					/**
					 * POR_CD,CAR_SRS,ORDR_TAKE_BASE_MNTH,PROD_MNTH,BUYER_GRP_CD,
					 * FEAT_TYPE_CD,OCF_FRME_CD,
					 * FEAT_CD,BUYER_GRP_OCF_USG_QTY,FRCST_VOL
					* */
					if (nscFrcstOcf == 0) {
						B000020ReportDao reportDao = new B000020ReportDao();
						m236 = nscFrcstOcflmt[0].toString() + "; "
								+ nscFrcstOcflmt[4].toString() + " ;"
								+ nscFrcstOcflmt[3].toString();
						reportDao.setPor(nscFrcstOcflmt[0].toString());
						reportDao.setCarSrs(nscFrcstOcflmt[1].toString());
						reportDao.setByrGrp(nscFrcstOcflmt[4].toString());
						reportDao.setProdMnth(nscFrcstOcflmt[3].toString());
						reportDao.setBatchId(PDConstants.BATCH_20_ID);
						reportDao.setOrdrStg(b000020ParamOutput
								.getProductionStageCd()+"-"+b000020ParamOutput.getStgCode());
						reportDao.setOrdrTkBSMnth(b000020ParamOutput
								.getOrdTkBsMnth());
						reportDao.setAvgCalBy(b000020ParamOutput.getAvgCalBy());
						reportDao.setColorBrkdwnPrity(PDConstants.IDL_MX);
						reportDao.setEiBrkPntPrity(PDConstants.IDL_MX);
						reportDao.setErrId(PDConstants.ERR_M00236);
						reportDao.setErrType(PDConstants.PRMTR_WARNING);
						reportDao.setErrMsg(PDMessageConsants.M00235.replace(
								PDConstants.ERROR_MESSAGE_1,
								PDConstants.BATCH_20_ID).replace(
								PDConstants.ERROR_MESSAGE_2, m236));
						reportDao.setTime(createDate + "");
						objB000020Report.getReportList().add(reportDao);
						CommonUtil.logMessage(PDMessageConsants.M00235,
								PDConstants.CONSTANT_V2, new String[] {
										PDConstants.BATCH_20_ID, m236 });
					}
				}

			}
		}
		LOG.info(DOLLAR +OUTSIDE_METHOD + DOLLAR);
		return b000020ParamOutput;
	}
}
