package cn.edu.nju.moon.consistency.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @description global data or resource
 * 
 * @author hengxin
 * @date 2012-12-6
 */
public final class GlobalData
{
	// type of {@link GenericOperation}
	public static final int DUMMY = -1;
	public static final int READ = 0;
	public static final int WRITE = 1;
	
	// set of variables 
	public static Set<String> VARSET = new HashSet<String>();
}
