package cn.edu.nju.moon.consistency.model.checker;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.checker.IChecker;
import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.model.observation.FileRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.IRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;

public class ReadIncCheckerTest
{
	IChecker ri_checker = null;

	@Before
	public void setUp() throws Exception
	{
		IRawObservationConstructor frobcons = new FileRawObservationConstructor("./test/testset/obfig4");
		ri_checker = new ReadIncChecker(new ReadIncObservation(0, frobcons.construct()));
	}

	@Test
	public void testCheck()
	{
		this.ri_checker.check();
	}

}
