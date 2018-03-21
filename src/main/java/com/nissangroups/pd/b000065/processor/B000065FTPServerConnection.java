/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :B000059/B000062
 * Module          :
 * Process Outline :This program checks and converts the data format example it converts incoming date format to YYYYMMDD
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date      z015847(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000065.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000065.output.B000065FTP_MST_INTERFACE_Details;
import com.nissangroups.pd.b000065.util.B000065CommonUtilityService;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000065FTPServerConnection implements
		ItemProcessor<B000065FTP_MST_INTERFACE_Details,B000065FTP_MST_INTERFACE_Details> {

	/* Constant LOG */
	private static final Log LOG = LogFactory.getLog(B000065FTPServerConnection.class.getName());
	
	
	/*private SftpClient client = null;
    private SshClient ssh = null;*/
    private File localDir = null;
    
    private File[] listFiles = null;
    private String sendSuccessPath = null;
    
    @Autowired(required = false)
    B000065CommonUtilityService commonutilservice;
    
    @Override
	public B000065FTP_MST_INTERFACE_Details process(B000065FTP_MST_INTERFACE_Details item) throws Exception{
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		
		String server = item.getFtpServer();
        int port = Integer.parseInt(item.getFtpPort());
        String user = item.getFtpUserName();
        String pass = item.getFtpPassword();		
        InputStream inputStream = null;
        
        FTPClient ftpClient = new FTPClient();
        try { 
        	
        	ftpClient = CommonUtil.openFtpConnection(server, port, user, pass);
            if(ftpClient != null){
            	
            	String falseStr = "FALSE";
                boolean isFTPDirExist = Boolean.FALSE;
                
                try{					
    				isFTPDirExist = commonutilservice.checkDirectoryExists(ftpClient,item.getFilePath());					
    			}catch(Exception e){
    				LOG.error(PDMessageConsants.M00116.replaceFirst("&1", item.getInterfaceFileId()).replaceFirst("&2", item.getFilePath()).replaceFirst("&3", e.getMessage()));
    				item.setProcessed(falseStr);
    				LOG.error(e);
    			}	
                
                if(isFTPDirExist && null != item.getLocalPath()){	
                	
                	try{
    					localDir = new File(item.getLocalPath());
    				}catch(Exception e){
    					// local path is not found. Error Message : M00117
    					item.setProcessed(falseStr);
    					LOG.equals(e);
    				}
                	
                	if(null != localDir && localDir.isDirectory()){						
    					listFiles = localDir.listFiles();																						
    					if(null != listFiles && listFiles.length == 0 ){								
    						LOG.error(PDMessageConsants.M00117.replaceAll("&1", item.getInterfaceFileId() ));		
    						item.setProcessed(falseStr);								
    					}else{								
    						sendSuccessPath = commonutilservice.getSendSuccessPath();								
    						if(null != sendSuccessPath){									
    							for (int i = 0; i < listFiles.length; i++) {											
    								LOG.info(listFiles[i].getName() + " is processing...");																				
    								if(listFiles[i].getName().equalsIgnoreCase(item.getFileName())){										
    									
    									inputStream = new FileInputStream(listFiles[i].getAbsoluteFile());
    									commonutilservice.copyToFTPFilePath(ftpClient,listFiles[i].getName(),inputStream);													
    									LOG.info(listFiles[i].getName() + " -----------is copied");
    									try{
    										CommonUtil.moveFile(listFiles[i],sendSuccessPath);
    									}catch(Exception e){
    										LOG.error( listFiles[i].getPath() + "File can not move to : " + sendSuccessPath);
    										item.setProcessed(falseStr);
    										LOG.error(e);
    									}
    								}			
    								
    								if(null != item.getCtrlFileFlag() && item.getCtrlFileFlag().equalsIgnoreCase("Y")){											
    									if(listFiles[i].getName().equalsIgnoreCase(item.getControlFileName())){												
    										inputStream = new FileInputStream(listFiles[i].getAbsoluteFile());
    										commonutilservice.copyToFTPFilePath(ftpClient,listFiles[i].getName(),inputStream);														
    										LOG.info(listFiles[i].getName() + " is copied");
    										try{
    											CommonUtil.moveFile(listFiles[i],sendSuccessPath);
    										}catch(Exception e){
    											LOG.error(listFiles[i].getPath() + "File can not move to : " + sendSuccessPath);
    											item.setProcessed(falseStr);
    											LOG.equals(e);
    										}
    									}											
    								}
    								LOG.info(listFiles[i].getName() + " is completed");
    							}
    						}else{
    							LOG.error(PDMessageConsants.M00173.replaceAll("&1", item.getInterfaceFileId()).replaceAll("&2", (String) PDConstants.B000065_SEND_SUCCESS_PATH));
    						}
    						
    						
    					}											
    				}
                	
                }           
     
                inputStream.close();
            	
            }else{            	
            	//cound not open connection, so batch stopped
            	LOG.error(PDMessageConsants.M00114.replaceAll("&1", item.getInterfaceFileId() + commonutilservice.getErrorMsgMap().get("SFTP_CONN_ERROR")));
            	CommonUtil.stopBatch();            	
            }           
          
 
        } catch (IOException ex) {
        	LOG.error(ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	LOG.error(ex);
            }
        }
				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return item;
	}
		
	/*public B000065FTP_MST_INTERFACE_Details process1(B000065FTP_MST_INTERFACE_Details item){
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		
		String falseStr = "FALSE";
		try{			
			// Open a connection.		
			ssh = CommonUtil.openSFTPConnection(item, commonutilservice);			
			//Get the File client 
			client = CommonUtil.openSFTPClient(ssh, item, commonutilservice);			
			boolean isFTPDirExist = Boolean.FALSE;			
			if(null != ssh && ssh.isConnected() && null!=client){				
				try{					
					isFTPDirExist = commonutilservice.checkFTPPathExist(client,item.getFilePath());					
				}catch(Exception e){
					LOG.error(PDMessageConsants.M00116.replaceFirst("&1", item.getInterfaceFileId()).replaceFirst("&2", item.getFilePath()).replaceFirst("&3", e.getMessage()));
					item.setProcessed(falseStr);
				}				
				if(isFTPDirExist ){				
					if(null != item.getLocalPath()){		
											
						try{
							localDir = new File(item.getLocalPath());
						}catch(Exception e){
							// local path is not found. Error Message : M00117
							item.setProcessed(falseStr);
							LOG.equals(e);
						}											
						if(null != localDir && localDir.isDirectory()){						
							listFiles = localDir.listFiles();																						
							if(null != listFiles && listFiles.length == 0 ){								
								LOG.error(PDMessageConsants.M00117.replaceAll("&1", item.getInterfaceFileId() ));		
								item.setProcessed(falseStr);								
							}else{								
								sendSuccessPath = commonutilservice.getSendSuccessPath();								
								if(null != sendSuccessPath){									
									for (int i = 0; i < listFiles.length; i++) {											
										LOG.info(listFiles[i].getName() + " is processing...");																				
										if(listFiles[i].getName().equalsIgnoreCase(item.getFileName())){											
											commonutilservice.copyToFTPFilePath(client,listFiles[i]);													
											LOG.info(listFiles[i].getName() + " -----------is copied");
											try{
												CommonUtil.moveFile(listFiles[i],sendSuccessPath);
											}catch(Exception e){
												LOG.error( listFiles[i].getPath() + "File can not move to : " + sendSuccessPath);
												item.setProcessed(falseStr);
											}
										}			
										
										if(null != item.getCtrlFileFlag() && item.getCtrlFileFlag().equalsIgnoreCase("Y")){											
											if(listFiles[i].getName().equalsIgnoreCase(item.getControlFileName())){												
												commonutilservice.copyToFTPFilePath(client,listFiles[i]);														
												LOG.info(listFiles[i].getName() + " is copied");
												try{
													CommonUtil.moveFile(listFiles[i],sendSuccessPath);
												}catch(Exception e){
													LOG.error(listFiles[i].getPath() + "File can not move to : " + sendSuccessPath);
													item.setProcessed(falseStr);
													LOG.equals(e);
												}
											}											
										}
										LOG.info(listFiles[i].getName() + " is completed");
									}
								}else{
									LOG.error(PDMessageConsants.M00173.replaceAll("&1", item.getInterfaceFileId()).replaceAll("&2", (String) PDConstants.B000065_SEND_SUCCESS_PATH));
								}
								
								
							}											
						}				
					}
										
				} 
			} else{
				LOG.error(PDMessageConsants.M00114.replaceAll("&1", item.getInterfaceFileId() + commonutilservice.getErrorMsgMap().get("SFTP_CONN_ERROR")));
			}
		}catch(Exception e){
			item.setProcessed(falseStr);
			LOG.equals(e);
		}finally{
			// Close the connection			
			if(null != ssh)
				ssh.disconnect();	
		}										
				
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return item;
	}*/
}