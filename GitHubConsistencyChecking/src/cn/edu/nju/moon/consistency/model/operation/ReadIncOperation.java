package cn.edu.nju.moon.consistency.model.operation;

/**
 * @description {@link ReadIncOperation} is specifically designed for {@link ReadIncChecker} algorithm.
 * 
 * @author hengxin
 * @date 2012-12-8
 */
public class ReadIncOperation extends BasicOperation
{
	private ReadIncOperation poOrder = null;		// program order 
	private ReadIncOperation write2Order = null;	// write to order 
	private ReadIncOperation wprimewrOrder = null;	// w'wr order
	
	public ReadIncOperation(GenericOperation otherOp)
	{
		super(otherOp);
	}

}
