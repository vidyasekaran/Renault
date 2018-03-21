package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_MC_SUB_REGION database table.
 * 
 */
@Entity
@Table(name="MST_MC_SUB_REGION")
@NamedQuery(name="MstMcSubRegion.findAll", query="SELECT m FROM MstMcSubRegion m")
public class MstMcSubRegion implements Serializable {
	

	@EmbeddedId
	private MstMcSubRegionPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstMcRegion
	@ManyToOne
	@JoinColumn(name="MC_REGION_CD",insertable=false,updatable=false)
	private MstMcRegion mstMcRegion;

	public MstMcSubRegion() {
	}

	public MstMcSubRegionPK getId() {
		return this.id;
	}

	public void setId(MstMcSubRegionPK id) {
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

	public MstMcRegion getMstMcRegion() {
		return this.mstMcRegion;
	}

	public void setMstMcRegion(MstMcRegion mstMcRegion) {
		this.mstMcRegion = mstMcRegion;
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