package cn.edu.nju.moon.consistency.model.operation;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;
import static org.junit.Assert.*;

/**
 * @description {@link ReadIncOperation} is specifically designed for {@link ReadIncChecker} algorithm.
 * 
 * @author hengxin
 * @date 2012-12-8
 */
public class ReadIncOperation extends BasicOperation
{
	private int index = -1;	// index in {@link ReadIncProcess}
	private int rid = -1;	// id of READ {@link ReadIncOperation}
	private int wid = -1;	// id of WRITE {@link ReadIncOperation}
	
	private ReadIncOperation programOrder = null;		// program order
	private ReadIncOperation reProgramOrder = null;		// reverse program order
	private ReadIncOperation readfromOrder = null; 		// read from order
	private List<ReadIncOperation> writetoOrder 
				= new ArrayList<ReadIncOperation>();	// write to relation 
	private ReadIncOperation wprimewrOrder = null;		// w'wr order
	
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
		
		return ReadIncObservation.WRITEPOOL.get(this.toString().replaceAll("r", "w"));
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
	public void setRid(int id)
	{
		assertTrue("Only READ operation has rid", this.isReadOp());
		
		this.rid = id;
	}
	
	public void setWid(int id)
	{
		assertTrue("Only WRITE operation has wid", ! this.isReadOp());
		
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
		assertTrue("WRITE writes to READ", (! this.isReadOp()) && riop.isReadOp());
		
		this.writetoOrder.add(riop);
		riop.readfromOrder = this;
	}
	
	public ReadIncOperation getReadfromWrite()
	{
		assertTrue("READ reads from WRITE", this.isReadOp());
		
		return this.readfromOrder;
	}
	/************ END: order related methods *************/

}
