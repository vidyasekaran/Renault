package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CMN_FILE_HDR database table.
 * 
 */
@Embeddable
public class CmnFileHdrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="IF_FILE_ID", insertable=false, updatable=false)
	private String ifFileId;

	@Column(name="SEQ_NO")
	private long seqNo;

	public CmnFileHdrPK() {
	}
	public String getIfFileId() {
		return this.ifFileId;
	}
	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}
	public long getSeqNo() {
		return this.seqNo;
	}
	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CmnFileHdrPK)) {
			return false;
		}
		CmnFileHdrPK castOther = (CmnFileHdrPK)other;
		return 
			this.ifFileId.equals(castOther.ifFileId)
			&& (this.seqNo == castOther.seqNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ifFileId.hashCode();
		hash = hash * prime + ((int) (this.seqNo ^ (this.seqNo >>> 32)));
		
		return hash;
	}
}