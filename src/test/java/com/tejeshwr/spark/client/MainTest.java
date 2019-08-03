package com.tejeshwr.spark.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tejeshwr.spark.client.Main;
import com.tejeshwr.spark.client.SparkTableDriver;
import com.tejeshwr.spark.client.util.SparkSessionManager;
import com.tejeshwr.spark.client.util.SparkUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Main.class, SparkTableDriver.class, SparkUtil.class })
public class MainTest {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Test(expected = NullPointerException.class)
	public void testMain() {
		String[] args = null;
		try {
			Main.main(args);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testArgumentValidation() {
		String[] args = {"directory1","directory2"};
		
		try {
			
			Main.main(args);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDirectoryAsArgument() {
		String[] args = {"testFolder"};
		try {
			Main.main(args);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDirectoryAsOneArgument() {
		PowerMockito.mockStatic(Main.class);
		
		
	}
	
	
	

}
