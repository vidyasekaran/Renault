/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline : This is a Validation utility used to perform all validation on data.
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.bean.B000059FileVO;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;


//@Component(value="b000059ValidateUtility")
public class B000059ValidateUtility 
{

	@Autowired(required = false)
	B000059CommonUtilityService commonutility;
	
	//custom log message for B000059
	CustomLogMessage clm = null;
	
	/* Constant LOG */
	private static final Log LOG = LogFactory.getLog(B000059ValidateUtility.class
			.getName());
	
	private static final String onColumnStr = "On Column : ";

	public void typeValidator(B000059FileVO item, String interfaceFileID)
	{
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		LOG.info("Value of Column getCol1 is "+item.getCol1());
		
		MstIfLayout msit = null;
		String retValue = null;
		List<MstIfLayout> mstIfLayoutList = null;
		Iterator<MstIfLayout> iterator = null;
		
		try{
			mstIfLayoutList = (ArrayList<MstIfLayout>) commonutility.getFileSpecVO().getInterfaceLayoutByOrder().get(interfaceFileID);			
			for (iterator = mstIfLayoutList.iterator(); iterator.hasNext();) {
				
				msit = iterator.next();
				
				retValue = CommonUtil.getBeanValue(item,msit.getColumnOrdr().intValue());		
				
				if(null != msit.getDataType()){
					selectOperation(msit, retValue,  interfaceFileID);
				}
						
			}
		}catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
		}finally{
			msit = null;
			retValue = null;
			mstIfLayoutList = null;
			iterator = null;
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
}
	
	private void selectOperation(MstIfLayout msit , String retValue,String interfaceFileID){
		switch(msit.getDataType().toUpperCase())
		{
		case "INT":
		case "NUMBER":
				callIntegerCheck(retValue, msit);
			break;
		case "VARCHAR":
		case "STRING":
		case "CHAR":
			 	callStringCheck(retValue, msit);
			 break;
		case "DATETIME":
				callDateTimeCheck(retValue, this.getReciveDateFrmt(), msit);	
				break;
		case "BOOLEAN":
				callBooleanCheck(retValue,msit);
				break;
		default:
				nonDataType( msit);						
		}		
	}
	private void nonDataType(MstIfLayout mstLayout) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		commonutility.getFileSpecVO().setValidationFlag(true);
		commonutility.getFileSpecVO().getValidationError().append("Type Validation Error Encountered");		
		clm = CustomLogMessage.M00168;
		clm.fetchLogMessage(commonutility.getFileSpecVO().getInterfaceFileId() ,mstLayout.getColumnOrdr().toString());		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	private void callDateTimeCheck(String retValue,String formatVal,  MstIfLayout mstLayout) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		
		
		if(null != mstLayout.getFxdLength() &&  mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_2)){
			
			if(null != retValue && retValue.trim().isEmpty()){
				
				formatLengthCheck(mstLayout,commonutility.getFileSpecVO().getInterfaceFileId());
				
			} else {
				boolean flag = isDateTime(retValue, formatVal);
				if (!flag) {
					commonutility.getFileSpecVO().setValidationFlag(true);
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));
				}
			}
			
		}else if(null != mstLayout.getFxdLength() && !retValue.trim().isEmpty() && mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_1)){
			
				boolean flag = isDateTime(retValue, formatVal);
				if (!flag) {
					commonutility.getFileSpecVO().setValidationFlag(true);
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));									
			}
		}	
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}
	
	private void callBooleanCheck(String retValue,  MstIfLayout mstLayout) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		
		if(null != mstLayout.getFxdLength() &&  mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_2)){
			
			if(retValue.isEmpty()){
				
				formatLengthCheck(mstLayout,commonutility.getFileSpecVO().getInterfaceFileId());
				
			}else{
				boolean flag = isBoolan(retValue);	
				if(!flag)
				{
					commonutility.getFileSpecVO().setValidationFlag(true);
					
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));
					

				}	
			}
		}else if(null != mstLayout.getFxdLength() && !retValue.isEmpty() &&  mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_1)){
			
				boolean flag = isBoolan(retValue);	
				if(!flag)
				{
					commonutility.getFileSpecVO().setValidationFlag(true);					
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));					
				}	
			
		}	
			
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);		
	}
	
	private static boolean isBoolan(String retValue){
		
		try{
			
			Boolean.parseBoolean(retValue);
			
			return true;
			
		}catch(Exception e){
			LOG.error(e);
			LOG.info("Conversion error from string to boolean " );
		}
		
		return false;
		
	}
	private static boolean isDateTime(String retValue,String formatVal) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		SimpleDateFormat df = new SimpleDateFormat(formatVal);
	    try {
	        df.parse(retValue);
	        LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	        return true;
	    } catch (ParseException e) {
	    	LOG.error(ERROR_MESSAGE, e);
	    	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	        return false;
	    }	    
	}
	
	private void callStringCheck(String retValue, MstIfLayout mstLayout) {
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
		if(null != mstLayout.getFxdLength() &&  mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_2)){
			
			if(null != retValue && retValue.trim().isEmpty()){
				
				formatLengthCheck(mstLayout,commonutility.getFileSpecVO().getInterfaceFileId());
				
			}else{		
				boolean flag = isString(retValue);		
				if(!flag)
				{
					commonutility.getFileSpecVO().setValidationFlag(true);					
					LOG.info("Expecting String values on column " +  mstLayout.getColumnOrdr() );
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));
				}		
			}
			
		}
		
				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	private void callIntegerCheck(String retValue, MstIfLayout mstLayout) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		
		if(null != mstLayout.getFxdLength() &&  mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_2)){
			
			if(null!=retValue && retValue.trim().isEmpty()){
				
				formatLengthCheck(mstLayout,commonutility.getFileSpecVO().getInterfaceFileId());
				
			}else{		
				if(!isInteger(retValue))
				{
					commonutility.getFileSpecVO().setValidationFlag(true);
					LOG.info("Expecting Integer values on column " +  mstLayout.getColumnOrdr() );
					
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));
					
				}		
			}			
		}else if(null != mstLayout.getFxdLength() && !isInteger(retValue) && !retValue.trim().isEmpty() && mstLayout.getFxdLength().equals(PDConstants.CONVERSION_LAYER_FIX_LENGTH_1)){
					commonutility.getFileSpecVO().setValidationFlag(true);
					LOG.info("Expecting Integer values on column " +  mstLayout.getColumnOrdr() );
					LOG.error(PDMessageConsants.M00336.replaceAll("&1", commonutility.getFileSpecVO().getInterfaceFileId()).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr() ));
					
		}		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
		
	/*
	 * isInteger check
	 */
	public static boolean isInteger(String str) 
	{
	    try 
	    {
	        Integer.parseInt(str);
	        return true;
	    } 
	    catch (NumberFormatException nfe) 
	    {
	    	LOG.info("Number format exception encountered in B000059ValidateUtility.isInteger"+nfe);
	        return false;
	    }
	}
	
	/*
	 * isString
	 */
	public static boolean isString(String str){	   		
		return ( null != str) ? true : false;	       
	}	
	
	public void callLengthCheck(String retValue, BigDecimal retValue2, String interfaceFileID, MstIfLayout mstLayout) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);	
		if(null != retValue  && null != retValue2 && retValue.length() != Integer.parseInt(retValue2.toBigInteger().toString())){			
			
				commonutility.getFileSpecVO().setValidationFlag(true);
				commonutility.getFileSpecVO().getValidationError().append("length Validation Error Encountered");				
				LOG.info("B000059FileSpecVO length validation error "+commonutility.getFileSpecVO().getValidationError() +"Flag value "+commonutility.getFileSpecVO().isValidationFlag());
				clm = CustomLogMessage.M00167;
				clm.fetchLogMessage(interfaceFileID ,mstLayout.getColumnOrdr().toString());
				
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		
	}

	public void formatLengthCheck(MstIfLayout mstLayout, String interfaceFileID){
		commonutility.getFileSpecVO().setValidationFlag(true);
		LOG.error(PDMessageConsants.M00025.replaceAll("&1", interfaceFileID ).replaceAll("&2", onColumnStr + mstLayout.getColumnOrdr()));
		
	}
	public String  getReciveDateFrmt() {		
			Map<String, MstPrmtr> mstPrmtrMap =  commonutility.getFileSpecVO().getPrmtrMasterDetails();					
			MstPrmtr mstPrmtr = (MstPrmtr) mstPrmtrMap.get(B000059Constants.B000059_RECIEV_DATE_FRMT);
			return mstPrmtr.getVal1();											
	}
	
}