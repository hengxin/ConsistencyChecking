package cn.edu.nju.moon.consistency.schedule.constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;
import cn.edu.nju.moon.consistency.schedule.View;

/**
 * @description generate valid {@link View} randomly
 * @author hengxin
 * @date 2013-1-12
 * 
 * @see RandomBasicObservationConstructor
 */
public class RandomValidViewFactory implements IViewFactory
{

	/**
	 * generate a valid {@link View} randomly
	 *  
	 * @param varNum number of variables
	 * @param valRange range of values of variables
	 * @param opNum number of operations
	 * @return a {@link View} which contains @param opNum {@link RawOperation}
	 * 
	 * @author hengxin
	 * @date 2013-1-11
	 */
	@Override
	public View generateView(int varNum, int valRange, int opNum)
	{
		Map<String, RawOperation> activeWriteMap = new HashMap<String, RawOperation>();	/** <var, op> pair */
		Map<String, RawOperation> writePool = new HashMap<String, RawOperation>();		/** <opStr, op> pair */
		
		int count = 0;
		Random rand = new Random();
		int ratio = 3;
		List<RawOperation> opList = new ArrayList<RawOperation>();
		
		while (count < opNum)
		{
			if (rand.nextInt(ratio) % ratio == 0)	// generate WRITE operation
			{
				while (true)
				{
					RawOperation wop = RawOperation.generateRawOperation(GlobalData.WRITE, varNum, valRange);
					if (! writePool.containsKey(wop.toString()))	// unique WRITE requirement
					{
						activeWriteMap.put(wop.getVariable(), wop);
						writePool.put(wop.toString(), wop);
						opList.add(wop);
						count++;
						break;
					}
				}
			}
			else	// generate READ operation according to activeWriteMap
			{
				if (activeWriteMap.size() != 0)
				{
					int index = rand.nextInt(activeWriteMap.size());
					String var = (String) activeWriteMap.keySet().toArray()[index];
					RawOperation rop = new RawOperation(GlobalData.READ, var, activeWriteMap.get(var).getValue());
					opList.add(rop);
					count++;
				}
			}
		}
		
		return new View(opList);
	}

}
