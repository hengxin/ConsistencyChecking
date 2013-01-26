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
	@Test
	public void perfEvalTest()
	{
		ExpTestSuite testSuite = new ExpTestSuite();
		for (ExpTestCase testCase : testSuite.getTestCases())
		{
			this.testCheck_random(testCase);
			testCase.store("./statistics/randomstat");
		}
	}

	private void testCheck_random(ExpTestCase testCase)
	{
		int procNum = testCase.getProcNum();
		int opNum = testCase.getOpNum();
		int loops = testCase.getLoops();
		
		Long cl_time = 0L;
		Long closure_total = 0L;
		Long ri_time = 0L;
		Long centric_total = 0L;
		
		IBasicObservationConstructor randcons = null;
		BasicObservation bob = null;
		Checker cl_checker = null;
		Checker ri_checker = null;
		int i = 0;
		try
		{
			for ( ; i < loops; i++)
			{
				randcons = new RandomBasicObservationConstructor(procNum, 100, 500, opNum, new RandomViewFactory());
			    bob = randcons.construct();
			    
			    System.err.println(i);
			    
			    cl_checker = new ClosureGraphChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
			    ri_checker = new ReadIncChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
			    
			    /** run the checking algorithms */
			    cl_time = System.currentTimeMillis();
			    cl_checker.check();
			    cl_time = System.currentTimeMillis() - cl_time;
			    closure_total += cl_time;
//			    System.out.println(cl_time);
			    
			    ri_time = System.currentTimeMillis();
			    ri_checker.check();
			    ri_time = System.currentTimeMillis() - ri_time;
			    centric_total += ri_time;
//			    System.out.println(ri_time);
			    
			    if (! cl_checker.getSchedule().compare(ri_checker.getSchedule()))
			    	System.out.println(i + ":" + bob);
			    
			    /** joint test */
			    assertTrue("Two checking algorithms should give the same result: " + i, cl_checker.getSchedule().compare(ri_checker.getSchedule()));
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
