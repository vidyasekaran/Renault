package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_DAILY_OCF_LMT database table.
 * 
 */
@Entity
@Table(name="TRN_DAILY_OCF_LMT")
@NamedQuery(name="TrnDailyOcfLmt.findAll", query="SELECT t FROM TrnDailyOcfLmt t")
public class TrnDailyOcfLmt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnDailyOcfLmtPK id;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="FEAT_CD")
	private String featCd;

	@Column(name="LAST_WK_SYMBL")
	private String lastWkSymbl;

	@Column(name="OCF_DATE")
	private String ocfDate;

	@Column(name="OCF_FRME_CD")
	private String ocfFrmeCd;

	@Column(name="OCF_LMT_QTY")
	private BigDecimal ocfLmtQty;

	@Column(name="ORDR_BASE_BASE_WK_NO")
	private String ordrBaseBaseWkNo;

	@Column(name="ORDR_TAKE_BASE_MNTH")
	private String ordrTakeBaseMnth;

	@Column(name="PROCESS_STTS_CD")
	private String processSttsCd;

	@Column(name="STND_QTY")
	private BigDecimal stndQty;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnDailyOcfLmt() {
	}

	public TrnDailyOcfLmtPK getId() {
		return this.id;
	}

	public void setId(TrnDailyOcfLmtPK id) {
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

	public String getFeatCd() {
		return this.featCd;
	}

	public void setFeatCd(String featCd) {
		this.featCd = featCd;
	}

	public String getLastWkSymbl() {
		return this.lastWkSymbl;
	}

	public void setLastWkSymbl(String lastWkSymbl) {
		this.lastWkSymbl = lastWkSymbl;
	}

	public String getOcfDate() {
		return this.ocfDate;
	}

	public void setOcfDate(String ocfDate) {
		this.ocfDate = ocfDate;
	}

	public String getOcfFrmeCd() {
		return this.ocfFrmeCd;
	}

	public void setOcfFrmeCd(String ocfFrmeCd) {
		this.ocfFrmeCd = ocfFrmeCd;
	}

	public BigDecimal getOcfLmtQty() {
		return this.ocfLmtQty;
	}

	public void setOcfLmtQty(BigDecimal ocfLmtQty) {
		this.ocfLmtQty = ocfLmtQty;
	}

	public String getOrdrBaseBaseWkNo() {
		return this.ordrBaseBaseWkNo;
	}

	public void setOrdrBaseBaseWkNo(String ordrBaseBaseWkNo) {
		this.ordrBaseBaseWkNo = ordrBaseBaseWkNo;
	}

	public String getOrdrTakeBaseMnth() {
		return this.ordrTakeBaseMnth;
	}

	public void setOrdrTakeBaseMnth(String ordrTakeBaseMnth) {
		this.ordrTakeBaseMnth = ordrTakeBaseMnth;
	}

	public String getProcessSttsCd() {
		return this.processSttsCd;
	}

	public void setProcessSttsCd(String processSttsCd) {
		this.processSttsCd = processSttsCd;
	}

	public BigDecimal getStndQty() {
		return this.stndQty;
	}

	public void setStndQty(BigDecimal stndQty) {
		this.stndQty = stndQty;
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