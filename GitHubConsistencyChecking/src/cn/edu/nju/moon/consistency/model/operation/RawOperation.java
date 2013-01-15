package cn.edu.nju.moon.consistency.model.operation;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import cn.edu.nju.moon.consistency.model.GlobalData;


/** 
 * The most generic model of operation, which
 * consists only of type, variable, and value.
 */
public class RawOperation
{
	private int type;
	private String var;
	private int val;
	private String opStr;	// String form of operation, such as rx1
	
	/**
	 * constructor for Operation
	 *
	 * @param type 	type of Operation (READ or WRITE)
	 * @param var 	variable
	 * @param val 	value
	 */
	public RawOperation(int type, String var, int val)
	{
			this.type = type;
			this.var = var;
			this.val = val;
	
			// get the String format of GenericOperation
			StringBuilder sb = new StringBuilder();
			if(this.isReadOp())
				sb.append('r');
			else if (this.isWriteOp())
				sb.append('w');
			sb.append(var).append(val);
			this.opStr = sb.toString();
			
			if (! this.var.equals(GlobalData.DUMMYVAR))
				GlobalData.VARSET.add(var);

	}

	/**
	 * constructor {@link RawOperation} from String
	 *
	 * @param opStr operation in String format: rx1
	 */
	public RawOperation(String opStr)
	{
		this.opStr = opStr;

		char type = opStr.charAt(0);
		this.var = String.valueOf(opStr.charAt(1));
		this.val = Integer.parseInt(opStr.substring(2));

		if(type == 'r')
			this.type = GlobalData.READ;
		else if (type == 'w')
			this.type = GlobalData.WRITE;
		
		if (! this.var.equals(GlobalData.DUMMYVAR))
			GlobalData.VARSET.add(var);
	}

	/**
	 * constructor: generate random {@link RawOperation}
	 * @param varNum 	possible variables
	 * @param valRange 	possible values
	 */
	public static RawOperation generateRandOperation(int varNum, int valRange)
	{
		Random rand = new Random();

		// generate a random operation
		int type = 0;
		if(rand.nextBoolean())
			type = GlobalData.READ;
		else
			type = GlobalData.WRITE;

		String var = String.valueOf((char) ('!' + rand.nextInt(varNum)));
		int val = rand.nextInt(valRange);
		
		return new RawOperation(type, var, val);
	}
	
	/**
	 * constructor: generate random {@link RawOperation} with @param specific type
	 * @param type 		type of operation: READ or WRITE
	 * @param varNum 	possible variables
	 * @param valRange 	possible values
	 * @return 
	 */
	public static RawOperation generateRawOperation(int type, int varNum, int valRange)
	{
		Random rand = new Random();

		String var = String.valueOf((char) ('!' + rand.nextInt(varNum)));
		int val = rand.nextInt(valRange);
		
		return new RawOperation(type, var, val);
	}
	
	/**
	 * @return dictating WRITE of some READ operation
	 */
	public RawOperation getDictatingWrite()
	{
		assertTrue("Only READ has dictating WRITE", this.isReadOp());
		return new RawOperation(this.toString().replaceFirst("r", "w"));
	}
	
	/**
	 * copy constructor
	 *
	 * @param otherOp {@link RawOperation} to be copied
	 */
	public RawOperation(RawOperation otherOp)
	{
		this(otherOp.getType(), otherOp.getVariable(), otherOp.getValue());
	}

	public int getType()
	{
		return type;
	}

	public boolean isReadOp()
	{
		return this.type == GlobalData.READ;
	}

	public boolean isWriteOp()
	{
		return this.type == GlobalData.WRITE;
	}
	
	public String getVariable()
	{
		return this.var;
	}

	public int getValue()
	{
		return this.val;
	}

	/**
	 * return the String format of Operation such as "rx1"
	 */
	@Override
	public String toString()
	{
		return this.opStr;
	}

	/**
	 * added by @author hengxin
	 * for List<Operation>.contains() method
	 * to guarantee the constraint that write operation
	 * writes distinct values for single var.
	 *
	 * just to check type+var+val and ignore their subclasses
	 */
	@Override
	public boolean equals(Object otherOp)
	{
		if((otherOp == null) || !(otherOp instanceof RawOperation))
			return false;

		RawOperation op = (RawOperation) otherOp;

		return op.type == this.type && op.var.equals(this.var)
			&& op.val == this.val;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;

		hash += 31 * hash + this.type;
		hash += 31 * hash + (null == this.var ? 0 : this.var.hashCode());
		hash += 31 * hash + this.val;

		return hash;
	}

}
