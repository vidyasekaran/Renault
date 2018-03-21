package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the MST_POR database table.
 * 
 */
@Entity
@Table(name="MST_POR")
@NamedQuery(name="MstPor.findAll", query="SELECT m FROM MstPor m")
public class MstPor implements Serializable {
	

	@Id
	@Column(name="POR_CD")
	private String porCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FRZN_EDTL_FLAG")
	private String frznEdtlFlag;

	@Column(name="OCF_EDTL_FLAG")
	private String ocfEdtlFlag;

	@Column(name="POR_DESC")
	private String porDesc;

	@Column(name="POR_HRZN")
	private BigDecimal porHrzn;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="VS_MNTR_FLAG")
	private String vsMntrFlag;

	//bi-directional many-to-one association to MstProdRegionCode
	@ManyToOne
	@JoinColumn(name="PROD_REGION_CD")
	private MstProdRegionCode mstProdRegionCode;

	//bi-directional many-to-one association to MstPorCarSr
	@OneToMany(mappedBy="mstPor")
	private List<MstPorCarSr> mstPorCarSrs;

	public MstPor() {
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
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

	public String getFrznEdtlFlag() {
		return this.frznEdtlFlag;
	}

	public void setFrznEdtlFlag(String frznEdtlFlag) {
		this.frznEdtlFlag = frznEdtlFlag;
	}

	public String getOcfEdtlFlag() {
		return this.ocfEdtlFlag;
	}

	public void setOcfEdtlFlag(String ocfEdtlFlag) {
		this.ocfEdtlFlag = ocfEdtlFlag;
	}

	public String getPorDesc() {
		return this.porDesc;
	}

	public void setPorDesc(String porDesc) {
		this.porDesc = porDesc;
	}

	public BigDecimal getPorHrzn() {
		return this.porHrzn;
	}

	public void setPorHrzn(BigDecimal porHrzn) {
		this.porHrzn = porHrzn;
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

	public MstProdRegionCode getMstProdRegionCode() {
		return this.mstProdRegionCode;
	}

	public void setMstProdRegionCode(MstProdRegionCode mstProdRegionCode) {
		this.mstProdRegionCode = mstProdRegionCode;
	}

	public List<MstPorCarSr> getMstPorCarSrs() {
		return this.mstPorCarSrs;
	}

	public void setMstPorCarSrs(List<MstPorCarSr> mstPorCarSrs) {
		this.mstPorCarSrs = mstPorCarSrs;
	}

	public MstPorCarSr addMstPorCarSr(MstPorCarSr mstPorCarSr) {
		getMstPorCarSrs().add(mstPorCarSr);
		mstPorCarSr.setMstPor(this);

		return mstPorCarSr;
	}

	public MstPorCarSr removeMstPorCarSr(MstPorCarSr mstPorCarSr) {
		getMstPorCarSrs().remove(mstPorCarSr);
		mstPorCarSr.setMstPor(null);

		return mstPorCarSr;
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