package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the MST_PRMTR database table.
 * 
 */
@Entity
@Table(name="MST_PRMTR")
@NamedQuery(name="MstPrmtr.findAll", query="SELECT m FROM MstPrmtr m")
public class MstPrmtr implements Serializable {
	

	@EmbeddedId
	private MstPrmtrPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="SEQ_DESC")
	private String seqDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	private String val1;

	@Column(name="VAL1_DESC")
	private String val1Desc;

	private String val2;

	@Column(name="VAL2_DESC")
	private String val2Desc;

	public MstPrmtr() {
	}

	public MstPrmtrPK getId() {
		return this.id;
	}

	public void setId(MstPrmtrPK id) {
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

	public String getSeqDesc() {
		return this.seqDesc;
	}

	public void setSeqDesc(String seqDesc) {
		this.seqDesc = seqDesc;
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

	public String getVal1() {
		return this.val1;
	}

	public void setVal1(String val1) {
		this.val1 = val1;
	}

	public String getVal1Desc() {
		return this.val1Desc;
	}

	public void setVal1Desc(String val1Desc) {
		this.val1Desc = val1Desc;
	}

	public String getVal2() {
		return this.val2;
	}

	public void setVal2(String val2) {
		this.val2 = val2;
	}

	public String getVal2Desc() {
		return this.val2Desc;
	}

	public void setVal2Desc(String val2Desc) {
		this.val2Desc = val2Desc;
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