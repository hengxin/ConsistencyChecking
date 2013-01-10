package cn.edu.nju.moon.consistency.model.process;

import java.util.List;

import cn.edu.nju.moon.consistency.checker.ClosureGraphChecker;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.observation.ClosureObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ClosureOperation;

/**
 * @description {@link ClosureProcess} consists of {@link ClosureOperation}
 *   and is used in {@link ClosureObservation} and {@link ClosureGraphChecker}  
 * 
 * @author hengxin
 * @date 2013-1-8
 */
public class ClosureProcess extends BasicProcess
{
	/**
	 * filter {@link BasicProcess} for specific purpose
	 * 
	 * @param masterPid process with masterPid is kept the same
	 * @param proc {@link BasicProcess} to be filtered
	 */
	public ClosureProcess(int masterPid, BasicProcess proc, BasicObservation bob)
	{
		super(proc.getPid(), bob);
		
		List<BasicOperation> opListTemp = proc.getOpListCopy();

		for (BasicOperation bop : opListTemp)
		{	
			if (bop.isReadOp())	// READ {@link ClosureOperation}
			{
				if (this.pid == masterPid)	// the {@link ClosureProcess} with masterPid	
				{
					ClosureOperation rclop = new ClosureOperation(bop);
					rclop.setIndex(this.opList.size());
					this.addOperation(rclop);
				}
			}
			else	// WRITE {@link ReadIncOperation}
			{
				ClosureOperation wclop = new ClosureOperation(bop);
				wclop.setIndex(this.opList.size());
				this.addOperation(wclop);
				bob.addWrite2Pool(wclop);
//				ClosureObservation.WRITEPOOL.put(wclop.toString(), wclop);
			}
		}
	}

//	/**
//	 * establish "program order" between {@link ClosureOperation}s 
//	 * in the same {@link ClosureProcess}
//	 */
//	public void establishProgramOrder()
//	{
//		if (this.opList.size() == 0)
//			return;
//		
//		ClosureOperation preOp = (ClosureOperation) this.opList.get(0);
//		ClosureOperation curOp = null;
//		int size = this.opList.size();
//		
//		for (int index = 1; index < size; index++)
//		{
//			curOp = (ClosureOperation) this.opList.get(index);
//			preOp.setProgramOrder(curOp);
//			preOp = curOp;
//		}
//	}
//	
//	/**
//	 * @see {@link ClosureObservation} private method #establishWritetoOrder()
//	 */
//	public void establishWritetoOrder()
//	{
//		List<BasicOperation> opList = this.opList;
//		ClosureOperation rclop = null;
//		ClosureOperation wclop = null;
//		int size = opList.size();
//		for (int index = 0; index < size; index++)
//		{	
//			rclop = (ClosureOperation) opList.get(index);
//			if(rclop.isReadOp())	
//			{
//				wclop = rclop.fetchDictatingWrite();
//				wclop.addWritetoOrder(rclop);
//			}
//		}
//	}
	
}
