/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午2:21:12 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

import org.footoo.jeffwebframe.JWFResult;

/**
 * @author jeff
 * JWF的HTTP异常的基类
 */
public class JWFHTTPException extends JWFException implements
		JWFHTTPExceptionHandler 
{
	public JWFHTTPException()
	{
		super();
	}
	
	public JWFHTTPException(String msg)
	{
		super(msg);
	}
	
	/* (non-Javadoc)
	 * @see org.footoo.jeffwebframe.exception.JWFHTTPExceptionHandler#setResult(org.footoo.jeffwebframe.JWFResult)
	 */
	@Override
	public void setResult(JWFResult result) 
	{

	}

}
