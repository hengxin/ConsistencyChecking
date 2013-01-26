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
	
//	@Test
	public void testCheck_file_jt_4_40()
	{
		IBasicObservationConstructor fcons = new FileBasicObservationConstructor("./test/testset/joint/jt_4_40");
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
	public void testCheck_file_jt_4_40_1()
	{
		IBasicObservationConstructor fcons = new FileBasicObservationConstructor("./test/testset/joint/jt_4_40_1");
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
	public void testCheck_file_jt_4_40_2()
	{
		IBasicObservationConstructor fcons = new FileBasicObservationConstructor("./test/testset/joint/jt_4_40_2");
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
