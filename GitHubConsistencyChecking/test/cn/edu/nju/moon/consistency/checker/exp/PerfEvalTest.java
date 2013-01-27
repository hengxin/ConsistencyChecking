package cn.edu.nju.moon.consistency.checker.exp;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.checker.Checker;
import cn.edu.nju.moon.consistency.checker.ClosureGraphChecker;
import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.observation.constructor.IBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomBasicObservationConstructor;
import cn.edu.nju.moon.consistency.schedule.WeakSchedule;
import cn.edu.nju.moon.consistency.schedule.constructor.RandomValidViewFactory;
import cn.edu.nju.moon.consistency.schedule.constructor.RandomViewFactory;

public class PerfEvalTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	
	/**
	 * joint tests for random observation
	 */
//	@Test
	public void perfEvalTest()
	{
		ExpTestSuite testSuite = new ExpTestSuite();
		for (ExpTestCase testCase : testSuite.getTestCases())
		{
			this.testCheck_random(testCase);
			testCase.store("./statistics/randomstat");
		}
	}

	/**
	 * Read-Centric & ValidView
	 */
	@Test
	public void perfEvalRCValidTest()
	{
		ExpTestSuite testSuite = new ExpTestSuite();
		
		testSuite.addTestCase(new ExpTestCase(5, 10000, 20));
		testSuite.addTestCase(new ExpTestCase(5, 15000, 20));
		testSuite.addTestCase(new ExpTestCase(5, 20000, 20));
		testSuite.addTestCase(new ExpTestCase(5, 25000, 20));
		testSuite.addTestCase(new ExpTestCase(5, 30000, 20));
		
		testSuite.addTestCase(new ExpTestCase(20, 10000, 20));
		testSuite.addTestCase(new ExpTestCase(20, 15000, 20));
		testSuite.addTestCase(new ExpTestCase(20, 20000, 15));
		testSuite.addTestCase(new ExpTestCase(20, 25000, 15));
		testSuite.addTestCase(new ExpTestCase(20, 30000, 15));
		
		for (ExpTestCase testCase : testSuite.getTestCases())
		{
			Long centric_time = 0L;
			Long centric_total = 0L;
			IBasicObservationConstructor randcons = null;
			BasicObservation bob = null;
			Checker centric_checker = null;
			for (int i = 0; i < testCase.getLoops(); i++)
			{
				randcons = new RandomBasicObservationConstructor(testCase.getProcNum(), 100, 500, testCase.getOpNum(), new RandomValidViewFactory());
			    bob = randcons.construct();
			    
			    System.err.println(i);
			   
			    centric_checker = new ReadIncChecker(bob);

			    centric_time = System.currentTimeMillis();
			    centric_checker.check();
			    centric_time = System.currentTimeMillis() - centric_time;
			    centric_total += centric_time;
			}
			
			testCase.setCentricTime(centric_total);
			
			testCase.store("./statistics/rcvalidstat");
		}
	}
	
	private void testCheck_random(ExpTestCase testCase)
	{
		int procNum = testCase.getProcNum();
		int opNum = testCase.getOpNum();
		int loops = testCase.getLoops();
		
		Long cl_time = 0L;
		Long closure_total = 0L;
		Long centric_time = 0L;
		Long centric_total = 0L;
		
		IBasicObservationConstructor randcons = null;
		BasicObservation bob = null;
		Checker cl_checker = null;
		Checker centric_checker = null;
		int i = 0;
		try
		{
			for ( ; i < loops; i++)
			{
				randcons = new RandomBasicObservationConstructor(procNum, 100, 500, opNum, new RandomViewFactory());
			    bob = randcons.construct();
			    
			    System.err.println(i);
			    
			    cl_checker = new ClosureGraphChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
			    centric_checker = new ReadIncChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
			    
			    /** run the checking algorithms */
			    cl_time = System.currentTimeMillis();
			    cl_checker.check();
			    cl_time = System.currentTimeMillis() - cl_time;
			    closure_total += cl_time;
//			    System.out.println(cl_time);
			    
			    centric_time = System.currentTimeMillis();
			    centric_checker.check();
			    centric_time = System.currentTimeMillis() - centric_time;
			    centric_total += centric_time;
//			    System.out.println(ri_time);
			    
			    if (! cl_checker.getSchedule().compare(centric_checker.getSchedule()))
			    	System.out.println(i + ":" + bob);
			    
			    /** joint test */
			    assertTrue("Two checking algorithms should give the same result: " + i, cl_checker.getSchedule().compare(centric_checker.getSchedule()));
			}
			
			testCase.setClosureTime(closure_total);
			testCase.setCentricTime(centric_total);
//			System.out.println("Closure:" + closure_total);
//			System.out.println("ReadInc:" + centric_total);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("\n" + i + ":" + bob);
		}
	}
	
}
