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
package com.nissangroups.pd.b000055.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000055.repository.BatchScheduleRepository;
import com.nissangroups.pd.b000055.util.B000055Constants;
import com.nissangroups.pd.bean.TmpJobSchedule;
import com.nissangroups.pd.model.MstJobSteram;
import com.nissangroups.pd.util.PDConstants;

/**
 * @author z015883
 *This Class will act as channel between Repository & main class
 */
public class B000055Processor {

	@Autowired
	private BatchScheduleRepository batchScheduleRepository;
	private static final Log LOG = LogFactory.getLog(B000055Processor.class);
	/* method use to execute batch Process 
	*@param args
	*@return string
	*/
	public String executeB000055(String[] args) {
		LOG.info("B000055 starting Execution");
		try{
		batchScheduleRepository.addToMap(args);
			List<TmpJobSchedule> jobs=batchScheduleRepository.getNonExecutedJobStrm();//P0001
			for (TmpJobSchedule job : jobs) {
				MstJobSteram jobstrm=batchScheduleRepository.getJobStrms(job);//P0002
				if(jobstrm.getCalcBasedateFlg().equalsIgnoreCase(B000055Constants.ONE))
				{
					//batchScheduleRepository.insertIntoJobShdltrn(jobstrm,job);//P0003(start time shd set based on screen time value)
				}
				
					if(jobstrm.getCalcBasedateFlg().equalsIgnoreCase(B000055Constants.ZERO))
					{
						if(jobstrm.getShdlCalcPttrn().equalsIgnoreCase(B000055Constants.ONE))
						{
							List<Object[]> objects=batchScheduleRepository.extractLeadTimeData(job);//P0004(what if this process returns multiple values)
							if(objects!=null)
							for (Object[] leadData : objects)  
								batchScheduleRepository.insertIntoJobStrmShdl(job,leadData);//P0004
						}
						if(jobstrm.getShdlCalcPttrn().equalsIgnoreCase(B000055Constants.TWO) && jobstrm.getOrdrtkBaseperiodTypeCd().equalsIgnoreCase(B000055Constants.MNTHLY))
						{
							List<Object[]> day_base_data=batchScheduleRepository.getDayBasedataMnthly(job.getJobstrmSeqId());//P0005
							if(day_base_data!=null)
								for(Object[] day:day_base_data){
									Date st_dt=batchScheduleRepository.getStartDateFrmWkCalndr(job.getOrdrTkBsMnth(),day[0].toString());
									batchScheduleRepository.insertIntoJobStrmShdl(job, day,st_dt);
									LOG.info("Records inserted for P0005 Case");
								}
						}
						if(jobstrm.getShdlCalcPttrn().equalsIgnoreCase(B000055Constants.TWO) && jobstrm.getOrdrtkBaseperiodTypeCd().equalsIgnoreCase(B000055Constants.WEEKLY))
						{
							List<Object[]> day_base_data=batchScheduleRepository.getDayBasedataWkly(job.getJobstrmSeqId());//P0006
							if(day_base_data.size()!=0)
								for(Object[] day:day_base_data){
									Date st_dt=batchScheduleRepository.getStartDateFrmWkCalndr(job.getOrdrTkBsMnth(),day[0].toString());
									batchScheduleRepository.insertIntoJobStrmShdl(job, day,st_dt);
									LOG.info("Records inserted for P0006 Case");
								}
						}
					
					}
				}
		}
		catch(Exception e)
		{
			LOG.error(PDConstants.EXCEPTION+e);
		}
		return null;
		
		
	}

}
