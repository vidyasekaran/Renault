package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MST_LCK_CNFGURTN database table.
 * 
 */
@Entity
@Table(name="MST_LCK_CNFGURTN")
@NamedQuery(name="MstLckCnfgurtn.findAll", query="SELECT m FROM MstLckCnfgurtn m")
public class MstLckCnfgurtn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="POR_CD")
	private String porCd;

	@Column(name="EXPTR_FLG")
	private String exptrFlg;

	@Column(name="NSC_FLG")
	private String nscFlg;

	@Column(name="ORDR_TRNSMSSN_FLG")
	private String ordrTrnsmssnFlg;

	@Column(name="RHQ_FLG")
	private String rhqFlg;

	@Column(name="STG_CD")
	private String stgCd;

	@Column(name="STG_STTS_CD")
	private String stgSttsCd;

	public MstLckCnfgurtn() {
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getExptrFlg() {
		return this.exptrFlg;
	}

	public void setExptrFlg(String exptrFlg) {
		this.exptrFlg = exptrFlg;
	}

	public String getNscFlg() {
		return this.nscFlg;
	}

	public void setNscFlg(String nscFlg) {
		this.nscFlg = nscFlg;
	}

	public String getOrdrTrnsmssnFlg() {
		return this.ordrTrnsmssnFlg;
	}

	public void setOrdrTrnsmssnFlg(String ordrTrnsmssnFlg) {
		this.ordrTrnsmssnFlg = ordrTrnsmssnFlg;
	}

	public String getRhqFlg() {
		return this.rhqFlg;
	}

	public void setRhqFlg(String rhqFlg) {
		this.rhqFlg = rhqFlg;
	}

	public String getStgCd() {
		return this.stgCd;
	}

	public void setStgCd(String stgCd) {
		this.stgCd = stgCd;
	}

	public String getStgSttsCd() {
		return this.stgSttsCd;
	}

	public void setStgSttsCd(String stgSttsCd) {
		this.stgSttsCd = stgSttsCd;
	}

}