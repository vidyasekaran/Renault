package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the TRN_MNTHLY_OCF_IF database table.
 * 
 */
@Entity
@Table(name="TRN_MNTHLY_OCF_IF")
@NamedQuery(name="TrnMnthlyOcfIf.findAll", query="SELECT t FROM TrnMnthlyOcfIf t")
public class TrnMnthlyOcfIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnMnthlyOcfIfPK id;

	@Column(name="APPLICATION_USER_ID")
	private String applicationUserId;

	@Column(name="CAR_GRP")
	private String carGrp;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="MAINTAIN_DT")
	private Timestamp maintainDt;

	@Column(name="MAINTAIN_UPD_DT")
	private Timestamp maintainUpdDt;

	private String notes;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="OCF_MAX_QTY")
	private String ocfMaxQty;

	@Column(name="OCF_STND_QTY")
	private String ocfStndQty;

	@Column(name="SHRT_DESC")
	private String shrtDesc;

	@Column(name="TERMINAL_ID")
	private String terminalId;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnMnthlyOcfIf() {
	}

	public TrnMnthlyOcfIfPK getId() {
		return this.id;
	}

	public void setId(TrnMnthlyOcfIfPK id) {
		this.id = id;
	}

	public String getApplicationUserId() {
		return this.applicationUserId;
	}

	public void setApplicationUserId(String applicationUserId) {
		this.applicationUserId = applicationUserId;
	}

	public String getCarGrp() {
		return this.carGrp;
	}

	public void setCarGrp(String carGrp) {
		this.carGrp = carGrp;
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

	public Timestamp getMaintainDt() {
		return this.maintainDt;
	}

	public void setMaintainDt(Timestamp maintainDt) {
		this.maintainDt = maintainDt;
	}

	public Timestamp getMaintainUpdDt() {
		return this.maintainUpdDt;
	}

	public void setMaintainUpdDt(Timestamp maintainUpdDt) {
		this.maintainUpdDt = maintainUpdDt;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
	}

	public String getOcfMaxQty() {
		return this.ocfMaxQty;
	}

	public void setOcfMaxQty(String ocfMaxQty) {
		this.ocfMaxQty = ocfMaxQty;
	}

	public String getOcfStndQty() {
		return this.ocfStndQty;
	}

	public void setOcfStndQty(String ocfStndQty) {
		this.ocfStndQty = ocfStndQty;
	}

	public String getShrtDesc() {
		return this.shrtDesc;
	}

	public void setShrtDesc(String shrtDesc) {
		this.shrtDesc = shrtDesc;
	}

	public String getTerminalId() {
		return this.terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
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