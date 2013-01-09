package cn.edu.nju.moon.consistency.model.process;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ClosureOperation;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description raw process is a list of {@link BasicOperation}
 *
 * @modified hengxin on 2013-1-8
 * @reason refactor: no RawProcess (in contrast to {@link RawOperation} 
 * 	change to {@link BasicProcess}
 */
public class BasicProcess
{
	protected int pid = -1;
	protected List<BasicOperation> opList = null;
	protected BasicObservation bob = null;	/** backward reference to {@link BasicObservation} */
	
	public BasicProcess()
	{
		this.opList = new ArrayList<BasicOperation>();
	}
	
	public BasicProcess(int pid)
	{
		this.pid = pid;
		this.opList = new ArrayList<BasicOperation>();
	}
	
	/**
	 * add the {@link BasicOperation} to the {@link BasicProcess}
	 * and set its pid to the id of {@link BasicProcess}
	 * 
	 * @param op {@link BasicOperation} to be added
	 */
	public void addOperation(BasicOperation op)
	{
		op.setPid(pid);
		this.opList.add(op);
	}
	
	public int getPid()
	{
		return this.pid;
	}
	
	/**
	 * return the {@link BasicOperation} with index
	 * @param index index of {@link BasicOperation} to return
	 */
	public BasicOperation getOperation(int index)
	{
		return this.opList.get(index);
	}
	
	/**
	 * @return the immutable field {@link #opList}
	 * @see {@link #getOpListCopy()} 
	 */
	public final List<BasicOperation> getOpList()
	{
		return this.opList;
	}
	
	/**
	 * @return the mutable copy of field {@link #opList}
	 * @see {@link #getOpList()}
	 */
	public List<BasicOperation> getOpListCopy()
	{
		return new ArrayList<BasicOperation>(this.opList);
	}
	
	/**
	 * @return number of {@link BasicOperation} in this {@link BasicProcess}
	 */
	public int size()
	{
		return this.opList.size();
	}
	
	/**
	 * establish "program order" between {@link ClosureOperation}s 
	 * in the same {@link ClosureProcess}
	 */
	public void establishProgramOrder()
	{
		if (this.opList.size() == 0)
			return;
		
		BasicOperation preOp = this.opList.get(0);
		BasicOperation curOp = null;
		int size = this.opList.size();
		
		for (int index = 1; index < size; index++)
		{
			curOp = this.opList.get(index);
			preOp.setProgramOrder(curOp);
			preOp = curOp;
		}
	}
	
	/**
	 * establish WritetoOrder: D(R) => R
	 */
	public void establishWritetoOrder()
	{
		List<BasicOperation> opList = this.opList;
		BasicOperation rclop = null;
		BasicOperation wclop = null;
		int size = opList.size();
		for (int index = 0; index < size; index++)
		{	
			rclop = opList.get(index);
			if(rclop.isReadOp())	
			{
//				wclop = rclop.fetchDictatingWrite();
				wclop = this.bob.getDictatingWrite(rclop);
				wclop.addWritetoOrder(rclop);
			}
		}
	}
	
	/**
	 * does some READ {@link BasicOperation} read value from later WRITE
	 * {@link BasicOperation} on the same {@link BasicProcess}
	 * 
	 * @return true, if it does; false, otherwise.
	 */
	public boolean readLaterWrite()
	{
		BasicOperation wriop = null;
		for (BasicOperation riop : this.opList)
		{
			if (riop.isReadOp())	// check every READ
			{
				wriop = riop.getReadfromWrite();
				if (riop.getPid() == wriop.getPid() && riop.getIndex() < wriop.getIndex())
					return true;
			}
		}
		return false;
	}
	
}
