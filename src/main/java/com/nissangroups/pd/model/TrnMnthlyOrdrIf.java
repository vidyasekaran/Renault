package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;


/**
 * The persistent class for the TRN_MNTHLY_ORDR_IF database table.
 * 
 */
@Entity
@Table(name="TRN_MNTHLY_ORDR_IF")
@NamedQuery(name="TrnMnthlyOrdrIf.findAll", query="SELECT t FROM TrnMnthlyOrdrIf t")
public class TrnMnthlyOrdrIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnMnthlyOrdrIfPK id;

	@Column(name="ADD_SPEC_CD")
	private String addSpecCd;

	@Column(name="APPLD_MDL_CD")
	private String appldMdlCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="BUYER_GRP_CD")
	private String buyerGrpCd;

	@Column(name="CAR_SRS")
	private String carSrs;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DRAGON_INDVDL_ORDR_NO")
	private String dragonIndvdlOrdrNo;

	@Column(name="DUE_DATE_FRM")
	private String dueDateFrm;

	@Column(name="DUE_DATE_TO")
	private String dueDateTo;

	@Column(name="ERR_CD")
	private String errCd;

	@Column(name="EXT_CLR_CD")
	private String extClrCd;

	@Column(name="FRZN_TYPE_CD")
	private String frznTypeCd;

	@Column(name="INT_CLR_CD")
	private String intClrCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="MS_QTY")
	private BigDecimal msQty;

	@Column(name="OEI_BUYER_ID")
	private String oeiBuyerId;

	@Column(name="OFFLINE_PLAN_DT")
	private String offlinePlanDt;

	@Column(name="ORDR_QTY")
	private BigDecimal ordrQty;

	@Column(name="ORDRTK_BASE_PRD")
	private String ordrtkBasePrd;

	@Column(name="ORDRTK_BASE_PRD_TYPE")
	private String ordrtkBasePrdType;

	@Column(name="OSEI_ID")
	private String oseiId;

	@Column(name="PACK_CD")
	private String packCd;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_ORDR_NO")
	private String prodOrdrNo;

	@Column(name="PROD_PLNT_CD")
	private String prodPlntCd;

	@Column(name="PRODUCTION_ORDER_STAGE_CD")
	private String productionOrderStageCd;

	@Column(name="PRODUCTION_PERIOD")
	private String productionPeriod;

	@Column(name="PRODUCTION_PERIOD_TYPE")
	private String productionPeriodType;

	@Column(name="SPEC_DESTN_CD")
	private String specDestnCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnMnthlyOrdrIf() {
	}

	public TrnMnthlyOrdrIfPK getId() {
		return this.id;
	}

	public void setId(TrnMnthlyOrdrIfPK id) {
		this.id = id;
	}
	
	@PrePersist
	public void creationTime() {
		Date date = new Date();
		crtdDt = new Timestamp(date.getTime());
	}

	@PreUpdate
	public void updationTime() {
		Date date = new Date();
		updtdDt = new Timestamp(date.getTime());
	}

	public String getAddSpecCd() {
		return this.addSpecCd;
	}

	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	public String getAppldMdlCd() {
		return this.appldMdlCd;
	}

	public void setAppldMdlCd(String appldMdlCd) {
		this.appldMdlCd = appldMdlCd;
	}

	public String getBuyerCd() {
		return this.buyerCd;
	}

	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	public String getBuyerGrpCd() {
		return this.buyerGrpCd;
	}

	public void setBuyerGrpCd(String buyerGrpCd) {
		this.buyerGrpCd = buyerGrpCd;
	}

	public String getCarSrs() {
		return this.carSrs;
	}

	public void setCarSrs(String carSrs) {
		this.carSrs = carSrs;
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

	public String getDragonIndvdlOrdrNo() {
		return this.dragonIndvdlOrdrNo;
	}

	public void setDragonIndvdlOrdrNo(String dragonIndvdlOrdrNo) {
		this.dragonIndvdlOrdrNo = dragonIndvdlOrdrNo;
	}

	public String getDueDateFrm() {
		return this.dueDateFrm;
	}

	public void setDueDateFrm(String dueDateFrm) {
		this.dueDateFrm = dueDateFrm;
	}

	public String getDueDateTo() {
		return this.dueDateTo;
	}

	public void setDueDateTo(String dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	public String getErrCd() {
		return this.errCd;
	}

	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}

	public String getExtClrCd() {
		return this.extClrCd;
	}

	public void setExtClrCd(String extClrCd) {
		this.extClrCd = extClrCd;
	}

	public String getFrznTypeCd() {
		return this.frznTypeCd;
	}

	public void setFrznTypeCd(String frznTypeCd) {
		this.frznTypeCd = frznTypeCd;
	}

	public String getIntClrCd() {
		return this.intClrCd;
	}

	public void setIntClrCd(String intClrCd) {
		this.intClrCd = intClrCd;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public BigDecimal getMsQty() {
		return this.msQty;
	}

	public void setMsQty(BigDecimal msQty) {
		this.msQty = msQty;
	}

	public String getOeiBuyerId() {
		return this.oeiBuyerId;
	}

	public void setOeiBuyerId(String oeiBuyerId) {
		this.oeiBuyerId = oeiBuyerId;
	}

	public String getOfflinePlanDt() {
		return this.offlinePlanDt;
	}

	public void setOfflinePlanDt(String offlinePlanDt) {
		this.offlinePlanDt = offlinePlanDt;
	}

	public BigDecimal getOrdrQty() {
		return this.ordrQty;
	}

	public void setOrdrQty(BigDecimal ordrQty) {
		this.ordrQty = ordrQty;
	}

	public String getOrdrtkBasePrd() {
		return this.ordrtkBasePrd;
	}

	public void setOrdrtkBasePrd(String ordrtkBasePrd) {
		this.ordrtkBasePrd = ordrtkBasePrd;
	}

	public String getOrdrtkBasePrdType() {
		return this.ordrtkBasePrdType;
	}

	public void setOrdrtkBasePrdType(String ordrtkBasePrdType) {
		this.ordrtkBasePrdType = ordrtkBasePrdType;
	}

	public String getOseiId() {
		return this.oseiId;
	}

	public void setOseiId(String oseiId) {
		this.oseiId = oseiId;
	}

	public String getPackCd() {
		return this.packCd;
	}

	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}

	public String getPorCd() {
		return this.porCd;
	}

	public void setPorCd(String porCd) {
		this.porCd = porCd;
	}

	public String getPotCd() {
		return this.potCd;
	}

	public void setPotCd(String potCd) {
		this.potCd = potCd;
	}

	public String getProdOrdrNo() {
		return this.prodOrdrNo;
	}

	public void setProdOrdrNo(String prodOrdrNo) {
		this.prodOrdrNo = prodOrdrNo;
	}

	public String getProdPlntCd() {
		return this.prodPlntCd;
	}

	public void setProdPlntCd(String prodPlntCd) {
		this.prodPlntCd = prodPlntCd;
	}

	public String getProductionOrderStageCd() {
		return this.productionOrderStageCd;
	}

	public void setProductionOrderStageCd(String productionOrderStageCd) {
		this.productionOrderStageCd = productionOrderStageCd;
	}

	public String getProductionPeriod() {
		return this.productionPeriod;
	}

	public void setProductionPeriod(String productionPeriod) {
		this.productionPeriod = productionPeriod;
	}

	public String getProductionPeriodType() {
		return this.productionPeriodType;
	}

	public void setProductionPeriodType(String productionPeriodType) {
		this.productionPeriodType = productionPeriodType;
	}

	public String getSpecDestnCd() {
		return this.specDestnCd;
	}

	public void setSpecDestnCd(String specDestnCd) {
		this.specDestnCd = specDestnCd;
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
	}