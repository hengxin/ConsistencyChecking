package cn.edu.nju.moon.consistency.checker;

import static org.junit.Assert.assertTrue;
import cn.edu.nju.moon.consistency.datastructure.GlobalActiveWritesMap;
import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.GenericOperation;
import cn.edu.nju.moon.consistency.model.operation.ReadIncOperation;
import cn.edu.nju.moon.consistency.model.process.ReadIncProcess;

public class ReadIncChecker implements IChecker
{
	private ReadIncObservation riob = null;
	
	public ReadIncChecker(ReadIncObservation riob)
	{
		this.riob = riob;
	}
	
	@Override
	public boolean check()
	{
		if (this.riob.nullCheck())	// no operations in the process to be checked
			return true;
		
		this.riob.preprocessing();	// preprocessing: program order and write to order
		if (this.riob.readLaterWrite())	// some READ reads later WRITE in the same process
			return false;
		
		ReadIncProcess master_proc = this.riob.getMasterProcess();
		int master_size = master_proc.size();
//		ReadIncOperation master_pre_rriop = master_proc.get_pre_rriop();
		ReadIncOperation master_cur_rriop = null;
		BasicOperation bop = null;	
		for (int index = 0; index < master_size; index++)
		{
			bop = master_proc.getOperation(index);
			if (bop.isReadOp())	// "ReadIncremental" checking algorithm is READ centric.
			{
				master_cur_rriop = (ReadIncOperation) bop;
				this.compute_globalActiveWritesMap(master_proc, master_proc.get_pre_rriop(), master_cur_rriop);	// compute global active WRITEs
				master_proc.advance_pre_rriop(master_cur_rriop);	// iterate over the next (R,R) pair
			}
		}
		
		return true;
	}

	/**
	 * computation of global active WRITEs map: 
	 * 		{@link GlobalActiveWritesMap} in {@link ReadIncObservation}
	 *  
	 * @param master_proc {@link ReadIncProcess} to be checked; it is the process with master_pid
	 * @param master_pre_rriop previous READ {@link ReadIncOperation} 
	 * @param master_cur_rriop current READ {@link ReadIncOperation} in consideration
	 * 
	 * @constraints both @param master_pre_rriop and @param master_cur_rriop must be READ {@link ReadIncOperation}
	 */
	private void compute_globalActiveWritesMap(ReadIncProcess master_proc, ReadIncOperation master_pre_rriop, ReadIncOperation master_cur_rriop)
	{
		assertTrue("READ incremental: two arguments should be READ", master_pre_rriop.isReadOp() && master_cur_rriop.isReadOp());
		
		int master_pre_rindex = master_pre_rriop.getIndex();
		int master_cur_rindex = master_cur_rriop.getIndex();
		
		// (1) dealing with rr_interval: (master_pre_rriop, master_cur_rriop)
		ReadIncOperation pre_riop = master_pre_rriop;
		ReadIncOperation rr_wriop = null;	/* WRITE {@link ReadIncOperation} in rr_interval*/
		// rr_interval: (master_pre_rriop, master_cur_rriop)
		for (int rr_index = master_pre_rindex + 1; rr_index < master_cur_rindex; rr_index++)	
		{
			rr_wriop = (ReadIncOperation) master_proc.getOperation(rr_index);
			assertTrue("WRITE ReadIncOperation in rr_interval", rr_wriop.isWriteOp());
			
			rr_wriop.getEarliestRead().initEarlistRead(master_cur_rriop);	// initialize earliest READ
			rr_wriop.getLatestWriteMap().updateLatestWrite(pre_riop);	// update latest WRITE map depending on previous operation
			this.riob.getGlobalActiveWritesMap().replace(rr_wriop);	// deactivate some WRITEs
			
			pre_riop = rr_wriop;	// iterate over the next WRITE  
		}
		master_cur_rriop.getLatestWriteMap().updateLatestWrite(pre_riop);	// update latest WRITE map for @param master_cur_rriop individually
		
		// (2) dealing with ww_interval
		ReadIncOperation dw = master_cur_rriop.getReadfromWrite();	// dictating WRITE for @param master_cur_rriop
		int pid_dw = dw.getPid();	// pid of dictating WRITE
		ReadIncProcess dw_proc = (ReadIncProcess) this.riob.getProcess(pid_dw);	// ReadIncProcess in which dictating WRITE resides
		ReadIncOperation dw_pre_wriop = dw_proc.get_pre_wriop();	// previous WRITE {@link ReadIncOperation}
		assertTrue("Previous ReadIncOperation in ReadIncProcess not with masterPid is WRITE", dw_pre_wriop.isWriteOp());
		ReadIncOperation ww_wriop = null;	// WRITE {@link ReadIncOperation} in ww_interval
		int dw_index = dw.getIndex();
		int dw_pre_wriop_index = dw_pre_wriop.getIndex();
		// ww_interval: (dw_index, dw_pre_wriop_index]
		// TODO: ww_interval case: W, R are in the same process
		for (int ww_index = dw_pre_wriop_index + 1; ww_index <= dw_index; ww_index++)
		{
			ww_wriop = (ReadIncOperation) dw_proc.getOperation(ww_index);
			assertTrue("WRITE ReadIncOperation in ww_interval", ww_wriop.isWriteOp());
			
			ww_wriop.getEarliestRead().initEarlistRead(master_cur_rriop);	// initialize earliest READ
			ww_wriop.getLatestWriteMap().updateLatestWrite(dw_pre_wriop);	// update latest WRITE map depending on previous operation
			/* deactivate some WRITEs */
			this.riob.getGlobalActiveWritesMap().deactivateFrom(dw_pre_wriop);	// deactivate some WRITEs
			this.riob.getGlobalActiveWritesMap().addActiveWrite(ww_wriop);	// add this new active WRITE
			
			dw_pre_wriop = ww_wriop;	// iterate over the next WRITE
		}
		dw_proc.advance_pre_wriop(dw_pre_wriop);	// advance the previous WRITE to the new one
		
		/**
		 *  (3) dealing with @param master_cur_rriop and "dw" separately and specially:
		 *  	@param master_cur_rriop reads value from "dw", causing other WRITEs with
		 *  	the same variable are scheduled before "dw" and LatestWrite are updated 
		 *  	accordingly 
		 */
		ReadIncOperation temp_riop = new ReadIncOperation(new GenericOperation(GlobalData.WRITE, "", -1));	// temp 
		for (ReadIncOperation active_wriop : this.riob.getGlobalActiveWritesMap().getActiveWrites(master_cur_rriop.getVariable()))
			temp_riop.getLatestWriteMap().updateLatestWrite(active_wriop);
		
	}
	
}
