package com.automation.TestCaseRunner;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.automation.TestSuiteRunner.SuiteRunner;
import com.automation.Utilities.ReportGenrator;
import com.automation.Utilities.TestLinkProgram;
import com.automation.Utilities.loggerLoad;

import testlink.api.java.client.TestLinkAPIResults;

/**
 * @author Automation Team 
 *
 */

public class TestCaseDriveClass 
{
	static Logger log = loggerLoad.config("TestCaseDriveClass");
	static TestLinkDataReader tr = new TestLinkDataReader();
	public static String result = TestLinkAPIResults.TEST_PASSED;
	TestCaseDataReader dr ;
	static TestLinkProgram tl = new TestLinkProgram();
	public static String executionTime;
	public static String Imagedest_path;
	public static File img_suite_dir;
	public static String[] testcase;
	public  static Boolean testcase_status;
	public static String testCaseName;
	ReportGenrator olog = new ReportGenrator();
	public boolean testcaseRunner(String testcasename)
	{
		int count=0;

		try{
			testcase = testcasename.split("\\s+");
			testCaseName=testcasename.split(" ")[0];
			int iterator  = Integer.parseInt(testcase[2]);
			for(int i= 1 ; i <= iterator ; i++){
				Imagedest_path = "reports/"+SuiteRunner.getDateandTime()+"/images/"+testcase[0];
				img_suite_dir = new File(Imagedest_path);
				if(!img_suite_dir.exists()){
					System.out.println("going to create directory :"+Imagedest_path);
					img_suite_dir.mkdirs();
			}
				long startTime = System.currentTimeMillis();
				olog.createInstance(testCaseName+"_"+startTime);
				log.info(String.format("==============================================================\n"));
				log.info(String.format("          STARTING TEST SUITE / FUNCTIONALITY " + testCaseName+"\n"));
				log.info(String.format("===============================================================\n"));
				dr=new TestCaseDataReader(testcase[0] ,img_suite_dir); 
			
				dr.Execute_test_steps();
				long endTime = System.currentTimeMillis();
				long time = TimeUnit.MILLISECONDS.toMinutes(endTime - startTime);
				long sec = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime-(time*60000));
				executionTime = "Execution Time = "+Long.toString(time)+"."+Long.toString(sec);

				

			}

			if(testcase[1].equalsIgnoreCase("terminate")){
				return false;

			}

		}catch(Exception e){
			log.error(String.format("Error : %s", e.getMessage())); 
		}
		return true;
	}

}