package cn.edu.nju.moon.consistency.schedule;

/**
 * @description The form of {@link WeakSchedule} depend on the respective consistency model.
 * Specifically, the strong consistency models (such as Atomic, Sequential Consistency)
 * require all the Processes hold the same view on the execution. In contrast, weak
 * consistency models (such as PRAM, Causal, Processor Consistency) allow different
 * Processes to hold different views on the execution.
 * 
 *  In checking algorithm ({@link Checker}), {@link WeakSchedule} is obtained.
 *  It is worth pointing out that, we check each Process with respect to 
 *  weak consistency models. Therefore, we will construct a legal serialization for 
 *  each Process which satisfies the consistency model in the {@link WeakSchedule}.
 *  It is the job for {@link ISchedule} to determine whether the partial serializations
 *  are sufficient.
 *  
 *  @author hengxin
 *  @date 2013-1-9
 */
public interface ISchedule
{
	/**
	 * is the schedule a witness for some particular Consistency condition?
	 * @return true, if it is; false, otherwise.
	 */
	public abstract boolean valid();
}
