package com.nissangroups.pd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.nissangroups.pd.util.CommonUtil;


/**
 * The persistent class for the TRN_MNTHLY_OCF database table.
 * 
 */
@Entity
@Table(name="TRN_MNTHLY_OCF")
@NamedQuery(name="TrnMnthlyOcf.findAll", query="SELECT t FROM TrnMnthlyOcf t")
public class TrnMnthlyOcf implements Serializable {
	

	@EmbeddedId
	private TrnMnthlyOcfPK id;

	@Column(name="CRTD_BY", updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT", updatable=false)
	private Timestamp crtdDt;
	
	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="OCF_MAX_QTY")
	private BigDecimal ocfMaxQty;

	@Column(name="OCF_STND_QTY")
	private BigDecimal ocfStndQty;

	@Column(name="PROCESS_STTS_CD")
	private String processSttsCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnMnthlyOcf() {
	}

	public TrnMnthlyOcfPK getId() {
		return this.id;
	}

	public void setId(TrnMnthlyOcfPK id) {
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

	public String getFeatCd() {
		return this.featCd;
	}

	public void setFeatCd(String featCd) {
		this.featCd = featCd;
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

	public BigDecimal getOcfMaxQty() {
		return this.ocfMaxQty;
	}

	public void setOcfMaxQty(BigDecimal ocfMaxQty) {
		this.ocfMaxQty = ocfMaxQty;
	}

	public BigDecimal getOcfStndQty() {
		return this.ocfStndQty;
	}

	public void setOcfStndQty(BigDecimal ocfStndQty) {
		this.ocfStndQty = ocfStndQty;
	}

	public String getProcessSttsCd() {
		return this.processSttsCd;
	}

	public void setProcessSttsCd(String processSttsCd) {
		this.processSttsCd = processSttsCd;
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