/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午2:18:53 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.exception;

import org.footoo.jeffwebframe.JWFResult;

/**
 * @author jeff
 * 
 */
public interface JWFHTTPExceptionHandler 
{
	/**
	 * 用来设置返回的结果的信息
	 * @param result
	 */
	public void setResult(JWFResult result);
}
