package cn.edu.nju.moon.consistency.model.observation.constructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cn.edu.nju.moon.consistency.model.GlobalData;
import cn.edu.nju.moon.consistency.model.observation.BasicObservation;
import cn.edu.nju.moon.consistency.model.operation.BasicOperation;
import cn.edu.nju.moon.consistency.model.operation.RawOperation;
import cn.edu.nju.moon.consistency.schedule.View;

/**
 * @description construct a basic observation {@link BasicObservation} randomly
 * 
 * @author hengxin
 * @date 2012-12-6
 */
public class RandomBasicObservationConstructor implements IBasicObservationConstructor
{
	// number of processes (RawProcess)
	private int procNum = 5;
	// number of variables involved (from 'a' to 'a' + (variableNum - 1))
	private int varNum = 5;
	// domain of all variables ([0, valueRange))
	private int valRange = 10;
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
	public RandomBasicObservationConstructor()
	{
		
	}

	public RandomBasicObservationConstructor(int procNum, int varNum, int valRange, int opNum)
	{
		this.procNum = procNum;
		this.varNum = varNum;
		this.valRange = valRange;
		this.opNum = opNum;
		
		GlobalData.VARSET = new HashSet<String>();
	}
	
	/**
	 * construct RawObservation randomly
	 * Requirement for test:
	 * every process contains at least one operation
	 * 
	 * @return RawObservation object or NULL 
	 * 	(when some process has no operations at all)
	 */
	@Override
	public BasicObservation construct()
	{
		BasicObservation bob = new BasicObservation(this.procNum);

		// distribute a list of Operation (s) into #processNum processes randomly
		Random pRand = new Random();
		
		for (RawOperation rop : View.generateRandomView(this.varNum, this.valRange, this.opNum).getView())
			bob.addOperation(pRand.nextInt(this.procNum), new BasicOperation(rop));
		
		return bob;
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
