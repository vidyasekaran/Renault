package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_WKLY_ORDR_IF database table.
 * 
 */
@Embeddable
public class TrnWklyOrdrIfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FILE_ID")
	private String fileId;

	@Column(name="FILE_SEQ")
	private long fileSeq;

	@Column(name="ROW_NO")
	private long rowNo;

	public TrnWklyOrdrIfPK() {
	}
	public String getFileId() {
		return this.fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public long getFileSeq() {
		return this.fileSeq;
	}
	public void setFileSeq(long fileSeq) {
		this.fileSeq = fileSeq;
	}
	public long getRowNo() {
		return this.rowNo;
	}
	public void setRowNo(long rowNo) {
		this.rowNo = rowNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TrnWklyOrdrIfPK)) {
			return false;
		}
		TrnWklyOrdrIfPK castOther = (TrnWklyOrdrIfPK)other;
		return 
			this.fileId.equals(castOther.fileId)
			&& (this.fileSeq == castOther.fileSeq)
			&& (this.rowNo == castOther.rowNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fileId.hashCode();
		hash = hash * prime + ((int) (this.fileSeq ^ (this.fileSeq >>> 32)));
		hash = hash * prime + ((int) (this.rowNo ^ (this.rowNo >>> 32)));
		
		return hash;
	}
}