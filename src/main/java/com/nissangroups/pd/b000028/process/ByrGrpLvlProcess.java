/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000028
 * Module          :OR Ordering					
 * Process Outline :Automatic_order_adjustment_to_OCF_Limit
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 24-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000028.process;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000028.output.B000028Output;
import com.nissangroups.pd.b000028.output.R000004ReportDao;
import com.nissangroups.pd.b000028.util.B000028Constants;
import com.nissangroups.pd.repository.SpecRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class ByrGrpLvlProcess.
 *
 * @author z015060
 */
public class ByrGrpLvlProcess {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(ByrGrpLvlProcess.class
			.getName());
	Map<String, String> byrGrpLvlMap = new HashMap<String, String>();
	Map<String, String> byrGrpLvlOCFMap = new HashMap<String, String>();
	Map<String, String> byrGrpCFMap = new HashMap<String, String>();
	Map<String, String> byrGrpDiffMap= new HashMap<String, String>();
	List<Object[]> byrGrpDiffList = new ArrayList<Object[]>();

	@Autowired(required = false)
	private SpecRepository specRep;
	
	@Autowired(required = false)
	private R000004ReportDao objR000004Report;

	public ByrGrpLvlProcess() {
	}

	public B000028Output executeProcess(B000028Output object)
			throws NumberFormatException, ParseException {
		LOG.info("ByrGrpLvlProcess");

		object.setCarSrsHrzn(specRep.getCarSrsHrzn(object
				.getObjB000028ParamOutput().getPorCd(), object
				.getObjB000028ParamOutput().getOrdrTkBsMnth()));

		object.setByrGrpLvl(specRep.getbyrGrpLvl(object.getCarSrsHrzn(), object
				.getObjB000028ParamOutput().getPorCd(), object
				.getObjB000028ParamOutput().getOrdrTkBsMnth()));
		addDataToByrGrpLvlMap(object.getByrGrpLvl());

		object.setByrGrpOCFLmt(specRep.getbyrGrpOCfLmt(object.getByrGrpLvl(),
				object.getObjB000028ParamOutput().getPorCd(), object
						.getObjB000028ParamOutput().getOrdrTkBsMnth()));
		addDataToByrGrpLvlOCFMap(object.getByrGrpOCFLmt());

		try{
		for (Object[] byrGrpOCFExtrt : object.getByrGrpOCFLmt()) {
			String prdMnth = byrGrpOCFExtrt[2].toString();
			String carSrs = byrGrpOCFExtrt[0].toString();
			String byrGrpCd = byrGrpOCFExtrt[3].toString();
			int ordrQty=Integer.parseInt(byrGrpLvlMap.get(carSrs + prdMnth + byrGrpCd)!= null?byrGrpLvlMap.get(carSrs + prdMnth + byrGrpCd):"0" );
			int ByrGrpAvilFlg=Integer.parseInt(byrGrpLvlMap.get(carSrs + prdMnth + byrGrpCd)!= null?byrGrpLvlMap.get(carSrs + prdMnth + byrGrpCd):"-1" );
			int ocfLmt=Integer.parseInt(byrGrpLvlOCFMap.get(carSrs + prdMnth + byrGrpCd) != null?byrGrpLvlOCFMap.get(carSrs + prdMnth + byrGrpCd):"0" );
			int diff=ocfLmt- ordrQty;
			if(diff != 0){
				if(ByrGrpAvilFlg != -1){
					LOG.info("ORDRQTY->"+ordrQty+"ocfLMT->"+ocfLmt+"diff->"+diff+"*************");
				byrGrpDiffList.add(new Object[] { carSrs,prdMnth,byrGrpCd,diff });
				byrGrpDiffMap.put(carSrs+prdMnth+byrGrpCd, diff+"");
				}
			}
			if(ordrQty == 0){
				R000004ReportDao reportDao = new R000004ReportDao();
				reportDao.setPorCd(object.getObjB000028ParamOutput().getPorCd());
				reportDao.setOrdrTkBsMnth(object.getObjB000028ParamOutput().getOrdrTkBsMnth());
				reportDao.setPrdMnth(prdMnth);
				reportDao.setOcfRgn(byrGrpOCFExtrt[1].toString());
				reportDao.setCarSrs(carSrs);
				reportDao.setByrGrp(byrGrpCd);
				reportDao.setOrdrQty(ordrQty+"");
				reportDao.setOrdrQuanToPlnt(PDConstants.ZERO);
				reportDao.setAutoAdjust(PDConstants.ZERO);
				reportDao.setVolAlloc(ocfLmt+"");
				reportDao.setDiff(diff+"");
				reportDao.setComments(B000028Constants.MESSAGE_REPORT_10);
				reportDao.setRecTypCd(B000028Constants.RECORD_TYPE_10);
				objR000004Report.getReportList().add(reportDao);
			}
		}
	}catch(Exception e){
		LOG.info(e);
		CommonUtil.logMessage(PDMessageConsants.M00076,
				PDConstants.CONSTANT_V1, new String[] {
						B000028Constants.BATCH_ID_B000028 });
	}
		object.setByrGrpDiffList(byrGrpDiffList);
		object.setByrGrpLvlMap(byrGrpLvlMap);
		object.setByrGrpLvlOCFMap(byrGrpLvlOCFMap);
		object.setByrGrpDiffMap(byrGrpDiffMap);
		object.setByrGrpCFMap(byrGrpCFMap);
		return object;
	}

	private void addDataToByrGrpLvlOCFMap(List<Object[]> byrGrpOCfLmt) {
		for (Object[] byrGrpOCFArry : byrGrpOCfLmt) {
			String prdMnth = byrGrpOCFArry[2].toString();
			String carSrs = byrGrpOCFArry[0].toString();
			String byrGrpCd = byrGrpOCFArry[3].toString();
			byrGrpCFMap.put(carSrs + prdMnth + byrGrpCd,
					byrGrpOCFArry[1].toString());
			byrGrpLvlOCFMap.put(carSrs + prdMnth + byrGrpCd,
					byrGrpOCFArry[4].toString());
		}
	}

	private void addDataToByrGrpLvlMap(List<Object[]> byrGrpLvl) {
		for (Object[] byrGrpArry : byrGrpLvl) {
			String prdMnth = byrGrpArry[1].toString();
			String carSrs = byrGrpArry[0].toString();
			String byrGrpCd = byrGrpArry[3].toString();
			byrGrpLvlMap.put(carSrs + prdMnth + byrGrpCd,
					byrGrpArry[2].toString());
		}
	}
}
