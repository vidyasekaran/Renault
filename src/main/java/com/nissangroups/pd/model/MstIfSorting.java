package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the MST_IF_SORTING database table.
 * 
 */
@Entity
@Table(name="MST_IF_SORTING")
@NamedQuery(name="MstIfSorting.findAll", query="SELECT m FROM MstIfSorting m")
public class MstIfSorting implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MstIfSortingPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	private String ordr;

	private BigDecimal prity;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIfSorting() {
	}

	public MstIfSortingPK getId() {
		return this.id;
	}

	public void setId(MstIfSortingPK id) {
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

	public String getOrdr() {
		return this.ordr;
	}

	public void setOrdr(String ordr) {
		this.ordr = ordr;
	}

	public BigDecimal getPrity() {
		return this.prity;
	}

	public void setPrity(BigDecimal prity) {
		this.prity = prity;
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