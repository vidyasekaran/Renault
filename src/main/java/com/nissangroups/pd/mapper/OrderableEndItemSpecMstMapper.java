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



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * The Class OrderableEndItemSpecMstMapper.
 */
@Entity
public class OrderableEndItemSpecMstMapper {
	
	/** Variable por cd. */
	/*@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int id;
*/	@Id
	@Column(name = "POR_CD")
	String POR_CD;

/** Variable buyer cd. */
@Column(name = "BUYER_CD")
private
String BUYER_CD;

   /** Variable applied model cd. */
   @Column(name="APPLIED_MODEL_CD")
   String APPLIED_MODEL_CD;
   
   /** Variable pack cd. */
   @Column(name="PACK_CD")
   String PACK_CD;
   
   /** Variable exterior color cd. */
   @Column(name="EXTERIOR_COLOR_CD")
   String EXTERIOR_COLOR_CD;
   
   /** Variable interior color cd. */
   @Column(name="INTERIOR_COLOR_CD")
   String INTERIOR_COLOR_CD;
   
   /** Variable spec destination cd. */
   @Column(name="SPEC_DESTINATION_CD")
   String SPEC_DESTINATION_CD;
   
 
   /** Variable osei adopt date. */
   @Column(name="OSEI_ADOPT_DATE")
   String OSEI_ADOPT_DATE;
   
   /** Variable osei abolish date. */
   @Column(name="OSEI_ABOLISH_DATE")

   String OSEI_ABOLISH_DATE;
   
   /** Variable additional spec cd. */
   @Column(name="ADDITIONAL_SPEC_CD")
   String ADDITIONAL_SPEC_CD;
   
  
 
   
   
   
   /** Variable production family cd. */
   @Column(name="PRODUCTION_FAMILY_CD")

   String PRODUCTION_FAMILY_CD;
   
   /** Variable ocf region cd. */
   @Column(name="OCF_REGION_CD")
   String OCF_REGION_CD;
   
   
   /** Variable uk osei id. */
   @Column(name="UK_OSEI_ID")
   String UK_OSEI_ID;
   
   /** Variable production stage cd. */
   @Column(name="PRODUCTION_STAGE_CD")

   String PRODUCTION_STAGE_CD;
   
   /** Variable car series. */
   @Column(name="CAR_SERIES")
private

   String CAR_SERIES;
   
	/**
	 * Gets the applied model cd.
	 *
	 * @return the aPPLIED_MODEL_CD
	 */
public String getAPPLIED_MODEL_CD() {
	return APPLIED_MODEL_CD;
}

/**
 * Sets the applied model cd.
 *
 * @param aPPLIED_MODEL_CD the aPPLIED_MODEL_CD to set
 */
public void setAPPLIED_MODEL_CD(String aPPLIED_MODEL_CD) {
	APPLIED_MODEL_CD = aPPLIED_MODEL_CD;
}

/**
 * Gets the pack cd.
 *
 * @return the pACK_CD
 */
public String getPACK_CD() {
	return PACK_CD;
}

/**
 * Sets the pack cd.
 *
 * @param pACK_CD the pACK_CD to set
 */
public void setPACK_CD(String pACK_CD) {
	PACK_CD = pACK_CD;
}

/**
 * Gets the spec destination cd.
 *
 * @return the sPEC_DESTINATION_CD
 */
public String getSPEC_DESTINATION_CD() {
	return SPEC_DESTINATION_CD;
}

/**
 * Sets the spec destination cd.
 *
 * @param sPEC_DESTINATION_CD the sPEC_DESTINATION_CD to set
 */
public void setSPEC_DESTINATION_CD(String sPEC_DESTINATION_CD) {
	SPEC_DESTINATION_CD = sPEC_DESTINATION_CD;
}





/**
 * Gets the uk osei id.
 *
 * @return the uK_OSEI_ID
 */
public String getUK_OSEI_ID() {
	return UK_OSEI_ID;
}

/**
 * Sets the uk osei id.
 *
 * @param uK_OSEI_ID the uK_OSEI_ID to set
 */
public void setUK_OSEI_ID(String uK_OSEI_ID) {
	UK_OSEI_ID = uK_OSEI_ID;
}

/**
 * Gets the exterior color cd.
 *
 * @return the eXTERIOR_COLOR_CD
 */
public String getEXTERIOR_COLOR_CD() {
	return EXTERIOR_COLOR_CD;
}

/**
 * Sets the exterior color cd.
 *
 * @param eXTERIOR_COLOR_CD the eXTERIOR_COLOR_CD to set
 */
public void setEXTERIOR_COLOR_CD(String eXTERIOR_COLOR_CD) {
	EXTERIOR_COLOR_CD = eXTERIOR_COLOR_CD;
}

/**
 * Gets the interior color cd.
 *
 * @return the iNTERIOR_COLOR_CD
 */
public String getINTERIOR_COLOR_CD() {
	return INTERIOR_COLOR_CD;
}

/**
 * Sets the interior color cd.
 *
 * @param iNTERIOR_COLOR_CD the iNTERIOR_COLOR_CD to set
 */
public void setINTERIOR_COLOR_CD(String iNTERIOR_COLOR_CD) {
	INTERIOR_COLOR_CD = iNTERIOR_COLOR_CD;
}

/**
 * Gets the osei adopt date.
 *
 * @return the oSEI_ADOPT_DATE
 */
public String getOSEI_ADOPT_DATE() {
	return OSEI_ADOPT_DATE;
}

/**
 * Sets the osei adopt date.
 *
 * @param oSEI_ADOPT_DATE the oSEI_ADOPT_DATE to set
 */
public void setOSEI_ADOPT_DATE(String oSEI_ADOPT_DATE) {
	OSEI_ADOPT_DATE = oSEI_ADOPT_DATE;
}


/**
 * Gets the ocf region cd.
 *
 * @return the oCF_REGION_CD
 */
public String getOCF_REGION_CD() {
	return OCF_REGION_CD;
}

/**
 * Sets the ocf region cd.
 *
 * @param oCF_REGION_CD the oCF_REGION_CD to set
 */
public void setOCF_REGION_CD(String oCF_REGION_CD) {
	OCF_REGION_CD = oCF_REGION_CD;
}



	/**
	 * Gets the por cd.
	 *
	 * @return the id
	 */
/*	public int getId() {
		return id;
	}

	*//**
	 * @param id the id to set
	 *//*
	public void setId(int id) {
		this.id = id;
	}
*/
	/**
	 * @return the pOR_CD
	 */
	public String getPOR_CD() {
		return POR_CD;
	}

	/**
	 * Sets the por cd.
	 *
	 * @param pOR_CD the pOR_CD to set
	 */
	public void setPOR_CD(String pOR_CD) {
		POR_CD = pOR_CD;
	}

	/**
	 * Gets the additional spec cd.
	 *
	 * @return the additional spec cd
	 */
	public String getADDITIONAL_SPEC_CD() {
		return ADDITIONAL_SPEC_CD;
	}

	/**
	 * Sets the additional spec cd.
	 *
	 * @param aDDITIONAL_SPEC_CD the new additional spec cd
	 */
	public void setADDITIONAL_SPEC_CD(String aDDITIONAL_SPEC_CD) {
		ADDITIONAL_SPEC_CD = aDDITIONAL_SPEC_CD;
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

	/**
	 * Gets the osei abolish date.
	 *
	 * @return the osei abolish date
	 */
	public String getOSEI_ABOLISH_DATE() {
		return OSEI_ABOLISH_DATE;
	}

	/**
	 * Sets the osei abolish date.
	 *
	 * @param oSEI_ABOLISH_DATE the new osei abolish date
	 */
	public void setOSEI_ABOLISH_DATE(String oSEI_ABOLISH_DATE) {
		OSEI_ABOLISH_DATE = oSEI_ABOLISH_DATE;
	}

	/**
	 * Gets the production stage cd.
	 *
	 * @return the production stage cd
	 */
	public String getPRODUCTION_STAGE_CD() {
		return PRODUCTION_STAGE_CD;
	}

	/**
	 * Sets the production stage cd.
	 *
	 * @param pRODUCTION_STAGE_CD the new production stage cd
	 */
	public void setPRODUCTION_STAGE_CD(String pRODUCTION_STAGE_CD) {
		PRODUCTION_STAGE_CD = pRODUCTION_STAGE_CD;
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
	 * @return the por
	 */
	


}
