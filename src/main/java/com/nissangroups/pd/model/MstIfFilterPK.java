package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_IF_FILTER database table.
 * 
 */
@Embeddable
public class MstIfFilterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="FLTR_GRP")
	private long fltrGrp;

	@Column(name="COL_NAME")
	private String colName;

	public MstIfFilterPK() {
	}
	public String getIfFileId() {
		return this.ifFileId;
	}
	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}
	public long getFltrGrp() {
		return this.fltrGrp;
	}
	public void setFltrGrp(long fltrGrp) {
		this.fltrGrp = fltrGrp;
	}
	public String getColName() {
		return this.colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstIfFilterPK)) {
			return false;
		}
		MstIfFilterPK castOther = (MstIfFilterPK)other;
		return 
			this.ifFileId.equals(castOther.ifFileId)
			&& (this.fltrGrp == castOther.fltrGrp)
			&& this.colName.equals(castOther.colName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ifFileId.hashCode();
		hash = hash * prime + ((int) (this.fltrGrp ^ (this.fltrGrp >>> 32)));
		hash = hash * prime + this.colName.hashCode();
		
		return hash;
	}
}