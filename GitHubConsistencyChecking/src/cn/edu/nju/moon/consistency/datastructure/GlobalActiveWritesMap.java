package cn.edu.nju.moon.consistency.datastructure;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	 * @param var variable
	 * @return active WRITE {@link ReadIncOperation}s performing on variable @param var
	 */
	public ArrayList<ReadIncOperation> getActiveWrites(String var)
	{
		return this.globalActiveWritesMap.get(var);
	}
	
	/**
	 * @param var	variable
	 * @return		String form of active WRITE {@link ReadIncOperation}s performing on variable @param var
	 * 
	 * @reason used in {@link ReadIncChecker#readFromDW()}
	 */
	public List<String> getActiveWritesPool(String var)
	{
		List<String> gawmPool = new ArrayList<String>();
		for (ReadIncOperation riop : this.globalActiveWritesMap.get(var))
			gawmPool.add(riop.toString());
		
		return gawmPool;
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
	 * deactivate some WRITE in @param wriop from {@link #globalActiveWritesMap}
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
	 * deactive (remove) @param wriop from {@link #globalActiveWritesMap}
	 * 
	 * @param wriop WRITE {@link ReadIncOperation} be deactivated (removed) from
	 * @param var 	variable
	 * 
	 * @modified hengxin on 2013-1-4
	 * @warning  used in {@link ReadIncOperation#apply_wprimew_order()}
	 */
	public void deactivate(ReadIncOperation wriop)
	{
		this.globalActiveWritesMap.get(wriop.getVariable()).remove(wriop);
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
	
	/**
	 * String format of {@link #globalActiveWritesMap}
	 * [x={}; y={}; z={}; ...]
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		
		List<ReadIncOperation> riops = null;
		for (String var : GlobalData.VARSET)
		{
			riops = this.globalActiveWritesMap.get(var);
			sb.append(var).append('=');
			sb.append(this.getRiopListStr(riops));
			sb.append(';');
//			sb.append('\n');
		}
		sb.deleteCharAt(sb.length() - 1);	// delete the extra ';'
		sb.append(']');
		
		return sb.toString();
	}
	
	/**
	 * String form of a list of {@link ReadIncOperation}:
	 * {wx1,wx3...,wx8}
	 * 
	 * @param riopList list of {@link ReadIncOperation}
	 * @return String form of a list of {@link ReadIncOperation} 
	 */
	private String getRiopListStr(List<ReadIncOperation> riopList)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		for (ReadIncOperation riop : riopList)
			sb.append(riop.toString()).append(',');
			
		if (riopList.size() != 0)
			sb.deleteCharAt(sb.length() - 1);	// delete the extra ','
		sb.append(']');
		
		return sb.toString();
	}
}
