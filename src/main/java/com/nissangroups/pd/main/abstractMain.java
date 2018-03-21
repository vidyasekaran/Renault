/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-abstractMain
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * The Class abstractMain.
 */
@Configuration
@PropertySource("classpath:batch_config.properties")
public abstract class abstractMain {


/** Variable environment. */
@Autowired
static Environment environment;

/**
 * Run.
 *
 * @param args the args
 */
abstract void run(String[] args);

/**
 * Gets the error message.
 *
 * @param messageKey the message key
 * @return the error message
 */
public static String getErrorMessage(String messageKey){
return environment.getProperty(messageKey);
}



}
