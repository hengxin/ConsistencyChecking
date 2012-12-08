package cn.edu.nju.moon.consistency.model.process;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description raw process is a list of BasicOperation
 *
 */
public class RawProcess
{
	protected int pid = -1;
	protected List<BasicOperation> opList = null;
	
	public RawProcess()
	{
		this.opList = new ArrayList<BasicOperation>();
	}
	
	public RawProcess(int pid)
	{
		this.pid = pid;
		this.opList = new ArrayList<BasicOperation>();
	}
	
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
}
