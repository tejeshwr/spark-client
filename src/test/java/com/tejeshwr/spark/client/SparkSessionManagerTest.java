package com.tejeshwr.spark.client;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tejeshwr.spark.client.util.SparkSessionManager;

/**
 * 
 * @author ejxxtjs
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SparkSessionManager.class })
public class SparkSessionManagerTest {

	private SparkSession sparkSession =PowerMockito.mock(SparkSession.class) ;
	private SparkSessionManager sparkSessionManager = PowerMockito.mock(SparkSessionManager.class);
	
	@Test(expected = IllegalArgumentException.class)
	public void test() throws Exception {

		PowerMockito.spy(SparkSessionManager.class);
		PowerMockito.when(SparkSessionManager.getInstance()).thenReturn(sparkSessionManager);
		PowerMockito.when(sparkSessionManager.getSession()).thenReturn(sparkSession);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullOfSessionManager() throws Exception {
		SparkSessionManager sparkSessionManager = null;
		PowerMockito.when(SparkSessionManager.getInstance()).thenReturn(sparkSessionManager);
	}
	
	@Test
	public void testNullOfSession() throws Exception {
		SparkSession sparkSession = null;
		PowerMockito.when(sparkSessionManager.getSession()).thenReturn(sparkSession);
	}


}
