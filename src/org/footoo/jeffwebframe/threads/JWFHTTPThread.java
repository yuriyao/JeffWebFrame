/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-26 上午12:15:00 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.threads;

import org.footoo.jeffwebframe.epoll.JWFNIO;
import org.footoo.jeffwebframe.http.JWFHttpHeaderParse;
import org.footoo.jeffwebframe.route.JWFRoute;

/**
 * @author jeff
 * 处理http路由和请求处理
 */
public class JWFHTTPThread extends Thread 
{
	public JWFHTTPThread(JWFHttpHeaderParse parser, JWFNIO.JWFIOEvent info)
	{
		this.parser = parser;
		this.info = info;
	}
	
	@Override
	public void run()
	{
		JWFRoute.getRoute().route(parser, info);
	}
	
	private JWFHttpHeaderParse parser;
	private JWFNIO.JWFIOEvent info;
}
