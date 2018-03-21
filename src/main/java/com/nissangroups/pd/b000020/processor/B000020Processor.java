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
import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

/**
 * This class to process the data extracted from the Reader(Monthly Order Take
 * base Month)
 *
 * @author z011479
 */
public class B000020Processor implements
		ItemProcessor<MstMnthOrdrTakeBasePd, B000020Output> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000020Processor.class
			.getName());

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
	public B000020Output process(MstMnthOrdrTakeBasePd mstMnthOrdrTakeBasePd)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(mstMnthOrdrTakeBasePd.getId().getPorCd());
		B000020Output b000020Output = new B000020Output();
		stgCd = mstMnthOrdrTakeBasePd.getStageCd();
		ordTkBsMnth = mstMnthOrdrTakeBasePd.getId().getOrdrTakeBaseMnth();
		setProdStgCd();
		setParamOutput();
		
		b000020ParamOutput.setOrdTkBsMnth(mstMnthOrdrTakeBasePd.getId()
				.getOrdrTakeBaseMnth());
		String ordrTkBsMnth = b000020ParamOutput.getOrdTkBsMnth();
		LOG.info("Order Take Base Month ::::: " + ordrTkBsMnth);
		String prdMnthFrm = CommonUtil.prdMnthCal(ordrTkBsMnth,PDConstants.CONSTANT_4);
		LOG.info("Production Month ::::: " + prdMnthFrm);
		b000020ParamOutput.setPrdMnthFrm(prdMnthFrm);
		extractByrGrpAndCrSrsOutput = extractByrGrpAndCrSrsProcess
				.executeProcess(b000020ParamOutput);
		extractMstSpecOutput = extractMstSpecProcess
				.executeProcess(b000020ParamOutput);
		extractMstSpecOutputNw =  validSpecMstProcess.executeProcess(extractByrGrpAndCrSrsOutput,extractMstSpecOutput,b000020ParamOutput);
		extractFrcstBaseVolOutput = extractFrcstBaseVolProcess.executeProcess(
				b000020ParamOutput, extractMstSpecOutputNw);
		extractMstSpecOutputNw.setMstSpecDtlsAll(extractMstSpecOutput.getMstSpecDtlsAll());
		b000020Output
				.setObjExtractByrGrpAndCrSrsOutput(extractByrGrpAndCrSrsOutput);
		b000020Output.setExtractMstSpecOutput(extractMstSpecOutputNw);
		b000020Output.setExtractFrcstBaseVolOutput(extractFrcstBaseVolOutput);
		b000020Output.setB000020ParamOutput(b000020ParamOutput);
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return b000020Output;

	}

	public void setParamOutput() {
		b000020ParamOutput.setPorCd(porCd);
		b000020ParamOutput.setProductionStageCd(prdOrdrStgCd);
		b000020ParamOutput.setSeqId(seqId);
		b000020ParamOutput.setOrdTkBsMnth(ordTkBsMnth);
		b000020ParamOutput.setAvgCalBy(prmStgCd);
		b000020ParamOutput.setStgCode(prmStgCd);
		
	}

	/**
	 * This method is used to set the Production stage code based on the Input
	 * Stage code. Process P0001
	 * 
	 * @param stgCd
	 */
	public void setProdStgCd() {
		if (stgCd.equalsIgnoreCase(PDConstants.STG_CD_D1)
				|| stgCd.equalsIgnoreCase(PDConstants.STG_CD_D2)) {
			prdOrdrStgCd = PDConstants.TEN;
			prmStgCd = PDConstants.DRAFT;
			
		} else if (stgCd.equalsIgnoreCase(PDConstants.STG_CD_F1)
				|| stgCd.equalsIgnoreCase(PDConstants.STG_CD_F2)) {
			prdOrdrStgCd = PDConstants.TWENTY;
			prmStgCd = PDConstants.FINAL;
		}

	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	/** loading entities from the database */
	public EntityManager getEntityManager() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
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

}
