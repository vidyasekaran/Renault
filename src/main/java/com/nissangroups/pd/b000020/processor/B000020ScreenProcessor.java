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
package com.nissangroups.pd.b000020.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000020.output.B000020Output;
import com.nissangroups.pd.b000020.output.B000020ParamOutput;
import com.nissangroups.pd.b000020.output.ExtractByrGrpAndCrSrsOutput;
import com.nissangroups.pd.b000020.output.ExtractFrcstBaseVolOutput;
import com.nissangroups.pd.b000020.output.ExtractMstSpecOutput;
import com.nissangroups.pd.b000020.process.ExtractByrGrpAndCrSrsProcess;
import com.nissangroups.pd.b000020.process.ExtractFrcstBaseVolProcess;
import com.nissangroups.pd.b000020.process.ExtractMstSpecProcess;
import com.nissangroups.pd.b000020.process.ValidSpecMstProcess;
import com.nissangroups.pd.model.MnthlyBatchProcessStt;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class to process the data extracted from the Reader(Monthly Order Take
 * base Month) if the Batch Triggered from the screen
 *
 * @author z011479
 */

public class B000020ScreenProcessor implements
		ItemProcessor<MnthlyBatchProcessStt, B000020Output> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(B000020ScreenProcessor.class.getName());

	/** Variable entity manager. */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable ordr take base mnth. */
	private String ordTkBsMnth = null;

	/** Variable POR CD. */
	private String porCd;

	/** Variable Stage Code. */
	private String stgCd;

	/** Variable Production Stage Code. */
	private String prdOrdrStgCd;

	/** Variable Screen Only Flag. */
	private String scrnOnlyFlg;

	/** Variable Sequence Id. */
	private String seqId;

	/** Variable Sequence Id. */
	private String batchId;

	private String prmStgCd;

	@Autowired(required = false)
	private ExtractByrGrpAndCrSrsProcess extractByrGrpAndCrSrsProcess;

	@Autowired(required = false)
	private ExtractMstSpecProcess extractMstSpecProcess;

	@Autowired(required = false)
	private ExtractFrcstBaseVolProcess extractFrcstBaseVolProcess;

	@Autowired(required = false)
	private ValidSpecMstProcess validSpecMstProcess;

	ExtractByrGrpAndCrSrsOutput extractByrGrpAndCrSrsOutput = new ExtractByrGrpAndCrSrsOutput();
	ExtractFrcstBaseVolOutput extractFrcstBaseVolOutput = new ExtractFrcstBaseVolOutput();
	B000020ParamOutput b000020ParamOutput = new B000020ParamOutput();
	ExtractMstSpecOutput extractMstSpecOutput = new ExtractMstSpecOutput();
	ExtractMstSpecOutput extractMstSpecOutputNw = new ExtractMstSpecOutput();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public B000020Output process(MnthlyBatchProcessStt mnthlyBatchProcessStt)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String prdMnthFrm = null;
		LOG.info(mnthlyBatchProcessStt.getId().getPorCd());
		B000020Output b000020Output = new B000020Output();
		stgCd = mnthlyBatchProcessStt.getPrmtr1();
		ordTkBsMnth = mnthlyBatchProcessStt.getId().getOrdrtkBaseMnth();
		seqId = mnthlyBatchProcessStt.getId().getSeqId();
		if (stgCd == null || ordTkBsMnth == null || seqId == null) {
			LOG.error(" Data is null " + "stgCd : " + stgCd + "ordTkBsMnth : "
					+ ordTkBsMnth + "seqId : " + seqId);
		}
		setProdStgCd();
		setParamOutput();
		b000020ParamOutput.setOrdTkBsMnth(ordTkBsMnth);
		b000020ParamOutput
				.setAvgCalBy(mnthlyBatchProcessStt.getPrmtr2().trim());
		b000020ParamOutput.setOcfRgnCd(mnthlyBatchProcessStt.getOcfRegionCd());
		b000020ParamOutput.setOcfByrGrp(mnthlyBatchProcessStt
				.getOcfBuyerGrpCd().trim());
		if (mnthlyBatchProcessStt.getCarSrs().trim()
				.equalsIgnoreCase(PDConstants.SCREEN_ALL)) {
			b000020ParamOutput.setCrSrs(mnthlyBatchProcessStt.getCarSrs()
					.trim());
		} else {
			b000020ParamOutput.setCrSrs(mnthlyBatchProcessStt.getCarSrs());
		}

		b000020ParamOutput.setByrGrp(mnthlyBatchProcessStt.getBuyerGrpCd()
				.trim());
		b000020ParamOutput.setScrnOnlyFlg(scrnOnlyFlg);
		String ordrTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		prdMnthFrm = mnthlyBatchProcessStt.getProdMnthFrm();
		String prdMnthTo = mnthlyBatchProcessStt.getProdMnthTo();
		if (prdMnthFrm == null || prdMnthFrm == ""
				|| prdMnthFrm == PDConstants.SCREEN_ALL) {
			prdMnthFrm = CommonUtil.prdMnthCal(ordrTkBsMnth,
					PDConstants.CONSTANT_4);
		}
		b000020ParamOutput.setPrdMnthFrm(prdMnthFrm);
		if (prdMnthTo != PDConstants.SCREEN_ALL) {
			b000020ParamOutput.setPrdMnthTo(mnthlyBatchProcessStt
					.getProdMnthTo());
		}

		extractByrGrpAndCrSrsOutput = extractByrGrpAndCrSrsProcess
				.executeProcess(b000020ParamOutput);
		extractMstSpecOutput = extractMstSpecProcess
				.executeProcess(b000020ParamOutput);
		extractMstSpecOutputNw = validSpecMstProcess.executeProcess(
				extractByrGrpAndCrSrsOutput, extractMstSpecOutput,
				b000020ParamOutput);
		extractFrcstBaseVolOutput = extractFrcstBaseVolProcess.executeProcess(
				b000020ParamOutput, extractMstSpecOutputNw);
		extractMstSpecOutputNw.setMstSpecDtlsAll(extractMstSpecOutput
				.getMstSpecDtlsAll());
		b000020Output
				.setObjExtractByrGrpAndCrSrsOutput(extractByrGrpAndCrSrsOutput);
		b000020Output.setExtractMstSpecOutput(extractMstSpecOutputNw);
		b000020Output.setExtractFrcstBaseVolOutput(extractFrcstBaseVolOutput);
		b000020Output.setB000020ParamOutput(b000020ParamOutput);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return b000020Output;

	}

	public void setParamOutput() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		b000020ParamOutput.setPorCd(porCd);
		b000020ParamOutput.setProductionStageCd(prdOrdrStgCd);
		b000020ParamOutput.setSeqId(seqId);
		b000020ParamOutput.setOrdTkBsMnth(ordTkBsMnth);
		b000020ParamOutput.setStgCode(prmStgCd);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is used to set the Production stage code based on the Input
	 * Stage code. Process P0001
	 * 
	 * @param stgCd
	 */
	public void setProdStgCd() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (stgCd.equalsIgnoreCase(PDConstants.STG_CD_D1)
				|| stgCd.equalsIgnoreCase(PDConstants.STG_CD_D2)) {
			prdOrdrStgCd = PDConstants.TEN;
			prmStgCd = PDConstants.DRAFT;
		} else if (stgCd.equalsIgnoreCase(PDConstants.STG_CD_F1)
				|| stgCd.equalsIgnoreCase(PDConstants.STG_CD_F2)) {
			prdOrdrStgCd = PDConstants.TWENTY;
			prmStgCd = PDConstants.FINAL;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	/** loading entities from the database */
	public EntityManager getEntityManager() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return entityManager;

	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager
	 *            the new entity manager
	 */
	/** loading entities from the database */
	public void setEntityManager(EntityManager entityManager) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.entityManager = entityManager;
	}

	public String getOrdTkBsMnth() {
		return ordTkBsMnth;
	}

	public void setOrdTkBsMnth(String ordTkBsMnth) {
		this.ordTkBsMnth = ordTkBsMnth;
	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getStgCd() {
		return stgCd;
	}

	public void setStgCd(String stgCd) {
		this.stgCd = stgCd;
	}

	public String getPrdOrdrStgCd() {
		return prdOrdrStgCd;
	}

	public void setPrdOrdrStgCd(String prdOrdrStgCd) {
		this.prdOrdrStgCd = prdOrdrStgCd;
	}

	public String getScrnOnlyFlg() {
		return scrnOnlyFlg;
	}

	public void setScrnOnlyFlg(String scrnOnlyFlg) {
		this.scrnOnlyFlg = scrnOnlyFlg;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

}
