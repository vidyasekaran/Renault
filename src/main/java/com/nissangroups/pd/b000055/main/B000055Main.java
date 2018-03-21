/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000055
 * Module          :CM Common		
 * Process Outline :Batch for Job Schedule Creation																
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-01-2016  	  z015883(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000055.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.b000055.processor.B000055Processor;
import com.nissangroups.pd.b000055.util.B000055Constants;


/**
 * @author z015883
 *
 */
public class B000055Main {

	private static ApplicationContext applicationContext;
	private static final Log LOG = LogFactory.getLog(B000055Main.class);
	
private B000055Main(){
	
}
	/*
	*@param args
	* return void
	*/
	public static void main(String[] args) {
		String[] batchConfig = {B000055Constants.B000055_XML };
		applicationContext = new ClassPathXmlApplicationContext(batchConfig);
		startB00055(args);
	}
	
	/*method to call execution of batch55
	*@param args
	*return void
	*/
	private static void startB00055(String[] args) {
		if(args.length<6)
			LOG.info("Six Input parameters required namely [porCd,OrderTakeBasePeriodTypeCd,OrderTakeBasePeriodFrom,OrderTakeBasePeriodTo,WeekNumberFrom,WeekNumberTo]");
		else if(args.length==6)
		{	
			
			 if(addParamToMap(args));
			 {
			 B000055Processor b000055Processor=(B000055Processor) applicationContext.getBean(B000055Constants.B000055_Processor);
			 b000055Processor.executeB000055(args);
			}
		}
	}
	
	private static boolean addParamToMap(String[] args)
	{
		for (String string : args) {
			if(string.equalsIgnoreCase(B000055Constants.BLANK))
			{
				LOG.info("Input parameter is Invalid.Please Avoid Blankspaces");
				return false;
			}
		}
		return true;
	}
	
}
