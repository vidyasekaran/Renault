/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-B000003CommonUtil
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  @author(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.util;

import static com.nissangroups.pd.util.PDMessageConsants.M00164;
import static com.nissangroups.pd.util.PDMessageConsants.M00163;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nissangroups.pd.mapper.EndItemMapper;
import com.nissangroups.pd.mapper.OSEIDetailMapper;
import com.nissangroups.pd.mapper.OrderableEndItemSpecMstMapper;
import com.nissangroups.pd.mapper.OseiProductionTypeMstMapper;

/**
 * The Class B000003CommonUtil.
 */
public class B000003CommonUtil {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000003CommonUtil.class);

	/** Variable key value. */
	String keyValue = "";

	/** Variable sdf. */
	SimpleDateFormat sdf = new SimpleDateFormat(PDConstants.DATE_FORMAT_MONTH);

	/** Variable por car srs insrtd set. */
	Set<String> porCarSrsInsrtdSet = new HashSet<String>();

	/** Variable entity manager. */
	private EntityManager entityManager;

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param entityManager
	 *            the new entity manager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Pattern matching.
	 *
	 * @param eismm
	 *            the eismm
	 * @return the list
	 */
	public List<Object[]> patternMatching(EndItemMapper eismm) {

		if (eismm == null) {
			throw new NullPointerException();
		}

		String specdest = eismm.getId().getSPEC_DESTN_CD().trim();

		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.getextractBuilder
						.toString());
		query.setParameter(PDConstants.PRMTR_APPLDMDLCD, eismm.getId()
				.getAPPLD_MDL_CD());
		query.setParameter(PDConstants.PRMTR_PACKCD, eismm.getId().getPCK_CD());
		query.setParameter(PDConstants.PRMTR_SPECDSTNCD, eismm.getId()
				.getSPEC_DESTN_CD().trim());
		query.setParameter(PDConstants.PRMTR_PORCD, eismm.getId().getPOR_CD());
		query.setParameter(PDConstants.PRMTR_IntrClr, eismm.getId()
				.getINT_CLR_CD().trim());
		query.setParameter(PDConstants.PRMTR_EXT_CLR_CD, eismm.getId()
				.getEXT_CLR_CD());
		query.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD, eismm.getId()
				.getPROD_FMY_CD());
		query.setParameter(PDConstants.PRMTR_SPEC_DESTN_LEN, specdest.length());
		query.setParameter(PDConstants.PRMTR_OCF_REGION, eismm.getId()
				.getOCF_REGION_CD());

		return query.getResultList();
	}

	/**
	 * Gets the production method code.
	 *
	 * @param porcd
	 *            the porcd
	 * @param minimumyearmonth
	 *            the minimumyearmonth
	 * @param abolishDate
	 *            the abolish date
	 * @return the production method code
	 * @throws ParseException
	 *             the parse exception
	 */
	/*
	 * public List<HashMap<String, String>> getProductionMethodCode(String
	 * porcd, String minimumyearmonth, String abolishDate) throws ParseException
	 * { List<HashMap<String, String>> hashMaps = new ArrayList<HashMap<String,
	 * String>>(); Query query = entityManager
	 * .createNativeQuery(B000003QueryConstants.getminrecords .toString());
	 * query.setParameter(PDConstants.PRMTR_ABLSHDATE, abolishDate);
	 * query.setParameter(PDConstants.PRMTR_PORCD, porcd);
	 * query.setParameter(PDConstants.PRMTR_MIN_yr_MONTH, minimumyearmonth);
	 * List<Object[]> minresults = query.getResultList(); for (Object[] obj :
	 * minresults) {
	 * 
	 * Map<String, String> productionmethodCodes = new HashMap<String,
	 * String>(); productionmethodCodes.put(PDConstants.POR_CD, (String)
	 * obj[0]);
	 * productionmethodCodes.put(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
	 * (String) obj[1]);
	 * productionmethodCodes.put(PDConstants.PRODUCTION_METHOD_CD, (String)
	 * obj[2]); productionmethodCodes.put(
	 * PDConstants.PRMTR_LENGTH_PRODUCTION_METHOD_CD, String.valueOf(obj[3]));
	 * productionmethodCodes.put(PDConstants.DIFFERENCE_OF_MONTHS,
	 * String.valueOf(obj[4])); productionmethodCodes.put(PDConstants.CR_SRS,
	 * String .valueOf(obj[5]).trim());
	 * productionmethodCodes.put(PDConstants.CAR_SERIES_PRIORITY_CD,
	 * String.valueOf(obj[6]).trim());
	 * productionmethodCodes.put(PDConstants.PRODUCTION_PLANT_CD, String
	 * .valueOf(obj[7]).trim());
	 * productionmethodCodes.put(PDConstants.PRODUCTION_FAMILY_CD, String
	 * .valueOf(obj[8]).trim());
	 * productionmethodCodes.put(PDConstants.PRMTR_PREFIX_YES, String
	 * .valueOf(obj[9]).trim());
	 * productionmethodCodes.put(PDConstants.PRMTR_SUFFIX_YES, String
	 * .valueOf(obj[10]).trim()); productionmethodCodes.put(
	 * PDConstants.SPEC_DESTINATION_CD_CONDITION,
	 * String.valueOf(obj[11]).trim());
	 * 
	 * if (productionmethodCodes.get(PDConstants.PRODUCTION_METHOD_CD) == null)
	 * { continue; }
	 * 
	 * LOG.info("Add some string" + productionmethodCodes
	 * .get(PDConstants.PRODUCTION_METHOD_CD).trim() .length() + 1); hashMaps =
	 * calculationForProductionMonth(productionmethodCodes, minimumyearmonth); }
	 * return hashMaps; }
	 */
	// Dont Touch this method since it is having critical business logic
	public List<HashMap<String, String>> getProductionMethodCode(EndItemMapper endItemMapper,
			String minimumyearmonth, String abolishDate) throws ParseException {

		/*String minrecords = "SELECT p1.POR_CD,p1.ORDR_TAKE_BASE_MNTH,p1.PROD_MTHD_CD,length(p1.PROD_MTHD_CD), "
				+ "(months_between(to_date('"
				+ abolishDate
				+ "','YYYYMM'),to_date('"
				+ minimumyearmonth
				+ "','YYYYMM'))+1)*5 ,p1.CAR_SRS,p1.CAR_SRS_PRITY_CD,p1.PROD_PLNT_CD, "
				+ " p1.PROD_FMY_CD,p1.PRFX_YES,p1.SFFX_YES,p1.SPEC_DESTN_CD_CNDTN"
				+ " FROM MST_PROD_TYPE p1 "
				+ " where p1.POR_CD ='"
				+ endItemMapper.getId().getPOR_CD()
				+ "' and p1.ORDR_TAKE_BASE_MNTH = (select max(ORDR_TAKE_BASE_MNTH) "
				+ " from MST_PROD_TYPE where ORDR_TAKE_BASE_MNTH <= '"
				+ minimumyearmonth
				+ "' and POR_CD = '"
				+ endItemMapper.getId().getPOR_CD()
				+ "' ) "
				+ " order by p1.CAR_SRS_PRITY_CD DESC";
		*/
		String minrecords = B000003QueryConstants.getProductionMethodCode.toString();
		String endItem = endItemMapper.getId().getAPPLD_MDL_CD()
				+ endItemMapper.getId().getPCK_CD();
		String color = endItemMapper.getId().getEXT_CLR_CD()+endItemMapper.getId().getINT_CLR_CD();
		Query query = entityManager.createNativeQuery(minrecords);
		query.setParameter(PDConstants.PRMTR_PORCD, endItemMapper
				.getId().getPOR_CD());
		query.setParameter(PDConstants.PRMTRT_ORDER_TAKE_BASE_MONTH, minimumyearmonth);
		query.setParameter(PDConstants.PRMTRT_PRD_FMLY_CD,
				endItemMapper.getId().getPROD_FMY_CD());
		query.setParameter(PDConstants.PREFIX_YES,
				endItem.substring(0, 7));
		query.setParameter(PDConstants.PRMTR_MAX_ABLSH_DT,
				abolishDate);
		
		query.setParameter(PDConstants.SUFFIX_YES,
				endItem.substring(10, 18));
		query.setParameter(PDConstants.PRMTR_CLR,color );
		List<Object[]> minresults = query.getResultList();
		List<HashMap<String, String>> hashMaps = new ArrayList<HashMap<String, String>>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		Set<String> checkotbm = new HashSet<String>();
		for (Object[] obj : minresults) {
			Map<String, String> tempDao = new HashMap<String, String>();

			Map<String, String> productionmethodCodes = new HashMap<String, String>();
			productionmethodCodes.put("POR_CD", (String) obj[0]);
			productionmethodCodes.put("ORDER_TAKE_BASE_MONTH", (String) obj[1]);
			productionmethodCodes.put("PRODUCTION_METHOD_CD", (String) obj[2]);
			productionmethodCodes.put("LENGTH_PRODUCTION_METHOD_CD",
					CommonUtil.convertObjectToStringNull(obj[3]));
			productionmethodCodes.put("DIFFERENCE OF MONTHS",
					CommonUtil.convertObjectToStringNull(obj[4]));
			productionmethodCodes.put("CAR_SERIES",
					CommonUtil.convertObjectToStringNull(obj[5]));
			productionmethodCodes.put("CAR_SERIES_PRIORITY_CD",
					CommonUtil.convertObjectToStringNull(obj[6]));
			productionmethodCodes.put("PRODUCTION_PLANT_CD",
					CommonUtil.convertObjectToStringNull(obj[7]));
			productionmethodCodes.put("PRODUCTION_FAMILY_CD",
					CommonUtil.convertObjectToStringNull(obj[8]));
			productionmethodCodes.put("PREFIX_YES",
					CommonUtil.convertObjectToStringNull(obj[9]));
			productionmethodCodes.put("SUFFIX_YES",
					CommonUtil.convertObjectToStringNull(obj[10]));
			productionmethodCodes.put("SPEC_DESTINATION_CD_CONDITION",
					CommonUtil.convertObjectToStringNull(obj[11]));

			if (productionmethodCodes.get("PRODUCTION_METHOD_CD") == null) {
				continue;
			}

			LOG.info("Add some string"
					+ productionmethodCodes.get("PRODUCTION_METHOD_CD").trim()
							.length() + 1);
			for (int j = 0; j < (productionmethodCodes.get(
					"PRODUCTION_METHOD_CD").trim().length()); j = j + 5) {
				if (!minimumyearmonth.equals(productionmethodCodes
						.get("ORDER_TAKE_BASE_MONTH"))) {
					if (tempDao.get("ORDER_TAKE_BASE_MONTH") == null
							|| "".equals(tempDao.get("ORDER_TAKE_BASE_MONTH"))) {
						c.setTime(sdf.parse(productionmethodCodes
								.get("ORDER_TAKE_BASE_MONTH")));
						j = 5;
					} else {
						c.setTime(sdf.parse(tempDao
								.get("ORDER_TAKE_BASE_MONTH")));
						tempDao = new HashMap<String, String>();
					}
					c.add(Calendar.MONTH, 1);
					tempDao.put("POR_CD", productionmethodCodes.get("POR_CD"));
					tempDao.put("ORDER_TAKE_BASE_MONTH",
							(sdf.format(c.getTime())));
					tempDao.put("PRODUCTION_METHOD_CD", productionmethodCodes
							.get("PRODUCTION_METHOD_CD").substring(j, j + 5));
					tempDao.put("CAR_SERIES",
							productionmethodCodes.get("CAR_SERIES"));
					tempDao.put("CAR_SERIES_PRIORITY_CD",
							productionmethodCodes.get("CAR_SERIES_PRIORITY_CD"));
					tempDao.put("PRODUCTION_PLANT_CD",
							productionmethodCodes.get("PRODUCTION_PLANT_CD"));
					tempDao.put("OTBM",
							productionmethodCodes.get("ORDER_TAKE_BASE_MONTH"));
					tempDao.put("PRODUCTION_FAMILY_CD",
							productionmethodCodes.get("PRODUCTION_FAMILY_CD"));
					tempDao.put("PREFIX_YES",
							productionmethodCodes.get("PREFIX_YES"));
					tempDao.put("SUFFIX_YES",
							productionmethodCodes.get("SUFFIX_YES"));
					tempDao.put("SPEC_DESTINATION_CD_CONDITION",
							productionmethodCodes
									.get("SPEC_DESTINATION_CD_CONDITION"));

					if (minimumyearmonth.equals(tempDao
							.get("ORDER_TAKE_BASE_MONTH"))
							&& !"     ".equals(tempDao
									.get("PRODUCTION_METHOD_CD"))
							&& tempDao.get("PRODUCTION_METHOD_CD") != null) {
						hashMaps.add((HashMap<String, String>) tempDao);
					}
				} else {

					if (tempDao.get("ORDER_TAKE_BASE_MONTH") == null
							|| "".equals(tempDao.get("ORDER_TAKE_BASE_MONTH"))) {
						c.setTime(sdf.parse(productionmethodCodes
								.get("ORDER_TAKE_BASE_MONTH")));
						c.add(Calendar.MONTH, 0);
					} else {
						c.setTime(sdf.parse(tempDao
								.get("ORDER_TAKE_BASE_MONTH")));
						tempDao = new HashMap<String, String>();
						c.add(Calendar.MONTH, 1);
					}
					tempDao.put("POR_CD", productionmethodCodes.get("POR_CD"));
					tempDao.put("ORDER_TAKE_BASE_MONTH",
							(sdf.format(c.getTime())));
					tempDao.put("PRODUCTION_METHOD_CD", productionmethodCodes
							.get("PRODUCTION_METHOD_CD").substring(j, j + 5));
					tempDao.put("CAR_SERIES",
							productionmethodCodes.get("CAR_SERIES"));
					tempDao.put("CAR_SERIES_PRIORITY_CD",
							productionmethodCodes.get("CAR_SERIES_PRIORITY_CD"));
					tempDao.put("PRODUCTION_PLANT_CD",
							productionmethodCodes.get("PRODUCTION_PLANT_CD"));
					tempDao.put("OTBM",
							productionmethodCodes.get("ORDER_TAKE_BASE_MONTH"));
					tempDao.put("PRODUCTION_FAMILY_CD",
							productionmethodCodes.get("PRODUCTION_FAMILY_CD"));
					tempDao.put("PREFIX_YES",
							productionmethodCodes.get("PREFIX_YES"));
					tempDao.put("SUFFIX_YES",
							productionmethodCodes.get("SUFFIX_YES"));
					tempDao.put("SPEC_DESTINATION_CD_CONDITION",
							productionmethodCodes
									.get("SPEC_DESTINATION_CD_CONDITION"));
					if (minimumyearmonth.equals(tempDao
							.get("ORDER_TAKE_BASE_MONTH"))
							&& !"     ".equals(tempDao
									.get("PRODUCTION_METHOD_CD"))
							&& tempDao.get("PRODUCTION_METHOD_CD") != null) {
						hashMaps.add((HashMap<String, String>) tempDao);
					}

				}
			}
		}
		return hashMaps;
	}

	/**
	 * Insert o sei production type mst.
	 *
	 * @param osei
	 *            the osei
	 */
	public void insertOSeiProductionTypeMst(OseiProductionTypeMstMapper osei) {
		Query delquery = entityManager
				.createNativeQuery(B000003QueryConstants.deleteOSEI.toString());
		delquery.setParameter(PDConstants.PRMTR_UK_OSEI_ID, osei
				.getUK_OSEI_ID().trim());
		delquery.setParameter(PDConstants.ORDERTAKEBASEMONTH,
				osei.getORDER_TAKE_BASE_MONTH());
		delquery.setParameter(PDConstants.PRODUCTION_MONTH,
				osei.getPRODUCTION_MONTH());
		delquery.setParameter(PDConstants.POR_CD, osei.getPOR_CD());
		delquery.executeUpdate();

		int count = 0;
		String productionmethodcd = "";
		for (int i = 0; i < osei.getPRODUCTION_METHOD_CD().length(); i++) {

			LOG.info(osei.getPRODUCTION_METHOD_CD().substring(i, i + 1));
			productionmethodcd = osei.getPRODUCTION_METHOD_CD().substring(i,
					i + 1);
			count++;
			LOG.info("Add some string" + count);
			if (!" ".equals(productionmethodcd)) {
				try {
					Query query = entityManager
							.createNativeQuery(B000003QueryConstants.oseiprodtypemst
									.toString());
					query.setParameter(PDConstants.PRMTR_UK_OSEI_ID, osei
							.getUK_OSEI_ID().trim());
					query.setParameter(PDConstants.ORDERTAKEBASEMONTH,
							osei.getORDER_TAKE_BASE_MONTH());
					query.setParameter(PDConstants.PRODUCTION_PLANT_CD,
							osei.getPRODUCTION_PLANT_CD());
					query.setParameter(PDConstants.PRODUCTION_MONTH,
							osei.getPRODUCTION_MONTH());
					query.setParameter(PDConstants.POR_CD, osei.getPOR_CD());
					query.setParameter(PDConstants.PRODUCTION_WEEK_NUMBER,
							count);
					query.setParameter(PDConstants.PRODUCTION_METHOD_CD,
							productionmethodcd);
					query.executeUpdate();
					LOG.info(M00163
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.BATCH_3_ID)
							.replace(PDConstants.ERROR_MESSAGE_2,
									PDConstants.MESSAGE_INSERTED)
							.replace(
									PDConstants.ERROR_MESSAGE_3,
									PDConstants.MESSAGE_OSEI_PRODUCTION_TYPE_MST));
				} catch (Exception e) {
					LOG.error(M00164
							.replace(PDConstants.ERROR_MESSAGE_1,
									PDConstants.BATCH_3_ID)
							.replace(PDConstants.ERROR_MESSAGE_2,
									PDConstants.MESSAGE_INSERTED)
							.replace(
									PDConstants.ERROR_MESSAGE_3,
									PDConstants.MESSAGE_OSEI_PRODUCTION_TYPE_MST)
							+ e);
				}
			}
		}

	}

	/**
	 * Checkforexistcarseries.
	 *
	 * @param oeism
	 *            the oeism
	 * @return the list
	 */
	public List<Object[]> checkforexistcarseries(
			OrderableEndItemSpecMstMapper oeism) {

		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.checkforexistingcarseries
						.toString());
		query.setParameter(PDConstants.POR_CD, oeism.getPOR_CD());
		query.setParameter(PDConstants.SPECDESTCD, oeism
				.getSPEC_DESTINATION_CD().trim());
		query.setParameter(PDConstants.PRMTR_PACKCD, ""
				+ PDConstants.SYMBL_PERCENTAGE);
		query.setParameter(PDConstants.PRMTR_APPLDMDLCD, ""
				+ PDConstants.SYMBL_PERCENTAGE);
		return query.getResultList();

	}

	/**
	 * Update end item status code.
	 *
	 * @param oseid
	 *            the oseid
	 */
	public void updateEndItemStatusCode(OSEIDetailMapper oseid) {
		Query endItemStatusCode = entityManager
				.createNativeQuery(B000003QueryConstants.updateEndItemStatusCode
						.toString());
		endItemStatusCode.setParameter(PDConstants.END_ITEM_STATUS_CODE,
				oseid.getEND_ITM_STTS_CD());
		endItemStatusCode.setParameter(PDConstants.POR_CD, oseid.getPOR_CD());
		endItemStatusCode.setParameter(PDConstants.PRMTR_UK_OSEI_ID, oseid
				.getUK_OSEI_ID().trim());
		endItemStatusCode.setParameter(PDConstants.PRMTR_OSEI_ABOLISH_DT,
				oseid.getOSEI_ABLSH_DATE());
		endItemStatusCode.setParameter(PDConstants.PRMTR_OSEI_ADOPT_DT,
				oseid.getOSEI_ADPT_DATE());
		endItemStatusCode.executeUpdate();

	}

	/**
	 * Por car series mst.
	 *
	 * @param oeism
	 *            the oeism
	 * @param porCarSrsInsrtdSet
	 *            the por car srs insrtd set
	 * @param porCd
	 *            the por cd
	 * @return the sets the
	 */
	public Set<String> porCarSeriesMst(OrderableEndItemSpecMstMapper oeism,
			Set<String> porCarSrsInsrtdSet, String porCd) {

		keyValue = oeism.getPOR_CD() + oeism.getPRODUCTION_FAMILY_CD()
				+ oeism.getCAR_SERIES();
		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.porcarseriesmst
						.toString());
		query.setParameter(PDConstants.POR_CD, oeism.getPOR_CD());
		query.setParameter(PDConstants.PRODUCTION_FAMILY_CD, oeism
				.getPRODUCTION_FAMILY_CD());
		query.setParameter(PDConstants.CR_SRS, oeism.getCAR_SERIES());
		List<Object[]> result = query.getResultList();

		if (result.isEmpty() && !porCarSrsInsrtdSet.contains(keyValue)) {
			porCarSrsInsrtdSet.add(keyValue);
			Query horizon = entityManager
					.createQuery(B000003QueryConstants.porHorizon.toString());
			horizon.setParameter(PDConstants.POR_CD, porCd);
			LOG.info("This is the input POR cd horizon and the  ["
					+ oeism.getPOR_CD()
					+ "][B000003QueryConstants.porHorizon.toString())]");
			LOG.info("This is the input POR cd horizon and the  [" + porCd
					+ "][B000003QueryConstants.porHorizon.toString())]");
			List<Object[]> horizonval = horizon.getResultList();
			LOG.info("This is the input POR cd horizon and the  "
					+ oeism.getPOR_CD());

			String maxabolishdt = getMaxAbolishDate();
			try {
				Query porcarseriesmstcreation = entityManager
						.createNativeQuery(B000003QueryConstants.insertporcarseriesmst
								.toString());
				porcarseriesmstcreation.setParameter(PDConstants.POR_CD, porCd);
				LOG.info("This is Por CD :::::::::::::: " + porCd);
				porcarseriesmstcreation.setParameter(
						PDConstants.PRODUCTION_FAMILY_CD, oeism
								.getPRODUCTION_FAMILY_CD());
				LOG.info("This is Production Family Code  :::::::::::::: "
						+ oeism.getPRODUCTION_FAMILY_CD());
				porcarseriesmstcreation.setParameter(PDConstants.CR_SRS, oeism
						.getCAR_SERIES());
				LOG.info("This is Car series :::::::::::::: "
						+ oeism.getCAR_SERIES());
				porcarseriesmstcreation.setParameter(
						PDConstants.MAXIMUM_ABOLISH_DATE, maxabolishdt);
				LOG.info("This is maximum abolish date :::::::::::::: "
						+ maxabolishdt);
				porcarseriesmstcreation
						.setParameter(PDConstants.CAR_SERIES_ORDER_HORIZON,
								horizonval.get(0));
				LOG.info("This is Car series horizon  :::::::::::::: "
						+ horizonval.get(0));
				porcarseriesmstcreation.setParameter(
						PDConstants.CAR_SERIES_SIZE_TRIM, oeism.getCAR_SERIES().substring(0, 4));
				LOG.info("This is car series size teim :::::::::::::: "
						+ oeism.getCAR_SERIES().trim().substring(0, 4));
				porcarseriesmstcreation.executeUpdate();
				LOG.info(M00163
						.replace(PDConstants.ERROR_MESSAGE_1,
								PDConstants.BATCH_3_ID)
						.replace(PDConstants.ERROR_MESSAGE_2,
								PDConstants.MESSAGE_INSERTED)
						.replace(PDConstants.ERROR_MESSAGE_3,
								PDConstants.MESSAGE_POR_CAR_SERIES_MST));
			} catch (Exception e) {
				LOG.error(M00163
						.replace(PDConstants.ERROR_MESSAGE_1,
								PDConstants.BATCH_3_ID)
						.replace(PDConstants.ERROR_MESSAGE_2,
								PDConstants.MESSAGE_INSERTED)
						.replace(PDConstants.ERROR_MESSAGE_3,
								PDConstants.MESSAGE_POR_CAR_SERIES_MST)
						+ e);
			}
		}

		return porCarSrsInsrtdSet;
	}

	/**
	 * Gets the horizonfrom parameter mst.
	 *
	 * @param eiSpecMst
	 *            the ei spec mst
	 * @return the horizonfrom parameter mst
	 */
	public String getHorizonfromParameterMst(EndItemMapper eiSpecMst) {

		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.getHorizonfromParameterMst
						.toString());
		query.setParameter(PDConstants.POR_CD, eiSpecMst.getId().getPOR_CD()
				.trim());
		return query.getResultList().get(0).toString();
	}

	/**
	 * Update carseries.
	 *
	 * @param oeism
	 *            the oeism
	 * @param porCd
	 *            the por cd
	 */
	public void updateCarseries(OrderableEndItemSpecMstMapper oeism,
			String porCd) {
		LOG.info("POR CD INSIDE THE update car series method "
				+ oeism.getPOR_CD());
		LOG.info("POR CD ACtual INSIDE THE update car series method " + porCd);
		LOG.info("POR CDoeism.getCAR_SERIES() INSIDE THE update car series method "
				+ oeism.getCAR_SERIES());
		LOG.info("oeism.getPRODUCTION_FAMILY_CD() INSIDE THE update car series method "
				+ oeism.getPRODUCTION_FAMILY_CD());
		LOG.info("oeism.getAPPLIED_MODEL_CD() update car series method "
				+ oeism.getAPPLIED_MODEL_CD());
		LOG.info(" oeism.getPACK_CD() THE update car series method "
				+ oeism.getPACK_CD());
		String updateCrSrsQry = B000003QueryConstants.updateCarseries
				.toString();
		
		try {
			Query updateCarseriesQuery = entityManager
					.createNativeQuery(updateCrSrsQry);
			updateCarseriesQuery.setParameter(PDConstants.POR_CD, porCd);
			updateCarseriesQuery.setParameter(PDConstants.CR_SRS,
					oeism.getCAR_SERIES());
			updateCarseriesQuery.setParameter(PDConstants.PRODUCTION_FAMILY_CD,
					oeism.getPRODUCTION_FAMILY_CD());
			updateCarseriesQuery.setParameter(PDConstants.PRMTR_APPLDMDLCD,
					oeism.getAPPLIED_MODEL_CD() + PDConstants.SYMBL_PERCENTAGE);
			updateCarseriesQuery.setParameter(PDConstants.PRMTR_PACKCD,
					oeism.getPACK_CD() + PDConstants.SYMBL_PERCENTAGE);
			updateCarseriesQuery.setParameter(PDConstants.PRMTR_SPECDSTNCD,
						oeism.getSPEC_DESTINATION_CD());
		
			updateCarseriesQuery.executeUpdate();
			LOG.info(M00163
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_3_ID)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.MESSAGE_UPDATED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.MESSAGE_B000003_MESSAGE_OESI));
		} catch (Exception e) {
			LOG.error(M00164
					.replace(PDConstants.ERROR_MESSAGE_1,
							PDConstants.BATCH_3_ID)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.MESSAGE_UPDATED)
					.replace(PDConstants.ERROR_MESSAGE_3,
							PDConstants.MESSAGE_B000003_MESSAGE_OESI)
					+ e);
		}
	}

	/**
	 * Gets the max uk o sei id.
	 *
	 * @return the max uk o sei id
	 */
	public String getMaxUkOSeiId() {
		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.maxukoseiid.toString());
		String maxid = "";
		if (query.getResultList().get(0) != null) {
			maxid = query.getResultList().get(0).toString();
		}
		return maxid.trim();

	}

	/**
	 * Gets the weeknocalendar.
	 *
	 * @param por
	 *            the por
	 * @param productionmonth
	 *            the productionmonth
	 * @return the weeknocalendar
	 */
	public List<Object[]> getweeknocalendar(String por, String productionmonth) {
		// String getweekno= "select * from MST_WK_NO_CLNDR where "
		// +" MST_WK_NO_CLNDR.POR_CD='"+por+"' AND "
		// +" MST_WK_NO_CLNDR.PROD_MNTH = '"+ production_month+"' "
		// +" AND MST_WK_NO_CLNDR.NON_OPRTNL_FLAG ='0' ";

		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.getweekno.toString());
		query.setParameter(PDConstants.POR_CD, por);
		query.setParameter(PDConstants.PRODUCTION_MONTH, productionmonth);
		return query.getResultList();

	}

	/**
	 * Gets the max abolish date.
	 *
	 * @return the max abolish date
	 */
	public String getMaxAbolishDate() {

		// String maxabolishdate=
		// " SELECT VAL1 FROM MST_PRMTR WHERE PRMTR_CD ='MAXIMUM_ABOLISHED_DATE' and KEY1='MAX' ";
		Query query = entityManager
				.createNativeQuery(B000003QueryConstants.maxabolishdate
						.toString());
		return query.getResultList().get(0).toString();
	}

	/**
	 * Calculation for production month.
	 *
	 * @param productionmethodCodes
	 *            the productionmethod codes
	 * @param minimumyearmonth
	 *            the minimumyearmonth
	 * @return the list
	 * @throws ParseException
	 *             the parse exception
	 */
	// For SonarQube issues
	public List<HashMap<String, String>> calculationForProductionMonth(
			Map<String, String> productionmethodCodes, String minimumyearmonth)
			throws ParseException {
		Map<String, String> tempDao = new HashMap<String, String>();
		List<HashMap<String, String>> hashMapsNew = new ArrayList<HashMap<String, String>>();

		for (int j = 0; j < (productionmethodCodes
				.get(PDConstants.PRODUCTION_METHOD_CD).trim().length() + 1); j = j + 5) {
			if (!minimumyearmonth.equals(productionmethodCodes
					.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH))) {
				tempDao = tempCalculationIfCase(productionmethodCodes, j);
				if (minimumyearmonth.equals(tempDao
						.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH))
						&& !"".equals(tempDao
								.get(PDConstants.PRODUCTION_METHOD_CD))
						&& tempDao.get(PDConstants.PRODUCTION_METHOD_CD) != null) {
					hashMapsNew.add((HashMap<String, String>) tempDao);
				}
			} else {

				tempDao = tempCalculationElseCase(productionmethodCodes, j);
				if (minimumyearmonth.equals(tempDao
						.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH))
						&& !"     ".equals(tempDao
								.get(PDConstants.PRODUCTION_METHOD_CD))
						&& tempDao.get(PDConstants.PRODUCTION_METHOD_CD) != null) {
					hashMapsNew.add((HashMap<String, String>) tempDao);
				}
			}
		}
		return hashMapsNew;
	}

	/**
	 * Temp calculation if case.
	 *
	 * @param productionmethodCodes
	 *            the productionmethod codes
	 * @param j
	 *            the j
	 * @return the map
	 * @throws ParseException
	 *             the parse exception
	 */
	public Map<String, String> tempCalculationIfCase(
			Map<String, String> productionmethodCodes, int j)
			throws ParseException {
		Map<String, String> tempDao = new HashMap<String, String>();

		Calendar c = Calendar.getInstance();
		if (tempDao.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH) == null
				|| "".equals(tempDao
						.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH))) {
			c.setTime(sdf.parse(productionmethodCodes
					.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH)));
			j = 5;
		} else {
			c.setTime(sdf.parse(tempDao
					.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH)));
			tempDao = new HashMap<String, String>();
		}
		c.add(Calendar.MONTH, 1);
		tempDao.put(PDConstants.POR_CD,
				productionmethodCodes.get(PDConstants.POR_CD));
		tempDao.put(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
				sdf.format(c.getTime()));
		tempDao.put(PDConstants.PRODUCTION_METHOD_CD, productionmethodCodes
				.get(PDConstants.PRODUCTION_METHOD_CD).substring(j, j + 5));
		tempDao.put(PDConstants.CR_SRS,
				productionmethodCodes.get(PDConstants.CR_SRS));
		tempDao.put(PDConstants.CAR_SERIES_PRIORITY_CD,
				productionmethodCodes.get(PDConstants.CAR_SERIES_PRIORITY_CD));
		tempDao.put(PDConstants.PRODUCTION_PLANT_CD,
				productionmethodCodes.get(PDConstants.PRODUCTION_PLANT_CD));
		tempDao.put(PDConstants.ORDR_TK_BS_MNTH, productionmethodCodes
				.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH));
		tempDao.put(PDConstants.PRODUCTION_FAMILY_CD,
				productionmethodCodes.get(PDConstants.PRODUCTION_FAMILY_CD));
		tempDao.put(PDConstants.PRMTR_PREFIX_YES,
				productionmethodCodes.get(PDConstants.PRMTR_PREFIX_YES));
		tempDao.put(PDConstants.PRMTR_SUFFIX_YES,
				productionmethodCodes.get(PDConstants.PRMTR_SUFFIX_YES));
		tempDao.put(PDConstants.SPEC_DESTINATION_CD_CONDITION,
				productionmethodCodes
						.get(PDConstants.SPEC_DESTINATION_CD_CONDITION));
		return tempDao;
	}

	/**
	 * Temp calculation else case.
	 *
	 * @param productionmethodCodes
	 *            the productionmethod codes
	 * @param j
	 *            the j
	 * @return the map
	 * @throws ParseException
	 *             the parse exception
	 */
	public Map<String, String> tempCalculationElseCase(
			Map<String, String> productionmethodCodes, int j)
			throws ParseException {

		Map<String, String> tempDao = new HashMap<String, String>();

		Calendar c = Calendar.getInstance();

		if (tempDao.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH) == null
				|| PDConstants.EMPTY_STRING.equals(tempDao
						.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH))) {
			c.setTime(sdf.parse(productionmethodCodes
					.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH)));
			c.add(Calendar.MONTH, 0);
		} else {
			c.setTime(sdf.parse(tempDao
					.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH)));
			tempDao = new HashMap<String, String>();
			c.add(Calendar.MONTH, 1);
		}
		tempDao.put(PDConstants.POR_CD,
				productionmethodCodes.get(PDConstants.POR_CD));
		tempDao.put(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
				sdf.format(c.getTime()));
		tempDao.put(PDConstants.PRODUCTION_METHOD_CD, productionmethodCodes
				.get(PDConstants.PRODUCTION_METHOD_CD).substring(j, j + 5));
		LOG.info("Production Method Code"
				+ productionmethodCodes.get(PDConstants.PRODUCTION_METHOD_CD)
						.substring(j, j + 5));
		tempDao.put(PDConstants.CR_SRS,
				productionmethodCodes.get(PDConstants.CR_SRS));
		tempDao.put(PDConstants.CAR_SERIES_PRIORITY_CD,
				productionmethodCodes.get(PDConstants.CAR_SERIES_PRIORITY_CD));
		tempDao.put(PDConstants.PRODUCTION_PLANT_CD,
				productionmethodCodes.get(PDConstants.PRODUCTION_PLANT_CD));
		tempDao.put(PDConstants.ORDR_TK_BS_MNTH, productionmethodCodes
				.get(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH));
		tempDao.put(PDConstants.PRODUCTION_FAMILY_CD,
				productionmethodCodes.get(PDConstants.PRODUCTION_FAMILY_CD));
		tempDao.put(PDConstants.PRMTR_PREFIX_YES,
				productionmethodCodes.get(PDConstants.PRMTR_PREFIX_YES));
		tempDao.put(PDConstants.PRMTR_SUFFIX_YES,
				productionmethodCodes.get(PDConstants.PRMTR_SUFFIX_YES));
		tempDao.put(PDConstants.SPEC_DESTINATION_CD_CONDITION,
				productionmethodCodes
						.get(PDConstants.SPEC_DESTINATION_CD_CONDITION));

		return tempDao;
	}
}
