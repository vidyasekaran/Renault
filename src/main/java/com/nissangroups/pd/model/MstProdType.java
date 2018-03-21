package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_PROD_TYPE database table.
 * 
 */
@Entity
@Table(name="MST_PROD_TYPE")
@NamedQuery(name="MstProdType.findAll", query="SELECT m FROM MstProdType m")
public class MstProdType implements Serializable {
	

	@EmbeddedId
	private MstProdTypePK id;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="CLR_CODE_CNDTN")
	private String clrCodeCndtn;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="OCF_REGION_CD")
	private String ocfRegionCd;

	@Column(name="PCK_SYMBL_CD_CNDTN")
	private String pckSymblCdCndtn;

	@Column(name="PRFX_NO")
	private String prfxNo;

	@Column(name="PRFX_YES")
	private String prfxYes;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

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

	public MstProdType() {
	}

	public MstProdTypePK getId() {
		return this.id;
	}

	public void setId(MstProdTypePK id) {
		this.id = id;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}

	public String getClrCodeCndtn() {
		return this.clrCodeCndtn;
	}

	public void setClrCodeCndtn(String clrCodeCndtn) {
		this.clrCodeCndtn = clrCodeCndtn;
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

	public String getPckSymblCdCndtn() {
		return this.pckSymblCdCndtn;
	}

	public void setPckSymblCdCndtn(String pckSymblCdCndtn) {
		this.pckSymblCdCndtn = pckSymblCdCndtn;
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

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
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