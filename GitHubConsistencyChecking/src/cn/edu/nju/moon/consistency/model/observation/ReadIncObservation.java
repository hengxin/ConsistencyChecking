package cn.edu.nju.moon.consistency.model.observation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;
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
	// {@link ReadIncProcess} with masterPid is to be checked against PRAM consistency
	private int masterPid = -1;
	
	/**
	 *  pool of all WRITE operations
	 *  in the PRAM-distinct problem, WRITE operations are distinct.
	 */
	public static Map<String, ReadIncOperation> WRITEPOOL = new HashMap<String, ReadIncOperation>();	
	
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
		this.masterPid = masterPid;
		
		Set<Integer> pids = rob.getProcMap().keySet();
		for (int pid : pids)
		{
			this.procMap.put(pid, new ReadIncProcess(masterPid, rob.getProcMap().get(pid)));
		}
	}

	/**
	 * preprocessing the {@link ReadIncObservation}, including
	 * (1) establishing "program order" between {@link ReadIncOperation}
	 * (2) establishing "write to order" between {@link ReadIncOperation}
	 */
	public void preprocessing()
	{
		this.establishProgramOrder();
		this.establishWritetoOrder();
	}
	
	/**
	 * establishing "program order" between {@link ReadIncOperation}
	 */
	private void establishProgramOrder()
	{
		Set<Integer> pids = this.procMap.keySet();
		for (int pid : pids)
			((ReadIncProcess) this.procMap.get(pid)).establishProgramOrder();
	}
	
	/**
	 * establishing "write to order" between {@link ReadIncOperation} 
	 */
	private void establishWritetoOrder()
	{
		// all READ {@link ReadIncOperation}s are in the {@link ReadIncProcess} with #masterPid
		List<BasicOperation> opList = this.procMap.get(this.masterPid).getOpList();
		ReadIncOperation rriop = null;
		for (BasicOperation bop : opList)
			if(bop.isReadOp())
			{  
				rriop = (ReadIncOperation) bop;
				rriop.getDictatingWrite().addWritetoOrder(rriop);
			}
	}
}
