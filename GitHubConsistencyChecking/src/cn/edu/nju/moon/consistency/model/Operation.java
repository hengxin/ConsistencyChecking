package cn.edu.nju.moon.consistency.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** The basic model of an operation that processes can do
 *
 */
public class Operation
{
	public static final int READ = 1;
	public static final int WRITE = 2;

	public static Set<String> VarSet = new HashSet<String>();
	public static Map<String, Operation> WriteOpPool = new HashMap<String, Operation>();

	// String form of operation: rx1
	private final String opStr;
	private int type;
	private final String var;
	private final int val;

	// which process does it belong to?
	private int pid;
	// which position does it reside in?
	protected int index;

	/**
	 * constructor for Operation
	 *
	 * @param type type of Operation (R or W)
	 * @param var variable
	 * @param val value
	 */
	public Operation(int type, String var, int val)
	{
		this.type = type;
		this.var = var;
		this.val = val;

		String typeStr;
		if(type == Operation.READ)
			typeStr = "r";
		else
			typeStr = "w";

		this.opStr = typeStr + var + val;

		Operation.VarSet.add(var);
	}

	/**
	 * constructor for {@link Operation}
	 *
	 * @param opStr operation in String format: rx1
	 */
	public Operation(String opStr)
	{
		this.opStr = opStr;

		char type = opStr.charAt(0);
		this.var = String.valueOf(opStr.charAt(1));
		this.val = Integer.parseInt(opStr.substring(2));

		if(type == 'r')
			this.type = Operation.READ;
		else
			this.type = Operation.WRITE;

		Operation.VarSet.add(var);
	}

	/**
	 * copy constructor
	 *
	 * @param otherOp {@link Operation} to be copied
	 */
	public Operation(Operation otherOp)
	{
		this(otherOp.getType(), otherOp.getVariable(), otherOp.getValue());
	}

	public String getOpStr()
	{
		return this.opStr;
	}

	public int getType()
	{
		return type;
	}

	public boolean isReadOp()
	{
		return this.type == Operation.READ;
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
	 * get the id of {@link Process} in which this {@link Operation} resides
	 * @return
	 */
	public int getPid()
	{
		return this.pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}

	/**
	 * get the index of this {@link Operation} in {@link Process}
	 * @return index of this {@link Operation} in {@link Process}
	 */
	public int getIndex()
	{
		return this.index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * reset data structures in {@link Operation}
	 * for reuse
	 */
	public void resetOp()
	{
		// do noting for general {@link Operation} object
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
//		sb.append('[');
		switch (type)
		{
			case READ:
				sb.append('r');
				break;
			case WRITE:
				sb.append('w');
				break;
			default:
				sb.append(type);
		}

//		sb.append('(');
		sb.append(this.var);
//		sb.append(")=");
		sb.append(this.val);
//		sb.append(']');

		return sb.toString();
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
		if((otherOp == null) || !(otherOp instanceof Operation))
			return false;

		Operation op = (Operation) otherOp;

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
