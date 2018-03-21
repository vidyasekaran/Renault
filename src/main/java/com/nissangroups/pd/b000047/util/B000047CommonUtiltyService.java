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
package com.nissangroups.pd.b000047.util;

import static com.nissangroups.pd.b000047.util.B000047Constants.M00003;
import static com.nissangroups.pd.b000047.util.B000047Constants.M00004;
import static com.nissangroups.pd.b000047.util.B000047Constants.M00164;
import static com.nissangroups.pd.b000047.util.B000047Constants.M00325;
import static com.nissangroups.pd.util.CommonUtil.addMonthToDate;
import static com.nissangroups.pd.util.CommonUtil.convertObjectToString;
import static com.nissangroups.pd.util.CommonUtil.convertStringToDate;
import static com.nissangroups.pd.util.CommonUtil.dateToString;
import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00163;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000047.output.B000047PipelineTrnDtls;
import com.nissangroups.pd.b000047.output.B000047QueryOutputVO;
import com.nissangroups.pd.b000047.output.B000047ReportDao;
import com.nissangroups.pd.b000050.util.QueryConstants;
import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.model.TrnLgclPpln;
import com.nissangroups.pd.model.TrnPhysclPpln;
import com.nissangroups.pd.repository.WeekNoCalendarRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.PDConstants;


public class B000047CommonUtiltyService {

	/* Constant LOG */
	private static final Log LOG = LogFactory
			.getLog(B000047CommonUtiltyService.class.getName());

	/** Stores entity manager */
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager entityManager;
	
	/** B000047QueryOutputVO bean injection */
	@Autowired(required = false)
	B000047QueryOutputVO queryOutputVO;
	
	/** B000047ReportDao bean injection */
	@Autowired(required = false)
	private B000047ReportDao listReportDao;
	
	/** WeekNoCalendarRepository bean injection */
	@Autowired(required=false)
	private WeekNoCalendarRepository weekNoCalendarRepositoryObj ;
	
	/**
	 * P0003 Method to Check the Parameter Master Table for VIN Allocation to Logical Pipeline
	 * 
	 * @return List of MstPrmtr
	 */
	public List<MstPrmtr> getPrmtrMstrDtls() {
		
		List<MstPrmtr> mstPrmtrLst = null;
			
		StringBuilder dynamicQuery = new StringBuilder();
			dynamicQuery.append(B000047QueryConstants.getPrmtrMstrDtls);
			String queryStr = dynamicQuery.toString();
			Query query = entityManager.createNativeQuery(queryStr, MstPrmtr.class);
			query.setParameter(IFConstants.porCd_Param, queryOutputVO.getPorCD());
			mstPrmtrLst = query.getResultList();
			if(mstPrmtrLst == null || mstPrmtrLst.isEmpty()){
				LOG.info(M00003.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
						.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.VIN_ALLCTN_LGCL_PPLN)
						.replace(PDConstants.ERROR_MESSAGE_3, queryOutputVO.getPorCD())
						.replace(PDConstants.ERROR_MESSAGE_4, B000047Constants.PARAMETER_MST));
				CommonUtil.stopBatch();
			}
		return mstPrmtrLst;
	}
	
	/**
	 * P0004 Method to Allocate the Vin Allocation/Error list from Parameter Values in Arraylist
	 */
	public void getAllctnOrErrorLst(){
		
		List<MstPrmtr> mstPrmtrLst = getPrmtrMstrDtls();
		
		for(MstPrmtr prmtr :mstPrmtrLst){
			if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.ALLOCATION_OPTIONS)){
				
				/**P0004.1 */
				queryOutputVO.setAllocationOptions(getSortedList(prmtr.getVal1()));
			
			}else if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.ERROR_CASE_OPTIONS)){
				
				/**P0004.2 */
				queryOutputVO.setErrorCseOptnValue(prmtr.getVal1());
				queryOutputVO.setErrorCseOptions(getSortedList(prmtr.getVal2()));
			}
		}
	}
	
	/**
	 * Method to get the sorted Allocation/Error list 
	 * @param value
	 * 			string
	 * @return list of string
	 */
	public List<String> getSortedList(String value){
		
		String[] tokens = value.split(B000047Constants.DELIMTR);
		List<String> allctnOptns = new ArrayList<String>(tokens.length);
		for (String token : tokens) {
			allctnOptns.add(token);
		}
		// Sort it
		Collections.sort(allctnOptns);
		
		return allctnOptns;
	}
	
	/**
	 * P0005.3 Method to get error case options list for report generation.
	 */
	public void getErrorCaseOptionList(){
		
		List<B000047PipelineTrnDtls> pplnTrnDtls =  queryOutputVO.getPplnTrnDtls();
		List<String> errorCseOptns =  queryOutputVO.getErrorCseOptions();
		
		if(queryOutputVO.getErrorCseOptnValue().equalsIgnoreCase(B000047Constants.YES)){
			for(B000047PipelineTrnDtls pplnTrn :pplnTrnDtls){
				
				B000047ReportDao reportDao = generateReportList(pplnTrn);
				if(errorCseOptns.contains(pplnTrn.getCseErr())){
					/**P0005.3 Add error code and message based on case */
					switch(pplnTrn.getCseErr()){
						case "2":
							reportDao.setWarngCd(B000047Constants.wrngCd30_2);
							reportDao.setWarngMsg(B000047Constants.wrngMsg30_2);
							getReportList(B000047Constants.THREE,pplnTrn);
							break;
						
						case "3":
							reportDao.setWarngCd(B000047Constants.wrngCd30_3);
							reportDao.setWarngMsg(B000047Constants.wrngMsg30_3);
							getReportList(B000047Constants.TWO,pplnTrn);
							break;
							
						case "4":
							reportDao.setWarngCd(B000047Constants.wrngCd30_4);
							reportDao.setWarngMsg(B000047Constants.wrngMsg30_4);
							break;
					}	
				}
				listReportDao.getReportList().add(reportDao);
			}
		}
	}
	
	/**
	 * P0005.4 Method to get the error code and message based on the case and add to the list
	 * @param cse
	 * 				string
	 * @param pplnTrn
	 * 				B000047PipelineTrnDtls
	 */
	public void getReportList(String cse, B000047PipelineTrnDtls pplnTrn){
		
		B000047ReportDao reportDao =null;
		
		List<String> allctnOptns = queryOutputVO.getAllocationOptions();
		if(allctnOptns.contains(cse)){
			 reportDao = generateReportList(pplnTrn);
			
			if(cse.equalsIgnoreCase(B000047Constants.TWO)){
				reportDao.setWarngCd(B000047Constants.wrngCd30_2);
				reportDao.setWarngMsg(B000047Constants.wrngMsg30_2);
			}else if(cse.equalsIgnoreCase(B000047Constants.THREE)){
				reportDao.setWarngCd(B000047Constants.wrngCd30_2);
				reportDao.setWarngMsg(B000047Constants.wrngMsg30_2);
			}
		}
		listReportDao.getReportList().add(reportDao);
	}
	
	/**
	 * P0005.4 Method to add the pipeline records to the list for report generation
	 * @param pplnTrn
	 * 				B000047PipelineTrnDtls
	 * @return B000047ReportDao
	 */
	public B000047ReportDao generateReportList(B000047PipelineTrnDtls pplnTrn){
		
		B000047ReportDao reportDao = new B000047ReportDao();
		reportDao.setPorCd(queryOutputVO.getPorCD());
		reportDao.setOcfRgnCd(pplnTrn.getOcfRegionCd());
		reportDao.setProdMnth(pplnTrn.getProdMnth());
		reportDao.setCarSrs(pplnTrn.getCarSrs());
		reportDao.setBuyerCd(pplnTrn.getBuyerCd());
		reportDao.setOrdrProdWkNo(pplnTrn.getProdWkNo());
		reportDao.setVinProdWkNo(pplnTrn.getProdWeekNo());
		reportDao.setProdOrdrNo(pplnTrn.getProdOrdrNo());
		reportDao.setVinNo(pplnTrn.getVinNo());
		reportDao.setEndItem(pplnTrn.getEndItem());
		reportDao.setColorCd(pplnTrn.getClrCd());
		reportDao.setSpecDestnCd(pplnTrn.getSpecDestnCd());
		reportDao.setSalesNteNo(pplnTrn.getSlsNteNo());
		reportDao.setExNo(pplnTrn.getExNo());
		reportDao.setOrdrOfflnDte_YYYY_MM_DD(pplnTrn.getPlndOfflnDate());
		reportDao.setVinOfflnDte_YYYY_MM_DD(pplnTrn.getOffLnPlndDate());
		reportDao.setOrdrPlntCd(pplnTrn.getProdPlntCd());
		reportDao.setVinPlntCd(pplnTrn.getProdPlantCd());
		return reportDao;
		
	}
	
	/**P0005.2 Method to get the VIN - Pipeline Difference records for case one and add to the  list.
	 *  
	 * @param lgclPplnRcrd
	 * 					TrnLgclPpln
	 * @return TrnPhysclPpln
	 */
	public TrnPhysclPpln getPhsclPplnTrnDtlsForCase1(TrnLgclPpln lgclPplnRcrd) {
		
		List<TrnPhysclPpln> physclPplnTrnDtls = null;
		TrnPhysclPpln trnPhysclPpln =null;
		
			StringBuilder dynamicQuery = new StringBuilder();
			dynamicQuery.append(B000047QueryConstants.getPhysclPpLnTrnDtls)
						.append(B000047QueryConstants.getPhysclPplnTrnDtlsCse2)
						.append(B000047QueryConstants.getPhysclPplnTrnDtlsCse3)
						.append(B000047QueryConstants.orderByClause);
			
			String queryStr = dynamicQuery.toString();
			Query query = entityManager.createNativeQuery(queryStr, TrnPhysclPpln.class);
			query.setParameter(IFConstants.POR_CD, lgclPplnRcrd.getPorCd());
			query.setParameter(IFConstants.PROD_MNTH, lgclPplnRcrd.getProdMnth());
			query.setParameter(IFConstants.POT_CD, lgclPplnRcrd.getPotCd());
			query.setParameter(IFConstants.OSEI_ID, lgclPplnRcrd.getOseiId());
			query.setParameter(IFConstants.PROD_PLNT_CD, lgclPplnRcrd.getProdPlntCd());
			query.setParameter(IFConstants.PROD_WK_NO, lgclPplnRcrd.getProdWkNo());
			physclPplnTrnDtls = query.getResultList();
			if(physclPplnTrnDtls != null && !physclPplnTrnDtls.isEmpty()){
				trnPhysclPpln = physclPplnTrnDtls.get(0);
			}else{
				LOG.info(M00325.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
						.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
						.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL));
			}
		return trnPhysclPpln;
	}
	
	/**P0005.2 Method to get the VIN - Pipeline Difference records for case 2 and add to the  list.
	 *  
	 * @param lgclPplnRcrd
	 * 					TrnLgclPpln
	 * @return TrnPhysclPpln
	 */
	public TrnPhysclPpln getPhsclPplnTrnDtlsForCase2(TrnLgclPpln lgclPplnRcrd) {
			
			List<TrnPhysclPpln> physclPplnTrnDtls = null;
			TrnPhysclPpln trnPhysclPpln =null;
			
				StringBuilder dynamicQuery = new StringBuilder();
				dynamicQuery.append(B000047QueryConstants.getPhysclPpLnTrnDtls)
							.append(B000047QueryConstants.getPhysclPplnTrnDtlsCse2)
							.append(B000047QueryConstants.orderByClause);
				
				String queryStr = dynamicQuery.toString();
				Query query = entityManager.createNativeQuery(queryStr, TrnPhysclPpln.class);
				query.setParameter(IFConstants.POR_CD, lgclPplnRcrd.getPorCd());
				query.setParameter(IFConstants.PROD_MNTH, lgclPplnRcrd.getProdMnth());
				query.setParameter(IFConstants.POT_CD, lgclPplnRcrd.getPotCd());
				query.setParameter(IFConstants.OSEI_ID, lgclPplnRcrd.getOseiId());
				query.setParameter(IFConstants.PROD_WK_NO, lgclPplnRcrd.getProdWkNo());
				physclPplnTrnDtls = query.getResultList();
				if(physclPplnTrnDtls != null && !physclPplnTrnDtls.isEmpty()){
					trnPhysclPpln = physclPplnTrnDtls.get(0);
				}else{
					LOG.info(M00325.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL));
				}
			return trnPhysclPpln;
		}	

	/**P0005.2 Method to get the VIN - Pipeline Difference records for case 3 and add to the list.
	 *  
	 * @param lgclPplnRcrd
	 * 					TrnLgclPpln
	 * @return TrnPhysclPpln
	 */
	public TrnPhysclPpln getPhsclPplnTrnDtlsForCase3(TrnLgclPpln lgclPplnRcrd) {
		
		List<TrnPhysclPpln> physclPplnTrnDtls = null;
		TrnPhysclPpln trnPhysclPpln =null;
		
			StringBuilder dynamicQuery = new StringBuilder();
			dynamicQuery.append(B000047QueryConstants.getPhysclPpLnTrnDtls)
						.append(B000047QueryConstants.getPhysclPplnTrnDtlsCse3)
						.append(B000047QueryConstants.orderByClause);
			
			String queryStr = dynamicQuery.toString();
			Query query = entityManager.createNativeQuery(queryStr, TrnPhysclPpln.class);
			query.setParameter(IFConstants.POR_CD, lgclPplnRcrd.getPorCd());
			query.setParameter(IFConstants.PROD_MNTH, lgclPplnRcrd.getProdMnth());
			query.setParameter(IFConstants.POT_CD, lgclPplnRcrd.getPotCd());
			query.setParameter(IFConstants.OSEI_ID, lgclPplnRcrd.getOseiId());
			query.setParameter(IFConstants.PROD_PLNT_CD, lgclPplnRcrd.getProdPlntCd());
			physclPplnTrnDtls = query.getResultList();
			if(physclPplnTrnDtls != null && !physclPplnTrnDtls.isEmpty()){
				trnPhysclPpln = physclPplnTrnDtls.get(0);
			}else{
				LOG.info(M00325.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
						.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
						.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL));
			}
		return trnPhysclPpln;
	}	
	
	/**P0005.2 Method to get the VIN - Pipeline Difference records for case 4 and add to the list.
	 *  
	 * @param lgclPplnRcrd
	 * 					TrnLgclPpln
	 * @return TrnPhysclPpln
	 */
	public TrnPhysclPpln getPhsclPplnTrnDtlsForCase4(TrnLgclPpln lgclPplnRcrd) {
			
			List<TrnPhysclPpln> physclPplnTrnDtls = null;
			TrnPhysclPpln trnPhysclPpln =null;
			
				StringBuilder dynamicQuery = new StringBuilder();
				dynamicQuery.append(B000047QueryConstants.getPhysclPpLnTrnDtls)
							.append(B000047QueryConstants.orderByClause);
				
				String queryStr = dynamicQuery.toString();
				Query query = entityManager.createNativeQuery(queryStr, TrnPhysclPpln.class);
				query.setParameter(IFConstants.POR_CD, lgclPplnRcrd.getPorCd());
				query.setParameter(IFConstants.PROD_MNTH, lgclPplnRcrd.getProdMnth());
				query.setParameter(IFConstants.POT_CD, lgclPplnRcrd.getPotCd());
				query.setParameter(IFConstants.OSEI_ID, lgclPplnRcrd.getOseiId());
				physclPplnTrnDtls = query.getResultList();
				if(physclPplnTrnDtls != null && !physclPplnTrnDtls.isEmpty()){
					trnPhysclPpln = physclPplnTrnDtls.get(0);
				}else{
					LOG.info(M00325.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL));
				}
			return trnPhysclPpln;
		}	


	 	/**
		 * P0005.5.4 Method to get the Logical pipeline trn Latest Sequence Id.
		 * 
		 * @return String Seq no.
		 */
		public String getTrnLgclPplnSeqNo() {
			LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);

			Query lgclPipLnSqId = entityManager.createNativeQuery(QueryConstants.TRN_LGCL_PIPELN_VHCL_SEQ_ID.toString());
			
			@SuppressWarnings("unchecked")
			List<Object[]> result = lgclPipLnSqId.getResultList();
			
			String seqNo = CommonUtil.bigDecimaltoString(result.get(0));
			if (result.isEmpty()) {
				return null;
			}
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);

			return seqNo;
		}
		
	    /**
	     * P0005.5.1 Update the VIN NO to Logical Pipeline Trn
	     * 
	     * @throws PdApplicationException
	     */
	    public void updateLgclPipLnTrn() throws PdApplicationException {
			LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
			
			List<B000047PipelineTrnDtls> pplnTrnDtls = queryOutputVO.getPplnTrnDtls();
			Query queryUpdate= entityManager.createNativeQuery(B000047QueryConstants.updateLgclPpln.toString());
			
			for(B000047PipelineTrnDtls pplnTrn :pplnTrnDtls){
				
				queryUpdate.setParameter(IFConstants.VIN_NO, pplnTrn.getVinNo());
				queryUpdate.setParameter(IFConstants.VHCL_SEQ_ID, pplnTrn.getVhcleSeqId());
				queryUpdate.setParameter(IFConstants.PROD_MNTH, pplnTrn.getProdMnth());
				queryUpdate.setParameter(IFConstants.PROD_WK_NO, pplnTrn.getProdWkNo());
				queryUpdate.setParameter(IFConstants.POT_CD,pplnTrn.getPotCd());
				queryUpdate.setParameter(IFConstants.OSEI_ID, pplnTrn.getOseiId());
				queryUpdate.setParameter(IFConstants.POR_CD, queryOutputVO.getPorCD());
				
				try {
					queryUpdate.executeUpdate();
					LOG.error(M00163.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.UPDATED)
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.LOGICAL_PIPELINE_TRN));
				} catch (Exception e) {
					LOG.error(PDConstants.EXCEPTION +e.getMessage());
					LOG.error(M00164.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.UPDATE)
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.LOGICAL_PIPELINE_TRN));
				} 
			}
	        
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		}	
	    
	    
	    /**
	     * P0005.5.2 Update record in Physical Pipeline Trn table
	     * 
	     * @throws PdApplicationException
	     */
	    public void updatePhsclPipLnTrn() throws PdApplicationException {
			LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
			
			List<B000047PipelineTrnDtls> pplnTrnDtls = queryOutputVO.getPplnTrnDtls();
			Query queryUpdate= entityManager.createNativeQuery(B000047QueryConstants.updatePhysclPpln.toString());
			
			for(B000047PipelineTrnDtls pplnTrn :pplnTrnDtls){
				
				queryUpdate.setParameter(IFConstants.VIN_NO, pplnTrn.getVinNo());
				queryUpdate.setParameter(IFConstants.VHCL_SEQ_ID, pplnTrn.getVhcleSeqId());
				queryUpdate.setParameter(IFConstants.POR_CD, queryOutputVO.getPorCD());
				
				try {
					queryUpdate.executeUpdate();
					LOG.error(M00163.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.UPDATED)
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL_PIPELINE_TRN));
				} catch (Exception e) {
					LOG.error(PDConstants.EXCEPTION +e.getMessage());
					LOG.error(M00164.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.UPDATE)
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL_PIPELINE_TRN));
				} 
			}
	        
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		}	
	    
	    
	    /**
	     * P0005.5.4 Update Sequence ID in PHYSICAL PIPELINE TRN for not allocated Vin to Logical Pipeline Trn
	     * 
	     * @throws PdApplicationException
	     */
	    public void updateSeqIdInPhsclPipLnTrn(TrnPhysclPpln lgclPplnRcrd) throws PdApplicationException {
			LOG.info(DOLLAR+INSIDE_METHOD+DOLLAR);
			
			Query queryUpdate= entityManager.createNativeQuery(B000047QueryConstants.updateSeqId.toString());
				
				queryUpdate.setParameter(IFConstants.VHCL_SEQ_ID, getTrnLgclPplnSeqNo());
				queryUpdate.setParameter(IFConstants.POR_CD, queryOutputVO.getPorCD());
			//	queryUpdate.setParameter(IFConstants.OSEI_ID, lgclPplnRcrd.);
				queryUpdate.setParameter(IFConstants.PROD_MNTH, lgclPplnRcrd.getProdMnth());
				queryUpdate.setParameter(IFConstants.PROD_WK_NO, lgclPplnRcrd.getProdWkNo());
				
				try {
					queryUpdate.executeUpdate();
					LOG.error(M00163.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.UPDATED)
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL_PIPELINE_TRN));
				} catch (Exception e) {
					LOG.error(PDConstants.EXCEPTION +e.getMessage());
					LOG.error(M00164.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.UPDATE)
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL_PIPELINE_TRN));
				} 
	        
			LOG.info(DOLLAR+OUTSIDE_METHOD+DOLLAR);
		}	
	
	    /**
		 * P0006.1 Check the Parameter Master for extracting Latest Offline date
		 * 
		 * @return List of MstPrmtr
		 */
		public String getOfflnDateFrmMstPrmtr() {
			
			String mstPrmtr = null;
				StringBuilder dynamicQuery = new StringBuilder();
				dynamicQuery.append(B000047QueryConstants.extractPrmtrMst);
				String queryStr = dynamicQuery.toString();
				Query query = entityManager.createNativeQuery(queryStr, MstPrmtr.class);
				query.setParameter(IFConstants.porCd_Param, queryOutputVO.getPorCD());
				List<MstPrmtr> mstPrmtrLst = query.getResultList();
				if(mstPrmtrLst != null && !mstPrmtrLst.isEmpty()){
					mstPrmtr = mstPrmtrLst.get(0).getVal1();
				}else{
					LOG.error(M00003.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, B000047Constants.VIN_ALLCTN_OFFLN_EXTRCTN)
							.replace(PDConstants.ERROR_MESSAGE_3, queryOutputVO.getPorCD())
							.replace(PDConstants.ERROR_MESSAGE_4, B000047Constants.PARAMETER_MST));
				}
			return mstPrmtr;
		}
		
		/**
		 * P0006.2 Extract Latest MS Offline Date / Planned Offline Date
		 * 
		 * @return List of MstPrmtr
		 */
		public Date getOfflnDateFrmPhysclPpln() {
			
			Date physclPpln = null;
				StringBuilder dynamicQuery = new StringBuilder();
				dynamicQuery.append(B000047QueryConstants.extctOfflineDate)
							.append("'" +getOfflnDateFrmMstPrmtr()+ "'")
							.append(B000047QueryConstants.extctOfflineDateCndtn);
				String queryStr = dynamicQuery.toString();
				Query query = entityManager.createNativeQuery(queryStr);
				query.setParameter(IFConstants.porCd_Param, queryOutputVO.getPorCD());
				
				List<Object[]> physclPplnLst = query.getResultList();
				if(physclPplnLst != null && !physclPplnLst.isEmpty()){
					
					try{
						physclPpln = convertStringToDate(convertObjectToString(physclPplnLst.get(1)));
						queryOutputVO.setOfflnDate(dateToString(physclPpln));
					}catch (ParseException e) {
						LOG.error("Exception :" +e);
					}
					
				}else{
					LOG.error(M00004.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL_PIPELINE_TRN));
					CommonUtil.stopBatch();
				}
			return physclPpln;
		}
		
		/**
		 * P0006.3 Offline Date calculation based on Parameter Mst
		 * 
		 * @return List of MstPrmtr
		 */
		public List<MstPrmtr> getRmngLgclPplnFrmPrmtr() {
			
			List<MstPrmtr> mstPrmtrLst = null;
				
			StringBuilder dynamicQuery = new StringBuilder();
				dynamicQuery.append(B000047QueryConstants.extctRmngLgclPpln);
				String queryStr = dynamicQuery.toString();
				Query query = entityManager.createNativeQuery(queryStr, MstPrmtr.class);
				query.setParameter(IFConstants.porCd_Param, queryOutputVO.getPorCD());
				mstPrmtrLst = query.getResultList();
				if(mstPrmtrLst != null && !mstPrmtrLst.isEmpty()){
					queryOutputVO.setMstPrmtrLst(mstPrmtrLst);
				}else{
					LOG.error(M00004.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.PHYSICAL_PIPELINE_TRN));
					CommonUtil.stopBatch();
				}
			return mstPrmtrLst;
		}
		
		/**P0006.4 Offline date calculation
		 * 
		 * @throws ParseException 
		 * @throws Exception 
		 * 
		 */
		public Date offlnCalculatnDate() throws Exception{
			
			List<MstPrmtr> mstPrmtrLst = getRmngLgclPplnFrmPrmtr();
			Date offlnDate = getOfflnDateFrmPhysclPpln();
			
			for(MstPrmtr prmtr:mstPrmtrLst){
				
				if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.MONTH) && prmtr.getVal1().equalsIgnoreCase(B000047Constants.YES)
							&& prmtr.getVal2() != B000047Constants.NULL){
					offlnDate = addMonthToDate(offlnDate, Integer.parseInt(prmtr.getVal2()));
				}else if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.DAY) && prmtr.getVal1().equalsIgnoreCase(B000047Constants.YES)
						&& prmtr.getVal2() != B000047Constants.NULL){
					offlnDate = getDayOfMonth(offlnDate, Integer.parseInt(prmtr.getVal2()));
				}else if(prmtr.getId().getKey2().equalsIgnoreCase(B000047Constants.WEEK) && prmtr.getVal1().equalsIgnoreCase(B000047Constants.YES)
						&& prmtr.getVal2() != B000047Constants.NULL){
					offlnDate = getWeekOfYear(offlnDate, Integer.parseInt(prmtr.getVal2()));
				}
			}
			return offlnDate;
		}
		
		/**
		 * P0006.5 Method to convert Offline_Date to YYYYMMWD format
		 */
		public String cnvrtOflnDateToYYYYMMWD(){
			
			Date offlnDate = null;
			String convrtdDate = "";
			try {
				offlnDate = offlnCalculatnDate();
				String adptPrd = weekNoCalendarRepositoryObj.convertYYYYMMDDtoYYYYMMWD(dateToString(offlnDate), queryOutputVO.getPorCD());
				if(adptPrd== null || adptPrd.equalsIgnoreCase("")){
					LOG.error(M00004.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
							.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
							.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.WEEK_NO_CALENDAR));
					CommonUtil.stopBatch();
				}else{
					convrtdDate = dateToString(offlnDate);
					queryOutputVO.setCalculatdDate(convrtdDate);
				}
			} catch (Exception e) {
				LOG.error(M00004.replace(PDConstants.ERROR_MESSAGE_1,B000047Constants.BATCH_47_ID_MSG)
						.replace(PDConstants.ERROR_MESSAGE_2, queryOutputVO.getPorCD())
						.replace(PDConstants.ERROR_MESSAGE_3, B000047Constants.WEEK_NO_CALENDAR));
				CommonUtil.stopBatch();
			}
			return convrtdDate;
		}
		
		
		/**
		 * Method to get the day of the month
		 * @param d
		 * @param addValue
		 * @return
		 * @throws Exception
		 */
		private Date getDayOfMonth(Date d,int addValue) throws Exception
	    {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(d);
	        calendar.set(Calendar.DAY_OF_MONTH, addValue);
	        return calendar.getTime();
	    }
		
		/**
		 * Method to get the week of the year
		 * @param d
		 * @param addValue
		 * @return
		 * @throws Exception
		 */
		private Date getWeekOfYear(Date d,int addValue) throws Exception
	    {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(d);
	        calendar.set(Calendar.WEEK_OF_YEAR, addValue);
	        return calendar.getTime();
	    }
	
	/**
	 * Get the entityManager
	 *
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entityManager
	 *
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Get the queryOutputVO
	 *
	 * @return the queryOutputVO
	 */
	public B000047QueryOutputVO getQueryOutputVO() {
		return queryOutputVO;
	}

	/**
	 * Sets the queryOutputVO
	 *
	 * @param queryOutputVO the queryOutputVO to set
	 */
	public void setQueryOutputVO(B000047QueryOutputVO queryOutputVO) {
		this.queryOutputVO = queryOutputVO;
	}
	
}
