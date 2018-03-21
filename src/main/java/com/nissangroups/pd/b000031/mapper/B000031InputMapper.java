package com.nissangroups.pd.b000031.mapper;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.nissangroups.pd.b000031.mapper.B000031InputMapperPK;

@Entity
public class B000031InputMapper implements Serializable{
	
	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	B000031InputMapperPK id;

	public B000031InputMapperPK getId() {
		return id;
	}

	public void setId(B000031InputMapperPK id) {
		this.id = id;
	}
	
	
	

}
