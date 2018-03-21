/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000005
 * Module          :Cretae Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z010343(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.main;

import static com.nissangroups.pd.util.PDConstants.PRMTR_BATCH;
import static com.nissangroups.pd.util.PDConstants.PRMTR_POR;
import static com.nissangroups.pd.util.PDConstants.PRMTR_UPDATE_FLAG;

import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nissangroups.pd.util.CommonUtil;

import static com.nissangroups.pd.util.PDConstants.BATCH_5_ID;
import static com.nissangroups.pd.util.PDConstants.BATCH_4_ID;
import static com.nissangroups.pd.util.PDConstants.BATCH000005;

/**
 * The Class B000005.
 */
@Configuration
@PropertySource("classpath:batch_config.properties")
public class B000005 extends abstractMain {

/** Constant LOG. */
private static final  Log LOG = LogFactory.getLog(B000005.class.getName());

/** Variable por. */
static String por ;

/** Variable update only flg. */
static String updateOnlyFlg; 

/** Variable batch. */
static String batch;

/** Variable batch id. */
static String batch_id;

/** Variable config id. */
static String config_id;
    
/**
 * Batch B000005 Execution Start from this Main Method.
 *
 * @param args the arguments
 */
public static void main(String[] args){
   
        //verifying arguments
        if(args==null || args.length!=3){
        	LOG.error("Arguments [3] expected, [POR_CD, UPDATE_ONLY_FLAG, BATCH], 06 B4/B5 1/0 " );
        	CommonUtil.stopBatch();
        }
        
         por = args[0];
         updateOnlyFlg =args[1]; 
         batch = args[2];
         if(batch.equals(BATCH000005)){
        	 batch_id=BATCH_5_ID;
        	 config_id="B000005/B000005_Batch_Config.xml";
         }else {
        	 batch_id=BATCH_4_ID;
        	 config_id="B000004/B000004_Batch_Config.xml";
         }
         new B000005().run(args);
        
        
    }

/* (non-Javadoc)
 * @see com.nissangroups.pd.main.abstractMain#run(java.lang.String[])
 */
@Override
 void run(String[] args) {
        String[] batchConfig = {config_id};
        ApplicationContext context = new ClassPathXmlApplicationContext(batchConfig);
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean(batch_id);
        
        try {
            
            JobParameters parameter = new JobParametersBuilder().addString(PRMTR_POR, por).addString(PRMTR_UPDATE_FLAG,updateOnlyFlg).addString(PRMTR_BATCH,batch).toJobParameters();
            jobLauncher.run(job, parameter);
            
        }
        catch(Exception e){
            LOG.info(Level.SEVERE,e);
            
        }
        
    }

   
    

}

