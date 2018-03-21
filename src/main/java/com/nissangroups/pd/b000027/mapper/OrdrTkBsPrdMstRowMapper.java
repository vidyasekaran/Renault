/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000027
 * Module          :O Ordering
 * Process Outline :Create Monthly Production Order
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 18-11-2015      z014433(RNTBCI)               Initial Version
 *
 */   
package com.nissangroups.pd.b000027.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.nissangroups.pd.b000027.mapper.OrdrTkBsPrdMstRowMapperPK;


@Entity
public class OrdrTkBsPrdMstRowMapper implements Serializable{
	
	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	OrdrTkBsPrdMstRowMapperPK id;
	
	public OrdrTkBsPrdMstRowMapperPK getId() {
		return id;
	}
	
	public void setId(OrdrTkBsPrdMstRowMapperPK id){
		this.id = id;
	}
	
	/** Variable stage code */
	@Column(name="STAGE_CD")
	private String STAGE_CD;
	
	/** Variable stage status code */
	@Column(name = "STAGE_STTS_CD")
	String STAGE_STTS_CD;
	
	/** Variable system Lock code */
	@Column(name = "SYS_LCK_STTS_CD")
	String SYS_LCK_STTS_CD;
	
	/**
	 * Gets the stageCd
	 *
	 * @return the sTAGE_CD
	 */
	public String getSTAGE_CD() {
		return STAGE_CD;
	}

	/**
	 * Sets the stageCd
	 *
	 * @param sTAGE_CD the stageCd to set
	 */
	public void setSTAGE_CD(String sTAGE_CD) {
		STAGE_CD = sTAGE_CD;
	}
	
	/**
	 * Gets the stageStatusCd
	 *
	 * @return the sTAGE_STTS_CD
	 */
	public String getSTAGE_STTS_CD() {
		return STAGE_STTS_CD;
	}

	/**
	 * Sets the stageStatusCd
	 *
	 * @param sTAGE_STTS_CD the stageStatusCd to set
	 */
	public void setSTAGE_STTS_CD(String sTAGE_STTS_CD) {
		STAGE_STTS_CD = sTAGE_STTS_CD;
	}

	/**
	 * Gets the systemLockStsCd
	 *
	 * @return the sYS_LCK_STTS_CD
	 */
	public String getSYS_LCK_STTS_CD() {
		return SYS_LCK_STTS_CD;
	}

	/**
	 * Sets the systemLockStsCd
	 *
	 * @param sYS_LCK_STTS_CD the systemLockStsCd to set
	 */
	public void setSYS_LCK_STTS_CD(String sYS_LCK_STTS_CD) {
		SYS_LCK_STTS_CD = sYS_LCK_STTS_CD;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((STAGE_CD == null) ? 0 : STAGE_CD.hashCode());
		result = prime * result
				+ ((STAGE_STTS_CD == null) ? 0 : STAGE_STTS_CD.hashCode());
		result = prime * result
				+ ((SYS_LCK_STTS_CD == null) ? 0 : SYS_LCK_STTS_CD.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OrdrTkBsPrdMstRowMapper)) {
			return false;
		}
		OrdrTkBsPrdMstRowMapper other = (OrdrTkBsPrdMstRowMapper) obj;
		
		if (STAGE_CD == null) {
			if (other.STAGE_CD != null) {
				return false;
			}
		} else if (!STAGE_CD.equals(other.STAGE_CD)) {
			return false;
		}
		if (STAGE_STTS_CD == null) {
			if (other.STAGE_STTS_CD != null) {
				return false;
			}
		} else if (!STAGE_STTS_CD.equals(other.STAGE_STTS_CD)) {
			return false;
		}
		if (SYS_LCK_STTS_CD == null) {
			if (other.SYS_LCK_STTS_CD != null) {
				return false;
			}
		} else if (!SYS_LCK_STTS_CD.equals(other.SYS_LCK_STTS_CD)) {
			return false;
		}
		return true;
	}


}
