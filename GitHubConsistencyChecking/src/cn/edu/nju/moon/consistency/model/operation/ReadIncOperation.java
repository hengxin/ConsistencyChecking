package cn.edu.nju.moon.consistency.model.operation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.datastructure.EarliestRead;
import cn.edu.nju.moon.consistency.datastructure.LatestWriteMap;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import cn.edu.nju.moon.consistency.ui.DotUI;

/**
 * @description {@link ReadIncOperation} is specifically designed for {@link ReadIncChecker} algorithm.
 * 
 * @author hengxin
 * @date 2012-12-8
 */
public class ReadIncOperation extends BasicOperation
{
	/* basic information related */
	/**
	 * @modified hengxin on 2013-1-8
	 * @reason refactor: migrate {@link #index} into super class {@link BasicOperation}
	 */
//	private int rid = -1;	// id of READ {@link ReadIncOperation}
	private int wid = -1;	// id of WRITE {@link ReadIncOperation}
	
//	/* precede order related */
//	private ReadIncOperation programOrder = null;		// program order
//	private ReadIncOperation reProgramOrder = null;		// reverse program order
//	private ReadIncOperation readfromOrder = null; 		// read from order
//	private List<ReadIncOperation> writetoOrder 
//				= new ArrayList<ReadIncOperation>();	// write to relation 
	/** 
	 * w'wr order TODO: ArrayList<ReadIncOperation> ? 
	 */
	private ReadIncOperation wprimewrOrder = null;		
	private List<ReadIncOperation> reWprimewrOrder 
				= new ArrayList<ReadIncOperation>();	// reverse w'wr order

	/* {@link ReadIncChecker} related */
	private EarliestRead earliestRead = new EarliestRead();	// earliest READ reachable 
	private LatestWriteMap latestWriteMap = new LatestWriteMap();	// latest WRITE for each variable

	/* {@link ReadIncChecker} reschedule related */
	private boolean isCandidate = false;	/** candidate for reschedule {@link ReadIncChecker}#draw_reschedule_boundary **/ 
	private boolean isDone = false;			/** done for reschedule {@link ReadIncChecker}#reschedule**/
	private boolean isCovered = false; 		/** is involved in propagation in {@link #apply_wprimew_order}**/
	
	private int count = 0;		/** related to and manipulated together with {@link #isCandidate} and {@link #isDone}**/

//	private List<ReadIncOperation> predecessors = null;
//	private List<ReadIncOperation> successors = null;
	
	/**
	 * @modified hengxin on 2013-1-9
	 * @reason Bug fix: using Set instead of List to avoid repetition
	 */
	private Set<ReadIncOperation> predecessors = null;
	private Set<ReadIncOperation> successors = null;
	
	public ReadIncOperation(RawOperation otherOp)
	{
		super(otherOp);
	}

	/**
	 * @return dictating WRITE {@link ReadIncOperation} for this one
	 * 	if this is a READ {link ReadIncOperation}
	 */
	public ReadIncOperation fetchDictatingWrite()
	{
		assertTrue("Only READ operation has corresponding dictating WRITE", this.isReadOp());
		
		return ReadIncObservation.WRITEPOOL.get(this.toString().replaceFirst("r", "w"));
	}
	
	/************* BEGIN: rid and wid **************/
	public int getWid()
	{
		return this.wid;
	}
	
	public void setWid(int id)
	{
		assertTrue("Only WRITE operation has wid", this.isWriteOp());
		
		if (this.wid == -1)	// keep the same with the earliest dictating READ {@link ReadIncOperation}
			this.wid = id;
	}
	/************* END: rid and wid **************/
	
	/*********** BEGIN: order related methods ************/
//	public void setProgramOrder(ReadIncOperation riop)
//	{
//		this.programOrder = riop;
//		riop.reProgramOrder = this;
//		
//		// ui
//		DotUI.getInstance().addPOEdge(this, riop);
//	}
//	
//	public void addWritetoOrder(ReadIncOperation riop)
//	{
//		assertTrue("WRITE writes to READ", this.isWriteOp() && riop.isReadOp());
//		
//		this.writetoOrder.add(riop);
//		riop.readfromOrder = this;
//		
//		// ui
//		DotUI.getInstance().addWritetoEdge(this, riop);
//	}
//	
//	/**
//	 * @return dictating WRITE {@link ReadIncOperation} from which this READ reads
//	 * 
//	 * @constraints this must be READ {@link ReadIncOperation}
//	 */
//	public ReadIncOperation getReadfromWrite()
//	{
//		assertTrue("READ reads from WRITE", this.isReadOp());
//		
//		return this.readfromOrder;
//	}
//	
//	/**
//	 * @return dictated READ {@link ReadIncOperation}s for this WRITE one
//	 * 
//	 * @constraints this must be WRITE {@link ReadIncOperation}
//	 */
//	public List<ReadIncOperation> getWritetoOrder()
//	{
//		assertTrue("WRITE writes to READ", this.isWriteOp());
//		
//		return this.writetoOrder;
//	}
	
	/**
	 * applying Rule (c): W'WR order; 
	 * return <a>true</a> if cycle emerges; <a>false</a>, otherwise.
	 * 
	 * @param wriop WRITE {@ReadIncOperation} to be pointed to
	 * @param riob {@ReadIncObservation} 
	 * 
	 * @return <a>true</a> if cycle emerges; <a>false</a>, otherwise.
	 */
	public boolean apply_wprimew_order(ReadIncOperation wriop, ReadIncObservation riob)
	{
		assertTrue("W'WR order: WRITE pointing to WRITE", this.isWriteOp() && wriop.isWriteOp());
		assertTrue("pointing to the WRITE ReadIncOperation which has corresponding READs", ! wriop.getWritetoOrder().isEmpty());
		assertTrue("W'WR order: on two operations performing on the same variable", this.getVariable().equals(wriop.getVariable()));
		
		this.wprimewrOrder = wriop;
		wriop.reWprimewrOrder.add(this);
		
		// ui
		DotUI.getInstance().addWprimeWREdge(this, wriop);
		
		/** cycle detection **/
		String var = wriop.getVariable();
		ReadIncOperation latest_wriop = this.getLatestWriteMap().getLatestWrite(var);
		if (latest_wriop != null && latest_wriop.getWid() >= wriop.getWid())
			return true;
		
		this.earliestRead.updateEarliestRead(wriop);	// update earliest read
		
		riob.getGlobalActiveWritesMap().deactivate(this);	// deactivate some WRITE
		
		// TODO: forward propagation by BFS: this => wriop
		Set<ReadIncOperation> coveredSet = new HashSet<ReadIncOperation>();
		Queue<ReadIncOperation> propQueue = new LinkedList<ReadIncOperation>();
		propQueue.offer(wriop);	// starting point
		
		ReadIncOperation riop = null;
		while (! propQueue.isEmpty())
		{
			riop = propQueue.poll();
			riop.getLatestWriteMap().updateLatestWrite(this);	// update latest WRITE
			riop.setCovered();	// propagation related to this operation is done 
			coveredSet.add(riop);	// for reset
			
			// propagation until the current ReadIncOperation being checked
			if (! riob.getMasterProcess().get_cur_rriop().equals(riop))
			{
				for (ReadIncOperation op : riop.getSuccessors())	// rule out the Write -> (unchecked) Read case
					if (! op.isCovered() && (riop.isWriteOp() || op.getIndex() <= riop.getIndex()))
						propQueue.add(op);
			}
		}
		
		/** reset {#isCovered} for reuse **/
		for (ReadIncOperation coveredOp : coveredSet)
			coveredOp.resetCovered();
		
		return false;
	}
	
	/************ END: order related methods *************/

	/********** BEGIN: {@link ReadIncChecker} related **********/
	public EarliestRead getEarliestRead()
	{
		return this.earliestRead;
	}
	
	public LatestWriteMap getLatestWriteMap()
	{
		return this.latestWriteMap;
	}
	/*********** END: {@link ReadIncChecker} related ***********/
	
	/*********** BEGIN: {@link ReadIncChecker} reschedule related ************/
	/**
	 * set {@link #isCandidate} true
	 */
	public void setCandidate()
	{
		this.isCandidate = true;
	}
	
	/**
	 * set {@link #isCandidate} false
	 */
	public void resetCandidate()
	{
		this.isCandidate = false;
	}
	
	/**
	 * Is it a candidate to be rescheduled? 
	 * @return {@link #isCandidate}
	 */
	public boolean isCandidate()
	{
		return this.isCandidate;
	}
	
	/**
	 * set {@link #isDone} false
	 */
	public void resetDone()
	{
		this.isDone = false;
	}
	
	/**
	 * set {@link #isDone} true
	 */
	public void setDone()
	{
		this.isDone = true;
	}
	
	/**
	 * @return {@link #isDone()}
	 */
	public boolean isDone()
	{
		return this.isDone;
	}
	
	/**
	 * set {@link #isCovered} true
	 */
	public void setCovered()
	{
		this.isCovered = true;
	}
	
	/**
	 * reset {@link #isCovered} false
	 */
	public void resetCovered()
	{
		this.isCovered = false;
	}
	
	/**
	 * @return {@link #isCovered}
	 */
	public boolean isCovered()
	{
		return this.isCovered;
	}
	
	/**
	 * @return {@link #count}
	 */
	public int getCount()
	{
		return this.count;
	}
	
	/**
	 * increment {@link #count} by 1
	 */
	public void incCount()
	{
		this.count = this.count + 1;
	}
	
	/**
	 * decrement {@link #count} by 1
	 */
	public void decCount()
	{
		this.count = this.count - 1;
	}
	
	/**
	 * set {@link #count} to @param val
	 * @param val value to be set
	 */
	public void initCount(int val)
	{
		this.count = val;
	}
	
	/**
	 * @return Set of predecessor {@link ReadIncOperation}s
	 */
	public Set<ReadIncOperation> getPredecessors()
	{
		if (this.predecessors != null)
			return this.predecessors;
		
		// identify predecessors
		this.predecessors = new HashSet<ReadIncOperation>();
		if (this.reProgramOrder != null)				// reverse program order
			this.predecessors.add((ReadIncOperation) this.reProgramOrder);	
		if (this.isReadOp())							// read from order
			this.predecessors.add((ReadIncOperation) this.readfromOrder);
		else											// reverse w'wr order
			this.predecessors.addAll(reWprimewrOrder);
		
		return this.predecessors;
	}
	
	/**
	 * @return set of successor {@link ReadIncOperation}
	 */
	public Set<ReadIncOperation> getSuccessors()
	{
		if (this.successors != null)
			return this.successors;
		
		// identify successors
		this.successors = new HashSet<ReadIncOperation>();
		if (this.programOrder != null)					// program order
			this.successors.add((ReadIncOperation) this.programOrder);
		if (this.isWriteOp())
		{
			for (BasicOperation bop : this.writetoOrder)
				this.successors.add((ReadIncOperation) bop);	// write to order
		}
		if (this.wprimewrOrder != null)					// w'wr order
			this.successors.add(this.wprimewrOrder);
		
		return this.successors;
	}
	/************ END: {@link ReadIncChecker} reschedule related *************/
	
}
