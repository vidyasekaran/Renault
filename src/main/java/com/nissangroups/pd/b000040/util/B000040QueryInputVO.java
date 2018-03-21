package com.nissangroups.pd.b000040.util;

import java.util.ArrayList;
import java.util.List;

import com.nissangroups.pd.b000040.bean.B000040OrderDetails;
import com.nissangroups.pd.b000040.bean.B000040ProdPrdDetails;

public class B000040QueryInputVO 
{

	//P0003 : Cong1:  PARAMETER MST (MONTHLY FIXED ORDER REQUIRED) ,Based on the value, getting in this method, Production Period Calculation will be performed.
	private String prodPeriodConfigValue;
	
	/* POR CD */
	private String porCD;
	/**
	 * P01 Order take base month
	 */
	private String ordrTakBseMnth;
	
	/**
	 * P01 Order take Base Week No
	 */
	private String ordrTakBseWkNo;
	
	/**
	 * P02 Order Production Period Horizon
	 */
	private Integer OrdrProdPrdHorizn;		
		
	
	/**
	 *P03 Production Period Details 
	 */
	private List<B000040ProdPrdDetails> prodPrdDtls = new ArrayList<B000040ProdPrdDetails>();
	
	/**
	 * P04.1 Line Class and Plant Cd 
	 */
	private boolean lneClsAndPlntCdFlg;
	
	/**
	 * P04.1 Constant Day Flag
	 */
	private boolean CnstDyFlg;
	
	/**
	 * P04.1 Constant Day Value
	 */
	private String CnstDyValue;
	
	/**
	 * P04.1 Suspended Flag
	 */
	private boolean suspendedFlag;
	
	private boolean nMonthSuspendedFlag;
	
	/**
	 * P04.1 Constant Space Value
	 *
	 */
	private String CnstSpaceValue;


	
	/**
	 * P04.1 Suspended Flag
	 */
	private boolean foreCastsuspendedFlag;
	

	/**
	 * P04.3 Frozen Symbol requiredFlag
	 */
	private boolean productionMthdCd;

	private String productionMthCnstValue;
	
	private boolean attachExNoMapSymbol;
	
	private List<String> exNoMapTrueCols;
	
	private List<String> exNoMapFalseCols;
	
	private boolean attachServiceParam;

	private boolean frozenSymbolRequiredFlag;
	/**
	 * UK OSEI ID
	 */
	private List<String> oseiId;
	
	/**
	 * Order details
	 */
	private List<B000040OrderDetails> orderDtls = new ArrayList<B000040OrderDetails>();

	public String getOrdrTakBseMnth() {
		return ordrTakBseMnth;
	}

	public void setOrdrTakBseMnth(String ordrTakBseMnth) {
		this.ordrTakBseMnth = ordrTakBseMnth;
	}

	public String getOrdrTakBseWkNo() {
		return ordrTakBseWkNo;
	}

	public void setOrdrTakBseWkNo(String ordrTakBseWkNo) {
		this.ordrTakBseWkNo = ordrTakBseWkNo;
	}

	public Integer getOrdrProdPrdHorizn() {
		return OrdrProdPrdHorizn;
	}

	public void setOrdrProdPrdHorizn(Integer ordrProdPrdHorizn) {
		OrdrProdPrdHorizn = ordrProdPrdHorizn;
	}

	public List<B000040ProdPrdDetails> getProdPrdDtls() {
		return prodPrdDtls;
	}

	public void setProdPrdDtls(List<B000040ProdPrdDetails> prodPrdDtls) {
		this.prodPrdDtls = prodPrdDtls;
	}

	public boolean isLneClsAndPlntCdFlg() {
		return lneClsAndPlntCdFlg;
	}

	public void setLneClsAndPlntCdFlg(boolean lneClsAndPlntCdFlg) {
		this.lneClsAndPlntCdFlg = lneClsAndPlntCdFlg;
	}

	public boolean isCnstDyFlg() {
		return CnstDyFlg;
	}

	public void setCnstDyFlg(boolean cnstDyFlg) {
		CnstDyFlg = cnstDyFlg;
	}
	

	public boolean isSuspendedFlag() {
		return suspendedFlag;
	}

	public void setSuspendedFlag(boolean suspendedFlag) {
		this.suspendedFlag = suspendedFlag;
	}

	public List<String> getOseiId() {
		return oseiId;
	}

	public void setOseiId(List<String> oseiId) {
		this.oseiId = oseiId;
	}

	public List<B000040OrderDetails> getOrderDtls() {
		return orderDtls;
	}

	public void setOrderDtls(List<B000040OrderDetails> orderDtls) {
		this.orderDtls = orderDtls;
	}

	

	public String getPorCD() {
		return porCD;
	}

	public void setPorCD(String porCD) {
		this.porCD = porCD;
	}
	
	public String getProdPeriodConfigValue() {
		return prodPeriodConfigValue;
	}

	public void setProdPeriodConfigValue(String prodPeriodConfigValue) {
		this.prodPeriodConfigValue = prodPeriodConfigValue;
	}
	
	public String getCnstSpaceValue() {
		return CnstSpaceValue;
	}

	public void setCnstSpaceValue(String cnstSpaceValue) {
		CnstSpaceValue = cnstSpaceValue;
	}

	

	public String getCnstDyValue() {
		return CnstDyValue;
	}

	public void setCnstDyValue(String cnstDyValue) {
		CnstDyValue = cnstDyValue;
	}
	
	public boolean isForeCastsuspendedFlag() {
		return foreCastsuspendedFlag;
	}

	public void setForeCastsuspendedFlag(boolean foreCastsuspendedFlag) {
		this.foreCastsuspendedFlag = foreCastsuspendedFlag;
	}
	
	public boolean isnMonthSuspendedFlag() {
		return nMonthSuspendedFlag;
	}

	public void setnMonthSuspendedFlag(boolean nMonthSuspendedFlag) {
		this.nMonthSuspendedFlag = nMonthSuspendedFlag;
	}
	
	public boolean isProductionMthdCd() {
		return productionMthdCd;
	}

	public void setProductionMthdCd(boolean productionMthdCd) {
		this.productionMthdCd = productionMthdCd;
	}
	
		
	
	
	public boolean isFrozenSymbolRequiredFlag() {
		return frozenSymbolRequiredFlag;
	}

	public void setFrozenSymbolRequiredFlag(boolean frozenSymbolRequiredFlag) {
		this.frozenSymbolRequiredFlag = frozenSymbolRequiredFlag;
	}

	public String getProductionMthCnstValue() {
		return productionMthCnstValue;
	}

	public void setProductionMthCnstValue(String productionMthCnstValue) {
		this.productionMthCnstValue = productionMthCnstValue;
	}

	public List<String> getExNoMapTrueCols() {
		return exNoMapTrueCols;
	}

	public void setExNoMapTrueCols(List<String> exNoMapTrueCols) {
		this.exNoMapTrueCols = exNoMapTrueCols;
	}

	public List<String> getExNoMapFalseCols() {
		return exNoMapFalseCols;
	}

	public void setExNoMapFalseCols(List<String> exNoMapFalseCols) {
		this.exNoMapFalseCols = exNoMapFalseCols;
	}

	public boolean isAttachServiceParam() {
		return attachServiceParam;
	}

	public void setAttachServiceParam(boolean attachServiceParam) {
		this.attachServiceParam = attachServiceParam;
	}

	public boolean isAttachExNoMapSymbol() {
		return attachExNoMapSymbol;
	}

	public void setAttachExNoMapSymbol(boolean attachExNoMapSymbol) {
		this.attachExNoMapSymbol = attachExNoMapSymbol;
	}
	
}
