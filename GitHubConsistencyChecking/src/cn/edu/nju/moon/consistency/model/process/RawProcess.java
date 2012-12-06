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
	private int pid = -1;
	private List<BasicOperation> opList = null;
	
	public RawProcess(int pid)
	{
		this.pid = pid;
		this.opList = new ArrayList<BasicOperation>();
	}
	
	public void addOperation(BasicOperation op)
	{
		op.setPid(pid);
		op.setIndex(this.opList.size());
		
		this.opList.add(op);
	}
}
