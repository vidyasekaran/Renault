/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This is the interface for some of the validator implementation such as Length,Type and Format
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000059.util;
import com.nissangroups.pd.b000059.bean.B000059FileVO;

public interface B000059IDataValidator 
{
	public void validate(B000059FileVO fileVo, String interfaceFileID);
}
