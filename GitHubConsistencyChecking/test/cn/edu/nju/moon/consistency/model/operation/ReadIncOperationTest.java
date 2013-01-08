package cn.edu.nju.moon.consistency.model.operation;

/**
 * @description test the behaviour when {@link ReadIncOperation}s are stored in 
 * 	Set collection.
 * @author hengxin
 * @date 2013-1-4
 */
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.GlobalData;

public class ReadIncOperationTest
{

	Set<ReadIncOperation> opSet = new HashSet<ReadIncOperation>();
	
	@Before
	public void setUp() throws Exception
	{
	}
	
	@Test
	public void testEquals()
	{
		ReadIncOperation wriop = new ReadIncOperation(new RawOperation(GlobalData.WRITE, "x", 1));
		ReadIncOperation rriop1 = new ReadIncOperation(new RawOperation(GlobalData.READ, "x", 1));
		rriop1.setIndex(0);
		ReadIncOperation rriop2 = new ReadIncOperation(new RawOperation(GlobalData.READ, "x", 1));
		rriop2.setIndex(0);
		
		opSet.add(wriop);
		opSet.add(rriop1);
		opSet.add(rriop2);
		
		assertTrue("rriop1 and rriop2: references are not identical", rriop1 != rriop2);
		assertTrue("rriop1 and rriop2: are equal", rriop1.equals(rriop2));
		assertTrue("rriop1 and rriop2: are equal", opSet.size() == 2);
	}

}
