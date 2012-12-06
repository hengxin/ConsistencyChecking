package cn.edu.nju.moon.consistency.model;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import cn.edu.nju.moon.consistency.model.operation.GenericOperation;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description global data or resource
 */
public final class GlobalData
{
	// type of Operation
	public static final int READ = 0;
	public static final int WRITE = 1;
	
	// set of all possible variables in the form of String 
	public static Set<String> VARSET = new HashSet<String>();
	/**
	 *  pool of all WRITE operations
	 *  in the PRAM-distinct problem, WRITE operations are distinct.
	 */
	public static Hashtable<String, GenericOperation> WRITEPOOL = new Hashtable<String, GenericOperation>();	
}
