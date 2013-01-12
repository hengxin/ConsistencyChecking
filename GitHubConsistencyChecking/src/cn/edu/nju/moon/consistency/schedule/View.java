package cn.edu.nju.moon.consistency.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;

/**
 * @description A {@link View} is a total order of all the {@link RawOperation}s 
 *   relevant to the particular Consistency Condition.
 *   
 * @author hengxin
 * @date 2013-1-9
 */
public class View
{
	private List<RawOperation> view = new ArrayList<RawOperation>();	/** a total order of {@link RawOperation}s */
	
	/**
	 * constructor
	 * @param list of operations as a view 
	 */
	public View(List<RawOperation> opList)
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
		/** schedule operations in bop-downset */
		for (BasicOperation bop : bob.getProcess(bob.getMasterPid()).getOpList())
			if (bop.isReadOp())	
				this.topoSorting(bop);
		
		/** picking up the remaining WRITEs */
		for (BasicOperation wop : bob.getWritePool().values())
		{
			if (wop.isInView())
				wop.resetInView();
			else
				this.view.add(wop);
		}
	}
	
	/**
	 * @return {@link #view}
	 */
	public List<RawOperation> getView()
	{
		return this.view;
	}
	
	/**
	 * performing DFS to give a topological sorting
	 * @param bop root operation from which DFS starts
	 */
	private void topoSorting(BasicOperation bop)
	{
		if (! bop.isInView())
		{
			bop.setInView();
			for (BasicOperation pre_bop : bop.getPredecessors())
				this.topoSorting(pre_bop);
			this.view.add(bop);
		}
	}
	
	/**
	 * to check whether {@link #view} satisfies PRAM Consistency:
	 * 	scan the {@link BasicOperation}s, record the active WRITEs for each variable, 
	 *  and check whether each READ works. 
	 *  
	 * @return true, if the {@link #view} satisfies PRAM Consistency; false, otherwise.
	 */
	public boolean self_check()
	{
		Map<String, RawOperation> activeWriteMap = new HashMap<String, RawOperation>();
		for (RawOperation op : this.view)
		{
			if (op.isReadOp())
			{	
				if (! activeWriteMap.get(op.getVariable()).equals(op.getDictatingWrite()))
					return false;
			}
			else
				activeWriteMap.put(op.getVariable(), op);
		}
		
		return true;
	}
	
	/**
	 * @return String form of view: ops are separated by whitespace
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (RawOperation rop : this.view)
			sb.append(rop.toString()).append(' ');
		return sb.toString();
	}
	
}
