/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午10:53:02 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jeff
 *
 */
public class NioTest 
{
	public static void main(String args[])
	{
		try{
			FileInputStream fInputStream = new FileInputStream("/home/jeff/hs_err_pid4196.log");
			FileChannel fChannel = fInputStream.getChannel();
		
			ByteBuffer buffer = ByteBuffer.allocate(1024); 
		
			while(fChannel.read(buffer) > 0)
			{
				
				System.out.println(new String(buffer.array())); 
				buffer.clear();
			}
			fInputStream.close();  
			
			ByteBuffer buffer2 = ByteBuffer.allocate(10);
			for(int i = 0; i < buffer2.capacity(); i ++)
			{
				buffer2.put((byte)('a' + i));
			}
			System.out.println(new String(buffer2.array()));
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e); 
		}
	}
}
