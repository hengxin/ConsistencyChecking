package cn.edu.nju.moon.consistency.model.observation;

import org.junit.Before;
import org.junit.Test;

/**
 * test {@link ReadIncObservation}
 * 
 * @author hengxin
 * @date 2012-12-7
 */
public class ReadIncObservationTest
{
	ReadIncObservation random_riob = null;
	ReadIncObservation file_riob = null;
	
	@Before
	public void setUp() throws Exception
	{
	}
	
//	@Test
//	public void testPreprocessing()
//	{
//		IRawObservationConstructor rrobcons = new RandomRawObservationConstructor();
//		this.random_riob = new ReadIncObservation(0, rrobcons.construct());
//		this.random_riob.preprocessing();
//		
//		IRawObservationConstructor frobcons = new FileRawObservationConstructor("./test/testset/filerawobservation0");
//		this.file_riob = new ReadIncObservation(0, frobcons.construct());
//		this.file_riob.preprocessing();
//	}
}
