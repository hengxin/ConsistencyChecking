package cn.edu.nju.moon.consistency.checker;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.constructor.FileRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.IRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomRawObservationConstructor;

public class ReadIncCheckerTest
{
	
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * @description from Fig 4. Case 2b): R and D(R) are in the same process and D(R) is in R'-downset
	 * @author hengxin
	 * @date 2013-1-7 
	 */
//	@Test
	public void testCheck_part_fig4()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/obfig4case2b");
		Checker ri_checker_fig4_2b = new ReadIncChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		ri_checker_fig4_2b.check();
	}
	
	/**
	 * @description from Fig 5. Case 1b): R and D(R) are in different processes and D(R) is in R'-downset
	 * @author hengxin
	 * @date 2013-1-7
	 */
//	@Test
	public void testCheck_part_fig5()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor frobcons_fig5_1b = new FileRawObservationConstructor("./test/testset/obfig5case1b");
		Checker ri_checker_fig5_1b = new ReadIncChecker(frobcons_fig5_1b.construct(), frobcons_fig5_1b.get_ob_id());
		ri_checker_fig5_1b.check();
	}
	
	/**
	 * @description figure 6 (multiple WRITE)
	 * @modified hengxin on 2013-1-5
	 * @reason refactor IChecker to Checker using Template Method design pattern
	 */
//	@Test
	public void testCheck_part_fig6()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor frobcons_fig6 = new FileRawObservationConstructor("./test/testset/obfig6");
		Checker ri_checker_fig6 = new ReadIncChecker(frobcons_fig6.construct(), frobcons_fig6.get_ob_id());
		ri_checker_fig6.check();
	}
	
	/**
	 * @description figure 7 (cycle detection)
	 * @author hengxin
	 * @date 2013-1-5
	 */
//	@Test
	public void testCheck_part_fig7()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor frobcons_fig7 = new FileRawObservationConstructor("./test/testset/obfig7");
		Checker ri_checker_fig7 = new ReadIncChecker(frobcons_fig7.construct(), frobcons_fig7.get_ob_id());
		ri_checker_fig7.check();
	}

	/**
	 * @description simple execution: wx1 wx2 ry1 rx1; wy1 
	 * @author hengxin
	 * @date 2013-1-9
	 */
//	@Test
	public void testCheck_part_wwprimer()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor frobcons = new FileRawObservationConstructor("./test/testset/readinc/wwprimer");
		Checker ri_checker = new ReadIncChecker(frobcons.construct(), frobcons.get_ob_id());
		ri_checker.check();
	}
	
	/**
	 * @description test (random observation)
	 * @author hengxin
	 * @date 2013-1-6
	 */
//	@Test
	public void testCheck_random()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor randcons_0 = new RandomRawObservationConstructor(10, 8, 15, 200);
		Checker ri_checker_rand0 = new ReadIncChecker(randcons_0.construct(), randcons_0.get_ob_id());
		ri_checker_rand0.check();
	}
	
//	@Test
	public void testCheck_random_1549()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor randcons_0 = new FileRawObservationConstructor("./test/testset/randomclosure/1549");
		Checker ri_checker_rand0 = new ReadIncChecker(randcons_0.construct(), randcons_0.get_ob_id());
		ri_checker_rand0.check();
	}
	
}
