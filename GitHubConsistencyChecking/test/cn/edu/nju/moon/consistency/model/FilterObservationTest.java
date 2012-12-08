package cn.edu.nju.moon.consistency.model;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.model.observation.RandomRawObservationConstructor;
import cn.edu.nju.moon.consistency.model.observation.RawObservation;

/**
 * test {@link ReadIncObservation}
 * 
 * @author hengxin
 * @date 2012-12-7
 */
public class FilterObservationTest
{
	RawObservation rob = null;
	ReadIncObservation fob = null;
	
	@Before
	public void setUp() throws Exception
	{
		RandomRawObservationConstructor rrocon = new RandomRawObservationConstructor();
		this.rob = rrocon.construct();
	}

	@Test
	public void testFilterObservation()
	{
		this.fob = new ReadIncObservation(0, this.rob);
		System.out.println(this.fob);
	}
}
