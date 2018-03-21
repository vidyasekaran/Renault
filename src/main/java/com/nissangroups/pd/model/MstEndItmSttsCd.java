package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_END_ITM_STTS_CD database table.
 * 
 */
@Entity
@Table(name="MST_END_ITM_STTS_CD")
@NamedQuery(name="MstEndItmSttsCd.findAll", query="SELECT m FROM MstEndItmSttsCd m")
public class MstEndItmSttsCd implements Serializable {
	

	@Id
	@Column(name="END_ITM_STTS_CD")
	private String endItmSttsCd;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="END_ITM_STTS_DESC")
	private String endItmSttsDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstOseiDtl
	@OneToMany(mappedBy="mstEndItmSttsCd")
	private List<MstOseiDtl> mstOseiDtls;

	public MstEndItmSttsCd() {
	}

	public String getEndItmSttsCd() {
		return this.endItmSttsCd;
	}

	public void setEndItmSttsCd(String endItmSttsCd) {
		this.endItmSttsCd = endItmSttsCd;
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

	public String getEndItmSttsDesc() {
		return this.endItmSttsDesc;
	}

	public void setEndItmSttsDesc(String endItmSttsDesc) {
		this.endItmSttsDesc = endItmSttsDesc;
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

	public List<MstOseiDtl> getMstOseiDtls() {
		return this.mstOseiDtls;
	}

	public void setMstOseiDtls(List<MstOseiDtl> mstOseiDtls) {
		this.mstOseiDtls = mstOseiDtls;
	}

	public MstOseiDtl addMstOseiDtl(MstOseiDtl mstOseiDtl) {
		getMstOseiDtls().add(mstOseiDtl);
		mstOseiDtl.setMstEndItmSttsCd(this);

		return mstOseiDtl;
	}

	public MstOseiDtl removeMstOseiDtl(MstOseiDtl mstOseiDtl) {
		getMstOseiDtls().remove(mstOseiDtl);
		mstOseiDtl.setMstEndItmSttsCd(null);

		return mstOseiDtl;
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