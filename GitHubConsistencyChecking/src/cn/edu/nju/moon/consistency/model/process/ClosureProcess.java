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
	 * @param pid 		id of this {@link ClosureProcess}
	 * @param bob		{@link BasicObservation} from which 
	 * 		{@link ClosureProcess} is constructed
	 */
	public ClosureProcess(int masterPid, int pid, BasicObservation bob /** BasicProcess proc */)
	{
		super(pid);
		BasicProcess proc = bob.getProcess(pid);
		
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
			}
		}
	}

}
