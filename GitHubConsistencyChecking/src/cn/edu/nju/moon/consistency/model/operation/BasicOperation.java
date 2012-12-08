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
//    private int index = -1;
    
	public BasicOperation(GenericOperation otherOp)
	{
		super(otherOp);
	}
	
	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}
	
}
