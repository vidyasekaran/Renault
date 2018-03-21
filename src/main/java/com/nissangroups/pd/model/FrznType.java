package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the FRZN_TYPE database table.
 * 
 */
@Entity
@Table(name="FRZN_TYPE")
@NamedQuery(name="FrznType.findAll", query="SELECT f FROM FrznType f")
public class FrznType implements Serializable {
	

	@Id
	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FRZN_TYPE_LNG_DESC")
	private String frznTypeLngDesc;

	@Column(name="FRZN_TYPE_SHRT_DESC")
	private String frznTypeShrtDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstFrzn
	@OneToMany(mappedBy="frznType")
	private List<MstFrzn> mstFrzns;

	public FrznType() {
	}

	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
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

	public String getFrznTypeLngDesc() {
		return this.frznTypeLngDesc;
	}

	public void setFrznTypeLngDesc(String frznTypeLngDesc) {
		this.frznTypeLngDesc = frznTypeLngDesc;
	}

	public String getFrznTypeShrtDesc() {
		return this.frznTypeShrtDesc;
	}

	public void setFrznTypeShrtDesc(String frznTypeShrtDesc) {
		this.frznTypeShrtDesc = frznTypeShrtDesc;
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

	public List<MstFrzn> getMstFrzns() {
		return this.mstFrzns;
	}

	public void setMstFrzns(List<MstFrzn> mstFrzns) {
		this.mstFrzns = mstFrzns;
	}

	public MstFrzn addMstFrzn(MstFrzn mstFrzn) {
		getMstFrzns().add(mstFrzn);
		mstFrzn.setFrznType(this);

		return mstFrzn;
	}

	public MstFrzn removeMstFrzn(MstFrzn mstFrzn) {
		getMstFrzns().remove(mstFrzn);
		mstFrzn.setFrznType(null);

		return mstFrzn;
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