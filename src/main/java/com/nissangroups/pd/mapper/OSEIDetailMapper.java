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
 * The Class OSEIDetailMapper.
 */
@Entity
public class OSEIDetailMapper {
	
	/** Variable por cd. */
	/*@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int id;
*/	@Id
	@Column(name = "POR_CD")
	String POR_CD;

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
 * Gets the uk id.
 *
 * @return the uk id
 */
public String getUK_ID() {
	return UK_ID;
}

/**
 * Sets the uk id.
 *
 * @param uK_ID the new uk id
 */
public void setUK_ID(String uK_ID) {
	UK_ID = uK_ID;
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
 * Gets the tosuko base pck cd.
 *
 * @return the tosuko base pck cd
 */
public String getTOSUKO_BASE_PCK_CD() {
	return TOSUKO_BASE_PCK_CD;
}

/**
 * Sets the tosuko base pck cd.
 *
 * @param tOSUKO_BASE_PCK_CD the new tosuko base pck cd
 */
public void setTOSUKO_BASE_PCK_CD(String tOSUKO_BASE_PCK_CD) {
	TOSUKO_BASE_PCK_CD = tOSUKO_BASE_PCK_CD;
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
 * Gets the pckge name.
 *
 * @return the pckge name
 */
public String getPCKGE_NAME() {
	return PCKGE_NAME;
}

/**
 * Sets the pckge name.
 *
 * @param pACKAGE_NAME the new pckge name
 */
public void setPCKGE_NAME(String pACKAGE_NAME) {
	PCKGE_NAME = pACKAGE_NAME;
}

/**
 * Gets the lcl note.
 *
 * @return the lcl note
 */
public String getLCL_NOTE() {
	return LCL_NOTE;
}

/**
 * Sets the lcl note.
 *
 * @param lOCAL_NOTE the new lcl note
 */
public void setLCL_NOTE(String lOCAL_NOTE) {
	LCL_NOTE = lOCAL_NOTE;
}

/**
 * Gets the mdfd flag.
 *
 * @return the mdfd flag
 */
public String getMDFD_FLAG() {
	return MDFD_FLAG;
}

/**
 * Sets the mdfd flag.
 *
 * @param mODIFIED_FLAG the new mdfd flag
 */
public void setMDFD_FLAG(String mODIFIED_FLAG) {
	MDFD_FLAG = mODIFIED_FLAG;
}

/**
 * Gets the mdl year.
 *
 * @return the mdl year
 */
public String getMDL_YEAR() {
	return MDL_YEAR;
}

/**
 * Sets the mdl year.
 *
 * @param mODEL_YEAR the new mdl year
 */
public void setMDL_YEAR(String mODEL_YEAR) {
	MDL_YEAR = mODEL_YEAR;
}

/** Variable uk id. */
@Column(name = "UK_ID")
String UK_ID;

/** Variable uk osei id. */
@Column(name = "UK_OSEI_ID")
String UK_OSEI_ID;

/** Variable osei adpt date. */
@Column(name = "OSEI_ADPT_DATE")
String OSEI_ADPT_DATE;

/** Variable osei suspended date. */
@Column(name = "OSEI_SUSPENDED_DATE")
String OSEI_SUSPENDED_DATE;

/** Variable osei ablsh date. */
@Column(name = "OSEI_ABLSH_DATE")
String OSEI_ABLSH_DATE;

/** Variable tosuko base pck cd. */
@Column(name = "TOSUKO_BASE_PCK_CD")
String TOSUKO_BASE_PCK_CD;

/** Variable end itm stts cd. */
@Column(name = "END_ITM_STTS_CD")
String END_ITM_STTS_CD;

/** Variable pckge name. */
@Column(name = "PCKGE_NAME")
String PCKGE_NAME;

/** Variable lcl note. */
@Column(name = "LCL_NOTE")
String LCL_NOTE;

/** Variable mdfd flag. */
@Column(name = "MDFD_FLAG")
String MDFD_FLAG;

/** Variable mdl year. */
@Column(name = "MDL_YEAR")
String MDL_YEAR;
}

   
