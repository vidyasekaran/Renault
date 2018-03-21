package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import com.nissangroups.pd.util.CommonUtil;




/**
 * The persistent class for the MST_MNTHLY_ORDR_TAKE_LCK database table.
 * 
 */
@Entity
@Table(name="MST_MNTHLY_ORDR_TAKE_LCK")
@NamedQuery(name="MstMnthlyOrdrTakeLck.findAll", query="SELECT m FROM MstMnthlyOrdrTakeLck m")
public class MstMnthlyOrdrTakeLck implements Serializable {
	

	@EmbeddedId
	private MstMnthlyOrdrTakeLckPK id;

	@Column(name="CRTD_BY",updatable=false) // Updated for Redmine Defect # 2343
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)// Updated for Redmine Defect # 2343
	private Timestamp crtdDt;

	@Column(name="EXPTR_LCK_FLAG")
	private String exptrLckFlag;

	@Column(name="FRZN_LCK_FLAG")
	private String frznLckFlag;

	@Column(name="NSC_LCK_FLAG")
	private String nscLckFlag;

	@Column(name="ORDR_TRANS_FLAG")
	private String ordrTransFlag;

	@Column(name="RHQ_LCK_FLAG")
	private String rhqLckFlag;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public MstMnthlyOrdrTakeLck() {
	}

	public MstMnthlyOrdrTakeLckPK getId() {
		return this.id;
	}

	public void setId(MstMnthlyOrdrTakeLckPK id) {
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

	public String getExptrLckFlag() {
		return this.exptrLckFlag;
	}

	public void setExptrLckFlag(String exptrLckFlag) {
		this.exptrLckFlag = exptrLckFlag;
	}

	public String getFrznLckFlag() {
		return this.frznLckFlag;
	}

	public void setFrznLckFlag(String frznLckFlag) {
		this.frznLckFlag = frznLckFlag;
	}

	public String getNscLckFlag() {
		return this.nscLckFlag;
	}

	public void setNscLckFlag(String nscLckFlag) {
		this.nscLckFlag = nscLckFlag;
	}

	public String getOrdrTransFlag() {
		return this.ordrTransFlag;
	}

	public void setOrdrTransFlag(String ordrTransFlag) {
		this.ordrTransFlag = ordrTransFlag;
	}

	public String getRhqLckFlag() {
		return this.rhqLckFlag;
	}

	public void setRhqLckFlag(String rhqLckFlag) {
		this.rhqLckFlag = rhqLckFlag;
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
	
	// Updated for Redmine Defect # 2343
	
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