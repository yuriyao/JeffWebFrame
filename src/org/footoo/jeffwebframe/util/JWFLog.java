/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午4:48:33 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.util;

import java.io.PrintStream;


/**
 * @author jeff
 * JWF的log系统
 */
public class JWFLog 
{
	/**
	 * 获取log的实例
	 * @return
	 */
	public static JWFLog getLog()
	{
		return jwfLog;
	}
	
	/**
	 * 设置log的流
	 * @param type
	 * @param stream
	 * @return
	 */
	public boolean setLog(int type, PrintStream stream)
	{
		if(type < LOG_TYPE_MIN || type > LOG_TYPE_MAX || stream == null)
			return false;
		jwfPrintStreams[type - LOG_TYPE_MIN] = stream;
		return true;
	}
	
	/**
	 * 输出log信息
	 * @param type log的类型
	 * @param text log的内容
	 */
	public void log(int type, String text)
	{
		if(text == null)
			return;
		if(type < LOG_TYPE_MIN || type > LOG_TYPE_MAX)
			type = NORMAL;
		jwfPrintStreams[type - LOG_TYPE_MIN].print(text);
		jwfPrintStreams[type - LOG_TYPE_MIN].flush();
	}
	
	public void log(String text)
	{
		log(NORMAL, text);
	}
	
	public void logln(int type, String text)
	{
		if(text == null)
			return;
		if(type < LOG_TYPE_MIN || type > LOG_TYPE_MAX)
			type = NORMAL;
		jwfPrintStreams[type - LOG_TYPE_MIN].println(text);
		jwfPrintStreams[type - LOG_TYPE_MIN].flush();
	}
	
	public void logln(String text)
	{
		logln(NORMAL, text);
	}
	
	public void close()
	{
		for(int i = 0; i < LOG_TYPE_NUMBER; i ++)
		{
			if(jwfPrintStreams[i] != System.out)
			{
				jwfPrintStreams[i].close();
			}
		}
	}
	
	/**
	 * 用于单例模式
	 */
	private JWFLog()
	{
		
		for(int i = 0; i < LOG_TYPE_NUMBER; i ++)
		{
			//jwfPrintStreams[i] = System.out;
		}
	}
	
	
	
	//Log的类型,他们的序号必须是连续的
	public final static int DEBUG = 1;
	public final static int NORMAL = 2;
	public final static int WARING = 3;
	public final static int ERROR = 4;
	//跟踪非法的用户请求行为
	public final static int TRACE = 5; 
	private final static int LOG_TYPE_MAX = TRACE;
	private final static int LOG_TYPE_MIN = DEBUG;
	private final static int LOG_TYPE_NUMBER = LOG_TYPE_MAX - LOG_TYPE_MIN + 1;
	
	//单例
	private static final JWFLog jwfLog = new JWFLog();
	//每一种log对应的流
	private static PrintStream jwfPrintStreams[] = new PrintStream[LOG_TYPE_NUMBER];
	
	static
	{ 
		for(int i = 0; i < LOG_TYPE_NUMBER; i ++)
		{
			jwfPrintStreams[i] = System.out;
		}
	}
}
