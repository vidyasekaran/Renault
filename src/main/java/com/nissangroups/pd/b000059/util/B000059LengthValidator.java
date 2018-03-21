/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program performs length Validation on incoming data.
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.util.CommonUtil;

@Component
public class B000059LengthValidator implements B000059IDataValidator {
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonUtilityService.class.getName());

	/**
	 * common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/**
	 * validate utility bean injection
	 */
	@Autowired(required = false)
	B000059ValidateUtility b000059ValidateUtility;

	// custom log message for B000059
	CustomLogMessage clm = null;

	/**
	 * validate the length
	 * 
	 * @param fileVO
	 * @param interfaceFileID interface file id
	 */
	@Override
	public void validate(B000059FileVO fileVo, String interfaceFileID) {

		LOG.info("B000059LengthValidator" + DOLLAR + INSIDE_METHOD + DOLLAR);		
		List<MstIfLayout> mstIfLayoutList = null;
		MstIfLayout msit = null;
		Iterator<MstIfLayout> iterator = null;
		try {
			mstIfLayoutList = (ArrayList<MstIfLayout>) commonutility
					.getFileSpecVO().getInterfaceLayoutByOrder()
					.get(interfaceFileID);

			for (iterator = mstIfLayoutList.iterator(); iterator.hasNext();) {

				msit = (MstIfLayout) iterator.next();

				String retValue = CommonUtil.getBeanValue(fileVo, msit.getColumnOrdr().intValue());

				BigDecimal len = msit.getLngth();

				b000059ValidateUtility.callLengthCheck(retValue, len,
						interfaceFileID, msit);

			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		} finally {
			mstIfLayoutList = null;
			msit = null;
			iterator = null;
		}
		LOG.info("B000059LengthValidator" + DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

}
