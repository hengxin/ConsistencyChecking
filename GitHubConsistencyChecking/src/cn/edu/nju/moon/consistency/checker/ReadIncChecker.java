package cn.edu.nju.moon.consistency.checker;

import cn.edu.nju.moon.consistency.model.observation.ReadIncObservation;

public class ReadIncChecker implements IChecker
{
	private ReadIncObservation riob = null;
	
	public ReadIncChecker(ReadIncObservation riob)
	{
		this.riob = riob;
	}
	
	@Override
	public boolean check()
	{
		this.riob.preprocessing();
		if (this.riob.readLaterWrite())
			return false;
		
		return true;
	}

}
