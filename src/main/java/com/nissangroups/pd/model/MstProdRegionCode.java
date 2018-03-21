package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_PROD_REGION_CODE database table.
 * 
 */
@Entity
@Table(name="MST_PROD_REGION_CODE")
@NamedQuery(name="MstProdRegionCode.findAll", query="SELECT m FROM MstProdRegionCode m")
public class MstProdRegionCode implements Serializable {
	

	@Id
	@Column(name="PROD_REGION_CD")
	private String prodRegionCd;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="PROD_REGION_DESC")
	private String prodRegionDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyer
	@OneToMany(mappedBy="mstProdRegionCode")
	private List<MstBuyer> mstBuyers;

	//bi-directional many-to-one association to MstOcfRegion
	@OneToMany(mappedBy="mstProdRegionCode")
	private List<MstOcfRegion> mstOcfRegions;

	//bi-directional many-to-one association to MstPor
	@OneToMany(mappedBy="mstProdRegionCode")
	private List<MstPor> mstPors;

	public MstProdRegionCode() {
	}

	public String getProdRegionCd() {
		return this.prodRegionCd;
	}

	public void setProdRegionCd(String prodRegionCd) {
		this.prodRegionCd = prodRegionCd;
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

	public String getProdRegionDesc() {
		return this.prodRegionDesc;
	}

	public void setProdRegionDesc(String prodRegionDesc) {
		this.prodRegionDesc = prodRegionDesc;
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
		mstBuyer.setMstProdRegionCode(this);

		return mstBuyer;
	}

	public MstBuyer removeMstBuyer(MstBuyer mstBuyer) {
		getMstBuyers().remove(mstBuyer);
		mstBuyer.setMstProdRegionCode(null);

		return mstBuyer;
	}

	public List<MstOcfRegion> getMstOcfRegions() {
		return this.mstOcfRegions;
	}

	public void setMstOcfRegions(List<MstOcfRegion> mstOcfRegions) {
		this.mstOcfRegions = mstOcfRegions;
	}

	public MstOcfRegion addMstOcfRegion(MstOcfRegion mstOcfRegion) {
		getMstOcfRegions().add(mstOcfRegion);
		mstOcfRegion.setMstProdRegionCode(this);

		return mstOcfRegion;
	}

	public MstOcfRegion removeMstOcfRegion(MstOcfRegion mstOcfRegion) {
		getMstOcfRegions().remove(mstOcfRegion);
		mstOcfRegion.setMstProdRegionCode(null);

		return mstOcfRegion;
	}

	public List<MstPor> getMstPors() {
		return this.mstPors;
	}

	public void setMstPors(List<MstPor> mstPors) {
		this.mstPors = mstPors;
	}

	public MstPor addMstPor(MstPor mstPor) {
		getMstPors().add(mstPor);
		mstPor.setMstProdRegionCode(this);

		return mstPor;
	}

	public MstPor removeMstPor(MstPor mstPor) {
		getMstPors().remove(mstPor);
		mstPor.setMstProdRegionCode(null);

		return mstPor;
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