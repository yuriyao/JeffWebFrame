/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午8:03:56 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.buffer;

/**
 * @author jeff
 *
 */
public class JWFHTTPHeaderBuffer extends JWFBuffer
{
	public JWFHTTPHeaderBuffer()
	{
		super();
	}
	
	public void append(byte data[], int len)
	{
		super.append(data, len);
		//进行检测
		
	}
	
	//是否已经接收到完整的http头
	public boolean hasAllHeader()
	{
		
	}
	
	
	//private static final 
}
