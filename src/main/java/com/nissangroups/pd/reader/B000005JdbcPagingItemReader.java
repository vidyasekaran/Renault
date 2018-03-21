/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000005
 * Module          :Cretae Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z010343(RNTBCI)               New Creation
 *
 */

package com.nissangroups.pd.reader;

import static com.nissangroups.pd.util.B00005QueryConstants.*;
import static com.nissangroups.pd.util.PDConstants.*;
import static com.nissangroups.pd.util.PDMessageConsants.*;
import static com.nissangroups.pd.util.CommonUtil.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.nissangroups.pd.exception.PdApplicationNonFatalException;
import com.nissangroups.pd.processor.B000005Processor;
import com.nissangroups.pd.util.PDConstants;
import com.nissangroups.pd.util.PDMessageConsants;

/**
 * The Class B000005JdbcPagingItemReader.
 *
 * @param <T>
 *            the generic type
 */
public class B000005JdbcPagingItemReader<T> extends AbstractPagingItemReader<T>
		implements InitializingBean {

	/** Constant LOG. */
	private static final Log LOG = LogFactory
			.getLog(B000005JdbcPagingItemReader.class.getName());

	/** Variable step execution. */
	private StepExecution stepExecution;

	/** Variable job execution. */
	private JobExecution jobExecution;

	/** Variable job parameter. */
	private JobParameters jobParameter;

	/** Variable step context. */
	private ExecutionContext stepContext;

	/** Variable por. */
	String por = null;

	/** Variable por. */
	String updateFlg = null;

	/** Variable batch. */
	String batch = null;

	/** Variable current index. */
	private int currentIndex = 0;

	/** Variable data source. */
	private DataSource dataSource;

	/** Variable query provider. */
	private PagingQueryProvider queryProvider;

	/** Variable parameter values. */
	private Map<String, Object> parameterValues;

	/** Variable named parameter jdbc template. */
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/** Variable row mapper. */
	private RowMapper<T> rowMapper;

	/** Variable first page sql. */
	private String firstPageSql;

	/** Variable remaining pages sql. */
	private String remainingPagesSql;

	/** Variable start after values. */
	private Map<String, Object> startAfterValues;

	/** Variable previous start after values. */
	private Map<String, Object> previousStartAfterValues;

	/** Variable fetch size. */
	private int fetchSize = VALUE_NOT_SET;

	/** Variable msgBatchId. */
	String msgBatchId = null;

	/**
	 * Instantiates a new b000005 jdbc paging item reader.
	 */
	public B000005JdbcPagingItemReader() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		setName(ClassUtils.getShortName(B000005JdbcPagingItemReader.class));
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.dataSource = dataSource;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Sets the fetch size.
	 *
	 * @param fetchSize
	 *            the new fetch size
	 */
	public void setFetchSize(int fetchSize) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.fetchSize = fetchSize;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Sets the query provider.
	 *
	 * @param queryProvider
	 *            the new query provider
	 */
	public void setQueryProvider(PagingQueryProvider queryProvider) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.queryProvider = queryProvider;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Sets the row mapper.
	 *
	 * @param rowMapper
	 *            the new row mapper
	 */
	public void setRowMapper(RowMapper<T> rowMapper) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.rowMapper = rowMapper;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Sets the parameter values.
	 *
	 * @param parameterValues
	 *            the parameter values
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.parameterValues = parameterValues;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Before step.
	 *
	 * @param stepExecution
	 *            the step execution
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		this.stepExecution = stepExecution;
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.item.database.AbstractPagingItemReader#
	 * afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Assert.notNull(dataSource);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		if (fetchSize != VALUE_NOT_SET) {
			jdbcTemplate.setFetchSize(fetchSize);
		}
		jdbcTemplate.setMaxRows(getPageSize());
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				jdbcTemplate);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.database.AbstractPagingItemReader#doReadPage
	 * ()
	 */
	@Override
	protected void doReadPage() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		jobExecution = this.stepExecution.getJobExecution();
		jobParameter = jobExecution.getJobParameters();
		stepContext = jobExecution.getExecutionContext();
		batch = jobParameter.getString(PRMTR_BATCH);
		por = jobParameter.getString(PRMTR_POR);
		updateFlg = jobParameter.getString(PRMTR_UPDATE_FLAG);

		if (batch.equals(BATCH000004)) {
			msgBatchId = BATCH4_ID;
		} else {
			msgBatchId = BATCH5_ID;
		}

		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		Map<String, Object> values = new HashMap<String, Object>();
		SqlPagingQueryProviderFactoryBean queryProvider1 = new SqlPagingQueryProviderFactoryBean();
		setParameters(queryProvider1, values);

		try {
			setQueryProvider((PagingQueryProvider) queryProvider1.getObject());
		} catch (Exception e) {
			LOG.info(READING_EXCEPTION_MSG, e);
		}

		this.firstPageSql = queryProvider.generateFirstPageQuery(getPageSize());
		this.remainingPagesSql = queryProvider
				.generateRemainingPagesQuery(getPageSize());

		List<?> query = defaultCompare();

		Collection<T> result = (Collection<T>) query;
		results.addAll(result);
		currentIndex++;

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Default compare.
	 *
	 * @return the list
	 */
	@SuppressWarnings(RAW_TYPES)
	private List defaultCompare() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		List<?> query = null;
		PagingRowMapper rowCallback = new PagingRowMapper();

		if (getPage() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(SQL_USED_FIRST_PAGE_MSG + firstPageSql);
			}
			if (parameterValues != null && !parameterValues.isEmpty()) {
				if (this.queryProvider.isUsingNamedParameters()) {
					query = namedParameterJdbcTemplate
							.query(firstPageSql,
									getParameterMap(parameterValues, null),
									rowCallback);
				} else {
					query = getJdbcTemplate().query(firstPageSql,
							getParameterList(parameterValues, null).toArray(),
							rowCallback);
				}
			} else {
				query = getJdbcTemplate().query(firstPageSql, rowCallback);
			}

		} else {
			previousStartAfterValues = startAfterValues;
			if (logger.isDebugEnabled()) {
				logger.debug(SQL_USED_REM_PAGES_MSG + remainingPagesSql);
			}
			if (this.queryProvider.isUsingNamedParameters()) {
				query = namedParameterJdbcTemplate.query(remainingPagesSql,
						getParameterMap(parameterValues, startAfterValues),
						rowCallback);
			} else {
				query = getJdbcTemplate().query(
						remainingPagesSql,
						getParameterList(parameterValues, startAfterValues)
								.toArray(), rowCallback);
			}
		}

		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return query;

	}

	/**
	 * Sets the parameters.
	 *
	 * @param queryProvider1
	 *            the query provider1
	 * @param values
	 *            the values
	 */
	private void setParameters(
			SqlPagingQueryProviderFactoryBean queryProvider1,
			Map<String, Object> values) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);

		String updateFlg = jobParameter.getString(PRMTR_UPDATE_FLAG);
		String logQry = null;
		List<Object> prodStageCdLst = (List<Object>) stepContext
				.get(PRMTR_PRODUCTION_STAGE_CODE);
		String abolishDate = (String) stepContext.get(PRMTR_BASE_PERIOD_WEEKLY);

		queryProvider1.setDataSource(this.dataSource);
		queryProvider1.setDatabaseType(PRMTR_ORACLE);
		String prodStageCd = convertListToStringIn(prodStageCdLst);
		if (batch.equals(BATCH000005)) {
			queryProvider1.setSelectClause(END_ITEM_SELECT.toString()
					+ END_ITEM_SELECT_B5.toString());
			queryProvider1.setFromClause(checkUpdateFlg(updateFlg));
			queryProvider1
					.setWhereClause(endItemWhereQuery(batch, prodStageCd));
			queryProvider1.setSortKey(PRMTR_OSEI_ADPT_DATE);
			values.put(BATCH_ID, BATCH_5_ID);
			logQry = END_ITEM_SELECT.toString() + END_ITEM_SELECT_B5.toString();
			LOG.info("Select Query :::" + logQry + checkUpdateFlg(updateFlg)
					+ endItemWhereQuery(batch, prodStageCd));
			LOG.info("Set Parameter : " + BATCH_ID + " :" + BATCH_5_ID);
		} else {
			queryProvider1.setSelectClause(END_ITEM_SELECT.toString()
					+ END_ITEM_SELECT_B4.toString());
			logQry = END_ITEM_SELECT.toString() + END_ITEM_SELECT_B4.toString();
			queryProvider1.setFromClause(checkUpdateFlg(updateFlg));
			queryProvider1
					.setWhereClause(endItemWhereQuery(batch, prodStageCd));
			queryProvider1.setSortKey(PRMTR_EI_MIN_ADPT_DATE);
			values.put(BATCH_ID, BATCH_4_ID);
			LOG.info("Select Query :::" + logQry + checkUpdateFlg(updateFlg)
					+ endItemWhereQuery(batch, prodStageCd));
			LOG.info("Set Parameter : " + BATCH_ID + " :" + BATCH_4_ID);

		}

		values.put(PRMTR_PORCD, por);
		values.put(PRMTR_ABLSHDATE, abolishDate);
		LOG.info("Set Parameter : " + PRMTR_PORCD + " :" + por);
		LOG.info("Set Parameter : " + PRMTR_ABLSHDATE + " :" + abolishDate);

		setParameterValues(values);
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.item.support.
	 * AbstractItemCountingItemStreamItemReader
	 * #update(org.springframework.batch.item.ExecutionContext)
	 */
	@Override
	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (isSaveState()) {
			if (isAtEndOfPage() && startAfterValues != null) {
				/** restart on next page */
				executionContext.put(getExecutionContextKey(START_AFTER_VALUE),
						startAfterValues);
			} else if (previousStartAfterValues != null) {
				/** restart on current page */
				executionContext.put(getExecutionContextKey(START_AFTER_VALUE),
						previousStartAfterValues);
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Checks if is at end of page.
	 *
	 * @return true, if is at end of page
	 */
	private boolean isAtEndOfPage() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return getCurrentItemCount() % getPageSize() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.item.support.
	 * AbstractItemCountingItemStreamItemReader
	 * #open(org.springframework.batch.item.ExecutionContext)
	 */
	@Override
	@SuppressWarnings(UNCHECKED)
	public void open(ExecutionContext executionContext) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (isSaveState()) {
			startAfterValues = (Map<String, Object>) executionContext
					.get(getExecutionContextKey(START_AFTER_VALUE));

			if (startAfterValues == null) {
				startAfterValues = new LinkedHashMap<String, Object>();
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.database.AbstractPagingItemReader#doJumpToPage
	 * (int)
	 */
	@Override
	protected void doJumpToPage(int itemIndex) {
		/*
		 * Normally this would be false (the startAfterValue is enough
		 * information to restart from.
		 */
		/**
		 * this is dead code, startAfterValues is never null - see
		 * #open(ExecutionContext)
		 */
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		if (startAfterValues == null && getPage() > 0) {

			String jumpToItemSql = queryProvider.generateJumpToItemQuery(
					itemIndex, getPageSize());

			if (logger.isDebugEnabled()) {
				logger.debug(SQL_USED_FOR_JUMPING + jumpToItemSql);
			}

			if (this.queryProvider.isUsingNamedParameters()) {
				startAfterValues = namedParameterJdbcTemplate.queryForMap(
						jumpToItemSql, getParameterMap(parameterValues, null));
			} else {
				startAfterValues = getJdbcTemplate().queryForMap(jumpToItemSql,
						getParameterList(parameterValues, null).toArray());
			}
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
	}

	/**
	 * Gets the parameter map.
	 *
	 * @param values
	 *            the values
	 * @param sortKeyValues
	 *            the sort key values
	 * @return the parameter map
	 */
	private Map<String, Object> getParameterMap(Map<String, Object> values,
			Map<String, Object> sortKeyValues) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();
		if (values != null) {
			parameterMap.putAll(values);
		}
		if (sortKeyValues != null && !sortKeyValues.isEmpty()) {
			for (Map.Entry<String, Object> sortKey : sortKeyValues.entrySet()) {
				parameterMap.put(UNDER_SCORE + sortKey.getKey(),
						sortKey.getValue());
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(USING_PARAMETER_MAP + parameterMap);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return parameterMap;
	}

	/**
	 * Gets the parameter list.
	 *
	 * @param values
	 *            the values
	 * @param sortKeyValue
	 *            the sort key value
	 * @return the parameter list
	 */
	private List<Object> getParameterList(Map<String, Object> values,
			Map<String, Object> sortKeyValue) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		SortedMap<String, Object> sm = new TreeMap<String, Object>();
		if (values != null) {
			sm.putAll(values);
		}
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.addAll(sm.values());
		if (sortKeyValue != null && !sortKeyValue.isEmpty()) {
			List<Map.Entry<String, Object>> keys = new ArrayList<Map.Entry<String, Object>>(
					sortKeyValue.entrySet());

			for (int i = 0; i < keys.size(); i++) {
				for (int j = 0; j < i; j++) {
					parameterList.add(keys.get(j).getValue());
				}

				parameterList.add(keys.get(i).getValue());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(USING_PARAMETEER_LIST + parameterList);
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return parameterList;
	}

	/**
	 * The Class PagingRowMapper.
	 */
	private class PagingRowMapper implements RowMapper<T> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
		 * int)
		 */
		@Override
		public T mapRow(ResultSet rs, int rowNum) throws SQLException {
			LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
			startAfterValues = new LinkedHashMap<String, Object>();
			for (Map.Entry<String, Order> sortKey : queryProvider.getSortKeys()
					.entrySet()) {
				startAfterValues.put(sortKey.getKey(),
						rs.getObject(sortKey.getKey()));
			}
			LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
			return rowMapper.mapRow(rs, rowNum);
		}
	}

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbc template
	 */
	private JdbcTemplate getJdbcTemplate() {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		return (JdbcTemplate) namedParameterJdbcTemplate.getJdbcOperations();
	}

	/**
	 * Check update flg.
	 *
	 * @param updateFlg
	 *            the update flg
	 * @return the string
	 */
	public String checkUpdateFlg(String updateFlg) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		String fromClause = END_ITEM_FROM_B5.toString();
		if (updateFlg.equals(UPDATE_FLG_YES)) {
			fromClause += FROM_QUERY_UPDATE_FLG.toString();
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return fromClause;

	}

	/**
	 * After Step Spring framework method used to print the transaction in the
	 * log file.
	 *
	 * @param stepExecution
	 *            the step execution
	 * @return the exit status
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info(DOLLAR + INSIDE_METHOD + DOLLAR);
		LOG.info(STAR + STEP_ID + stepExecution.getId() + STAR);
		LOG.info(READ_COUNT + stepExecution.getReadCount());
		if (stepExecution.getReadCount() == 0) {
			if (updateFlg.equals(UPDATE_FLG_YES)) {
				LOG.info(msgBatchId + B4_EI_UPDATE_MES + por + B4_BATCH_STOP);
			} else {
				LOG.info(msgBatchId + B4_EI_MES + por + B4_BATCH_STOP);
			}

			return ExitStatus.FAILED;
		}
		LOG.info(DOLLAR + OUTSIDE_METHOD + DOLLAR);
		return ExitStatus.COMPLETED;

	}

}
