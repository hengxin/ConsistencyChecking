package cn.edu.nju.moon.consistency.model.process;

import static org.junit.Assert.assertTrue;

import java.util.List;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.GenericOperation;
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
	/* {@link ReadIncChecker} related */ 
	private ReadIncOperation pre_rriop = new ReadIncOperation(
			new GenericOperation(GlobalData.READ, "", -1));		// for {@link ReadIncProcess} with masterPid
	private ReadIncOperation pre_wriop = new ReadIncOperation(
			new GenericOperation(GlobalData.WRITE, "", -1));	// for other {@link ReadIncProcess}es
	
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
//					this.opList.add(rriop);	// transform to READ {@link ReadIncOperation}
					this.addOperation(rriop);	// transform to READ {@link ReadIncOperation}
				}
			}
			else	// WRITE {@link ReadIncOperation}
			{
				ReadIncOperation wriop = new ReadIncOperation(bop);
				wriop.setIndex(this.opList.size());
//				this.opList.add(wriop);
				this.addOperation(wriop);	// transform to READ {@link ReadIncOperation}
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
				if (rriop.getPid() == wriop.getPid() && rriop.getIndex() < wriop.getIndex())
					return true;
			}
		}
		return false;
	}
	
	/**
	 * @return {@link #pre_wriop}
	 * 
	 * @constraints for {@link ReadIncProcess} other than that with masterPid 
	 * 		{@link ReadIncObservation#masterPid}, it is a WRITE.
	 */
	public ReadIncOperation get_pre_wriop()
	{
		return this.pre_wriop;
	}
	
	/**
	 * advance {@link #pre_wriop} to be new WRITE {@link ReadIncOperation} (@param cur_wriop)
	 * 
	 * @param cur_wriop new WRITE {@link ReadIncOperation}
	 * 
	 * @constraints @param cur_wriop must be WRITE {@link ReadIncOperation}
	 * 		and @param cur_wriop must be in this {@link ReadIncProcess}
	 */
	public void advance_pre_wriop(ReadIncOperation cur_wriop)
	{
		assertTrue("for ReadIncProcess with no masterPid, cur_wriop must be WRITE", cur_wriop.isWriteOp());
		assertTrue("new WRITE cur_wriop must be in this ReadIncProcess", cur_wriop.getPid() == this.pid);
		
		this.pre_wriop = cur_wriop;
	}
	
	/**
	 * @return {@link #pre_rriop}
	 * 
	 * @constraints for {@link ReadIncProcess} with masterPid 
	 * 		{@link ReadIncObservation#masterPid}, it is a READ.
	 */
	public ReadIncOperation get_pre_rriop()
	{
		return this.pre_rriop;
	}
	
	/**
	 * advance {@link #pre_rriop} to be new READ {@link ReadIncOperation} (@param cur_rriop)
	 * 
	 * @param cur_rriop new READ {@link ReadIncOperation}
	 * 
	 * @constraints @param cur_rriop must be READ {@link ReadIncOperation}
	 * 		and @param cur_rriop must be in this {@link ReadIncProcess}
	 */
	public void advance_pre_rriop(ReadIncOperation cur_rriop)
	{
		assertTrue("for ReadIncProcess with masterPid, cur_rriop must be READ", cur_rriop.isReadOp());
		assertTrue("new WRITE cur_rriop must be in this ReadIncProcess", cur_rriop.getPid() == this.pid);
		
		this.pre_rriop = cur_rriop;
	}
}
