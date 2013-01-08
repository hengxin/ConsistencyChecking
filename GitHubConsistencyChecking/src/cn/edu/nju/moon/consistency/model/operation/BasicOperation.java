package cn.edu.nju.moon.consistency.model.operation;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description basic operation which resides on some process
 * 	and thus has the field pid.
 */
public class BasicOperation extends GenericOperation
{
	/**
	 * "pid" depend on the RawProcess 
	 * on which the BasicOperation reside
	 */
    private int pid = -1;
    
    /** index in process */
    private int index = -1;	
    
	public BasicOperation(GenericOperation otherOp)
	{
		super(otherOp);
	}
	
	public BasicOperation(String opStr)
	{
		super(opStr);
	}
	
	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}
	
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	
	/**
	 * if the {@link BasicOperation}s are both READ, they may be differentiated from
	 * each other with respect to the field {@link #index}.
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean basicComparison = super.equals(obj);
		
		if (basicComparison && this.isReadOp())
			return this.index == ((BasicOperation) obj).index;
		
		return basicComparison;
	}
	
}
