package com.tejeshwr.spark.client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import com.tejeshwr.spark.client.SparkTable;

import static org.mockito.Mockito.mock;

public class SparkTableTest {

	@Test
	public void test() {
		SparkTable sparkTable = mock(SparkTable.class);
		PowerMockito.when(sparkTable.getTableName()).thenReturn("DAILY_FAF_ADMIN");
		PowerMockito.when(sparkTable.getHdfsPath()).thenReturn("/edm/data/bi/finalAggregated/Daily/DAILY_FAF_ADMIN");
		PowerMockito.when(sparkTable.getSchema()).thenReturn(new StringBuilder());
		
	}
}
