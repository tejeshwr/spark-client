package com.tejeshwr.spark.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tejeshwr.spark.client.util.SparkClientConstants;
import com.tejeshwr.spark.client.util.SparkSessionManager;
import com.tejeshwr.spark.client.util.SparkUtil;

/**
 * 
 * @author ejxxtjs
 *
 */
public class SparkTableDriverImpl implements SparkTableDriver {

	public static SparkSessionManager sparkSessionManager;
	public static SparkSession sparkSession;
	public static SparkTable sparkTable = new SparkTable();
	private static Logger logger = LoggerFactory.getLogger(SparkTableDriverImpl.class);

	@Override
	public void createTable(List<File> fileList) throws IOException, ParseException {
		sparkSessionManager = SparkSessionManager.getInstance();
		sparkSession = sparkSessionManager.getSession();
		for (File file : fileList) {
			sparkTable.setSchema(SparkUtil.readJSON(file.getAbsolutePath(), sparkTable));
			try {
				SparkUtil.constructCreateTableCommand(sparkSession, sparkTable, sparkTable.getSchema(), file);
			} catch (IllegalArgumentException | AnalysisException e) {
				System.err.println("Table " + sparkTable.getTableName() + " creation failed!");
				System.err.println("Please check the schema file " + file.getAbsolutePath()
						+ " for the required fields namely Type, Table name, Directory and Fields(with column names and data types) ");
				logger.error("Table " + sparkTable.getTableName() + " creation failed!");
				logger.error("Please check the schema file " + file.getAbsolutePath()
						+ " for the required fields namely Type, Table name, Directory and Fields(with column names and data types) ");
			}
		}
	}

}
