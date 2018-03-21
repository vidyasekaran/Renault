package com.nissangroups.pd.bean;

public class CmHdrStatusVo {

	private long seqNo;
	private long recCount;
	private boolean status;
	private String remarks;

	public long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	public long getRecCount() {
		return recCount;
	}

	public void setRecCount(long recCount) {
		this.recCount = recCount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
