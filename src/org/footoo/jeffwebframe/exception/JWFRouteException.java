/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午3:39:37 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;


/**
 * @author jeff
 *
 */
public class JWFRouteException extends JWFException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4380513906952680451L;

	public JWFRouteException(String msg)
	{
		super(msg);
		this.msg = msg;
	}
	public JWFRouteException()
	{
		this("发生路由异常");
	}
	
	public String toString()
	{
		return msg;
	}
	
	private String msg = null; 
}
