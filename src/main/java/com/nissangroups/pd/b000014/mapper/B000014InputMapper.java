/*
 * System Name       : Post Dragon 
 * Sub system Name : Batch
 * Function ID            : PST-DRG-B000014
 * Module                  : Ordering		
 * Process Outline     : RHQ/NSC wise Volume/OCF allocation															
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 6-11-2015  	  z015399(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000014.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.nissangroups.pd.b000017.mapper.InputMapperPK;


@Entity
public class B000014InputMapper implements Serializable{
	
	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	InputMapperPK id;
	
	public InputMapperPK getId() {
		return id;
	}
	
	public void setId(InputMapperPK id){
		this.id = id;
	}
	
	/** Variable Stage_Cd */
	@Column(name="STAGE_CD")
	private String STAGE_CD;
	
	public String getSTAGE_CD() {
		return STAGE_CD;
	}

	public void setSTAGE_CD(String sTAGE_CD) {
		STAGE_CD = sTAGE_CD;
	}


}
