package cn.edu.nju.moon.consistency.model.process;

import static org.junit.Assert.assertTrue;

import java.util.List;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;
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
public class ReadIncProcess extends BasicProcess
{
	/* {@link ReadIncChecker} related */
	private ReadIncOperation cur_rriop = null;	// the current READ {@link ReadIncOperation} to be check
	
	private ReadIncOperation pre_rriop = new ReadIncOperation(
			new RawOperation(GlobalData.READ, GlobalData.DUMMYVAR, -1));		// for {@link ReadIncProcess} with masterPid
	private ReadIncOperation pre_wriop = new ReadIncOperation(
			new RawOperation(GlobalData.WRITE, GlobalData.DUMMYVAR, -1));	// for other {@link ReadIncProcess}es
	
	/**
	 * filter {@link BasicProcess} for specific purpose
	 * 
	 * @param masterPid process with masterPid is kept the same
	 * @param proc {@link BasicProcess} to be filtered
	 */
	public ReadIncProcess(int masterPid, BasicProcess proc, ReadIncObservation riob)
	{
		this.pid = proc.getPid();
		this.bob = riob;

		List<BasicOperation> opListTemp = proc.getOpListCopy();

		for (BasicOperation bop : opListTemp)
		{	
			if (bop.isReadOp())	// READ {@link ReadIncOperation}
			{
				if (this.pid == masterPid)	// the {@link ReadIncProcess} with masterPid	
				{
					ReadIncOperation rriop = new ReadIncOperation(bop);
					rriop.setIndex(this.opList.size());
					this.addOperation(rriop);
				}
			}
			else	// WRITE {@link ReadIncOperation}
			{
				ReadIncOperation wriop = new ReadIncOperation(bop);
				wriop.setIndex(this.opList.size());
				this.addOperation(wriop);
				riob.addWrite2Pool(wriop);
//				ReadIncObservation.WRITEPOOL.put(wriop.toString(), wriop);
			}
		}
	}

//	/**
//	 * establish "program order" between {@link ReadIncOperation}s 
//	 * in the same {@link ReadIncProcess}
//	 */
//	public void establishProgramOrder()
//	{
//		if (this.opList.size() == 0)
//			return;
//		
//		ReadIncOperation preOp = (ReadIncOperation) this.opList.get(0);
//		ReadIncOperation curOp = null;
//		int size = this.opList.size();
//		
//		for (int index = 1; index < size; index++)
//		{
//			curOp = (ReadIncOperation) this.opList.get(index);
//			preOp.setProgramOrder(curOp);
//			preOp = curOp;
//		}
//	}
	
	/**
	 * @see ReadIncObservation private method #establishWritetoOrder()
	 */
	@Override
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
//				wriop = rriop.fetchDictatingWrite();
				wriop = (ReadIncOperation) this.bob.getDictatingWrite(rriop);
				rriop.getEarliestRead().setEarlistReadInt(index);	/** initialize earliest read */
				wriop.setWid(index);								/** set {#wid} */
				
				wriop.addWritetoOrder(rriop);
			}
		}
	}
	
//	/**
//	 * does some READ {@link ReadIncOperation} read value from later WRITE
//	 * {@link ReadIncOperation} on the same {@link ReadIncProcess}
//	 * 
//	 * @return true, if it does; false, o.w..
//	 * 
//	 * @see ReadIncObservation#readLaterWrite()
//	 */
//	public boolean readLaterWrite()
//	{
//		BasicOperation wriop = null;
//		for (BasicOperation riop : this.opList)
//		{
//			if (riop.isReadOp())	// check every READ
//			{
//				wriop = riop.getReadfromWrite();
//				if (riop.getPid() == wriop.getPid() && riop.getIndex() < wriop.getIndex())
//					return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * set {@link #cur_rriop} to @param riop
	 * @param riop current {@link ReadIncOperation} to be check
	 */
	public void set_cur_rriop(ReadIncOperation riop)
	{
		assertTrue("READ operation is to check", riop.isReadOp());
		
		this.cur_rriop = riop;
	}
	
	/**
	 * get the current {@link ReadIncOperation} being checked {@link #cur_rriop}
	 */
	public ReadIncOperation get_cur_rriop()
	{
		return this.cur_rriop;
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
	 * @constraints @param cur_wriop must be WRITE {@link ReadIncOperation};
	 * 		@param cur_wriop must be in this {@link ReadIncProcess};
	 * 		and @param cur_wriop should not precede {@link #pre_wriop} in program order
	 */
	public void advance_pre_wriop(ReadIncOperation cur_wriop)
	{
		assertTrue("for ReadIncProcess with no masterPid, cur_wriop must be WRITE", cur_wriop.isWriteOp());
		assertTrue("new WRITE cur_wriop must be in this ReadIncProcess", cur_wriop.getPid() == this.pid);
		assertTrue("you should advance forward", this.pre_wriop.getIndex() < cur_wriop.getIndex());
		
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
	 * @constraints @param cur_rriop must be READ {@link ReadIncOperation};
	 * 		@param cur_rriop must be in this {@link ReadIncProcess};
	 * 		and {@link #pre_wriop} precedes @param cur_rriop
	 */
	public void advance_pre_rriop(ReadIncOperation cur_rriop)
	{
		assertTrue("for ReadIncProcess with masterPid, cur_rriop must be READ", cur_rriop.isReadOp());
		assertTrue("new WRITE cur_rriop must be in this ReadIncProcess", cur_rriop.getPid() == this.pid);
		assertTrue("you should advance forward in the ReadIncProcess with masterPid", this.pre_rriop.getIndex() < cur_rriop.getIndex());
		
		this.pre_rriop = cur_rriop;
	}
}
