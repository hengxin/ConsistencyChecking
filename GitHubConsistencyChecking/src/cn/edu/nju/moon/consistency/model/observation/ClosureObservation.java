package cn.edu.nju.moon.consistency.model.observation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nju.moon.consistency.checker.ClosureGraphChecker;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ClosureOperation;
import cn.edu.nju.moon.consistency.model.process.ClosureProcess;
import cn.edu.nju.moon.consistency.ui.DotUI;

/**
 * @description {@link ClosureObservation} is used in {@link ClosureGraphChecker}
 *   and is related to {@link ClosureOperation} and {@link ClosureProcess} 
 * 
 * @author hengxin
 * @date 2013-1-8
 */
public class ClosureObservation extends BasicObservation
{
	/**
	 * @modified hengxin on 2013-1-9
	 * @modification extracted to super class {@link BasicObservation} 
	 */
//	public static Map<String, ClosureOperation> WRITEPOOL = null;
	
	/** array of {@link ClosureOperation}s @see #encodeOperations() */
	private final ClosureOperation[] opArray;

	/** matrix of {@link ClosureOperation}s */
	private final boolean [][] opMatrix;

	/**
	 * get the filtered observation in which READ operations
	 * are ignored except those reside on process with @param masterPid
	 * 
	 * @param masterPid process with masterPid is kept the same
	 * @param rob RawObservation to be filtered
	 * 
	 * @see {@link ClosureProcess}
	 * 
	 * @TODO: using design pattern to refactor the creation of observation
	 */
	public ClosureObservation(int masterPid, BasicObservation rob)
	{
		/** initialize to avoid {@link NullPointerException} */
		super(rob.getProcNum());
		
		this.masterPid = masterPid;
		
		for (int pid : rob.getProcMap().keySet())
			this.procMap.put(pid, new ClosureProcess(masterPid, rob.getProcess(pid)));

		this.storeWrite2Pool();
		
		this.totalOpNum = this.getTotalOpNum();
		this.opArray = new ClosureOperation[this.totalOpNum];	/** initialize {#opArray} */
		this.opMatrix = new boolean[this.totalOpNum][this.totalOpNum];	/** initialize the matrix of {@link ClosureOperation}s */
		
		// ui
		DotUI.getInstance().visual_ob(this);
	}
	
	/**
	 * preprocessing the {@link ClosureObservation}, including
	 * (1) establishing "program order" between {@link ClosureOperation}
	 * (2) establishing "write to order" between {@link ClosureOperation}
	 * (3) fill its own data structure for checking: {@link #opArray} and {@link #opMatrix}
	 */
	@Override
	public void preprocessing()
	{
		super.preprocessing();	/** establish "program order" and "write to order" */
		
		this.fillOpArray();		/** fill the {#opArray} */
		this.fillOpMatrix();	/** fill the {#opMatrix} */
	}
	
	/**
	 * encode {@link ClosureOperation}s with unique global index
	 * which is calculated by the following formula:
	 * number of {@link ClosureOperation}s in previous {@link ClosureProcess}es
	 * 		+ write_index in its own {@link ClosureProcess}
	 */
	private void fillOpArray()
	{
		int procNum = this.getProcNum();
		int accIndex = 0;
		int globalIndex = 0;
		List<BasicOperation> opList = null;
		
		// encode operations for each process
		for(int pid = 0; pid < procNum; pid++)
		{
			try
			{
			opList = this.getProcess(pid)
				.getOpList();
			} catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(this.toString() + "pid = " + pid);
			}
			// encode each operation
			for(BasicOperation bop : opList)
			{
				globalIndex = accIndex + bop.getIndex();
				((ClosureOperation) bop).setGlobalIndex(globalIndex);
				// put this operation in the right position of array
				this.opArray[globalIndex] = (ClosureOperation) bop;
			}

			accIndex += opList.size();
		}
	}
	
	/**
	 * fill in the matrix of {@link ClosureOperation}s
	 * according to both program order and writeto order
	 */
	private void fillOpMatrix()
	{
		ClosureOperation temp_clop = null;
		for(ClosureOperation clop : this.opArray)
		{
			// program order
			temp_clop = (ClosureOperation) clop.getProgramOrder();
			if(temp_clop != null)
				this.opMatrix[clop.getGlobalIndex()][temp_clop.getGlobalIndex()] = true;

			// writeto order
			if(clop.isWriteOp())
				for(BasicOperation rclop : clop.getWritetoOrder())
					this.opMatrix[clop.getGlobalIndex()][((ClosureOperation) rclop).getGlobalIndex()] = true;
		}
	}
	
	/**
	 * apply the Warshall's algorithm to compute closure
	 *
	 * @return true, if some element of {@link #opMatrix} is set true;
	 * 		false, o.w..
	 *
	 * Please refer to CAV'06 for details
	 */
	public boolean closure()
	{
		boolean changed = false;

		for(int mid = 0; mid < this.totalOpNum; mid++)
			for(int src = 0; src < this.totalOpNum; src++)
				for(int dest = 0; dest < this.totalOpNum; dest++)
					if(! this.opMatrix[src][dest])
					{
						this.opMatrix[src][dest] = this.opMatrix[src][mid] && this.opMatrix[mid][dest];
						changed = changed || this.opMatrix[src][dest];	// is it changed? 
					}

		return changed;
	}
	
	/**
	 * to check whether the {@link #opMatrix} is cyclic;
	 * it means the violation of PRAM Consistency
	 *
	 * @return true, if the {@link #opMatrix} has some cycle; false, otherwise.
	 */
	public boolean cycle_check()
	{
		boolean cycle = false;
		
		// check the diagonal elements in opMatrix
		for(int dia = 0; dia < this.totalOpNum; dia++)
		{
			cycle = this.opMatrix[dia][dia];
			if (cycle)
				break;
		}

		return cycle;
	}
	
	/**
	 * apply W'WR rule.
	 *
	 * @return true, if any wprime2w type of edge is added; false, otherwise.
	 */
	public boolean apply_wprime2w_edge_rule()
	{
		boolean changed = false;

		ClosureOperation wop = null;
		ClosureOperation wprime_op = null;

		// 1) only the master process holds Read operations
		for(BasicOperation bop : this.getProcess(masterPid).getOpList())
		{
			// 2) for each Read operation (R)
			if(bop.isReadOp())
			{
				// 3) look up #opMatrix for all the Write precedences W' of R which is not W = D(R)
				for(int row = 0; row < this.totalOpNum; row++)
				{
					wprime_op = this.opArray[row];
					wop = (ClosureOperation) bop.getReadfromWrite();

					if(! wprime_op.isReadOp()
							&& wprime_op.getVariable().equals(bop.getVariable())
							&& ! wprime_op.equals(wop)
							&& this.opMatrix[row][((ClosureOperation) bop).getGlobalIndex()]
							&& ! this.isReachable(wprime_op, wop))
					{
						// 4) add edge W' -> W
						wprime_op.add_wprimew_order(wop);
						this.opMatrix[wprime_op.getGlobalIndex()][wop.getGlobalIndex()] = true;
						changed = true;
					}
				}
			}
		}

		return changed;
	}

	/**
	 * is @param to_op reachable from @param from_op
	 * @param from_op source operation
	 * @param to_op	  target operation
	 * @return true, if @param to_op is reachable from @param from_op; false, otherwise.
	 */
	private boolean isReachable(ClosureOperation from_op, ClosureOperation to_op)
	{
		return this.opMatrix[from_op.getGlobalIndex()][to_op.getGlobalIndex()];
	}
	
}
