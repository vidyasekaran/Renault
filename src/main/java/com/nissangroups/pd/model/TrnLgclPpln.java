package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import com.nissangroups.pd.util.CommonUtil;
import java.sql.Timestamp;


/**
 * The persistent class for the TRN_LGCL_PPLN database table.
 * 
 */
@Entity
@Table(name="TRN_LGCL_PPLN")
@NamedQuery(name="TrnLgclPpln.findAll", query="SELECT t FROM TrnLgclPpln t")
public class TrnLgclPpln implements Serializable {
	

	@Id
	@Column(name="VHCL_SEQ_ID")
	private String vhclSeqId;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="LGCL_PPLN_STAGE_CD")
	private String lgclPplnStageCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="MS_FXD_FLAG")
	private String msFxdFlag;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Column(name="ORDR_DEL_FLAG")
	private String ordrDelFlag;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="SLS_NOTE_NO")
	private String slsNoteNo;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="VIN_NO")
	private String vinNo;

	public TrnLgclPpln() {
	}

	public String getVhclSeqId() {
		return this.vhclSeqId;
	}

	public void setVhclSeqId(String vhclSeqId) {
		this.vhclSeqId = vhclSeqId;
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

	public String getLgclPplnStageCd() {
		return this.lgclPplnStageCd;
	}

	public void setLgclPplnStageCd(String lgclPplnStageCd) {
		this.lgclPplnStageCd = lgclPplnStageCd;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getMsFxdFlag() {
		return this.msFxdFlag;
	}

	public void setMsFxdFlag(String msFxdFlag) {
		this.msFxdFlag = msFxdFlag;
	}

	public String getOfflnPlanDate() {
		return this.offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public String getOrdrDelFlag() {
		return this.ordrDelFlag;
	}

	public void setOrdrDelFlag(String ordrDelFlag) {
		this.ordrDelFlag = ordrDelFlag;
	}

	public String getOseiId() {
		return this.oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getPotCd() {
		return this.potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdMnth() {
		return this.prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
	}

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}

	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	public String getProdPlntCd() {
		return this.prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getProdWkNo() {
		return this.prodWkNo;
	}

	public void setProdWkNo(String prodWkNo) {
		this.prodWkNo = prodWkNo;
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

	public String getVinNo() {
		return this.vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
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