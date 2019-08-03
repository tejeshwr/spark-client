package com.tejeshwr.spark.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.spark.sql.SparkSession;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author ejxxtjs
 *
 */
public interface SparkTableDriver {
	/**
	 * 
	 * @param folder
	 * @throws IOException
	 * @throws ParseException
	 */
	void createTable(List<File> fileList) throws IOException, ParseException;

}
