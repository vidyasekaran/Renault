package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_SERV_PRMTR database table.
 * 
 */
@Entity
@Table(name="MST_SERV_PRMTR")
@NamedQuery(name="MstServPrmtr.findAll", query="SELECT m FROM MstServPrmtr m")
public class MstServPrmtr implements Serializable {
	

	@EmbeddedId
	private MstServPrmtrPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DEALER_LST")
	private String dealerLst;

	@Column(name="OWNR_MNL")
	private String ownrMnl;

	@Column(name="PRFX_NO")
	private String prfxNo;

	@Column(name="PRFX_YES")
	private String prfxYes;

	@Column(name="SFFX_NO")
	private String sffxNo;

	@Column(name="SFFX_YES")
	private String sffxYes;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="WRNTY_BKLT")
	private String wrntyBklt;

	public MstServPrmtr() {
	}

	public MstServPrmtrPK getId() {
		return this.id;
	}

	public void setId(MstServPrmtrPK id) {
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

	public String getDealerLst() {
		return this.dealerLst;
	}

	public void setDealerLst(String dealerLst) {
		this.dealerLst = dealerLst;
	}

	public String getOwnrMnl() {
		return this.ownrMnl;
	}

	public void setOwnrMnl(String ownrMnl) {
		this.ownrMnl = ownrMnl;
	}

	public String getPrfxNo() {
		return this.prfxNo;
	}

	public void setPrfxNo(String prfxNo) {
		this.prfxNo = prfxNo;
	}

	public String getPrfxYes() {
		return this.prfxYes;
	}

	public void setPrfxYes(String prfxYes) {
		this.prfxYes = prfxYes;
	}

	public String getSffxNo() {
		return this.sffxNo;
	}

	public void setSffxNo(String sffxNo) {
		this.sffxNo = sffxNo;
	}

	public String getSffxYes() {
		return this.sffxYes;
	}

	public void setSffxYes(String sffxYes) {
		this.sffxYes = sffxYes;
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

	public String getWrntyBklt() {
		return this.wrntyBklt;
	}

	public void setWrntyBklt(String wrntyBklt) {
		this.wrntyBklt = wrntyBklt;
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