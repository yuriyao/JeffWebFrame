/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午3:33:55 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

/**
 * @author jeff
 *
 */
public class JWFBase implements JWFBaseInterface
{

	/* 实现的默认的处理方法
	 * @see org.footoo.jeffwebframe.JWFBaseInterface#get()
	 */
	public JWFResult get() throws JWFNoImplementException {
		throw new JWFNoImplementException(GET);
	}

	/* 实现的默认的POST处理方法
	 * @see org.footoo.jeffwebframe.JWFBaseInterface#post()
	 */
	public JWFResult post() throws JWFNoImplementException {
		throw new JWFNoImplementException(POST);
	}
	
	/*请求方式列表*/
	public static final int POST = 1;
	public static final int GET = 2;
	public static final int METHOD_MIN = POST;
	public static final int METHOD_MAX = GET;
}
