package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_JOBSTRM_COMBNTN database table.
 * 
 */
@Embeddable
public class MstJobstrmCombntnPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="JOBSTRM_SEQ_ID", insertable=false, updatable=false)
	private long jobstrmSeqId;

	@Column(name="JOB_SEQ_ID", insertable=false, updatable=false)
	private long jobSeqId;

	@Column(name="EXEC_SEQ")
	private long execSeq;

	public MstJobstrmCombntnPK() {
	}
	public long getJobstrmSeqId() {
		return this.jobstrmSeqId;
	}
	public void setJobstrmSeqId(long jobstrmSeqId) {
		this.jobstrmSeqId = jobstrmSeqId;
	}
	public long getJobSeqId() {
		return this.jobSeqId;
	}
	public void setJobSeqId(long jobSeqId) {
		this.jobSeqId = jobSeqId;
	}
	public long getExecSeq() {
		return this.execSeq;
	}
	public void setExecSeq(long execSeq) {
		this.execSeq = execSeq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstJobstrmCombntnPK)) {
			return false;
		}
		MstJobstrmCombntnPK castOther = (MstJobstrmCombntnPK)other;
		return 
			(this.jobstrmSeqId == castOther.jobstrmSeqId)
			&& (this.jobSeqId == castOther.jobSeqId)
			&& (this.execSeq == castOther.execSeq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.jobstrmSeqId ^ (this.jobstrmSeqId >>> 32)));
		hash = hash * prime + ((int) (this.jobSeqId ^ (this.jobSeqId >>> 32)));
		hash = hash * prime + ((int) (this.execSeq ^ (this.execSeq >>> 32)));
		
		return hash;
	}
}