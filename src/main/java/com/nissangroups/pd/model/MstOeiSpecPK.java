package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_OEI_SPEC database table.
 * 
 */
@Embeddable
public class MstOeiSpecPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="PROD_FMY_CD")
	private String prodFmyCd;

	@Column(name="PROD_STAGE_CD")
	private String prodStageCd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="PCK_CD")
	private String pckCd;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="ADTNL_SPEC_CD")
	private String adtnlSpecCd;

	public MstOeiSpecPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getProdFmyCd() {
		return this.prodFmyCd;
	}
	public void setProdFmyCd(String prodFmyCd) {
		this.prodFmyCd = prodFmyCd;
	}
	public String getProdStageCd() {
		return this.prodStageCd;
	}
	public void setProdStageCd(String prodStageCd) {
		this.prodStageCd = prodStageCd;
	}
	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}
	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}
	public String getPckCd() {
		return this.pckCd;
	}
	public void setPckCd(String pckCd) {
		this.pckCd = pckCd;
	}
	public String getSpecDestnCd() {
		return this.specDestnCd;
	}
	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
	}
	public String getAdtnlSpecCd() {
		return this.adtnlSpecCd;
	}
	public void setAdtnlSpecCd(String adtnlSpecCd) {
		this.adtnlSpecCd = adtnlSpecCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstOeiSpecPK)) {
			return false;
		}
		MstOeiSpecPK castOther = (MstOeiSpecPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.prodFmyCd.equals(castOther.prodFmyCd)
			&& this.prodStageCd.equals(castOther.prodStageCd)
			&& this.appldMdlCd.equals(castOther.appldMdlCd)
			&& this.pckCd.equals(castOther.pckCd)
			&& this.specDestnCd.equals(castOther.specDestnCd)
			&& this.adtnlSpecCd.equals(castOther.adtnlSpecCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.prodFmyCd.hashCode();
		hash = hash * prime + this.prodStageCd.hashCode();
		hash = hash * prime + this.appldMdlCd.hashCode();
		hash = hash * prime + this.pckCd.hashCode();
		hash = hash * prime + this.specDestnCd.hashCode();
		hash = hash * prime + this.adtnlSpecCd.hashCode();
		
		return hash;
	}
}