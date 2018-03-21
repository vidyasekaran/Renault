package com.nissangroups.pd.r000020.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class R000020ExNoBean {
	

	/** Variable por cd. */
	@Column(name = "POR_CD")
	String porCd;

	/** Variable applied model cd. */
	@Id
	@Column(name = "SEQ_ID")
	String seqId;

	/** Variable pack cd. */
	@Column(name = "PROD_MNTH")
	String prdMnth;
	
	
	/** Variable production family cd. */
	@Column(name = "OSEI_ID")
	String oseiId;

	/** Variable car series. */
	@Column(name = "OEI_BUYER_ID")
	String oeiByrId;

	/** Variable exterior color cd. */
	@Column(name = "EX_NO")
	String exNo;
	
	/** Variable exterior color cd. */
	@Column(name = "ORDRTK_BASE_MNTH")
	String ordrTkBsMnth;
	
	/** Variable exterior color cd. */
	@Column(name = "POT_CD")
	String potCd;




	/**
	 * @return the seqId
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	/**
	 * @return the prdMnth
	 */
	public String getPrdMnth() {
		return prdMnth;
	}

	/**
	 * @param prdMnth the prdMnth to set
	 */
	public void setPrdMnth(String prdMnth) {
		this.prdMnth = prdMnth;
	}

	/**
	 * @return the oseiId
	 */
	public String getOseiId() {
		return oseiId;
	}

	/**
	 * @param oseiId the oseiId to set
	 */
	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	/**
	 * @return the oeiByrId
	 */
	public String getOeiByrId() {
		return oeiByrId;
	}

	/**
	 * @param oeiByrId the oeiByrId to set
	 */
	public void setOeiByrId(String oeiByrId) {
		this.oeiByrId = oeiByrId;
	}

	/**
	 * @return the exNo
	 */
	public String getExNo() {
		return exNo;
	}

	/**
	 * @param exNo the exNo to set
	 */
	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	/**
	 * @return the porCd
	 */
	public String getPorCd() {
		return porCd;
	}

	/**
	 * @param porCd the porCd to set
	 */
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	/**
	 * @return the ordrTkBsMnth
	 */
	public String getOrdrTkBsMnth() {
		return ordrTkBsMnth;
	}

	/**
	 * @param ordrTkBsMnth the ordrTkBsMnth to set
	 */
	public void setOrdrTkBsMnth(String ordrTkBsMnth) {
		this.ordrTkBsMnth = ordrTkBsMnth;
	}

	/**
	 * @return the potCd
	 */
	public String getPotCd() {
		return potCd;
	}

	/**
	 * @param potCd the potCd to set
	 */
	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}


}
