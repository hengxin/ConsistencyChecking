package cn.edu.nju.moon.consistency.datastructure;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;

/**
 * @description {@link GlobalActiveWritesMap} is a global data structure
 * 	for {@link ReadIncChecker}. It maintains the "active" WRITE 
 * 	{@link ReadIncOperation}s for each variable. 
 * 
 * @author hengxin
 * @date 2012-12-9
 * 
 * @see ReadIncChecker#gawsMap
 */
public class GlobalActiveWritesMap
{
	/**
	 * <var, operations> pair, in which:
	 * 		String: variable
	 * 		ArrayList<ReadIncOperation>: {@link ReadIncOperation}s
	 * 
	 * @constraints {@link ReadIncOperation} must be WRITE and perform on var
	 */
	private HashMap<String,ArrayList<ReadIncOperation>> globalActiveWritesMap = null;
	
	/**
	 * @constructor initialize {@link #globalActiveWritesMap}
	 */
	public GlobalActiveWritesMap()
	{
		this.globalActiveWritesMap = new HashMap<String, ArrayList<ReadIncOperation>>();
		
		for (String var: GlobalData.VARSET)
		{
			this.globalActiveWritesMap.put(var, new ArrayList<ReadIncOperation>());
		}
	}
	
	/**
	 * @param var varaible
	 * @return active WRITE {@link ReadIncOperation} performing on variable @param var
	 */
	public ArrayList<ReadIncOperation> getActiveWrites(String var)
	{
		return this.globalActiveWritesMap.get(var);
	}
	
	/**
	 * replace the value of appropriate <wriop.variable, operations> 
	 * with a singleton set containing @param wriop 
	 * 
	 * @param wriop WRITE {@link ReadIncOperation} to be replaced
	 * 
	 * @constraints @param wriop must be WRITE {@link ReadIncOperation} 
	 */
	public void replace(ReadIncOperation wriop)
	{
		assertTrue("can only be replaced by WRITE ReadIncOperation", wriop.isWriteOp());
		
		ArrayList<ReadIncOperation> wriop_list = new ArrayList<ReadIncOperation>();
		wriop_list.add(wriop);
		this.globalActiveWritesMap.put(wriop.getVariable(), wriop_list);
	}
	
	/**
	 * deactivate some WRITEs in @param wriop from {@link #globalActiveWritesMap}
	 *  
	 * @param ReadIncOperation WRITE {@link ReadIncOperation} whose LatestWrite may be removed
	 * 	from {@link #globalActiveWritesMap}
	 * 
	 * @constraints @param wriop must be WRITE {@link ReadIncOperation}
	 */
	public void deactivateFrom(ReadIncOperation wriop)
	{
		assertTrue("deactivate WRITEs from WRITE ReadIncOperation", wriop.isWriteOp());
		
		String var = wriop.getVariable();
		ReadIncOperation latestWrite = wriop.getLatestWriteMap().getLatestWrite(var);
		if (latestWrite != null)
			this.globalActiveWritesMap.get(var).remove(latestWrite);
	}
	
	/**
	 * add new active WRITE into {@link #globalActiveWritesMap}
	 * 
	 * @param wriop new active WRITE {@link ReadIncOperation} to be added
	 * 
	 * @constraints @param wriop must be WRITE {@link ReadIncOperation}
	 */
	public void addActiveWrite(ReadIncOperation wriop)
	{
		assertTrue("active write is of course WRITE", wriop.isWriteOp());
		
		String var = wriop.getVariable();
		this.globalActiveWritesMap.get(var).add(wriop);
	}
}
