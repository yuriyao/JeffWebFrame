/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午3:36:02 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

import org.footoo.jeffwebframe.epoll.JWFNIO;

/**
 * @author jeff
 *
 */
public class JWFResult 
{
	//支持的编码
	//不编码
	public static final int NOENCODE = 0;
	//GZIP编码
	public static final int GZIP = 1;
	//
	
	public JWFResult()
	{
		
	}
	
	public void clear()
	{
		
	}
	
	/**
	 * 设置返回的状态码
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	//设置编码
	public void setEncode(int encode)
	{
		this.encodeType = encode;
	}
	
	//发送结果
	public void send(JWFNIO.JWFIOEvent event)
	{
		
	}
	
	/**
	 * 设置返回给用户的数据
	 */
	public void setContent(byte content[])
	{
		this.content = content;
	}
	
	//返回给客户端的状态码
	private int status = 200;
	//返回给客户端的内容
	private byte content[] = null;
	//
	private int encodeType = NOENCODE;
	
}
