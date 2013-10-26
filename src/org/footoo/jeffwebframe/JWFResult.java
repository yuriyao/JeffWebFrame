/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午3:36:02 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

import org.footoo.jeffwebframe.epoll.JWFNIO;
import org.footoo.jeffwebframe.http.JWFHttpHeaderParse;

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
	
	public void setHttpVersion(int version)
	{
		this.version = version;
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
	
	//进行编码
	public void encode()
	{
		
	}
	
	
	//发送结果
	public void send(JWFNIO.JWFIOEvent event)
	{
		byte[] data = null;
		String header = "";
		//返回第一行的状态行
		if(version == JWFHttpHeaderParse.HTTP_VERSION_1_0)
			header = "HTTP/1.0";
		else 
			header = "HTTP/1.1";
		header += " " + status + " " + getStatusString() + "\r\n";
		//设置内容长度
		header += "Content-Length: " + (content == null ? 0 : content.length) + "\r\n";
		//
		header += "\r\n";
		event.outputBuffer.append(header.getBytes());
		//发送内容
		if(content != null)
		{
			encode();
			event.outputBuffer.append(content);
		}
		//标志可写
		event.writable = true;
	}
	
	public String getStatusString()
	{
		String ret = null;
		switch(status / 100)
		{
		case 1:
			ret = "RECVD";
			break;
		case 2:
			ret = "OK";
			break;
		case 3:
			ret = "REDIRECT";
			break;
		case 4:
			ret = "UNREACHABLE";
			break;
		case 5:
			ret = "INTENAL-ERROR";
			break;
		default:
			ret = "UNKNOWN";
			break;
		}
		return ret;
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
	//http版本号
	private int version = JWFHttpHeaderParse.HTTP_VERSION_1_1;
	
}
