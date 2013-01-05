package cn.edu.nju.moon.consistency.checker;

import cn.edu.nju.moon.consistency.model.observation.RawObservation;
import cn.edu.nju.moon.consistency.model.process.RawProcess;

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
	protected RawObservation rob = null;	/** {@link RawObservation} to check **/
	
	/**
	 * Constructor
	 * @param rob {@link RawObservation} to check
	 */
	public Checker(RawObservation rob)
	{
		this.rob = rob;
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
		RawObservation masterObservation = null;
		for (int pid = 0; pid < pids; pid++)
		{
			masterObservation = this.getMasterObservation(pid);
			if (! this.check_part(masterObservation))	/** process with pid does not satisfy consistency condition **/
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param masterPid pid of {@link RawProcess} to check against PRAM Consistency
	 * @return specific subclass of {@link RawObservation} with @param masterPid to check 
	 */
	protected abstract RawObservation getMasterObservation(int masterPid);
	
	/**
	 * check with respect to some process against PRAM Consistency
	 * 
	 * @return true, if this process satisfies PRAM Consistency; false, otherwise. 
	 */
	protected abstract boolean check_part(RawObservation masterObservation);
}
