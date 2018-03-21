package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the MST_POR_CAR_SRS database table.
 * 
 */
@Entity
@Table(name="MST_POR_CAR_SRS")
@NamedQuery(name="MstPorCarSr.findAll", query="SELECT m FROM MstPorCarSr m")
public class MstPorCarSr implements Serializable {
	

	@EmbeddedId
	private MstPorCarSrPK id;

	@Column(name="CAR_GRP")
	private String carGrp;

	@Column(name="CAR_SRS_ABLSH_DATE")
	private String carSrsAblshDate;

	@Column(name="CAR_SRS_ADPT_DATE")
	private String carSrsAdptDate;

	@Column(name="CAR_SRS_DESC")
	private String carSrsDesc;

	@Column(name="CAR_SRS_ORDR_HRZN")
	private BigDecimal carSrsOrdrHrzn;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstBrnd
	@ManyToOne
	@JoinColumn(name="BRND_CD",insertable=false,updatable=false)
	private MstBrnd mstBrnd;

	//bi-directional many-to-one association to MstPor
	@ManyToOne
	@JoinColumn(name="POR_CD",insertable=false,updatable=false)
	private MstPor mstPor;

	public MstPorCarSr() {
	}

	public MstPorCarSrPK getId() {
		return this.id;
	}

	public void setId(MstPorCarSrPK id) {
		this.id = id;
	}

	public String getCarGrp() {
		return this.carGrp;
	}

	public void setCarGrp(String carGrp) {
		this.carGrp = carGrp;
	}

	public String getCarSrsAblshDate() {
		return this.carSrsAblshDate;
	}

	public void setCarSrsAblshDate(String carSrsAblshDate) {
		this.carSrsAblshDate = carSrsAblshDate;
	}

	public String getCarSrsAdptDate() {
		return this.carSrsAdptDate;
	}

	public void setCarSrsAdptDate(String carSrsAdptDate) {
		this.carSrsAdptDate = carSrsAdptDate;
	}

	public String getCarSrsDesc() {
		return this.carSrsDesc;
	}

	public void setCarSrsDesc(String carSrsDesc) {
		this.carSrsDesc = carSrsDesc;
	}

	public BigDecimal getCarSrsOrdrHrzn() {
		return this.carSrsOrdrHrzn;
	}

	public void setCarSrsOrdrHrzn(BigDecimal carSrsOrdrHrzn) {
		this.carSrsOrdrHrzn = carSrsOrdrHrzn;
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

	public MstBrnd getMstBrnd() {
		return this.mstBrnd;
	}

	public void setMstBrnd(MstBrnd mstBrnd) {
		this.mstBrnd = mstBrnd;
	}

	public MstPor getMstPor() {
		return this.mstPor;
	}

	public void setMstPor(MstPor mstPor) {
		this.mstPor = mstPor;
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