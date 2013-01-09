package cn.edu.nju.moon.consistency.model.operation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.moon.consistency.checker.ClosureGraphChecker;
import cn.edu.nju.moon.consistency.model.observation.ClosureObservation;
import cn.edu.nju.moon.consistency.ui.DotUI;

/**
 * @description ClosureOperation is the main data structure used in
 *   closure checking algorithm {@link ClosureGraphChecker}
 *   In contrast to {@link ReadIncOperation}, it only consists of basic
 *   information: Program Order and Writeto Order 
 *    
 * @author hengxin
 * @date 2013-1-8
 */
public class ClosureOperation extends BasicOperation
{
	private int globalIndex = -1;	/** global index of operation, accessed by {@link ClosureGraphChecker} **/
	
	private List<BasicOperation> wprimewrOrder = new ArrayList<BasicOperation>();	// W'WR order

	public ClosureOperation(RawOperation otherOp)
	{
		super(otherOp);
	}

	public ClosureOperation(String opStr)
	{
		super(opStr);
	}
	
	/**
	 * @description apply W'WR order: this => @param to_clop
	 * @param to_clop W part of the W'WR order
	 * 
	 * @constraints @param to_clop should be a WRITE
	 */
	public void add_wprimew_order(ClosureOperation to_clop)
	{
		assertTrue("The target of W'WR edge is WRITE", to_clop.isWriteOp());
		
		/** set edge */
		this.wprimewrOrder.add(to_clop);
		
		/** update successors and predecessors */
		this.successors.add(to_clop);
		to_clop.predecessors.add(this);
		
		// ui
		DotUI.getInstance().addWprimeWREdge(this, to_clop);
	}
	
//	/**
//	 * @return dictating WRITE {@link ClosureOperation} for this one
//	 * 	if this is a READ {@link ClosureOperation}
//	 */
//	public ClosureOperation fetchDictatingWrite()
//	{
//		assertTrue("Only READ operation has corresponding dictating WRITE", this.isReadOp());
//		
//		return ClosureObservation.WRITEPOOL.get(this.toString().replaceFirst("r", "w"));
//	}
	
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

}
