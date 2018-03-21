package com.nissangroups.pd.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.nissangroups.pd.util.CommonUtil;


/**
 * The persistent class for the MST_JOB_STERAM database table.
 * 
 */
@Entity
@Table(name="MST_JOB_STERAM")
@NamedQuery(name="MstJobSteram.findAll", query="SELECT m FROM MstJobSteram m")
public class MstJobSteram implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="JOBSTRM_SEQ_ID")
	private long jobstrmSeqId;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CALC_BASEDATE_FLG")
	private String calcBasedateFlg;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DURTN_FLG")
	private String durtnFlg;

	@Column(name="IF_CATGRY")
	private String ifCatgry;

	@Column(name="IF_ID")
	private String ifId;

	@Column(name="IF_JOBSTRM_FLG")
	private String ifJobstrmFlg;

	@Column(name="IF_RCEVR")
	private String ifRcevr;

	@Column(name="IF_SNDR")
	private String ifSndr;

	@Column(name="JOBSTRM_DESC")
	private String jobstrmDesc;

	@Column(name="JOBSTRM_DESC_SHRT_VER")
	private String jobstrmDescShrtVer;

	@Column(name="ORDRTK_BASEPERIOD_TYPE_CD")
	private String ordrtkBaseperiodTypeCd;

	@Column(name="ORDRTK_JOBSTRM_FLG")
	private String ordrtkJobstrmFlg;

	private String por;

	@Column(name="SHDL_CALC_PTTRN")
	private String shdlCalcPttrn;

	
	@Column(name="ST_TIME")
	private Timestamp stTime;

	@Column(name="STAGE_CD")
	private String stageCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstJobSteram() {
	}

	public long getJobstrmSeqId() {
		return this.jobstrmSeqId;
	}

	public void setJobstrmSeqId(long jobstrmSeqId) {
		this.jobstrmSeqId = jobstrmSeqId;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public String getCalcBasedateFlg() {
		return this.calcBasedateFlg;
	}

	public void setCalcBasedateFlg(String calcBasedateFlg) {
		this.calcBasedateFlg = calcBasedateFlg;
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

	public String getDurtnFlg() {
		return this.durtnFlg;
	}

	public void setDurtnFlg(String durtnFlg) {
		this.durtnFlg = durtnFlg;
	}

	public String getIfCatgry() {
		return this.ifCatgry;
	}

	public void setIfCatgry(String ifCatgry) {
		this.ifCatgry = ifCatgry;
	}

	public String getIfId() {
		return this.ifId;
	}

	public void setIfId(String ifId) {
		this.ifId = ifId;
	}

	public String getIfJobstrmFlg() {
		return this.ifJobstrmFlg;
	}

	public void setIfJobstrmFlg(String ifJobstrmFlg) {
		this.ifJobstrmFlg = ifJobstrmFlg;
	}

	public String getIfRcevr() {
		return this.ifRcevr;
	}

	public void setIfRcevr(String ifRcevr) {
		this.ifRcevr = ifRcevr;
	}

	public String getIfSndr() {
		return this.ifSndr;
	}

	public void setIfSndr(String ifSndr) {
		this.ifSndr = ifSndr;
	}

	public String getJobstrmDesc() {
		return this.jobstrmDesc;
	}

	public void setJobstrmDesc(String jobstrmDesc) {
		this.jobstrmDesc = jobstrmDesc;
	}

	public String getJobstrmDescShrtVer() {
		return this.jobstrmDescShrtVer;
	}

	public void setJobstrmDescShrtVer(String jobstrmDescShrtVer) {
		this.jobstrmDescShrtVer = jobstrmDescShrtVer;
	}

	public String getOrdrtkBaseperiodTypeCd() {
		return this.ordrtkBaseperiodTypeCd;
	}

	public void setOrdrtkBaseperiodTypeCd(String ordrtkBaseperiodTypeCd) {
		this.ordrtkBaseperiodTypeCd = ordrtkBaseperiodTypeCd;
	}

	public String getOrdrtkJobstrmFlg() {
		return this.ordrtkJobstrmFlg;
	}

	public void setOrdrtkJobstrmFlg(String ordrtkJobstrmFlg) {
		this.ordrtkJobstrmFlg = ordrtkJobstrmFlg;
	}

	public String getPor() {
		return this.por;
	}

	public void setPor(String por) {
		this.por = por;
	}

	public String getShdlCalcPttrn() {
		return this.shdlCalcPttrn;
	}

	public void setShdlCalcPttrn(String shdlCalcPttrn) {
		this.shdlCalcPttrn = shdlCalcPttrn;
	}

	public Timestamp getStTime() {
		return this.stTime;
	}

	public void setStTime(Timestamp stTime) {
		this.stTime = stTime;
	}

	public String getStageCd() {
		return this.stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
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