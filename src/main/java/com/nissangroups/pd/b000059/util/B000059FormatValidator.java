/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program checks and converts the format of incoming data. Example converts date to YYYYMMDD format.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.util.CommonUtil;

public class B000059FormatValidator implements B000059IDataValidator {
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonUtilityService.class.getName());
	
	/**
	 * filespecvo bean injection
	 */
	@Autowired(required = false)
	B000059ValidateUtility b000059ValidateUtility;

	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	
	/**
	 * validate utility bean injection
	 */
	@Autowired(required = false)
	B000059ValidateUtility validate;

	/**
	 * validate the format
	 * 
	 * @param item
	 *            FileVo object
	 * @param interfaceFileID
	 *            interface file id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void validate(B000059FileVO item, String interfaceFileID) {		
		LOG.info( DOLLAR + INSIDE_METHOD + DOLLAR);		
		LOG.info("Performing  B000059FormatValidator"	+ item.toString());

		List<MstIfLayout> mstIfLayoutList = null;
		Iterator<MstIfLayout> iterator = null;
		MstIfLayout msit = null;
		String retValue = null;
		String dataType = null;
		String trimType = null;
		try {
			mstIfLayoutList = (ArrayList<MstIfLayout>) commonutility.getFileSpecVO()
					.getInterfaceLayoutByOrder().get(interfaceFileID);
			for (iterator = mstIfLayoutList.iterator(); iterator.hasNext();) {

				msit = iterator.next();
				retValue = CommonUtil.getBeanValue(item, msit.getColumnOrdr()
						.intValue());
				dataType = msit.getDataType();				
				// Tobe added after the trim properties in master interface  trimType = msit.getTrim();
				
				if(null != trimType){
					if (null != dataType && dataType.equalsIgnoreCase("CHAR")) {
						retValue = trimOperaionForChar(trimType,retValue, msit);
					} else if (null != dataType
							&& dataType.equalsIgnoreCase("VARCHAR")) {
						retValue = trimOperaionForVachar(trimType,retValue,msit);
					}
					CommonUtil.setBeanValue(item, msit.getColumnOrdr()
							.intValue(), retValue);		
				}

			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);

		} finally {
			mstIfLayoutList = null;
			iterator = null;
			LOG.info( DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	private String trimOperaionForVachar(String trimType, String inputValue,
			MstIfLayout msit) {
		
		String retValue = inputValue;

		if (trimType.equalsIgnoreCase("Y")) {

			retValue = retValue.trim();
			retValue = String.format("%" + msit.getLngth()
					+ "s", retValue);
		} else if (trimType.equalsIgnoreCase("N")) {
			// remove end space. (right trim)
			retValue = retValue.replaceFirst("\\s+$", "");

			retValue = String.format("%" + msit.getLngth()
					+ "s", retValue);
		}
		return retValue;
	
	}

	private String trimOperaionForChar(String trimType, String inputValue,
			MstIfLayout msit) {

		String retValue = inputValue;
		if (trimType.equalsIgnoreCase("Y")) {
			retValue = retValue.trim();
			retValue = String.format("%" + msit.getLngth()
					+ "s", retValue);
		} else if (trimType.equalsIgnoreCase("N")) {

			// remove  end space (right trim)
			retValue = retValue.replaceFirst("\\s+$", "");
			retValue = String.format("%" + msit.getLngth()
					+ "s", retValue);
		}

	
		return retValue;
	}
	
}
