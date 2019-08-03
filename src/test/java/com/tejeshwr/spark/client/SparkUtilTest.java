package com.tejeshwr.spark.client;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tejeshwr.spark.client.SparkTable;
import com.tejeshwr.spark.client.util.SparkSessionManager;
import com.tejeshwr.spark.client.util.SparkUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SparkSessionManager.class, SparkUtil.class })
public class SparkUtilTest {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Test
	public void testListAllFiles() {
		PowerMockito.mockStatic(SparkUtil.class);
		List<File> listOfFiles = new ArrayList<File>();
		try {
			listOfFiles.add(testFolder.newFile("File1.txt"));
			listOfFiles.add(testFolder.newFile("File2.txt"));
			listOfFiles.add(testFolder.newFile("File3.txt"));
			File tempFolder = testFolder.newFolder("folder");
			PowerMockito.when(SparkUtil.listAllFiles(tempFolder)).thenReturn(listOfFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readJSONTest() throws IOException, ParseException {
		SparkTable mockSparkTable = PowerMockito.mock(SparkTable.class);
		PowerMockito.mockStatic(SparkUtil.class);
		StringBuilder sb = new StringBuilder();
		File tempFile = testFolder.newFile("FileNew.txt");
		PowerMockito.when(SparkUtil.readJSON(tempFile.getName(), mockSparkTable)).thenReturn(sb);
	}

}
