package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_BUYER_MNTHLY_OCF_USG database table.
 * 
 */
@Entity
@Table(name="TRN_BUYER_MNTHLY_OCF_USG")
@NamedQuery(name="TrnBuyerMnthlyOcfUsg.findAll", query="SELECT t FROM TrnBuyerMnthlyOcfUsg t")
public class TrnBuyerMnthlyOcfUsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnBuyerMnthlyOcfUsgPK id;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="BUYER_OCF_USG_QTY")
	private BigDecimal buyerOcfUsgQty;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnBuyerMnthlyOcfUsg() {
	}

	public TrnBuyerMnthlyOcfUsgPK getId() {
		return this.id;
	}

	public void setId(TrnBuyerMnthlyOcfUsgPK id) {
		this.id = id;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public BigDecimal getBuyerOcfUsgQty() {
		return this.buyerOcfUsgQty;
	}

	public void setBuyerOcfUsgQty(BigDecimal buyerOcfUsgQty) {
		this.buyerOcfUsgQty = buyerOcfUsgQty;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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

	public String getFeatTypeCd() {
		return this.featTypeCd;
	}

	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
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
	    this.setUpdtdDt(CommonUtil.createTimeStamp());
	}
	
	@PreUpdate
	void onPersist() {
	    this.setUpdtdDt(CommonUtil.createTimeStamp());
	}

}