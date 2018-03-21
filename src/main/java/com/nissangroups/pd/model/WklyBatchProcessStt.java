package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the WKLY_BATCH_PROCESS_STTS database table.
 * 
 */
@Entity
@Table(name="WKLY_BATCH_PROCESS_STTS")
@NamedQuery(name="WklyBatchProcessStt.findAll", query="SELECT w FROM WklyBatchProcessStt w")
public class WklyBatchProcessStt implements Serializable {
	

	@EmbeddedId
	private WklyBatchProcessSttPK id;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAR_SRS")
	private String carSrs;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="ERR_FLAG")
	private String errFlag;

	@Column(name="ERR_RPRT_SEQ_ID")
	private String errRprtSeqId;

	@Column(name="OCF_BUYER_GRP_CD")
	private String ocfBuyerGrpCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	private String prmtr1;

	private String prmtr2;

	private String prmtr3;

	private String prmtr4;

	private String prmtr5;

	@Column(name="PROCESS_STTS_FLAG")
	private String processSttsFlag;

	@Column(name="PROD_PRD_FRM")
	private String prodPrdFrm;

	@Column(name="PROD_PRD_TO")
	private String prodPrdTo;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public WklyBatchProcessStt() {
	}

	public WklyBatchProcessSttPK getId() {
		return this.id;
	}

	public void setId(WklyBatchProcessSttPK id) {
		this.id = id;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
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

	public String getErrFlag() {
		return this.errFlag;
	}

	public void setErrFlag(String errFlag) {
		this.errFlag = errFlag;
	}

	public String getErrRprtSeqId() {
		return this.errRprtSeqId;
	}

	public void setErrRprtSeqId(String errRprtSeqId) {
		this.errRprtSeqId = errRprtSeqId;
	}

	public String getOcfBuyerGrpCd() {
		return this.ocfBuyerGrpCd;
	}

	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getPrmtr1() {
		return this.prmtr1;
	}

	public void setPrmtr1(String prmtr1) {
		this.prmtr1 = prmtr1;
	}

	public String getPrmtr2() {
		return this.prmtr2;
	}

	public void setPrmtr2(String prmtr2) {
		this.prmtr2 = prmtr2;
	}

	public String getPrmtr3() {
		return this.prmtr3;
	}

	public void setPrmtr3(String prmtr3) {
		this.prmtr3 = prmtr3;
	}

	public String getPrmtr4() {
		return this.prmtr4;
	}

	public void setPrmtr4(String prmtr4) {
		this.prmtr4 = prmtr4;
	}

	public String getPrmtr5() {
		return this.prmtr5;
	}

	public void setPrmtr5(String prmtr5) {
		this.prmtr5 = prmtr5;
	}

	public String getProcessSttsFlag() {
		return this.processSttsFlag;
	}

	public void setProcessSttsFlag(String processSttsFlag) {
		this.processSttsFlag = processSttsFlag;
	}

	public String getProdPrdFrm() {
		return this.prodPrdFrm;
	}

	public void setProdPrdFrm(String prodPrdFrm) {
		this.prodPrdFrm = prodPrdFrm;
	}

	public String getProdPrdTo() {
		return this.prodPrdTo;
	}

	public void setProdPrdTo(String prodPrdTo) {
		this.prodPrdTo = prodPrdTo;
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