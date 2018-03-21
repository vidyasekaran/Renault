package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_WKLY_ORDR_TEMP database table.
 * 
 */
@Entity
@Table(name="TRN_WKLY_ORDR_TEMP")
@NamedQuery(name="TrnWklyOrdrTemp.findAll", query="SELECT t FROM TrnWklyOrdrTemp t")
public class TrnWklyOrdrTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnWklyOrdrTempPK id;

	@Column(name="ACCPTD_ORDR_QTY")
	private BigDecimal accptdOrdrQty;

	@Column(name="CRTD_BY")
	private String crtdBy;

	@Column(name="CRTD_DT")
	private Timestamp crtdDt;

	@Column(name="DEL_FLG")
	private String delFlg;

	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="ORGNL_ORDR_QTY")
	private BigDecimal orgnlOrdrQty;

	@Column(name="PROD_MTHD_CD")
	private String prodMthdCd;

	@Column(name="REQTD_ORDR_QTY")
	private BigDecimal reqtdOrdrQty;

	@Column(name="SIMU_ORDR_QTY")
	private BigDecimal simuOrdrQty;

	@Column(name="SUSPENDED_ORDR_FLAG")
	private String suspendedOrdrFlag;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnWklyOrdrTemp() {
	}

	public TrnWklyOrdrTempPK getId() {
		return this.id;
	}

	public void setId(TrnWklyOrdrTempPK id) {
		this.id = id;
	}

	public BigDecimal getAccptdOrdrQty() {
		return this.accptdOrdrQty;
	}

	public void setAccptdOrdrQty(BigDecimal accptdOrdrQty) {
		this.accptdOrdrQty = accptdOrdrQty;
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

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	public BigDecimal getOrgnlOrdrQty() {
		return this.orgnlOrdrQty;
	}

	public void setOrgnlOrdrQty(BigDecimal orgnlOrdrQty) {
		this.orgnlOrdrQty = orgnlOrdrQty;
	}

	public String getProdMthdCd() {
		return this.prodMthdCd;
	}

	public void setProdMthdCd(String prodMthdCd) {
		this.prodMthdCd = prodMthdCd;
	}

	public BigDecimal getReqtdOrdrQty() {
		return this.reqtdOrdrQty;
	}

	public void setReqtdOrdrQty(BigDecimal reqtdOrdrQty) {
		this.reqtdOrdrQty = reqtdOrdrQty;
	}

	public BigDecimal getSimuOrdrQty() {
		return this.simuOrdrQty;
	}

	public void setSimuOrdrQty(BigDecimal simuOrdrQty) {
		this.simuOrdrQty = simuOrdrQty;
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

}