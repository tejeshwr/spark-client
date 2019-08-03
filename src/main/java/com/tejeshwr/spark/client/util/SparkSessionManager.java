package com.tejeshwr.spark.client.util;

import org.apache.spark.sql.SparkSession;

/**
 * 
 * @author ejxxtjs
 *
 */
public class SparkSessionManager {

	private static SparkSessionManager sparkSessionManager;
	private SparkSession sparkSession ;
	private SparkSessionManager(){
				sparkSession= SparkSession.
				 builder()
				.master(SparkClientConstants.SPARK_MASTER_YARN)
				.appName(SparkClientConstants.CREATING_SPARK_TABLES)
				.config(SparkClientConstants.SPARK_SQL_WAREHOUSE_DIR,SparkClientConstants.SPARK_SQL_WAREHOURSE_DIR_VALUE).enableHiveSupport().getOrCreate();
		
	}
	
	public static SparkSessionManager getInstance() {
		if (sparkSessionManager == null) {		      
			sparkSessionManager = new SparkSessionManager();
		}
		return sparkSessionManager;
	}
	
	public SparkSession getSession() {
		if(sparkSession == null){
			sparkSession = sparkSessionManager.getSession();
		}
		return sparkSession;
	}
	
}
