package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the TRN_LTST_MST_SHDL database table.
 * 
 */
@Entity
@Table(name="TRN_LTST_MST_SHDL")
@NamedQuery(name="TrnLtstMstShdl.findAll", query="SELECT t FROM TrnLtstMstShdl t")
public class TrnLtstMstShdl implements Serializable {
	

	@EmbeddedId
	private TrnLtstMstShdlPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="MS_FIXED_FLAG")
	private String msFixedFlag;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Column(name="ORDR_BASE_BASE_PRD_TYPE")
	private String ordrBaseBasePrdType;

	@Column(name="ORDR_BASE_BASE_WK_NO")
	private String ordrBaseBaseWkNo;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="SLS_NOTE_NO")
	private String slsNoteNo;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnLtstMstShdl() {
	}

	public TrnLtstMstShdlPK getId() {
		return this.id;
	}

	public void setId(TrnLtstMstShdlPK id) {
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

	public String getExNo() {
		return this.exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getMsFixedFlag() {
		return this.msFixedFlag;
	}

	public void setMsFixedFlag(String msFixedFlag) {
		this.msFixedFlag = msFixedFlag;
	}

	public String getOfflnPlanDate() {
		return this.offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public String getOrdrBaseBasePrdType() {
		return this.ordrBaseBasePrdType;
	}

	public void setOrdrBaseBasePrdType(String ordrBaseBasePrdType) {
		this.ordrBaseBasePrdType = ordrBaseBasePrdType;
	}

	public String getOrdrBaseBaseWkNo() {
		return this.ordrBaseBaseWkNo;
	}

	public void setOrdrBaseBaseWkNo(String ordrBaseBaseWkNo) {
		this.ordrBaseBaseWkNo = ordrBaseBaseWkNo;
	}

	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}

	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public String getProdPlntCd() {
		return this.prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getSlsNoteNo() {
		return this.slsNoteNo;
	}

	public void setSlsNoteNo(String slsNoteNo) {
		this.slsNoteNo = slsNoteNo;
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