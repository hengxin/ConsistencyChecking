package cn.edu.nju.moon.consistency.schedule.constructor;

import cn.edu.nju.moon.consistency.schedule.View;

/**
 * @description factory for different kinds of {@link View}s
 * 	such as random view ({@link RandomViewFactory}), 
 *  valid view (@link RandomValidViewFactory).
 *  
 * @author hengxin
 * @date 2013-1-12
 */
public interface IViewFactory
{
	/**
	 * generate {@link View}
	 * @return {@link View} generated
	 */
	public abstract View generateView(int varNum, int valRange, int opNum);
}
