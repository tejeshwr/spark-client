package com.tejeshwr.spark.client;

/**
 * 
 * @author ejxxtjs
 *
 */
public class SparkTableFactory {

	private static final SparkTableDriver sparkTableDriver = new SparkTableDriverImpl();

	public static SparkTableDriver getInstance() {
		return sparkTableDriver;
	}
}
