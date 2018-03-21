package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CMN_FILE_HDR database table.
 * 
 */
@Entity
@Table(name="CMN_FILE_HDR")
@NamedQuery(name="CmnFileHdr.findAll", query="SELECT c FROM CmnFileHdr c")
public class CmnFileHdr implements Serializable {
	

	@EmbeddedId
	private CmnFileHdrPK id;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="REC_COUNT")
	private BigDecimal recCount;

	private String remarks;

	private String stts;

	@Column(name="TRN_TYPE")
	private String trnType;
	
	@Column(name="CONTROL_FILE_NAME")
	private String controlFileName;

	//bi-directional many-to-one association to CmnInterfaceMst
	@ManyToOne
	@JoinColumn(name="IF_FILE_ID", insertable=false, updatable=false)
	private CmnInterfaceMst cmnInterfaceMst;

	//bi-directional many-to-one association to CmnInterfaceData
	@OneToMany(mappedBy="cmnFileHdr")
	private List<CmnInterfaceData> cmnInterfaceData;

	public CmnFileHdr() {
	}

	public CmnFileHdrPK getId() {
		return this.id;
	}

	public void setId(CmnFileHdrPK id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getRecCount() {
		return this.recCount;
	}

	public void setRecCount(BigDecimal recCount) {
		this.recCount = recCount;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStts() {
		return this.stts;
	}

	public void setStts(String stts) {
		this.stts = stts;
	}

	public String getTrnType() {
		return this.trnType;
	}

	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}

	public CmnInterfaceMst getCmnInterfaceMst() {
		return this.cmnInterfaceMst;
	}

	public void setCmnInterfaceMst(CmnInterfaceMst cmnInterfaceMst) {
		this.cmnInterfaceMst = cmnInterfaceMst;
	}

	public List<CmnInterfaceData> getCmnInterfaceData() {
		return this.cmnInterfaceData;
	}

	public void setCmnInterfaceData(List<CmnInterfaceData> cmnInterfaceData) {
		this.cmnInterfaceData = cmnInterfaceData;
	}

	public CmnInterfaceData addCmnInterfaceData(CmnInterfaceData cmnInterfaceData) {
		getCmnInterfaceData().add(cmnInterfaceData);
		cmnInterfaceData.setCmnFileHdr(this);

		return cmnInterfaceData;
	}

	public CmnInterfaceData removeCmnInterfaceData(CmnInterfaceData cmnInterfaceData) {
		getCmnInterfaceData().remove(cmnInterfaceData);
		cmnInterfaceData.setCmnFileHdr(null);

		return cmnInterfaceData;
	}

	public String getControlFileName() {
		return controlFileName;
	}

	public void setControlFileName(String controlFileName) {
		this.controlFileName = controlFileName;
	}

}