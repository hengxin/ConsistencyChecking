package cn.edu.nju.moon.consistency.model.observation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.edu.nju.moon.consistency.model.operation.GenericOperation;
import cn.edu.nju.moon.consistency.model.process.ReadIncProcess;

/**
 * @description filtered RawObservation:
 * 	all the READ operations are ignored except those reside on process with masterPid 
 * 
 * @author hengxin
 * @date 2012-12-7
 *
 */
public class ReadIncObservation extends RawObservation
{
	/**
	 *  pool of all WRITE operations
	 *  in the PRAM-distinct problem, WRITE operations are distinct.
	 */
	public static Map<String, GenericOperation> WRITEPOOL = new HashMap<String, GenericOperation>();	
	
	/**
	 * get the filtered observation in which READ operations
	 * are ignored except those reside on process with @param masterPid
	 * 
	 * @param masterPid process with masterPid is kept the same
	 * @param rob RawObservation to be filtered
	 * 
	 * @see {@link ReadIncProcess}
	 */
	public ReadIncObservation(int masterPid, RawObservation rob)
	{
		Set<Integer> pids = rob.getProcMap().keySet();
		for (int pid : pids)
		{
			this.procMap.put(pid, new ReadIncProcess(masterPid, rob.getProcMap().get(pid)));
		}
	}

}
