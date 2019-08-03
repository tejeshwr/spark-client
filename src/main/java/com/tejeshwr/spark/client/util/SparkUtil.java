package com.tejeshwr.spark.client.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.tejeshwr.spark.client.SparkTable;
import com.tejeshwr.spark.client.SparkTableDriverImpl;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;
import org.json.*;

/**
 * 
 * @author ejxxtjs
 *
 */
public class SparkUtil {

	private static Logger logger = LoggerFactory.getLogger(SparkUtil.class);
	public static JSONParser parser = new JSONParser();
	
	
	/**
	 * 
	 * @param fileName
	 * @param sparkTable
	 * @return
	 * @throws IOException
	 * @throws ParseException To read the json schema file for creating the
	 *                        corresponding spark table.
	 */

	public static StringBuilder readJSON(String fileName, SparkTable sparkTable) throws IOException, ParseException {

		StringBuilder schema = new StringBuilder();
		Map<String, String> tableSchemaMap = new LinkedHashMap<String, String>();
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			TableSchema tableSchema = mapper.readValue(new File(fileName), TableSchema.class);
			Iterator<Map<String, String>> iterator = tableSchema.getFields().iterator();
			while (iterator.hasNext()) {
				Map<String, String> fieldMap = iterator.next();
				for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(SparkClientConstants.COLUMN_NAME)) {
						if (entry.getValue().contains(SparkClientConstants.COLUMN_SEPARATOR)) {
							String col_last_name = entry.getValue().substring(entry.getValue().lastIndexOf(':') + 1);
							keyList.add(col_last_name);
						} else {
							keyList.add(entry.getValue());
						}
					} else if (entry.getKey().equalsIgnoreCase(SparkClientConstants.COLUMN_TYPE)) {
						valueList.add(entry.getValue());
					}
				}
			}

			for (int i = 0; i < keyList.size() && i < valueList.size(); i++) {
				tableSchemaMap.put(keyList.get(i), valueList.get(i));
			}

			String prefix = "";
			schema.append(SparkClientConstants.OPEN_BRACES);
			for (Map.Entry<String, String> entry : tableSchemaMap.entrySet()) {
				schema.append(prefix);
				prefix = SparkClientConstants.COMMA_SEPARATOR;
				schema.append(entry.getKey() + SparkClientConstants.SPACE_SEPARATOR + entry.getValue());
			}
			schema.append(SparkClientConstants.CLOSE_BRACES);
			sparkTable.setSchema(schema);
			sparkTable.setDatabaseName(tableSchema.getDatabase());
			sparkTable.setTableName(tableSchema.getName());
			sparkTable.setHdfsPath(tableSchema.getDirectory());
		} catch (FileNotFoundException | UnrecognizedPropertyException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return sparkTable.getSchema();

	}

	public static List<File> listAllFiles(File folder) {
		List<File> listOfFiles = new ArrayList<File>();
		File[] fileNames = folder.listFiles();
		for (File file : fileNames) {
			if (file.isDirectory()) {
				listAllFiles(file);
			} else {
				listOfFiles.add(file);
			}
		}
		return listOfFiles;
	}

	public static boolean isNotEmptyDirectory(String directory) throws ParseException {
		File file = new File(directory);
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isJSONValid(File folder) throws ParseException {
		List<File> listOfFiles = SparkUtil.listAllFiles(folder);

		try {
			for (File file : listOfFiles) {
				logger.info("Processing file : " + file.getAbsolutePath());
				Object obj = parser.parse(new FileReader(file));
				JSONObject jsonObject = (JSONObject) obj;
				final ObjectMapper mapper = new ObjectMapper();
				mapper.readTree(jsonObject.toJSONString());
			}
		} catch (IOException e) {
			logger.error("Please check the json files.");
			return false;
		}
		return true;
	}

	public static boolean checkIfTableExists(String[] existingTableArray, String tableToLocate) {
		for (String table : existingTableArray) {
			if (table.equalsIgnoreCase(tableToLocate)) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkIfTableExistsInDB(String dbName, String tableName, SparkSession sparkSession) {
		if (sparkSession.catalog().tableExists(dbName, tableName)) {
			return true;
		}
		return false;
	}

	public static boolean checkIfDBExists(SparkSession sparkSession, String dbName) {
		if (sparkSession.catalog().databaseExists(dbName)) {
			return true;
		}
		return false;
	}

	public static void constructCreateTableCommand(SparkSession spark, SparkTable sparkTable, StringBuilder schema,
			File file) throws AnalysisException, IllegalArgumentException {

		try {
			StringBuilder createTable = new StringBuilder();
			createTable.append(SparkClientConstants.CREATE_TABLE + SparkClientConstants.SPACE_DELIMITER);
			createTable.append(sparkTable.getDatabaseName() + SparkClientConstants.DOT_SEPARATOR
					+ sparkTable.getTableName() + SparkClientConstants.SPACE_DELIMITER);
			createTable.append(schema + SparkClientConstants.SPACE_DELIMITER);
			createTable.append(SparkClientConstants.STORED_AS_PARQUET + SparkClientConstants.SPACE_DELIMITER);
			createTable.append(SparkClientConstants.LOCATION + sparkTable.getHdfsPath() + "'");

			if (checkIfDBExists(spark, sparkTable.getDatabaseName())) {
				logger.info(
						"Skipping creating the database " + sparkTable.getDatabaseName() + " as it already exists! ");
			} else {
				spark.sql(SparkClientConstants.CREATE_DATABASE + SparkClientConstants.SPACE_SEPARATOR
						+ sparkTable.getDatabaseName());
				logger.info("Database " + sparkTable.getDatabaseName() + " created successfully!");
			}

			if (checkIfTableExistsInDB(sparkTable.getDatabaseName(), sparkTable.getTableName(), spark)) {
				logger.info("Skipping creating the table " + sparkTable.getTableName()
						+ " as it already exists in database " + sparkTable.getDatabaseName());
			} else {
				spark.sql(createTable.toString());
				logger.info("Table " + sparkTable.getTableName() + " created successfully in database "
						+ sparkTable.getDatabaseName());
			}

		} catch (IllegalArgumentException e) {
			logger.error("Error while creating table " + sparkTable.getTableName());
			logger.error(e.getMessage());
		}
	}

}
