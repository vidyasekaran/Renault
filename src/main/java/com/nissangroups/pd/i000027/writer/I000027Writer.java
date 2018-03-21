package com.nissangroups.pd.i000027.writer;

import static com.nissangroups.pd.util.PDConstants.DOLLAR;
import static com.nissangroups.pd.util.PDConstants.INSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.OUTSIDE_METHOD;
import static com.nissangroups.pd.util.PDConstants.PERSISTENCE_NAME;
import static com.nissangroups.pd.util.PDConstants.UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;

import com.nissangroups.pd.exception.PdApplicationException;
import com.nissangroups.pd.model.CmnInterfaceData;
import com.nissangroups.pd.model.MstPrmtr;
import com.nissangroups.pd.repository.ParameterMstRepository;
import com.nissangroups.pd.util.CommonUtil;
import com.nissangroups.pd.util.IFConstants;
import com.nissangroups.pd.util.IfCommonUtil;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

public class I000027Writer implements ItemWriter<CmnInterfaceData> {

	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(I000027Writer.class
			.getName());

	/** Variable entity manager. */
	@PersistenceContext(unitName = PERSISTENCE_NAME)
	private EntityManager entityManager;

	/** Variable common utility. */
	@Autowired(required = false)
	IfCommonUtil commonutility;

	/** Variable jobExec. */
	private JobExecution jobExec;

	/*
	 * private String ifFileId;
	 * 
	 * private String porCd;
	 */

	/** Variable Parameter Repository. */
	@Autowired(required = false)
	private ParameterMstRepository prmtrMstRepo;

	/**
	 * Marks a method to be called before a Step is executed, which comes after
	 * a StepExecution is created and persisted, but before the first item is
	 * read.
	 * 
	 * @param stepExecution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExec = stepExecution.getJobExecution();
		/*
		 * this.ifFileId = jobExec.getJobParameters().getString(
		 * IFConstants.INTERFACE_FILE_ID);
		 */LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * This method is use for write the CmnInterfaceData data and process.
	 * Process the supplied data element. Will not be called with any null items
	 * in normal operation.
	 * 
	 * @param items
	 * @throws Exception
	 *             if there are errors(Unable to obtain a transactional Entity
	 *             Manager).
	 */
	@Override
	public void write(List<? extends CmnInterfaceData> items) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException(
					UNABLE_TO_OBTAIN_TRANS_ENTITY_MGR_MSG);
		}
		if (!items.isEmpty()) {
			for (CmnInterfaceData cmnInterfaceData : items) {
				boolean ocfLmtFlag = byrGrpOCFLmtChk(
						cmnInterfaceData.getCol1(),
						Long.parseLong(cmnInterfaceData.getId().getSeqNo() + ""));
				if (ocfLmtFlag) {
					
/*					long rowCount = commonutility.getRowCount() + 1;
					cmnInterfaceData.getId().setRowNo(rowCount);*/
					entityManager.merge(cmnInterfaceData);
					
					/*commonutility.setRowCount(rowCount);*/
				}
			}
			entityManager.flush();
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		}
	}

	private boolean byrGrpOCFLmtChk(String porCd, long seqNo) throws Exception {

		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String ifFileId = jobExec.getJobParameters().getString(
				IFConstants.INTERFACE_FILE_ID);
		List<MstPrmtr> resultList = prmtrMstRepo.fetchValue(
				PDConstants.OCF_UNLIMITED_OCF_CHECK, porCd);

		if (resultList != null && (resultList.size() != 0)) {
			if ("false".equalsIgnoreCase(resultList.get(0).getVal1())) {
				return false;
			}
		} else {
			String remarks = (PDMessageConsants.M00160
					.replace(PDConstants.ERROR_MESSAGE_1, ifFileId)
					.replace(PDConstants.ERROR_MESSAGE_2,
							PDConstants.OCF_UNLMTD_OCF_CHK_FLG)
					.replace(PDConstants.ERROR_MESSAGE_3, porCd).replace(
					PDConstants.ERROR_MESSAGE_4,
					PDConstants.MESSAGE_MST_PARAMETER));
			commonutility.setRemarks(remarks);
			commonutility.updateCmnFileHdr(ifFileId, seqNo,
					PDConstants.INTERFACE_FAILURE_STATUS, remarks);
			throw new PdApplicationException(
					"There is no OCF UNLIMITED OCF CHECK for this POR CD in PARAMETER_MST");			
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return true;
	}
}
