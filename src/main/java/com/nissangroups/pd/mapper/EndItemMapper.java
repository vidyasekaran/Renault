/*
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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


/**
 * The Class EndItemMapper.
 *
 * @author z013865
 */
@Entity
public class EndItemMapper implements Serializable{
	
	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Variable id. */
	@EmbeddedId
	private EndItemMapperpk id;
	
	/** Variable osei ablsh date. */
	@Column(name="OSEI_ABLSH_DATE")
	private String OSEI_ABLSH_DATE;
	
	 /** Variable car srs. */
 	@Column(name="CAR_SRS")
	 private String CAR_SRS;


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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public EndItemMapperpk getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(EndItemMapperpk id) {
		this.id = id;
	}
	
}