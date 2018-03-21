package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the MST_OCF_CLASSFTN database table.
 * 
 */
@Entity
@Table(name="MST_OCF_CLASSFTN")
@NamedQuery(name="MstOcfClassftn.findAll", query="SELECT m FROM MstOcfClassftn m")
public class MstOcfClassftn implements Serializable {
	

	@EmbeddedId
	private MstOcfClassftnPK id;

	@Column(name="CLR_CD_CNDTN")
	private String clrCdCndtn;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="ERR_FLAG")
	private String errFlag;

	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="LNG_DESC")
	private String lngDesc;

	@Column(name="MNL_OCF_FLAG")
	private String mnlOcfFlag;

	@Column(name="OCF_ABLSH_DATE")
	private String ocfAblshDate;

	@Column(name="OCF_ADPT_DATE")
	private String ocfAdptDate;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="OPTN_SPEC_CD_CNDTN")
	private String optnSpecCdCndtn;

	@Column(name="PRFX_NO")
	private String prfxNo;

	@Column(name="PRFX_YES")
	private String prfxYes;

	@Column(name="SFFX_NO")
	private String sffxNo;

	@Column(name="SFFX_YES")
	private String sffxYes;

	@Column(name="SPEC_DESTN_CD_CNDTN")
	private String specDestnCdCndtn;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="VS_MNTR_FLAG")
	private String vsMntrFlag;
	
	@Column(name="OCF_DEL_FLAG")
	private String ocfDelFlag;
	
	
	public String getOcfDelFlag() {
		return ocfDelFlag;
	}

	public void setOcfDelFlag(String ocfDelFlag) {
		this.ocfDelFlag = ocfDelFlag;
	}

	@PrePersist
	public void creationTime() {
		Date date = new Date();
		crtdDt = new Timestamp(date.getTime());
	}

	@PreUpdate
	public void updationTime() {
		Date date = new Date();
		updtdDt = new Timestamp(date.getTime());
	}

	public MstOcfClassftn() {
	}

	public MstOcfClassftnPK getId() {
		return this.id;
	}

	public void setId(MstOcfClassftnPK id) {
		this.id = id;
	}

	public String getClrCdCndtn() {
		return this.clrCdCndtn;
	}

	public void setClrCdCndtn(String clrCdCndtn) {
		this.clrCdCndtn = clrCdCndtn;
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

	public String getFeatTypeCd() {
		return this.featTypeCd;
	}

	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
	}

	public String getLngDesc() {
		return this.lngDesc;
	}

	public void setLngDesc(String lngDesc) {
		this.lngDesc = lngDesc;
	}

	public String getMnlOcfFlag() {
		return this.mnlOcfFlag;
	}

	public void setMnlOcfFlag(String mnlOcfFlag) {
		this.mnlOcfFlag = mnlOcfFlag;
	}

	public String getOcfAblshDate() {
		return this.ocfAblshDate;
	}

	public void setOcfAblshDate(String ocfAblshDate) {
		this.ocfAblshDate = ocfAblshDate;
	}

	public String getOcfAdptDate() {
		return this.ocfAdptDate;
	}

	public void setOcfAdptDate(String ocfAdptDate) {
		this.ocfAdptDate = ocfAdptDate;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
	}

	public String getOcfRegionCd() {
		return this.ocfRegionCd;
	}

	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	public String getOptnSpecCdCndtn() {
		return this.optnSpecCdCndtn;
	}

	public void setOptnSpecCdCndtn(String optnSpecCdCndtn) {
		this.optnSpecCdCndtn = optnSpecCdCndtn;
	}

	public String getPrfxNo() {
		return this.prfxNo;
	}

	public void setPrfxNo(String prfxNo) {
		this.prfxNo = prfxNo;
	}

	public String getPrfxYes() {
		return this.prfxYes;
	}

	public void setPrfxYes(String prfxYes) {
		this.prfxYes = prfxYes;
	}

	public String getSffxNo() {
		return this.sffxNo;
	}

	public void setSffxNo(String sffxNo) {
		this.sffxNo = sffxNo;
	}

	public String getSffxYes() {
		return this.sffxYes;
	}

	public void setSffxYes(String sffxYes) {
		this.sffxYes = sffxYes;
	}

	public String getSpecDestnCdCndtn() {
		return this.specDestnCdCndtn;
	}

	public void setSpecDestnCdCndtn(String specDestnCdCndtn) {
		this.specDestnCdCndtn = specDestnCdCndtn;
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

	public String getVsMntrFlag() {
		return this.vsMntrFlag;
	}

	public void setVsMntrFlag(String vsMntrFlag) {
		this.vsMntrFlag = vsMntrFlag;
	}
   

}