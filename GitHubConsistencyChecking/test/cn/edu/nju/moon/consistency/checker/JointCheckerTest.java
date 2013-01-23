package cn.edu.nju.moon.consistency.checker;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.observation.constructor.FileBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.IBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomBasicObservationConstructor;
import cn.edu.nju.moon.consistency.schedule.WeakSchedule;
import cn.edu.nju.moon.consistency.schedule.constructor.RandomValidViewFactory;
import cn.edu.nju.moon.consistency.schedule.constructor.RandomViewFactory;

/**
 * joint tests for both {@link ClosureGraphChecker} and {@link ReadIncChecker}
 * 
 * @author hengxin
 * @date 2013-1-10
 */
public class JointCheckerTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * joint tests for random observation
	 */
	@Test
	public void testCheck_random()
	{
//		GlobalData.VISUALIZATION = true;

		Long cl_time = 0L;
		Long cl_total = 0L;
		Long ri_time = 0L;
		Long ri_total = 0L;
		
		IBasicObservationConstructor randcons = null;
		BasicObservation bob = null;
		Checker cl_checker = null;
		Checker ri_checker = null;
		int i = 0;
		try
		{
			for ( ; i < 10000; i++)
			{
				randcons = new RandomBasicObservationConstructor(15, 100, 100, 5000, new RandomValidViewFactory());
			    bob = randcons.construct();
			    
//			    System.out.println("Original observation: \n" + bob.toString());
			    System.err.println(i);
			    
			    cl_checker = new ClosureGraphChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
			    ri_checker = new ReadIncChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
			    
			    /** run the checking algorithms */
			    cl_time = System.currentTimeMillis();
			    cl_checker.check();
			    cl_time = System.currentTimeMillis() - cl_time;
			    cl_total += cl_time;
			    System.out.println(cl_time);
			    
			    ri_time = System.currentTimeMillis();
			    ri_checker.check();
			    ri_time = System.currentTimeMillis() - ri_time;
			    ri_total += ri_time;
			    System.out.println(ri_time);
			    
			    /** oracles only for test on valid view */
//			    assertTrue("Closure algorithm goes wrong.", cl_checker.getSchedule().valid());
//			    assertTrue("ReadInc algorithm goes wrong.", ri_checker.getSchedule().valid());
			    
			    if (! cl_checker.getSchedule().compare(ri_checker.getSchedule()))
			    	System.out.println(i + ":" + bob);
			    /** joint test */
			    assertTrue("Two checking algorithms should give the same result: " + i, cl_checker.getSchedule().compare(ri_checker.getSchedule()));
			}
			System.out.println("Closure:" + cl_total);
			System.out.println("ReadInc:" + ri_total);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("\n" + i + ":" + bob);
		}
	}

//	@Test
	public void testCheck_file_jt_50_420()
	{
		IBasicObservationConstructor fcons = new FileBasicObservationConstructor("./test/testset/readinc/jt_50_420");
		BasicObservation bob = fcons.construct();
		
		Checker cl_checker = new ClosureGraphChecker(bob, fcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
		Checker ri_checker = new ReadIncChecker(bob, fcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
	    
	    /** run the checking algorithms */
	    cl_checker.check();
	    ri_checker.check();
	    
	    /** joint test */
	    assertTrue("Two checking algorithms should give the same result: ", cl_checker.getSchedule().compare(ri_checker.getSchedule()));
	}
	
//	@Test
	public void testCheck_file_jt_50_10_1720()
	{
		IBasicObservationConstructor fcons = new FileBasicObservationConstructor("./test/testset/joint/jt_50_10_1720");
		BasicObservation bob = fcons.construct();
		
		Checker cl_checker = new ClosureGraphChecker(bob, fcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
		Checker ri_checker = new ReadIncChecker(bob, fcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
	    
	    /** run the checking algorithms */
	    cl_checker.check();
	    ri_checker.check();
	    
	    /** joint test */
	    assertTrue("Two checking algorithms should give the same result: ", cl_checker.getSchedule().compare(ri_checker.getSchedule()));
	}
	
//	@Test
	public void testCheck_file_jt_50_10_1726()
	{
		IBasicObservationConstructor fcons = new FileBasicObservationConstructor("./test/testset/joint/jt_50_10_1726");
		BasicObservation bob = fcons.construct();
		
		Checker cl_checker = new ClosureGraphChecker(bob, fcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
		Checker ri_checker = new ReadIncChecker(bob, fcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
	    
	    /** run the checking algorithms */
	    cl_checker.check();
	    ri_checker.check();
	    
	    /** joint test */
	    assertTrue("Two checking algorithms should give the same result: ", cl_checker.getSchedule().compare(ri_checker.getSchedule()));
	}
}
