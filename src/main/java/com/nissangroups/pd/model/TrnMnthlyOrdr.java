package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_MNTHLY_ORDR database table.
 * 
 */
@Entity
@Table(name="TRN_MNTHLY_ORDR")
@NamedQuery(name="TrnMnthlyOrdr.findAll", query="SELECT t FROM TrnMnthlyOrdr t")
public class TrnMnthlyOrdr implements Serializable {
	

	@EmbeddedId
	private TrnMnthlyOrdrPK id;

	@Column(name="AUTO_ADJST_ORDR_QTY")
	private BigDecimal autoAdjstOrdrQty;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DRAFT_QTY")
	private BigDecimal draftQty;

	@Column(name="MS_QTY" )
	private BigDecimal msQty;

	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;

	@Column(name="SIMU_QTY")
	private BigDecimal simuQty;

	@Column(name="SUSPENDED_ORDR_FLAG")
	private String suspendedOrdrFlag;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstProdOrdrType
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="POR_CD", referencedColumnName="POR_CD",insertable=false,updatable=false),
		@JoinColumn(name="POT_CD", referencedColumnName="POT_CD",insertable=false,updatable=false)
		})
	private MstProdOrdrType mstProdOrdrType;

	public TrnMnthlyOrdr() {
	}

	public TrnMnthlyOrdrPK getId() {
		return this.id;
	}

	public void setId(TrnMnthlyOrdrPK id) {
		this.id = id;
	}

	public BigDecimal getAutoAdjstOrdrQty() {
		return this.autoAdjstOrdrQty;
	}

	public void setAutoAdjstOrdrQty(BigDecimal autoAdjstOrdrQty) {
		this.autoAdjstOrdrQty = autoAdjstOrdrQty;
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

	public BigDecimal getDraftQty() {
		return this.draftQty;
	}

	public void setDraftQty(BigDecimal draftQty) {
		this.draftQty = draftQty;
	}

	public BigDecimal getMsQty() {
		if(msQty==null){
			msQty = new BigDecimal(0);
		}
		return this.msQty;
	}

	public void setMsQty(BigDecimal msQty) {
		this.msQty = msQty;
	}

	public BigDecimal getOrdrQty() {
		if(ordrQty==null){
			ordrQty = new BigDecimal(0);
		}
		return this.ordrQty;
	}

	public void setOrdrQty(BigDecimal ordrQty) {
		this.ordrQty = ordrQty;
	}

	public BigDecimal getSimuQty() {
		return this.simuQty;
	}

	public void setSimuQty(BigDecimal simuQty) {
		this.simuQty = simuQty;
	}

	public String getSuspendedOrdrFlag() {
		return this.suspendedOrdrFlag;
	}

	public void setSuspendedOrdrFlag(String suspendedOrdrFlag) {
		this.suspendedOrdrFlag = suspendedOrdrFlag;
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

	public MstProdOrdrType getMstProdOrdrType() {
		return this.mstProdOrdrType;
	}

	public void setMstProdOrdrType(MstProdOrdrType mstProdOrdrType) {
		this.mstProdOrdrType = mstProdOrdrType;
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