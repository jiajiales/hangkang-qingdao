package com.hotcomm.prevention.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private static final ThreadLocal<String> datasourceHolder = new ThreadLocal<>();

	@Override
	public Object determineCurrentLookupKey() {
		return datasourceHolder.get();
	}

	static void setDataSource(String sourceName) {
		datasourceHolder.set(sourceName);
	}

	static void clearDataSource() {
		datasourceHolder.remove();
	}
}
