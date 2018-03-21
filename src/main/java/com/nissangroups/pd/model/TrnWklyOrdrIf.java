package com.nissangroups.pd.model;

import java.io.Serializable;

import javax.persistence.*;

import com.nissangroups.pd.util.CommonUtil;

import java.sql.Timestamp;


/**
 * The persistent class for the TRN_WKLY_ORDR_IF database table.
 * 
 */
@Entity
@Table(name="TRN_WKLY_ORDR_IF")
@NamedQuery(name="TrnWklyOrdrIf.findAll", query="SELECT t FROM TrnWklyOrdrIf t")
public class TrnWklyOrdrIf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrnWklyOrdrIfPK id;

	@Column(name="ADD_SPEC_CD")
	private String addSpecCd;

	@Column(name="APPLIED_MODEL_CD")
	private String appliedModelCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="CAR_SRS")
	private String carSrs;

    @Column(name="CRTD_BY",updatable=false)
	private String crtdBy;

	@Column(name="CRTD_DT",updatable=false)
	private Timestamp crtdDt;

	@Column(name="DUE_DATE_FROM")
	private String dueDateFrom;

	@Column(name="DUE_DATE_TO")
	private String dueDateTo;

	@Column(name="EXTERIOR_COLOR_CD")
	private String exteriorColorCd;

	@Column(name="INTERIOR_COLOR_CD")
	private String interiorColorCd;

	@Column(name="LINE_CLASS")
	private String lineClass;

	@Column(name="LOCAL_PROD_ORDER_NO")
	private String localProdOrderNo;

	@Column(name="OFFLN_PLAN_DATE")
	private String offlnPlanDate;

	@Column(name="ORDER_QTY")
	private long orderQty;

	@Column(name="ORDER_TAKE_BASE_PERIOD")
	private String orderTakeBasePeriod;

	@Column(name="ORDER_TAKE_BASE_PERIOD_TYPE")
	private String orderTakeBasePeriodType;

	@Column(name="PACK_CD")
	private String packCd;

	@Column(name="POR_CD")
	private String porCd;

	@Column(name="POT_CD")
	private String potCd;

	@Column(name="PROD_ORDER_NO")
	private String prodOrderNo;

	@Column(name="PROD_PLANT_CD")
	private String prodPlantCd;

	@Column(name="PRODUCTION_ORDER_STAGE_CD")
	private String productionOrderStageCd;

	@Column(name="PRODUCTION_PERIOD")
	private String productionPeriod;

	@Column(name="PRODUCTION_PERIOD_TYPE")
	private String productionPeriodType;

	@Column(name="SPEC_DESTINATION_CD")
	private String specDestinationCd;

	@Column(name="UPDTD_BY")
	private String updtdBy;

	@Column(name="UPDTD_DT")
	private Timestamp updtdDt;

	public TrnWklyOrdrIf() {
	}

	public TrnWklyOrdrIfPK getId() {
		return this.id;
	}

	public void setId(TrnWklyOrdrIfPK id) {
		this.id = id;
	}

	public String getAddSpecCd() {
		return this.addSpecCd;
	}

	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	public String getAppliedModelCd() {
		return this.appliedModelCd;
	}

	public void setAppliedModelCd(String appliedModelCd) {
		this.appliedModelCd = appliedModelCd;
	}

	public String getBuyerCd() {
		return this.buyerCd;
	}

	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
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

	public String getDueDateFrom() {
		return this.dueDateFrom;
	}

	public void setDueDateFrom(String dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	public String getDueDateTo() {
		return this.dueDateTo;
	}

	public void setDueDateTo(String dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	public String getExteriorColorCd() {
		return this.exteriorColorCd;
	}

	public void setExteriorColorCd(String exteriorColorCd) {
		this.exteriorColorCd = exteriorColorCd;
	}

	public String getInteriorColorCd() {
		return this.interiorColorCd;
	}

	public void setInteriorColorCd(String interiorColorCd) {
		this.interiorColorCd = interiorColorCd;
	}

	public String getLineClass() {
		return this.lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getLocalProdOrderNo() {
		return this.localProdOrderNo;
	}

	public void setLocalProdOrderNo(String localProdOrderNo) {
		this.localProdOrderNo = localProdOrderNo;
	}

	public String getOfflnPlanDate() {
		return this.offlnPlanDate;
	}

	public void setOfflnPlanDate(String offlnPlanDate) {
		this.offlnPlanDate = offlnPlanDate;
	}

	public long getOrderQty() {
		return this.orderQty;
	}

	public void setOrderQty(long orderQty) {
		this.orderQty = orderQty;
	}

	public String getOrderTakeBasePeriod() {
		return this.orderTakeBasePeriod;
	}

	public void setOrderTakeBasePeriod(String orderTakeBasePeriod) {
		this.orderTakeBasePeriod = orderTakeBasePeriod;
	}

	public String getOrderTakeBasePeriodType() {
		return this.orderTakeBasePeriodType;
	}

	public void setOrderTakeBasePeriodType(String orderTakeBasePeriodType) {
		this.orderTakeBasePeriodType = orderTakeBasePeriodType;
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

	public String getProdOrderNo() {
		return this.prodOrderNo;
	}

	public void setProdOrderNo(String prodOrderNo) {
		this.prodOrderNo = prodOrderNo;
	}

	public String getProdPlantCd() {
		return this.prodPlantCd;
	}

	public void setProdPlantCd(String prodPlantCd) {
		this.prodPlantCd = prodPlantCd;
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

	public String getSpecDestinationCd() {
		return this.specDestinationCd;
	}

	public void setSpecDestinationCd(String specDestinationCd) {
		this.specDestinationCd = specDestinationCd;
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