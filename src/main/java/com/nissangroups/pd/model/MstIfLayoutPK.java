package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_IF_LAYOUT database table.
 * 
 */
@Embeddable
public class MstIfLayoutPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="IF_FILE_ID")
	private String ifFileId;

	@Column(name="ROW_ID")
	private String rowId;

	@Column(name="IF_CLMN_NAME")
	private String ifClmnName;

	public MstIfLayoutPK() {
	}
	public String getIfFileId() {
		return this.ifFileId;
	}
	public void setIfFileId(String ifFileId) {
		this.ifFileId = ifFileId;
	}
	public String getRowId() {
		return this.rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getIfClmnName() {
		return this.ifClmnName;
	}
	public void setIfClmnName(String ifClmnName) {
		this.ifClmnName = ifClmnName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstIfLayoutPK)) {
			return false;
		}
		MstIfLayoutPK castOther = (MstIfLayoutPK)other;
		return 
			this.ifFileId.equals(castOther.ifFileId)
			&& this.rowId.equals(castOther.rowId)
			&& this.ifClmnName.equals(castOther.ifClmnName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ifFileId.hashCode();
		hash = hash * prime + this.rowId.hashCode();
		hash = hash * prime + this.ifClmnName.hashCode();
		
		return hash;
	}
}