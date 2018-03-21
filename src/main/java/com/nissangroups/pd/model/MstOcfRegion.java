package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_OCF_REGION database table.
 * 
 */
@Entity
@Table(name="MST_OCF_REGION")
@NamedQuery(name="MstOcfRegion.findAll", query="SELECT m FROM MstOcfRegion m")
public class MstOcfRegion implements Serializable {
	

	@EmbeddedId
	private MstOcfRegionPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="OCF_AUTO_ALLCTN_FLAG")
	private String ocfAutoAllctnFlag;

	@Column(name="OCF_REGION_CD_DESC")
	private String ocfRegionCdDesc;

	@Column(name="PROD_STAGE_FLAG")
	private String prodStageFlag;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyer
	@OneToMany(mappedBy="mstOcfRegion")
	private List<MstBuyer> mstBuyers;

	//bi-directional many-to-one association to MstProdRegionCode
	@ManyToOne
	@JoinColumn(name="PROD_REGION_CD",insertable=false,updatable=false)
	private MstProdRegionCode mstProdRegionCode;

	public MstOcfRegion() {
	}

	public MstOcfRegionPK getId() {
		return this.id;
	}

	public void setId(MstOcfRegionPK id) {
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

	public String getOcfAutoAllctnFlag() {
		return this.ocfAutoAllctnFlag;
	}

	public void setOcfAutoAllctnFlag(String ocfAutoAllctnFlag) {
		this.ocfAutoAllctnFlag = ocfAutoAllctnFlag;
	}

	public String getOcfRegionCdDesc() {
		return this.ocfRegionCdDesc;
	}

	public void setOcfRegionCdDesc(String ocfRegionCdDesc) {
		this.ocfRegionCdDesc = ocfRegionCdDesc;
	}

	public String getProdStageFlag() {
		return this.prodStageFlag;
	}

	public void setProdStageFlag(String prodStageFlag) {
		this.prodStageFlag = prodStageFlag;
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

	public List<MstBuyer> getMstBuyers() {
		return this.mstBuyers;
	}

	public void setMstBuyers(List<MstBuyer> mstBuyers) {
		this.mstBuyers = mstBuyers;
	}

	public MstBuyer addMstBuyer(MstBuyer mstBuyer) {
		getMstBuyers().add(mstBuyer);
		mstBuyer.setMstOcfRegion(this);

		return mstBuyer;
	}

	public MstBuyer removeMstBuyer(MstBuyer mstBuyer) {
		getMstBuyers().remove(mstBuyer);
		mstBuyer.setMstOcfRegion(null);

		return mstBuyer;
	}

	public MstProdRegionCode getMstProdRegionCode() {
		return this.mstProdRegionCode;
	}

	public void setMstProdRegionCode(MstProdRegionCode mstProdRegionCode) {
		this.mstProdRegionCode = mstProdRegionCode;
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