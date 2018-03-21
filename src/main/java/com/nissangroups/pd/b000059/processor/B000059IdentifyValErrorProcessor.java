/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :Default Value Set
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.processor;

import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.exception.PdApplicationException;

public class B000059IdentifyValErrorProcessor implements
		ItemProcessor<B000059FileVO, B000059FileVO> {

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/** Variable interface file id. */
	private long counter = 1L;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059IdentifyValErrorProcessor.class.getName());

	/**
	 * This method used to check the validation error
	 * 
	 * @param item
	 *            B000059FileVO object
	 * @return B000059FileVO object
	 */
	@Override
	public B000059FileVO process(B000059FileVO item) throws PdApplicationException {
		boolean flag;
		
		try 
		{
			flag = commonutility.getFileSpecVO().isValidationFlag();			
			if (flag) 
			{
				commonutility.updateFileStatusOnBean(counter++);
				commonutility.getFileSpecVO().setValidationFlag(false);
			}

		} 
		catch (Exception e) 
		{
			LOG.error(ERROR_MESSAGE, e);
			LOG.info("Exception in B000059IdentifyValErrorProcessor process method "
					+ e);
		} 

		return item;
	}

}
