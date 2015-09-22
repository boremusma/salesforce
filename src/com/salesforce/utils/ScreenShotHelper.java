package com.salesforce.utils;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenShotHelper {

	private static Logger logger = Logger.getLogger(ScreenShotHelper.class);
	private static String screenshotDirection = "screenshots/";

	/**
	 * take screen shot of entire page.
	 * 
	 * @param driver
	 * @param className
	 * @param methodName
	 */
	public static void shotEntirePage(Object driver, String className,
			String methodName) {

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(screenshotDirection + className
					+ "." + methodName + ".jpg"));
			logger.info("Screenshot saved to " + screenshotDirection + className
					+ "." + methodName + ".jpg");
		} catch (IOException e) {
			logger.info("Fail to create screenshot file: " + screenshotDirection
					+ "/n" + e.getMessage());

		}
	}

}