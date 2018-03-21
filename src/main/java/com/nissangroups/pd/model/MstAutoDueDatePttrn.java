package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_AUTO_DUE_DATE_PTTRN database table.
 * 
 */
@Entity
@Table(name="MST_AUTO_DUE_DATE_PTTRN")
@NamedQuery(name="MstAutoDueDatePttrn.findAll", query="SELECT m FROM MstAutoDueDatePttrn m")
public class MstAutoDueDatePttrn implements Serializable {
	

	@EmbeddedId
	private MstAutoDueDatePttrnPK id;

	@Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DUE_DATE")
	private String dueDate;

	@Column(name="PTTRN_DESC")
	private String pttrnDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to MstAutoDueDatePrmtr
	@OneToMany(mappedBy="mstAutoDueDatePttrn")
	private List<MstAutoDueDatePrmtr> mstAutoDueDatePrmtrs;

	public MstAutoDueDatePttrn() {
	}

	public MstAutoDueDatePttrnPK getId() {
		return this.id;
	}

	public void setId(MstAutoDueDatePttrnPK id) {
		this.id = id;
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

	public String getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getPttrnDesc() {
		return this.pttrnDesc;
	}

	public void setPttrnDesc(String pttrnDesc) {
		this.pttrnDesc = pttrnDesc;
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

	public List<MstAutoDueDatePrmtr> getMstAutoDueDatePrmtrs() {
		return this.mstAutoDueDatePrmtrs;
	}

	public void setMstAutoDueDatePrmtrs(List<MstAutoDueDatePrmtr> mstAutoDueDatePrmtrs) {
		this.mstAutoDueDatePrmtrs = mstAutoDueDatePrmtrs;
	}

	public MstAutoDueDatePrmtr addMstAutoDueDatePrmtr(MstAutoDueDatePrmtr mstAutoDueDatePrmtr) {
		getMstAutoDueDatePrmtrs().add(mstAutoDueDatePrmtr);
		mstAutoDueDatePrmtr.setMstAutoDueDatePttrn(this);

		return mstAutoDueDatePrmtr;
	}

	public MstAutoDueDatePrmtr removeMstAutoDueDatePrmtr(MstAutoDueDatePrmtr mstAutoDueDatePrmtr) {
		getMstAutoDueDatePrmtrs().remove(mstAutoDueDatePrmtr);
		mstAutoDueDatePrmtr.setMstAutoDueDatePttrn(null);

		return mstAutoDueDatePrmtr;
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