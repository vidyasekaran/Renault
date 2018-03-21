package com.nissangroups.pd.b000053.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000053.output.B000053Output;
import com.nissangroups.pd.repository.PipelineProcessRepository;



public class TriggerWklyBatchProcess {
	
	
	private static final Log LOG = LogFactory.getLog(TriggerWklyBatchProcess.class
			.getName());
	
	
	@Autowired(required = false)/** Repository for executing queries. */
	PipelineProcessRepository PplProcessRepo;
	
	public B000053Output extractTriggerWklyBatchProcess(B000053Output obj) {
		obj.setWeeklyBatchProcessSts(PplProcessRepo
				.getWeeklyBatchProcessStatus(obj.getObjB000053ParamOutput()
						.getPorCd(), obj.getObjB000053ParamOutput()
						.getOrdrTakeBaseMonth(), obj.getObjB000053ParamOutput()
						.getOrdrTakeBaseWeekNo(), obj
						.getObjB000053ParamOutput().getBatchId(), obj
						.getObjB000053ParamOutput().getSeqId()

				));

		return obj;

	}
	
}
