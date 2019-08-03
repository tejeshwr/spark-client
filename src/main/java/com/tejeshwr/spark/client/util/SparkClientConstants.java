package com.tejeshwr.spark.client.util;

/**
 * 
 * @author ejxxtjs
 *
 */
public interface SparkClientConstants {

	String SPACE_DELIMITER = " ";
	String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";
	String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS";
	String STORED_AS_PARQUET = "STORED AS PARQUET";
	String LOCATION = "LOCATION 'maprfs://";
	String SPARK_SQL_WAREHOUSE_DIR = "spark.sql.warehouse.dir";
	String SPARK_SQL_WAREHOURSE_DIR_VALUE = "maprfs:///user/mapr/hive";
	String SPARK_MASTER_YARN = "yarn";
	String CREATING_SPARK_TABLES = "Creating Spark Tables";
	String COLUMN_NAME = "name";
	String COLUMN_TYPE = "type";
	String COLUMN_SEPARATOR = ":";
	String COMMA_SEPARATOR = " , ";
	String OPEN_BRACES = "(";
	String CLOSE_BRACES = ")";
	String SPACE_SEPARATOR = "  ";
	String LOG_DIR = "/var/log/crs/spark/sparkClient.log";
	String DOT_SEPARATOR = ".";
}
