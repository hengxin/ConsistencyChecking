package cn.edu.nju.moon.consistency.checker;

import cn.edu.nju.moon.consistency.model.observation.ClosureObservation;
import cn.edu.nju.moon.consistency.model.observation.RawObservation;
import cn.edu.nju.moon.consistency.ui.DotUI;

/**
 * @description OperationGraphChecker is a PRAM Consistency checking algorithm
 *   which based on ``closure'' idea
 *   
 * @author hengxin
 * @date 2013-1-8
 */
public class OperationGraphChecker extends Checker
{

	public OperationGraphChecker(RawObservation rob)
	{
		super(rob);
	}

	/**
	 * Constructor
	 * @param rob	{@link RawObservation} to check
	 * @param name	name for visualization in {@link DotUI}
	 */
	public OperationGraphChecker(RawObservation rob, String name)
	{
		super(rob, name);
	}
	
	/**
	 * get the appropriate {@link ClosureObservation} with respect to @param masterPid
	 */
	@Override
	protected RawObservation getMasterObservation(int masterPid)
	{
		return new ClosureObservation(masterPid, super.rob);
	}

	/**
	 * @description operation-graph checking algorithm (based on iterative closure)
	 * 
	 * @param rob observation to check (i.e., {@link ClosureObservation})
	 */
	@Override
	protected boolean check_part(RawObservation rob)
	{
		boolean consistent = true;
		
		ClosureObservation master_cls_ob = (ClosureObservation) rob;

		// 1) add program order edge and read-write mapping
		master_cls_ob.preprocessing();

		/**
		 *  2) iteration of applying two rules until no new edges are added,
		 *  in other words, a fixed point is reached
		 */
		boolean changed_wprime2w = false;
		do
		{
			master_cls_ob.closure();
			changed_wprime2w = master_cls_ob.apply_wprime2w_edge_rule();
			if (master_cls_ob.cycle_check())	// cycle detection
			{
				consistent = false;
				break;
			}
		}while(changed_wprime2w);

//		if (consistent)	// satisfying PRAM Consistency; construct a witness
//			schedule.constructExecution(master_cls_ob);

		// ui
		DotUI.getInstance().execute("operationgraph/" + name + "_" + master_cls_ob.getMasterPid());
		
		return consistent;
	}

}
