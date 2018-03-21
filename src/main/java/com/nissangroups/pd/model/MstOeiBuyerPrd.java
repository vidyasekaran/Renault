package com.nissangroups.pd.model;
import com.nissangroups.pd.util.CommonUtil;
import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the MST_OEI_BUYER_PRD database table.
 * 
 */
@Entity
@Table(name="MST_OEI_BUYER_PRD")
@NamedQuery(name="MstOeiBuyerPrd.findAll", query="SELECT m FROM MstOeiBuyerPrd m")
public class MstOeiBuyerPrd implements Serializable {
	

	@EmbeddedId
	private MstOeiBuyerPrdPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstOeiBuyerPrd() {
	}

	public MstOeiBuyerPrdPK getId() {
		return this.id;
	}

	public void setId(MstOeiBuyerPrdPK id) {
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

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
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