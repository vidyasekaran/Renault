/*
 * System Name     :Post Dragon 
 * Sub system Name :B Batch 
 * Function ID     :PST-DRG-B000047
 * Module          :Ordering 	
 * Process Outline :VIN Allocation to Logical Pipeline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 03-02-2016  	  z016127(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000047.output;

import java.util.List;

import com.nissangroups.pd.model.MstPrmtr;

/**
 * The Class B000047PipelineTrnDtls is to store the extracted column values from different database table values.
 * 
 * @author z016127
 */
public class B000047QueryOutputVO 
{

	/** Variable POR CD */
	private String porCD;
	
	/** Variable allocation options */
	private List<String> allocationOptions;
	
	/** Variable error case options */
	private List<String> errorCseOptions;
	
	/** Variable error case option values */
	private String errorCseOptnValue;
	
	/** Variable pipeline transaction details */
	private List<B000047PipelineTrnDtls> pplnTrnDtls;  
	
	/** Variable offline date */
	private String offlnDate;
	
	/** Variable master parameter list */
	private List<MstPrmtr> mstPrmtrLst;
	
	/** Variable calculated date*/
	private String calculatdDate;

	/**
	 * Get the porCD
	 *
	 * @return the porCD
	 */
	public String getPorCD() {
		return porCD;
	}

	/**
	 * Sets the porCD
	 *
	 * @param porCD the porCD to set
	 */
	public void setPorCD(String porCD) {
		this.porCD = porCD;
	}

	/**
	 * Get the allocationOptions
	 *
	 * @return the allocationOptions
	 */
	public List<String> getAllocationOptions() {
		return allocationOptions;
	}

	/**
	 * Sets the allocationOptions
	 *
	 * @param allocationOptions the allocationOptions to set
	 */
	public void setAllocationOptions(List<String> allocationOptions) {
		this.allocationOptions = allocationOptions;
	}

	/**
	 * Get the errorCseOptions
	 *
	 * @return the errorCseOptions
	 */
	public List<String> getErrorCseOptions() {
		return errorCseOptions;
	}

	/**
	 * Sets the errorCseOptions
	 *
	 * @param errorCseOptions the errorCseOptions to set
	 */
	public void setErrorCseOptions(List<String> errorCseOptions) {
		this.errorCseOptions = errorCseOptions;
	}

	/**
	 * Get the errorCseOptnValue
	 *
	 * @return the errorCseOptnValue
	 */
	public String getErrorCseOptnValue() {
		return errorCseOptnValue;
	}

	/**
	 * Sets the errorCseOptnValue
	 *
	 * @param errorCseOptnValue the errorCseOptnValue to set
	 */
	public void setErrorCseOptnValue(String errorCseOptnValue) {
		this.errorCseOptnValue = errorCseOptnValue;
	}

	/**
	 * Get the pplnTrnDtls
	 *
	 * @return the pplnTrnDtls
	 */
	public List<B000047PipelineTrnDtls> getPplnTrnDtls() {
		return pplnTrnDtls;
	}

	/**
	 * Sets the pplnTrnDtls
	 *
	 * @param pplnTrnDtls the pplnTrnDtls to set
	 */
	public void setPplnTrnDtls(List<B000047PipelineTrnDtls> pplnTrnDtls) {
		this.pplnTrnDtls = pplnTrnDtls;
	}

	/**
	 * Get the offlnDate
	 *
	 * @return the offlnDate
	 */
	public String getOfflnDate() {
		return offlnDate;
	}

	/**
	 * Sets the offlnDate
	 *
	 * @param offlnDate the offlnDate to set
	 */
	public void setOfflnDate(String offlnDate) {
		this.offlnDate = offlnDate;
	}

	/**
	 * Get the mstPrmtrLst
	 *
	 * @return the mstPrmtrLst
	 */
	public List<MstPrmtr> getMstPrmtrLst() {
		return mstPrmtrLst;
	}

	/**
	 * Sets the mstPrmtrLst
	 *
	 * @param mstPrmtrLst the mstPrmtrLst to set
	 */
	public void setMstPrmtrLst(List<MstPrmtr> mstPrmtrLst) {
		this.mstPrmtrLst = mstPrmtrLst;
	}

	/**
	 * Get the calculatdDate
	 *
	 * @return the calculatdDate
	 */
	public String getCalculatdDate() {
		return calculatdDate;
	}

	/**
	 * Sets the calculatdDate
	 *
	 * @param calculatdDate the calculatdDate to set
	 */
	public void setCalculatdDate(String calculatdDate) {
		this.calculatdDate = calculatdDate;
	}
	
	
}
