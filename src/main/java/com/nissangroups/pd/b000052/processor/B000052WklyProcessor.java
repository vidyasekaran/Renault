package com.nissangroups.pd.b000052.processor;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
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
import com.nissangroups.pd.model.MstWklyOrdrTakeBase;
import com.nissangroups.pd.repository.ByrWklyOcfUsgRepository;
import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.repository.QueryParamBean;
import com.nissangroups.pd.repository.TrnLtstMstShdlRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class B000052WklyProcessor implements ItemProcessor<MstWklyOrdrTakeBase, B000052MnthlyOutputBean>{

	
	@Autowired(required = false)
	IfCommonUtil commonutility;
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog
			(B000052Processor.class);
	
	@PersistenceContext(name = PERSISTENCE_NAME)
	private EntityManager eMgr;
	
	@Autowired(required = false)
	private TrnLtstMstShdlRepository trnLtstMstShdlRepository ;
	
	@Autowired(required = false)
	private ByrWklyOcfUsgRepository byrWklyOcfUsgRepository;
	
	@Autowired(required = false)
	ParameterMstRepository parameterMstRepository;
	

	
	QueryParamBean qryParamBean = new QueryParamBean();
	
	/** Variable POR CD. */
	private String porCd;

	

	/** Variable Stage Code. */
	private String prcsType;
	
	
	private String ordrTkBsMnth;
	private String ordrTkBsPrd;
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
	private String byrGrpCd;
	private String mnth;
	private String weekNo;
	
	private int maxProdMnth=0;
	private int maxProdWk=0;


	
	@Override
	public B000052MnthlyOutputBean process(MstWklyOrdrTakeBase item) throws Exception {
		 LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
B000052MnthlyOutputBean outputBean =  new B000052MnthlyOutputBean(); 
		
		porCd =  getPorCd();
		prcsType = getPrcsType();
		
		LOG.info("The por value from config:" +porCd);
		LOG.info("The prcssType value from config:"+prcsType);
		
		outputBean.setPorCd(porCd);
		outputBean.setPrcsType(prcsType);
		
		qryParamBean.setPorCd(porCd);
		qryParamBean.setPrcsType(prcsType);
		
		
		LOG.info("The value from: reader to processor :"+item);
		if(item==null){
			LOG.info(B000052Constants.M00213.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2,prcsType )
					.replace(PDConstants.ERROR_MESSAGE_3, B000052Constants.WKLY_TABLE)
					.replace(PDConstants.ERROR_MESSAGE_4, porCd));
			
			CommonUtil.stopBatch();
		}
		 mnth = item.getId().getOrdrTakeBaseMnth();
		 weekNo = item.getId().getOrdrTakeBaseWkNo();
		
		 
		LOG.info("wkly process :"+mnth+" "+weekNo);
		ordrTkBsPrd = OrdrtkbsPrdCalc(mnth,weekNo);
		outputBean.setOrdrtkBasePd( ordrTkBsPrd);
		qryParamBean.setOrdrTkBsPrd(ordrTkBsPrd);
		
		pltLnSmry = parameterMstRepository.fetchPlntlnSmry(porCd);
		if(pltLnSmry==null ||pltLnSmry.isEmpty() ){
			LOG.info(PDMessageConsants.M00160.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2,B000052Constants.PLTNLINESUMMARY )
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4, B000052Constants.PARAMTRTABLE));
			CommonUtil.stopBatch();
			}
		
		outputBean.setPltLnSmry(pltLnSmry);
		qryParamBean.setPlntLneSummary(pltLnSmry);
		
		
		List<Object[]> valList = new ArrayList<Object[]>();
		valList = parameterMstRepository.fetchPlntln(porCd);
		
		if(valList==null || valList.isEmpty()){
			LOG.info(B000052Constants.M00315.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
					.replace(PDConstants.ERROR_MESSAGE_2,B000052Constants.PLNTCDCLASS )
					.replace(PDConstants.ERROR_MESSAGE_3, porCd)
					.replace(PDConstants.ERROR_MESSAGE_4, B000052Constants.PARAMTRTABLE));
			CommonUtil.stopBatch();
		}
		
		 for (Object[] rowObject : valList) {
			 if(rowObject[0]!=null && rowObject[1]!=null){
				 plntCd = rowObject[0].toString();
				 lnCls = rowObject[1].toString();
			 }else{
				 LOG.error(M00003);
					CommonUtil.stopBatch();
			 }
		 }
		 outputBean.setPlntCd(plntCd);
		 outputBean.setLnCls(lnCls);
		 
		 qryParamBean.setPlntCd(plntCd);
		 qryParamBean.setLineClass(lnCls);
		 
		 cnstntDay = parameterMstRepository.fetchCnstntDay(porCd);
		 
		 if(cnstntDay==null || cnstntDay.isEmpty()){
				LOG.info(B000052Constants.M00315.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
						.replace(PDConstants.ERROR_MESSAGE_2,B000052Constants.CONSTANTDAY )
						.replace(PDConstants.ERROR_MESSAGE_3, porCd)
						.replace(PDConstants.ERROR_MESSAGE_4, B000052Constants.PARAMTRTABLE));
				CommonUtil.stopBatch();
			} 
		 
		 outputBean.setCnstntDay(cnstntDay);
		 qryParamBean.setCnstDayNo(cnstntDay);
		 
		 
		 //P0003.2
		 featTypeCd = parameterMstRepository.fetchFeatCdInfo(porCd,prcsType);
		 if(featTypeCd==null){
		 LOG.error(M00003);
			CommonUtil.stopBatch();
		 }
		// P3.2
		   List<Object[]> featCdInfoList = new ArrayList<Object[]>(); 
		 featCdInfoList = trnLtstMstShdlRepository.fetchByrLvlFeatCdInfoWkly(qryParamBean);
		 
		 if(featCdInfoList.isEmpty()){
			 LOG.info(B000052Constants.M00158.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
						.replace(PDConstants.ERROR_MESSAGE_2,B000052Constants.WKLYFEATMSG )
						.replace(PDConstants.ERROR_MESSAGE_3, porCd)
						.replace(PDConstants.ERROR_MESSAGE_4, B000052Constants.LATESTMASTER));
				CommonUtil.stopBatch();
		 }
		 
for(Object[] ftTypCdAry :featCdInfoList ){
			 
			 int tempProdMnth = Integer.valueOf((String)ftTypCdAry[1]);
			int tempProdWk = Integer.valueOf(String.valueOf(ftTypCdAry[2]));
			if(tempProdMnth>maxProdMnth){
				maxProdMnth=tempProdMnth;
			}
			if(tempProdWk>maxProdWk){
				maxProdWk=tempProdWk;
			}
			 	 
		 }
		 outputBean.setMaxProdMnth(maxProdMnth);
		 outputBean.setMaxProdWk(maxProdWk);
		 
		//P4
		 boolean flag =findFeatCdDuplicate(featCdInfoList);
		
		return outputBean;
	}
	
	public String OrdrtkbsPrdCalc(String ordrTkBsMnth,String wkNo){
		
		ordrTkBsPrd = ordrTkBsMnth.concat(wkNo);
		return ordrTkBsPrd;
		
	}
	
	




public boolean findFeatCdDuplicate(List<Object[]> valList){
	LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
	List<Object[]> cmnFetchList = new ArrayList<Object[]>();
	StringBuilder fetchquery = new StringBuilder();
	for (Object[] rowObject : valList) {
		 porCd = (String)rowObject[0];
		 prodMnth = (String)rowObject[1];
		 prodWkNo = String.valueOf(rowObject[2]);
		 oseiId = (String)rowObject[3];
		 featCd = (String)rowObject[4];
		 featTypeCd = (String)rowObject[5];
		 ocfFrameCd = (String)rowObject[6];
		 carSrs = (String)rowObject[7];
		 byrGrpCd = (String)rowObject[8];
		 featTypeCd2 = (String)rowObject[9];
		 plntCd = (String)rowObject[10];
		 lnCls = (String)rowObject[11];
		 cnstntDay = (String)rowObject[12];
		// if(){
		
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
		qryParamBean.setCnstDayNo(cnstntDay);
		
		cmnFetchList = byrWklyOcfUsgRepository.findDuplicateMnthly(qryParamBean);
		
		LOG.info("cmnFetchList size"+ cmnFetchList.size());
		 
		 if (cmnFetchList.isEmpty()||cmnFetchList==null){
			 
			 int resultInsert = byrWklyOcfUsgRepository.insrtWklyocfUsg(qryParamBean);
			
			 if(resultInsert==1){
				 LOG.info(PDMessageConsants.M00163.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
							.replace(PDConstants.ERROR_MESSAGE_2,PDConstants.INSERTED )
							.replace(PDConstants.ERROR_MESSAGE_3, B000052Constants.BYRWKLYOCFUSAGE));
					
			 }	else
			 {
				 
					 LOG.error(PDMessageConsants.M00164.replace(PDConstants.ERROR_MESSAGE_1,PDConstants.B000052)
								.replace(PDConstants.ERROR_MESSAGE_2,PDConstants.INSERTED )
								.replace(PDConstants.ERROR_MESSAGE_3, B000052Constants.BYRWKLYOCFUSAGE));
						CommonUtil.stopBatch();
				 
			 }
		 }
		 LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		 
	}
	
	return true;
	
}

public String getPorCd() {
	return porCd;
}

public void setPorCd(String porCd) {
	this.porCd = porCd;
}

public String getPrcsType() {
	return prcsType;
}

public void setPrcsType(String prcsType) {
	this.prcsType = prcsType;
}

}
