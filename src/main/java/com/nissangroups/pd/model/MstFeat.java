package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.Date;



/**
 * The persistent class for the MST_FEAT database table.
 * 
 */
@Entity
@Table(name="MST_FEAT")
@NamedQuery(name="MstFeat.findAll", query="SELECT m FROM MstFeat m")
public class MstFeat implements Serializable {
	

	@EmbeddedId
	private MstFeatPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FEAT_ABLSH_DATE")
	private String featAblshDate;

	@Column(name="FEAT_ADPT_DATE")
	private String featAdptDate;

	@Column(name="FEAT_LNG_DESC")
	private String featLngDesc;

	@Column(name="FEAT_SHRT_DESC")
	private String featShrtDesc;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstFeatGrp
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="FEAT_GRP_CD", referencedColumnName="FEAT_GRP_CD", insertable=false, updatable=false),
		@JoinColumn(name="POR_CD", referencedColumnName="POR_CD", insertable=false, updatable=false)
		})
	private MstFeatGrp mstFeatGrp;

	//bi-directional many-to-one association to MstFeatType
	@ManyToOne
	@JoinColumn(name="FEAT_TYPE_CD", insertable=false, updatable=false)
	private MstFeatType mstFeatType;

	@PrePersist
	public void creationTime() {
		Date date = new Date();
		crtdDt = new Timestamp(date.getTime());
	}

	@PreUpdate
	public void updationTime() {
		Date date = new Date();
		updtdDt = new Timestamp(date.getTime());
	}

	public MstFeat() {
	}

	public MstFeatPK getId() {
		return this.id;
	}

	public void setId(MstFeatPK id) {
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

	public String getFeatAblshDate() {
		return this.featAblshDate;
	}

	public void setFeatAblshDate(String featAblshDate) {
		this.featAblshDate = featAblshDate;
	}

	public String getFeatAdptDate() {
		return this.featAdptDate;
	}

	public void setFeatAdptDate(String featAdptDate) {
		this.featAdptDate = featAdptDate;
	}

	public String getFeatLngDesc() {
		return this.featLngDesc;
	}

	public void setFeatLngDesc(String featLngDesc) {
		this.featLngDesc = featLngDesc;
	}

	public String getFeatShrtDesc() {
		return this.featShrtDesc;
	}

	public void setFeatShrtDesc(String featShrtDesc) {
		this.featShrtDesc = featShrtDesc;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
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

	public MstFeatGrp getMstFeatGrp() {
		return this.mstFeatGrp;
	}

	public void setMstFeatGrp(MstFeatGrp mstFeatGrp) {
		this.mstFeatGrp = mstFeatGrp;
	}

	public MstFeatType getMstFeatType() {
		return this.mstFeatType;
	}

	public void setMstFeatType(MstFeatType mstFeatType) {
		this.mstFeatType = mstFeatType;
	}

}