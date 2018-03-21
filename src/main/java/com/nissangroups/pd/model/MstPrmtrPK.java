package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_PRMTR database table.
 * 
 */
@Embeddable
public class MstPrmtrPK implements Serializable {
	//default serial version id, required for serializable classes.
	

	@Column(name="SEQ_NO")
	private long seqNo;

	@Column(name="PRMTR_CD")
	private String prmtrCd;

	private String key1;

	private String key2;

	public MstPrmtrPK() {
	}
	public long getSeqNo() {
		return this.seqNo;
	}
	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}
	public String getPrmtrCd() {
		return this.prmtrCd;
	}
	public void setPrmtrCd(String prmtrCd) {
		this.prmtrCd = prmtrCd;
	}
	public String getKey1() {
		return this.key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return this.key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstPrmtrPK)) {
			return false;
		}
		MstPrmtrPK castOther = (MstPrmtrPK)other;
		return 
			(this.seqNo == castOther.seqNo)
			&& this.prmtrCd.equals(castOther.prmtrCd)
			&& this.key1.equals(castOther.key1)
			&& this.key2.equals(castOther.key2);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.seqNo ^ (this.seqNo >>> 32)));
		hash = hash * prime + this.prmtrCd.hashCode();
		hash = hash * prime + this.key1.hashCode();
		hash = hash * prime + this.key2.hashCode();
		
		return hash;
	}
}