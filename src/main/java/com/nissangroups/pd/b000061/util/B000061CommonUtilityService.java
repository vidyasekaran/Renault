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
package com.nissangroups.pd.b000061.util;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.ERROR_MESSAGE;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.scope.context.ChunkContext;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstIfFilter;
import com.nissangroups.pd.model.MstIfLayout;
import com.nissangroups.pd.model.MstIfSorting;
import com.nissangroups.pd.model.MstInterface;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFQueryConstants;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;
public class B000061CommonUtilityService {

	/* Constant LOG */
	private static final Log LOG = LogFactory.getLog(B000061CommonUtilityService.class.getName());
	

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/**
	 * Stores interface master detail
	 */
	private Map interfaceMaster = null;

	/**
	 * stores order as key and list of interfaceLayoutVO as value
	 */
	private Map interfaceLayoutByOrder = null;
	
	private String interfaceId = null;
	
	private String sendInterfaceFileName = null;
	
	private String sendCtrlFileName = null;
	
	private String timeStamp = null;
	
	private String localPath = null;
	
	private Long maxSeqNo = null;
	
	private Integer maxEndPosition = null;
	
	
		
	
	


	/*
	 * Extracts the Filter Criteria based on the Interface_file_Id
	 * 	
	 */
	public String generateFilterCriteria(String interfaceId) {		
		String filterCriteria = null;
		
		
		
		/*All the filter conditions belogs to the same Filter Group should have the AND condition
		 * All the filter conditions belogs to the same Filter Group should be within the braces.
		 */
		Map<Long,Map<String,String[]>> filterMap = null;
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {

			List<MstIfFilter> interfaceMstList = entityManager.createQuery(
					"SELECT m FROM MstIfFilter m where m.id.ifFileId='" + interfaceId + "'").getResultList();
			
			if (null != interfaceMstList && !interfaceMstList.isEmpty()) {
				
				filterMap = getFilterMap(interfaceMstList);
				
				
				Set<Long> keySetFilterGrp =  filterMap.keySet();
				Iterator<Long> keySetFilterGrpIterator = keySetFilterGrp.iterator();				
				StringBuilder filterCriteriaFormation = new StringBuilder("");
				while(keySetFilterGrpIterator.hasNext()){
					Long key = keySetFilterGrpIterator.next();					
					Map<String,String[]> innerHash = filterMap.get(key);					
					filterCriteriaFormation.append(constructANDConditonForSameGrp(innerHash));
					
					filterCriteriaFormation.append(keySetFilterGrpIterator.hasNext() ? " OR " : "");	
															
				}				
				// Combine the andStr and orStr
				filterCriteria = filterCriteriaFormation.toString();
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);			
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return filterCriteria;			
	}

	
	private Map<Long, Map<String, String[]>> getFilterMap(List<MstIfFilter> interfaceMstList) {
		Map<Long,Map<String,String[]>>  filterMap = new HashMap<Long,Map<String,String[]>>();
		String[] operandVal = null;
		Map<String,String[]> subHash = null;
		
		for (Iterator<MstIfFilter> iterator = interfaceMstList
				.iterator(); iterator.hasNext();) {
			MstIfFilter interfaceMst = iterator.next();
			
			Long fltrGrp = interfaceMst.getId().getFltrGrp();
			
			if(filterMap.containsKey(fltrGrp)){
				subHash = filterMap.get(fltrGrp);
			}else{
				subHash = new HashMap<String,String[]>();
				filterMap.put(fltrGrp, subHash);
			}					
			operandVal = new String[2];
			operandVal[0] = interfaceMst.getFltrType();
			operandVal[1] = interfaceMst.getFltrVal();					
			subHash.put(interfaceMst.getId().getColName(), operandVal);
			
		}
		return filterMap;
	}


	/*All the filter conditions belogs to the same Filter Group should have the AND condition
	 * All the filter conditions belogs to the same Filter Group should be within the braces.
	 */
	private String constructANDConditonForSameGrp(Map<String,String[]> andHash){
		//AND Operations 
		StringBuilder andStr = new StringBuilder("(");				
		Set<String> keySet = andHash.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   String key = keySetIterator.next();				   
		   andStr.append("c.").append(key.toLowerCase()).append(getFilterType(andHash.get(key)));				   
		   if(keySetIterator.hasNext()){
			andStr.append(" AND ");	
		   }
		  
		}
		 andStr.append(")");
		return andStr.toString();
	}
	
	
	/**
	 * update file status on common file header
	 * 
	 * @param ifFileId
	 *            interface file id
	 * @param seqNo
	 *            sequence number
	 * @param fileName
	 *            filename
	 * @param status
	 */
	public void updateFileStatusCommonFileHdr() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query updateHdrInsert = entityManager
					.createNativeQuery(PDConstants.updateCmnHeaderCtrl
							.toString());
			updateHdrInsert.setParameter("IF_FILE_ID", getInterfaceId());
			updateHdrInsert.setParameter("SEQ_NO", getMaxSeqNo());			
			updateHdrInsert.setParameter("STTS", "P");
			updateHdrInsert.setParameter("FILE_NAME", getSendInterfaceFileName());
			updateHdrInsert.setParameter("CONTROL_FILE_NAME", getSendCtrlFileName());
			updateHdrInsert.executeUpdate();
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			LOG.error(e.getStackTrace());
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}
	
	/*
	 * Converting the Filter type
	 */
	private String getFilterType(String[] strings) {
		
		StringBuilder filterType = null;
		if(null != strings){
			filterType = new StringBuilder(" ");
			
			if(strings[0].equalsIgnoreCase(PDConstants.EQUALSTR)){
				
				filterType.append(PDConstants.EQUALSTR).append("'").append(strings[1]).append("'");
				
			}else if(strings[0].equalsIgnoreCase(PDConstants.LIKESTR)){
				
				filterType.append(PDConstants.LIKESTR).append(" ").append("'%").append(strings[1]).append("%'");
				
			}else if(strings[0].equalsIgnoreCase(PDConstants.INSTR)){
				
				filterType.append(PDConstants.INSTR).append("(").append(strings[1]).append(")");
				
			}else if(strings[0].equalsIgnoreCase(PDConstants.BETWEENSTR)){
				filterType.append(PDConstants.BETWEENSTR).append(" ").append(strings[1]).append("");
			}else if(strings[0].equalsIgnoreCase(PDConstants.CURRENT_YM)){
				
				String firstDate = null;
				String lastDate = null;
				StringBuilder firstLastDate = new StringBuilder();
				try {
					firstDate = CommonUtil.getFirstDay(new Date());
					lastDate = CommonUtil.getLastDay(new Date());								
					
					firstLastDate.append("'").append(firstDate).append("'").append(" AND ").append("'").append(lastDate).append("'");
					
					
				} catch (Exception e) {				
					LOG.error("Error on CURRENT_YM : " + e.getMessage()) ;
					LOG.error(ERROR_MESSAGE, e);
				}
				
				filterType.append(PDConstants.BETWEENSTR).append(" ").append(firstLastDate.toString()).append("");
			}
			filterType.append(" ");
		}
		return (null != filterType ) ? filterType.toString() : null;
	}


	public boolean insertCmnFileHdr(String ifFileId, long seqNo) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Query hdrInsert = entityManager
					.createNativeQuery(IFQueryConstants.insertCmnHeader2arg
							.toString());
			hdrInsert.setParameter("IF_FILE_ID", ifFileId);
			hdrInsert.setParameter("SEQ_NO", seqNo);
			hdrInsert.executeUpdate();

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
			return false;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return true;
	}
	
	/*
	 * Sort  the extracted Data based on the Sorting Configuration
	 */
	public String generateSortOrderCriteria(String interfaceId) {
		
		String orderByFormation = "";		
		try{
			List<MstIfSorting> interfaceMstList = entityManager.createQuery(
					"SELECT m FROM MstIfSorting m where m.id.ifFileId='" + interfaceId + "' ORDER BY m.prity").getResultList();
			
			
			if (null != interfaceMstList && !interfaceMstList.isEmpty()) {				
				StringBuilder orderQuery = new StringBuilder(" Order BY ");
				for (Iterator<MstIfSorting> iterator = interfaceMstList
						.iterator(); iterator.hasNext();) {
					
					MstIfSorting mstIfFilter = iterator.next();
					String order = "ASC";										
					
					order = mstIfFilter.getOrdr().equalsIgnoreCase(PDConstants.ORDER_BY_ASC) ? " ASC " : " DESC";
					
					orderQuery.append(mstIfFilter.getId().getColName().toString()).append(order);
					
					orderQuery.append(iterator.hasNext() ? "," : "");
										
				}				
				orderByFormation = orderQuery.toString();
			}			
		}catch(Exception e){
			LOG.error(ERROR_MESSAGE, e);
		}
		return orderByFormation;
	}


	/*
	 * Store the max of seqno for received interface id.
	 */
	public Long getMaxSeqNo(String interfaceId2) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			Long max = (Long) entityManager
					.createQuery("SELECT MAX(d.id.seqNo) from CmnInterfaceData d where d.id.ifFileId='" + interfaceId2 + "'").getSingleResult();					
			if (null != max ) {
				return max;
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);			
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return 1L;
	}
	
	public Map getInterfaceLayoutByOrder() {
		return interfaceLayoutByOrder;
	}


	public void setInterfaceLayoutByOrder(Map interfaceLayoutByOrder) {
		this.interfaceLayoutByOrder = interfaceLayoutByOrder;
	}
		
	/**
	 * Gets the entityManager
	 * 
	 * @return the entityManager
	 */

	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 * 
	 * @param entityManager
	 *            the entityManager to set
	 */

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getSendInterfaceFileName() {
		return sendInterfaceFileName;
	}

	public void setSendInterfaceFileName(String sendInterfaceFileName) {
		this.sendInterfaceFileName = sendInterfaceFileName;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public Long getMaxSeqNo() {
		return maxSeqNo;
	}

	public void setMaxSeqNo(Long maxSeqNo) {
		this.maxSeqNo = maxSeqNo;
	}

	public String getSendCtrlFileName() {
		return sendCtrlFileName;
	}

	public void setSendCtrlFileName(String sendCtrlFileName) {
		this.sendCtrlFileName = sendCtrlFileName;
	}
	
	
	/**
	 * Query and store Interface Master Data
	 * 
	 * @param interface file id
	 */
	@SuppressWarnings("unchecked")
	public void storeInterfaceMasterData(String interfaceFileID)throws Exception {
		
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {

			List<MstInterface> interfaceMstList = getEntityManager().createQuery(
					"SELECT  f FROM MstInterface  f where f.ifFileId='"
							+ interfaceFileID + "'").getResultList();			

			if (null != interfaceMstList && !interfaceMstList.isEmpty()) {
				interfaceMaster = new HashMap();
				for (Iterator<MstInterface> iterator = interfaceMstList
						.iterator(); iterator.hasNext();) {
					MstInterface interfaceMst = iterator.next();
					interfaceMaster.put(interfaceMst.getIfFileId(), interfaceMst);
				}
			}else{				
				LOG.info("Interface File ID: " + interfaceFileID + PDMessageConsants.M00003 + " on Interface Master table") ;
				LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00076, new String[]{"&1"}, new String[]{ "Interface File ID: " + interfaceFileID}) );
				CommonUtil.stopBatch();				
			}
		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	
	/**
	 * Query interface layout table and store it in interfaceLayoutList
	 * 
	 * @param interface file id
	 */
	@SuppressWarnings("unchecked")
	public void storeInterfaceLayoutDetail(String interfaceFileID) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		try {
			List<MstIfLayout> mstIfLayoutList = (ArrayList<MstIfLayout>) getEntityManager()
					.createQuery(
							"SELECT  f FROM MstIfLayout  f where f.id.ifFileId='"
									+ interfaceFileID
									+ "' order by f.columnOrdr")
					.getResultList();

			// START:: Load Interface Master into hashmap - key as fileid and
			// value as all row of that file id.

			if (null != mstIfLayoutList && !mstIfLayoutList.isEmpty()) {
				interfaceLayoutByOrder = new HashMap();
				interfaceLayoutByOrder.put(interfaceFileID, mstIfLayoutList);
			}else{				
				LOG.info("Interface File ID: " + interfaceFileID + PDMessageConsants.M00003+ " on Interface_Layout table" );
				LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00076, new String[]{"&1"}, new String[]{ "Interface File ID: " + interfaceFileID}) );
				CommonUtil.stopBatch();
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);						
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}


	public void initializeData(String interfaceFileID, ChunkContext chunkContext) throws Exception {		
		
		
		LOG.info("############### Send Interface File Id :" + interfaceFileID);
		
		storeInterfaceMasterData(interfaceFileID);
		storeInterfaceLayoutDetail(interfaceFileID);
		
		
		MstInterface mstInterface = (MstInterface) getInterfaceMaster().get(interfaceFileID);
		
		List<MstIfLayout> mstIfLayout = (List<MstIfLayout>) getInterfaceLayoutByOrder().get(interfaceFileID);
		
		String recievInterfaceId = mstInterface.getReceiveIfFileId();		
		LOG.info("############### Receive Interface File Id :" + recievInterfaceId);
		
		setInterfaceId(interfaceFileID);									
		setLocalPath(mstInterface.getLocalPath());
		
		Integer maxEndPos = CommonUtil.getMaxEndPosition(mstIfLayout);
		
		setMaxEndPosition(maxEndPos);
		
		
		String localPath = mstInterface.getLocalPath();
		
		if(null != localPath){
			
			File f = new File(localPath);
			
			if( !(f.exists() && f.isDirectory())){
				LOG.info(PDMessageConsants.M00135.replaceAll("&1", interfaceFileID).replaceAll("&2", localPath)) ;
				LOG.info( CommonUtil.replacePrmtWithMsg(PDMessageConsants.M00076, new String[]{"&1"}, new String[]{ "Interface File ID: " + interfaceFileID}) );
				CommonUtil.stopBatch();
			}
			
		}
				
		String format = CommonUtil.currentDateTimeinFormat(getFileCreationTimeStamp());
		
		setSendInterfaceFileName(mstInterface.getFilenameFormat()+ "_" + format + PDConstants.B000061_FILE_EXT);
		
		//set null if ctrl file flag is "N"
		if(null != mstInterface.getControlFileFlag() && mstInterface.getControlFileFlag() .equalsIgnoreCase("Y")){
			setSendCtrlFileName(mstInterface.getFilenameFormat()+ PDConstants.B000061_CTRL_FILE + format + PDConstants.B000061_FILE_EXT);
		}						
		if(null != recievInterfaceId){
			
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(PDConstants.R_INTERFACE_FILE_ID, recievInterfaceId);			
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("CMN_FILE_STTS", "P");														
			long seqNo = 0L;
			try {
				seqNo = getSequenceNoForInterfaceId(interfaceFileID,null);						
				insertCmnFileHdr(interfaceFileID, seqNo);				
			} catch (PdApplicationException e) {	
				LOG.error(ERROR_MESSAGE, e);
				LOG.error(e.getMessage());
			}
			
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("MAX_SEQ_NO", seqNo);
			setMaxSeqNo(seqNo);
			
		}else{
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(PDConstants.R_INTERFACE_FILE_ID, interfaceFileID);
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("CMN_FILE_STTS", "U");
		}
		
		//if no receiv if found, the max seq no using sender interface file id from cmn file header
		if(null == recievInterfaceId){
			setMaxSeqNo(getSequenceNoForInterfaceId(interfaceFileID,"U")-1);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public long getSequenceNoForInterfaceId(String fileInterfaceID,String stts)
			throws PdApplicationException {
		Long seqNo = 0L;
		List<Object> result = null;
		
		StringBuilder query = null;
		try {
			LOG.info(new StringBuilder("Starint....LOG ")
					.append(this.getClass().getName())
					.append(" in getSequenceNoForInterfaceId").toString());
			if (null != fileInterfaceID) {
				
				query = new StringBuilder("SELECT MAX(f.id.seqNo) FROM CmnFileHdr  f where f.id.ifFileId='" + fileInterfaceID + "' ");
				
				if(null != stts){
					query.append(" AND f.stts='").append(stts).append("'");
				}
						
				result = (List<Object>) entityManager.createQuery(query.toString()).getResultList();
				seqNo = ((Long) result.get(0) == null) ? 0L : (Long) result
						.get(0);
				seqNo++;
			} else {
				LOG.info("Interface id should not be null");
			}

		} catch (Exception e) {
			LOG.error(ERROR_MESSAGE, e);
		}

		return seqNo;

	}
	
	public Map getInterfaceMaster() {
		return interfaceMaster;
	}


	public void setInterfaceMaster(Map interfaceMaster) {
		this.interfaceMaster = interfaceMaster;
	}


	public String getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getFileCreationTimeStamp(){
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);		
		if(null == timeStamp){
			Map<String, MstPrmtr> a = CommonUtil.getPrmtrMstDetails(entityManager);		
			MstPrmtr mstPrmtr = a.get(PDConstants.B000061_TIME_STAMP_FMT);			
			setTimeStamp(mstPrmtr.getVal1());		
		}		
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return getTimeStamp();
		
	}

	
	public void setMaxEndPosition(Integer maxEndPosition) {
		this.maxEndPosition = maxEndPosition;
	}

	public Integer getMaxEndPosition() {

		return maxEndPosition;
	}
	

}
