package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_FRZN database table.
 * 
 */
@Entity
@Table(name="MST_FRZN")
@NamedQuery(name="MstFrzn.findAll", query="SELECT m FROM MstFrzn m")
public class MstFrzn implements Serializable {
	

	@EmbeddedId
	private MstFrznPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FRZN_CLR_CODE_CNDTN")
	private String frznClrCodeCndtn;

	@Column(name="FRZN_DEL_FLAG")
	private String frznDelFlag;

	@Column(name="FRZN_SPEC_DESTN_CD_CNDTN")
	private String frznSpecDestnCdCndtn;

	@Column(name="MNL_FRZN_FLAG")
	private String mnlFrznFlag;

	@Column(name="PRFX_NO")
	private String prfxNo;

	@Column(name="PRFX_YES")
	private String prfxYes;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="SFFX_NO")
	private String sffxNo;

	@Column(name="SFFX_YES")
	private String sffxYes;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to FrznType
	@ManyToOne
	@JoinColumn(name="FRZN_TYPE_CD", insertable=false,updatable=false)
	private FrznType frznType;

	public MstFrzn() {
	}

	public MstFrznPK getId() {
		return this.id;
	}

	public void setId(MstFrznPK id) {
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

	public String getFrznClrCodeCndtn() {
		return this.frznClrCodeCndtn;
	}

	public void setFrznClrCodeCndtn(String frznClrCodeCndtn) {
		this.frznClrCodeCndtn = frznClrCodeCndtn;
	}

	public String getFrznDelFlag() {
		return this.frznDelFlag;
	}

	public void setFrznDelFlag(String frznDelFlag) {
		this.frznDelFlag = frznDelFlag;
	}

	public String getFrznSpecDestnCdCndtn() {
		return this.frznSpecDestnCdCndtn;
	}

	public void setFrznSpecDestnCdCndtn(String frznSpecDestnCdCndtn) {
		this.frznSpecDestnCdCndtn = frznSpecDestnCdCndtn;
	}

	public String getMnlFrznFlag() {
		return this.mnlFrznFlag;
	}

	public void setMnlFrznFlag(String mnlFrznFlag) {
		this.mnlFrznFlag = mnlFrznFlag;
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

	public String getProdFmyCd() {
		return this.prodFmyCd;
	}

	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
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

	public FrznType getFrznType() {
		return this.frznType;
	}

	public void setFrznType(FrznType frznType) {
		this.frznType = frznType;
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