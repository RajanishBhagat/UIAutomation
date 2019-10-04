package com.automation.genrics;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.Driver.DriverClass;
import com.automation.TestCaseRunner.TestCaseDataReader;
import com.automation.TestCaseRunner.TestCaseDriveClass;
import com.automation.Utilities.loggerLoad;

/**
 * @author Automation Team
 *
 */

public class ActionsMethods {

	public static WebDriver driver;
	public WebElement ele;
	public List<WebElement> eles;
	public Select sl;
	static DriverClass driverclass = new DriverClass();
	static Logger log = loggerLoad.config("ActionsMethods");
	public static JavascriptExecutor js;
	public static Actions action;
	public static String ActualText;

	public static void registerDriver(String browser, File img_dest) {

		log.info("browser name : " + browser);
		driver = DriverClass.registerDriver(browser);
		action = new Actions(driver);
		js = (JavascriptExecutor) driver;
		log.info("driver : " + driver);

	}

	public void waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		});
	}

	public boolean browse(String url, String image_name) {
		try {
			//log.info(String.format("%s going to open", url));
			driver.get(url);
			waitForPageLoad();
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean navigateTo(String url, String image_name) {
		try {
			log.info(String.format("%s going to navigate", url));
			driver.navigate().to(url);
			waitForPageLoad();
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean closebrowser() throws Exception {
		try {
			log.info(String.format(" going to close browser"));
			driver.close();
			return true;
		} catch (Exception e) {
			log.error("Error :", e);
			return false;
		}

	}

	public boolean quitbrowser() {
		try {
			log.info(String.format(" going to quit browser"));
			driver.quit();
			return true;
		} catch (Exception e) {
			log.error("Error :", e);
			return false;
		}
	}

	public boolean click(String identifier, String identifiervalue, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.name(identifiervalue)));
				ele.click();

				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ByXpath":
				ele = new WebDriverWait(driver, 10)
						.until(ExpectedConditions.elementToBeClickable(By.xpath(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ById":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.id(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ByClassName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.className(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ByTagName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.tagName(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ByLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.linkText(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ByPartialLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			case "ByCssSelector":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifiervalue)));
				ele.click();
				log.info(String.format("sucessfully click on %s with value %s", identifier, identifiervalue));
				screenCapture(image_name);
				return true;
			// break;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			// break;
			}
			// waitForPageLoad();
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean sendKeys(String identifier, String identifiervalue, String values, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.name(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByXpath":
<<<<<<< ActionsMethods.java
				ele = new WebDriverWait(driver, 120).until(ExpectedConditions
						.elementToBeClickable(By.xpath(identifiervalue)));
	         		ele.sendKeys(values);
				log.info(String.format(
						"sucessfully SendKeys on %s with value %s",
						identifiervalue, values));
=======
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.xpath(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
>>>>>>> 1.27
				screenCapture(image_name);
				// break;
				return true;
			case "ById":
<<<<<<< ActionsMethods.java
				ele = new WebDriverWait(driver, 120).until(ExpectedConditions
						.elementToBeClickable(By.id(identifiervalue)));
			ele.sendKeys(values);
			//	ele.sendKeys(Integer.valueOf(values));
				log.info(String.format(
						"sucessfully SendKeys on %s with value %s",
						identifiervalue, values));
=======
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.id(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
>>>>>>> 1.27
				screenCapture(image_name);
				// break;
				return true;
			case "ByClassName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.className(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				// break;
				return true;
			case "ByTagName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.tagName(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				// break;
				return true;
			case "ByLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.linkText(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				// break;
				return true;
			case "ByPartialLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				// break;
				return true;
			case "ByCssSelector":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifiervalue)));
				ele.sendKeys(Keys.CONTROL+"a");
				ele.sendKeys(Keys.DELETE);
				ele.sendKeys(values);
				log.info(String.format("sucessfully SendKeys on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				// break;
				return true;
			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				// break;
				return false;

			}
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			log.error("identifier value is not correct");
			return false;
		}

	}

	public boolean keypress(String identifier, String identifiervalue, String values, String image_name) {
		try {
			HashMap<String, CharSequence> map = new HashMap<String, CharSequence>();
			map.put("enter", Keys.ENTER);
			map.put("up", Keys.UP);
			map.put("down", Keys.DOWN);
			map.put("control", Keys.CONTROL);
			map.put("page up", Keys.PAGE_UP);
			map.put("page down", Keys.PAGE_DOWN);
			map.put("shift", Keys.SHIFT);
			map.put("tab", Keys.TAB);

			switch (identifier) {
			case "ByName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.name(identifiervalue)));

				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.xpath(identifiervalue)));
				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			case "ById":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.id(identifiervalue)));

				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			case "ByClassName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.className(identifiervalue)));
				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			case "ByTagName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.tagName(identifiervalue)));
				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			case "ByLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.linkText(identifiervalue)));
				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			case "ByPartialLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifiervalue)));
				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			case "ByCssSelector":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifiervalue)));
				ele.sendKeys(map.get(values.toLowerCase()));
				log.info(String.format("Sucessfully Pressed " + values + "  key "));
				screenCapture(image_name);
				// break;
				return true;
			default:
				log.error(String.format("Not able to press key"));
				// break;
				return false;

			}
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Not able to press key \n");
			log.error("Error : ", e);
			return false;
		}

	}

	public boolean waitforVisiblity(String identifier, String identifiervalue, String values, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.name(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ById":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.id(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByClassName":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.className(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByTagName":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				new WebDriverWait(driver, 120)
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(identifiervalue)));
				log.info(String.format("sucessfully element visible on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean waitforClickability(String identifier, String identifiervalue, String values, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.name(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with value %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ById":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.id(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByClassName":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.className(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByTagName":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.tagName(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.linkText(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				new WebDriverWait(driver, Integer.parseInt(values))
						.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifiervalue)));
				log.info(String.format("sucessfully element clickable on %s with time %s", identifiervalue, values));
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public void screenCapture(String img_name) {
		try {
			Thread.sleep(3000);
			String destpath = TestCaseDataReader.img_testcase_dir + "\\" + img_name + ".png";
			System.out.println("destpath :" + destpath);
			TakesScreenshot tc = (TakesScreenshot) driver;
			File dest = new File(destpath);
			File src = tc.getScreenshotAs(OutputType.FILE);
			FileUtils.moveFile(src, dest);
			log.info(String.format("Screen Capture %s", destpath));
		} catch (Exception e) {
			log.error(e.fillInStackTrace());
			log.error("Error in capture screenshot");
		}
	}

	public boolean scrollonpage(String cordinate, String image_name) {
		try {
			js.executeScript("window.scrollBy(" + cordinate + ")");
			
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public java.util.Date getCurrentDateTime() {

		java.util.Date date = java.util.Calendar.getInstance().getTime();

		System.out.println(date);
		return date;
		// screenCapture(image_name);

	}

	public boolean validatetitle(String title, String image_name) {
		boolean data = driver.getTitle().equals(title);
		log.info(String.format("Page Title %s", driver.getTitle()));
		screenCapture(image_name);
		return data;
	}

	public boolean verifycontentonpage(String content, String image_name) {
		boolean data = driver.getPageSource().equals(content);
		log.info(String.format("Page Content exists %s", data));
		screenCapture(image_name);
		return data;
	}

	public boolean alertaccept(String image_name) {

		try {
			Alert alt = new WebDriverWait(driver, 60).until(ExpectedConditions.alertIsPresent());
			alt.accept();
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			log.error("Alert not Present");
			screenCapture(image_name);
			return false;
		}
	}

	public boolean alertdecline(String image_name) {
		try {
			Alert alt = new WebDriverWait(driver, 60).until(ExpectedConditions.alertIsPresent());
			alt.dismiss();
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			log.error("Alert not Present");
			screenCapture(image_name);
			return false;
		}
	}

	public boolean alertsendkeys(String data, String image_name) {
		try {
			Alert alt = new WebDriverWait(driver, 60).until(ExpectedConditions.alertIsPresent());
			alt.sendKeys(data);
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			log.error("Alert not Present");
			screenCapture(image_name);
			return false;
		}
	}

	public boolean getwindowhandles(String image_name) {
		try {
			Set<String> windows = driver.getWindowHandles();
			for (String window : windows) {
				driver.switchTo().window(window);
			}
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error in switch on latest window");
			return false;
		}
	}

	public boolean gotowindowhandlebyindex(int index, String image_name) {
		try {
			Set<String> windows = driver.getWindowHandles();
			System.out.println("windows : " + windows);
			System.out.println("window 1 : " + (String) windows.toArray()[index]);
			driver.switchTo().window((String) windows.toArray()[index]);
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error in switch on latest window");
			return false;
		}
	}

	public boolean frameswitch(String identifier, String identifierValue, String image_name) {
		try {
			// driver.switchTo().frame(framename);
			driver.switchTo().frame(driver.findElement(By.xpath(identifierValue)));
			screenCapture(image_name);
			return true;
		} catch (Exception e) {
			log.error("Error in switch on frame");
			screenCapture(image_name);
			return false;
		}
	}

	public boolean selectfromcheckbox(String identifier, String identifiervalue, String image_name) {
		try {

			switch (identifier) {
			case "ByName":
				ele = driver.findElement(By.name(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;
			case "ByXpath":
				ele = driver.findElement(By.xpath(image_name));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;

			case "ById":
				ele = driver.findElement(By.id(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;

			case "ByClassName":
				ele = driver.findElement(By.cssSelector(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;

			case "ByTagName":
				ele = driver.findElement(By.tagName(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				ele = driver.findElement(By.linkText(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				ele = driver.findElement(By.partialLinkText(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				ele = driver.findElement(By.cssSelector(identifiervalue));
				if (!ele.isSelected())
					ele.click();
				screenCapture(image_name);
				return true;
			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}

	}

	public boolean verifycontentforelement(String identifier, String identifiervalue, String content,
			String image_name) {
		try {
			boolean data = false;

			switch (identifier) {

			case "ByName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.name(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			case "ByXpath":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.xpath(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}

				return data;

			case "ById":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.id(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			case "ByClassName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.className(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			case "ByTagName":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.tagName(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			case "ByLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.linkText(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			case "ByPartialLinkText":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			case "ByCssSelector":
				ele = new WebDriverWait(driver, 120)
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector(identifiervalue)));
				data = ele.getText().toString().equals(content);
				screenCapture(image_name);
				if (data) {
					log.info(String.format("sucessfully validate content for element with %s content", content));
				} else {
					log.error(String.format("Verfication unsuccessfull as Expected - " + content
							+ " and Actual conetnt -" + ele.getText().toString() + " does not match"));
				}
				return data;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				screenCapture(image_name);
				break;
			}
			waitForPageLoad();
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
		}
		return false;

	}

	public boolean selectvaluefromlist(String identifier, String identifiervalue, String value, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				eles = driver.findElements(By.name(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ByXpath":
				eles = driver.findElements(By.xpath(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ById":
				eles = driver.findElements(By.id(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ByClassName":
				eles = driver.findElements(By.className(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ByTagName":
				eles = driver.findElements(By.tagName(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				eles = driver.findElements(By.linkText(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				eles = driver.findElements(By.partialLinkText(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				eles = driver.findElements(By.cssSelector(identifiervalue));
				for (int i = 0; i < eles.size(); i++) {
					if (eles.get(i).getText().toString().contains(value)) {
						eles.get(i).click();
					}
				}
				screenCapture(image_name);
				return true;
			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean selectfromdropdownbyindex(String identifier, String identifiervalue, String value,
			String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				eles = driver.findElements(By.name(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ByXpath":
				eles = driver.findElements(By.xpath(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ById":
				eles = driver.findElements(By.id(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ByClassName":
				eles = driver.findElements(By.className(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ByTagName":
				eles = driver.findElements(By.tagName(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				eles = driver.findElements(By.linkText(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				eles = driver.findElements(By.partialLinkText(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				eles = driver.findElements(By.cssSelector(identifiervalue));
				eles.get(Integer.parseInt(value)).click();
				screenCapture(image_name);
				return true;
			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean selectdatafromvisibletext(String identifier, String identifiervalue, String value,
			String image_name) {
		try {

			switch (identifier) {
			case "ByName":
				sl = new Select(driver.findElement(By.name(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ByXpath":
				sl = new Select(driver.findElement(By.xpath(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ById":
				sl = new Select(driver.findElement(By.id(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ByClassName":
				sl = new Select(driver.findElement(By.className(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ByTagName":
				sl = new Select(driver.findElement(By.tagName(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				sl = new Select(driver.findElement(By.linkText(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				sl = new Select(driver.findElement(By.partialLinkText(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				sl = new Select(driver.findElement(By.cssSelector(identifiervalue)));
				sl.selectByVisibleText(value);
				screenCapture(image_name);
				return true;
			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean uploadfile(String identifier, String identifiervalue, String value, String image_name) {
		try {

			switch (identifier) {
			case "ByName":
				driver.findElement(By.name(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ByXpath":
				driver.findElement(By.xpath(identifiervalue)).sendKeys(value);
				//ele.sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ById":
				driver.findElement(By.id(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ByClassName":
				driver.findElement(By.className(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ByTagName":
				driver.findElement(By.tagName(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				driver.findElement(By.linkText(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				driver.findElement(By.partialLinkText(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				driver.findElement(By.cssSelector(identifiervalue)).sendKeys(value);
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}

		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}
	}

	public boolean doubleClick(String identifier, String identifierValue, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				ele = driver.findElement(By.name(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				ele = driver.findElement(By.xpath(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ById":
				ele = driver.findElement(By.id(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ByClassName":
				ele = driver.findElement(By.className(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ByTagName":
				ele = driver.findElement(By.tagName(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				ele = driver.findElement(By.linkText(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				ele = driver.findElement(By.partialLinkText(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				ele = driver.findElement(By.cssSelector(identifierValue));
				action.doubleClick(ele).perform();
				log.info(String.format("Sucessfully performed double click"));
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("Double click operation unsuccessfull"));
				return false;
			}
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}

	}

	public boolean mouseHover(String identifier, String identifierValue, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				ele = driver.findElement(By.name(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s --  ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				ele = driver.findElement(By.xpath(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s --  ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ById":
				ele = driver.findElement(By.id(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s --  ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByClassName":
				ele = driver.findElement(By.className(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s --  ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByTagName":
				ele = driver.findElement(By.tagName(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s --  ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				ele = driver.findElement(By.linkText(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s -- ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				ele = driver.findElement(By.partialLinkText(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s -- ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				ele = driver.findElement(By.cssSelector(identifierValue));
				action.moveToElement(ele).build().perform();
				log.info(String.format("sucessfully moved to the element on %s -- ", identifierValue));
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}

	}

	public boolean rightClick(String identifier, String identifierValue, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				ele = driver.findElement(By.name(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				ele = driver.findElement(By.xpath(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ById":
				ele = driver.findElement(By.id(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByClassName":
				ele = driver.findElement(By.className(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByTagName":
				ele = driver.findElement(By.tagName(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				ele = driver.findElement(By.linkText(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				ele = driver.findElement(By.partialLinkText(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				ele = driver.findElement(By.cssSelector(identifierValue));
				action.contextClick(ele).build().perform();
				log.info(String.format("sucessfully right click on %s ", identifierValue));
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}

	}

	public boolean scrollforelement(String identifier, String identifiervalue, String image_name) {
		try {
			switch (identifier) {
			case "ByName":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.name(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ByXpath":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ById":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ByClassName":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.className(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ByTagName":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.tagName(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ByLinkText":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.linkText(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ByPartialLinkText":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.partialLinkText(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			case "ByCssSelector":
				
				js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.cssSelector(identifiervalue)));
				log.info(String.format("sucessfully scroll on %s ", identifiervalue));
				screenCapture(image_name);
				return true;

			default:
				log.error(String.format("%s is not a valid identifier", identifier));
				return false;
			}
		} catch (Exception e) {
			screenCapture(image_name);
			log.error("Error : ", e);
			return false;
		}

	}
}
