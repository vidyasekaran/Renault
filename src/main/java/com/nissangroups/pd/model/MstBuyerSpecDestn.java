package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_BUYER_SPEC_DESTN database table.
 * 
 */
@Entity
@Table(name="MST_BUYER_SPEC_DESTN")
@NamedQuery(name="MstBuyerSpecDestn.findAll", query="SELECT m FROM MstBuyerSpecDestn m")
public class MstBuyerSpecDestn implements Serializable {
	

	@EmbeddedId
	private MstBuyerSpecDestnPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBuyer
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="BUYER_CD", referencedColumnName="BUYER_CD",insertable=false,updatable=false),
		@JoinColumn(name="PROD_REGION_CD", referencedColumnName="PROD_REGION_CD",insertable=false,updatable=false)
		})
	private MstBuyer mstBuyer;

	//bi-directional many-to-one association to MstSpecDestn
	@ManyToOne
	@JoinColumn(name="SPEC_DESTN_CD",insertable=false,updatable=false)
	private MstSpecDestn mstSpecDestn;

	public MstBuyerSpecDestn() {
	}

	public MstBuyerSpecDestnPK getId() {
		return this.id;
	}

	public void setId(MstBuyerSpecDestnPK id) {
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

	public MstBuyer getMstBuyer() {
		return this.mstBuyer;
	}

	public void setMstBuyer(MstBuyer mstBuyer) {
		this.mstBuyer = mstBuyer;
	}

	public MstSpecDestn getMstSpecDestn() {
		return this.mstSpecDestn;
	}

	public void setMstSpecDestn(MstSpecDestn mstSpecDestn) {
		this.mstSpecDestn = mstSpecDestn;
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