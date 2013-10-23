/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午5:28:29 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.epoll;

import org.footoo.jeffwebframe.buffer.JWFBuffer;

/**
 * @author jeff
 * 异步
 */
public interface JWFNIOTrigger {
	public void trigger(int event, JWFBuffer inputBuffer, JWFNIO.JWFIOEvent info);
}
