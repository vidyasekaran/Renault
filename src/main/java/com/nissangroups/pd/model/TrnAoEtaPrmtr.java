package com.nissangroups.pd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.nissangroups.pd.util.CommonUtil;


/**
 * The persistent class for the TRN_AO_ETA_PRMTR database table.
 * 
 */
@Entity
@Table(name="TRN_AO_ETA_PRMTR")
@NamedQuery(name="TrnAoEtaPrmtr.findAll", query="SELECT t FROM TrnAoEtaPrmtr t")
public class TrnAoEtaPrmtr implements Serializable {
	

	@EmbeddedId
	private TrnAoEtaPrmtrPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DATE_FRM_1")
	private String dateFrm1;

	@Column(name="DATE_FRM_2")
	private String dateFrm2;

	@Column(name="DATE_FRM_3")
	private String dateFrm3;

	@Column(name="DATE_FRM_4")
	private String dateFrm4;

	@Column(name="DATE_FRM_5")
	private String dateFrm5;

	@Column(name="DATE_TO_1")
	private String dateTo1;

	@Column(name="DATE_TO_2")
	private String dateTo2;

	@Column(name="DATE_TO_3")
	private String dateTo3;

	@Column(name="DATE_TO_4")
	private String dateTo4;

	@Column(name="DATE_TO_5")
	private String dateTo5;

	@Column(name="DESTN_CLASS_1")
	private String destnClass1;

	@Column(name="DESTN_CLASS_2")
	private String destnClass2;

	@Column(name="DESTN_CLASS_3")
	private String destnClass3;

	@Column(name="DESTN_CLASS_4")
	private String destnClass4;

	@Column(name="DESTN_CLASS_5")
	private String destnClass5;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="QTY_1")
	private BigDecimal qty1;

	@Column(name="QTY_2")
	private BigDecimal qty2;

	@Column(name="QTY_3")
	private BigDecimal qty3;

	@Column(name="QTY_4")
	private BigDecimal qty4;

	@Column(name="QTY_5")
	private BigDecimal qty5;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnAoEtaPrmtr() {
	}

	public TrnAoEtaPrmtrPK getId() {
		return this.id;
	}

	public void setId(TrnAoEtaPrmtrPK id) {
		this.id = id;
	}

	public String getCrtdBy() {
		return this.crtdBy;
	}

	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}

	public Timestamp getCrtdDt() {
		return this.crtdDt;
	}

	public void setCrtdDt(Timestamp crtdDt) {
		this.crtdDt = crtdDt;
	}

	public String getDateFrm1() {
		return this.dateFrm1;
	}

	public void setDateFrm1(String dateFrm1) {
		this.dateFrm1 = dateFrm1;
	}

	public String getDateFrm2() {
		return this.dateFrm2;
	}

	public void setDateFrm2(String dateFrm2) {
		this.dateFrm2 = dateFrm2;
	}

	public String getDateFrm3() {
		return this.dateFrm3;
	}

	public void setDateFrm3(String dateFrm3) {
		this.dateFrm3 = dateFrm3;
	}

	public String getDateFrm4() {
		return this.dateFrm4;
	}

	public void setDateFrm4(String dateFrm4) {
		this.dateFrm4 = dateFrm4;
	}

	public String getDateFrm5() {
		return this.dateFrm5;
	}

	public void setDateFrm5(String dateFrm5) {
		this.dateFrm5 = dateFrm5;
	}

	public String getDateTo1() {
		return this.dateTo1;
	}

	public void setDateTo1(String dateTo1) {
		this.dateTo1 = dateTo1;
	}

	public String getDateTo2() {
		return this.dateTo2;
	}

	public void setDateTo2(String dateTo2) {
		this.dateTo2 = dateTo2;
	}

	public String getDateTo3() {
		return this.dateTo3;
	}

	public void setDateTo3(String dateTo3) {
		this.dateTo3 = dateTo3;
	}

	public String getDateTo4() {
		return this.dateTo4;
	}

	public void setDateTo4(String dateTo4) {
		this.dateTo4 = dateTo4;
	}

	public String getDateTo5() {
		return this.dateTo5;
	}

	public void setDateTo5(String dateTo5) {
		this.dateTo5 = dateTo5;
	}

	public String getDestnClass1() {
		return this.destnClass1;
	}

	public void setDestnClass1(String destnClass1) {
		this.destnClass1 = destnClass1;
	}

	public String getDestnClass2() {
		return this.destnClass2;
	}

	public void setDestnClass2(String destnClass2) {
		this.destnClass2 = destnClass2;
	}

	public String getDestnClass3() {
		return this.destnClass3;
	}

	public void setDestnClass3(String destnClass3) {
		this.destnClass3 = destnClass3;
	}

	public String getDestnClass4() {
		return this.destnClass4;
	}

	public void setDestnClass4(String destnClass4) {
		this.destnClass4 = destnClass4;
	}

	public String getDestnClass5() {
		return this.destnClass5;
	}

	public void setDestnClass5(String destnClass5) {
		this.destnClass5 = destnClass5;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getProdFmyCd() {
		return this.prodFmyCd;
	}

	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}

	public BigDecimal getQty1() {
		return this.qty1;
	}

	public void setQty1(BigDecimal qty1) {
		this.qty1 = qty1;
	}

	public BigDecimal getQty2() {
		return this.qty2;
	}

	public void setQty2(BigDecimal qty2) {
		this.qty2 = qty2;
	}

	public BigDecimal getQty3() {
		return this.qty3;
	}

	public void setQty3(BigDecimal qty3) {
		this.qty3 = qty3;
	}

	public BigDecimal getQty4() {
		return this.qty4;
	}

	public void setQty4(BigDecimal qty4) {
		this.qty4 = qty4;
	}

	public BigDecimal getQty5() {
		return this.qty5;
	}

	public void setQty5(BigDecimal qty5) {
		this.qty5 = qty5;
	}

	public String getUpdtdBy() {
		return this.updtdBy;
	}

	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}

	public Timestamp getUpdtdDt() {
		return this.updtdDt;
	}

	public void setUpdtdDt(Timestamp updtdDt) {
		this.updtdDt = updtdDt;
	}

	
	@PrePersist
	void onCreate() {
	    this.setCrtdDt(CommonUtil.createTimeStamp());
	}
	
	@PreUpdate
	void onPersist() {
	    this.setUpdtdDt(CommonUtil.createTimeStamp());
	}

}