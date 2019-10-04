package com.automation.TestCaseRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.TestSuiteRunner.SuiteRunner;
import com.automation.Utilities.ReportGenrator;
import com.automation.Utilities.TestLinkProgram;
import com.automation.Utilities.loggerLoad;
import com.automation.genrics.ActionsMethods;
import com.automation.genrics.SharedSteps;
import com.automation.genrics.customMethods;
import com.automation.genrics.genericMethods;
import com.aventstack.extentreports.ExtentTest;

import testlink.api.java.client.TestLinkAPIResults;

/**
 * @author Automation Team
 *
 */

public class TestCaseDataReader {
	public static Workbook workbook;
	ActionsMethods act = new ActionsMethods();
	static ExtentTest test;
	static Logger log = loggerLoad.config("TestCaseDataReader");
	private BufferedReader br;
	ReportGenrator olog = new ReportGenrator();
	genericMethods gm = new genericMethods();
	public customMethods cm = new customMethods();
	SharedSteps share =new SharedSteps();
	TestLinkProgram tl = new TestLinkProgram();
	String result = TestLinkAPIResults.TEST_PASSED;
	public static String Testcase_name;
	public static Sheet sheet;
	public static Cell cell;
	public static Row row;
	public ArrayList<String> TestCase_executable;
	HashMap<String, Boolean> status = new HashMap<String, Boolean>();
	static File[] paths;
	String excelpath;
	File img_suite_dir;
	public static File img_testcase_dir;
	
	public TestCaseDataReader(String excelpath, File img_suite_dir) {
		this.excelpath = excelpath;
		this.img_suite_dir = img_suite_dir;
	}
	

	@SuppressWarnings("unused")
	public void Read_excel_dir() {
		try {
			olog.createInstance(excelpath);
			String excel_dir = "testcases/" + excelpath;
			File dir = new File(excel_dir);

			FilenameFilter fileNameFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (name.lastIndexOf('.') > 0) {

						if (name.equals("testcaseStep.xls") || name.equals("testcaseStep.xlsx")) {
							return true;
						}
					}
					return false;
				}
			};

			paths = dir.listFiles(fileNameFilter);
			String path = paths[0].toString();
			// System.out.println(path);
			if (path.substring(path.lastIndexOf('.')).equals(".xlsx")) {
				workbook = new XSSFWorkbook(path);
			} else if (path.substring(path.lastIndexOf('.')).equals(".xls")) {
				FileInputStream src = new FileInputStream(path);
				workbook = new HSSFWorkbook(src);
			} else {
				System.out.println("excel not available ");

			}
			log.info(String.format("Going to read data from %s - %s fle \n", excelpath, path));
		}

		catch (Exception e) {

			log.error(String.format("Error in execution of %s test case. Please open TestNG report ", excelpath));
			log.error("Error are :  ", e);
			olog.errorlog(test, e.toString());

		}
	}

	public void Execute_test_steps() {
		try {
		TestCase_executable = Read_master_sheet();
		log.info(" ********** NUMBER OF TEST CASES TO EXECUTE: " + TestCase_executable.size() + " **********\n");
		
		for (int count = 0; count < TestCase_executable.size(); count++) {
			
			sheet = workbook.getSheet(TestCase_executable.get(count));
			Testcase_name = sheet.getSheetName();
			System.out.println(Testcase_name);
			String img_path= img_suite_dir.toString() +"/"+Testcase_name;
			img_testcase_dir = new File(img_path);
			if(!img_testcase_dir.exists()){
				img_testcase_dir.mkdirs();
				}
			log.info("***********************************************************************\n");
			log.info(" ========== Starting Test Case No." + (count + 1) + " ===========\n");
			log.info(" ========== TEST CASE NAME : " + Testcase_name + " ===========\n");
			log.info("***********************************************************************\n");
			

			test = olog.genRateReport(Testcase_name);
			int row_size = sheet.getPhysicalNumberOfRows();
			if (row_size!=0)
			 ActionsMethods.registerDriver(SuiteRunner.getBrowsername(), img_suite_dir);
			
			 ArrayList<Boolean> test_step_status = new ArrayList<Boolean>();         // initialized for capturing steps results
			
			 for (int i = 1; i < row_size; i++) {
				
				Cell cell = sheet.getRow(i).getCell(2);
				if (is_cell_blank(cell)) {
					break;
				}
				log.info("Executing Step No. " + i);
				String keywords = sheet.getRow(i).getCell(2).getStringCellValue().toLowerCase();
				log.info("Actions need to perform : " + keywords);

				if (keywords.contains(":"))
					test_step_status.add(cm.userdefinedMethods(i, excelpath, keywords, test,workbook,sheet));
				else if(keywords.contains("share"))
					test_step_status.add(share.sharedstepmethod(i,workbook, sheet,test ));
				else
					test_step_status.add(gm.defMthd(i, excelpath, keywords, test, workbook, sheet));
				
			}
			if (test_step_status.contains(false))
				status.put(Testcase_name, false);
			else
				status.put(Testcase_name, true);

			ReportGenrator.extent.flush();
			log.info("***********************************************************************\n");
			log.info("             ========= END OF TEST CASE ========== \n");
			log.info("***********************************************************************\n");

		}
		Testlink_Call();
		
		log.info("=============================================================================\n");
		log.info("                         SUITE FINISHED \n");
		log.info("==============================================================================\n");
	}
		catch(Exception e)
		{
			log.error("error");
			log.error(e);
			
			
			
		}
	}
	public void Testlink_Call() {
		try {
			Set<Entry<String, Boolean>> set = status.entrySet();
			Iterator<Entry<String, Boolean>> iterator = set.iterator();
			Iterator<Entry<String, Boolean>> hmIterator = status.entrySet().iterator();
			while (hmIterator.hasNext()) {
				Entry<String, Boolean> pair = hmIterator.next();

				String TestCaseName = (String) pair.getKey();
				Boolean TestCaseStatus = (Boolean) pair.getValue();
				log.info("TestCase Name is  " + TestCaseName + " & Result for TestLink updation is: " + TestCaseStatus);
				 //TestLinkProgram.TestLinkCall(TestCaseName, TestCaseStatus);
			}
		}

		catch (Exception e) {
			log.error(String.format("Error in calling testlink"));
			log.error("Error" + e);
		}

	}

	public ArrayList<String> Read_master_sheet() {
		Read_excel_dir();    // getting excel path, initialized workbook and all necessary variables
		ArrayList<String> test = new ArrayList<String>(); // local List initialized to store test cases that needs to be executed
		int testcase_heading_index = 1;
		int to_be_executed_index = 3;
		sheet = workbook.getSheetAt(0); // this will return master sheet index to variable sheet
		int size = sheet.getPhysicalNumberOfRows(); // calculating number of rows
		log.info("\n \n");
		log.info("***********TOTAL NUMBER OF TEST CASES =>"+(size-2)+" ***********************\n");
		log.info("***********************************************************************\n");
		row = sheet.getRow(0); // fetching heading row
		
		for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)  // loop to iterate through cells in heading row
		{
			cell = row.getCell(j); // getting cell value startign from 0
			if (is_cell_blank(cell))  // checking in case cell value is null  
			{  
				System.out.println("cell value is null. Please enter value at master sheet 1st row " + j + " column \n");
				break;
			} 
			else // if not null changing index of test case heading and to execute heading respectively
			{
				if (cell.getStringCellValue().contains("Test"))
				{		
					testcase_heading_index = j;
				}
					
				if (cell.getStringCellValue().contains("execute"))
				 {
					to_be_executed_index = j;
				}
			}
			}

		
		log.info(String.format("****** TEST CASES THAT NEEDS TO BE EXECUTED ********\n"));
		for (int i = 1; i < size; i++) {

			Cell cell = sheet.getRow(i).getCell(to_be_executed_index); // getting cell value of to execute column either yes or no or blank
			String Testcase_name = sheet.getRow(i).getCell(testcase_heading_index).getStringCellValue();
			if (is_cell_blank(cell)) { // if execute column contains blank then test case is considered as no
					continue;
				}
			else if (cell.getStringCellValue().equalsIgnoreCase("Yes")) { // if yes then it is added to "test" array list
				if (!test.contains(Testcase_name)) {
					test.add(Testcase_name);
					log.info(String.format("             "+ Testcase_name+" \n"));
					}
				
				else {
					continue;}
				} 
			
			else // in case its anything other then yes or blank then it is considered as no 
				continue;
		}

		return test;

	}

	public static boolean is_cell_blank(Cell cell) {
		return (cell == null || cell.getCellType() == cell.CELL_TYPE_BLANK);
	}

	public ArrayList<String> getData(String iterationfile, String parameter) { // not used as of now but do not delete
		ArrayList<String> al = new ArrayList<>();
		try {
			int index = 0;
			br = new BufferedReader(new FileReader(new File("testcases/" + iterationfile + "/iteration.spec")));
			String line;

			String l = br.readLine();
			String[] l2 = l.split("\\s+");

			index = Arrays.asList(l2).indexOf(parameter);

			while ((line = br.readLine()) != null) {

				String line2[] = line.split("\\s+");
				al.add(line2[index]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(al);
		return al;
	}
	
}
