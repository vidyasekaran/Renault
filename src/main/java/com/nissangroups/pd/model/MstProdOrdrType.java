package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MST_PROD_ORDR_TYPE database table.
 * 
 */
@Entity
@Table(name="MST_PROD_ORDR_TYPE")
@NamedQuery(name="MstProdOrdrType.findAll", query="SELECT m FROM MstProdOrdrType m")
public class MstProdOrdrType implements Serializable {
	

	@EmbeddedId
	private MstProdOrdrTypePK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="POT_DESC")
	private String potDesc;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	//bi-directional many-to-one association to TrnMnthlyOrdr
	@OneToMany(mappedBy="mstProdOrdrType")
	private List<TrnMnthlyOrdr> trnMnthlyOrdrs;

	public MstProdOrdrType() {
	}

	public MstProdOrdrTypePK getId() {
		return this.id;
	}

	public void setId(MstProdOrdrTypePK id) {
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

	public String getPotDesc() {
		return this.potDesc;
	}

	public void setPotDesc(String potDesc) {
		this.potDesc = potDesc;
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

	public List<TrnMnthlyOrdr> getTrnMnthlyOrdrs() {
		return this.trnMnthlyOrdrs;
	}

	public void setTrnMnthlyOrdrs(List<TrnMnthlyOrdr> trnMnthlyOrdrs) {
		this.trnMnthlyOrdrs = trnMnthlyOrdrs;
	}

	public TrnMnthlyOrdr addTrnMnthlyOrdr(TrnMnthlyOrdr trnMnthlyOrdr) {
		getTrnMnthlyOrdrs().add(trnMnthlyOrdr);
		trnMnthlyOrdr.setMstProdOrdrType(this);

		return trnMnthlyOrdr;
	}

	public TrnMnthlyOrdr removeTrnMnthlyOrdr(TrnMnthlyOrdr trnMnthlyOrdr) {
		getTrnMnthlyOrdrs().remove(trnMnthlyOrdr);
		trnMnthlyOrdr.setMstProdOrdrType(null);

		return trnMnthlyOrdr;
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