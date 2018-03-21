package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MNTHLY_BATCH_PROCESS_STTS database table.
 * 
 */
@Embeddable
public class MnthlyBatchProcessSttPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="ORDRTK_BASE_MNTH")
	private String ordrtkBaseMnth;

	@Column(name="BATCH_ID")
	private String batchId;

	@Column(name="SEQ_ID")
	private String seqId;

	public MnthlyBatchProcessSttPK() {
	}
	public String getPorCd() {
		return this.porCd;
	}
	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}
	public String getOrdrtkBaseMnth() {
		return this.ordrtkBaseMnth;
	}
	public void setOrdrtkBaseMnth(String ordrtkBaseMnth) {
		this.ordrtkBaseMnth = ordrtkBaseMnth;
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
		if (!(other instanceof MnthlyBatchProcessSttPK)) {
			return false;
		}
		MnthlyBatchProcessSttPK castOther = (MnthlyBatchProcessSttPK)other;
		return 
			this.porCd.equals(castOther.porCd)
			&& this.ordrtkBaseMnth.equals(castOther.ordrtkBaseMnth)
			&& this.batchId.equals(castOther.batchId)
			&& this.seqId.equals(castOther.seqId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.porCd.hashCode();
		hash = hash * prime + this.ordrtkBaseMnth.hashCode();
		hash = hash * prime + this.batchId.hashCode();
		hash = hash * prime + this.seqId.hashCode();
		
		return hash;
	}
}