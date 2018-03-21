/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000017
 * Module          :O Ordering
 * Process Outline :OCF/CCF Usage Calculation for Ordered Volume
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *12-11-2015      z015399(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.b000017.output;

import java.sql.Timestamp;
import java.util.Date;

public class TableDetails {
	
	private String strTableName;
	private Timestamp updatedTime;
	
	public TableDetails(){
		strTableName="";
		updatedTime=new Timestamp((new Date()).getTime());
	}
	public String getStrTableName() {
		return strTableName;
	}
	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}
	public Timestamp getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	

}
