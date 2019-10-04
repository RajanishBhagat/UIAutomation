package com.automation.genrics;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.automation.TestCaseRunner.TestCaseDataReader;
import com.automation.Utilities.loggerLoad;
import com.aventstack.extentreports.ExtentTest;

public class SharedSteps {
	ActionsMethods act = new ActionsMethods();
	static Logger log = loggerLoad.config("Sharedsteps");
	ArrayList<Boolean> test_step_status = new ArrayList<Boolean>();
	genericMethods gm = new genericMethods();
	public customMethods cm = new customMethods();
	public static TestCaseDataReader tcr ;
	public String TestCaseID;
	public String Description;
	public String Actions;
	public String identifier;
	public String identifierValue;
	public String parameter;
	public Sheet shared_sheet;
	String shared_sheetname;
	String shared_steps;
	public Boolean sharedstepmethod(int i,Workbook workbook, Sheet sheet, ExtentTest test) {
		try {
			
			TestCaseID = sheet.getRow(i).getCell(0).toString();
			Description = sheet.getRow(i).getCell(1).toString();
			parameter = sheet.getRow(i).getCell(5).toString();
			shared_sheetname=parameter.split(",")[0];
			
			shared_steps=(parameter.split(","))[1];
		
			int start_step=Integer.parseInt(shared_steps.split("-")[0]);
			int last_step=Integer.parseInt(shared_steps.split("-")[1]);
			try {
			shared_sheet=workbook.getSheet(shared_sheetname);}
			catch(Exception e)
			{
				log.error("shared sheet does not exist in workbook"+shared_sheetname);
				log.error(e);
			}
			
			for (int x= start_step; x <= last_step ; x++)
			{
				Cell cell = shared_sheet.getRow(x).getCell(2);
				if (tcr.is_cell_blank(cell)) {
					break;
				}
				log.info("Executing Shared Step No. " + x);
				String keywords = shared_sheet.getRow(x).getCell(2).getStringCellValue().toLowerCase();
				log.info("Actions need to perform : " + keywords);

				if (keywords.contains(":"))
					test_step_status.add(cm.userdefinedMethods(x, null, keywords, test,workbook,shared_sheet));
				else
					test_step_status.add(gm.defMthd(x, null, keywords, test, workbook,shared_sheet));
				
			}
			if (test_step_status.contains(false))
				return false;
			else
				return true;
			
		}
		catch(Exception e)
		{
			System.out.println("error in shared step class");
			e.printStackTrace();
			act.quitbrowser();
			return false;
		}
		
	}

	
}
