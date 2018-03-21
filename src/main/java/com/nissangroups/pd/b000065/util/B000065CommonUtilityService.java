/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This is the common utility service which is used to load static from database  *             
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015896(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000065.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;

import com.nissangroups.pd.b000065.output.B000065FTP_MST_INTERFACE_Details;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.QueryConstants;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.sftp.FileAttributes;
public class B000065CommonUtilityService {

	/* Constant LOG */
	private static final Log LOG = LogFactory.getLog(B000065CommonUtilityService.class.getName());
	

	/* Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	private String ftpSendSuccessPath = null;
	
	private Map<String,String> errorMsgMap = new HashMap<String,String>();
	
	
	public boolean checkDirectoryExists(FTPClient ftpClient, String dirPath) throws Exception {
		if (ftpClient == null)
			throw new Exception("SFTP channel is not initialized.");
		if (ftpClient == null || dirPath.trim().length() == 0)
			throw new Exception("Remote directory name is not provided.");
        ftpClient.changeWorkingDirectory(dirPath);
        int returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) {
            return false;
        }
        return true;
    }
		
	public boolean checkFTPPathExist(SftpClient client, String filePath) throws Exception{

		FileAttributes fAttr = null;
		if (client == null)
			throw new Exception("SFTP channel is not initialized.");
		if (client == null || filePath.trim().length() == 0)
			throw new Exception("Remote directory name is not provided.");
		try {				
			fAttr = client.stat(filePath);		
			
			if(fAttr.isDirectory()){
				client.cd(filePath);
				return true;
			}
			
		} catch (Exception e) {
			throw new Exception("Failed to change remote directory: "	+ e);
		}
		return false;
	}
	
	
	public String getSendSuccessPath(){
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		if(null == ftpSendSuccessPath){
			Map<String, MstPrmtr> a = CommonUtil.getPrmtrMstDetails(entityManager);		
			MstPrmtr mstPrmtr = a.get(PDConstants.B000065_SEND_SUCCESS_PATH);			
			setFtpSendSuccessPath(mstPrmtr.getVal1());		
		}		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return ftpSendSuccessPath;
		
	}
	
	public synchronized void copyToFTPFilePath(FTPClient ftpClient, String remoteFile, InputStream inputStream)
			throws Exception {

		try {
			if (ftpClient != null) {
				ftpClient.storeFile(remoteFile, inputStream);
			}
		} catch (Exception e) {
			LOG.error(e);
			LOG.error("Faile to upload file : " + remoteFile);
			throw new Exception("Failed to upload file: " + e.getMessage());
		}
	}
	
	public synchronized void copyToFTPFilePath(SftpClient client, File file)
			throws Exception {

		String filePath = file.getAbsolutePath();
		String fileName = file.getName();
		try {
			if (client != null) {
				client.put(filePath, fileName);
			}
		} catch (Exception e) {
			LOG.error(e);
			LOG.error("Faile to upload file : " + filePath);
			throw new Exception("Failed to upload file: " + e.getMessage());
		}
	}

	
	/**
	 * update common file header with row count for the file.
	 * 
	 * @param ifFileId
	 *            interface file id
	 * @param seqNo
	 *            sequence number
	 * @param fileName
	 *            filename
	 * @param recCount
	 *            record count
	 */
	public void updateCommonFileHdr(B000065FTP_MST_INTERFACE_Details item, String status) {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			
			Query updateHdrInsert = entityManager
					.createNativeQuery(QueryConstants.updateCurrDateCmnFileHdr
							.toString());
			updateHdrInsert.setParameter("IF_FILE_ID", item.getInterfaceFileId());
			updateHdrInsert.setParameter("SEQ_NO", item.getSeqNo());	
			updateHdrInsert.setParameter("STATUS", status);	
			updateHdrInsert.setParameter("UPDTD_DT", CommonUtil.createTimeStamp());	
			
			updateHdrInsert.executeUpdate();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}



	public String getFtpSendSuccessPath() {
		return ftpSendSuccessPath;
	}



	public void setFtpSendSuccessPath(String ftpSendSuccessPath) {
		this.ftpSendSuccessPath = ftpSendSuccessPath;
	}


	public Map<String,String> getErrorMsgMap() {
		return errorMsgMap;
	}


	public void setErrorMsgMap(Map<String,String> errorMsgMap) {
		this.errorMsgMap = errorMsgMap;
	}
}
