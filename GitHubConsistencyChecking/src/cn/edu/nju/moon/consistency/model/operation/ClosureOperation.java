package cn.edu.nju.moon.consistency.model.operation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.moon.consistency.checker.OperationGraphChecker;
import cn.edu.nju.moon.consistency.model.observation.ClosureObservation;
import cn.edu.nju.moon.consistency.ui.DotUI;

/**
 * @description ClosureOperation is the main data structure used in
 *   closure checking algorithm {@link OperationGraphChecker}
 *   In contrast to {@link ReadIncOperation}, it only consists of basic
 *   information: Program Order and Writeto Order 
 *    
 * @author hengxin
 * @date 2013-1-8
 */
public class ClosureOperation extends BasicOperation
{
	private int globalIndex = -1;	/** global index of operation, accessed by {@link OperationGraphChecker} **/
	
	/** 
	 * precede order related 
	 */
	private ClosureOperation programOrder = null;		// program order
	private ClosureOperation reProgramOrder = null;		// reverse program order
	private ClosureOperation readfromOrder = null; 		// read from order
	private List<ClosureOperation> writetoOrder 
				= new ArrayList<ClosureOperation>();	// writeto order
	private List<ClosureOperation> wprimewrOrder = new ArrayList<ClosureOperation>();	// W'WR order
	
	private List<ClosureOperation> predecessors = null;

	public ClosureOperation(GenericOperation otherOp)
	{
		super(otherOp);
	}

	public ClosureOperation(String opStr)
	{
		super(opStr);
	}
	
	/*********** BEGIN: order related methods ************/
	public void setProgramOrder(ClosureOperation riop)
	{
		this.programOrder = riop;
		riop.reProgramOrder = this;
		
		// ui
		DotUI.getInstance().addPOEdge(this, riop);
	}
	
	/**
	 * @return the next operation in program order
	 */
	public ClosureOperation getProgramOrder()
	{
		return this.programOrder;
	}
	
	public void addWritetoOrder(ClosureOperation riop)
	{
		assertTrue("WRITE writes to READ", this.isWriteOp() && riop.isReadOp());
		
		this.writetoOrder.add(riop);
		riop.readfromOrder = this;
		
		// ui
		DotUI.getInstance().addWritetoEdge(this, riop);
	}
	
	/**
	 * @return dictating WRITE {@link ClosureOperation} from which this READ reads
	 * 
	 * @constraints this must be READ {@link ClosureOperation}
	 */
	public ClosureOperation getReadfromWrite()
	{
		assertTrue("READ reads from WRITE", this.isReadOp());
		
		return this.readfromOrder;
	}
	
	/**
	 * @return dictated READ {@link ClosureOperation}s for this WRITE one
	 * 
	 * @constraints this must be WRITE {@link ClosureOperation}
	 */
	public List<ClosureOperation> getWritetoOrder()
	{
		assertTrue("WRITE writes to READ", this.isWriteOp());
		
		return this.writetoOrder;
	}

	/**
	 * @return dictating WRITE {@link ClosureOperation} for this one
	 * 	if this is a READ {@link ClosureOperation}
	 */
	public ClosureOperation fetchDictatingWrite()
	{
		assertTrue("Only READ operation has corresponding dictating WRITE", this.isReadOp());
		
		return ClosureObservation.WRITEPOOL.get(this.toString().replaceFirst("r", "w"));
	}
	
	/**
	 * @return list of predecessor {@link ClosureOperation}s
	 */
	public List<ClosureOperation> getPredecessors()
	{
		if (this.predecessors != null)
			return this.predecessors;
		
		// identify predecessors
		this.predecessors = new ArrayList<ClosureOperation>();
		if (this.reProgramOrder != null)				// reverse program order
			this.predecessors.add(this.reProgramOrder);	
		if (this.isReadOp())							// read from order
			this.predecessors.add(this.readfromOrder);
		
		return this.predecessors;
	}
	
	/**
	 * set {@link #globalIndex} to be @param gIndex
	 * @param gIndex global index
	 */
	public void setGlobalIndex(int gIndex)
	{
		this.globalIndex = gIndex;
	}
	
	/**
	 * @return {@link #globalIndex}
	 */
	public int getGlobalIndex()
	{
		return this.globalIndex;
	}
	
	/**
	 * @description apply W'WR order: this => @param to_clop
	 * @param to_clop W part of the W'WR order
	 * 
	 * @constraints @param to_clop should be a WRITE
	 */
	public void addWprimewrEdge(ClosureOperation to_clop)
	{
		assertTrue("The target of W'WR edge is WRITE", to_clop.isWriteOp());
		
		this.wprimewrOrder.add(to_clop);
		// ui
		DotUI.getInstance().addWprimeWREdge(this, to_clop);
	}
}
