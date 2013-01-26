package cn.edu.nju.moon.consistency.checker.exp;

import java.util.ArrayList;
import java.util.List;

public class ExpTestSuite
{
	private List<ExpTestCase> testSuite = new ArrayList<ExpTestCase>();
	
	public ExpTestSuite()
	{
		/** for valid view */
		/*
		this.testSuite.add(new ExpTestCase(5, 1000, 2));
		this.testSuite.add(new ExpTestCase(5, 1500, 50));
		this.testSuite.add(new ExpTestCase(5, 1800, 50));
		this.testSuite.add(new ExpTestCase(5, 2000, 50));
		this.testSuite.add(new ExpTestCase(5, 2500, 50));
		this.testSuite.add(new ExpTestCase(5, 3000, 30));
		this.testSuite.add(new ExpTestCase(5, 5000, 20));
//		this.testSuite.add(new ExpTestCase(5, 10000, 10));
		
		this.testSuite.add(new ExpTestCase(20, 1000, 30));
		this.testSuite.add(new ExpTestCase(20, 1500, 30));
		this.testSuite.add(new ExpTestCase(20, 1800, 30));
		this.testSuite.add(new ExpTestCase(20, 2000, 20));
		this.testSuite.add(new ExpTestCase(20, 2500, 10));
		this.testSuite.add(new ExpTestCase(20, 3000, 10));
		this.testSuite.add(new ExpTestCase(20, 5000, 10));
//		this.testSuite.add(new ExpTestCase(20, 10000, 5));
  		*/
		
		/** for random view */
		this.testSuite.add(new ExpTestCase(5, 1000, 100));
		this.testSuite.add(new ExpTestCase(5, 1500, 100));
		this.testSuite.add(new ExpTestCase(5, 1800, 100));
		this.testSuite.add(new ExpTestCase(5, 2000, 100));
		this.testSuite.add(new ExpTestCase(5, 2500, 100));
		this.testSuite.add(new ExpTestCase(5, 3000, 50));
		this.testSuite.add(new ExpTestCase(5, 5000, 50));
		this.testSuite.add(new ExpTestCase(5, 10000, 50));
		this.testSuite.add(new ExpTestCase(5, 15000, 50));
		this.testSuite.add(new ExpTestCase(5, 20000, 50));
		this.testSuite.add(new ExpTestCase(5, 25000, 50));
		this.testSuite.add(new ExpTestCase(5, 30000, 50));
		this.testSuite.add(new ExpTestCase(5, 40000, 50));
		this.testSuite.add(new ExpTestCase(5, 50000, 50));
		
		this.testSuite.add(new ExpTestCase(20, 1000, 100));
		this.testSuite.add(new ExpTestCase(20, 1500, 100));
		this.testSuite.add(new ExpTestCase(20, 1800, 100));
		this.testSuite.add(new ExpTestCase(20, 2000, 100));
		this.testSuite.add(new ExpTestCase(20, 2500, 50));
		this.testSuite.add(new ExpTestCase(20, 3000, 50));
		this.testSuite.add(new ExpTestCase(20, 5000, 50));
		this.testSuite.add(new ExpTestCase(20, 10000, 5));
		this.testSuite.add(new ExpTestCase(20, 15000, 50));
		this.testSuite.add(new ExpTestCase(20, 20000, 50));
		this.testSuite.add(new ExpTestCase(20, 25000, 50));
		this.testSuite.add(new ExpTestCase(20, 30000, 50));
		this.testSuite.add(new ExpTestCase(20, 40000, 50));
		this.testSuite.add(new ExpTestCase(20, 50000, 50));
	}
	
	public List<ExpTestCase> getTestCases()
	{
		return this.testSuite;
	}
}
