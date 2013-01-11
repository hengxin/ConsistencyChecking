package cn.edu.nju.moon.consistency.model.operation.factory;

import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;

/**
 * transform one kind of operation (mainly {@link BasicOperation}) into another
 * ({@link ClosureOperation} and {@link ReadIncOperation})
 * 
 * @author hengxin
 * @date 2013-1-11
 * 
 * @design_pattern (Simple) Factory Method
 */
public interface IOperationTransformer
{
	/**
	 * transform one kind of operation into another
	 * @param bop {@link BasicOperation} to be transformed
	 * @return resulting {@link BasicOperation}
	 */
	public abstract BasicOperation transform(BasicOperation bop);
}
