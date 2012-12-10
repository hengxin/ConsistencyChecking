package cn.edu.nju.moon.consistency.datastructure;

import static org.junit.Assert.assertTrue;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;

/**
 * @description the leftmost READ {@link ReadIncOperation} 
 * 		reachable from this {@link ReadIncOperation}; 
 * 
 * @author hengxin
 * @date 2012-12-9
 * 
 * @see {@link ReadIncOperation}{@link #earlistRead}
 */
public class EarliestRead
{
	private int earlistRead = -1;
	
	/**
	 * initialize the earliest {@link ReadIncOperation} (by {@link #earlistRead})
	 * reachable to be @param rriop
	 * 
	 * @param rriop the earliest reachable {@link ReadIncOperation}
	 * 
	 * @constraints @param rriop must be READ {@link ReadIncOperation}
	 */
	public void initEarlistRead(ReadIncOperation rriop)
	{
		assertTrue("Using READ ReadIncOperation as initial value", rriop.isReadOp());
		
		this.earlistRead = rriop.getRid();
	}
}
