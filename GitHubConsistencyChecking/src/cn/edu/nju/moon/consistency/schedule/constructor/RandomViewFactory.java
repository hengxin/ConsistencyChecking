package cn.edu.nju.moon.consistency.schedule.constructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.constructor.RandomBasicObservationConstructor;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;
import cn.edu.nju.moon.consistency.schedule.View;

/**
 * @description generate {@link View} randomly
 * @author hengxin
 * @date 2013-1-12
 * 
 * @see RandomBasicObservationConstructor
 */
public class RandomViewFactory implements IViewFactory
{

	/**
	 * generate a {@link View} randomly
	 * @param varNum number of variables
	 * @param valRange range of values of variables
	 * @param opNum number of operations
	 * @return a {@link View} which contains @param opNum {@link RawOperation}
	 */
	@Override
	public View generateView(int varNum, int valRange, int opNum)
	{
		List<RawOperation> opList = new ArrayList<RawOperation>();
		Random random = new Random();
		RawOperation rop = null;
		int loop = 0;

		for(int i = 0;i < opNum;)
		{
			loop++;

			rop = RawOperation.generateRandOperation(varNum, valRange);
			
			// if it is a Read operation, generate a corresponding Write operation
			if(rop.isReadOp())
			{
				opList.add(rop);
				i++;

				RawOperation wop = new RawOperation(GlobalData.WRITE, rop.getVariable(), rop.getValue());

				if(i == opNum)
				{
					if(! opList.contains(wop))
					{
						opList.remove(rop);
						i--;
					}
				}
				else
				{
					if(! opList.contains(wop))
					{
						opList.add(wop);
						i++;
					}
				}
			}
			else // Write Operation
			{
				if(! opList.contains(rop) && random.nextInt(3) == 0)
				{
					opList.add(rop);
					i++;
				}
			}
		}
		Collections.shuffle(opList);
		
		return new View(opList);
	}

}
