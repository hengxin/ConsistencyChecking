package cn.edu.nju.moon.consistency.checker.exp;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ExpTestCase
{
	private int procNum = 0;
	private int opNum = 0;
	
	private int loops = 0;
	
	private long closure_time = 0L;
	private long centric_time = 0L;
	
	public ExpTestCase(int procNum, int opNum, int loops)
	{
		this.procNum = procNum;
		this.opNum = opNum;
		this.loops = loops;
	}

	public int getProcNum()
	{
		return this.procNum;
	}
	
	public int getOpNum()
	{
		return this.opNum;
	}
	
	public void setClosureTime(long time)
	{
		this.closure_time = time;
	}
	
	public void setCentricTime(long time)
	{
		this.centric_time = time;
	}
	
	public void store(String file_name)
	{
		try
		{
			FileWriter fw = new FileWriter(file_name, true);
			BufferedWriter out = new BufferedWriter(fw); 
			out.write(this.procNum + " " + this.opNum + " " + 
					this.closure_time / (double) this.loops + " " + 
					this.centric_time / (double) this.loops + "\n");
			out.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}

	public int getLoops()
	{
		return this.loops;
	}
}
