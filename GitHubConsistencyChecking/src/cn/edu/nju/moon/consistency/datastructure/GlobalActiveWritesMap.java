package cn.edu.nju.moon.consistency.datastructure;

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
	
	public GlobalActiveWritesMap()
	{
		this.globalActiveWritesMap = new HashMap<String, ArrayList<ReadIncOperation>>();
		
		for (String var: GlobalData.VARSET)
		{
			this.globalActiveWritesMap.put(var, new ArrayList<ReadIncOperation>());
		}
	}
}
