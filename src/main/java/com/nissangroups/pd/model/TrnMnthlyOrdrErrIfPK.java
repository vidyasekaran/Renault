package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TRN_MNTHLY_ORDR_ERR_IF database table.
 * 
 */
@Embeddable
public class TrnMnthlyOrdrErrIfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="SEQ_NO")
	private long seqNo;

	@Column(name="ROW_NO")
	private long rowNo;

	public TrnMnthlyOrdrErrIfPK() {
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
		if (!(other instanceof TrnMnthlyOrdrErrIfPK)) {
			return false;
		}
		TrnMnthlyOrdrErrIfPK castOther = (TrnMnthlyOrdrErrIfPK)other;
		return 
			this.ifFileId.equals(castOther.ifFileId)
			&& (this.seqNo == castOther.seqNo)
			&& (this.rowNo == castOther.rowNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ifFileId.hashCode();
		hash = hash * prime + ((int) (this.seqNo ^ (this.seqNo >>> 32)));
		hash = hash * prime + ((int) (this.rowNo ^ (this.rowNo >>> 32)));
		
		return hash;
	}
}