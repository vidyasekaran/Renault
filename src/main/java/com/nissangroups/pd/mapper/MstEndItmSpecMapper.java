/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-MstEndItmSpecMapper
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
 * The Class MstEndItmSpecMapper.
 */
@Entity
public class MstEndItmSpecMapper {

	/** Variable por cd. */
	/*@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int id;
	 */	@Id
	 @Column(name = "POR_CD")
	 String POR_CD;

	 /** Variable appld mdl cd. */
 	@Column(name="APPLD_MDL_CD")
	 String APPLD_MDL_CD;

	 /** Variable pck cd. */
 	@Column(name="PCK_CD")
	 String PCK_CD;

	 /** Variable spec destnn cd. */
 	@Column(name="SPEC_DESTNN_CD")
	 String SPEC_DESTNN_CD;

	 /** Variable uk oei buyer id. */
 	@Column(name="UK_OEI_BUYER_ID")
	 String UK_OEI_BUYER_ID;

	 /** Variable optn spec code. */
 	@Column(name="OPTN_SPEC_CODE")
	 String OPTN_SPEC_CODE;

	 /** Variable car srs. */
 	@Column(name="CAR_SRS")
	 String CAR_SRS;

	 /** Variable uk osei id. */
 	@Column(name="UK_OSEI_ID")
	 String UK_OSEI_ID;

	 /** Variable ext clr cd. */
 	@Column(name="EXT_CLR_CD")
	 String EXT_CLR_CD;

	 /** Variable int clr cd. */
 	@Column(name="INT_CLR_CD")
	 String INT_CLR_CD;

	 /** Variable osei adpt date. */
 	@Column(name="OSEI_ADPT_DATE")
	 String OSEI_ADPT_DATE;

	 /** Variable ocf buyer grp cd. */
 	@Column(name="OCF_BUYER_GRP_CD")
	 String OCF_BUYER_GRP_CD;

	 /** Variable ocf region cd. */
 	@Column(name="OCF_REGION_CD")
	 String OCF_REGION_CD;

	 /** Variable ablsh date. */
 	@Column(name="ABLSH_DATE")
	 String ABLSH_DATE;

	 /** Variable osei ablsh date. */
 	@Column(name="OSEI_ABLSH_DATE")

	 String OSEI_ABLSH_DATE;

	 /** Variable end itm stts cd. */
 	@Column(name="END_ITM_STTS_CD")
	 String END_ITM_STTS_CD;
	 
	 /** Variable prod fmy cd. */
 	@Column(name="PROD_FMY_CD")
	 String PROD_FMY_CD;

	 /** Variable production stage cd. */
 	@Column(name="PRODUCTION_STAGE_CD")
	 String PRODUCTION_STAGE_CD;
	 
 	/** Variable additional spec cd. */
 	@Column(name="ADDITIONAL_SPEC_CD")
	 String ADDITIONAL_SPEC_CD;

	 /** Variable buyer cd. */
 	@Column(name="BUYER_CD")
	 String BUYER_CD;

	 /** Variable uk oei spec id. */
 	@Column(name="UK_OEI_SPEC_ID")
	
	 String UK_OEI_SPEC_ID;

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
 	 * Gets the uk oei buyer id.
 	 *
 	 * @return the uK_OEI_BUYER_ID
 	 */
	 public String getUK_OEI_BUYER_ID() {
		 return UK_OEI_BUYER_ID;
	 }

	 /**
 	 * Sets the uk oei buyer id.
 	 *
 	 * @param uK_OEI_BUYER_ID the uK_OEI_BUYER_ID to set
 	 */
	 public void setUK_OEI_BUYER_ID(String uK_OEI_BUYER_ID) {
		 UK_OEI_BUYER_ID = uK_OEI_BUYER_ID;
	 }

	 /**
 	 * Gets the optn spec code.
 	 *
 	 * @return the oPTION_SPEC_CODE
 	 */
	 public String getOPTN_SPEC_CODE() {
		 return OPTN_SPEC_CODE;
	 }

	 /**
 	 * Sets the optn spec code.
 	 *
 	 * @param oPTION_SPEC_CODE the oPTION_SPEC_CODE to set
 	 */
	 public void setOPTN_SPEC_CODE(String oPTION_SPEC_CODE) {
		 OPTN_SPEC_CODE = oPTION_SPEC_CODE;
	 }

	 /**
 	 * Gets the car srs.
 	 *
 	 * @return the cAR_SRS
 	 */
	 public String getCAR_SRS() {
		 return CAR_SRS;
	 }

	 /**
 	 * Sets the car srs.
 	 *
 	 * @param cAR_SRS the cAR_SRS to set
 	 */
	 public void setCAR_SRS(String cAR_SRS) {
		 CAR_SRS = cAR_SRS;
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
 	 * Gets the ocf buyer grp cd.
 	 *
 	 * @return the oCF_BUYER_GRP_CD
 	 */
	 public String getOCF_BUYER_GRP_CD() {
		 return OCF_BUYER_GRP_CD;
	 }

	 /**
 	 * Sets the ocf buyer grp cd.
 	 *
 	 * @param oCF_BUYER_GRP_CD the oCF_BUYER_GRP_CD to set
 	 */
	 public void setOCF_BUYER_GRP_CD(String oCF_BUYER_GRP_CD) {
		 OCF_BUYER_GRP_CD = oCF_BUYER_GRP_CD;
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
 	 * Gets the ablsh date.
 	 *
 	 * @return the aBOLISH_DATE
 	 */
	 public String getABLSH_DATE() {
		 return ABLSH_DATE;
	 }

	 /**
 	 * Sets the ablsh date.
 	 *
 	 * @param aBOLISH_DATE the aBOLISH_DATE to set
 	 */
	 public void setABLSH_DATE(String aBOLISH_DATE) {
		 ABLSH_DATE = aBOLISH_DATE;
	 }

	 /**
 	 * Gets the end itm stts cd.
 	 *
 	 * @return the eND_ITM_STTS_CD
 	 */
	 public String getEND_ITM_STTS_CD() {
		 return END_ITM_STTS_CD;
	 }

	 /**
 	 * Sets the end itm stts cd.
 	 *
 	 * @param eND_ITM_STTS_CD the eND_ITM_STTS_CD to set
 	 */
	 public void setEND_ITM_STTS_CD(String eND_ITM_STTS_CD) {
		 END_ITM_STTS_CD = eND_ITM_STTS_CD;
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
	 * Gets the uk oei spec id.
	 *
	 * @return the uk oei spec id
	 */
	public String getUK_OEI_SPEC_ID() {
		return UK_OEI_SPEC_ID;
	}

	/**
	 * Sets the uk oei spec id.
	 *
	 * @param uK_OEI_SPEC_ID the new uk oei spec id
	 */
	public void setUK_OEI_SPEC_ID(String uK_OEI_SPEC_ID) {
		UK_OEI_SPEC_ID = uK_OEI_SPEC_ID;
	}

	 /**
	  * @return the por
	  */



}
