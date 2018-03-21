/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program maps the input file data to B000059FileVO for further processing.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000059.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.util.CommonUtil;

public class B000059FileVOFieldSetMapper implements
		FieldSetMapper<B000059FileVO> {

	/**
	 * Constant LOG
	 */
	private static final Log LOG = LogFactory
			.getLog(B000059FileVOFieldSetMapper.class.getName());

	/**
	 * This method used to map fieldset with filevo object
	 * 
	 * @param Fieldset
	 *            object
	 * @return B000059FileVO filevo object
	 */
	@Override
	public B000059FileVO mapFieldSet(FieldSet fieldSet) throws BindException {

		B000059FileVO fileVO = new B000059FileVO();

		LOG.info("Maximum cols are : " + fieldSet.getValues().length);

		// readRawString() - Read the String value at index 'index' including
		// trailing whitespace (don't trim).

		for (int i = 0; i < fieldSet.getValues().length; i++) {
			CommonUtil.setBeanValue(fileVO, i + 1, fieldSet.readRawString(i));
		}
		return fileVO;
	}

}
