package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_FEAT_TYPE database table.
 * 
 */
@Entity
@Table(name="MST_FEAT_TYPE")
@NamedQuery(name="MstFeatType.findAll", query="SELECT m FROM MstFeatType m")
public class MstFeatType implements Serializable {
	

	@Id
	@Column(name="FEAT_TYPE_CD")
	private String featTypeCd;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FEAT_TYPE_LNG_DESC")
	private String featTypeLngDesc;

	@Column(name="FEAT_TYPE_SHRT_DESC")
	private String featTypeShrtDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstFeat
	@OneToMany(mappedBy="mstFeatType")
	private List<MstFeat> mstFeats;

	public MstFeatType() {
	}

	public String getFeatTypeCd() {
		return this.featTypeCd;
	}

	public void setFeatTypeCd(String featTypeCd) {
		this.featTypeCd = featTypeCd;
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

	public String getFeatTypeLngDesc() {
		return this.featTypeLngDesc;
	}

	public void setFeatTypeLngDesc(String featTypeLngDesc) {
		this.featTypeLngDesc = featTypeLngDesc;
	}

	public String getFeatTypeShrtDesc() {
		return this.featTypeShrtDesc;
	}

	public void setFeatTypeShrtDesc(String featTypeShrtDesc) {
		this.featTypeShrtDesc = featTypeShrtDesc;
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

	public List<MstFeat> getMstFeats() {
		return this.mstFeats;
	}

	public void setMstFeats(List<MstFeat> mstFeats) {
		this.mstFeats = mstFeats;
	}

	public MstFeat addMstFeat(MstFeat mstFeat) {
		getMstFeats().add(mstFeat);
		mstFeat.setMstFeatType(this);

		return mstFeat;
	}

	public MstFeat removeMstFeat(MstFeat mstFeat) {
		getMstFeats().remove(mstFeat);
		mstFeat.setMstFeatType(null);

		return mstFeat;
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