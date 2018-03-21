/*
 * System Name     :Post Dragon 
 * Sub system Name :Interface
 * Function ID     :PST-DRG-I000002
 * Module          :CM COMMON
 * Process Outline : 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 19-11-2015  	  z015887(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000002.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.nissangroups.pd.i000002.output.I000002OutputBean;

/*
 * 
 *  This class used to map data obtained from a FieldSet into an object
 */
public class I000002DataMapper implements FieldSetMapper<I000002OutputBean> {
	
	/*
	 * This method is used to map the field value from fieldSet to I000002OutputBean
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet
	 * (org.springframework.batch.item.file.transform.FieldSet)
	 */
	@Override
	public I000002OutputBean mapFieldSet(FieldSet fieldSet)
			throws BindException {
		I000002OutputBean outBean = new I000002OutputBean();

		outBean.setProductionRegionCD(fieldSet.readString(1)); 				//Production Region Code
		outBean.setBuyerCD(fieldSet.readString(2));			   				//Buyer Code
		outBean.setSpecDestinationCD(fieldSet.readString(3)); 				//Spec Destination Code
		outBean.setBuyerDescription(fieldSet.readString(4));   				//Buyer Description
		outBean.setOcfRegionCD(fieldSet.readString(5));        				//OCF Region Code
		outBean.setOcfBuyerGroupCD(fieldSet.readString(6));    				//OCF Buyer Groupd Code
		outBean.setNscEimOrderHorizon(fieldSet.readString(7)); 				//NSC EIM Order Horizon
		outBean.setEndOfPiplineAchievementPoint(fieldSet.readString(8));    //End of PipeLine Achievement Point
		outBean.setPreShipmentInspectionSymbol(fieldSet.readString(9));		//Pre Shipment Inspection Symbol
		outBean.setBuyerGroupCD(fieldSet.readString(10));					//Buyer Group Code
		outBean.setCreateUserID(fieldSet.readString(11));					//Create User ID 
		outBean.setCreateDateTime(fieldSet.readString(12));					//Create Date Time
		outBean.setUpdateUserID(fieldSet.readString(13));					//Update User ID
		outBean.setUpdateDateTime(fieldSet.readString(14));					//Update Date Time

		return outBean;
	}

}
