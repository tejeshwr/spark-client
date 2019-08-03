package com.tejeshwr.spark.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.SparkSession;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tejeshwr.spark.client.SparkTableDriver;
import com.tejeshwr.spark.client.SparkTableDriverImpl;
import com.tejeshwr.spark.client.SparkTableFactory;
import com.tejeshwr.spark.client.util.SparkSessionManager;
import com.tejeshwr.spark.client.util.SparkUtil;

import junit.framework.Assert;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SparkTableDriver.class, SparkUtil.class, SparkTableDriverImpl.class,SparkSession.class })
public class SparkTableDriverImplTest {
	private SparkTableDriverImpl sparkTableDriverImpl = PowerMockito.mock(SparkTableDriverImpl.class) ;
	

	@Mock
	private SparkSessionManager sparkSessionManager;
	  
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	
	@Before
	    public void setup() throws IOException {
		 List<File> listOfFiles = new ArrayList<File>();
		 listOfFiles.add(testFolder.newFile("File1.txt"));
			listOfFiles.add(testFolder.newFile("File2.txt"));
		 sparkTableDriverImpl = PowerMockito.spy(new SparkTableDriverImpl());
	        try {
	        	PowerMockito.doNothing().when(sparkTableDriverImpl).createTable(listOfFiles);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	
	
	@Test(expected = IllegalArgumentException.class)
	public void createTableTests() throws Exception {
		PowerMockito.mockStatic(SparkUtil.class);
		PowerMockito.mockStatic(SparkTableDriverImpl.class);
		PowerMockito.spy(SparkSession.class);
		SparkTableDriver sparkTableDriver = SparkTableFactory.getInstance();
		sparkSessionManager = PowerMockito.mock(SparkSessionManager.class);
		PowerMockito.whenNew(SparkSessionManager.class).withNoArguments().thenReturn(sparkSessionManager);
		List<File> listOfFiles = new ArrayList<File>();
		try {
			listOfFiles.add(testFolder.newFile("File1.txt"));
			listOfFiles.add(testFolder.newFile("File2.txt"));
			PowerMockito.when(sparkSessionManager.getInstance()).thenReturn(sparkSessionManager);
			sparkTableDriver.createTable(listOfFiles); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
