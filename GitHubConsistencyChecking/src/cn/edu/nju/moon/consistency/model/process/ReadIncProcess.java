package cn.edu.nju.moon.consistency.model.process;

import java.util.List;

import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;

/**
 * @description {@link ReadIncProcess} filter READ BasicOperation
 * 	if it is not the process with masterPid
 * 
 * @author hengxin
 * @date 2012-12-7
 * 
 * @see {@link ReadIncObservation}
 */
public class ReadIncProcess extends RawProcess
{
	/**
	 * filter {@link RawProcess} for specific purpose
	 * 
	 * @param masterPid process with masterPid is kept the same
	 * @param proc {@link RawProcess} to be filtered
	 */
	public ReadIncProcess(int masterPid, RawProcess proc)
	{
		this.pid = proc.getPid();
		
		List<BasicOperation> opListTemp = proc.getOpListCopy();
		
		if(proc.getPid() != masterPid)	// not the process with masterPid
		{
			for (BasicOperation bop : opListTemp)
				if (! bop.isReadOp())	// filter READ BasicOperation
				{
					ReadIncOperation riop = new ReadIncOperation(bop);
					this.opList.add(riop);
					ReadIncObservation.WRITEPOOL.put(riop.toString(), riop);
				}
		}
	}

}
