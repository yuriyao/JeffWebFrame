/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午3:33:55 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

/**
 * JWF的请求处理的基本接口
 * @author jeff
 * 
 */
public interface JWFBaseInterface 
{
	/**
	 * 处理GET方式
	 * @return
	 * @throws JWFNoImplementException 
	 */
	public JWFResult get() throws JWFNoImplementException;
	
	/**
	 * 处理POST方式
	 * @return
	 * @throws JWFNoImplementException 
	 */
	public JWFResult post() throws JWFNoImplementException;
}
