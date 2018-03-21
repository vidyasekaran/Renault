/*
 * System Name            : Post Dragon 
 * Sub system Name 		  : Interface
 * Function ID            : PST-DRG-I000055
 * Module                 : Ordering		
 * Process Outline     	  : Interface To Receive Weekly OCF from Plant															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 05-01-2016  	  z015895(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TMP_DAILY_OCF_LMT_IF database table.
 * 
 */
@Entity
@Table(name="TMP_DAILY_OCF_LMT_IF")
@NamedQuery(name="TmpDailyOcfLmtIf.findAll", query="SELECT t FROM TmpDailyOcfLmtIf t")
public class TmpDailyOcfLmtIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TmpDailyOcfLmtIfPK id;

	@Column(name="FRME_CD")
	private String frmeCd;

	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="INTGRTD_PROD_CTGRY")
	private String intgrtdProdCtgry;

	@Column(name="\"KEY\"")
	private String key;

	@Column(name="LAST_WK_SYMBL")
	private String lastWkSymbl;

	@Column(name="MAINTAIN_UPD_DT")
	private Timestamp maintainUpdDt;

	@Column(name="MNTH_IN_WK")
	private String mnthInWk;

	@Column(name="OCF_LIMIT")
	private String ocfLimit;

	private String preliminary;

	@Column(name="TARGET_PRD")
	private String targetPrd;

	@Column(name="TERMINAL_ID")
	private String terminalId;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	public TmpDailyOcfLmtIf() {
	}

	public TmpDailyOcfLmtIfPK getId() {
		return this.id;
	}

	public void setId(TmpDailyOcfLmtIfPK id) {
		this.id = id;
	}

	public String getFrmeCd() {
		return this.frmeCd;
	}

	public void setFrmeCd(String frmeCd) {
		this.frmeCd = frmeCd;
	}

	public String getIfFileId() {
		return this.ifFileId;
	}

	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}

	public String getIntgrtdProdCtgry() {
		return this.intgrtdProdCtgry;
	}

	public void setIntgrtdProdCtgry(String intgrtdProdCtgry) {
		this.intgrtdProdCtgry = intgrtdProdCtgry;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLastWkSymbl() {
		return this.lastWkSymbl;
	}

	public void setLastWkSymbl(String lastWkSymbl) {
		this.lastWkSymbl = lastWkSymbl;
	}

	public Timestamp getMaintainUpdDt() {
		return this.maintainUpdDt;
	}

	public void setMaintainUpdDt(Timestamp maintainUpdDt) {
		this.maintainUpdDt = maintainUpdDt;
	}

	public String getMnthInWk() {
		return this.mnthInWk;
	}

	public void setMnthInWk(String mnthInWk) {
		this.mnthInWk = mnthInWk;
	}

	public String getOcfLimit() {
		return this.ocfLimit;
	}

	public void setOcfLimit(String ocfLimit) {
		this.ocfLimit = ocfLimit;
	}

	public String getPreliminary() {
		return this.preliminary;
	}

	public void setPreliminary(String preliminary) {
		this.preliminary = preliminary;
	}

	public String getTargetPrd() {
		return this.targetPrd;
	}

	public void setTargetPrd(String targetPrd) {
		this.targetPrd = targetPrd;
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

}