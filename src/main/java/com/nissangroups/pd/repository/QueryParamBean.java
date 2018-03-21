package com.nissangroups.pd.repository;

import java.util.List;

/**
 * This class is used to set the Query Parameters.
 * 
 * @author z011479
 *
 */
public class QueryParamBean {

	public QueryParamBean() {
	}

	/** The por cd. */
	private String porCd;

	/** The stage cd. */
	private String stageCd;

	/** The stage status cd. */
	private String stageStatusCd;

	/** The Exporter Flag */
	private String expFlg;

	/** The Order transmission Flag */
	private String OrdrTsmnFlg;

	/** The RHQ Flag */
	private String rHQFlg;

	/** The NSC Flag */
	private String nSCFlg;

	/** The Order take base month */
	private String ordrTkBsMnth;

	private String carSrs;

	private String ocfRgnCd;

	private String ocfFrmCd;

	private String byrGrpCd;

	private String featShrtDesc;

	private String featTypCd;

	private String oseiId;

	private String prodFmlyCd;

	private String byrCd;

	private String pckCd;

	private String specDstnCd;

	private String appldMdlCd;

	private String adntlSpecCd;

	private String extClrCd;

	private String intClrCd;

	private String prdMnth;

	private String prmtrCd;

	private String key1;

	private String key2;

	private List<String> prdStgCd;

	private String ordrTkBsWkNo;

	private String prdMnthWkNo;

	private String potCd;

	private String accptOrdrQty;

	private String adptDt;

	private String ablshDt;

	private String plntLneSummary;

	private String plntCd;

	private String lineClass;

	private String cnstDayNo;

	private String FeaturetypeCd;
	
	private String prdDayNo;
	

	private String prcsType;
	
	private String ordrTkBsPrd;
	
	private String featCd;
	
	private String ocfAllocFlg;
	
	private String qty;

	/* Added for B000014 */
	private String prdnStgCd;
	
	private String prdnMnthFrm;


	private String ocfByrGrpCd;

	private String prdnMnthTo;

	private int maxProdMnth ;
	private int maxProdWk ;

	private String prmtr4;

	private String limtQty;

	private String prmtr5;

	
	
	public String getLimtQty() {
		return limtQty;
	}

	public void setLimtQty(String limtQty) {
		this.limtQty = limtQty;
	}

	public int getMaxProdWk() {
		return maxProdWk;
	}

	public void setMaxProdWk(int maxProdWk) {
		this.maxProdWk = maxProdWk;
	}

	public int getMaxProdMnth() {
		return maxProdMnth;
	}

	public void setMaxProdMnth(int maxProdMnth) {
		this.maxProdMnth = maxProdMnth;
	}

	
	
	
	

	public String getOcfByrGrpCd() {
		return ocfByrGrpCd;
	}

	public void setOcfByrGrpCd(String ocfByrGrpCd) {
		this.ocfByrGrpCd = ocfByrGrpCd;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getOcfAllocFlg() {
		return ocfAllocFlg;
	}

	public void setOcfAllocFlg(String ocfAllocFlg) {
		this.ocfAllocFlg = ocfAllocFlg;
	}

	public String getFeatCd() {
		return featCd;
	}

	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public String getOrdrTkBsPrd() {
		return ordrTkBsPrd;
	}

	public void setOrdrTkBsPrd(String ordrTkBsPrd) {
		this.ordrTkBsPrd = ordrTkBsPrd;
	}

	public String getPrcsType() {
		return prcsType;
	}

	public void setPrcsType(String prcsType) {
		this.prcsType = prcsType;
	}

	/**
	 * Gets the prdnStgCd
	 *
	 * @return the prdnStgCd
	 */
	
	public String getPrdnStgCd() {
		return prdnStgCd;
	}

	/**
	 * Sets the prdnStgCd
	 *
	 * @param prdnStgCd the prdnStgCd to set
	 */
	
	public void setPrdnStgCd(String prdnStgCd) {
		this.prdnStgCd = prdnStgCd;
	}
	
	
	/**
	 * Gets the prdnMnthFrm
	 *
	 * @return the prdnMnthFrm
	 */
	
	public String getPrdnMnthFrm() {
		return prdnMnthFrm;
	}

	/**
	 * Sets the prdnMnthFrm
	 *
	 * @param prdnMnthFrm the prdnMnthFrm to set
	 */
	
	public void setPrdnMnthFrm(String prdnMnthFrm) {
		this.prdnMnthFrm = prdnMnthFrm;
	}

	/**
	 * Gets the prdnMnthTo
	 *
	 * @return the prdnMnthTo
	 */
	
	public String getPrdnMnthTo() {
		return prdnMnthTo;
	}

	/**
	 * Sets the prdnMnthTo
	 *
	 * @param prdnMnthTo the prdnMnthTo to set
	 */
	
	public void setPrdnMnthTo(String prdnMnthTo) {
		this.prdnMnthTo = prdnMnthTo;
	}
	
	/**
	 * Gets the prmtr4
	 *
	 * @return the prmtr4
	 */
	
	public String getPrmtr4() {
		return prmtr4;
	}

	/**
	 * Sets the prmtr4
	 *
	 * @param prmtr4 the prmtr4 to set
	 */
	
	public void setPrmtr4(String prmtr4) {
		this.prmtr4 = prmtr4;
	}

	/**
	 * Gets the prmtr5
	 *
	 * @return the prmtr5
	 */
	
	public String getPrmtr5() {
		return prmtr5;
	}

	/**
	 * Sets the prmtr5
	 *
	 * @param prmtr5 the prmtr5 to set
	 */
	
	public void setPrmtr5(String prmtr5) {
		this.prmtr5 = prmtr5;
	}
	
	 /* B000014 changes till here */

	public String getPrdDayNo() {
		return prdDayNo;
	}

	public void setPrdDayNo(String prdDayNo) {
		this.prdDayNo = prdDayNo;
	}

	public String getPlntLneSummary() {
		return plntLneSummary;
	}

	public void setPlntLneSummary(String plntLneSummary) {
		this.plntLneSummary = plntLneSummary;
	}

	public String getPlntCd() {
		return plntCd;
	}

	public void setPlntCd(String plntCd) {
		this.plntCd = plntCd;
	}

	public String getLineClass() {
		return lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getCnstDayNo() {
		return cnstDayNo;
	}

	public void setCnstDayNo(String cnstDayNo) {
		this.cnstDayNo = cnstDayNo;
	}

	public String getFeaturetypeCd() {
		return FeaturetypeCd;
	}

	public void setFeaturetypeCd(String featuretypeCd) {
		FeaturetypeCd = featuretypeCd;
	}

	public String getAdptDt() {
		return adptDt;
	}

	public void setAdptDt(String adptDt) {
		this.adptDt = adptDt;
	}

	public String getAblshDt() {
		return ablshDt;
	}

	public void setAblshDt(String ablshDt) {
		this.ablshDt = ablshDt;
	}

	public String getAccptOrdrQty() {
		return accptOrdrQty;
	}

	public void setAccptOrdrQty(String accptOrdrQty) {
		this.accptOrdrQty = accptOrdrQty;
	}

	public String getPotCd() {
		return potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getPrdMnthWkNo() {
		return prdMnthWkNo;
	}

	public void setPrdMnthWkNo(String prdMnthWkNo) {
		this.prdMnthWkNo = prdMnthWkNo;
	}

	public String getOrdrTkBsWkNo() {
		return ordrTkBsWkNo;
	}

	public void setOrdrTkBsWkNo(String ordrTkBsWkNo) {
		this.ordrTkBsWkNo = ordrTkBsWkNo;
	}

	public String getPrdMnth() {
		return prdMnth;
	}

	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public String getStageStatusCd() {
		return stageStatusCd;
	}

	public void setStageStatusCd(String stageStatusCd) {
		this.stageStatusCd = stageStatusCd;
	}

	public String getExpFlg() {
		return expFlg;
	}

	public void setExpFlg(String expFlg) {
		this.expFlg = expFlg;
	}

	public String getOrdrTsmnFlg() {
		return OrdrTsmnFlg;
	}

	public void setOrdrTsmnFlg(String ordrTsmnFlg) {
		OrdrTsmnFlg = ordrTsmnFlg;
	}

	public String getrHQFlg() {
		return rHQFlg;
	}

	public void setrHQFlg(String rHQFlg) {
		this.rHQFlg = rHQFlg;
	}

	public String getnSCFlg() {
		return nSCFlg;
	}

	public void setnSCFlg(String nSCFlg) {
		this.nSCFlg = nSCFlg;
	}

	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}

	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}

	public String getCarSrs() {
		return carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getOcfRgnCd() {
		return ocfRgnCd;
	}

	public void setOcfRgnCd(String ocfRgnCd) {
		this.ocfRgnCd = ocfRgnCd;
	}

	public String getOcfFrmCd() {
		return ocfFrmCd;
	}

	public void setOcfFrmCd(String ocfFrmCd) {
		this.ocfFrmCd = ocfFrmCd;
	}

	public String getByrGrpCd() {
		return byrGrpCd;
	}

	public void setByrGrpCd(String byrGrpCd) {
		this.byrGrpCd = byrGrpCd;
	}

	public String getFeatShrtDesc() {
		return featShrtDesc;
	}

	public void setFeatShrtDesc(String featShrtDesc) {
		this.featShrtDesc = featShrtDesc;
	}

	public String getFeatTypCd() {
		return featTypCd;
	}

	public void setFeatTypCd(String featTypCd) {
		this.featTypCd = featTypCd;
	}

	public String getOseiId() {
		return oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getProdFmlyCd() {
		return prodFmlyCd;
	}

	public void setProdFmlyCd(String prodFmlyCd) {
		this.prodFmlyCd = prodFmlyCd;
	}

	public String getByrCd() {
		return byrCd;
	}

	public void setByrCd(String byrCd) {
		this.byrCd = byrCd;
	}

	public String getAppldMdlCd() {
		return appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public String getPckCd() {
		return pckCd;
	}

	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}

	public String getSpecDstnCd() {
		return specDstnCd;
	}

	public void setSpecDstnCd(String specDstnCd) {
		this.specDstnCd = specDstnCd;
	}

	public String getAdntlSpecCd() {
		return adntlSpecCd;
	}

	public void setAdntlSpecCd(String adntlSpecCd) {
		this.adntlSpecCd = adntlSpecCd;
	}

	public String getExtClrCd() {
		return extClrCd;
	}

	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	public String getIntClrCd() {
		return intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getPrmtrCd() {
		return prmtrCd;
	}

	public String getKey1() {
		return key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setPrmtrCd(String prmtrCd) {
		this.prmtrCd = prmtrCd;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	/**
	 * @return the prdStgCd
	 */
	public List<String> getPrdStgCd() {
		return prdStgCd;
	}

	/**
	 * @param prdStgCd the prdStgCd to set
	 */
	public void setPrdStgCd(List<String> prdStgCd) {
		this.prdStgCd = prdStgCd;
	}



}
