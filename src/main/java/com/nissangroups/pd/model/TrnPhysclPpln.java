package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the TRN_PHYSCL_PPLN database table.
 * 
 */
@Entity
@Table(name="TRN_PHYSCL_PPLN")
@NamedQuery(name="TrnPhysclPpln.findAll", query="SELECT t FROM TrnPhysclPpln t")
public class TrnPhysclPpln implements Serializable {
	

	@EmbeddedId
	private TrnPhysclPplnPK id;

	@Column(name="ACTL_FINAL_OK_DATE")
	private String actlFinalOkDate;

	@Column(name="ACTL_FINAL_PASS_DATE")
	private String actlFinalPassDate;

	@Column(name="ACTL_METAL_OK_DATE")
	private String actlMetalOkDate;

	@Column(name="ACTL_OFFLN_DATE")
	private String actlOfflnDate;

	@Column(name="ACTL_PAINT_IN_DATE")
	private String actlPaintInDate;

	@Column(name="ACTL_PAINT_OK_DATE")
	private String actlPaintOkDate;

	@Column(name="ACTL_SETUP_DATE")
	private String actlSetupDate;

	@Column(name="ACTL_TRIM_IN_DATE")
	private String actlTrimInDate;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="CAR_SRS")
	private String carSrs;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="ENG_NO")
	private String engNo;

	@Column(name="ENG_TYPE")
	private String engType;

	@Column(name="EX_NO")
	private String exNo;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="INSPCTN_DATE")
	private String inspctnDate;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="INTRNL_OR_TRD_FLAG")
	private String intrnlOrTrdFlag;

	@Column(name="LGCL_PPLN_SEQ_ID")
	private String lgclPplnSeqId;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Column(name="PCK_CD")
	private String pckCd;

	@Column(name="PLNND_DLVRY_DATE")
	private String plnndDlvryDate;

	@Column(name="PLNND_FINAL_OK_DATE")
	private String plnndFinalOkDate;

	@Column(name="PLNND_FINAL_PASS_DATE")
	private String plnndFinalPassDate;

	@Column(name="PLNND_LDNG_DATE")
	private String plnndLdngDate;

	@Column(name="PLNND_METAL_OK_DATE")
	private String plnndMetalOkDate;

	@Column(name="PLNND_OFFLN_DATE")
	private String plnndOfflnDate;

	@Column(name="PLNND_PAINT_IN_DATE")
	private String plnndPaintInDate;

	@Column(name="PLNND_PAINT_OK_DATE")
	private String plnndPaintOkDate;

	@Column(name="PLNND_PORT_IN_DATE")
	private String plnndPortInDate;

	@Column(name="PLNND_SETUP_DATE")
	private String plnndSetupDate;

	@Column(name="PLNND_TRIM_IN_DATE")
	private String plnndTrimInDate;

	@Column(name="PROD_MNTH")
	private String prodMnth;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="PROD_WK_NO")
	private String prodWkNo;

	@Column(name="PRTYPE_VHCL_FLAG")
	private String prtypeVhclFlag;

	@Column(name="SCRPD_DATE")
	private String scrpdDate;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="VIN_ALLCT_FLAG")
	private String vinAllctFlag;

	public TrnPhysclPpln() {
	}

	public TrnPhysclPplnPK getId() {
		return this.id;
	}

	public void setId(TrnPhysclPplnPK id) {
		this.id = id;
	}

	public String getActlFinalOkDate() {
		return this.actlFinalOkDate;
	}

	public void setActlFinalOkDate(String actlFinalOkDate) {
		this.actlFinalOkDate = actlFinalOkDate;
	}

	public String getActlFinalPassDate() {
		return this.actlFinalPassDate;
	}

	public void setActlFinalPassDate(String actlFinalPassDate) {
		this.actlFinalPassDate = actlFinalPassDate;
	}

	public String getActlMetalOkDate() {
		return this.actlMetalOkDate;
	}

	public void setActlMetalOkDate(String actlMetalOkDate) {
		this.actlMetalOkDate = actlMetalOkDate;
	}

	public String getActlOfflnDate() {
		return this.actlOfflnDate;
	}

	public void setActlOfflnDate(String actlOfflnDate) {
		this.actlOfflnDate = actlOfflnDate;
	}

	public String getActlPaintInDate() {
		return this.actlPaintInDate;
	}

	public void setActlPaintInDate(String actlPaintInDate) {
		this.actlPaintInDate = actlPaintInDate;
	}

	public String getActlPaintOkDate() {
		return this.actlPaintOkDate;
	}

	public void setActlPaintOkDate(String actlPaintOkDate) {
		this.actlPaintOkDate = actlPaintOkDate;
	}

	public String getActlSetupDate() {
		return this.actlSetupDate;
	}

	public void setActlSetupDate(String actlSetupDate) {
		this.actlSetupDate = actlSetupDate;
	}

	public String getActlTrimInDate() {
		return this.actlTrimInDate;
	}

	public void setActlTrimInDate(String actlTrimInDate) {
		this.actlTrimInDate = actlTrimInDate;
	}

	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
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

	public String getEngNo() {
		return this.engNo;
	}

	public void setEngNo(String engNo) {
		this.engNo = engNo;
	}

	public String getEngType() {
		return this.engType;
	}

	public void setEngType(String engType) {
		this.engType = engType;
	}

	public String getExNo() {
		return this.exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getExtClrCd() {
		return this.extClrCd;
	}

	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	public String getInspctnDate() {
		return this.inspctnDate;
	}

	public void setInspctnDate(String inspctnDate) {
		this.inspctnDate = inspctnDate;
	}

	public String getIntClrCd() {
		return this.intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getIntrnlOrTrdFlag() {
		return this.intrnlOrTrdFlag;
	}

	public void setIntrnlOrTrdFlag(String intrnlOrTrdFlag) {
		this.intrnlOrTrdFlag = intrnlOrTrdFlag;
	}

	public String getLgclPplnSeqId() {
		return this.lgclPplnSeqId;
	}

	public void setLgclPplnSeqId(String lgclPplnSeqId) {
		this.lgclPplnSeqId = lgclPplnSeqId;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getOfflnPlanDate() {
		return this.offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public String getPckCd() {
		return this.pckCd;
	}

	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}

	public String getPlnndDlvryDate() {
		return this.plnndDlvryDate;
	}

	public void setPlnndDlvryDate(String plnndDlvryDate) {
		this.plnndDlvryDate = plnndDlvryDate;
	}

	public String getPlnndFinalOkDate() {
		return this.plnndFinalOkDate;
	}

	public void setPlnndFinalOkDate(String plnndFinalOkDate) {
		this.plnndFinalOkDate = plnndFinalOkDate;
	}

	public String getPlnndFinalPassDate() {
		return this.plnndFinalPassDate;
	}

	public void setPlnndFinalPassDate(String plnndFinalPassDate) {
		this.plnndFinalPassDate = plnndFinalPassDate;
	}

	public String getPlnndLdngDate() {
		return this.plnndLdngDate;
	}

	public void setPlnndLdngDate(String plnndLdngDate) {
		this.plnndLdngDate = plnndLdngDate;
	}

	public String getPlnndMetalOkDate() {
		return this.plnndMetalOkDate;
	}

	public void setPlnndMetalOkDate(String plnndMetalOkDate) {
		this.plnndMetalOkDate = plnndMetalOkDate;
	}

	public String getPlnndOfflnDate() {
		return this.plnndOfflnDate;
	}

	public void setPlnndOfflnDate(String plnndOfflnDate) {
		this.plnndOfflnDate = plnndOfflnDate;
	}

	public String getPlnndPaintInDate() {
		return this.plnndPaintInDate;
	}

	public void setPlnndPaintInDate(String plnndPaintInDate) {
		this.plnndPaintInDate = plnndPaintInDate;
	}

	public String getPlnndPaintOkDate() {
		return this.plnndPaintOkDate;
	}

	public void setPlnndPaintOkDate(String plnndPaintOkDate) {
		this.plnndPaintOkDate = plnndPaintOkDate;
	}

	public String getPlnndPortInDate() {
		return this.plnndPortInDate;
	}

	public void setPlnndPortInDate(String plnndPortInDate) {
		this.plnndPortInDate = plnndPortInDate;
	}

	public String getPlnndSetupDate() {
		return this.plnndSetupDate;
	}

	public void setPlnndSetupDate(String plnndSetupDate) {
		this.plnndSetupDate = plnndSetupDate;
	}

	public String getPlnndTrimInDate() {
		return this.plnndTrimInDate;
	}

	public void setPlnndTrimInDate(String plnndTrimInDate) {
		this.plnndTrimInDate = plnndTrimInDate;
	}

	public String getProdMnth() {
		return this.prodMnth;
	}

	public void setProdMnth(String prodMnth) {
		this.prodMnth = prodMnth;
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

	public String getPrtypeVhclFlag() {
		return this.prtypeVhclFlag;
	}

	public void setPrtypeVhclFlag(String prtypeVhclFlag) {
		this.prtypeVhclFlag = prtypeVhclFlag;
	}

	public String getScrpdDate() {
		return this.scrpdDate;
	}

	public void setScrpdDate(String scrpdDate) {
		this.scrpdDate = scrpdDate;
	}

	public String getSpecDestnCd() {
		return this.specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
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

	public String getVinAllctFlag() {
		return this.vinAllctFlag;
	}

	public void setVinAllctFlag(String vinAllctFlag) {
		this.vinAllctFlag = vinAllctFlag;
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