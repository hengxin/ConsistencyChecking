package cn.edu.nju.moon.consistency.model.observation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.GenericOperation;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description construct a raw observation (RawObservation) randomly
 */
public class RandomRawObservationConstructor implements IRawObservationConstructor
{
	// number of processes (RawProcess)
	private int processNum = 5;
	// number of variables involved (from 'a' to 'a' + (variableNum - 1))
	private int variableNum = 5;
	// domain of all variables ([0, valueRange))
	private int valueRange = 10;
	// number of operations in all (Operation)
	private int opNum = 30;
	
	private String random_id = null;	// id for generated observation

	/**
	 * Default value:
	 * variableNum = 5;
	 * valueRange = [0,10);
	 * opNum = 30; (number of processes)
	 * processNum = 5;
	 */
	public RandomRawObservationConstructor()
	{
		
	}

	public RandomRawObservationConstructor(int processNum, int variableNum, int valueRange, int opNum)
	{
		this.processNum = processNum;
		this.variableNum = variableNum;
		this.valueRange = valueRange;
		this.opNum = opNum;
	}
	
	/**
	 * construct RawObservation randomly
	 * 
	 * @return RawObservation object
	 */
	@Override
	public RawObservation construct()
	{
		RawObservation rob = new RawObservation();

		// distribute a list of Operation (s) into #processNum processes randomly
		Random pRandom = new Random();
		Iterator<GenericOperation> iter = this.constructOperationList().iterator();
		while(iter.hasNext())
		{
			rob.addOperation(pRandom.nextInt(this.processNum), new BasicOperation(iter.next()));
		}

		this.record(rob);
		
		return rob;
	}
	
	/**
	 * @return List of Operation.
	 *   Constraint: Write distinct values for a variable.
	 */
	private List<GenericOperation> constructOperationList()
	{
		List<GenericOperation> opList = new ArrayList<GenericOperation>();
		Random random = new Random();
		GenericOperation op = null;
		int loop = 0;

		for(int i=0;i<this.opNum;)
		{
			loop++;

			op = this.consturctOperation();

			// if it is a Read operation, generate a corresponding Write operation
			if(op.isReadOp())
			{
				opList.add(op);
				i++;

				GenericOperation wop = new GenericOperation(GlobalData.WRITE, op.getVariable(), op.getValue());

				if(i == this.opNum)
				{
					if(! opList.contains(wop))
					{
						opList.remove(op);
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
				if(! opList.contains(op) && random.nextInt(3) == 0)
				{
					opList.add(op);
					i++;
				}
			}
		}

//		System.out.println("#" + loop + " loop for " + this.opNum + " Operations");

		Collections.shuffle(opList);

		return opList;

	}

	/**
	 * construct generic operation randomly
	 * 
	 * @return {@link GenericOperation} object
	 */
	private GenericOperation consturctOperation()
	{
		Random typeRandom = new Random();
		Random variableRandom = new Random();
		Random valueRandom = new Random();

		// generate a random operation
		int type = 0;
		if(typeRandom.nextBoolean())
			type = GlobalData.READ;
		else
			type = GlobalData.WRITE;

		String var = String.valueOf((char) ('a' + variableRandom.nextInt(this.variableNum)));
		int val = valueRandom.nextInt(this.valueRange);

		return new GenericOperation(type, var, val);
	}
	
	/**
	 * @description record the {@link RawObservation} generated randomly
	 * @date 2013-1-7
	 * 
	 * @param rob {@link RawObservation} to record 
	 */
	private void record(RawObservation rob)
	{
		this.random_id = this.processNum + "_" + this.variableNum + "_" + 
			this.valueRange + "_" + this.opNum + "_" + new Random().nextInt();
		
		try
		{
			FileWriter fw = new FileWriter("data/randomtest/" + this.random_id + ".txt");
			BufferedWriter out = new BufferedWriter(fw);
			out.write(rob.toString());

			out.close();
		}
		catch (IOException ioe)
		{
			System.err.println("Failure with storage of randomly generated observation");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * @return {@link #random_id}
	 */
	@Override
	public String get_ob_id()
	{
		return this.random_id;
	}
}
