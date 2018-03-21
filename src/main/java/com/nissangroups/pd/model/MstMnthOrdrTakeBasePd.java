package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_MNTH_ORDR_TAKE_BASE_PD database table.
 * 
 */
@Entity
@Table(name="MST_MNTH_ORDR_TAKE_BASE_PD")
@NamedQuery(name="MstMnthOrdrTakeBasePd.findAll", query="SELECT m FROM MstMnthOrdrTakeBasePd m")
public class MstMnthOrdrTakeBasePd implements Serializable {
	

	@EmbeddedId
	private MstMnthOrdrTakeBasePdPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="STAGE_CD")
	private String stageCd;

	@Column(name="STAGE_STTS_CD")
	private String stageSttsCd;

	@Column(name="SYS_LCK_STTS_CD")
	private String sysLckSttsCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstMnthOrdrTakeBasePd() {
	}

	public MstMnthOrdrTakeBasePdPK getId() {
		return this.id;
	}

	public void setId(MstMnthOrdrTakeBasePdPK id) {
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

	public String getStageCd() {
		return this.stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public String getStageSttsCd() {
		return this.stageSttsCd;
	}

	public void setStageSttsCd(String stageSttsCd) {
		this.stageSttsCd = stageSttsCd;
	}

	public String getSysLckSttsCd() {
		return this.sysLckSttsCd;
	}

	public void setSysLckSttsCd(String sysLckSttsCd) {
		this.sysLckSttsCd = sysLckSttsCd;
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