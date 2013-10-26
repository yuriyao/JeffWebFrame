/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-26 下午4:33:04 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

/**
 * @author jeff
 * 参数不合法的异常
 */
public class JWFInvalidArgException extends JWFException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5522290761679671651L;

	public JWFInvalidArgException()
	{
		this("JWF发现参数不合法的异常");
	}
	
	public JWFInvalidArgException(String msg)
	{
		super(msg);
	}
}
