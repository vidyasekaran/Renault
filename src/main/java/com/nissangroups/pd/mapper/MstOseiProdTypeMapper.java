/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-MstOseiProdTypeMapper
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
 * The Class MstOseiProdTypeMapper.
 */
@Entity
public class MstOseiProdTypeMapper {

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
 	 * Gets the prod plnt cd.
 	 *
 	 * @return the prod plnt cd
 	 */
 	public String getPROD_PLNT_CD() {
		 return PROD_PLNT_CD;
	 }

	 /**
 	 * Sets the prod plnt cd.
 	 *
 	 * @param pRODUCTION_PLNT_CD the new prod plnt cd
 	 */
 	public void setPROD_PLNT_CD(String pRODUCTION_PLNT_CD) {
		 PROD_PLNT_CD = pRODUCTION_PLNT_CD;
	 }

	 /**
 	 * Gets the ordr take base mnth.
 	 *
 	 * @return the ordr take base mnth
 	 */
 	public String getORDR_TAKE_BASE_MNTH() {
		 return ORDR_TAKE_BASE_MNTH;
	 }

	 /**
 	 * Sets the ordr take base mnth.
 	 *
 	 * @param oRDER_TAKE_BASE_MNTH the new ordr take base mnth
 	 */
 	public void setORDR_TAKE_BASE_MNTH(String oRDER_TAKE_BASE_MNTH) {
		 ORDR_TAKE_BASE_MNTH = oRDER_TAKE_BASE_MNTH;
	 }

	 /**
 	 * Gets the prod mnth.
 	 *
 	 * @return the prod mnth
 	 */
 	public String getPROD_MNTH() {
		 return PROD_MNTH;
	 }

	 /**
 	 * Sets the prod mnth.
 	 *
 	 * @param pRODUCTION_MNTH the new prod mnth
 	 */
 	public void setPROD_MNTH(String pRODUCTION_MNTH) {
		 PROD_MNTH = pRODUCTION_MNTH;
	 }

	 /**
 	 * Gets the prod wk no.
 	 *
 	 * @return the prod wk no
 	 */
 	public String getPROD_WK_NO() {
		 return PROD_WK_NO;
	 }

	 /**
 	 * Sets the prod wk no.
 	 *
 	 * @param pRODUCTION_WK_NO the new prod wk no
 	 */
 	public void setPROD_WK_NO(String pRODUCTION_WK_NO) {
		 PROD_WK_NO = pRODUCTION_WK_NO;
	 }

	 /**
 	 * Gets the prod mthd cd.
 	 *
 	 * @return the prod mthd cd
 	 */
 	public String getPROD_MTHD_CD() {
		 return PROD_MTHD_CD;
	 }

	 /**
 	 * Sets the prod mthd cd.
 	 *
 	 * @param pRODUCTION_MTHD_CD the new prod mthd cd
 	 */
 	public void setPROD_MTHD_CD(String pRODUCTION_MTHD_CD) {
		 PROD_MTHD_CD = pRODUCTION_MTHD_CD;
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

	/** Variable uk osei id. */
	@Column(name="UK_OSEI_ID")
	 String UK_OSEI_ID;

	 /** Variable prod plnt cd. */
 	@Column(name="PROD_PLNT_CD")
	 String PROD_PLNT_CD;

	 /** Variable ordr take base mnth. */
 	@Column(name="ORDR_TAKE_BASE_MNTH")
	 String ORDR_TAKE_BASE_MNTH;

	 /** Variable prod mnth. */
 	@Column(name="PROD_MNTH")
	 String PROD_MNTH;


	 /** Variable prod wk no. */
 	@Column(name="PROD_WK_NO")
	 String PROD_WK_NO;

	 /** Variable prod mthd cd. */
 	@Column(name="PROD_MTHD_CD")

	 String PROD_MTHD_CD;
	 
 	/** Variable uk id. */
 	@Column(name="UK_ID")
	 String UK_ID;



}
