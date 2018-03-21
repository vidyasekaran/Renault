/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.processor;

import static com.nissangroups.pd.b000028.util.B000028Constants.BATCH_ID_B000028;
import static com.nissangroups.pd.util.PDConstants.DATE_TIME_FORMAT;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FILE_EXT_TSV;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.MONTHLY_ORDER_TAKE_BASE_PERIOD_MST;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.POR_CD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nissangroups.pd.b000028.output.B000028Output;
import com.nissangroups.pd.b000028.output.B000028ParamOutput;
import com.nissangroups.pd.b000028.process.AutoAdjustReport;
import com.nissangroups.pd.b000028.process.ByrCdLvlProcess;
import com.nissangroups.pd.b000028.process.ByrGrpLvlProcess;
import com.nissangroups.pd.b000028.process.ClrLvlProcess;
import com.nissangroups.pd.b000028.process.EndItmLvlProcess;
import com.nissangroups.pd.b000028.process.OCFBreachReport;
import com.nissangroups.pd.b000028.process.PotLvlProcess;
import com.nissangroups.pd.b000028.process.UpdtAutoAdjustProcess;
import com.nissangroups.pd.model.MstFrzn;
import com.nissangroups.pd.repository.SpecRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class OrdrAdjstProcessor implements ItemProcessor<String, MstFrzn> {

	private static final Log LOG = LogFactory.getLog(OrdrAdjstProcessor.class
			.getName());

	/** Variable environment. */
	@Autowired(required = false)
	Environment environment;

	@Autowired(required = false)
	private ByrGrpLvlProcess objByrGrpLvlProcess;

	@Autowired(required = false)
	private ByrCdLvlProcess objByrCdLvlProcess;

	@Autowired(required = false)
	private AutoAdjustReport objAutoAdjustReport;

	@Autowired(required = false)
	private ClrLvlProcess objClrLvlProcess;

	@Autowired(required = false)
	private EndItmLvlProcess objEndItmLvlProcess;

	@Autowired(required = false)
	private OCFBreachReport objOCFBreachReport;

	@Autowired(required = false)
	private PotLvlProcess objPotLvlProcess;

	@Autowired(required = false)
	private UpdtAutoAdjustProcess objUpdtAutoAdjustProcess;
	
	@Autowired(required = false)
	private SpecRepository specRep;

	private String stgCd;
	private String porCd;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String errorPathr4 = environment
				.getProperty(PDConstants.R000004_REPORT_PATH);
		String errorPathr5 = environment
				.getProperty(PDConstants.R000005_REPORT_PATH);
		DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		String fileName = errorPathr4.trim() + PDConstants.REPORT_SUFFIX_R4
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;
		String fileName1 = errorPathr5.trim() + PDConstants.REPORT_SUFFIX_R5
				+ dateFormat.format(new Date()) + FILE_EXT_TSV;

		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext stepContext = jobExecution.getExecutionContext();
		stepContext.put(PDConstants.PRMTR_FILE_NAME_R5, fileName1);
		stepContext.put(PDConstants.PRMTR_FILE_NAME_R4, fileName);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	@Override
	public MstFrzn process(String item) throws Exception {
		B000028Output output= new B000028Output();
		B000028ParamOutput param= new B000028ParamOutput();
		param.setPorCd(porCd);
		param.setStgCd(stgCd);
		param.setOrdrTkBsMnth(item);
		
		specRep.getOrdTkBsMnth(porCd,stgCd);
		output.setObjB000028ParamOutput(param);
		output=objByrGrpLvlProcess.executeProcess(output);
		output=objByrCdLvlProcess.executeProcess(output);
		output=objEndItmLvlProcess.executeProcess(output);
		output=objClrLvlProcess.executeProcess(output);
		output=objPotLvlProcess.executeProcess(output);
		output=objUpdtAutoAdjustProcess.executeProcess(output);
		objAutoAdjustReport.executeProcess(output);
		objOCFBreachReport.executeProcess(output);
		return null;
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0) {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] { BATCH_ID_B000028,
					PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
							stepExecution.getJobParameters().getString(POR_CD),
							MONTHLY_ORDER_TAKE_BASE_PERIOD_MST });
			CommonUtil.stopBatch();
		}
	}

	public String getStgCd() {
		return stgCd;
	}

	public void setStgCd(String stgCd) {
		this.stgCd = stgCd;
	}

	public String getPorCd() {
		return porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

}
