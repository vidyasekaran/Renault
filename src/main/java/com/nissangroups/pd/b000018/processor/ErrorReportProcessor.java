/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Create the Reports PST-DRG-R000007(Item Check Error Report),
 * R000008 (Frozen Order Error Report),R000009 (OCF Breach Report)
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000018.processor;

import static com.nissangroups.pd.util.PDConstants.B000018_REPORT_PATH;
import static com.nissangroups.pd.util.PDConstants.BATCH_CONFIG_CLASSPATH;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_END;
import static com.nissangroups.pd.util.PDConstants.STEP_AFTER_START;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000018.bean.InputBean;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.repository.ByrGrpMnthlyOcfLimitTrnRepository;
import com.nissangroups.pd.repository.MnthlyOrdrIfTrnRepository;


@PropertySource(BATCH_CONFIG_CLASSPATH)
public class ErrorReportProcessor implements
		ItemProcessor<InputBean, InputBean> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ErrorReportProcessor.class);
	
	
	@Autowired(required=false)
	private MnthlyOrdrIfTrnRepository mnthlyOrdrIfTrnRepositoryObj ;
	

	@Autowired(required=false)
	private ByrGrpMnthlyOcfLimitTrnRepository byrGrpMnthlyOcfLimitTrnRepositoryObj;
	
	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;
	
	@Override
	public InputBean process(InputBean input) throws PdApplicationException {

		LOG.info(STEP_AFTER_START);

		String reportPath = environment.getProperty(B000018_REPORT_PATH);
		try {
			mnthlyOrdrIfTrnRepositoryObj.generateItmChkErrReport(input,reportPath);
		} catch (ParseException e) {
			LOG.error("PARSE EXCEPTION : " + e.getMessage());
		}
		mnthlyOrdrIfTrnRepositoryObj.generateFrznOrdrErrReport(input,reportPath);
		List<Object[]> distinctByrGrpCarSrsList = mnthlyOrdrIfTrnRepositoryObj.fetchDistinctByrGrpCarSrs(input.getPorCd(),input.getFileId(),input.getTableName(),input.getSeqNo());
		
			try {
				byrGrpMnthlyOcfLimitTrnRepositoryObj.generateOcfBrchReport(input,distinctByrGrpCarSrsList,reportPath);
				
			} catch (ParseException e) {
				LOG.error("PARSE EXCEPTION : " + e.getMessage());
			}
		
		LOG.info(STEP_AFTER_END);
		
		return input;
	}	

	}
