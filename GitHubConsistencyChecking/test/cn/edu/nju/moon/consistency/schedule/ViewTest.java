package cn.edu.nju.moon.consistency.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;

/**
 * @description  test for {@link View}
 * @author hengxin
 *
 */
public class ViewTest
{

	@Before
	public void setUp() throws Exception
	{
	}

//	@Test
	public void testSelf_check()
	{
		RawOperation bop1 = new RawOperation("wx1");
		RawOperation bop2 = new RawOperation("wz1");
		RawOperation bop3 = new RawOperation("rz1");
		RawOperation bop4 = new RawOperation("wx2");
		RawOperation bop5 = new RawOperation("wy1");
		RawOperation bop6 = new RawOperation("wx1");
		
		List<RawOperation> opList = new ArrayList<RawOperation>();
		opList.add(bop1);
		opList.add(bop2);
		opList.add(bop3);
		opList.add(bop4);
		opList.add(bop5);
		opList.add(bop6);
		
		View v1 = new View(opList);
		assertFalse("The view is not valid.", v1.self_check());
	}

//	@Test
	public void testSelf_check2()
	{
		RawOperation bop1 = new RawOperation("w?18");
		RawOperation bop2 = new RawOperation("w?3");
		RawOperation bop3 = new RawOperation("r?18");
		
		List<RawOperation> opList = new ArrayList<RawOperation>();
		opList.add(bop1);
		opList.add(bop2);
		opList.add(bop3);
		
		View v1 = new View(opList);
		assertFalse("The view is not valid.", v1.self_check());
	}
	
	@Test
	public void testGenerateValidView()
	{
		View v = View.generateValidView(10, 20, 100);
		System.out.println(v);
		assertTrue("The generated valid view is valid indeed", v.self_check());
	}
}
