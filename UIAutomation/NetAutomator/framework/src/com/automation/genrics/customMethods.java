package com.automation.genrics;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.automation.TestCaseRunner.TestCaseDataReader;
import com.automation.Utilities.ReportGenrator;
import com.automation.Utilities.loggerLoad;
import com.aventstack.extentreports.ExtentTest;

public class customMethods {
	static Logger log = loggerLoad.config("customMethods");
	public ActionsMethods act = new ActionsMethods();
	genericMethods gm = new genericMethods();
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
	public static TestCaseDataReader tcr ;

	@SuppressWarnings("static-access")
	public boolean userdefinedMethods(int i, String excelpath, String keywords, ExtentTest test, Workbook workbook, Sheet sheet) {
		try {

			String keyword = keywords.split(":")[1];
			log.info("Actions need to perform : " + keyword);
			switch (keyword) {
			case "":
				log.error("Keywords should not be blank");
				act.quitbrowser();
				olog.errorlog(test, "Keywords should not be blank");
				System.exit(0);
			case "randomnum":
				TestCaseID = sheet.getRow(i).getCell(0).toString();
				Description = sheet.getRow(i).getCell(1).toString();
				identifier = sheet.getRow(i).getCell(3).toString();
				identifierValue =sheet.getRow(i).getCell(4).toString();
				parameter = sheet.getRow(i).getCell(5).toString();
				String SALTCHARS = "ABCDEFLMNOWXYZ1234567890";
				StringBuilder salt = new StringBuilder();
				Random rnd = new Random();
				while (salt.length() < 18) {
					int index = (int) (rnd.nextFloat() * SALTCHARS.length());
					salt.append(SALTCHARS.charAt(index));
				}
				String saltStr = salt.toString();
				System.out.println(saltStr);
				boolean data = act.sendKeys(identifier, identifierValue, saltStr, TestCaseID + "_" + parameter);
				log.info(String.format("Going to send keys %s on page", saltStr));
				olog.passlog(test, Description + " parameter " + parameter);
				break;
				
				
			
				
				
			}
			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			act.quitbrowser();
			log.error(String.format("Error in execution of %s test case. Please open TestNG report ", excelpath));
			log.error("Error are :  ", e);
			olog.errorlog(test, e.toString());
			return false;

		}

	}
}
