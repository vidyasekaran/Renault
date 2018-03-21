package com.nissangroups.pd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MONTHLY_ORDER_INTERFACE_TRN database table.
 * 
 */
@Entity
@Table(name="MONTHLY_ORDER_INTERFACE_TRN")
@NamedQuery(name="MonthlyOrderInterfaceTrn.findAll", query="SELECT m FROM MonthlyOrderInterfaceTrn m")
public class MonthlyOrderInterfaceTrn implements Serializable {
	

	@EmbeddedId
	private MonthlyOrderInterfaceTrnPK id;

	@Column(name="ADD_SPEC_CD")
	private String addSpecCd;

	@Column(name="APPLIED_MODEL_CD")
	private String appliedModelCd;

	@Column(name="BUYER_CD")
	private String buyerCd;

	@Column(name="CAR_SERIES")
	private String carSeries;

	@Column(name="CREATE_DATE_TIME")
	private String createDateTime;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="DUE_DATE_FROM")
	private String dueDateFrom;

	@Column(name="DUE_DATE_TO")
	private String dueDateTo;

	@Column(name="EXTERIOR_COLOR_CD")
	private String exteriorColorCd;

	@Column(name="INTERIOR_COLOR_CD")
	private String interiorColorCd;

	@Column(name="ORDER_QTY")
	private BigDecimal orderQty;

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

	@Column(name="PRODUCTION_ORDER_NO")
	private String productionOrderNo;

	@Column(name="PRODUCTION_ORDER_STAGE_CD")
	private String productionOrderStageCd;

	@Column(name="PRODUCTION_PERIOD")
	private String productionPeriod;

	@Column(name="PRODUCTION_PERIOD_TYPE")
	private String productionPeriodType;

	@Column(name="SPEC_DESTINATION_CD")
	private String specDestinationCd;

	@Column(name="UPDATE_DATE_TIME")
	private String updateDateTime;

	@Column(name="UPDATE_USER_ID")
	private String updateUserId;

	public MonthlyOrderInterfaceTrn() {
	}

	public MonthlyOrderInterfaceTrnPK getId() {
		return this.id;
	}

	public void setId(MonthlyOrderInterfaceTrnPK id) {
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

	public String getCarSeries() {
		return this.carSeries;
	}

	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	public String getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
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

	public BigDecimal getOrderQty() {
		return this.orderQty;
	}

	public void setOrderQty(BigDecimal orderQty) {
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

	public String getProductionOrderNo() {
		return this.productionOrderNo;
	}

	public void setProductionOrderNo(String productionOrderNo) {
		this.productionOrderNo = productionOrderNo;
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

	public String getUpdateDateTime() {
		return this.updateDateTime;
	}

	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}