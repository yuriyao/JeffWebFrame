/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午8:03:56 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.buffer;

import org.footoo.jeffwebframe.datastruct.JWFByteCompare;
import org.footoo.jeffwebframe.util.JWFLog;
import org.footoo.jeffwebframe.util.JWFUtil;

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
		detect(data);
	}
	
	public void append(byte data[])
	{
		super.append(data);
		detect(data);
	}
	
	private void detect(byte data[])
	{
		//触发请求
		JWFByteCompare compare = new JWFByteCompare(data);
		if(state == STATE_RECV_R_N_R_N && content_recvd < content_len)
		{
			content_recvd += data.length;
		}
		else if(compare.contains("\r\n\r\n".getBytes()))
			state = STATE_RECV_R_N_R_N;
		else 
		{
			int index = 0;
			String ss[] = {"\r", "\r\n", "\r\n\r", "\n", "\n\r", "\n\r\n"};
			for(int i = 0; i < ss.length; i ++)
			{
				if(compare.startWith(ss[i].getBytes()))
					index = i;
				else 
					break;
			}
			state = states[state - STATE_RECV_0][index];
			
			if(state != STATE_RECV_R_N_R_N && !compare.contentIs(ss[index].getBytes()))
				state = STATE_RECV_0;
			
			/*记录结尾信息*/
			if(state == STATE_RECV_0)
			{
				String ss2[] = {"\r", "\r\n", "\r\n\r"};
				for(int i = 0; i < ss2.length; i ++)
				{
					if(compare.endWith(ss2[i].getBytes()))
						state = STATE_RECV_0 + i;
				}
			}
			
		}
		
		if(state == STATE_RECV_R_N_R_N)
		{
			//计算Content-Length的长度
			byte array[] = toArray();
			JWFByteCompare all = new JWFByteCompare(array);
			//找到\r\n\r\n的位置
			int _r_n_r_n_index = all.indexOf("\r\n\r\n".getBytes());
			//这是软件的bug啊
			if(_r_n_r_n_index < 0)
			{
				JWFLog.getLog().logln(JWFLog.BUG, "出现了软件bug,行号：" +  new Throwable().getStackTrace()[1].getLineNumber());
				JWFUtil.exit(-1);
			}
			//找到Content-Length的位置
			int index = all.indexOf("Content-Length:".getBytes());
			if(index < 0 || index > _r_n_r_n_index)
				content_len = 0;
			else
			{
				//计算content的长度
				int begin = index + "Content-Length:".length();
				content_len = 0;
				//去掉空格
				while(array[begin] == ' ')
					begin ++;
				for(int i = begin; array[i] != '\r'; i ++)
				{
					if(array[i] > '9' || array[i] < '0')
					{
						JWFLog.getLog().logln(JWFLog.WARING, "接收到请求的行号含有非数字");
						break;
					}
					content_len = content_len * 10 + array[i] - '0';
				}
				//计算接收到的数据的长度
				content_recvd = array.length - _r_n_r_n_index - 4;
				JWFLog.getLog().logln(JWFLog.WARING, "Recv Content-Length:" + content_len + "Recvd-Length:" + content_recvd);
			}
		}
	
	}
	
	private int states[][] = 
		{   	//"\r"     		 \r\n					\r\n\r				\n				\n\r				\n\r\n
				{STATE_RECV_R, 	STATE_RECV_R_N, 		STATE_RECV_R_N_R, 	STATE_RECV_0, 	STATE_RECV_R, 		STATE_RECV_R_N}, //0
				{STATE_RECV_R,	STATE_RECV_R_N, 		STATE_RECV_R_N_R, 	STATE_RECV_R_N, STATE_RECV_R_N_R, 	STATE_RECV_R_N_R_N},//\r
				{STATE_RECV_R_N_R,STATE_RECV_R_N_R_N,	STATE_RECV_R_N_R_N,	STATE_RECV_0,	STATE_RECV_R,		STATE_RECV_R_N },//\r\n
				{STATE_RECV_R,	STATE_RECV_R_N, 		STATE_RECV_R_N_R,	STATE_RECV_R_N_R_N,STATE_RECV_R_N_R, 	STATE_RECV_R_N_R_N},//\r\n\r	
		};
	
	
	
	//是否已经接收到完整的http头
	public boolean hasAllHeader()
	{
		return (state == STATE_RECV_R_N_R_N) && (content_len <= content_recvd);
	}
	
	//最长的可以接受的长度 16K
	public static final int HTTP_HEADER_MAX = 16 * 1024;
	
	//\r\n\r\n啥都没有接收到,下面的序号都不能改
	private static final int STATE_RECV_0 = 0;
	//末尾接收到\r
	private static final int STATE_RECV_R = 1;
	//末尾接收到\r\n
	private static final int STATE_RECV_R_N = 2;
	//末尾接收到\r\n\r
	private static final int STATE_RECV_R_N_R = 3;
	//接收到了\r\n\r\n
	private static final int STATE_RECV_R_N_R_N = 4;
	
	
	
	//现在的状态
	private int state = STATE_RECV_0;
	//带有请求的内容的长度
	private int content_len = 0;
	//已经接受到的内容的长度
	private int content_recvd = 0;
	
}
