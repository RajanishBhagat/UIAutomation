
package com.automation.Driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.log4j.Logger;

import com.automation.Utilities.loggerLoad;

/**
 * @author Automation Team
 *
 */

public class DriverClass {

	public static WebDriver driver;
	static Logger log = loggerLoad.config("Driver");
	static String path;
	
	public static String set_path()
	{
		String os = System.getProperty("os.name");
		if (os.equalsIgnoreCase("linux")) 
		{
			System.out.println("Running Test On Ubuntu Machine");
			path = System.getProperty("user.dir") + "/../framework/src/com/automation/Driver/chromedriver";
		}
		else
		{
			System.out.println("Running Test On Window Machine");
			 path = System.getProperty("user.dir")
					+ "/../framework/src/com/automation/Driver/chromedriver.exe";
		}
		
		return path;
	}

	public static WebDriver registerDriver(String name) {
		if (name.equalsIgnoreCase("chrome")) {
			driver = chromeDriver();
		} else if (name.equalsIgnoreCase("firefox")) {
			driver = firefoxDriver();
		} else if (name.equalsIgnoreCase("headless")) {
			driver = headless();
		}

		return driver;
	}

	public static WebDriver chromeDriver() {
		try {
				 
			System.setProperty("webdriver.chrome.driver", set_path());
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			log.info("Chrome driver initialized");
		} catch (Exception e) {
			log.error("Error : " + e);
		}
		return driver;
	}

	public static WebDriver firefoxDriver() {
		try {
			System.setProperty("webdriver.gecko.driver", set_path());
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			log.info("Firefox driver initialized ");
		} catch (Exception e) {
			log.error("Error : " + e);
		}
		return driver;
	}

	public static WebDriver headless() {
		try {

			System.setProperty("webdriver.chrome.driver", set_path());
			// System.setProperty("webdriver.chrome.driver",
			// "/home/netstorm/Desktop/chromedriver");

			DesiredCapabilities caps = new DesiredCapabilities();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless"); // For head less feature we need chrome 65 and above version
			chromeOptions.addArguments("--no-sandbox"); // chromedriver should be 2.35 or latest
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--ignore-certificate-errors");
			chromeOptions.addArguments("--window-size=1325x744");

			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setCapability("acceptInsecureCert", true);
			caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			driver = new ChromeDriver(caps);
			log.info("Headless driver initiated");
		} catch (Exception e) {
			log.error("Error : " + e);
		}
		return driver;
	}

}
