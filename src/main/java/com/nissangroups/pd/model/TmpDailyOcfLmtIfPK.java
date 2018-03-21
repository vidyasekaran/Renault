package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TMP_DAILY_OCF_LMT_IF database table.
 * 
 */
@Embeddable
public class TmpDailyOcfLmtIfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POR_CD")
	private String porCd;

	private String period;

	@Column(name="CAR_SRS")
	private String carSrs;

	@Column(name="MAPS_SYMBL")
	private String mapsSymbl;

	@Column(name="OCF_REG_IDNTCTN_CD")
	private String ocfRegIdntctnCd;

	@Column(name="PLNT_CD")
	private String plntCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="FRME_SRT_CD")
	private String frmeSrtCd;

	@Column(name="OCF_MDL_GRP")
	private String ocfMdlGrp;

	@Column(name="OCF_IDNTCTN_CD")
	private String ocfIdntctnCd;

	public TmpDailyOcfLmtIfPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getPeriod() {
		return this.period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getCarSrs() {
		return this.carSrs;
	}
	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
	}
	public String getMapsSymbl() {
		return this.mapsSymbl;
	}
	public void setMapsSymbl(String mapsSymbl) {
		this.mapsSymbl = mapsSymbl;
	}
	public String getOcfRegIdntctnCd() {
		return this.ocfRegIdntctnCd;
	}
	public void setOcfRegIdntctnCd(String ocfRegIdntctnCd) {
		this.ocfRegIdntctnCd = ocfRegIdntctnCd;
	}
	public String getPlntCd() {
		return this.plntCd;
	}
	public void setPlntCd(String plntCd) {
		this.plntCd = plntCd;
	}
	public String getLineClass() {
		return this.lineClass;
	}
	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}
	public String getFrmeSrtCd() {
		return this.frmeSrtCd;
	}
	public void setFrmeSrtCd(String frmeSrtCd) {
		this.frmeSrtCd = frmeSrtCd;
	}
	public String getOcfMdlGrp() {
		return this.ocfMdlGrp;
	}
	public void setOcfMdlGrp(String ocfMdlGrp) {
		this.ocfMdlGrp = ocfMdlGrp;
	}
	public String getOcfIdntctnCd() {
		return this.ocfIdntctnCd;
	}
	public void setOcfIdntctnCd(String ocfIdntctnCd) {
		this.ocfIdntctnCd = ocfIdntctnCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TmpDailyOcfLmtIfPK)) {
			return false;
		}
		TmpDailyOcfLmtIfPK castOther = (TmpDailyOcfLmtIfPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.period.equals(castOther.period)
			&& this.carSrs.equals(castOther.carSrs)
			&& this.mapsSymbl.equals(castOther.mapsSymbl)
			&& this.ocfRegIdntctnCd.equals(castOther.ocfRegIdntctnCd)
			&& this.plntCd.equals(castOther.plntCd)
			&& this.lineClass.equals(castOther.lineClass)
			&& this.frmeSrtCd.equals(castOther.frmeSrtCd)
			&& this.ocfMdlGrp.equals(castOther.ocfMdlGrp)
			&& this.ocfIdntctnCd.equals(castOther.ocfIdntctnCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.period.hashCode();
		hash = hash * prime + this.carSrs.hashCode();
		hash = hash * prime + this.mapsSymbl.hashCode();
		hash = hash * prime + this.ocfRegIdntctnCd.hashCode();
		hash = hash * prime + this.plntCd.hashCode();
		hash = hash * prime + this.lineClass.hashCode();
		hash = hash * prime + this.frmeSrtCd.hashCode();
		hash = hash * prime + this.ocfMdlGrp.hashCode();
		hash = hash * prime + this.ocfIdntctnCd.hashCode();
		
		return hash;
	}
}