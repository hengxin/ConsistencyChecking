package cn.edu.nju.moon.consistency.checker;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.observation.FileRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.IRawObservationConstructor;

public class ReadIncCheckerTest
{
	Checker ri_checker_fig4_2b = null;
	Checker ri_checker_fig5_1b = null;
	Checker ri_checker_rescheduleread = null;
	Checker ri_checker_fig6 = null;
	
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testCheck()
	{
		// from Fig 4. Case 2b): R and D(R) are in the same process and D(R) is in R'-downset
//		IRawObservationConstructor frobcons_fig4_2b = new FileRawObservationConstructor("./test/testset/obfig4case2b");
//		ri_checker_fig4_2b = new ReadIncChecker(new ReadIncObservation(0, frobcons_fig4_2b.construct()));
//		this.ri_checker_fig4_2b.check();
		
		// from Fig 5. Case 1b): R and D(R) are in different processes and D(R) is in R'-downset
//		IRawObservationConstructor frobcons_fig5_1b = new FileRawObservationConstructor("./test/testset/obfig5case1b");
//		ri_checker_fig5_1b = new ReadIncChecker(new ReadIncObservation(0, frobcons_fig5_1b.construct()));
//		this.ri_checker_fig5_1b.check();

		// from file: rescheduleread
//		IRawObservationConstructor frobcons_rescheduleread = new FileRawObservationConstructor("./test/testset/rescheduleread");
//		ri_checker_rescheduleread = new ReadIncChe0cker(new ReadIncObservation(0, frobcons_rescheduleread.construct()));
//		this.ri_checker_rescheduleread.check();
		
		// from file: obfib6
//		IRawObservationConstructor frobcons_fig6 = new FileRawObservationConstructor("./test/testset/obfig6");
//		ri_checker_fig6 = new ReadIncChecker(new ReadIncObservation(0, frobcons_fig6.construct()), "obfib6");
//		this.ri_checker_fig6.check_part();
		
		/**
		 * @modified hengxin on 2013-1-5
		 * @reason refactor IChecker to Checker using Template Method design pattern
		 */
		IRawObservationConstructor frobcons_fig6 = new FileRawObservationConstructor("./test/testset/obfig6");
		ri_checker_fig6 = new ReadIncChecker(frobcons_fig6.construct(), "obfib6");
		this.ri_checker_fig6.check();
	}

}
