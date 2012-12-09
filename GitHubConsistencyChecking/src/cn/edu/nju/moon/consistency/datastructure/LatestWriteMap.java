package cn.edu.nju.moon.consistency.datastructure;

import java.util.HashMap;
import java.util.Map;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;

/**
 * @description for each {@link ReadIncOperation}, it keeps a record
 * 	of the latest preceding WRITE {@link ReadIncOperation} for each variable.
 * 
 * @author hengxin
 * @date 2012-12-9
 * 
 * @see ReadIncOperation#lwMap
 */
public class LatestWriteMap
{
	/**
	 * <var, operation> pair, in which:
	 * 		String: variable
	 * 		{@link ReadIncOperation}: latest WRITE
	 * 
	 * @constraints {@link ReadIncOperation} must be WRITE and perform on var
	 * 
	 */
	private Map<String, ReadIncOperation> latestWriteMap = null;
	
	public LatestWriteMap()
	{
		this.latestWriteMap = new HashMap<String, ReadIncOperation>();
		
		for (String var : GlobalData.VARSET)
			this.latestWriteMap.put(var, null);
	}
}
