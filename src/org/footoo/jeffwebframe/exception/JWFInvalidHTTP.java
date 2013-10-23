/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午9:05:44 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

import org.footoo.jeffwebframe.buffer.JWFHTTPHeaderBuffer;
import org.footoo.jeffwebframe.util.JWFLog;

/**
 * @author jeff
 * 无效的HTTP请求头
 */
public class JWFInvalidHTTP extends Exception
{
	public JWFInvalidHTTP(String msg, JWFHTTPHeaderBuffer buffer)
	{
		super(msg);
		//跟踪非法的用户行为
		int len = buffer.getLength() > MAX_BUFFER_TO_RECORD ? MAX_BUFFER_TO_RECORD : buffer.getLength(); 
		JWFLog.getLog().logln(JWFLog.TRACE, "捕获到可能恶意的非法请求:" + new String(buffer.toArray(), 0, len) + "...");
	}
	
	public JWFInvalidHTTP(JWFHTTPHeaderBuffer buffer)
	{
		this("非法的HTTP请求", buffer);
	}
	
	//最多记录的非法HTTP请求头的长度
	private static final int MAX_BUFFER_TO_RECORD = 20;
}
