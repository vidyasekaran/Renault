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

import java.util.ArrayList;
import java.util.List;

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
 * The Class AutoAdjustReport.
 *
 * @author z015060
 */
public class AutoAdjustReport {

	/** Constant LOG */
	private static final Log LOG = LogFactory.getLog(AutoAdjustReport.class
			.getName());

	@Autowired(required = false)
	private R000004ReportDao objR000004Report;

	@Autowired(required = false)
	private SpecRepository specRep;

	/** Constructor for AutoAdjustReport */
	public AutoAdjustReport() {
	}

	public void executeProcess(B000028Output objOutput) {
		LOG.info(" AutoAdjustReport");
		List<Object[]> byrGrpOrdr = new ArrayList<Object[]>();
		List<Object[]> extrctAutoAdjstByrGrp = specRep.getAutoAdjustReport(
				objOutput.getObjB000028ParamOutput().getPorCd(), objOutput
						.getObjB000028ParamOutput().getOrdrTkBsMnth(),
				objOutput.getPotLvlByrGrp());
		try{
		for (Object[] temp : extrctAutoAdjstByrGrp) {
			float allocation =Integer.parseInt(temp[7].toString());
			float ordrQty =Integer.parseInt(temp[3].toString());
			float diff= allocation - ordrQty;
			if (Float.floatToRawIntBits(diff) != 0) {
				byrGrpOrdr.add(new Object[] { temp[0], temp[1], temp[2],
						temp[3], temp[4], temp[5], temp[6], temp[7], temp[8],
						temp[9] });
				R000004ReportDao reportA = new R000004ReportDao();
				reportA.setPorCd(temp[0].toString());
				reportA.setOrdrTkBsMnth(temp[1].toString());
				reportA.setPrdMnth(temp[2].toString());
				reportA.setCarSrs(temp[6].toString());
				reportA.setByrGrp(temp[4].toString());
				reportA.setOcfRgn(temp[5].toString());
				reportA.setRecTypCd(B000028Constants.RECORD_TYPE_10);
				reportA.setOrdrQty(temp[3].toString());
				reportA.setOrdrQuanToPlnt(temp[8].toString());
				reportA.setDiff(diff+"");
				reportA.setAutoAdjust(temp[9].toString());
				reportA.setVolAlloc( CommonUtil.convertObjectToStringNull(temp[7]));
				objR000004Report.getReportList().add(reportA);
			}
		}
		List<Object[]> extrctAutoAdjstByrCd = specRep
				.getAutoAdjustReportByrCd(byrGrpOrdr);
		LOG.info("C20"+extrctAutoAdjstByrCd.size());
		LOG.info("C20"+extrctAutoAdjstByrCd.size());
		for (Object[] repB : extrctAutoAdjstByrCd) {
			
			R000004ReportDao reportB = new R000004ReportDao();
			reportB.setPorCd(repB[0].toString());
			reportB.setOrdrTkBsMnth(repB[1].toString());
			reportB.setPrdMnth(repB[2].toString());
			reportB.setCarSrs(repB[7].toString());
			reportB.setByrGrp(repB[4].toString());
			reportB.setByrCd(repB[5].toString());
			reportB.setOcfRgn(repB[6].toString());
			reportB.setRecTypCd(B000028Constants.RECORD_TYPE_20);
			reportB.setOrdrQty(repB[3].toString());
			reportB.setOrdrQuanToPlnt(repB[9].toString());
			reportB.setAutoAdjust(repB[8].toString());
			objR000004Report.getReportList().add(reportB);
		}
		List<Object[]> extrctAutoAdjstClrLvl = specRep
				.getAutoAdjustReportClrLvl(extrctAutoAdjstByrCd);
		LOG.info("C30"+extrctAutoAdjstClrLvl.size());
		LOG.info("C30"+extrctAutoAdjstClrLvl.size());
		for (Object[] repC : extrctAutoAdjstClrLvl) {
			R000004ReportDao reportC = new R000004ReportDao();
			reportC.setPorCd(repC[0].toString());
			reportC.setOrdrTkBsMnth(repC[1].toString());
			reportC.setPrdMnth(repC[2].toString());
			reportC.setCarSrs(repC[7].toString());
			reportC.setByrGrp(repC[4].toString());
			reportC.setByrCd(repC[5].toString());
			reportC.setOcfRgn(repC[6].toString());
			reportC.setExNo(CommonUtil.convertObjectToStringNull(repC[14]));
			reportC.setExtClr(repC[11].toString());
			reportC.setIntClr(repC[12].toString());
			reportC.setEndItmPk(repC[9].toString());
			reportC.setEndItmApp(repC[8].toString());
			reportC.setSpecCd(repC[10].toString());
			reportC.setPot(repC[13].toString());
			reportC.setRecTypCd(B000028Constants.RECORD_TYPE_30);
			reportC.setOrdrQtyCs(repC[3].toString());
			reportC.setOrdrQuanToPlntCs(repC[15].toString());
			reportC.setAutoAdjustCs(repC[16].toString());
			objR000004Report.getReportList().add(reportC);
		}
		}catch(Exception e){
			LOG.info(e);
			CommonUtil.logMessage(PDMessageConsants.M00197,
					PDConstants.CONSTANT_V1, new String[] {
							B000028Constants.BATCH_ID_B000028});
			CommonUtil.stopBatch();
		}

	}

	public void addReport(Object[] byrGrp, B000028Output objB000028) {
		LOG.info("Add Auto Adjust Report");
		try {
			CommonUtil.logMessage(PDMessageConsants.M00160,
					PDConstants.CONSTANT_V4, new String[] {
							B000028Constants.BATCH_ID_B000028,
							PDConstants.MESSAGE_BUYER_EI_LEVEL_DETAILS,
							objB000028.getObjB000028ParamOutput().getPorCd(),
							PDConstants.MONTHLY_ORDER_TRN });
			R000004ReportDao reportDaoC1 = new R000004ReportDao();
			String carSrs = byrGrp[0].toString();
			String prdMnth = byrGrp[1].toString();
			String byrGrpCd = byrGrp[2].toString();
			String allocation = objB000028.getByrGrpLvlOCFMap().get(
					carSrs + prdMnth + byrGrpCd);
			String ordrQty = objB000028.getByrGrpLvlMap().get(
					carSrs + prdMnth + byrGrpCd);
			int diff = Integer.parseInt(allocation) - Integer.parseInt(ordrQty);
			reportDaoC1.setPorCd(objB000028.getObjB000028ParamOutput()
					.getPorCd());
			reportDaoC1.setOrdrTkBsMnth(objB000028.getObjB000028ParamOutput()
					.getOrdrTkBsMnth());
			reportDaoC1.setPrdMnth(prdMnth);
			reportDaoC1.setOcfRgn(objB000028.getByrGrpCFMap().get(
					carSrs + prdMnth + byrGrpCd));
			reportDaoC1.setCarSrs(carSrs);
			reportDaoC1.setByrGrp(byrGrpCd);
			reportDaoC1.setOrdrQty(ordrQty);
			reportDaoC1.setAutoAdjust(PDConstants.CONSTANT_ZERO);
			reportDaoC1.setOrdrQuanToPlnt(ordrQty);
			reportDaoC1.setVolAlloc(allocation);
			reportDaoC1.setDiff(diff + "");
			reportDaoC1.setComments(B000028Constants.MESSAGE_REPORT_20);
			reportDaoC1.setRecTypCd(B000028Constants.RECORD_TYPE_10);
			objR000004Report.getReportList().add(reportDaoC1);
			List<Object[]> extrctAutoAdjstByrCase20 = specRep.getbyrGrpC20(
					byrGrp, objB000028.getObjB000028ParamOutput().getPorCd(),
					objB000028.getObjB000028ParamOutput().getOrdrTkBsMnth());
			for (Object[] temp : extrctAutoAdjstByrCase20) {
				// tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY)
				// as ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
				// mbb.OCF_REGION_CD,ms.CAR_SRS,
				R000004ReportDao reportDaoC2 = new R000004ReportDao();
				reportDaoC2.setPorCd(objB000028.getObjB000028ParamOutput()
						.getPorCd());
				reportDaoC2.setOrdrTkBsMnth(objB000028
						.getObjB000028ParamOutput().getOrdrTkBsMnth());
				reportDaoC2.setPrdMnth(byrGrp[1].toString());
				reportDaoC2.setCarSrs(byrGrp[0].toString());
				reportDaoC2.setByrGrp(byrGrp[2].toString());
				reportDaoC2.setOcfRgn(temp[6].toString());
				reportDaoC2.setByrCd(temp[5].toString());
				reportDaoC2.setOrdrQty(temp[3].toString());
				reportDaoC2.setOrdrQuanToPlnt(temp[3].toString());
				reportDaoC2.setAutoAdjust(PDConstants.ZERO);
				reportDaoC2.setRecTypCd(B000028Constants.RECORD_TYPE_20);
				reportDaoC2.setComments(B000028Constants.MESSAGE_REPORT_20);
				objR000004Report.getReportList().add(reportDaoC2);
			}
			List<Object[]> extrctAutoAdjstByrCase30 = specRep
					.getbyrGrpC30(extrctAutoAdjstByrCase20);
			// select
			// tr.POR_CD,tr.ORDRTK_BASE_MNTH,tr.PROD_MNTH,sum(tr.ORDR_QTY) as
			// ordrQty, mbb.BUYER_GRP_CD,mbb.BUYER_CD,")
			// mbb.OCF_REGION_CD,ms.CAR_SRS,ms.APPLD_MDL_CD,ms.PCK_CD,ms.ADTNL_SPEC_CD,
			// mo.EXT_CLR_CD,mo.INT_CLR_CD,tr.POT_CD, me.EX_NO
			for (Object[] case30 : extrctAutoAdjstByrCase30) {
				R000004ReportDao reportDaoC3 = new R000004ReportDao();
				reportDaoC3.setPorCd(case30[0].toString());
				reportDaoC3.setOrdrTkBsMnth(case30[1].toString());
				reportDaoC3.setPrdMnth(case30[2].toString());
				reportDaoC3.setByrGrp(case30[4].toString());
				reportDaoC3.setByrCd(case30[5].toString());
				reportDaoC3.setOcfRgn(case30[6].toString());
				reportDaoC3.setCarSrs(case30[7].toString());
				reportDaoC3.setEndItmApp(case30[8].toString());
				reportDaoC3.setEndItmPk(case30[9].toString());
				reportDaoC3.setSpecCd(case30[10].toString());
				reportDaoC3.setExtClr(case30[11].toString());
				reportDaoC3.setIntClr(case30[12].toString());
				reportDaoC3.setPot(case30[13].toString());
				reportDaoC3.setExNo(CommonUtil.convertObjectToStringNull(case30[14]));
				reportDaoC3.setOrdrQty(case30[3].toString());
				reportDaoC3.setOrdrQuanToPlnt(case30[3].toString());
				reportDaoC3.setAutoAdjust(PDConstants.ZERO);
				reportDaoC3.setRecTypCd(B000028Constants.RECORD_TYPE_30);
				reportDaoC3.setComments(B000028Constants.MESSAGE_REPORT_30);
				objR000004Report.getReportList().add(reportDaoC3);

			}
		} catch (Exception e) {
			LOG.info(e);
			CommonUtil.stopBatch();
		}
	}

}
