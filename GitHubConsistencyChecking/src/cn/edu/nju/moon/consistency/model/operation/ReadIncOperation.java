package cn.edu.nju.moon.consistency.model.operation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.moon.consistency.checker.ReadIncChecker;
import cn.edu.nju.moon.consistency.datastructure.EarliestRead;
import cn.edu.nju.moon.consistency.datastructure.LatestWriteMap;
import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;

/**
 * @description {@link ReadIncOperation} is specifically designed for {@link ReadIncChecker} algorithm.
 * 
 * @author hengxin
 * @date 2012-12-8
 */
public class ReadIncOperation extends BasicOperation
{
	/* basic information related */
	private int index = -1;	// index in {@link ReadIncProcess}
	private int rid = -1;	// id of READ {@link ReadIncOperation}
	private int wid = -1;	// id of WRITE {@link ReadIncOperation}
	
	/* precede order related */
	private ReadIncOperation programOrder = null;		// program order
	private ReadIncOperation reProgramOrder = null;		// reverse program order
	private ReadIncOperation readfromOrder = null; 		// read from order
	private List<ReadIncOperation> writetoOrder 
				= new ArrayList<ReadIncOperation>();	// write to relation 
	private ReadIncOperation wprimewrOrder = null;		// w'wr order TODO: ArrayList<ReadIncOperation> ?
	private List<ReadIncOperation> reWprimewrOrder 
				= new ArrayList<ReadIncOperation>();	// reverse w'wr order

	/* {@link ReadIncChecker} related */
	private EarliestRead earlistRead = new EarliestRead();	// earliest READ reachable 
	private LatestWriteMap latestWriteMap = new LatestWriteMap();	// latest WRITE for each variable

	/* {@link ReadIncChecker} reschedule related */
	private boolean isCandidate = false;
	private List<ReadIncOperation> predecessors = null;
	private List<ReadIncOperation> successors = null;
	
	
	public ReadIncOperation(GenericOperation otherOp)
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
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	/************* BEGIN: rid and wid **************/
	public int getRid()
	{
		return this.rid;
	}
	
	public void setRid(int id)
	{
		assertTrue("Only READ operation has rid", this.isReadOp());
		
		this.rid = id;
	}
	
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
	public void setProgramOrder(ReadIncOperation riop)
	{
		this.programOrder = riop;
		riop.reProgramOrder = this;
	}
	
	public void addWritetoOrder(ReadIncOperation riop)
	{
		assertTrue("WRITE writes to READ", this.isWriteOp() && riop.isReadOp());
		
		this.writetoOrder.add(riop);
		riop.readfromOrder = this;
	}
	
	/**
	 * @return dictating WRITE {@link ReadIncOperation} from which this READ reads
	 * 
	 * @constraints this must be READ {@link ReadIncOperation}
	 */
	public ReadIncOperation getReadfromWrite()
	{
		assertTrue("READ reads from WRITE", this.isReadOp());
		
		return this.readfromOrder;
	}
	
	/**
	 * @return dictated READ {@link ReadIncOperation}s for this WRITE one
	 * 
	 * @constraints this must be WRITE {@link ReadIncOperation}
	 */
	public List<ReadIncOperation> getWritetoOrder()
	{
		assertTrue("WRITE writes to READ", this.isWriteOp());
		
		return this.writetoOrder;
	}
	
	/**
	 * applying Rule (c): W'WR order; 
	 * return <a>true</a> if cycle emerges; <a>false</a>, otherwise.
	 * 
	 * @param wriop WRITE {@ReadIncOperation} to be pointed to
	 * @return <a>true</a> if cycle emerges; <a>false</a>, otherwise.
	 */
	public boolean apply_wprimew_order(ReadIncOperation wriop)
	{
		assertTrue("W'WR order: WRITE pointing to WRITE", this.isWriteOp() && wriop.isWriteOp());
		assertTrue("pointing to the WRITE ReadIncOperation which has corresponding READs", ! wriop.getWritetoOrder().isEmpty());
		assertTrue("W'WR order: on two operations performing on the same variable", this.getVariable().equals(wriop.getVariable()));
		
		this.wprimewrOrder = wriop;
		wriop.reWprimewrOrder.add(this);
		
		// cycle detection
		String var = wriop.getVariable();
		ReadIncOperation latest_wriop = this.getLatestWriteMap().getLatestWrite(var);
		if (latest_wriop != null && latest_wriop.getWid() >= wriop.getWid())
			return true;
		
		// update earlist read
		this.earlistRead.updateEarliestRead(wriop);
		
		// TODO: forward propagation: this => wriop
		
		return false;
	}
	
	/************ END: order related methods *************/

	/********** BEGIN: {@link ReadIncChecker} related **********/
	public EarliestRead getEarliestRead()
	{
		return this.earlistRead;
	}
	
	public LatestWriteMap getLatestWriteMap()
	{
		return this.latestWriteMap;
	}
	/*********** END: {@link ReadIncChecker} related ***********/
	
	/*********** BEGIN: {@link ReadIncChecker} reschedule related ************/
	/**
	 * set {@link #isCandidate} to be true
	 */
	public void setCandidate()
	{
		this.isCandidate = true;
	}
	
	/**
	 * @return list of predecessor {@link ReadIncOperation}s
	 */
	public List<ReadIncOperation> getPredecessors()
	{
		if (this.predecessors != null)
			return this.predecessors;
		
		// identify predecessors
		this.predecessors = new ArrayList<ReadIncOperation>();
		if (this.reProgramOrder != null)				// reverse program order
			this.predecessors.add(this.reProgramOrder);	
		if (this.isReadOp())							// read from order
			this.predecessors.add(this.readfromOrder);
		else											// w'wr order
			this.predecessors.addAll(reWprimewrOrder);
		
		return this.predecessors;
	}
	
	/**
	 * @return list of successor {@link ReadIncOperation}
	 */
//	public List<ReadIncOperation> getSuccessors()
//	{
//		if (this.successors != null)
//			return this.successors;
//		
//		// identify successors
//		if (this.programOrder)
//		this.successors.add(this.programOrder);
//		
//	}
	/************ END: {@link ReadIncChecker} reschedule related *************/
}
