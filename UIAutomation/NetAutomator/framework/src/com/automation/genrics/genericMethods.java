package com.automation.genrics;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.automation.TestCaseRunner.TestCaseDataReader;
import com.automation.Utilities.ReportGenrator;
import com.automation.Utilities.loggerLoad;
import com.aventstack.extentreports.ExtentTest;

public class genericMethods {
	static Logger log = loggerLoad.config("genericMethods");
	ActionsMethods act = new ActionsMethods();
	ReportGenrator olog = new ReportGenrator();
	ExtentTest test;
	ArrayList<String> al = new ArrayList<>();
	public String TestCaseID;
	public String Description;
	public String Actions;
	public String identifier;
	public String identifierValue;
	public String parameter;
	int index;
	long time;
	public String iteration;
	public static TestCaseDataReader tcr;

	@SuppressWarnings("static-access")
	public boolean defMthd(int i, String excelpath, String keywords, ExtentTest test,Workbook workbook, Sheet sheet) {
		try {
			switch (keywords) {
			case "":{
				log.error("Keywords should not be blank");
				act.quitbrowser();
				olog.errorlog(test, "Keywords should not be blank");
				System.exit(0);
				return false;}

			case "browse":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				// log.info(String.format("Going to browse url - %s", TestCaseID));
				Description = sheet.getRow(i).getCell(1).toString();
				// log.info(String.format("Going to browse url - %s", Description));
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to browse url - %s", parameter));
				iteration = sheet.getRow(i).getCell(6).toString();

				boolean result;

				if (iteration.equalsIgnoreCase("yes") && !iteration.equalsIgnoreCase("NA") || iteration.equals("")) {

					al = tcr.getData(excelpath, parameter);
					System.out.println("@@@@@@@@@ @@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@@@@");
					for (String url : al) {
						result = act.browse(url, TestCaseID + "_" + url);
						if (result) {
							log.info(String.format("Successfully opened URL %s", url));
							olog.passlog(test, Description + " :::  Successfully opened URL " + url);
							return true;
						} else {
							log.error(String.format("browse url failed"));
							olog.faillog(test, Description + " ::: Browsing URL Unsuccessfull " + url);
							return false;
						}

					}

				} else {

					result = act.browse(parameter, TestCaseID);
					if (result) {
						log.info(String.format("Successfully opened URL - %s", parameter));
						olog.passlog(test, Description + " :::  Successfully opened URL " + parameter);
						return true;
					} else {
						log.error(String.format("browse url failed"));
						olog.faillog(test, Description + " ::: Browsing URL Unsuccessfull " + parameter);
						return false;
					}
				}
			}

			case "click":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				log.info(String.format("Going to perform click action"));
				boolean result_click = act.click(identifier, identifierValue, TestCaseID);
				if (result_click) {
					olog.passlog(test, Description +" ::: Click action is Successfull");
					return true;
				} else {
					log.error("Click action is Unsuccessful");
					olog.faillog(test, Description + " ::: Click action is Unsuccessful ");
					return false;
				}}

			case "keypress":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				log.info(String.format("Going to press key entered"));
				boolean result_keypress = act.keypress(identifier, identifierValue, parameter, TestCaseID);
				if (result_keypress) {
					olog.passlog(test, Description+ " ::: Successfully pressed " + identifierValue + " key");
					return true;
				} else {
					log.error("Not able to press key");
					olog.faillog(test, Description+" ::: Not able to press " + identifierValue + " key");
					return false;
				}}
				

			case "sendkeys":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				iteration = sheet.getRow(i).getCell(6).toString();
				log.info(String.format("Going to perform Sendkeys(Entering Text) action"));
				boolean result_sendkeys;
				if (iteration.equalsIgnoreCase("yes") && !iteration.equalsIgnoreCase("NA") || iteration.equals("")) {
					al = tcr.getData(excelpath, parameter);

					for (String param : al) {
						result_sendkeys = act.sendKeys(identifier, identifierValue, param, TestCaseID + "_" + param);
						if (result_sendkeys) {

							olog.passlog(test, Description + " ::: Successfully entered value" + param);
							return true;
						} else {
							log.error(String.format("Sendkeys Action is not performed and is unsuccessfull"));
							olog.faillog(test, Description + " ::: Sendkeys Action is unsuccessfull" + param);
							return false;
						}
					}
				} else {

					result_sendkeys = act.sendKeys(identifier, identifierValue, parameter, TestCaseID);
					if (result_sendkeys) {

						olog.passlog(test, Description+ " ::: Successfully entered value" + parameter);
						return true;
						
					} else {

						olog.faillog(test, Description + " ::: Sendkeys Action is unsuccessfull " + parameter);
						return false;
					}
				}}
				
				

			case "waitforvisiblity":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info("Going to wait for " + parameter + " seconds for visibility of Element");
				if (parameter.equals(""))
					parameter = "120";
				boolean visibility = act.waitforVisiblity(identifier, identifierValue, parameter, TestCaseID);
				if (visibility) {
					olog.passlog(test, Description+ "   ::: Wait for Visibilty of Element is Successfull");
					return true;
				} else {
					log.error(String.format("Wait for Visibilty of Element is unsucessfull"));
					olog.faillog(test,Description + " ::: Wait for Visibilty of Element is unsucessfull");
					return false;
				}}
				

			case "waitforclickability":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info("Going to wait for " + parameter + " seconds for clickability of Element");
				if (parameter.equals(""))
					parameter = "120";
				boolean clickability = act.waitforVisiblity(identifier, identifierValue, parameter, TestCaseID);
				if (clickability) {
					olog.passlog(test, Description + " ::: Wait for clickablity of Element is Successfull");
					return true;
				} else {
					log.error(String.format("Wait for clickablity of Element is unsucessfull"));
					olog.faillog(test, Description + " ::: Wait for clickablity of Element is unsucessfull");
					return false;
				}}
				

			case "sleep":{
				Description = sheet.getRow(i).getCell(1).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				int time1 = Integer.parseInt(parameter);
				long time = time1;
				if (parameter.equals(""))
					time = 5000;

				log.info(String.format("Test execution stopped for %d milliseconds", time));
				Thread.sleep(time);
				olog.passlog(test, Description + " parameter " + parameter);
				return true;}

			case "scrollonpage":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to scroll %s on page", parameter));
				boolean scroll = act.scrollonpage(parameter, TestCaseID);
				
				if (scroll) {
					log.info("Successfully scrolled on page");
					olog.passlog(test,Description+ " ::: Scrolling on page is successfull with cordinates : " + parameter);
					return true;
				} else {
					log.error("Scrolling on page is unsuccessful");
					olog.faillog(test,Description + " ::: Scrolling on page is Unsuccessfull with cordinates : " + parameter);
					return false;
				}}
				

			
			 case "scrollforelement": {
				 TestCaseID = sheet.getRow(i).getCell(0).toString();
				 Description = sheet.getRow(i).getCell(1).toString(); 
				 identifier = sheet.getRow(i).getCell(3).toString(); 
				 identifierValue = sheet.getRow(i).getCell(4).toString(); 
				 log.info(String.format("Going to scroll %s on page", identifierValue));
				 boolean scrollforelement_result=act.scrollforelement(identifier,identifierValue , TestCaseID);
				 if (scrollforelement_result) {
						log.info("Successfully scrolled on page and element is found");
						olog.passlog(test,Description + " ::: Scrolling on page is successfull and element is found");
						return true;
					} else {
						log.error("Scrolling on page is unsuccessfull & element is not found");
						olog.faillog(test,Description+ " ::: Scrolling on page is Unsuccessfull & element is not found");
						return false;
					}
			 }
			     
			 

			case "validatetitle":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				parameter = sheet.getRow(i).getCell(5).toString();

				log.info(String.format("Going to validate title on page as - %s", parameter));
				boolean title = act.validatetitle(parameter, TestCaseID);
				if (title) {
					olog.passlog(test,
							String.format(Description+" ::: Title validation on page done successfully. Title is - %s", parameter));
				return true;}
				else {
					olog.faillog(test, String.format(Description+" ::: Title validation on page failed. Title is - %s", parameter));
				return false;}}

				

			case "verifycontentonpage":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				parameter = sheet.getRow(i).getCell(5).toString();

				log.info(String.format("Going to verify Content on page %s", parameter));
				boolean contentverify = act.verifycontentonpage(parameter, TestCaseID);
				if (contentverify) {
					olog.passlog(test, String.format(Description+" ::: Content on page - %s, is verified successfully", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Content on page - %s, failed on verification", parameter));
					return false;}}
				

			case "verifycontentforelement":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to verify Content on page %s", parameter));
				boolean contentverifyforElement = act.verifycontentforelement(identifier, identifierValue, parameter,
						TestCaseID);
				if (contentverifyforElement)
					{olog.passlog(test, String.format(Description + " ::: Element content - %s, is verified successfully", parameter));
					return true;
					}
				else {
					olog.faillog(test, String.format(Description + " ::: Element content - %s, failed on verification", parameter));
				return false;
				}}

			case "alertaccept":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				log.info("Going to accept alert on Current page");
				boolean alert = act.alertaccept(TestCaseID);
				if (alert) {
					olog.passlog(test, String.format(Description + " ::: Alert accepted successfully on current page"));
					return true;}
				else {
					olog.faillog(test, String.format(Description+ " ::: Alert acceptance failed on current page"));
					return false;}}
			

			case "alertsendkeys":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info("Going to accept alert on Current page");
				boolean alertsend = act.alertsendkeys(parameter, TestCaseID);
				if (alertsend) {
					olog.passlog(test, String.format(Description + " ::: Input - %s sent during alert is successful", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description+ " ::: Input - %s sent durig alert failed", parameter));
					return false;}}
				

			case "alertdecline":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				log.info("Going to accept alert on Current page");
				boolean alertdec = act.alertdecline(TestCaseID);
				if (alertdec) {
					olog.passlog(test, String.format(Description + " ::: Alert dismissal successful"));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Alert dismissal failed"));
					return false;
				}}
				

			case "navigateto":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				boolean navigate_result;
				if (iteration.equalsIgnoreCase("yes") && !iteration.equalsIgnoreCase("NA") || iteration.equals("")) {
					al = tcr.getData(excelpath, parameter);
					for (String url : al) {
						navigate_result = act.navigateTo(url, TestCaseID + "_" + url);
						if (navigate_result) {
							log.info(String.format("Successfully Navigated to the following url - %s", url));
							olog.passlog(test, Description + " ::: Successfully Navigated to the following url " + url);
							return true;
						} else {
							log.error(String.format("Navigation to URL is Unsuccessfull"));
							olog.faillog(test, Description + " ::: Navigation to URL is Unsuccessfull");
							return false;
						}
					}
				}

				else {
					navigate_result = act.navigateTo(parameter, TestCaseID);
					if (navigate_result) {
						log.info(String.format("Successfully Navigated to the following url - %s", parameter));
						olog.passlog(test, Description +" ::: Successfully Navigated to the following url " + parameter);
						return true;
					} else {
						log.error(String.format("Navigation to URL is Unsuccessfull"));
						olog.faillog(test, Description + " ::: Navigation to URL is Unsuccessfull");
						return false;
					}
				}}
				

			case "getwindowhandles":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				log.info("Going to switch on latest window");
				boolean getWindowHandles = act.getwindowhandles(TestCaseID);
				if (getWindowHandles) {
					olog.passlog(test, String.format(Description + " ::: Switch to latest window successful"));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Switch to latest window failed"));
					return false;}}
				

			case "gotowindowhandlebyindex":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				int index = (int) sheet.getRow(i).getCell(5).getNumericCellValue();
				log.info(String.format("Going to switch on window with index %s", parameter));
				// int index =Integer.parseInt(parameter);
				boolean getwindowHandleIndex = act.gotowindowhandlebyindex(index, TestCaseID);
				if (getwindowHandleIndex) {
					olog.passlog(test, String.format(Description + " ::: Switch on window with index %s successful", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Switch on window with index %s failed", parameter));
					return false;}}
				

			case "frameswitch":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				log.info(String.format("Going to switch on window with identifier %s , value %s", identifier,
						identifierValue));
				boolean switchframe = act.frameswitch(identifier, identifierValue, TestCaseID);
				if (switchframe) {
					olog.passlog(test, String.format(Description +" ::: Switch frame on %s successful", parameter));
					return true;} 
				else {
					olog.faillog(test, String.format(Description +" ::: Switch frame on %s failed", parameter));
					return false;}}
				

			case "selectcheckboxorradiobutton":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to select check box and Radio button"));
				boolean selectcheckbox = act.selectfromcheckbox(identifier, identifierValue, TestCaseID);
				if (selectcheckbox) {
					olog.passlog(test, String.format(Description + " ::: Switch frame on %s successful", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description +" ::: Switch frame on %s failed", parameter));
					return false;}}
				

			case "rightclick":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				log.info(String.format("Going to perform right click"));
				boolean rightclick_result;
				rightclick_result = act.rightClick(identifier, identifierValue, TestCaseID);
				if (rightclick_result) {
					olog.passlog(test,Description + " ::: Sucessfully performed right click operation ");
					return true;
				} else {
					log.error(String.format("Right click operation unsuccessfull "));
					olog.faillog(test, Description + " ::: Right click operation unsuccessfull ");
					return false;
				}}
				

			case "mousehover":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				log.info(String.format("Going to perform mouse hover"));
				boolean mousehover_result;
				mousehover_result = act.mouseHover(identifier, identifierValue, TestCaseID);
				if (mousehover_result) {
					olog.passlog(test, Description +" ::: Sucessfully moved to element ");
					return true;
				} else {
					log.error(String.format("Move to Element operation is unsuccessfull "));
					olog.faillog(test,Description + " ::: Move to Element operation is unsuccessfull ");
					return false;
				}
			}

			case "doubleclick":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				log.info(String.format("Going to perform doubleClick on page"));
				boolean doubleclick_result;
				doubleclick_result = act.doubleClick(identifier, identifierValue, TestCaseID);
				if (doubleclick_result) {
					olog.passlog(test, Description + " ::: Sucessfully performed double click ");
					return true;
				} else {
					log.error(String.format("Double click operation unsuccessfull "));
					olog.faillog(test, Description + " ::: Double click operation unsuccessfull ");
					return false;
				}
			}
				

			case "selectvaluefromlist":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to select data from list data is %s", parameter));
				boolean selectvaluefromlst = act.selectvaluefromlist(identifier, identifierValue, parameter,
						TestCaseID);
				if (selectvaluefromlst) {
					olog.passlog(test, String.format(Description +" ::: Selecting data from list %s successful", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Selecting data from list %s failed", parameter));
					return false;}}
				

			case "selectfromdropdownbyvisibletext":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to select data from drop down list data - %s", parameter));
				boolean selectdatafromvisibletext = act.selectdatafromvisibletext(identifier, identifierValue,
						parameter, TestCaseID);
				if (selectdatafromvisibletext) {
					olog.passlog(test, String.format(Description + " ::: Select data from drop down list %s successful", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Select data from drop down list %s failed", parameter));
					return false;}}
				

			case "selectfromdropdownbyindex":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to select data from drop down list data - %s", parameter));
				boolean SelectFromDropDownByIndex = act.selectfromdropdownbyindex(identifier, identifierValue,
						parameter, TestCaseID);
				if (SelectFromDropDownByIndex) {
					olog.passlog(test,
							String.format(Description + " ::: Select data from drop down list with index %s successful", parameter));
					return true;}
				else {
					olog.faillog(test,
							String.format(Description + " ::: Select data from drop down list with index %s failed", parameter));
					return false;}}
				

			case "uploadfile":{
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue = sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				log.info(String.format("Going to upload the following file - %s", parameter));
				if (parameter.equals("")) {
					olog.faillog(test, String.format(Description +" ::: Parameter should not be blank in case of file upload"));
					return false;
					
				}

				boolean uploadFile = act.uploadfile(identifier, identifierValue, parameter, TestCaseID);
				if (uploadFile) {
					olog.passlog(test, String.format( Description +" ::: File - %s uploaded successfully", parameter));
					return true;}
				else {
					olog.faillog(test, String.format(Description + " ::: Upload of file - %s has failed", parameter));
					return false;}}
				

			case "closebrowser":{
				boolean closebrowser_result = act.closebrowser();
				if (closebrowser_result) {
					log.info(String.format("Successfully closed browser"));
					olog.passlog(test,Description + " ::: Successfully closed browser");
					return true;
				} else {
					log.error("Browser not closed");
					olog.faillog(test,Description + " ::: Browser not closed");
					return false;
				}
			}
				

			case "quitbrowser":{
				boolean quitbrowser_result = act.quitbrowser();
				if (quitbrowser_result) {
					log.info(String.format("Successfully quit browser"));
					olog.passlog(test,Description + " ::: Successfully quit browser");
					return true;
				} else {
					log.error("Browser not closed");
					olog.faillog(test,Description + " ::: Browser not closed");
					return false;
				}
			}
			default:
					return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//boolean error = act.quitbrowser();
			log.error(String.format("Error in execution of %s test case. Please open TestNG report ", excelpath));
			log.error("Error are :  ", e);
			olog.errorlog(test, e.toString());
			return false;

		}

	}

}
