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

		for (BasicOperation bop : opListTemp)
		{	
			if (bop.isReadOp())	// READ {@link ReadIncOperation}
			{
				if (this.pid == masterPid)	// the {@link ReadIncProcess} with masterPid	
				{
					ReadIncOperation rriop = new ReadIncOperation(bop);
					rriop.setIndex(this.opList.size());
					this.opList.add(rriop);	// transform to READ {@link ReadIncOperation}
				}
			}
			else	// WRITE {@link ReadIncOperation}
			{
				ReadIncOperation wriop = new ReadIncOperation(bop);
				wriop.setIndex(this.opList.size());
				this.opList.add(wriop);
				ReadIncObservation.WRITEPOOL.put(wriop.toString(), wriop);
			}
		}
	}

	/**
	 * establish "program order" between {@link ReadIncOperation}s 
	 * in the same {@link ReadIncProcess}
	 */
	public void establishProgramOrder()
	{
		if (this.opList.size() == 0)
			return ;
		
		ReadIncOperation preOp = (ReadIncOperation) this.opList.get(0);
		ReadIncOperation curOp = null;
		int size = this.opList.size();
		
		for (int index = 1; index < size; index++)
		{
			curOp = (ReadIncOperation) this.opList.get(index);
			preOp.setProgramOrder(curOp);
			preOp = curOp;
		}
	}
	
	/**
	 * @see ReadIncObservation private method #establishWritetoOrder()
	 */
	public void establishWritetoOrder()
	{
		List<BasicOperation> opList = this.opList;
		ReadIncOperation rriop = null;
		ReadIncOperation wriop = null;
		int size = opList.size();
		for (int index = 0; index < size; index++)
		{	
			rriop = (ReadIncOperation) opList.get(index);
			if(rriop.isReadOp())	
			{
				wriop = rriop.fetchDictatingWrite();
				rriop.setRid(index);
				wriop.setWid(index);
				wriop.addWritetoOrder(rriop);
			}
		}
	}
	
	/**
	 * does some READ {@link ReadIncOperation} read value from later WRITE
	 * {@link ReadIncOperation} on the same {@link ReadIncProcess}
	 * 
	 * @return true, if it does; false, o.w..
	 * 
	 * @see ReadIncObservation#readLaterWrite()
	 */
	public boolean readLaterWrite()
	{
		ReadIncOperation rriop = null;
		ReadIncOperation wriop = null;
		for (BasicOperation riop : this.opList)
		{
			if (riop.isReadOp())	// check every READ
			{
				rriop = (ReadIncOperation) riop;
				wriop = rriop.getReadfromWrite();
				if (rriop.getPid() == wriop.getPid() && rriop.getIndex() > wriop.getIndex())
					return true;
			}
		}
		return false;
	}
	
}
