package cn.edu.nju.moon.consistency.schedule;

import cn.edu.nju.moon.consistency.checker.Checker;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;

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
	 * construct {@link View} from observation
	 * @param bob {@link BasicObservation} from which {@link View} is constructed
	 */
	public abstract void constructView(BasicObservation bob);

	/**
	 * is the schedule a witness for some particular Consistency condition?
	 * @return true, if it is; false, otherwise.
	 */
	public abstract boolean valid();
	
	/**
	 * compare two {@link ISchedule}s to see whether they are compatible to each other.
	 * The "compatibility" is user-defined.
	 *  
	 * @param s another {@link ISchedule}
	 * @return true, if the two {@link ISchedule} are compatible to each other; false, otherwise.
	 */
	public abstract boolean compare(ISchedule s);
}
