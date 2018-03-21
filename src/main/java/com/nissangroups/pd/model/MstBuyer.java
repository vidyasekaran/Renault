package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_BUYER database table.
 * 
 */
@Entity
@Table(name="MST_BUYER")
@NamedQuery(name="MstBuyer.findAll", query="SELECT m FROM MstBuyer m")
public class MstBuyer implements Serializable {
	

	@EmbeddedId
	private MstBuyerPK id;

	@Column(name="BUYER_DESC")
	private String buyerDesc;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="END_OF_PPLN_ACHV")
	private String endOfPplnAchv;

	@Column(name="NSC_EIM_ODER_HRZN_FLAG")
	private String nscEimOderHrznFlag;

	@Column(name="PRFX_SHPMNT_INSPCTN")
	private String prfxShpmntInspctn;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyerGrp
	@ManyToOne
	@JoinColumn(name="BUYER_GRP_CD")
	private MstBuyerGrp mstBuyerGrp;

	//bi-directional many-to-one association to MstOcfRegion
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="OCF_BUYER_GRP_CD", referencedColumnName="OCF_BUYER_GRP_CD", insertable=false, updatable=false),
		@JoinColumn(name="OCF_REGION_CD", referencedColumnName="OCF_REGION_CD", insertable=false, updatable=false),
		@JoinColumn(name="PROD_REGION_CD", referencedColumnName="PROD_REGION_CD", insertable=false, updatable=false)
		})
	private MstOcfRegion mstOcfRegion;

	//bi-directional many-to-one association to MstProdRegionCode
	@ManyToOne
	@JoinColumn(name="PROD_REGION_CD", insertable=false, updatable=false)
	private MstProdRegionCode mstProdRegionCode;

	//bi-directional many-to-one association to MstBuyerSpecDestn
	@OneToMany(mappedBy="mstBuyer")
	private List<MstBuyerSpecDestn> mstBuyerSpecDestns;

	public MstBuyer() {
	}

	public MstBuyerPK getId() {
		return this.id;
	}

	public void setId(MstBuyerPK id) {
		this.id = id;
	}

	public String getBuyerDesc() {
		return this.buyerDesc;
	}

	public void setBuyerDesc(String buyerDesc) {
		this.buyerDesc = buyerDesc;
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

	public String getEndOfPplnAchv() {
		return this.endOfPplnAchv;
	}

	public void setEndOfPplnAchv(String endOfPplnAchv) {
		this.endOfPplnAchv = endOfPplnAchv;
	}

	public String getNscEimOderHrznFlag() {
		return this.nscEimOderHrznFlag;
	}

	public void setNscEimOderHrznFlag(String nscEimOderHrznFlag) {
		this.nscEimOderHrznFlag = nscEimOderHrznFlag;
	}

	public String getPrfxShpmntInspctn() {
		return this.prfxShpmntInspctn;
	}

	public void setPrfxShpmntInspctn(String prfxShpmntInspctn) {
		this.prfxShpmntInspctn = prfxShpmntInspctn;
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

	public MstBuyerGrp getMstBuyerGrp() {
		return this.mstBuyerGrp;
	}

	public void setMstBuyerGrp(MstBuyerGrp mstBuyerGrp) {
		this.mstBuyerGrp = mstBuyerGrp;
	}

	public MstOcfRegion getMstOcfRegion() {
		return this.mstOcfRegion;
	}

	public void setMstOcfRegion(MstOcfRegion mstOcfRegion) {
		this.mstOcfRegion = mstOcfRegion;
	}

	public MstProdRegionCode getMstProdRegionCode() {
		return this.mstProdRegionCode;
	}

	public void setMstProdRegionCode(MstProdRegionCode mstProdRegionCode) {
		this.mstProdRegionCode = mstProdRegionCode;
	}

	public List<MstBuyerSpecDestn> getMstBuyerSpecDestns() {
		return this.mstBuyerSpecDestns;
	}

	public void setMstBuyerSpecDestns(List<MstBuyerSpecDestn> mstBuyerSpecDestns) {
		this.mstBuyerSpecDestns = mstBuyerSpecDestns;
	}

	public MstBuyerSpecDestn addMstBuyerSpecDestn(MstBuyerSpecDestn mstBuyerSpecDestn) {
		getMstBuyerSpecDestns().add(mstBuyerSpecDestn);
		mstBuyerSpecDestn.setMstBuyer(this);

		return mstBuyerSpecDestn;
	}

	public MstBuyerSpecDestn removeMstBuyerSpecDestn(MstBuyerSpecDestn mstBuyerSpecDestn) {
		getMstBuyerSpecDestns().remove(mstBuyerSpecDestn);
		mstBuyerSpecDestn.setMstBuyer(null);

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