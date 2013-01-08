package cn.edu.nju.moon.consistency.model.observation.constructor;

import cn.edu.nju.moon.consistency.model.observation.RawObservation;

/**
 * @description constructor for {@link RawObservation}
 * 
 * @author hengxin
 * @date 2012-12-8
 *
 * @see {@link RawObservation}
 */
public interface IRawObservationConstructor
{
	public abstract RawObservation construct();
	
	public abstract String get_ob_id();
}
