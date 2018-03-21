package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_REGIONAL_WKLY_OCF_LMT database table.
 * 
 */
@Embeddable
public class TrnRegionalWklyOcfLmtPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="FEAT_CD")
	private String featCd;
	
	@Column(name="PROD_DAY_NO")
	private String prodDayNo;
	
	@Column(name="PLANT_CD")
	private String plantCd;
	
	@Column(name="LINE_CLASS")
	private String lineClass;

	public String getPlantCd() {
		return plantCd;
	}
	public void setPlantCd(String plantCd) {
		this.plantCd = plantCd;
	}
	public String getLineClass() {
		return lineClass;
	}
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}
	public String getProdDayNo() {
		return prodDayNo;
	}
	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}
	public TrnRegionalWklyOcfLmtPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdMnth() {
		return this.prodMnth;
	}
	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}
	public String getProdWkNo() {
		return this.prodWkNo;
	}
	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}
	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}
	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnRegionalWklyOcfLmtPK)) {
			return false;
		}
		TrnRegionalWklyOcfLmtPK castOther = (TrnRegionalWklyOcfLmtPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodWkNo.equals(castOther.prodWkNo)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
			&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd)
			&& this.featCd.equals(castOther.featCd)
			&& this.prodDayNo.equals(castOther.prodDayNo)
			&& this.plantCd.equals(castOther.plantCd)
			&& this.lineClass.equals(castOther.lineClass);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodMnth.hashCode();
		hash = hash * prime + this.prodWkNo.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.prodDayNo.hashCode();
		hash = hash * prime + this.plantCd.hashCode();
		hash = hash * prime + this.lineClass.hashCode();
		
		return hash;
	}
}