/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午4:39:48 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.epoll;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.footoo.jeffwebframe.buffer.JWFBuffer;
import org.footoo.jeffwebframe.buffer.JWFHTTPHeaderBuffer;
import org.footoo.jeffwebframe.http.JWFHTTPTrigger;
import org.footoo.jeffwebframe.route.JWFRoute;
import org.footoo.jeffwebframe.util.JWFLog;
import org.footoo.jeffwebframe.util.JWFUtil;



/**
 * @author jeff
 * JWF系统的底层网络IO，使用的epoll（linux等）
 */
public class JWFNIO 
{
	
	public JWFNIO()
	{
		
	}
	
	public void append(AbstractSelectableChannel channel, int event, JWFNIOTrigger trigger)
	{
		JWFIOEvent nioEvent = new JWFIOEvent();
		nioEvent.trigger = trigger;
		nioEvent.event = event;
		try {
			channel.configureBlocking(false);
			channel.register(selector, event);
			infos.put(channel, nioEvent);
		} catch (ClosedChannelException e) {
			JWFLog.getLog().logln(JWFLog.ERROR, "注册通道" + channel.toString() + "失败");
		} catch (IOException e) {
			JWFLog.getLog().logln(JWFLog.ERROR, "设置" + channel.toString() + "非阻塞状态失败");
		}
		
	}
	
	public void setRunning(boolean running)
	{
		this.running = running;
	}
	
	public void run()
	{
		while(running)
		{
			try {
				/**
				 * 监听所有的套接口，等待事件到来，进行逻辑处理
				 */
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while(iterator.hasNext())
				{
					SelectionKey key = iterator.next();
					if(key != null)
					{
						AbstractSelectableChannel channel = (AbstractSelectableChannel)key.channel();
						JWFIOEvent nioEvent = null;
						
						if(channel == null)
							continue;
						
						nioEvent = infos.get(channel);
						if(nioEvent == null)
						{
							JWFLog.getLog().logln(JWFLog.WARING, "在map中没有找到对应的value");
							continue;
						}
						
						//accept获得满足
						if(key.isAcceptable() && (nioEvent.event & SelectionKey.OP_ACCEPT) != 0)
						{
							SocketChannel client = ((ServerSocketChannel)channel).accept();
							if(client != null)
							{
								append(client, SelectionKey.OP_READ | SelectionKey.OP_WRITE, new JWFHTTPTrigger());
								//打印信息
								JWFLog.getLog().logln(JWFLog.NORMAL, "接收到来自" + client.socket().getInetAddress() + ":" + client.socket().getPort() + "的连接");
							}
						}
						else if(key.isReadable() && (nioEvent.event & SelectionKey.OP_READ) != 0)
						{
							//读取数据
							buffer.clear();
							int ret = ((SocketChannel)channel).read(buffer);
							//添加到缓冲区
							if(ret > 0)
							{
								nioEvent.inputBuffer.append(buffer.array(), ret);
								//进行测试
								//nioEvent.outputBuffer.push(buffer.array(), 0, ret);
								JWFLog.getLog().logln(JWFLog.DEBUG, nioEvent.inputBuffer.toString());
								//触发事件
								if(nioEvent.trigger != null)
								{
									nioEvent.trigger.trigger(SelectionKey.OP_READ, nioEvent.inputBuffer, nioEvent);
								}
							}
								
						}
						else if(key.isWritable() && (nioEvent.event & SelectionKey.OP_WRITE) != 0)
						{
							if(nioEvent.writable)
							{
								buffer.clear();
								
								//nioEvent.outputBuffer.append(("Write" + nioEvent.inputBuffer.getLength()).getBytes());
								byte output[] = nioEvent.outputBuffer.delHead(BUFFER_LEN);
								System.out.println(new String(output));
								buffer.put(output);
								//开始输出
								buffer.flip();
								try {
									//JWFLog.getLog().logln(JWFLog.DEBUG, "即将写入" + new String(buffer.array()));
									int ret = ((SocketChannel)channel).write(buffer);
									//没有完全输出，返还给缓冲区
									if(ret != output.length)
									{
										ret = ret >= 0 ? ret : 0;
										nioEvent.outputBuffer.push(output, ret, output.length - ret);
									}
									if(nioEvent.outputBuffer.getLength() == 0)
									{
										((SocketChannel)channel).close();
									}
									
								} catch (Exception e) {
									JWFLog.getLog().logln(JWFLog.WARING, "向" + channel + "中写入数据出现错误");
									//进行处理
									
								}
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	
	static
	{
		try {
			selector = Selector.open();
		} catch (Exception e) {
			JWFLog.getLog().logln(JWFLog.ERROR, "内部错误：初始化IO失败");
			JWFUtil.exit(-1);
		}
	}
	
	//底层的selector
	private static Selector selector;
	//用于
	private Map<Channel, JWFIOEvent> infos = new Hashtable<Channel, JWFNIO.JWFIOEvent>();
	//
	private boolean running = true;
	
	private static final int BUFFER_LEN = 1024;
	//
	ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LEN);
	
	public static class JWFIOEvent
	{
		//输入缓冲区
		public JWFBuffer inputBuffer = new JWFHTTPHeaderBuffer();
		//输出缓冲区
		public JWFBuffer outputBuffer = new JWFBuffer();
		//触发函数
		public JWFNIOTrigger trigger;
		//接受的事件
		public int event;
		//是否应该开始向外输出数据
		public volatile boolean writable = false;
	}
	
	public static void main(String args[]) throws IOException
	{
		JWFNIO nio = new JWFNIO();
		JWFRoute.getRoute().setRoutePath("Router.xml");
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress("localhost", 8080));
		nio.append(server, SelectionKey.OP_ACCEPT, null);
		nio.run();
		
	}
}
