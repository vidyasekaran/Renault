/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000018/PST-DRG-R000007,R000008,R000009
 * Module          :MONTHLY ORDERING
 * Process Outline :Pojo Class to Hold the batch input parameters and parameters to pass through one processor to another processor 
 * in a composite item processor
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 06-OCT-2015  	 z001870(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.b000018.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nissangroups.pd.model.MstMnthOrdrTakeBasePd;




public class InputBean {

	
	private String porCd;
	private String fileId;
	private String singleRecordFlag;
	private String prdOrdrStgCd;
	private String tableName;
	private String seqNo;
	private Map<String,MstMnthOrdrTakeBasePd> ordrTkBsMnthList = new HashMap<String,MstMnthOrdrTakeBasePd>();
	private List<Object[]> frznOrdrErrRcrds = new ArrayList<Object[]>();
	
	
	public String getPorCd() {
		return porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSingleRecordFlag() {
		return singleRecordFlag;
	}
	public void setSingleRecordFlag(String singleRecordFlag) {
		this.singleRecordFlag = singleRecordFlag;
	}
	public String getPrdOrdrStgCd() {
		return prdOrdrStgCd;
	}
	public void setPrdOrdrStgCd(String prdOrdrStgCd) {
		this.prdOrdrStgCd = prdOrdrStgCd;
	}
	public Map<String, MstMnthOrdrTakeBasePd> getOrdrTkBsMnthList() {
		return ordrTkBsMnthList;
	}
	public void setOrdrTkBsMnthList(
			Map<String, MstMnthOrdrTakeBasePd> ordrTkBsMnthList) {
		this.ordrTkBsMnthList = ordrTkBsMnthList;
	}
	public List<Object[]> getFrznOrdrErrRcrds() {
		return frznOrdrErrRcrds;
	}
	public void setFrznOrdrErrRcrds(List<Object[]> frznOrdrErrRcrds) {
		this.frznOrdrErrRcrds = frznOrdrErrRcrds;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	
	
}