package com.nissangroups.pd.b000040.util;

import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nissangroups.pd.b000040.bean.B000040PrdOrdrDetailReport;
import com.nissangroups.pd.b000040.bean.B000040ServiceErrorReport;
import com.nissangroups.pd.b000040.bean.B000040WklyOcfBreachReport;
import com.nissangroups.pd.b000040.bean.B000040WklyOrderSpecErrorReport;

/**
 * This class provides utility method and attributes to be used by B000040 
 * @author z015847
 *
 */
public class B000040UtilityService 
{

	/** Stores entity manager */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	//This Collection is used to hold list of B000040ServiceErrorReport to generate the service error report
	private List<B000040ServiceErrorReport> svcErrorReportList = new ArrayList<B000040ServiceErrorReport>();
	
	//This Collection is used to hold list of B000040WklyOrderSpecErrorReport to generate Weekly Order Spec Error Report
	private List<B000040WklyOrderSpecErrorReport> wklyOrdrSpecErrorReportList = new ArrayList<B000040WklyOrderSpecErrorReport>();
	
	//This Collection is used to hold list of  B000040PrdOrdrDetailReport to generate Production Order Detail Report
	private List<B000040PrdOrdrDetailReport> prdOrdrDetailReportList = new ArrayList<B000040PrdOrdrDetailReport>();
	
	//This Collection is used to hold list of  B000040WklyOcfBreachReport to generate Weekly Ocf Breach Report
	private List<B000040WklyOcfBreachReport> wklyOcfBreachReportList = new ArrayList<B000040WklyOcfBreachReport>();
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public List<B000040ServiceErrorReport> getSvcErrorReportList() {
		return svcErrorReportList;
	}
	public void setSvcErrorReportList(
			List<B000040ServiceErrorReport> svcErrorReportList) {
		this.svcErrorReportList = svcErrorReportList;
	}
	public List<B000040WklyOrderSpecErrorReport> getWklyOrdrSpecErrorReportList() {
		return wklyOrdrSpecErrorReportList;
	}
	public void setWklyOrdrSpecErrorReportList(
			List<B000040WklyOrderSpecErrorReport> wklyOrdrSpecErrorReportList) {
		this.wklyOrdrSpecErrorReportList = wklyOrdrSpecErrorReportList;
	}
	public List<B000040PrdOrdrDetailReport> getPrdOrdrDetailReportList() {
		return prdOrdrDetailReportList;
	}
	public void setPrdOrdrDetailReportList(
			List<B000040PrdOrdrDetailReport> prdOrdrDetailReportList) {
		this.prdOrdrDetailReportList = prdOrdrDetailReportList;
	}
	public List<B000040WklyOcfBreachReport> getWklyOcfBreachReportList() {
		return wklyOcfBreachReportList;
	}
	public void setWklyOcfBreachReportList(
			List<B000040WklyOcfBreachReport> wklyOcfBreachReportList) {
		this.wklyOcfBreachReportList = wklyOcfBreachReportList;
	}
	
	
}
