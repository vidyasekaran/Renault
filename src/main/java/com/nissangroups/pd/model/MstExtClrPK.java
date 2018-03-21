package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the MST_EXT_CLR database table.
 * 
 */
@Embeddable
public class MstExtClrPK implements Serializable {
	//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;

		@Column(name="PROD_FMY_CD")
		private String prodFmyCd;

		@Column(name="EXT_CLR_CD")
		private String extClrCd;

		@Column(name="GSIS_REGION_GRND")
		private String gsisRegionGrnd;

		@Column(name="PROD_STAGE_CD")
		private String prodStageCd;

		public MstExtClrPK() {
		}
		public String getProdFmyCd() {
			return this.prodFmyCd;
		}
		public void setProdFmyCd(String prodFmyCd) {
			this.prodFmyCd = prodFmyCd;
		}
		public String getExtClrCd() {
			return this.extClrCd;
		}
		public void setExtClrCd(String extClrCd) {
			this.extClrCd = extClrCd;
		}
		public String getGsisRegionGrnd() {
			return this.gsisRegionGrnd;
		}
		public void setGsisRegionGrnd(String gsisRegionGrnd) {
			this.gsisRegionGrnd = gsisRegionGrnd;
		}
		public String getProdStageCd() {
			return this.prodStageCd;
		}
		public void setProdStageCd(String prodStageCd) {
			this.prodStageCd = prodStageCd;
		}

		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof MstExtClrPK)) {
				return false;
			}
			MstExtClrPK castOther = (MstExtClrPK)other;
			return 
				this.prodFmyCd.equals(castOther.prodFmyCd)
				&& this.extClrCd.equals(castOther.extClrCd)
				&& this.gsisRegionGrnd.equals(castOther.gsisRegionGrnd)
				&& this.prodStageCd.equals(castOther.prodStageCd);
		}

		public int hashCode() {
			final int prime = 31;
			int hash = 17;
			hash = hash * prime + this.prodFmyCd.hashCode();
			hash = hash * prime + this.extClrCd.hashCode();
			hash = hash * prime + this.gsisRegionGrnd.hashCode();
			hash = hash * prime + this.prodStageCd.hashCode();
			
			return hash;
		}
}