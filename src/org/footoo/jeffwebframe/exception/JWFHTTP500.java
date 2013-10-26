/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午2:25:40 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

import org.footoo.jeffwebframe.JWFResult;

/**
 * @author jeff
 * http 500
 */
public class JWFHTTP500 extends JWFHTTPException 
{
	public JWFHTTP500()
	{
		msg = "发生了500";
	}
	
	public JWFHTTP500(String msg)
	{
		this.msg = msg;
	}
	
	public String toString()
	{
		return msg;
	}
	
	@Override
	public void setResult(JWFResult result)
	{
		result.setStatus(500);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4047338227683153425L;
	
	private String msg = null;
}
