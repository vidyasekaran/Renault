package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_BUYER_GRP_WKLY_OCF_LMT database table.
 * 
 */
@Entity
@Table(name="TRN_BUYER_GRP_WKLY_OCF_LMT")
@NamedQuery(name="TrnBuyerGrpWklyOcfLmt.findAll", query="SELECT t FROM TrnBuyerGrpWklyOcfLmt t")
public class TrnBuyerGrpWklyOcfLmt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnBuyerGrpWklyOcfLmtPK id;

	@Column(name="BUYER_GRP_OCF_LMT_QTY")
	private BigDecimal buyerGrpOcfLmtQty;

	@Column(name="BUYER_GRP_OCF_USG_QTY")
	private BigDecimal buyerGrpOcfUsgQty;

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

	public TrnBuyerGrpWklyOcfLmt() {
	}

	public TrnBuyerGrpWklyOcfLmtPK getId() {
		return this.id;
	}

	public void setId(TrnBuyerGrpWklyOcfLmtPK id) {
		this.id = id;
	}

	public BigDecimal getBuyerGrpOcfLmtQty() {
		return this.buyerGrpOcfLmtQty;
	}

	public void setBuyerGrpOcfLmtQty(BigDecimal buyerGrpOcfLmtQty) {
		this.buyerGrpOcfLmtQty = buyerGrpOcfLmtQty;
	}

	public BigDecimal getBuyerGrpOcfUsgQty() {
		return this.buyerGrpOcfUsgQty;
	}

	public void setBuyerGrpOcfUsgQty(BigDecimal buyerGrpOcfUsgQty) {
		this.buyerGrpOcfUsgQty = buyerGrpOcfUsgQty;
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