/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This is an interface between item processors and validation utilities.
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nissangroups.pd.b000059.bean.B000059FileSpecVO;
import com.nissangroups.pd.b000059.bean.B000059FileVO;

@Component
public class B000059DataValidatorService {

	/**
	 * B000059FileVO bean injection
	 */
	@Autowired(required = false)
	B000059FileVO fileVo;

	/**
	 * Common utility service bean injection
	 */
	@Autowired(required = false)
	B000059CommonUtilityService commonutility;

	/**
	 * Filespecvo bean injection
	 */
	@Autowired(required = false)
	B000059FileSpecVO fileSpecVO;

	/**
	 * Data validator bean injection
	 */
	B000059IDataValidator idataValidator;

	/**
	 * Length validator bean injection
	 */
	@Autowired(required = false)
	B000059LengthValidator lengthVal;

	/**
	 * Format validator bean injection
	 */
	@Autowired(required = false)
	B000059FormatValidator formatVal;

	/**
	 * Type validator bean injection
	 */
	@Autowired(required = false)
	B000059TypeValidator typeVal;

	/**
	 * Default value validator bean injection
	 */
	@Autowired(required = false)
	B000059DefaultValueValidator defaultVal;

	/**
	 * Call validate method to perform length validation
	 * 
	 * @param fileVo
	 * @param interfaceFileID
	 *            interface file id
	 */
	public void performLengthValidation(B000059FileVO fileVo,
			String interfaceFileID) {
		idataValidator = lengthVal;
		idataValidator.validate(fileVo, interfaceFileID);
	}

	/**
	 * Call validate method to perform format validation
	 * 
	 * @param fileVo
	 * @param interfaceFileID
	 *            interface file id
	 */
	public void performformatValidation(B000059FileVO fileVo,
			String interfaceFileID) {		
		idataValidator = formatVal;
		idataValidator.validate(fileVo, interfaceFileID);
	}

	/**
	 * Call validate method to perform type validation
	 * 
	 * @param fileVo
	 * @param interfaceFileID
	 *            interface file id
	 */
	public void performtypeValidation(B000059FileVO fileVo,
			String interfaceFileID) {
		idataValidator = typeVal;
		idataValidator.validate(fileVo, interfaceFileID);
	}

	/**
	 * Call validate method to set default values
	 * 
	 * @param fileVo
	 * @param interfaceFileID
	 *            interface file id
	 */
	public void performDefaultValueSet(B000059FileVO fileVo,
			String interfaceFileID) {
		idataValidator = defaultVal;
		idataValidator.validate(fileVo, interfaceFileID);
	}

}
