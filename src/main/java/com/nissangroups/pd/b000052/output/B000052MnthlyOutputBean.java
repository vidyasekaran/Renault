package com.nissangroups.pd.b000052.output;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


public class B000052MnthlyOutputBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String porCd;
	private String prcsType;

	private String pltLnSmry;
	private String plntCd;
	private String lnCls;
	private String cnstntDay;
	private String featTypeCd;
	private String featTypeCd2;
	
	private int maxProdMnth;
	
	private int maxProdWk;
	
	private List<Object[]> p5List;



public List<Object[]> getP5List() {
		return p5List;
	}

	public void setP5List(List<Object[]> p5List) {
		this.p5List = p5List;
	}

private String ordrtkBasePd;
	
	
	

	public String getOrdrtkBasePd() {
	return ordrtkBasePd;
}

public void setOrdrtkBasePd(String ordrtkBasePd) {
	this.ordrtkBasePd = ordrtkBasePd;
}


	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getPrcsType() {
		return prcsType;
	}

	public void setPrcsType(String prcsType) {
		this.prcsType = prcsType;
	}

	public String getPltLnSmry() {
		return pltLnSmry;
	}

	public void setPltLnSmry(String pltLnSmry) {
		this.pltLnSmry = pltLnSmry;
	}

	public String getPlntCd() {
		return plntCd;
	}

	public void setPlntCd(String plntCd) {
		this.plntCd = plntCd;
	}

	public String getLnCls() {
		return lnCls;
	}

	public void setLnCls(String lnCls) {
		this.lnCls = lnCls;
	}

	public String getCnstntDay() {
		return cnstntDay;
	}

	public void setCnstntDay(String cnstntDay) {
		this.cnstntDay = cnstntDay;
	}

	public String getFeatTypeCd() {
		return featTypeCd;
	}

	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
	}

	public String getFeatTypeCd2() {
		return featTypeCd2;
	}

	public void setFeatTypeCd2(String featTypeCd2) {
		this.featTypeCd2 = featTypeCd2;
	}
	
	public int getMaxProdMnth() {
		return maxProdMnth;
	}

	public void setMaxProdMnth(int maxProdMnth) {
		this.maxProdMnth = maxProdMnth;
	}

	public int getMaxProdWk() {
		return maxProdWk;
	}

	public void setMaxProdWk(int maxProdWk) {
		this.maxProdWk = maxProdWk;
	}


	
	
}
