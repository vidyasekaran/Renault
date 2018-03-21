package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_REGIONAL_MNTHLY_OCF_LMT database table.
 * 
 */
@Entity
@Table(name="TRN_REGIONAL_MNTHLY_OCF_LMT")
@NamedQuery(name="TrnRegionalMnthlyOcfLmt.findAll", query="SELECT t FROM TrnRegionalMnthlyOcfLmt t")
public class TrnRegionalMnthlyOcfLmt implements Serializable {
	

	@EmbeddedId
	private TrnRegionalMnthlyOcfLmtPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="REGIONAL_OCF_LMT_QTY")
	private BigDecimal regionalOcfLmtQty;

	@Column(name="REGIONAL_OCF_USG_QTY")
	private BigDecimal regionalOcfUsgQty;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnRegionalMnthlyOcfLmt() {
	}

	public TrnRegionalMnthlyOcfLmtPK getId() {
		return this.id;
	}

	public void setId(TrnRegionalMnthlyOcfLmtPK id) {
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

	public BigDecimal getRegionalOcfLmtQty() {
		return this.regionalOcfLmtQty;
	}

	public void setRegionalOcfLmtQty(BigDecimal regionalOcfLmtQty) {
		this.regionalOcfLmtQty = regionalOcfLmtQty;
	}

	public BigDecimal getRegionalOcfUsgQty() {
		return this.regionalOcfUsgQty;
	}

	public void setRegionalOcfUsgQty(BigDecimal regionalOcfUsgQty) {
		this.regionalOcfUsgQty = regionalOcfUsgQty;
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