/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000058
 * Module          :CM Common
 * Process Outline :Email related Queryconstants
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 * 17-12-15		z015883						Execute B000058	
 */

package com.nissangroups.pd.b000058.util;

/**
 * @author z015883
 * Email related query constants
 */
public class B000058QueryConstants {

	public static final StringBuilder checkMailCndtn=new StringBuilder()
	.append("select count(*) from MST_MAIL_SENDING_CNDTN msc")
	.append(" where msc.JOB_SEQ_ID=:jobExecSeqId AND TRIM(msc.JOB_EXEC_STTS)=:stts group by msc.JOB_SEQ_ID");
	
	public static final StringBuilder getEmailAddressToSend =new StringBuilder()
	.append("select DISTINCT(mn.MAIL_ADDR),p.POR_DESC,p.POR_CD, je.JOB_EXEC_STTS,js.JOBSTRM_DESC, j.JOB_DESC,je.UPDTD_DT,je.ST_DT,je.ST_TIME from ")
	.append(" MST_MAIL_SENDING_CNDTN msc,MST_MAIL_NOTIFCTN mn,MST_JOB_STERAM js,MST_JOB j,TRN_JOB_EXEC je,TRN_JOBSTRM_EXEC jse,TRN_JOBSTRM_SHDL jss,MST_POR p,MST_JOBSTRM_COMBNTN jsc ")
	.append(" where je.JOB_EXEC_SEQ_ID=:jobExecSeqId AND je.JOB_SEQ_ID=j.JOB_SEQ_ID AND msc.JOB_SEQ_ID=j.JOB_SEQ_ID AND RTRIM(msc.DESTN_CD)=RTRIM(mn.DESTN_CD) ")
	.append(" AND j.JOB_SEQ_ID=jsc.JOB_SEQ_ID AND je.JOBSTRM_EXEC_SEQ_ID=jse.JOBSTRM_EXEC_SEQ_ID AND jse.JOBSTRM_SHDL_SEQ_ID=jss.JOBSTRM_SHDL_SEQ_ID ")
	.append(" AND jss.JOBSTRM_SEQ_ID=js.JOBSTRM_SEQ_ID AND js.POR=p.POR_CD");
	
	public static final StringBuilder getFrmEmailAddrss=new StringBuilder()
	.append("select p.VAL1 as Email_From from MST_PRMTR p ")
	.append("where p.PRMTR_CD=:EMAIL AND p.KEY1=:DEFAULT_FROM_ADDRESS");

}
