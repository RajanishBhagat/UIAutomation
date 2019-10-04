package com.automation.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.automation.TestCaseRunner.TestCaseDriveClass;

/**
 * @author Automation Team 
 *
 */

public class SuiteDriveClass {
	public TestCaseDriveClass testcasedrive;
	private BufferedReader br;
	private StringBuffer sbr;
	
	public SuiteDriveClass()
	{
		
		testcasedrive = new TestCaseDriveClass();
<<<<<<< SuiteDriveClass.java
		System.out.println("Initialized suite drive class");
		
=======
		
>>>>>>> 1.5
	}

	public boolean suiteRunner(String name) throws IOException{
		
		try{
		sbr = new StringBuffer();
		br = new BufferedReader(new FileReader(new File(name))); //Read the testcasename from Suite file
		String line;
		while((line = br.readLine()) != null ){
			if(! line.startsWith("#")){
				boolean bl = testcasedrive.testcaseRunner(line); //Pass the testcasename to the SuiteDriveClass method 
				if(bl == false){
					System.out.println("Testsuite mentioned as not to continue");
					System.exit(0);
				}
			}
			System.out.println("==========================================================================");
			
		}
		}catch(Exception e){
			System.err.println("Insided suiteRunner " + e.getMessage());
			return false;
		}
		return true;
	}
}
