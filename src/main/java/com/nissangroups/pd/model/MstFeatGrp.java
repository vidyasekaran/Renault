package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_FEAT_GRP database table.
 * 
 */
@Entity
@Table(name="MST_FEAT_GRP")
@NamedQuery(name="MstFeatGrp.findAll", query="SELECT m FROM MstFeatGrp m")
public class MstFeatGrp implements Serializable {
	

	@EmbeddedId
	private MstFeatGrpPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FEAT_GRP_DESC")
	private String featGrpDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstFeat
	@OneToMany(mappedBy="mstFeatGrp")
	private List<MstFeat> mstFeats;

	public MstFeatGrp() {
	}

	public MstFeatGrpPK getId() {
		return this.id;
	}

	public void setId(MstFeatGrpPK id) {
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

	public String getFeatGrpDesc() {
		return this.featGrpDesc;
	}

	public void setFeatGrpDesc(String featGrpDesc) {
		this.featGrpDesc = featGrpDesc;
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
		mstFeat.setMstFeatGrp(this);

		return mstFeat;
	}

	public MstFeat removeMstFeat(MstFeat mstFeat) {
		getMstFeats().remove(mstFeat);
		mstFeat.setMstFeatGrp(null);

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