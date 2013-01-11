package cn.edu.nju.moon.consistency.model.operation.factory;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ClosureOperation;

/**
 * @description transform {@link BasicOperation} into {@link ClosureOperation} 
 * @author hengxin
 * @date 2013-1-11
 */
public class ClosureOperationTransfomer implements IOperationTransformer
{

	/**
	 * transform {@link BasicOperation} into {@link ClosureOperation}
	 * @param {@link BasicOperation} to be transformed
	 * @return resulting {@link ClosureOperation}
	 */
	@Override
	public BasicOperation transform(BasicOperation bop)
	{
		return new ClosureOperation(bop);
	}

}
