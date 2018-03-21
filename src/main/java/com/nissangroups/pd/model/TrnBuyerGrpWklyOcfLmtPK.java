package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_BUYER_GRP_WKLY_OCF_LMT database table.
 * 
 */
@Embeddable
public class TrnBuyerGrpWklyOcfLmtPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="PROD_DAY_NO")
	private String prodDayNo;

	@Column(name="PLANT_CD")
	private String plantCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	public TrnBuyerGrpWklyOcfLmtPK() {
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
	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}
	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}
	public String getFeatCd() {
		return this.featCd;
	}
	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}
	public String getProdDayNo() {
		return this.prodDayNo;
	}
	public void setProdDayNo(String prodDayNo) {
		this.prodDayNo = prodDayNo;
	}
	public String getPlantCd() {
		return this.plantCd;
	}
	public void setPlantCd(String plantCd) {
		this.plantCd = plantCd;
	}
	public String getLineClass() {
		return this.lineClass;
	}
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnBuyerGrpWklyOcfLmtPK)) {
			return false;
		}
		TrnBuyerGrpWklyOcfLmtPK castOther = (TrnBuyerGrpWklyOcfLmtPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodMnth.equals(castOther.prodMnth)
			&& this.prodWkNo.equals(castOther.prodWkNo)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.buyerGrpCd.equals(castOther.buyerGrpCd)
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
		hash = hash * prime + this.buyerGrpCd.hashCode();
		hash = hash * prime + this.featCd.hashCode();
		hash = hash * prime + this.prodDayNo.hashCode();
		hash = hash * prime + this.plantCd.hashCode();
		hash = hash * prime + this.lineClass.hashCode();
		
		return hash;
	}
}