package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;
import com.nissangroups.pd.util.CommonUtil;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the MST_INT_CLR database table.
 * 
 */
@Entity
@Table(name="MST_INT_CLR")
@NamedQuery(name="MstIntClr.findAll", query="SELECT m FROM MstIntClr m")
public class MstIntClr implements Serializable {
	

	@Id
	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="INT_CLR_DESC")
	private String intClrDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIntClr() {
	}

	public String getIntClrCd() {
		return this.intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
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

	public String getIntClrDesc() {
		return this.intClrDesc;
	}

	public void setIntClrDesc(String intClrDesc) {
		this.intClrDesc = intClrDesc;
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
		  this.setCrtdBy("B000002");
			this.setUpdtdBy("B000002");
	        this.setCrtdDt(CommonUtil.createTimeStamp());
	        this.setUpdtdDt(CommonUtil.createTimeStamp());
	    }
	    
	    @PreUpdate
	    void onPersist() {
	    	this.setCrtdBy("B000002");
			this.setUpdtdBy("B000002");
	        this.setUpdtdDt(CommonUtil.createTimeStamp());
	    }


}