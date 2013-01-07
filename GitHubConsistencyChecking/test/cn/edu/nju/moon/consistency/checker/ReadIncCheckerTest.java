package cn.edu.nju.moon.consistency.checker;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.FileRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.IRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.RandomRawObservationConstructor;

public class ReadIncCheckerTest
{
	
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testCheck()
	{
		GlobalData.VISUALIZATION = true;

		/**
		 * @description from Fig 4. Case 2b): R and D(R) are in the same process and D(R) is in R'-downset
		 * @author hengxin
		 * @date 2013-1-7 
		 */
		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/obfig4case2b");
		Checker ri_checker_fig4_2b = new ReadIncChecker(frobcons_fig4_2b.construct(), "obfig4");
//		ri_checker_fig4_2b.check();
		
		/**
		 * @description from Fig 5. Case 1b): R and D(R) are in different processes and D(R) is in R'-downset
		 * @author hengxin
		 * @date 2013-1-7
		 */
		IRawObservationConstructor frobcons_fig5_1b = new FileRawObservationConstructor("./test/testset/obfig5case1b");
		Checker ri_checker_fig5_1b = new ReadIncChecker(frobcons_fig5_1b.construct(), "obfig5");
//		ri_checker_fig5_1b.check();
		
		/**
		 * @description figure 6 (multiple WRITE)
		 * @modified hengxin on 2013-1-5
		 * @reason refactor IChecker to Checker using Template Method design pattern
		 */
		IRawObservationConstructor frobcons_fig6 = new FileRawObservationConstructor("./test/testset/obfig6");
		Checker ri_checker_fig6 = new ReadIncChecker(frobcons_fig6.construct(), "obfig6");
//		ri_checker_fig6.check();
		
		/**
		 * @description figure 7 (cycle detection)
		 * @author hengxin
		 * @date 2013-1-5
		 */
		IRawObservationConstructor fronbcons_fig7 = new FileRawObservationConstructor("./test/testset/obfig7");
		Checker ri_checker_fig7 = new ReadIncChecker(fronbcons_fig7.construct(), "obfig7");
//		ri_checker_fig7.check();
	}

	/**
	 * @description test (random observation)
	 * @author hengxin
	 * @date 2013-1-6
	 */
	@Test
	public void testCheck_random()
	{
		GlobalData.VISUALIZATION = true;

		IRawObservationConstructor randcons_0 = new RandomRawObservationConstructor(6, 10, 30, 50);
		Checker ri_checker_rand0 = new ReadIncChecker(randcons_0.construct(), randcons_0.get_ob_id());
		ri_checker_rand0.check();
	}
	
}
