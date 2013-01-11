package cn.edu.nju.moon.consistency.model.operation.factory;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;

/**
 * @description transform {@link BasicOperation} into {@link ReadIncOperation} 
 * @author hengxin
 * @date 2013-1-11
 */
public class ReadIncOperationTransformer implements IOperationTransformer
{

	/**
	 * transform {@link BasicOperation} into {@link ReadIncOperation}
	 * @param {@link BasicOperation} to be transformed
	 * @return resulting {@link ReadIncOperation}
	 */
	@Override
	public BasicOperation transform(BasicOperation bop)
	{
		return new ReadIncOperation(bop);
	}

}
