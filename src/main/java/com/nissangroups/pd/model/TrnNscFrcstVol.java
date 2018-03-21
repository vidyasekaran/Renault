package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_NSC_FRCST_VOL database table.
 * 
 */
@Entity
@Table(name="TRN_NSC_FRCST_VOL")
@NamedQuery(name="TrnNscFrcstVol.findAll", query="SELECT t FROM TrnNscFrcstVol t")
public class TrnNscFrcstVol implements Serializable {
	

	@EmbeddedId
	private TrnNscFrcstVolPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FRCST_VOL")
	private BigDecimal frcstVol;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnNscFrcstVol() {
	}

	public TrnNscFrcstVolPK getId() {
		return this.id;
	}

	public void setId(TrnNscFrcstVolPK id) {
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

	public BigDecimal getFrcstVol() {
		return this.frcstVol;
	}

	public void setFrcstVol(BigDecimal frcstVol) {
		this.frcstVol = frcstVol;
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