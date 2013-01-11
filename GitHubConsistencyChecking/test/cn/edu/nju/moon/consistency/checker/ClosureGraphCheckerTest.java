package cn.edu.nju.moon.consistency.checker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.observation.constructor.FileBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.IBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomBasicObservationConstructor;
import cn.edu.nju.moon.consistency.schedule.WeakSchedule;

/**
 * @description test for {@link ClosureGraphChecker}
 * @author hengxin
 * @date 2013-1-8
 */
public class ClosureGraphCheckerTest
{

	@Before
	public void setUp() throws Exception
	{
	}

//	@Test
	public void testCheck_part_fig4()
	{
		GlobalData.VISUALIZATION = true;
		
		IBasicObservationConstructor frobcons_fig4_2b = new FileBasicObservationConstructor("./test/testset/operationgraph/obfig4case2b");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
	}

//	@Test
	public void testCheck_part_fig5()
	{
		GlobalData.VISUALIZATION = true;
		
		IBasicObservationConstructor frobcons_fig4_2b = new FileBasicObservationConstructor("./test/testset/operationgraph/obfig5case1b");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
	}
	
//	@Test
	public void testCheck_part_fig6()
	{
		GlobalData.VISUALIZATION = true;
		
		IBasicObservationConstructor frobcons_fig4_2b = new FileBasicObservationConstructor("./test/testset/operationgraph/obfig6");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
	}
	
//	@Test
	public void testCheck_part_fig7()
	{
		GlobalData.VISUALIZATION = true;
		
		IBasicObservationConstructor frobcons = new FileBasicObservationConstructor("./test/testset/operationgraph/obfig7");
		Checker og_checker = new ClosureGraphChecker(frobcons.construct(), frobcons.get_ob_id());
		og_checker.check();
	}
	
	/**
	 * test for {@link Checker#check()}
	 */
//	@Test
	public void testCheck_fig7()
	{
		GlobalData.VISUALIZATION = true;
		
		IBasicObservationConstructor frobcons = new FileBasicObservationConstructor("./test/testset/operationgraph/obfig7");
		BasicObservation bob = frobcons.construct();
		Checker og_checker = new ClosureGraphChecker(bob, frobcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
		
		assertFalse("Fig7 observation does not satisfy PRAM Consistency", og_checker.check());
		assertTrue("None of the three processes satisfies PRAM Consistnecy", 
				Arrays.equals( ((WeakSchedule) og_checker.getSchedule()).getBinarySchedule(), new boolean[] {false, false, false}));
	}
	
	/**
	 * @description test (random observation)
	 * @author hengxin
	 * @date 2013-1-8
	 */
//	@Test
	public void testCheck_part_random()
	{
		GlobalData.VISUALIZATION = true;

		IBasicObservationConstructor randcons_0 = new RandomBasicObservationConstructor(10, 8, 15, 200);
		Checker og_checker_rand0 = new ClosureGraphChecker(randcons_0.construct(), randcons_0.get_ob_id());
		og_checker_rand0.check();
	}
	
//	@Test
	public void testCheck_part_random_1549()
	{
		GlobalData.VISUALIZATION = true;

		IBasicObservationConstructor randcons = new FileBasicObservationConstructor("./test/testset/randomclosure/1549");
		BasicObservation bob = randcons.construct();

		Checker cl_checker_rand = new ClosureGraphChecker(bob, randcons.get_ob_id() + "check", new WeakSchedule(bob.getProcNum()));
		assertFalse("Random 1459 does not satisfy PRAM Consistency", cl_checker_rand.check());
		assertTrue("Process 0 and Process 1 has legal view for PRAM Consistency",
				Arrays.equals(((WeakSchedule) cl_checker_rand.getSchedule()).getBinarySchedule(),
						new boolean[] {true, false, false, false, false, false, false, false, false, false}));
		assertFalse("The views in schedule are all valid", cl_checker_rand.getSchedule().valid());
		System.out.println(cl_checker_rand.getSchedule());
	}
	
//	@Test
	public void testCheck_part_jt_50_420()
	{
		GlobalData.VISUALIZATION = true;
		
		IBasicObservationConstructor frobcons = new FileBasicObservationConstructor("./test/testset/operationgraph/jt_50_420");
		Checker og_checker = new ClosureGraphChecker(frobcons.construct(), frobcons.get_ob_id());
		og_checker.check();
	}
	
}
