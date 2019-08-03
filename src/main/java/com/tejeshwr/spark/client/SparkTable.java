package com.tejeshwr.spark.client;

/**
 * 
 * @author ejxxtjs
 *
 */
public class SparkTable {

	private String tableName;
	private String hdfsPath;
	private StringBuilder schema;
	private String databaseName;

	public String getTableName() {
		return tableName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getHdfsPath() {
		return hdfsPath;
	}

	public void setHdfsPath(String hdfsPath) {
		this.hdfsPath = hdfsPath;
	}

	public StringBuilder getSchema() {
		return schema;
	}

	public void setSchema(StringBuilder schema) {
		this.schema = schema;
	}

}
