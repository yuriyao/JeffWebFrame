/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午9:44:38 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.http;

import org.footoo.jeffwebframe.JWFBase;
import org.footoo.jeffwebframe.exception.JWFInvalidHTTP;

/**
 * @author jeff
 * 解析HTTP头
 */
public class JWFHttpHeaderParse 
{
	public JWFHttpHeaderParse(byte header[])
	{
		this.header = header;
	}
	
	public void parse() throws JWFInvalidHTTP
	{
		
	}
	
	public String getDir()
	{
		return dirString;
	}
	
	public int getRequestMethod()
	{
		return request_method;
	}
	
	
	
	//用于解析的原请求头数据
	private byte header[] = null;
	//请求的目录
	private String dirString = "/";
	//请求方式
	private int request_method = JWFBase.GET;
}
