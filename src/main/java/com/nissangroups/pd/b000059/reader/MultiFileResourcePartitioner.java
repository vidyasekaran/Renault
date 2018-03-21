package com.nissangroups.pd.b000059.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;

public class MultiFileResourcePartitioner extends MultiResourceItemReader<Object> {
	private static final Log LOG = LogFactory
			.getLog(MultiFileResourcePartitioner.class.getName());
	
	@Override
	public void update(final ExecutionContext pExecutionContext)
			throws ItemStreamException {
		super.update(pExecutionContext);
		if (getCurrentResource() != null
				&& getCurrentResource().getFilename() != null) {
			LOG.info(" File Name:" + getCurrentResource().getFilename());
		}
	}
}