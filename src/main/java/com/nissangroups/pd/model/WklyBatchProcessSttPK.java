package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the WKLY_BATCH_PROCESS_STTS database table.
 * 
 */
@Embeddable
public class WklyBatchProcessSttPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="ORDR_TAKE_BASE_WK_NO")
	private String ordrTakeBaseWkNo;

	@Column(name="BATCH_ID")
	private String batchId;

	@Column(name="SEQ_ID")
	private String seqId;

	public WklyBatchProcessSttPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}
	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}
	public String getOrdrTakeBaseWkNo() {
		return this.ordrTakeBaseWkNo;
	}
	public void setOrdrTakeBaseWkNo(String ordrTakeBaseWkNo) {
		this.ordrTakeBaseWkNo = ordrTakeBaseWkNo;
	}
	public String getBatchId() {
		return this.batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getSeqId() {
		return this.seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WklyBatchProcessSttPK)) {
			return false;
		}
		WklyBatchProcessSttPK castOther = (WklyBatchProcessSttPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrTakeBaseMnth.equals(castOther.ordrTakeBaseMnth)
			&& this.ordrTakeBaseWkNo.equals(castOther.ordrTakeBaseWkNo)
			&& this.batchId.equals(castOther.batchId)
			&& this.seqId.equals(castOther.seqId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrTakeBaseMnth.hashCode();
		hash = hash * prime + this.ordrTakeBaseWkNo.hashCode();
		hash = hash * prime + this.batchId.hashCode();
		hash = hash * prime + this.seqId.hashCode();
		
		return hash;
	}
}