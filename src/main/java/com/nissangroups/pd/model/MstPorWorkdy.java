package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the MST_POR_WORKDY database table.
 * 
 */
@Entity
@Table(name="MST_POR_WORKDY")
@NamedQuery(name="MstPorWorkdy.findAll", query="SELECT m FROM MstPorWorkdy m")
public class MstPorWorkdy implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MstPorWorkdyPK id;

	@Column(name="CRTD_BY")
	private String crtdBy;

	@Column(name="CRTD_DT")
	private Timestamp crtdDt;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	@Column(name="WORKDY_FLG")
	private String workdyFlg;

	public MstPorWorkdy() {
	}

	public MstPorWorkdyPK getId() {
		return this.id;
	}

	public void setId(MstPorWorkdyPK id) {
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

	public String getWorkdyFlg() {
		return this.workdyFlg;
	}

	public void setWorkdyFlg(String workdyFlg) {
		this.workdyFlg = workdyFlg;
	}

}