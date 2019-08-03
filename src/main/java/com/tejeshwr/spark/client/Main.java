package com.tejeshwr.spark.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tejeshwr.spark.client.util.SparkClientConstants;
import com.tejeshwr.spark.client.util.SparkUtil;


/**
 * 
 * @author ejxxtjs
 *
 */

public class Main {

	private static SparkTableDriver sparkTableDriver = SparkTableFactory.getInstance();
	public static List<File> schemaList = new ArrayList<File>();
	private static Logger logger = LoggerFactory.getLogger(Main.class); 
	
	public static void main(String[] args) throws ParseException, IOException {

		try {

			if (args.length > 1) {
				System.out.println("Invalid number of arguments.");
			} else if (args.length == 1) {

				File arg = new File(args[0]);
				if (arg.isDirectory()) {
					if (SparkUtil.isNotEmptyDirectory(args[0])) {
						System.out.println("Please check the log file " + SparkClientConstants.LOG_DIR + " for progress...");
						logger.info("Processing the directory : " + args[0] + " for the structural validity of schemas");
						if (SparkUtil.isJSONValid(new File(args[0]))) {
							schemaList = SparkUtil.listAllFiles(new File(args[0]));
							sparkTableDriver.createTable(schemaList);
						} else {
							logger.error("Please verify the schemas files.");
							return;
						}
					} else {
						logger.error("The given directory is empty");
					}
				} else if (arg.isFile()) {
					System.out.println("Please check the log file " + SparkClientConstants.LOG_DIR + " for progress...");
					logger.info("Starting schema processing for the file : " + args[0]);
					schemaList.add(new File(args[0]));
					sparkTableDriver.createTable(schemaList);
				} else {
					logger.error("Please provide the schema directory or schema file as the argument.");
					return;
				}
			}

		} catch (ParseException | IOException e) {
			logger.error(e.getMessage());
		}

	}
}
