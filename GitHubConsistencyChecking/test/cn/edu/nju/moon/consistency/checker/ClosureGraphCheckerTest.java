package cn.edu.nju.moon.consistency.checker;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.constructor.FileRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.IRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomRawObservationConstructor;

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
		
		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/operationgraph/obfig4case2b");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
	}

//	@Test
	public void testCheck_part_fig5()
	{
		GlobalData.VISUALIZATION = true;
		
		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/operationgraph/obfig5case1b");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
	}
	
//	@Test
	public void testCheck_part_fig6()
	{
		GlobalData.VISUALIZATION = true;
		
		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/operationgraph/obfig6");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
	}
	
//	@Test
	public void testCheck_part_fig7()
	{
		GlobalData.VISUALIZATION = true;
		
		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/operationgraph/obfig7");
		Checker og_checker_fig4_2b = new ClosureGraphChecker(frobcons_fig4_2b.construct(), frobcons_fig4_2b.get_ob_id());
		og_checker_fig4_2b.check();
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

		IRawObservationConstructor randcons_0 = new RandomRawObservationConstructor(10, 8, 15, 200);
		Checker og_checker_rand0 = new ClosureGraphChecker(randcons_0.construct(), randcons_0.get_ob_id());
		og_checker_rand0.check();
	}
	
//	@Test
	public void testCheck_part_random_1549()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor randcons_0 = new FileRawObservationConstructor("./test/testset/randomclosure/1549");
		Checker cl_checker_rand0 = new ClosureGraphChecker(randcons_0.construct(), randcons_0.get_ob_id());
		cl_checker_rand0.check();
	}
	
}
