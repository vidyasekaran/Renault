/* System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This is the common utility service which is used to load static data to B000059FileSpecVO from database 
 *                  it also contain other method which needs to do some computation and data conversion to java 
 *                  collection store the value to B000059FileSpecVO.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000066.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDMessageConsants;


public class B000066CommonUtilityService {
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000066CommonUtilityService.class.getName());
	
	public static String decider;
	
	public static String getController(String interfaceId, EntityManager entityManager){

		String cntrlVal = null;
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {

			List<MstInterface> interfaceMstList = entityManager.createQuery(
					"SELECT  f FROM MstInterface  f where f.ifFileId='"
							+ interfaceId + "'").getResultList();

			// START:: Load Interface Master into hashmap - key as fileid and
			// value as entrie row of that file id.

			if (null != interfaceMstList && !interfaceMstList.isEmpty()) {
				for (Iterator<MstInterface> iterator = interfaceMstList
						.iterator(); iterator.hasNext();) {
					MstInterface interfaceMst = (MstInterface) iterator.next();
					
					cntrlVal = interfaceMst.getTransactionType();
				}
			}else{				
				LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00003, new String[]{"&1", "&2"}, new String[]{ "Interface File ID: " + interfaceId," on Interface Master table"}) );
				CommonUtil.stopBatch();
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		return cntrlVal;
	
	}
	public static String getDecider() {
		return decider;
	}

	public static void setDecider(String decider) {
		B000066CommonUtilityService.decider = decider;
	}
}
