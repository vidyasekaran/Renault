package com.nissangroups.pd.dao;

import java.util.ArrayList;
import java.util.List;

public class OrderQtyBean {

	public OrderQtyBean() {

	}

	private int prdDyNo;
	private int prdWeekNo;
	private int weekNoOfYear;
	private String mxQty;
	private String stndQty;
	private String offlnDt;

	private List<OrderQtyBean> OrderQtyBeanLst = new ArrayList<>();


	public int getPrdDyNo() {
		return prdDyNo;
	}

	public void setPrdDyNo(int prdDyNo) {
		this.prdDyNo = prdDyNo;
	}


	public int getPrdWeekNo() {
		return prdWeekNo;
	}

	public void setPrdWeekNo(int prdWeekNo) {
		this.prdWeekNo = prdWeekNo;
	}

	public String getMxQty() {
		return mxQty;
	}

	public void setMxQty(String mxQty) {
		this.mxQty = mxQty;
	}

	public String getStndQty() {
		return stndQty;
	}

	public void setStndQty(String stndQty) {
		this.stndQty = stndQty;
	}

	public List<OrderQtyBean> getOrderQtyBeanLst() {
		return OrderQtyBeanLst;
	}

	public void setOrderQtyBeanLst(List<OrderQtyBean> orderQtyBeanLst) {
		OrderQtyBeanLst = orderQtyBeanLst;
	}

	public String getOfflnDt() {
		return offlnDt;
	}

	public void setOfflnDt(String offlnDt) {
		this.offlnDt = offlnDt;
	}

	public int getWeekNoOfYear() {
		return weekNoOfYear;
	}

	public void setWeekNoOfYear(int weekNoOfYear) {
		this.weekNoOfYear = weekNoOfYear;
	}

}
