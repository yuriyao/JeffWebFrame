/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-26 上午12:20:44 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author jeff
 *
 */
public class JWFThreadPool 
{
	public static JWFThreadPool getThreadPool()
	{
		return threadPool;
	}
	
	public void execute(Thread thread)
	{
		pool.execute(thread);
	}
	
	public void closePool()
	{
		pool.shutdown();
	}
	
	private JWFThreadPool()
	{
		
	}
	/**
	 * 最大的线程数
	 */
	private static final int THREAD_NUM_MAX = 100;
	
	/**
	 * 单例
	 */
	private static final JWFThreadPool threadPool = new JWFThreadPool();
	
	/**
	 * 具体的线程池
	 */
	private final ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM_MAX);
}
