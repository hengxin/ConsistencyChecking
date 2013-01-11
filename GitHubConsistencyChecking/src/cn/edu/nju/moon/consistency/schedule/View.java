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
	 * generate a {@link View} randomly
	 * @param varNum number of variables
	 * @param valRange range of values of variables
	 * @param opNum number of operations
	 * @return a {@link View} which contains @param opNum {@link RawOperation}
	 */
	public static View generateRandomView(int varNum, int valRange, int opNum)
	{
		List<RawOperation> opList = new ArrayList<RawOperation>();
		Random random = new Random();
		RawOperation rop = null;
		int loop = 0;

		for(int i = 0;i < opNum;)
		{
			loop++;

			rop = RawOperation.generateRandOperation(varNum, valRange);
			
			// if it is a Read operation, generate a corresponding Write operation
			if(rop.isReadOp())
			{
				opList.add(rop);
				i++;

				RawOperation wop = new RawOperation(GlobalData.WRITE, rop.getVariable(), rop.getValue());

				if(i == opNum)
				{
					if(! opList.contains(wop))
					{
						opList.remove(rop);
						i--;
					}
				}
				else
				{
					if(! opList.contains(wop))
					{
						opList.add(wop);
						i++;
					}
				}
			}
			else // Write Operation
			{
				if(! opList.contains(rop) && random.nextInt(3) == 0)
				{
					opList.add(rop);
					i++;
				}
			}
		}
		Collections.shuffle(opList);
		
		return new View(opList);
	}
	
	/**
	 * generate a valid {@link View} randomly
	 *  
	 * @param varNum number of variables
	 * @param valRange range of values of variables
	 * @param opNum number of operations
	 * @return a {@link View} which contains @param opNum {@link RawOperation}
	 * 
	 * @author hengxin
	 * @date 2013-1-11
	 */
	public static View generateValidView(int varNum, int valRange, int opNum)
	{
		Map<String, RawOperation> activeWriteMap = new HashMap<String, RawOperation>();	/** <var, op> pair */
		Map<String, RawOperation> writePool = new HashMap<String, RawOperation>();		/** <opStr, op> pair */
		
		int count = 0;
		Random rand = new Random();
		int ratio = 3;
		List<RawOperation> opList = new ArrayList<RawOperation>();
		
		while (count < opNum)
		{
			if (rand.nextInt(ratio) % ratio == 0)	// generate WRITE operation
			{
				while (true)
				{
					RawOperation wop = RawOperation.generateRawOperation(GlobalData.WRITE, varNum, valRange);
					if (! writePool.containsKey(wop.toString()))	// unique WRITE requirement
					{
						activeWriteMap.put(wop.getVariable(), wop);
						writePool.put(wop.toString(), wop);
						opList.add(wop);
						count++;
						break;
					}
				}
			}
			else	// generate READ operation according to activeWriteMap
			{
				if (activeWriteMap.size() != 0)
				{
					int index = rand.nextInt(activeWriteMap.size());
					String var = (String) activeWriteMap.keySet().toArray()[index];
					RawOperation rop = new RawOperation(GlobalData.READ, var, activeWriteMap.get(var).getValue());
					opList.add(rop);
					count++;
				}
			}
		}
		
		return new View(opList);
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
