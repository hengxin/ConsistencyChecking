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
	public ReadIncOperation getDictatingWrite()
	{
		assertTrue("Only READ operation has corresponding dictating WRITE", this.isReadOp());
		
		return ReadIncObservation.WRITEPOOL.get(this.toString());
	}
	
	/************ order related methods ****************/
	
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
}
