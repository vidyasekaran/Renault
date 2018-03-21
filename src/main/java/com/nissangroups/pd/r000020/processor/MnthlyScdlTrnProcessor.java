package com.nissangroups.pd.r000020.processor;

import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.TrnMnthProdShdl;
import com.nissangroups.pd.model.TrnMnthProdShdlIf;
import com.nissangroups.pd.model.TrnMnthProdShdlPK;
import com.nissangroups.pd.repository.MnthlySchdlIfTrnRepository;
import com.nissangroups.pd.repository.VldnRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;

public class MnthlyScdlTrnProcessor implements
		ItemProcessor<TrnMnthProdShdlIf, TrnMnthProdShdl> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(MnthlyScdlTrnProcessor.class);

	@Autowired(required = false)
	private VldnRepository vldnRepositoryObj;

	@Autowired(required = false)
	private MnthlySchdlIfTrnRepository mnthlySchdlIfTrnRepositoryObj;

	private String errorCd;

	@Override
	public TrnMnthProdShdl process(TrnMnthProdShdlIf input)
			throws PdApplicationException {

		LOG.info(STEP_AFTER_START);
		TrnMnthProdShdl trnMnthProdShdl = new TrnMnthProdShdl();
		TrnMnthProdShdlPK trnMnthProdShdlPk = new TrnMnthProdShdlPK();
		trnMnthProdShdlPk.setPorCd(input.getId().getPorCd());
		trnMnthProdShdlPk.setOrdrtkBaseMnth(input.getId().getOrdrtkBaseMnth());
		trnMnthProdShdlPk.setProdMnth(input.getId().getProdMnth());
		trnMnthProdShdlPk.setSeqId(input.getId().getSeqId());
		trnMnthProdShdl.setPotCd(input.getPotCd());
		trnMnthProdShdl.setOfflnPlanDate(input.getOfflnPlanDate());
		trnMnthProdShdl.setProdWkNo(input.getProdWkNo());
		trnMnthProdShdl.setProdDayNo(input.getProdDayNo().trim());
		trnMnthProdShdl.setOrdrQty(input.getOrdrQty());
		trnMnthProdShdl.setProdPlntCd(input.getProdPlntCd());
		trnMnthProdShdl.setLineClass(input.getLineClass());
		trnMnthProdShdl.setExNo(input.getExNo());
		trnMnthProdShdl.setProdMthdCd(input.getProdMthdCd());
		trnMnthProdShdl.setFrznTypeCd(input.getFrznTypeCd());
		trnMnthProdShdl.setSlsNoteNo(input.getSlsNoteNo());
		trnMnthProdShdl.setTyreMkrCd(input.getTyreMkrCd());
		trnMnthProdShdl.setDealerLst(input.getDealerLst());
		trnMnthProdShdl.setOwnrMnl(input.getOwnrMnl());
		trnMnthProdShdl.setWrntyBklt(input.getWrntyBklt());
		trnMnthProdShdl.setBdyPrtctnCd(input.getBdyPrtctnCd());
		trnMnthProdShdl.setOcfRegionCd(input.getOcfRegionCd());
		trnMnthProdShdl.setProdOrdrNo(input.getProdOrdrNo());
		trnMnthProdShdl.setWkNoOfYear(input.getWkNoOfYear());
		trnMnthProdShdl.setFxdSymbl(input.getFxdSymbl());
		trnMnthProdShdl.setPrtypeFlag(input.getPrtypeFlag());
		trnMnthProdShdl.setIntrnlOrTrdFlag(input.getIntrnlOrTrdFlag());
		trnMnthProdShdl.setCrtdBy(PDConstants.R000020);
		trnMnthProdShdl.setCrtdDt(CommonUtil.createTimeStamp());
		trnMnthProdShdl.setUpdtdBy(PDConstants.R000020);
		trnMnthProdShdl.setUpdtdDt(CommonUtil.createTimeStamp());
		trnMnthProdShdl.setProdFmlyCd(input.getProdFmlyCd());
		trnMnthProdShdl.setCarSrs(input.getCarSrs());
		trnMnthProdShdl.setBuyerCd(input.getBuyerCd());
		trnMnthProdShdl.setAppldMdlCd(input.getAppldMdlCd());
		trnMnthProdShdl.setPackCd(input.getPackCd());
		trnMnthProdShdl.setSpecDestnCd(input.getSpecDestnCd());
		trnMnthProdShdl.setAddSpecCd(input.getAddSpecCd());
		trnMnthProdShdl.setExtClrCd(input.getExtClrCd());
		trnMnthProdShdl.setIntClrCd(input.getIntClrCd());
		trnMnthProdShdl.setOseiId(input.getOseiId());
		trnMnthProdShdl.setId(trnMnthProdShdlPk);
		LOG.info(STEP_AFTER_END);
		return trnMnthProdShdl;
	}

}
