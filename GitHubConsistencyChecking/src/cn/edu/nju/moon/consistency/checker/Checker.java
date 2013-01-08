package cn.edu.nju.moon.consistency.checker;

import org.apache.commons.lang.RandomStringUtils;

import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.process.BasicProcess;
import cn.edu.nju.moon.consistency.ui.DotUI;

/**
 * @description Consistency checking algorithm is responsible for implementing
 * 	{@link IChecker}
 * 
 * @author hengxin
 * @date 2012-12-8
 * 
 * @modified hengxin on 2013-1-5
 * @reason using Template Method design pattern
 */
public abstract class Checker
{
	protected BasicObservation rob = null;	/** {@link BasicObservation} to check **/
	protected String name = "";		/** for {@link DotUI}; the name of file for visualization **/
	
	/**
	 * Constructor
	 * @param rob {@link BasicObservation} to check
	 */
	public Checker(BasicObservation rob)
	{
		this.rob = rob;
		this.name = RandomStringUtils.random(8);
	}
	
	/**
	 * Constructor
	 * @param riob	{@link BasicObservation} to check
	 * @param name	for {@link DotUI}; the name of file for visualization
	 */
	public Checker(BasicObservation rob, String name)
	{
		this.rob = rob;
		this.name = name;
	}
	
	/**
	 * check whether {@link #rob} satisfies PRAM Consistency
	 * @return true, if {@link #rob} satisfies PRAM Consistency; false, otherwise.
	 * 
	 * Template Method design pattern
	 */
	public final boolean check()
	{
		int pids = this.rob.getSize();
		BasicObservation masterObservation = null;
		for (int pid = 0; pid < pids; pid++)
		{
			masterObservation = this.getMasterObservation(pid);
			if (this.trivial_check(masterObservation))	/** pass the simple check */
			{	
				if (! this.check_part(masterObservation))	/** process with pid does not satisfy consistency condition **/
					return false;
			}
			else
				return false;
		}
		
		return true;
	}
	
	/**
	 * simple check for PRAM Consistency
	 * 1. nullCheck: no operations in the process to be checked; 
	 * 		it is trivially PRAM Consistent
	 * 2. readLaterWrite: some READ reads later WRITE in the same process; 
	 * 		it does not satisfy PRAM Consistency
	 * 
	 * @param ob {@link BasicObservation} to check
	 * @return true, if it passes the simple check; false, otherwise.
	 */
	private boolean trivial_check(BasicObservation ob)
	{
		if (ob.nullCheck())	/** no operations in the process to be checked; it is trivially PRAM Consistent **/
		{
			System.out.println("Null Check: true");
			return true;
		}
		
		ob.preprocessing();	// preprocessing: program order and write to order
		if (ob.readLaterWrite())	/** some READ reads later WRITE in the same process; it does not satisfy PRAM Consistency **/
		{
			System.err.println("Read late write: false");
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param masterPid pid of {@link BasicProcess} to check against PRAM Consistency
	 * @return specific subclass of {@link BasicObservation} with @param masterPid to check 
	 */
	protected abstract BasicObservation getMasterObservation(int masterPid);
	
	/**
	 * check with respect to some process against PRAM Consistency
	 * 
	 * @return true, if this process satisfies PRAM Consistency; false, otherwise. 
	 */
	protected abstract boolean check_part(BasicObservation masterObservation);
}
