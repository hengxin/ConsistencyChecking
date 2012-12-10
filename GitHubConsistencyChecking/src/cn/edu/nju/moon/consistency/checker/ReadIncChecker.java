package cn.edu.nju.moon.consistency.checker;

import static org.junit.Assert.assertTrue;

import cn.edu.nju.moon.consistency.datastructure.GlobalActiveWritesMap;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
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
		ReadIncOperation master_pre_rriop = master_proc.get_pre_riop();
		ReadIncOperation master_cur_rriop = null;
		BasicOperation bop = null;	
		for (int index = 0; index < master_size; index++)
		{
			bop = master_proc.getOperation(index);
			if (bop.isReadOp())	// "ReadIncremental" checking algorithm is READ centric.
			{
				master_cur_rriop = (ReadIncOperation) bop;
				this.compute_globalActiveWritesMap(master_proc, master_pre_rriop, master_cur_rriop);	// compute global active WRITEs
				
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
		
		// rr_interval: (master_pre_rriop, master_cur_rriop)
		ReadIncOperation pre_riop = master_pre_rriop;
		ReadIncOperation rr_wriop = null;	/* WRITE {@link ReadIncOperation} in rr_interval*/
		for (int index = master_pre_rindex + 1; index < master_cur_rindex; index++)	
		{
			rr_wriop = (ReadIncOperation) master_proc.getOperation(index);
			assertTrue("WRITE ReadIncOperation in rr_interval", ! rr_wriop.isReadOp());
			
			rr_wriop.getEarliestRead().initEarlistRead(master_cur_rriop);	// initialize earliest READ
			rr_wriop.getLatestWriteMap().updateLatestWrite(pre_riop);	// update latest WRITE map
			this.riob.getGlobalActiveWritesMap().replace(rr_wriop);	// deactivate some WRITEs
			
			pre_riop = rr_wriop; 
		}
		
		// ww_interval
	}
	
}
