package cn.edu.nju.moon.consistency.model.observation;

import java.util.Hashtable;

import cn.edu.nju.moon.consistency.model.process.RawProcess;

/**
 * @author hengxin
 * @date 2012-12-6
 * 
 * @description raw observation is a collection of RawProcess
 */
public class RawObservation
{
	private Hashtable<Integer, RawProcess> processTbl = new Hashtable<Integer, RawProcess>();
	
	public void addProcess(int pid, RawProcess process)
	{
		this.processTbl.put(pid, process);
	}
}
