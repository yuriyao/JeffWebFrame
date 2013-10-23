/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午10:19:38 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.route;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.footoo.jeffwebframe.epoll.JWFNIO.JWFIOEvent;
import org.footoo.jeffwebframe.http.JWFHttpHeaderParse;
import org.footoo.jeffwebframe.util.JWFLog;

/**
 * @author jeff
 *
 */
public class JWFRoute 
{
	public JWFRoute(JWFHttpHeaderParse parse, JWFIOEvent info)
	{
		this.parse = parse;
		this.info = info;
	}
	
	//设置路由表的路径
	public void setRoutePath(String path) throws FileNotFoundException
	{
		//重复设置路由表路径
		if(routeFile != null)
		{
			JWFLog.getLog().logln(JWFLog.WARING, "重复设置路由表路径");
			return;
		}
		routeFile = new FileInputStream(path);
		
	}
	
	//进行路由
	public void route()
	{
		
	}
	
	private JWFHttpHeaderParse parse;
	//用于处理连接信息
	private JWFIOEvent info;
	//
	private FileInputStream routeFile = null;
}
