package cn.edu.nju.moon.consistency.model.observation;

import java.util.HashMap;
import java.util.Map;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.GenericOperation;
import cn.edu.nju.moon.consistency.model.process.RawProcess;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description raw observation is a collection of RawProcess
 */
public class RawObservation
{
	protected Map<Integer, RawProcess> procMap = new HashMap<Integer, RawProcess>();
	protected int totalOpNum = -1;
	
	public void addProcess(int pid, RawProcess process)
	{
		this.procMap.put(pid, process);
	}
	
	/**
	 * append the @param op to the RawProcess with @param pid
	 * 
	 * @param pid id of RawProcess
	 * @param op BasicOperation to be added
	 */
	public void addOperation(int pid, BasicOperation op)
	{
		RawProcess proc = this.procMap.get(pid);
		if(proc == null)
			proc = new RawProcess(pid);
		proc.addOperation(op);
		this.procMap.put(pid, proc);
	}
	
	/**
	 * process with pid
	 * @param pid id of process
	 * @return process with @param pid
	 */
	public RawProcess getProcess(int pid)
	{
		return this.procMap.get(pid);
	}
	
	/**
	 * @return field {@link #procMap}
	 */
	public Map<Integer, RawProcess> getProcMap()
	{
		return this.procMap;
	}
	
	/**
	 * @return number of Processes in this Observation
	 */
	public int getSize()
	{
		return this.procMap.size();
	}
	
	/**
	 * @return total number of operations
	 */
	public int getOpNum()
	{
		if (this.totalOpNum == -1)
		{
			this.totalOpNum = 0;
			for (RawProcess proc : this.procMap.values())
				totalOpNum += proc.size();
		}
		
		return this.totalOpNum;
	}
	
	/**
	 * @return String format of {@link RawObservation}
	 * 	which is suitable to be stored in file
	 * 
	 * Format: 
	 * one line for each process
	 * operations in each process are separated by whitespace
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int pid : this.procMap.keySet())
		{
			for (GenericOperation rop : this.procMap.get(pid).getOpList())
				sb.append(rop.toString()).append(' ');
			sb.append('\n');
		}
		
		return sb.toString();
	}
}
