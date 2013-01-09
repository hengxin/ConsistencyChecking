package cn.edu.nju.moon.consistency.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;

/**
 * @description A {@link View} is a total order of all the {@link BasicOperation}s 
 *   relevant to the particular Consistency Condition.
 *   
 * @author hengxin
 * @date 2013-1-9
 */
public class View
{
	private List<BasicOperation> view = new ArrayList<BasicOperation>();	/** a total order of {@link BasicOperation}s */
	
	public View(List<BasicOperation> opList)
	{
		this.view = opList;
	}
	
	/**
	 * Constructor: to construct {@link View} for process 
	 * 	with {@link BasicObservation#getMasterPid()}
	 * Approach: Scan the READ operations in this process and perform 
	 * topological sorting for each READ.
	 * 
	 * @param bob {@link BasicObservation} from which {@link View} is constructed 
	 * 	for Process with {@link BasicObservation#getMasterPid()}
	 */
	public View(BasicObservation bob)
	{
		for (BasicOperation bop : bob.getProcess(bob.getMasterPid()).getOpList())
		{
//			if (bop.isReadOp())
//				this.topoSorting(bop);
			
		}
	}
	
//	private void topoSorting(BasicOperation bop)
//	{
//		if (! bop.isInView())
//		{
//			bop.setInView();
//			for (BasicOperation op : bop.)
//		}
//	}
	
	/**
	 * to check whether {@link #view} satisfies PRAM Consistency:
	 * 	scan the {@link BasicOperation}s, record the active WRITEs for each variable, 
	 *  and check whether each READ works. 
	 *  
	 * @return true, if the {@link #view} satisfies PRAM Consistency; false, otherwise.
	 */
	public boolean self_check()
	{
		Map<String, BasicOperation> activeWriteMap = new HashMap<String, BasicOperation>();
		for (BasicOperation bop : this.view)
		{
			if (bop.isReadOp())
			{	
				if (! activeWriteMap.get(bop.getVariable()).equals(bop.getReadfromWrite()))
					return false;
			}
			else
				activeWriteMap.put(bop.getVariable(), bop);
		}
		
		return true;
	}
	
}
