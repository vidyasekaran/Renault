/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program performs type validation.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
public class B000059TypeValidator implements B000059IDataValidator 
{
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonUtilityService.class.getName());
	
	@Autowired(required = false)
	B000059ValidateUtility validate;
		
	@Override
	public void validate(B000059FileVO item, String interfaceFileID) {
				
		validate.typeValidator(item, interfaceFileID);
		LOG.info("Performing  B000059TypeValidator"+item.toString());
	}

}
