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
 * The Class OseiProductionTypeMstMapper.
 */
@Entity
public class OseiProductionTypeMstMapper {

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
 	 * Gets the production plant cd.
 	 *
 	 * @return the production plant cd
 	 */
 	public String getPRODUCTION_PLANT_CD() {
		 return PRODUCTION_PLANT_CD;
	 }

	 /**
 	 * Sets the production plant cd.
 	 *
 	 * @param pRODUCTION_PLANT_CD the new production plant cd
 	 */
 	public void setPRODUCTION_PLANT_CD(String pRODUCTION_PLANT_CD) {
		 PRODUCTION_PLANT_CD = pRODUCTION_PLANT_CD;
	 }

	 /**
 	 * Gets the order take base month.
 	 *
 	 * @return the order take base month
 	 */
 	public String getORDER_TAKE_BASE_MONTH() {
		 return ORDER_TAKE_BASE_MONTH;
	 }

	 /**
 	 * Sets the order take base month.
 	 *
 	 * @param oRDER_TAKE_BASE_MONTH the new order take base month
 	 */
 	public void setORDER_TAKE_BASE_MONTH(String oRDER_TAKE_BASE_MONTH) {
		 ORDER_TAKE_BASE_MONTH = oRDER_TAKE_BASE_MONTH;
	 }

	 /**
 	 * Gets the production month.
 	 *
 	 * @return the production month
 	 */
 	public String getPRODUCTION_MONTH() {
		 return PRODUCTION_MONTH;
	 }

	 /**
 	 * Sets the production month.
 	 *
 	 * @param pRODUCTION_MONTH the new production month
 	 */
 	public void setPRODUCTION_MONTH(String pRODUCTION_MONTH) {
		 PRODUCTION_MONTH = pRODUCTION_MONTH;
	 }

	 /**
 	 * Gets the production week no.
 	 *
 	 * @return the production week no
 	 */
 	public String getPRODUCTION_WEEK_NO() {
		 return PRODUCTION_WEEK_NO;
	 }

	 /**
 	 * Sets the production week no.
 	 *
 	 * @param pRODUCTION_WEEK_NO the new production week no
 	 */
 	public void setPRODUCTION_WEEK_NO(String pRODUCTION_WEEK_NO) {
		 PRODUCTION_WEEK_NO = pRODUCTION_WEEK_NO;
	 }

	 /**
 	 * Gets the production method cd.
 	 *
 	 * @return the production method cd
 	 */
 	public String getPRODUCTION_METHOD_CD() {
		 return PRODUCTION_METHOD_CD;
	 }

	 /**
 	 * Sets the production method cd.
 	 *
 	 * @param pRODUCTION_METHOD_CD the new production method cd
 	 */
 	public void setPRODUCTION_METHOD_CD(String pRODUCTION_METHOD_CD) {
		 PRODUCTION_METHOD_CD = pRODUCTION_METHOD_CD;
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

	 /** Variable production plant cd. */
 	@Column(name="PRODUCTION_PLANT_CD")
	 String PRODUCTION_PLANT_CD;

	 /** Variable order take base month. */
 	@Column(name="ORDER_TAKE_BASE_MONTH")
	 String ORDER_TAKE_BASE_MONTH;

	 /** Variable production month. */
 	@Column(name="PRODUCTION_MONTH")
	 String PRODUCTION_MONTH;


	 /** Variable production week no. */
 	@Column(name="PRODUCTION_WEEK_NO")
	 String PRODUCTION_WEEK_NO;

	 /** Variable production method cd. */
 	@Column(name="PRODUCTION_METHOD_CD")

	 String PRODUCTION_METHOD_CD;
	 
 	/** Variable uk id. */
 	@Column(name="UK_ID")
	 String UK_ID;



}
