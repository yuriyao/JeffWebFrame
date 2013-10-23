/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午7:58:36 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

import org.footoo.jeffwebframe.JWFNIO.JWFIOEvent;
import org.footoo.jeffwebframe.buffer.JWFBuffer;

/**
 * @author jeff
 *
 */
public class JWFHTTPTrigger implements JWFNIOTrigger {

	/* (non-Javadoc)
	 * @see org.footoo.jeffwebframe.JWFNIOTrigger#trigger(int, org.footoo.jeffwebframe.buffer.JWFBuffer, org.footoo.jeffwebframe.JWFNIO.JWFIOEvent)
	 */
	@Override
	public void trigger(int event, JWFBuffer inputBuffer, JWFIOEvent info) {
		// TODO Auto-generated method stub
		
	}
	
	
	private boolean isTotalHttpHeader(JWFBuffer inputBuffer)
	{
		
	}

}
