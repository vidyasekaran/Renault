package com.nissangroups.pd.b000052.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.FAILED;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDMessageConsants.M00003;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.nissangroups.pd.b000052.output.B000052MnthlyOutputBean;
import com.nissangroups.pd.b000052.util.B000052Constants;
import com.nissangroups.pd.b000052.util.B000052QueryConstants;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmt;
import com.nissangroups.pd.model.TrnBuyerGrpWklyOcfLmtPK;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.TrnBuyerGrpWklyOcfLmtRepository;
import com.nissangroups.pd.repository.TrnLtstMstShdlRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class ByrGrpLvlUsgProcessor implements
		ItemProcessor<B000052MnthlyOutputBean, B000052MnthlyOutputBean> {

	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;

	@Autowired(required = false)
	TrnLtstMstShdlRepository trnLtstMstShdlRepository;

	@Autowired(required = false)
	TrnBuyerGrpWklyOcfLmtRepository trnBuyerGrpWklyOcfLmtRepository;

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(ByrGrpLvlUsgProcessor.class);

	QueryParamBean qryParamBean = new QueryParamBean();

	/** Variable POR CD. */
	private String porCd;

	/** Variable Stage Code. */
	private String prcsType;

	private String ordrTkBsMnth;
	private String ordrTkBsPd;
	private String pltLnSmry;
	private String plntCd;
	private String lnCls;
	private String cnstntDay;
	private String featTypeCd;
	private String featTypeCd2;

	private String prodMnth;
	private String prodWkNo;
	private String oseiId;
	private String featCd;

	private String ocfFrameCd;
	private String carSrs;
	private String potCd;
	private String byrGrpCd;
	private String ocfAllocFlg;
	private String qty;
	private String prodDay;
	private String ocfByrGrpCd;
	private String ocfRgnCd;

	private boolean updtFlag;

	boolean initFlag;

	List<Object[]> p5List = new ArrayList<Object[]>();

	public boolean isInitFlag() {
		return initFlag;
	}

	public void setInitFlag(boolean initFlag) {
		this.initFlag = initFlag;
	}

	@Override
	public B000052MnthlyOutputBean process(B000052MnthlyOutputBean item)
			throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		porCd = item.getPorCd();
		prcsType = item.getPrcsType();
		ordrTkBsPd = item.getOrdrtkBasePd();
		plntCd = item.getPlntCd();
		lnCls = item.getLnCls();
		pltLnSmry = item.getPltLnSmry();
		cnstntDay = item.getCnstntDay();

		qryParamBean.setPorCd(porCd);
		qryParamBean.setPrcsType(prcsType);
		qryParamBean.setOrdrTkBsPrd(ordrTkBsPd);
		qryParamBean.setPlntCd(plntCd);
		qryParamBean.setLineClass(lnCls);
		qryParamBean.setPlntLneSummary(pltLnSmry);
		qryParamBean.setCnstDayNo(cnstntDay);

		setInitFlag(true);

		// List<Object[]> p5List = new ArrayList<Object[]>();
		p5List = trnLtstMstShdlRepository.fetchByrGrgUsgQty(qryParamBean);

		if (p5List == null) {
			LOG.info(B000052Constants.M00158
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2,
							B000052Constants.WKLYFEATMSG)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4,
							B000052Constants.LATESTMASTER));
			CommonUtil.stopBatch();
		}
		LOG.info("The size of p5List :" + p5List.size());
		if (!(p5List == null)) {
			item.setP5List(p5List);
		}
		for (Object[] rowObject : p5List) {
			// if(rowObject[0]!=null && rowObject[1]!=null){
			porCd = String.valueOf(rowObject[0]);
			prodMnth = String.valueOf(rowObject[1]);
			prodWkNo = String.valueOf(rowObject[2]);
			oseiId = String.valueOf(rowObject[3]);
			featCd = String.valueOf(rowObject[4]);
			featTypeCd = String.valueOf(rowObject[5]);
			ocfFrameCd = String.valueOf(rowObject[6]);
			carSrs = String.valueOf(rowObject[7]);
			featTypeCd2 = String.valueOf(rowObject[8]);
			plntCd = String.valueOf(rowObject[9]);
			lnCls = String.valueOf(rowObject[10]);
			prodDay = String.valueOf(rowObject[11]);
			potCd = String.valueOf(rowObject[12]);
			qty = String.valueOf(rowObject[13]);
			ocfAllocFlg = String.valueOf(rowObject[14]);
			ocfRgnCd = String.valueOf(rowObject[15]);
			byrGrpCd = String.valueOf(rowObject[16]);
			ocfByrGrpCd = String.valueOf(rowObject[17]);

			

			if (prodDay.length() == 1) {
				LOG.info("Length of prod Day before space included :"
						+ prodDay.length());
				prodDay = prodDay + " ";
				LOG.info("Length of prod Day after space included :"
						+ prodDay.length());
			}
			
			qryParamBean.setOcfRgnCd(ocfRgnCd);
			
			qryParamBean.setOcfByrGrpCd(ocfByrGrpCd);
			qryParamBean.setPotCd(potCd);
			qryParamBean.setOcfAllocFlg(ocfAllocFlg);
			qryParamBean.setQty(qty);
			qryParamBean.setPorCd(porCd);
			qryParamBean.setPrdMnth(prodMnth);
			qryParamBean.setPrdMnthWkNo(prodWkNo);
			qryParamBean.setOseiId(oseiId);
			qryParamBean.setFeatCd(featCd);
			qryParamBean.setFeatTypCd(featTypeCd);
			qryParamBean.setOcfFrmCd(ocfFrameCd);
			qryParamBean.setCarSrs(carSrs);
			qryParamBean.setByrGrpCd(byrGrpCd);
			qryParamBean.setPlntCd(plntCd);
			qryParamBean.setLineClass(lnCls);
			qryParamBean.setPrdDayNo(prodDay);

			if (isInitFlag()) {
				trnBuyerGrpWklyOcfLmtRepository.initUsgQty(porCd, prodWkNo,
						prodMnth);
				setInitFlag(false);

			}

			// Check for duplicate value
			updtFlag = trnBuyerGrpWklyOcfLmtRepository
					.isListExist(qryParamBean);

			if (updtFlag) {
				// int updtSts;
				try {
					// int updtlmt = ocfLimitUpdate(ocfFrameCd,ocfAllocFlg); //8
					remngWkDaysRevised(qryParamBean);
				} catch (Exception e) {
					LOG.error(e);
					LOG.error(PDMessageConsants.M00164
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.B000052)
							.replace(PDConstants.ERROR_MESSAGE_2,
									B000052Constants.UPDATE)
							.replace(PDConstants.ERROR_MESSAGE_3,
									B000052Constants.BYRWKLYOCFLMT));
					CommonUtil.stopBatch();
				}

			} else {
				try {
					int insrtCount = ocfLimitInsert(qryParamBean); // 2
					remngWkDays(qryParamBean);
				} catch (Exception e) {
					LOG.error(e);
					LOG.error(PDMessageConsants.M00164
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.B000052)
							.replace(PDConstants.ERROR_MESSAGE_2,
									PDConstants.Insert)
							.replace(PDConstants.ERROR_MESSAGE_3,
									B000052Constants.BYRWKLYOCFLMT));
					CommonUtil.stopBatch();
				}

			}

		}

		List<Object[]> byrLvlOcfZeroLmt = new ArrayList<>();
		byrLvlOcfZeroLmt = extraction(qryParamBean);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return item;
	}

	public List<Object[]> extraction(QueryParamBean qpBean) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<Object[]> resultList = new ArrayList<Object[]>();

		resultList = trnBuyerGrpWklyOcfLmtRepository.extractionFetch(qpBean);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

		return resultList;

	}

	public int ocfLimitInsert(QueryParamBean qpBean) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		int rowCount = trnBuyerGrpWklyOcfLmtRepository
				.ocfLimitInsertQuery(qpBean);

		if (rowCount != 0) {
			LOG.info(PDMessageConsants.M00163
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							B000052Constants.BYRWKLYOCFLMT));

		} else {

			LOG.error(PDMessageConsants.M00164
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.Insert)
					.replace(PDConstants.ERROR_MESSAGE_3,
							B000052Constants.BYRWKLYOCFLMT));
			CommonUtil.stopBatch();

		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return rowCount;

	}

	public void remngWkDays(QueryParamBean qp) {
		int day = CommonUtil.stringtoInt(prodDay);
		for (int i = 1; i <= 7; i++) {
			if (i != day) {

				usgLmtQtyInsert(i, qp);
			}
		}
	}

	public void remngWkDaysRevised(QueryParamBean qpBean) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int day = CommonUtil.stringtoInt(prodDay);

		List<String> selectList = new ArrayList<>();
		selectList = trnBuyerGrpWklyOcfLmtRepository
				.ocftrnlimitDaySelect(qpBean);

		for (int i = 1; i <= 7; i++) {
			if (selectList.contains(i + " ")) {
				int val2 = ocfLimitUpdate(i, qpBean);
			}
			if (!selectList.contains(i + " ")) {
				int val1 = usgLmtQtyInsert(i, qpBean);
			}
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	public int usgLmtQtyInsert(int dayNo, QueryParamBean qpBean) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		int rowCount = trnBuyerGrpWklyOcfLmtRepository.ocfLimitInsertQuery(
				dayNo, qpBean);

		if (rowCount != 0) {
			LOG.info(PDMessageConsants.M00163
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							B000052Constants.RNGLVLOCFUSAGE));

		} else {

			LOG.error(PDMessageConsants.M00164
					.replace(PDConstants.ERROR_MESSAGE_1, PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2, PDConstants.INSERTED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							B000052Constants.RNGLVLOCFUSAGE));
			CommonUtil.stopBatch();

		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return rowCount;
	}

	public int ocfLimitUpdate(int testDay, QueryParamBean qpBean) {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		int rowCount = trnBuyerGrpWklyOcfLmtRepository.ocfLmtUpdtQuery(testDay,
				qpBean);
		LOG.info("ocfLimitUpdate inside revised day, row updated :" + rowCount);

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return rowCount;

	}

}
