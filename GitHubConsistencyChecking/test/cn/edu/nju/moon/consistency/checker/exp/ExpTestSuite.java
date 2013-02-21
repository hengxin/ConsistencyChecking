package cn.edu.nju.moon.consistency.checker.exp;

import java.util.ArrayList;
import java.util.List;

public class ExpTestSuite
{
	private List<ExpTestCase> testSuite = new ArrayList<ExpTestCase>();
	
	public ExpTestSuite()
	{
		/** for valid view */
		
		/**
		 * @date 2013-2-3 (100 loops)
		 * @date 2013-2-5 completed
		 */
		/**
		this.testSuite.add(new ExpTestCase(5, 1000, 100));
		this.testSuite.add(new ExpTestCase(5, 1500, 100));
		this.testSuite.add(new ExpTestCase(5, 1800, 100));
		this.testSuite.add(new ExpTestCase(5, 2000, 100));
		this.testSuite.add(new ExpTestCase(5, 2500, 100));
		this.testSuite.add(new ExpTestCase(5, 3000, 100));
		this.testSuite.add(new ExpTestCase(5, 5000, 100));
		this.testSuite.add(new ExpTestCase(5, 10000, 100));
		
		this.testSuite.add(new ExpTestCase(20, 1000, 100));
		this.testSuite.add(new ExpTestCase(20, 1500, 100));
		this.testSuite.add(new ExpTestCase(20, 1800, 100));
		this.testSuite.add(new ExpTestCase(20, 2000, 100));
		this.testSuite.add(new ExpTestCase(20, 2500, 100));
		this.testSuite.add(new ExpTestCase(20, 3000, 100));
		this.testSuite.add(new ExpTestCase(20, 5000, 100));
		this.testSuite.add(new ExpTestCase(20, 10000, 100));
  		**/
		
		/*
		 * for valid view
		 * tune the number of processes
		 * @date start: 2013-01-28
		 */
//		this.testSuite.add(new ExpTestCase(2, 500, 50));
//		this.testSuite.add(new ExpTestCase(2, 800, 50));
//		this.testSuite.add(new ExpTestCase(2, 1000, 50));
//		this.testSuite.add(new ExpTestCase(2, 1500, 50));
//		this.testSuite.add(new ExpTestCase(2, 1800, 50));
//		this.testSuite.add(new ExpTestCase(2, 2000, 50));
//		this.testSuite.add(new ExpTestCase(2, 2500, 50));
//		this.testSuite.add(new ExpTestCase(2, 3000, 50));
//		this.testSuite.add(new ExpTestCase(2, 4000, 50));
//		this.testSuite.add(new ExpTestCase(2, 5000, 15));
//		this.testSuite.add(new ExpTestCase(2, 6000, 15));
//		this.testSuite.add(new ExpTestCase(2, 8000, 15));
//		this.testSuite.add(new ExpTestCase(2, 10000, 10));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(2, 12000, 10));
//		this.testSuite.add(new ExpTestCase(2, 15000, 10));
//		this.testSuite.add(new ExpTestCase(2, 20000, 5));
//		this.testSuite.add(new ExpTestCase(2, 25000, 5));
		
//		this.testSuite.add(new ExpTestCase(5, 500, 10));
//		this.testSuite.add(new ExpTestCase(5, 800, 10));
//		this.testSuite.add(new ExpTestCase(5, 4000, 10));
//		this.testSuite.add(new ExpTestCase(5, 6000, 10));
//		this.testSuite.add(new ExpTestCase(5, 8000, 5));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(5, 10000, 10));
//		this.testSuite.add(new ExpTestCase(5, 12000, 10));
//		this.testSuite.add(new ExpTestCase(5, 15000, 5));
//		this.testSuite.add(new ExpTestCase(5, 20000, 5));
		
//		this.testSuite.add(new ExpTestCase(8, 500, 10));
//		this.testSuite.add(new ExpTestCase(8, 800, 10));
//		this.testSuite.add(new ExpTestCase(8, 1000, 10));
//		this.testSuite.add(new ExpTestCase(8, 1500, 10));
//		this.testSuite.add(new ExpTestCase(8, 1800, 10));
//		this.testSuite.add(new ExpTestCase(8, 2000, 10));
//		this.testSuite.add(new ExpTestCase(8, 2500, 10));
//		this.testSuite.add(new ExpTestCase(8, 3000, 10));
//		this.testSuite.add(new ExpTestCase(8, 4000, 5));
//		this.testSuite.add(new ExpTestCase(8, 5000, 5));
//		this.testSuite.add(new ExpTestCase(8, 6000, 5));
//		this.testSuite.add(new ExpTestCase(8, 8000, 5));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(8, 10000, 5));
//		this.testSuite.add(new ExpTestCase(8, 12000, 5));
//		this.testSuite.add(new ExpTestCase(8, 15000, 5));
//		this.testSuite.add(new ExpTestCase(8, 20000, 5));
//		this.testSuite.add(new ExpTestCase(8, 25000, 5));
		
//		this.testSuite.add(new ExpTestCase(10, 500, 10));
//		this.testSuite.add(new ExpTestCase(10, 800, 10));
//		this.testSuite.add(new ExpTestCase(10, 1000, 10));
//		this.testSuite.add(new ExpTestCase(10, 1500, 10));
//		this.testSuite.add(new ExpTestCase(10, 1800, 10));
//		this.testSuite.add(new ExpTestCase(10, 2000, 10));
//		this.testSuite.add(new ExpTestCase(10, 2500, 10));
//		this.testSuite.add(new ExpTestCase(10, 3000, 5));
//		this.testSuite.add(new ExpTestCase(10, 4000, 5));
//		this.testSuite.add(new ExpTestCase(10, 5000, 5));
//		this.testSuite.add(new ExpTestCase(10, 6000, 5));
//		this.testSuite.add(new ExpTestCase(10, 8000, 5));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(10, 10000, 10));
//		this.testSuite.add(new ExpTestCase(10, 12000, 10));
//		this.testSuite.add(new ExpTestCase(10, 15000, 10));
//		this.testSuite.add(new ExpTestCase(10, 20000, 10));
//		this.testSuite.add(new ExpTestCase(10, 25000, 10));
		
//		this.testSuite.add(new ExpTestCase(12, 500, 10));
//		this.testSuite.add(new ExpTestCase(12, 800, 10));
//		this.testSuite.add(new ExpTestCase(12, 1000, 10));
//		this.testSuite.add(new ExpTestCase(12, 1500, 10));
//		this.testSuite.add(new ExpTestCase(12, 1800, 10));
//		this.testSuite.add(new ExpTestCase(12, 2000, 10));
//		this.testSuite.add(new ExpTestCase(12, 2500, 5));
//		this.testSuite.add(new ExpTestCase(12, 3000, 5));
//		this.testSuite.add(new ExpTestCase(12, 4000, 5));
//		this.testSuite.add(new ExpTestCase(12, 5000, 5));
//		this.testSuite.add(new ExpTestCase(12, 6000, 5));
//		this.testSuite.add(new ExpTestCase(12, 8000, 5));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(12, 10000, 5));
//		this.testSuite.add(new ExpTestCase(12, 12000, 5));
//		this.testSuite.add(new ExpTestCase(12, 15000, 5));
//		this.testSuite.add(new ExpTestCase(12, 20000, 5));
//		this.testSuite.add(new ExpTestCase(12, 25000, 5));
		
//		this.testSuite.add(new ExpTestCase(15, 500, 10));
//		this.testSuite.add(new ExpTestCase(15, 800, 10));
//		this.testSuite.add(new ExpTestCase(15, 1000, 10));
//		this.testSuite.add(new ExpTestCase(15, 1500, 10));
//		this.testSuite.add(new ExpTestCase(15, 1800, 10));
//		this.testSuite.add(new ExpTestCase(15, 2000, 5));
//		this.testSuite.add(new ExpTestCase(15, 2500, 5));
//		this.testSuite.add(new ExpTestCase(15, 3000, 5));
//		this.testSuite.add(new ExpTestCase(15, 4000, 5));
//		this.testSuite.add(new ExpTestCase(15, 5000, 5));
//		this.testSuite.add(new ExpTestCase(15, 6000, 5));
//		this.testSuite.add(new ExpTestCase(15, 8000, 5));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(15, 10000, 5));
//		this.testSuite.add(new ExpTestCase(15, 12000, 5));
//		this.testSuite.add(new ExpTestCase(15, 15000, 5));
//		this.testSuite.add(new ExpTestCase(15, 20000, 5));
//		this.testSuite.add(new ExpTestCase(15, 25000, 5));
		
//		this.testSuite.add(new ExpTestCase(20, 500, 10));
//		this.testSuite.add(new ExpTestCase(20, 800, 10));
//		this.testSuite.add(new ExpTestCase(20, 4000, 5));
//		this.testSuite.add(new ExpTestCase(20, 6000, 5));
//		this.testSuite.add(new ExpTestCase(20, 8000, 5));
		
		/**
		 * @date 2013-1-29 commented (huge time cost)
		 */
//		this.testSuite.add(new ExpTestCase(20, 10000, 5));
//		this.testSuite.add(new ExpTestCase(20, 12000, 5));
//		this.testSuite.add(new ExpTestCase(20, 15000, 5));
//		this.testSuite.add(new ExpTestCase(20, 20000, 5));
		
		
		/** ******************************************* 
		 * 				for random view 
		 *  *******************************************/
		
		/**
		 *  done
		 *  @date 2013-1-27 
		 */
		/**
		this.testSuite.add(new ExpTestCase(5, 1000, 1000));
		this.testSuite.add(new ExpTestCase(5, 1500, 1000));
		this.testSuite.add(new ExpTestCase(5, 2000, 1000));
		this.testSuite.add(new ExpTestCase(5, 2500, 1000));
		*/
		
		/**
		 * @date 2013-2-3 (500 => 1000 loops)
		 * DONE: @date 2013-2-3
		 */
		/**
		this.testSuite.add(new ExpTestCase(5, 1000, 1000));
		this.testSuite.add(new ExpTestCase(5, 1500, 1000));
		this.testSuite.add(new ExpTestCase(5, 2000, 1000));
		this.testSuite.add(new ExpTestCase(5, 2500, 1000));
		this.testSuite.add(new ExpTestCase(5, 3000, 1000));
		this.testSuite.add(new ExpTestCase(5, 5000, 1000));
		this.testSuite.add(new ExpTestCase(5, 10000, 1000));
		this.testSuite.add(new ExpTestCase(5, 15000, 1000));
		this.testSuite.add(new ExpTestCase(5, 20000, 1000));
		this.testSuite.add(new ExpTestCase(5, 25000, 1000));
		this.testSuite.add(new ExpTestCase(5, 30000, 1000));
		*/
		
		// out of memory 
//		this.testSuite.add(new ExpTestCase(5, 40000, 50));
//		this.testSuite.add(new ExpTestCase(5, 50000, 50));
		
		/** finished @date 2013-1-28 file: randomstat
		this.testSuite.add(new ExpTestCase(20, 1000, 200));
		this.testSuite.add(new ExpTestCase(20, 1500, 200));
		this.testSuite.add(new ExpTestCase(20, 2000, 200));
		this.testSuite.add(new ExpTestCase(20, 2500, 100));
		this.testSuite.add(new ExpTestCase(20, 3000, 100));
		this.testSuite.add(new ExpTestCase(20, 5000, 100));
		this.testSuite.add(new ExpTestCase(20, 10000, 100));
		this.testSuite.add(new ExpTestCase(20, 15000, 100));
		this.testSuite.add(new ExpTestCase(20, 20000, 100));
		this.testSuite.add(new ExpTestCase(20, 25000, 100));
		this.testSuite.add(new ExpTestCase(20, 30000, 100));
		*/
		
		/**
		 * adding more testcase:
		 * 
		 * @date 2013-1-30 file: randomstat
		 * @description tune the number of processes
		 */
		
		/**
		 *  out of memory
		 *  @date 2013-1-27
		 *  @description 1280MB VM
		 */
//		this.testSuite.add(new ExpTestCase(20, 40000, 50));
//		this.testSuite.add(new ExpTestCase(20, 50000, 50));
		
		/**
		 * @date 2013-2-2
		 * @description finished
		 */
		/**
		this.testSuite.add(new ExpTestCase(2, 500, 1000));
		this.testSuite.add(new ExpTestCase(2, 800, 1000));
		this.testSuite.add(new ExpTestCase(2, 1000, 1000));
		this.testSuite.add(new ExpTestCase(2, 1500, 1000));
		this.testSuite.add(new ExpTestCase(2, 1800, 1000));
		this.testSuite.add(new ExpTestCase(2, 2000, 1000));
		this.testSuite.add(new ExpTestCase(2, 2500, 1000));
		this.testSuite.add(new ExpTestCase(2, 3000, 1000));
		this.testSuite.add(new ExpTestCase(2, 4000, 1000));
		this.testSuite.add(new ExpTestCase(2, 5000, 1000));
		this.testSuite.add(new ExpTestCase(2, 6000, 1000));
		this.testSuite.add(new ExpTestCase(2, 8000, 1000));
		this.testSuite.add(new ExpTestCase(2, 10000, 1000));
		this.testSuite.add(new ExpTestCase(2, 12000, 1000));
		this.testSuite.add(new ExpTestCase(2, 15000, 1000));
		this.testSuite.add(new ExpTestCase(2, 20000, 1000));
		this.testSuite.add(new ExpTestCase(2, 25000, 1000));
		this.testSuite.add(new ExpTestCase(2, 30000, 1000));

		this.testSuite.add(new ExpTestCase(5, 500, 1000));
		this.testSuite.add(new ExpTestCase(5, 800, 1000));
		this.testSuite.add(new ExpTestCase(5, 4000, 1000));
		this.testSuite.add(new ExpTestCase(5, 6000, 1000));
		this.testSuite.add(new ExpTestCase(5, 8000, 1000));
		this.testSuite.add(new ExpTestCase(5, 10000, 1000));
		this.testSuite.add(new ExpTestCase(5, 12000, 1000));
		this.testSuite.add(new ExpTestCase(5, 15000, 1000));
		this.testSuite.add(new ExpTestCase(5, 20000, 1000));
		this.testSuite.add(new ExpTestCase(5, 25000, 1000));
		this.testSuite.add(new ExpTestCase(5, 30000, 1000));

		this.testSuite.add(new ExpTestCase(8, 500, 1000));
		this.testSuite.add(new ExpTestCase(8, 800, 1000));
		this.testSuite.add(new ExpTestCase(8, 1000, 1000));
		this.testSuite.add(new ExpTestCase(8, 1500, 1000));
		this.testSuite.add(new ExpTestCase(8, 1800, 1000));
		this.testSuite.add(new ExpTestCase(8, 2000, 1000));
		this.testSuite.add(new ExpTestCase(8, 2500, 1000));
		this.testSuite.add(new ExpTestCase(8, 3000, 1000));
		this.testSuite.add(new ExpTestCase(8, 4000, 1000));
		this.testSuite.add(new ExpTestCase(8, 5000, 1000));
		this.testSuite.add(new ExpTestCase(8, 6000, 1000));
		this.testSuite.add(new ExpTestCase(8, 8000, 1000));
		this.testSuite.add(new ExpTestCase(8, 10000, 1000));
		this.testSuite.add(new ExpTestCase(8, 12000, 1000));
		this.testSuite.add(new ExpTestCase(8, 15000, 1000));
		this.testSuite.add(new ExpTestCase(8, 20000, 1000));
		this.testSuite.add(new ExpTestCase(8, 25000, 1000));
		this.testSuite.add(new ExpTestCase(8, 30000, 1000));

		this.testSuite.add(new ExpTestCase(10, 500, 1000));
		this.testSuite.add(new ExpTestCase(10, 800, 1000));
		this.testSuite.add(new ExpTestCase(10, 1000, 1000));
		this.testSuite.add(new ExpTestCase(10, 1500, 1000));
		this.testSuite.add(new ExpTestCase(10, 1800, 1000));
		this.testSuite.add(new ExpTestCase(10, 2000, 1000));
		this.testSuite.add(new ExpTestCase(10, 2500, 1000));
		this.testSuite.add(new ExpTestCase(10, 3000, 1000));
		this.testSuite.add(new ExpTestCase(10, 4000, 1000));
		this.testSuite.add(new ExpTestCase(10, 5000, 1000));
		this.testSuite.add(new ExpTestCase(10, 6000, 1000));
		this.testSuite.add(new ExpTestCase(10, 8000, 1000));
		this.testSuite.add(new ExpTestCase(10, 10000, 1000));
		this.testSuite.add(new ExpTestCase(10, 12000, 1000));
		this.testSuite.add(new ExpTestCase(10, 15000, 1000));
		this.testSuite.add(new ExpTestCase(10, 20000, 1000));
		this.testSuite.add(new ExpTestCase(10, 25000, 1000));
		this.testSuite.add(new ExpTestCase(10, 30000, 1000));

		this.testSuite.add(new ExpTestCase(12, 500, 1000));
		this.testSuite.add(new ExpTestCase(12, 800, 1000));
		this.testSuite.add(new ExpTestCase(12, 1000, 1000));
		this.testSuite.add(new ExpTestCase(12, 1500, 1000));
		this.testSuite.add(new ExpTestCase(12, 1800, 1000));
		this.testSuite.add(new ExpTestCase(12, 2000, 1000));
		this.testSuite.add(new ExpTestCase(12, 2500, 1000));
		this.testSuite.add(new ExpTestCase(12, 3000, 1000));
		this.testSuite.add(new ExpTestCase(12, 4000, 1000));
		this.testSuite.add(new ExpTestCase(12, 5000, 1000));
		this.testSuite.add(new ExpTestCase(12, 6000, 1000));
		this.testSuite.add(new ExpTestCase(12, 8000, 1000));
		this.testSuite.add(new ExpTestCase(12, 10000, 1000));
		this.testSuite.add(new ExpTestCase(12, 12000, 1000));
		this.testSuite.add(new ExpTestCase(12, 15000, 1000));
		this.testSuite.add(new ExpTestCase(12, 20000, 1000));
		this.testSuite.add(new ExpTestCase(12, 25000, 1000));
		this.testSuite.add(new ExpTestCase(12, 30000, 1000));

		this.testSuite.add(new ExpTestCase(15, 500, 1000));
		this.testSuite.add(new ExpTestCase(15, 800, 1000));
		this.testSuite.add(new ExpTestCase(15, 1000, 1000));
		this.testSuite.add(new ExpTestCase(15, 1500, 1000));
		this.testSuite.add(new ExpTestCase(15, 1800, 1000));
		this.testSuite.add(new ExpTestCase(15, 2000, 1000));
		this.testSuite.add(new ExpTestCase(15, 2500, 1000));
		this.testSuite.add(new ExpTestCase(15, 3000, 1000));
		this.testSuite.add(new ExpTestCase(15, 4000, 1000));
		this.testSuite.add(new ExpTestCase(15, 5000, 1000));
		*/
		
		/**
		 * @date 2013-2-2
		 * @description reduce number of loops 
		 */
		
		/**
		 * done @date 2013-2-3
		 */
		/**
		this.testSuite.add(new ExpTestCase(15, 6000, 200));
		this.testSuite.add(new ExpTestCase(15, 8000, 200));
		this.testSuite.add(new ExpTestCase(15, 10000, 200));
		this.testSuite.add(new ExpTestCase(15, 12000, 200));
		this.testSuite.add(new ExpTestCase(15, 15000, 200));
		this.testSuite.add(new ExpTestCase(15, 20000, 200));
		this.testSuite.add(new ExpTestCase(15, 25000, 100));
		this.testSuite.add(new ExpTestCase(15, 30000, 100));

		this.testSuite.add(new ExpTestCase(20, 500, 1000));
		this.testSuite.add(new ExpTestCase(20, 800, 1000));
		*/
		
		/**
		this.testSuite.add(new ExpTestCase(20, 4000, 100));
		this.testSuite.add(new ExpTestCase(20, 6000, 100));
		this.testSuite.add(new ExpTestCase(20, 8000, 100));
		this.testSuite.add(new ExpTestCase(20, 10000, 100));
		this.testSuite.add(new ExpTestCase(20, 12000, 100));
		this.testSuite.add(new ExpTestCase(20, 15000, 100));
		this.testSuite.add(new ExpTestCase(20, 20000, 100));
		this.testSuite.add(new ExpTestCase(20, 25000, 100));
		this.testSuite.add(new ExpTestCase(20, 30000, 100));
		*/
	}
	
	public void addTestCase(ExpTestCase testCase)
	{
		this.testSuite.add(testCase);
	}
	
	public List<ExpTestCase> getTestCases()
	{
		return this.testSuite;
	}
}
