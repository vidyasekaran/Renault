/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-I000002
 * Module          :CM Common
 * Process Outline :
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 *19-11-2015      z015887(RNTBCI)               Initial Version
 *
 */

package com.nissangroups.pd.i000002.output;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * This class is used to set and retrieve the output parameter value
 * and allow control over the values passed
 */
@Entity
public class I000002OutputBean implements Serializable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROWNUM")
	private long rowNum;

	/** Variable col1. */
	@Column(name = "PROD_REGION_CD")
	private String productionRegionCD;

	/** Variable col2. */
	@Column(name = "BUYER_CD")
	private String buyerCD;

	/** Variable col3. */
	@Column(name = "SPEC_DESTN_CD")
	private String specDestinationCD;

	/** Variable col4. */
	@Column(name = "BUYER_DESC")
	private String buyerDescription;

	/** Variable col5. */
	@Column(name = "OCF_REGION_CD")
	private String ocfRegionCD;

	/** Variable col6. */
	@Column(name = "OCF_BUYER_GRP_CD")
	private String ocfBuyerGroupCD;

	/** Variable col7. */
	@Column(name = "NSC_EIM_ODER_HRZN_FLAG")
	private String nscEimOrderHorizon;

	/** Variable col8. */
	@Column(name = "END_OF_PPLN_ACHV")
	private String endOfPiplineAchievementPoint;

	/** Variable col9. */
	@Column(name = "PRFX_SHPMNT_INSPCTN")
	private String preShipmentInspectionSymbol;

	/** Variable col10. */
	@Column(name = "BUYER_GRP_CD")
	private String buyerGroupCD;

	/** Variable col11. */
	@Column(name = "CRTD_BY")
	private String createUserID;

	/** Variable col12. */
	@Column(name = "CRTD_DT")
	private String createDateTime;

	/** Variable col13. */
	@Column(name = "UPDTD_BY")
	private String updateUserID;

	/** Variable col14. */
	@Column(name = "UPDTD_DT")
	private String updateDateTime;

	public long getRowNum() {
		return rowNum;
	}

	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	public String getProductionRegionCD() {
		return productionRegionCD;
	}

	public void setProductionRegionCD(String productionRegionCD) {
		this.productionRegionCD = productionRegionCD;
	}

	public String getBuyerCD() {
		return buyerCD;
	}

	public void setBuyerCD(String buyerCD) {
		this.buyerCD = buyerCD;
	}

	public String getSpecDestinationCD() {
		return specDestinationCD;
	}

	public void setSpecDestinationCD(String specDestinationCD) {
		this.specDestinationCD = specDestinationCD;
	}

	public String getBuyerDescription() {
		return buyerDescription;
	}

	public void setBuyerDescription(String buyerDescription) {
		this.buyerDescription = buyerDescription;
	}

	public String getOcfRegionCD() {
		return ocfRegionCD;
	}

	public void setOcfRegionCD(String ocfRegionCD) {
		this.ocfRegionCD = ocfRegionCD;
	}

	public String getOcfBuyerGroupCD() {
		return ocfBuyerGroupCD;
	}

	public void setOcfBuyerGroupCD(String ocfBuyerGroupCD) {
		this.ocfBuyerGroupCD = ocfBuyerGroupCD;
	}

	public String getNscEimOrderHorizon() {
		return nscEimOrderHorizon;
	}

	public void setNscEimOrderHorizon(String nscEimOrderHorizon) {
		this.nscEimOrderHorizon = nscEimOrderHorizon;
	}

	public String getEndOfPiplineAchievementPoint() {
		return endOfPiplineAchievementPoint;
	}

	public void setEndOfPiplineAchievementPoint(
			String endOfPiplineAchievementPoint) {
		this.endOfPiplineAchievementPoint = endOfPiplineAchievementPoint;
	}

	public String getPreShipmentInspectionSymbol() {
		return preShipmentInspectionSymbol;
	}

	public void setPreShipmentInspectionSymbol(
			String preShipmentInspectionSymbol) {
		this.preShipmentInspectionSymbol = preShipmentInspectionSymbol;
	}

	public String getBuyerGroupCD() {
		return buyerGroupCD;
	}

	public void setBuyerGroupCD(String buyerGroupCD) {
		this.buyerGroupCD = buyerGroupCD;
	}

	public String getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUpdateUserID() {
		return updateUserID;
	}

	public void setUpdateUserID(String updateUserID) {
		this.updateUserID = updateUserID;
	}

	public String getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}
