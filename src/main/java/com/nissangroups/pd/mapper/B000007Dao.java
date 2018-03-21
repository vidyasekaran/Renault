/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000007
 * Module          :O Ordering
 * Process Outline :Create OSEI Frozen Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z011479(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the mapper class for the custom B000007 custom reader.
 *
 * @author z011479
 */
@Entity
public class B000007Dao {

	
	/** Variable por cd. */
	@Column(name = "POR_CD")
	String POR_CD;

	/** Variable applied model cd. */
	@Column(name = "APPLD_MDL_CD")
	String APPLIED_MODEL_CD;

	/** Variable pack cd. */
	@Column(name = "PCK_CD")
	String PACK_CD;
	
	
	/** Variable production family cd. */
	@Column(name = "PROD_FMY_CD")
	String PRODUCTION_FAMILY_CD;

	/** Variable car series. */
	@Column(name = "CAR_SRS")
	String CAR_SERIES;

	/** Variable exterior color cd. */
	@Column(name = "EXT_CLR_CD")
	String EXTERIOR_COLOR_CD;

	/** Variable interior color cd. */
	@Column(name = "INT_CLR_CD")
	String INTERIOR_COLOR_CD;

	/** Variable spec destination cd. */
	@Column(name = "SPEC_DESTN_CD")
	String SPEC_DESTINATION_CD;

	/** Variable ocf region cd. */
	@Column(name = "OCF_REGION_CD")
	String OCF_REGION_CD;

	/** Variable uk osei id. */
	@Id
	@Column(name = "OSEI_ID")
	String UK_OSEI_ID;

	/** Variable buyer cd. */
	@Column(name = "BUYER_CD")
	String BUYER_CD;
	
	
	/**
	 * Gets the osei suspended date.
	 *
	 * @return the osei suspended date
	 */
	public String getOSEI_SUSPENDED_DATE() {
		return OSEI_SUSPENDED_DATE;
	}

	/**
	 * Sets the osei suspended date.
	 *
	 * @param oSEI_SUSPENDED_DATE the new osei suspended date
	 */
	public void setOSEI_SUSPENDED_DATE(String oSEI_SUSPENDED_DATE) {
		OSEI_SUSPENDED_DATE = oSEI_SUSPENDED_DATE;
	}

	/** Variable osei suspended date. */
	@Column(name = "OSEI_SUSPENDED_DATE")
	String OSEI_SUSPENDED_DATE;

	/**
	 * Gets the por cd.
	 *
	 * @return the por cd
	 */
	public String getPOR_CD() {
		return POR_CD;
	}

	/**
	 * Sets the por cd.
	 *
	 * @param pOR_CD the new por cd
	 */
	public void setPOR_CD(String pOR_CD) {
		POR_CD = pOR_CD;
	}

	/**
	 * Gets the pack cd.
	 *
	 * @return the pack cd
	 */
	public String getPACK_CD() {
		return PACK_CD;
	}

	/**
	 * Sets the pack cd.
	 *
	 * @param pACK_CD the new pack cd
	 */
	public void setPACK_CD(String pACK_CD) {
		PACK_CD = pACK_CD;
	}

	/**
	 * Gets the car series.
	 *
	 * @return the car series
	 */
	public String getCAR_SERIES() {
		return CAR_SERIES;
	}

	/**
	 * Sets the car series.
	 *
	 * @param cAR_SERIES the new car series
	 */
	public void setCAR_SERIES(String cAR_SERIES) {
		CAR_SERIES = cAR_SERIES;
	}

	/**
	 * Gets the exterior color cd.
	 *
	 * @return the exterior color cd
	 */
	public String getEXTERIOR_COLOR_CD() {
		return EXTERIOR_COLOR_CD;
	}

	/**
	 * Sets the exterior color cd.
	 *
	 * @param eXTERIOR_COLOR_CD the new exterior color cd
	 */
	public void setEXTERIOR_COLOR_CD(String eXTERIOR_COLOR_CD) {
		EXTERIOR_COLOR_CD = eXTERIOR_COLOR_CD;
	}

	/**
	 * Gets the interior color cd.
	 *
	 * @return the interior color cd
	 */
	public String getINTERIOR_COLOR_CD() {
		return INTERIOR_COLOR_CD;
	}

	/**
	 * Sets the interior color cd.
	 *
	 * @param iNTERIOR_COLOR_CD the new interior color cd
	 */
	public void setINTERIOR_COLOR_CD(String iNTERIOR_COLOR_CD) {
		INTERIOR_COLOR_CD = iNTERIOR_COLOR_CD;
	}

	/**
	 * Gets the spec destination cd.
	 *
	 * @return the spec destination cd
	 */
	public String getSPEC_DESTINATION_CD() {
		return SPEC_DESTINATION_CD;
	}

	/**
	 * Sets the spec destination cd.
	 *
	 * @param sPEC_DESTINATION_CD the new spec destination cd
	 */
	public void setSPEC_DESTINATION_CD(String sPEC_DESTINATION_CD) {
		SPEC_DESTINATION_CD = sPEC_DESTINATION_CD;
	}

	/**
	 * Gets the ocf region cd.
	 *
	 * @return the ocf region cd
	 */
	public String getOCF_REGION_CD() {
		return OCF_REGION_CD;
	}

	/**
	 * Sets the ocf region cd.
	 *
	 * @param oCF_REGION_CD the new ocf region cd
	 */
	public void setOCF_REGION_CD(String oCF_REGION_CD) {
		OCF_REGION_CD = oCF_REGION_CD;
	}


	/**
	 * Gets the uk osei id.
	 *
	 * @return the uk osei id
	 */
	public String getUK_OSEI_ID() {
		return UK_OSEI_ID;
	}

	/**
	 * Sets the uk osei id.
	 *
	 * @param uK_OSEI_ID the new uk osei id
	 */
	public void setUK_OSEI_ID(String uK_OSEI_ID) {
		UK_OSEI_ID = uK_OSEI_ID;
	}

	/**
	 * Gets the buyer cd.
	 *
	 * @return the buyer cd
	 */
	public String getBUYER_CD() {
		return BUYER_CD;
	}

	/**
	 * Sets the buyer cd.
	 *
	 * @param bUYER_CD the new buyer cd
	 */
	public void setBUYER_CD(String bUYER_CD) {
		BUYER_CD = bUYER_CD;
	}

	/**
	 * Gets the applied model cd.
	 *
	 * @return the applied model cd
	 */
	public String getAPPLIED_MODEL_CD() {
		return APPLIED_MODEL_CD;
	}

	/**
	 * Sets the applied model cd.
	 *
	 * @param aPPLIED_MODEL_CD the new applied model cd
	 */
	public void setAPPLIED_MODEL_CD(String aPPLIED_MODEL_CD) {
		APPLIED_MODEL_CD = aPPLIED_MODEL_CD;
	}

	/**
	 * Gets the production family cd.
	 *
	 * @return the production family cd
	 */
	public String getPRODUCTION_FAMILY_CD() {
		return PRODUCTION_FAMILY_CD;
	}

	/**
	 * Sets the production family cd.
	 *
	 * @param pRODUCTION_FAMILY_CD the new production family cd
	 */
	public void setPRODUCTION_FAMILY_CD(String pRODUCTION_FAMILY_CD) {
		PRODUCTION_FAMILY_CD = pRODUCTION_FAMILY_CD;
	}
	
	

}
