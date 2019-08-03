package com.tejeshwr.spark.client.util;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author ejxxtjs
 *
 */
public class TableSchema {

	private String type;
	private String name;
	private List<Map<String, String>> fields;
	private String directory;
	private String database;

	public List<Map<String, String>> getFields() {
		return fields;
	}

	public void setFields(List<Map<String, String>> fields) {
		this.fields = fields;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
