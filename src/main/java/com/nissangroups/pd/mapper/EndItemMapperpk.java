/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000003/R000002
 * Module          :S SPEC
 * Process Outline :Create POR CAR SERIES MASTER & OSEI Production Type Master
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z013865(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class EndItemMapperpk.
 *
 * @author z002548
 */
@Embeddable
public class EndItemMapperpk implements Serializable{
	
	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	 
 	/** Variable por cd. */
 	@Column(name = "POR_CD")
	 String POR_CD;

	 /** Variable appld mdl cd. */
 	@Column(name="APPLD_MDL_CD")
	 String APPLD_MDL_CD;
	 
 	/** Variable pck cd. */
 	@Column(name="PCK_CD")
	 String PCK_CD;

	 /** Variable spec destn cd. */
 	@Column(name="SPEC_DESTN_CD")
	 String SPEC_DESTN_CD;

	/* @Column(name="UK_OEI_BUYER_ID")
	 String UK_OEI_BUYER_ID;
*/
	/* @Column(name="OPTN_SPEC_CODE")
	 String OPTN_SPEC_CODE;*/

	
	 /** Variable osei id. */
	@Column(name="OSEI_ID")
	 String OSEI_ID;

	 /** Variable ext clr cd. */
 	@Column(name="EXT_CLR_CD")
	 String EXT_CLR_CD;

	 /** Variable int clr cd. */
 	@Column(name="INT_CLR_CD")
	 String INT_CLR_CD;

	 /** Variable osei adpt date. */
 	@Column(name="OSEI_ADPT_DATE")
	 String OSEI_ADPT_DATE;

	 /*@Column(name="OCF_BUYER_GRP_CD")
	 String OCF_BUYER_GRP_CD;*/

	 /** Variable ocf region cd. */
 	@Column(name="OCF_REGION_CD")
	 String OCF_REGION_CD;
/*
	 @Column(name="ABLSH_DATE")
	 String ABLSH_DATE;*/

	

	 /** Variable end itm stts cd. */
@Column(name="END_ITM_STTS_CD")
	 String END_ITM_STTS_CD;
	 
	 /** Variable prod fmy cd. */
 	@Column(name="PROD_FMY_CD")
	 String PROD_FMY_CD;

	 /** Variable prod stage cd. */
 	@Column(name="PROD_STAGE_CD")
	 String PROD_STAGE_CD;
	 
 	/** Variable adtnl spec cd. */
 	@Column(name="ADTNL_SPEC_CD")
	 String ADTNL_SPEC_CD;

	 /** Variable buyer cd. */
 	@Column(name="BUYER_CD")
	 String BUYER_CD;

	 /** Variable oei spec id. */
 	@Column(name="OEI_SPEC_ID")
	
	 String OEI_SPEC_ID;

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
	 * Gets the appld mdl cd.
	 *
	 * @return the appld mdl cd
	 */
	public String getAPPLD_MDL_CD() {
		return APPLD_MDL_CD;
	}

	/**
	 * Sets the appld mdl cd.
	 *
	 * @param aPPLD_MDL_CD the new appld mdl cd
	 */
	public void setAPPLD_MDL_CD(String aPPLD_MDL_CD) {
		APPLD_MDL_CD = aPPLD_MDL_CD;
	}

	/**
	 * Gets the pck cd.
	 *
	 * @return the pck cd
	 */
	public String getPCK_CD() {
		return PCK_CD;
	}

	/**
	 * Sets the pck cd.
	 *
	 * @param pCK_CD the new pck cd
	 */
	public void setPCK_CD(String pCK_CD) {
		PCK_CD = pCK_CD;
	}

	/**
	 * Gets the spec destn cd.
	 *
	 * @return the spec destn cd
	 */
	public String getSPEC_DESTN_CD() {
		return SPEC_DESTN_CD;
	}

	/**
	 * Sets the spec destn cd.
	 *
	 * @param sPEC_DESTN_CD the new spec destn cd
	 */
	public void setSPEC_DESTN_CD(String sPEC_DESTN_CD) {
		SPEC_DESTN_CD = sPEC_DESTN_CD;
	}

	

	/**
	 * Gets the osei id.
	 *
	 * @return the osei id
	 */
	public String getOSEI_ID() {
		return OSEI_ID;
	}

	/**
	 * Sets the osei id.
	 *
	 * @param oSEI_ID the new osei id
	 */
	public void setOSEI_ID(String oSEI_ID) {
		OSEI_ID = oSEI_ID;
	}

	/**
	 * Gets the ext clr cd.
	 *
	 * @return the ext clr cd
	 */
	public String getEXT_CLR_CD() {
		return EXT_CLR_CD;
	}

	/**
	 * Sets the ext clr cd.
	 *
	 * @param eXT_CLR_CD the new ext clr cd
	 */
	public void setEXT_CLR_CD(String eXT_CLR_CD) {
		EXT_CLR_CD = eXT_CLR_CD;
	}

	/**
	 * Gets the int clr cd.
	 *
	 * @return the int clr cd
	 */
	public String getINT_CLR_CD() {
		return INT_CLR_CD;
	}

	/**
	 * Sets the int clr cd.
	 *
	 * @param iNT_CLR_CD the new int clr cd
	 */
	public void setINT_CLR_CD(String iNT_CLR_CD) {
		INT_CLR_CD = iNT_CLR_CD;
	}

	/**
	 * Gets the osei adpt date.
	 *
	 * @return the osei adpt date
	 */
	public String getOSEI_ADPT_DATE() {
		return OSEI_ADPT_DATE;
	}

	/**
	 * Sets the osei adpt date.
	 *
	 * @param oSEI_ADPT_DATE the new osei adpt date
	 */
	public void setOSEI_ADPT_DATE(String oSEI_ADPT_DATE) {
		OSEI_ADPT_DATE = oSEI_ADPT_DATE;
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
	 * Gets the end itm stts cd.
	 *
	 * @return the end itm stts cd
	 */
	public String getEND_ITM_STTS_CD() {
		return END_ITM_STTS_CD;
	}

	/**
	 * Sets the end itm stts cd.
	 *
	 * @param eND_ITM_STTS_CD the new end itm stts cd
	 */
	public void setEND_ITM_STTS_CD(String eND_ITM_STTS_CD) {
		END_ITM_STTS_CD = eND_ITM_STTS_CD;
	}

	/**
	 * Gets the prod fmy cd.
	 *
	 * @return the prod fmy cd
	 */
	public String getPROD_FMY_CD() {
		return PROD_FMY_CD;
	}

	/**
	 * Sets the prod fmy cd.
	 *
	 * @param pROD_FMY_CD the new prod fmy cd
	 */
	public void setPROD_FMY_CD(String pROD_FMY_CD) {
		PROD_FMY_CD = pROD_FMY_CD;
	}

	/**
	 * Gets the prod stage cd.
	 *
	 * @return the prod stage cd
	 */
	public String getPROD_STAGE_CD() {
		return PROD_STAGE_CD;
	}

	/**
	 * Sets the prod stage cd.
	 *
	 * @param pROD_STAGE_CD the new prod stage cd
	 */
	public void setPROD_STAGE_CD(String pROD_STAGE_CD) {
		PROD_STAGE_CD = pROD_STAGE_CD;
	}

	/**
	 * Gets the adtnl spec cd.
	 *
	 * @return the adtnl spec cd
	 */
	public String getADTNL_SPEC_CD() {
		return ADTNL_SPEC_CD;
	}

	/**
	 * Sets the adtnl spec cd.
	 *
	 * @param aDTNL_SPEC_CD the new adtnl spec cd
	 */
	public void setADTNL_SPEC_CD(String aDTNL_SPEC_CD) {
		ADTNL_SPEC_CD = aDTNL_SPEC_CD;
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
	 * Gets the oei spec id.
	 *
	 * @return the oei spec id
	 */
	public String getOEI_SPEC_ID() {
		return OEI_SPEC_ID;
	}

	/**
	 * Sets the oei spec id.
	 *
	 * @param oEI_SPEC_ID the new oei spec id
	 */
	public void setOEI_SPEC_ID(String oEI_SPEC_ID) {
		OEI_SPEC_ID = oEI_SPEC_ID;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EndItemMapperpk)) {
			return false;
		}
		EndItemMapperpk castOther = (EndItemMapperpk)other;
		return 
			this.POR_CD.equals(castOther.POR_CD)
			&& this.APPLD_MDL_CD.equals(castOther.APPLD_MDL_CD)
			&& this.PCK_CD.equals(castOther.PCK_CD)
			&& this.SPEC_DESTN_CD.equals(castOther.SPEC_DESTN_CD)
			//&& this.CAR_SRS.equals(castOther.CAR_SRS)
			&& this.OSEI_ID.equals(castOther.OSEI_ID)
			&& this.EXT_CLR_CD.equals(castOther.EXT_CLR_CD)
			&& this.INT_CLR_CD.equals(castOther.INT_CLR_CD)
			&& this.OSEI_ADPT_DATE.equals(castOther.OSEI_ADPT_DATE)
			&& this.OCF_REGION_CD.equals(castOther.OCF_REGION_CD)
			&& this.END_ITM_STTS_CD.equals(castOther.END_ITM_STTS_CD)
			&& this.PROD_FMY_CD.equals(castOther.PROD_FMY_CD)
			&& this.PROD_STAGE_CD.equals(castOther.PROD_STAGE_CD)
			&& this.ADTNL_SPEC_CD.equals(castOther.ADTNL_SPEC_CD)
			&& this.BUYER_CD.equals(castOther.BUYER_CD)
			&& this.OEI_SPEC_ID.equals(castOther.OEI_SPEC_ID);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		
		hash = hash * prime + this.POR_CD.hashCode();
		hash = hash * prime + this.APPLD_MDL_CD.hashCode();
		hash = hash * prime + this.PCK_CD.hashCode();
		hash = hash * prime + this.SPEC_DESTN_CD.hashCode();
		//hash = hash * prime + this.CAR_SRS.hashCode();
		hash = hash * prime + this.OSEI_ID.hashCode();
		hash = hash * prime + this.EXT_CLR_CD.hashCode();
		hash = hash * prime + this.INT_CLR_CD.hashCode();
		hash = hash * prime + this.OSEI_ADPT_DATE.hashCode();
		hash = hash * prime + this.OCF_REGION_CD.hashCode();
		hash = hash * prime + this.END_ITM_STTS_CD.hashCode();
		hash = hash * prime + this.PROD_FMY_CD.hashCode();
		hash = hash * prime + this.PROD_STAGE_CD.hashCode();
		hash = hash * prime + this.ADTNL_SPEC_CD.hashCode();
		hash = hash * prime + this.BUYER_CD.hashCode();
		hash = hash * prime + this.OEI_SPEC_ID.hashCode();
	
		
		return hash;
	}

}
