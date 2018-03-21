package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the MST_IF_LAYOUT database table.
 * 
 */
@Entity
@Table(name="MST_IF_LAYOUT")
@NamedQuery(name="MstIfLayout.findAll", query="SELECT m FROM MstIfLayout m")
public class MstIfLayout implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MstIfLayoutPK id;

	@Column(name="COLUMN_ORDR")
	private BigDecimal columnOrdr;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DATA_TYPE")
	private String dataType;

	@Column(name="DATE_CONVERSION")
	private String dateConversion;

	@Column(name="DEFAULT_VAL")
	private String defaultVal;

	@Column(name="END_POSITION")
	private BigDecimal endPosition;

	private String format;

	@Column(name="FXD_LENGTH")
	private String fxdLength;

	private BigDecimal lngth;

	@Column(name="STRT_POSITION")
	private BigDecimal strtPosition;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstIfLayout() {
	}

	public MstIfLayoutPK getId() {
		return this.id;
	}

	public void setId(MstIfLayoutPK id) {
		this.id = id;
	}

	public BigDecimal getColumnOrdr() {
		return this.columnOrdr;
	}

	public void setColumnOrdr(BigDecimal columnOrdr) {
		this.columnOrdr = columnOrdr;
	}

	public String getCrtdBy() {
		return this.crtdBy;
	}

	public void setCrtdBy(String crtdBy) {
		this.crtdBy = crtdBy;
	}

	public Timestamp getCrtdDt() {
		return this.crtdDt;
	}

	public void setCrtdDt(Timestamp crtdDt) {
		this.crtdDt = crtdDt;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDateConversion() {
		return this.dateConversion;
	}

	public void setDateConversion(String dateConversion) {
		this.dateConversion = dateConversion;
	}

	public String getDefaultVal() {
		return this.defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public BigDecimal getEndPosition() {
		return this.endPosition;
	}

	public void setEndPosition(BigDecimal endPosition) {
		this.endPosition = endPosition;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFxdLength() {
		return this.fxdLength;
	}

	public void setFxdLength(String fxdLength) {
		this.fxdLength = fxdLength;
	}

	public BigDecimal getLngth() {
		return this.lngth;
	}

	public void setLngth(BigDecimal lngth) {
		this.lngth = lngth;
	}

	public BigDecimal getStrtPosition() {
		return this.strtPosition;
	}

	public void setStrtPosition(BigDecimal strtPosition) {
		this.strtPosition = strtPosition;
	}

	public String getUpdtdBy() {
		return this.updtdBy;
	}

	public void setUpdtdBy(String updtdBy) {
		this.updtdBy = updtdBy;
	}

	public Timestamp getUpdtdDt() {
		return this.updtdDt;
	}

	public void setUpdtdDt(Timestamp updtdDt) {
		this.updtdDt = updtdDt;
	}

	@PrePersist
    void onCreate() {
        this.setCrtdDt(CommonUtil.createTimeStamp());
        this.setUpdtdDt(CommonUtil.createTimeStamp());
    }
    
    @PreUpdate
    void onPersist() {
        this.setUpdtdDt(CommonUtil.createTimeStamp());
    }

}