/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000008
 * Module          : Monthly Ordering					
 * Process Outline :Create Monthly Order Base Data
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 21-10-2015  	  z015060(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.b000008.output;


/**
 * The Class B000008Output.
 *
 * @author z015060
 */
public class B000008Output {	

	B000008P1Output objB000008P1Output;
	B000008ParamOutput objB000008ParamOutput;
	ReaderOutput objReaderOutput;
	B000008P2Output objB000008P2Output;
	B000008P3Output objB000008P3Output;
	
	
	public ReaderOutput getObjReaderOutput() {
		return objReaderOutput;
	}

	public void setObjReaderOutput(ReaderOutput objReaderOutput) {
		this.objReaderOutput = objReaderOutput;
	}

	public B000008P2Output getObjB000008P2Output() {
		return objB000008P2Output;
	}

	public void setObjB000008P2Output(B000008P2Output objB000008P2Output) {
		this.objB000008P2Output = objB000008P2Output;
	}

	public B000008P3Output getObjB000008P3Output() {
		return objB000008P3Output;
	}

	public void setObjB000008P3Output(B000008P3Output objB000008P3Output) {
		this.objB000008P3Output = objB000008P3Output;
	}

	public B000008ParamOutput getObjB000008ParamOutput() {
		return objB000008ParamOutput;
	}

	public void setObjB000008ParamOutput(B000008ParamOutput objB000008ParamOutput) {
		this.objB000008ParamOutput = objB000008ParamOutput;
	}

	public B000008P1Output getObjB000008P1Output() {
		return objB000008P1Output;
	}

	public void setObjB000008P1Output(B000008P1Output objB000008P1Output) {
		this.objB000008P1Output = objB000008P1Output;
	}
	
	
	
	
	}
	
