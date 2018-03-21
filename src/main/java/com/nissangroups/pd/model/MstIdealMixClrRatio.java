package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the MST_IDEAL_MIX_CLR_RATIO database table.
 * 
 */
@Entity
@Table(name="MST_IDEAL_MIX_CLR_RATIO")
@NamedQuery(name="MstIdealMixClrRatio.findAll", query="SELECT m FROM MstIdealMixClrRatio m")
public class MstIdealMixClrRatio implements Serializable {
	

	@EmbeddedId
	private MstIdealMixClrRatioPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="IDEAL_MIX_CLR_RATIO")
	private BigDecimal idealMixClrRatio;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIdealMixClrRatio() {
	}

	public MstIdealMixClrRatioPK getId() {
		return this.id;
	}

	public void setId(MstIdealMixClrRatioPK id) {
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

	public BigDecimal getIdealMixClrRatio() {
		return this.idealMixClrRatio;
	}

	public void setIdealMixClrRatio(BigDecimal idealMixClrRatio) {
		this.idealMixClrRatio = idealMixClrRatio;
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