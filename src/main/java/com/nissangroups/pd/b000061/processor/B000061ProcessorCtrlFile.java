package com.nissangroups.pd.b000061.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000061.util.B000061CommonUtilityService;
import com.nissangroups.pd.model.MstCtrlFileDetail;
import com.nissangroups.pd.model.MstInterface;


public class B000061ProcessorCtrlFile implements ItemProcessor<MstCtrlFileDetail, MstCtrlFileDetail> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000061ProcessorCtrlFile.class);
	
	@Autowired(required = false)
	B000061CommonUtilityService B61commonutility;
	
	private String sendInterfaceId = null;
		

	@Override
	public MstCtrlFileDetail process(MstCtrlFileDetail item) throws Exception {							


		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		
		if(null != item.getInfoType() && item.getIfFileId().equals("C")){			
			MstInterface s = (MstInterface) B61commonutility.getInterfaceMaster().get(B61commonutility.getInterfaceId());
			
			B61commonutility.getSendInterfaceFileName();
			B61commonutility.getSendCtrlFileName();			
			s.getLocalPath();									
			item.setDesc("Test Description");
			
			}else if(null != item.getInfoType() && item.getIfFileId().equals("S")){
			
			}				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return item;
	}

	public String getSendInterfaceId() {
		return sendInterfaceId;
	}

	public void setSendInterfaceId(String sendInterfaceId) {
		this.sendInterfaceId = sendInterfaceId;
	}
	
}
