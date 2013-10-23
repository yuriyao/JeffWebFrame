/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午2:12:07 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.corba.se.pept.transport.Acceptor;

/**
 * @author jeff
 *
 */
public class NIOServer 
{
	private Selector selector;
	private ByteBuffer readBuffer = ByteBuffer.allocate(8);
	
	private Map<SocketChannel, byte[]> clientMessage = new ConcurrentHashMap<SocketChannel, byte[]>();
	
	public void start() throws IOException
	{
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ServerSocket server = ssc.socket();
		
		
		ssc.configureBlocking(false);
		server.bind(new InetSocketAddress("localhost", 8001));
		
		selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while(!Thread.currentThread().isInterrupted())
		{
			selector.select();
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> keyInterator = keys.iterator();
			
			while(keyInterator.hasNext())
			{
				SelectionKey key = keyInterator.next();
				if(!key.isValid())
					continue;
				if(key.isAcceptable())
					accept(key);
				else if(key.isReadable())
					read(key);
			}
		}
	}
	
	private void read(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel)key.channel();
		
		this.readBuffer.clear();
		
		int numRead;
		try {
			numRead = socketChannel.read(readBuffer);
			if(numRead > 0)
			{
				byte buf[] = readBuffer.array();
				System.out.println("length " + buf.length + "read " + new String(buf));
			}
		} catch (Exception e) {
			// TODO: handle exception
			key.cancel();
			socketChannel.close();
			clientMessage.remove(socketChannel);
			e.printStackTrace();
		}
		
		
	}
	
	private void accept(SelectionKey key) throws IOException
	{
		ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
		//接受客户端的链接
		SocketChannel clientChannel = ssc.accept();
		if(clientChannel == null)
			return;
		//客户端启用非阻塞模式
		clientChannel.configureBlocking(false);
		clientChannel.register(selector, SelectionKey.OP_READ);
		System.out.println("A new client connected");
	}
	
	public static void main(String args[]) throws IOException
	{
		new NIOServer().start();
	}
}













































