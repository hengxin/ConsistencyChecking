package cn.edu.nju.moon.consistency.model.observation;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ClosureOperation;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;
import cn.edu.nju.moon.consistency.model.process.BasicProcess;
import cn.edu.nju.moon.consistency.model.process.ReadIncProcess;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description raw observation is a collection of RawProcess
 * @modified hengxin on 2013-1-8
 * @reason refactor: no RawObservation any more; change to  
 */
public class BasicObservation
{
	/** {@link BasicProcess} with masterPid is to be checked against PRAM consistency **/
	protected int masterPid = -1;
	protected Map<Integer, BasicProcess> procMap = new HashMap<Integer, BasicProcess>();
	protected int totalOpNum = -1;
	protected Map<String, BasicOperation> write_pool = new HashMap<String, BasicOperation>();
	
	
	/**
	 * add process into this observation
	 * @param pid assign pid to this process to add
	 * @param process process to add
	 */
	public void addProcess(int pid, BasicProcess process)
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
		BasicProcess proc = this.procMap.get(pid);
		if(proc == null)
			proc = new BasicProcess(pid);
		proc.addOperation(op);
		
		this.procMap.put(pid, proc);
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
	 * does some READ {@link ReadIncOperation} read value from later WRITE
	 * {@link ReadIncOperation} on the same {@link ReadIncProcess} with masterPid
	 * 
	 * @return true, if some READ {@link ReadIncOperation} read value from later WRITE
	 * 	{@link ReadIncOperation} on the same {@link ReadIncProcess} with masterPid;
	 * 		   false, o.w..
	 * 
	 * @see ReadIncProcess#readLaterWrite()
	 * @see ReadIncChecker#check_part()
	 */
	public boolean readLaterWrite()
	{
		return this.procMap.get(this.masterPid).readLaterWrite();
	}
	
	/**
	 * is there no {@link ReadIncOperation} at all in the {@link ReadIncProcess}
	 * with masterPid. if it is the case, PRAM consistency is satisfied trivially.
	 * 
	 * @return true, if no {@link ReadIncOperation} 
	 * 	in {@link ReadIncProcess} with masterPid;	false, o.w..
	 */
	public boolean nullCheck()
	{
		if (this.procMap.get(this.masterPid).size() == 0)
			return true;
		return false;
	}
	
	/**
	 * establishing "program order" between {@link ClosureOperation}s
	 */
	protected void establishProgramOrder()
	{
		for (int pid : this.procMap.keySet())
			this.procMap.get(pid).establishProgramOrder();
	}
	
	/**
	 * establishing "write to order" between {@link ClosureOperation}s 
	 * and set rid for READ {@link ClosureOperation} and wid for corresponding
	 * WRITE {@link ClosureOperation} 
	 */
	protected void establishWritetoOrder()
	{
		// all READ {@link BasicOperation}s are in the {@link BasicProcess} with #masterPid
		this.procMap.get(this.masterPid).establishWritetoOrder(this);
	}
	
	/**
	 * process with pid
	 * @param pid id of process
	 * @return process with @param pid
	 */
	public BasicProcess getProcess(int pid)
	{
		return this.procMap.get(pid);
	}
	
	/**
	 * @return field {@link #procMap}
	 */
	public Map<Integer, BasicProcess> getProcMap()
	{
		return this.procMap;
	}
	
	/**
	 * @return number of Processes in this Observation
	 */
	public int getProcNum()
	{
		return this.procMap.size();
	}
	
	/**
	 * @return {@link #masterPid}
	 */
	public int getMasterPid()
	{
		return this.masterPid;
	}
	
	/**
	 * @return total number of operations
	 */
	public int getOpNum()
	{
		if (this.totalOpNum == -1)
		{
			this.totalOpNum = 0;
			for (BasicProcess proc : this.procMap.values())
				totalOpNum += proc.size();
		}
		
		return this.totalOpNum;
	}
	
	/** ############### {@link #write_pool} related ################ */
	
	/**
	 * store all WRITEs into {@link #write_pool}
	 */
	public void storeWrite2Pool()
	{
		for (int pid : this.procMap.keySet())
			for (BasicOperation bop : this.getProcess(pid).getOpList())
				if (bop.isWriteOp())
					this.write_pool.put(bop.toString(), bop);
	}
	
	/**
	 * @param opStr String form of WRITE operation to retrieve
	 * @return WRITE operation in {@link #write_pool} with key = @param opStr
	 */
	public BasicOperation getWrite(String opStr)
	{
		return this.write_pool.get(opStr);
	}
	
	/**
	 * get the dictating WRITE for some READ
	 * @param rop READ operation
	 * @return dictating WRITE for @param rop
	 */
	public BasicOperation getDictatingWrite(BasicOperation rop)
	{
		assertTrue("Get dictating WRITE of READ", rop.isReadOp());
		
		return this.getWrite(rop.toString().replaceFirst("r", "w"));
	}
	
	/**
	 * @return {@link #write_pool}
	 */
	public Map<String, BasicOperation> getWritePool()
	{
		return this.write_pool;
	}
	
	/**
	 * @return String format of {@link BasicObservation}
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
			for (RawOperation rop : this.procMap.get(pid).getOpList())
				sb.append(rop.toString()).append(' ');
			sb.append('\n');
		}
		
		return sb.toString();
	}
}
