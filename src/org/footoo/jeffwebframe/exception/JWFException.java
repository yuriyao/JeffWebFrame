/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午2:16:37 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

/**
 * @author jeff
 * JWF的异常的基类
 */
public class JWFException extends Exception 
{
	public JWFException()
	{
		super();
	}
	
	public JWFException(String msg)
	{
		super(msg);
	}
}
