/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-MstOeiSpecMapper
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.mapper;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * The Class MstOeiSpecMapper.
 */
@Entity
public class MstOeiSpecMapper {
	
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

   /** Variable appld mdl cd. */
   @Column(name="APPLD_MDL_CD")
   String APPLD_MDL_CD;
   
   /** Variable pck cd. */
   @Column(name="PCK_CD")
   String PCK_CD;
   
   /** Variable ext clr cd. */
   @Column(name="EXT_CLR_CD")
   String EXT_CLR_CD;
   
   /** Variable int clr cd. */
   @Column(name="INT_CLR_CD")
   String INT_CLR_CD;
   
   /** Variable spec destnn cd. */
   @Column(name="SPEC_DESTNN_CD")
   String SPEC_DESTNN_CD;
   
 
   /** Variable osei adpt date. */
   @Column(name="OSEI_ADPT_DATE")
   String OSEI_ADPT_DATE;
   
   /** Variable osei ablsh date. */
   @Column(name="OSEI_ABLSH_DATE")

   String OSEI_ABLSH_DATE;
   
   /** Variable adtnl spec cd. */
   @Column(name="ADTNL_SPEC_CD")
   String ADTNL_SPEC_CD;
   
  
 
   
   
   
   /** Variable prod fmy cd. */
   @Column(name="PROD_FMY_CD")

   String PROD_FMY_CD;
   
   /** Variable ocf region cd. */
   @Column(name="OCF_REGION_CD")
   String OCF_REGION_CD;
   
   
   /** Variable uk osei id. */
   @Column(name="UK_OSEI_ID")
   String UK_OSEI_ID;
   
   /** Variable prod stage cd. */
   @Column(name="PROD_STAGE_CD")

   String PROD_STAGE_CD;
   
   /** Variable car srs. */
   @Column(name="CAR_SRS")
private

   String CAR_SRS;
   
	/**
	 * Gets the appld mdl cd.
	 *
	 * @return the aPPLIED_MDL_CD
	 */
public String getAPPLD_MDL_CD() {
	return APPLD_MDL_CD;
}

/**
 * Sets the appld mdl cd.
 *
 * @param aPPLIED_MDL_CD the aPPLIED_MDL_CD to set
 */
public void setAPPLD_MDL_CD(String aPPLIED_MDL_CD) {
	APPLD_MDL_CD = aPPLIED_MDL_CD;
}

/**
 * Gets the pck cd.
 *
 * @return the pACK_CD
 */
public String getPCK_CD() {
	return PCK_CD;
}

/**
 * Sets the pck cd.
 *
 * @param pACK_CD the pACK_CD to set
 */
public void setPCK_CD(String pACK_CD) {
	PCK_CD = pACK_CD;
}

/**
 * Gets the spec destnn cd.
 *
 * @return the sPEC_DESTNN_CD
 */
public String getSPEC_DESTNN_CD() {
	return SPEC_DESTNN_CD;
}

/**
 * Sets the spec destnn cd.
 *
 * @param sPEC_DESTNN_CD the sPEC_DESTNN_CD to set
 */
public void setSPEC_DESTNN_CD(String sPEC_DESTNN_CD) {
	SPEC_DESTNN_CD = sPEC_DESTNN_CD;
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
 * Gets the ext clr cd.
 *
 * @return the eXTERIOR_CLR_CD
 */
public String getEXT_CLR_CD() {
	return EXT_CLR_CD;
}

/**
 * Sets the ext clr cd.
 *
 * @param eXTERIOR_CLR_CD the eXTERIOR_CLR_CD to set
 */
public void setEXT_CLR_CD(String eXTERIOR_CLR_CD) {
	EXT_CLR_CD = eXTERIOR_CLR_CD;
}

/**
 * Gets the int clr cd.
 *
 * @return the iNTERIOR_CLR_CD
 */
public String getINT_CLR_CD() {
	return INT_CLR_CD;
}

/**
 * Sets the int clr cd.
 *
 * @param iNTERIOR_CLR_CD the iNTERIOR_CLR_CD to set
 */
public void setINT_CLR_CD(String iNTERIOR_CLR_CD) {
	INT_CLR_CD = iNTERIOR_CLR_CD;
}

/**
 * Gets the osei adpt date.
 *
 * @return the oSEI_ADPT_DATE
 */
public String getOSEI_ADPT_DATE() {
	return OSEI_ADPT_DATE;
}

/**
 * Sets the osei adpt date.
 *
 * @param oSEI_ADPT_DATE the oSEI_ADPT_DATE to set
 */
public void setOSEI_ADPT_DATE(String oSEI_ADPT_DATE) {
	OSEI_ADPT_DATE = oSEI_ADPT_DATE;
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
	 * @param aDDITIONAL_SPEC_CD the new adtnl spec cd
	 */
	public void setADTNL_SPEC_CD(String aDDITIONAL_SPEC_CD) {
		ADTNL_SPEC_CD = aDDITIONAL_SPEC_CD;
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
	 * @param pRODUCTION_FMY_CD the new prod fmy cd
	 */
	public void setPROD_FMY_CD(String pRODUCTION_FMY_CD) {
		PROD_FMY_CD = pRODUCTION_FMY_CD;
	}

	/**
	 * Gets the osei ablsh date.
	 *
	 * @return the osei ablsh date
	 */
	public String getOSEI_ABLSH_DATE() {
		return OSEI_ABLSH_DATE;
	}

	/**
	 * Sets the osei ablsh date.
	 *
	 * @param oSEI_ABLSH_DATE the new osei ablsh date
	 */
	public void setOSEI_ABLSH_DATE(String oSEI_ABLSH_DATE) {
		OSEI_ABLSH_DATE = oSEI_ABLSH_DATE;
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
	 * @param pRODUCTION_STAGE_CD the new prod stage cd
	 */
	public void setPROD_STAGE_CD(String pRODUCTION_STAGE_CD) {
		PROD_STAGE_CD = pRODUCTION_STAGE_CD;
	}

	/**
	 * Gets the car srs.
	 *
	 * @return the car srs
	 */
	public String getCAR_SRS() {
		return CAR_SRS;
	}

	/**
	 * Sets the car srs.
	 *
	 * @param cAR_SRS the new car srs
	 */
	public void setCAR_SRS(String cAR_SRS) {
		CAR_SRS = cAR_SRS;
	}

	/**
	 * @return the por
	 */
	


}
