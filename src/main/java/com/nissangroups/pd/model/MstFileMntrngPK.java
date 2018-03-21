package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_FILE_MNTRNG database table.
 * 
 */
@Embeddable
public class MstFileMntrngPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="JOBSTRM_SEQ_ID", insertable=false, updatable=false)
	private long jobstrmSeqId;

	@Column(name="IF_FILE_PATH")
	private String ifFilePath;

	public MstFileMntrngPK() {
	}
	public long getJobstrmSeqId() {
		return this.jobstrmSeqId;
	}
	public void setJobstrmSeqId(long jobstrmSeqId) {
		this.jobstrmSeqId = jobstrmSeqId;
	}
	public String getIfFilePath() {
		return this.ifFilePath;
	}
	public void setIfFilePath(String ifFilePath) {
		this.ifFilePath = ifFilePath;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstFileMntrngPK)) {
			return false;
		}
		MstFileMntrngPK castOther = (MstFileMntrngPK)other;
		return 
			(this.jobstrmSeqId == castOther.jobstrmSeqId)
			&& this.ifFilePath.equals(castOther.ifFilePath);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.jobstrmSeqId ^ (this.jobstrmSeqId >>> 32)));
		hash = hash * prime + this.ifFilePath.hashCode();
		
		return hash;
	}
}