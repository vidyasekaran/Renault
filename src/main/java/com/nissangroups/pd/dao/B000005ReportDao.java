/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000005
 * Module          :Create Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 08-Jul-2015  	  z010343(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;

/**
 * The Class B000005ReportDao.
 *
 * @author z010343
 */
@Scope("job")
public class B000005ReportDao {

	/** Variable por. */
	private String por;

	/** Variable car series. */
	private String carSeries;

	/** Variable buyer cd. */
	private String buyerCd;

	/** Variable ei mdl cd. */
	private String eiMdlCd;

	/** Variable ei clr cd. */
	private String eiClrCd;

	/** Variable adpt date. */
	private String adptDate;

	/** Variable ablsh date. */
	private String ablshDate;

	/** Variable add spec cd. */
	private String addSpecCd;

	/** Variable spec destination cd. */
	private String specDestinationCd;

	/** Variable ocf region cd. */
	private String ocfRegionCd;

	/** Variable ocf buyer grp cd. */
	private String ocfBuyerGrpCd;

	/** Variable from otbm. */
	private String fromOtbm;

	/** Variable to otbm. */
	private String toOtbm;

	/** Variable error type. */
	private String errorType;

	/** Variable cmmnts. */
	private String cmmnts;

	/** Variable report list. */
	private List<B000005ReportDao> reportList = Collections
			.synchronizedList(new ArrayList<B000005ReportDao>());

	/**
	 * Gets the por.
	 *
	 * @return the por
	 */
	public String getPor() {
		return por;
	}

	/**
	 * Sets the por.
	 *
	 * @param por
	 *            the new por
	 */
	public void setPor(String por) {
		this.por = por;
	}

	/**
	 * Gets the car series.
	 *
	 * @return the car series
	 */
	public String getCarSeries() {
		return carSeries;
	}

	/**
	 * Sets the car series.
	 *
	 * @param carSeries
	 *            the new car series
	 */
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	/**
	 * Gets the buyer cd.
	 *
	 * @return the buyer cd
	 */
	public String getBuyerCd() {
		return buyerCd;
	}

	/**
	 * Sets the buyer cd.
	 *
	 * @param buyerCd
	 *            the new buyer cd
	 */
	public void setBuyerCd(String buyerCd) {
		this.buyerCd = buyerCd;
	}

	/**
	 * Gets the ei mdl cd.
	 *
	 * @return the ei mdl cd
	 */
	public String getEiMdlCd() {
		return eiMdlCd;
	}

	/**
	 * Sets the ei mdl cd.
	 *
	 * @param eiMdlCd
	 *            the new ei mdl cd
	 */
	public void setEiMdlCd(String eiMdlCd) {
		this.eiMdlCd = eiMdlCd;
	}

	/**
	 * Gets the ei clr cd.
	 *
	 * @return the ei clr cd
	 */
	public String getEiClrCd() {
		return eiClrCd;
	}

	/**
	 * Sets the ei clr cd.
	 *
	 * @param eiClrCd
	 *            the new ei clr cd
	 */
	public void setEiClrCd(String eiClrCd) {
		this.eiClrCd = eiClrCd;
	}

	/**
	 * Gets the adpt date.
	 *
	 * @return the adpt date
	 */
	public String getAdptDate() {
		return adptDate;
	}

	/**
	 * Sets the adpt date.
	 *
	 * @param adptDate
	 *            the new adpt date
	 */
	public void setAdptDate(String adptDate) {
		this.adptDate = adptDate;
	}

	/**
	 * Gets the ablsh date.
	 *
	 * @return the ablsh date
	 */
	public String getAblshDate() {
		return ablshDate;
	}

	/**
	 * Sets the ablsh date.
	 *
	 * @param ablshDate
	 *            the new ablsh date
	 */
	public void setAblshDate(String ablshDate) {
		this.ablshDate = ablshDate;
	}

	/**
	 * Gets the adds the spec cd.
	 *
	 * @return the adds the spec cd
	 */
	public String getAddSpecCd() {
		return addSpecCd;
	}

	/**
	 * Sets the adds the spec cd.
	 *
	 * @param addSpecCd
	 *            the new adds the spec cd
	 */
	public void setAddSpecCd(String addSpecCd) {
		this.addSpecCd = addSpecCd;
	}

	/**
	 * Gets the spec destination cd.
	 *
	 * @return the spec destination cd
	 */
	public String getSpecDestinationCd() {
		return specDestinationCd;
	}

	/**
	 * Sets the spec destination cd.
	 *
	 * @param specDestinationCd
	 *            the new spec destination cd
	 */
	public void setSpecDestinationCd(String specDestinationCd) {
		this.specDestinationCd = specDestinationCd;
	}

	/**
	 * Gets the ocf region cd.
	 *
	 * @return the ocf region cd
	 */
	public String getOcfRegionCd() {
		return ocfRegionCd;
	}

	/**
	 * Sets the ocf region cd.
	 *
	 * @param ocfRegionCd
	 *            the new ocf region cd
	 */
	public void setOcfRegionCd(String ocfRegionCd) {
		this.ocfRegionCd = ocfRegionCd;
	}

	/**
	 * Gets the ocf buyer grp cd.
	 *
	 * @return the ocf buyer grp cd
	 */
	public String getOcfBuyerGrpCd() {
		return ocfBuyerGrpCd;
	}

	/**
	 * Sets the ocf buyer grp cd.
	 *
	 * @param ocfBuyerGrpCd
	 *            the new ocf buyer grp cd
	 */
	public void setOcfBuyerGrpCd(String ocfBuyerGrpCd) {
		this.ocfBuyerGrpCd = ocfBuyerGrpCd;
	}

	/**
	 * Gets the cmmnts.
	 *
	 * @return the cmmnts
	 */
	public String getCmmnts() {
		return cmmnts;
	}

	/**
	 * Sets the cmmnts.
	 *
	 * @param cmmnts
	 *            the new cmmnts
	 */
	public void setCmmnts(String cmmnts) {
		this.cmmnts = cmmnts;
	}

	/**
	 * Gets the report list.
	 *
	 * @return the report list
	 */
	public List<B000005ReportDao> getReportList() {
		return reportList;
	}

	/**
	 * Sets the report list.
	 *
	 * @param reportList
	 *            the new report list
	 */
	public void setReportList(List<B000005ReportDao> reportList) {
		this.reportList = reportList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof B000005ReportDao)) {
			return false;
		}
		B000005ReportDao castOther = (B000005ReportDao) other;
		return this.por.equals(castOther.por)
				&& this.carSeries.equals(castOther.carSeries)
				&& this.buyerCd.equals(castOther.buyerCd)
				&& this.eiMdlCd.equals(castOther.eiMdlCd)
				&& this.eiClrCd.equals(castOther.eiClrCd)
				&& this.adptDate.equals(castOther.adptDate)
				&& this.ablshDate.equals(castOther.ablshDate)
				&& this.addSpecCd.equals(castOther.addSpecCd)
				&& this.specDestinationCd.equals(castOther.specDestinationCd)
				&& this.ocfRegionCd.equals(castOther.ocfRegionCd)
				&& this.ocfBuyerGrpCd.equals(castOther.ocfBuyerGrpCd)
				&& this.errorType.equals(castOther.errorType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.por.hashCode();
		hash = hash * prime + this.buyerCd.hashCode();
		hash = hash * prime + this.carSeries.hashCode();
		hash = hash * prime + this.eiMdlCd.hashCode();
		hash = hash * prime + this.eiClrCd.hashCode();
		hash = hash * prime + this.adptDate.hashCode();
		hash = hash * prime + this.ablshDate.hashCode();
		hash = hash * prime + this.addSpecCd.hashCode();
		hash = hash * prime + this.specDestinationCd.hashCode();
		hash = hash * prime + this.ocfRegionCd.hashCode();
		hash = hash * prime + this.ocfBuyerGrpCd.hashCode();
		hash = hash * prime + this.errorType.hashCode();

		return hash;
	}

	/**
	 * Gets the error type.
	 *
	 * @return the error type
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * Sets the error type.
	 *
	 * @param errorType
	 *            the new error type
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * Gets the from otbm.
	 *
	 * @return the from otbm
	 */
	public String getFromOtbm() {
		return fromOtbm;
	}

	/**
	 * Sets the from otbm.
	 *
	 * @param fromOtbm
	 *            the new from otbm
	 */
	public void setFromOtbm(String fromOtbm) {
		this.fromOtbm = fromOtbm;
	}

	/**
	 * Gets the to otbm.
	 *
	 * @return the to otbm
	 */
	public String getToOtbm() {
		return toOtbm;
	}

	/**
	 * Sets the to otbm.
	 *
	 * @param toOtbm
	 *            the new to otbm
	 */
	public void setToOtbm(String toOtbm) {
		this.toOtbm = toOtbm;
	}

}
