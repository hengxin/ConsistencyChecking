package cn.edu.nju.moon.consistency.schedule;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;

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

	@Test
	public void testSelf_check()
	{
		BasicOperation bop1 = new BasicOperation("wx1");
		BasicOperation bop2 = new BasicOperation("wz1");
		BasicOperation bop3 = new BasicOperation("rz1");
		BasicOperation bop4 = new BasicOperation("wx2");
		BasicOperation bop5 = new BasicOperation("wy1");
		BasicOperation bop6 = new BasicOperation("wx1");
		
		List<BasicOperation> opList = new ArrayList<BasicOperation>();
		opList.add(bop1);
		opList.add(bop2);
		opList.add(bop3);
		opList.add(bop4);
		opList.add(bop5);
		opList.add(bop6);
		
		View v1 = new View(opList);
		assertFalse("The view is not valid.", v1.self_check());
	}

}
