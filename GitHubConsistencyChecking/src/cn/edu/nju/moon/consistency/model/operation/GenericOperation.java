package cn.edu.nju.moon.consistency.model.operation;

import cn.edu.nju.moon.consistency.model.GlobalData;


/** 
 * The most generic model of operation, which
 * consists only of type, variable, and value.
 */
public class GenericOperation
{
	private final int type;
	private final String var;
	private final int val;
	
	// String form of operation, such as rx1
	private String opStr;
	
	/**
	 * constructor for Operation
	 *
	 * @param type type of Operation (READ or WRITE)
	 * @param var variable
	 * @param val value
	 */
	public GenericOperation(int type, String var, int val)
	{
		this.type = type;
		this.var = var;
		this.val = val;

		// get the String format of GenericOperation
		StringBuilder sb = new StringBuilder();
		if(this.isReadOp())
			sb.append('r');
		else
			sb.append('w');
		sb.append(var).append(val);
		this.opStr = sb.toString();
		
		GlobalData.VARSET.add(var);
	}

//	/**
//	 * constructor for {@link Operation}
//	 *
//	 * @param opStr operation in String format: rx1
//	 */
//	public Operation(String opStr)
//	{
//		this.opStr = opStr;
//
//		char type = opStr.charAt(0);
//		this.var = String.valueOf(opStr.charAt(1));
//		this.val = Integer.parseInt(opStr.substring(2));
//
//		if(type == 'r')
//			this.type = Operation.READ;
//		else
//			this.type = Operation.WRITE;
//
//		Operation.VarSet.add(var);
//	}

	/**
	 * copy constructor
	 *
	 * @param otherOp {@link GenericOperation} to be copied
	 */
	public GenericOperation(GenericOperation otherOp)
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

	public String getVariable()
	{
		return this.var;
	}

	public int getValue()
	{
		return this.val;
	}

	/**
	 * reset data structures in {@link GenericOperation}
	 * for reuse
	 */
	public void resetOp()
	{
		// do noting for general {@link Operation} object
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
		if((otherOp == null) || !(otherOp instanceof GenericOperation))
			return false;

		GenericOperation op = (GenericOperation) otherOp;

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
