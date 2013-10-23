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
	
	private void detect()
	{
		//触发请求
	}
	
	//KMP算法
	
	
	//是否已经接收到完整的http头
	public boolean hasAllHeader()
	{
		return false;
	}
	
	//最长的可以接受的长度 16K
	public static final int HTTP_HEADER_MAX = 16 * 1024;
	
	//\r\n\r\n啥都没有接收到
	private static final int STATEˍ0 = 0;
	//末尾接收到\r
	private static final int STATE_RECV_R = 1;
	//末尾接收到\r\n
	private static final int STATE_RECV_R_N = 2;
	//末尾接收到\r\n\r
	private static final int STATE_RECV_R_N_R = 3;
	//接收到了\r\n\r\n
	private static final int STATE_RECV_R_N_R_N = 4;
	
	//现在的状态
	private int state = STATEˍ0;
	//带有请求的内容的长度
	private int content_len = 0;
	//已经接受到的内容的长度
	private int content_recvd = 0;
	
}
