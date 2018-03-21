package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the MST_OSEI_DTL database table.
 * 
 */
@Entity
@Table(name="MST_OSEI_DTL")
@NamedQuery(name="MstOseiDtl.findAll", query="SELECT m FROM MstOseiDtl m")
public class MstOseiDtl implements Serializable {
	

	@EmbeddedId
	private MstOseiDtlPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="GSIS_APPLD_MDL_NO")
	private String gsisAppldMdlNo;

	@Column(name="GSIS_REGION_GRND")
	private String gsisRegionGrnd;

	@Column(name="LCL_NOTE")
	private String lclNote;

	@Column(name="MDFD_FLAG")
	private String mdfdFlag;

	@Column(name="MDL_YEAR")
	private String mdlYear;

	@Column(name="PCKGE_NAME")
	private String pckgeName;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="TOSUKO_BASE_PCK_CD")
	private String tosukoBasePckCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstEndItmSttsCd
	@ManyToOne
	@JoinColumn(name="END_ITM_STTS_CD")
	private MstEndItmSttsCd mstEndItmSttsCd;

	public MstOseiDtl() {
	}

	public MstOseiDtlPK getId() {
		return this.id;
	}

	public void setId(MstOseiDtlPK id) {
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

	public String getGsisAppldMdlNo() {
		return this.gsisAppldMdlNo;
	}

	public void setGsisAppldMdlNo(String gsisAppldMdlNo) {
		this.gsisAppldMdlNo = gsisAppldMdlNo;
	}

	public String getGsisRegionGrnd() {
		return this.gsisRegionGrnd;
	}

	public void setGsisRegionGrnd(String gsisRegionGrnd) {
		this.gsisRegionGrnd = gsisRegionGrnd;
	}

	public String getLclNote() {
		return this.lclNote;
	}

	public void setLclNote(String lclNote) {
		this.lclNote = lclNote;
	}

	public String getMdfdFlag() {
		return this.mdfdFlag;
	}

	public void setMdfdFlag(String mdfdFlag) {
		this.mdfdFlag = mdfdFlag;
	}

	public String getMdlYear() {
		return this.mdlYear;
	}

	public void setMdlYear(String mdlYear) {
		this.mdlYear = mdlYear;
	}

	public String getPckgeName() {
		return this.pckgeName;
	}

	public void setPckgeName(String pckgeName) {
		this.pckgeName = pckgeName;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getTosukoBasePckCd() {
		return this.tosukoBasePckCd;
	}

	public void setTosukoBasePckCd(String tosukoBasePckCd) {
		this.tosukoBasePckCd = tosukoBasePckCd;
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

	public MstEndItmSttsCd getMstEndItmSttsCd() {
		return this.mstEndItmSttsCd;
	}

	public void setMstEndItmSttsCd(MstEndItmSttsCd mstEndItmSttsCd) {
		this.mstEndItmSttsCd = mstEndItmSttsCd;
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