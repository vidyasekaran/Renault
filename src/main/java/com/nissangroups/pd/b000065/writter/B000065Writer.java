/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program is used to Write the processed data from input file to CmnInterfaceData table.
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000065.writter;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000059.util.B000059CommonUtilityService;
import com.nissangroups.pd.b000065.output.B000065FTP_MST_INTERFACE_Details;
import com.nissangroups.pd.b000065.util.B000065CommonUtilityService;
import com.nissangroups.pd.model.CmnFileHdr;
import com.nissangroups.pd.model.CmnInterfaceData;

/**
 * The Class B000003Writer.
 */
public class B000065Writer implements ItemWriter<B000065FTP_MST_INTERFACE_Details>
{
	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000059CommonUtilityService.class.getName());
	
	@Autowired(required = false)
    B000065CommonUtilityService commonutilservice;

	@Override
	public void write(List<? extends B000065FTP_MST_INTERFACE_Details> items) throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			B000065FTP_MST_INTERFACE_Details b000065ftp_MST_INTERFACE_Details = (B000065FTP_MST_INTERFACE_Details) iterator
					.next();
			if( b000065ftp_MST_INTERFACE_Details.isProcessed().equalsIgnoreCase("true") ) {
				commonutilservice.updateCommonFileHdr(b000065ftp_MST_INTERFACE_Details,"M");
			}else{
				commonutilservice.updateCommonFileHdr(b000065ftp_MST_INTERFACE_Details,"P");
			}
		}
		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);		
	}	
}