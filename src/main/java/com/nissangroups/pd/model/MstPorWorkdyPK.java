package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MST_POR_WORKDY database table.
 * 
 */
@Embeddable
public class MstPorWorkdyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String por;

	@Temporal(TemporalType.DATE)
	private java.util.Date dt;

	public MstPorWorkdyPK() {
	}
	public String getPor() {
		return this.por;
	}
	public void setPor(String por) {
		this.por = por;
	}
	public java.util.Date getDt() {
		return this.dt;
	}
	public void setDt(java.util.Date dt) {
		this.dt = dt;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MstPorWorkdyPK)) {
			return false;
		}
		MstPorWorkdyPK castOther = (MstPorWorkdyPK)other;
		return 
			this.por.equals(castOther.por)
			&& this.dt.equals(castOther.dt);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.por.hashCode();
		hash = hash * prime + this.dt.hashCode();
		
		return hash;
	}
}