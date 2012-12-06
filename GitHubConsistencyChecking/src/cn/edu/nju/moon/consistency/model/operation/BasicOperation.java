package cn.edu.nju.moon.consistency.model.operation;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description basic operation which resides on some process
 * 	and thus has the fields pid and index.
 */
public class BasicOperation extends GenericOperation
{
    private int pid = -1;
    private int index = -1;
    
	public BasicOperation(GenericOperation otherOp, int pid, int index)
	{
		super(otherOp);
		
		// specific to BasicOperation
		this.pid = pid;
		this.index = index;
	}
	
	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}
	
}
