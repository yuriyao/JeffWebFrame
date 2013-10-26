/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午7:58:36 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.http;

import org.footoo.jeffwebframe.buffer.JWFBuffer;
import org.footoo.jeffwebframe.buffer.JWFHTTPHeaderBuffer;
import org.footoo.jeffwebframe.epoll.JWFNIOTrigger;
import org.footoo.jeffwebframe.epoll.JWFNIO.JWFIOEvent;
import org.footoo.jeffwebframe.route.JWFRoute;
import org.footoo.jeffwebframe.threads.JWFHTTPThread;
import org.footoo.jeffwebframe.threads.JWFThreadPool;
import org.footoo.jeffwebframe.util.JWFLog;

/**
 * @author jeff
 *
 */
public class JWFHTTPTrigger implements JWFNIOTrigger 
{

	/* (non-Javadoc)
	 * @see org.footoo.jeffwebframe.JWFNIOTrigger#trigger(int, org.footoo.jeffwebframe.buffer.JWFBuffer, org.footoo.jeffwebframe.JWFNIO.JWFIOEvent)
	 */
	@Override
	public void trigger(int event, JWFBuffer inputBuffer, JWFIOEvent info) 
	{
		//获取到整个的HTTP请求了，进行请求头解析
		if(isTotalHttpHeader(inputBuffer))
		{
			//解析HTTP请求头
			JWFHttpHeaderParse parser = new JWFHttpHeaderParse(inputBuffer.toArray());
			JWFLog.getLog().logln(JWFLog.DEBUG, "开始解析HTTP请求头");
			try {
				parser.parse();
				
				//进行路由和执行
				JWFThreadPool.getThreadPool().execute(new JWFHTTPThread(parser, info));
			} 
			catch (Exception e) 
			{
				//
				JWFLog.getLog().logln(JWFLog.WARING, "解析HTTP请求头出现错误");
				//直接返回错误给客户端
				return;
			}
		}
		
	}
	
	
	private boolean isTotalHttpHeader(JWFBuffer inputBuffer)
	{
		return ((JWFHTTPHeaderBuffer)inputBuffer).hasAllHeader();
	}

}
