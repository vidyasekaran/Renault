package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_SPEC_DESTN database table.
 * 
 */
@Entity
@Table(name="MST_SPEC_DESTN")
@NamedQuery(name="MstSpecDestn.findAll", query="SELECT m FROM MstSpecDestn m")
public class MstSpecDestn implements Serializable {
	

	@Id
	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="SPEC_DESTN_DESC")
	private String specDestnDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyerSpecDestn
	@OneToMany(mappedBy="mstSpecDestn")
	private List<MstBuyerSpecDestn> mstBuyerSpecDestns;

	public MstSpecDestn() {
	}

	public String getSpecDestnCd() {
		return this.specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
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

	public String getSpecDestnDesc() {
		return this.specDestnDesc;
	}

	public void setSpecDestnDesc(String specDestnDesc) {
		this.specDestnDesc = specDestnDesc;
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

	public List<MstBuyerSpecDestn> getMstBuyerSpecDestns() {
		return this.mstBuyerSpecDestns;
	}

	public void setMstBuyerSpecDestns(List<MstBuyerSpecDestn> mstBuyerSpecDestns) {
		this.mstBuyerSpecDestns = mstBuyerSpecDestns;
	}

	public MstBuyerSpecDestn addMstBuyerSpecDestn(MstBuyerSpecDestn mstBuyerSpecDestn) {
		getMstBuyerSpecDestns().add(mstBuyerSpecDestn);
		mstBuyerSpecDestn.setMstSpecDestn(this);

		return mstBuyerSpecDestn;
	}

	public MstBuyerSpecDestn removeMstBuyerSpecDestn(MstBuyerSpecDestn mstBuyerSpecDestn) {
		getMstBuyerSpecDestns().remove(mstBuyerSpecDestn);
		mstBuyerSpecDestn.setMstSpecDestn(null);

		return mstBuyerSpecDestn;
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