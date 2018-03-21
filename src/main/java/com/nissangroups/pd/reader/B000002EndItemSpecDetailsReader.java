/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000002EndItemSpecDetailsReader
 * Module          :@Create Spec Masters
 * Process Outline :@Reading End item Spec details 
 *
 * <Revision History>
 * Date       		  Name(RNTBCI)             		Description 
 * ---------- ------------------------------ ---------------------
 * @08 July 2015  	  @author(z013576)              New Creation
 *
 */
package com.nissangroups.pd.reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;

import static com.nissangroups.pd.util.B000002QueryConstants.Query_01_Fetch_EI_Spec_details;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PRMTR_PORCD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.READER_INFO;

/**
 * Non Processed End Item Spec Details Reader Class
 * Process Id - P0002.
 * @version 1.0
 */
public class B000002EndItemSpecDetailsReader implements
              ItemReader<List<Object[]>>  {
	
       /** Constant LOG. */
       private static final Log LOG = LogFactory.getLog(B000002EndItemSpecDetailsReader.class);
       
       /** Object emgr. */
       @PersistenceContext(name = PERSISTENCE_NAME)
       private EntityManager eMgr;
       
       /** Object jobexecution. */
       private JobExecution jobExecution;
       
       /** Variable maxindex. */
       private int maxIndex;
       
       /** Variable current index count. */
       private int currentIndexCount;
       
       /** Variable emptylist. */
       private List<Object[]> emptyList = null;
       
       /** Variable ei spec dtls list. */
       private List<Object[]> eiSpecDtlsList = new ArrayList<Object[]>();
       
       /** Variable fetcheispecdetail query. */
       private String fetchEISpecDetailQuery = Query_01_Fetch_EI_Spec_details.toString();
            
       /**
        * Before Step method to set the stepExecution to jobExecution Object
        * Setting the Jobexecution details to the stepExecution object
        * This is to get the Job Parameters declared in the Main Class.
        *
        * @param stepExecution the step execution
        */
       @BeforeStep
       public void beforeStep(StepExecution stepExecution) {
    	   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	   jobExecution = stepExecution.getJobExecution();
    	   LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
       }

       /**
        * Reads Non Processed EndItemSpecDetails.
        *
        * @return the list
        * @throws PdApplicationNonFatalException the pd application non fatal exception
        */
       @Override
       public List<Object[]> read() throws PdApplicationNonFatalException{
    	   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
    	   String jobPorCd = jobExecution.getJobParameters().getString(PRMTR_PORCD);
    	   Connection connection = null;
   		
   		
   		Session session = (Session)eMgr.getDelegate();
   		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory();
   		ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
   		
   		 
//   		connection = eMgr.getEntityManagerFactory().unwrap(Connection.class);
   		try {
   			connection = connectionProvider.getConnection();
   			LOG.info("connection-----"+connection.toString());
   		}catch(SQLException e){
   			LOG.info("exception "+e);
   		}
   	
    	   	 /* Query Execution */
             Query result = eMgr.createNativeQuery(fetchEISpecDetailQuery)
                     		.setParameter(PRMTR_PORCD, jobPorCd);
             eiSpecDtlsList = result.getResultList();
             
             /* Current Index Count Check Begins*/
     		if (currentIndexCount > getMaxIndex()) {
     			LOG.info(READER_INFO+currentIndexCount);
     			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
     			return emptyList;
     		}/* Current Index Count Check Ends*/
           
             currentIndexCount++;
           
             LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
             /* returning the fetchedList */
             return eiSpecDtlsList;
       }
       
       /**
        * Gets the max index.
        *
        * @return the max index
        */
       public int getMaxIndex() {
    	   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
           return maxIndex;
       }
      
       /**
        * Sets the max index.
        * @param maxIndex the new max index
        */
       public void setMaxIndex(int maxIndex) {
    	   LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
           this.maxIndex = maxIndex;
           LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
       }
       
       /**
        * Method to interrupt the Job Execution and returning ExitStatus based on Business Logic Condition.
        *
        * @param stepExec the step exec
        * @return the exit status
        */
        @AfterStep
        public ExitStatus afterStep(StepExecution stepExec){
        	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
        	/* Checking Reader List is Empty */
        	if(eiSpecDtlsList.isEmpty()){
        		/* Setting the Exit Status as STOPPED */
        		stepExec.setExitStatus(ExitStatus.STOPPED);
        		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
       			 /* returning the Exit Status */
        		 return ExitStatus.STOPPED;
        	}
        	LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
         return ExitStatus.COMPLETED;
        }
       
   }
