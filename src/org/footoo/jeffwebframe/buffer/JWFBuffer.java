/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-22 下午6:11:12 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.buffer;

import java.nio.ByteBuffer;

import org.footoo.jeffwebframe.JWFLog;

/**
 * @author jeff
 * JWF的缓冲管理
 * 线程安全的
 */
public class JWFBuffer 
{
	public JWFBuffer()
	{
		capacity = 2;
		length = 0;
		begin = 0;
		buffer = new byte[capacity];
		total_capacity += capacity;
	}
	
	/**
	 * 从头部插入数据
	 * @param data
	 * @param begin
	 * @param len
	 */
	public void push(byte data[], int from, int len)
	{
		//参数检查
		if(data == null || from < 0 || from >= data.length || len <= 0 || from + len > data.length)
			return;
		synchronized (this) 
		{
			int total_len = length + len;
			int first_copy = 0;
			int second_copy = 0;
			int end;
			
			//需要内存扩容
			if(capacity < total_len)
			{
				//计算需要扩容的大小
				int mem_len = resize();
				byte temp[] = null;
				
				mem_len = mem_len > total_len ? mem_len : total_len;
				//申请新的空间
				temp = new byte[mem_len];
				//统计数据
				total_capacity += mem_len - capacity;
				//保存原有的数据
				//计算拷贝信息
				end = ((begin + length) % capacity);
				if(length == 0)
				{
					;
				}
				else if(end <= begin)
				{
					System.arraycopy(buffer, begin, temp, 0, capacity - begin);
					System.arraycopy(buffer, 0, temp, capacity - begin, end);
				}
				else
				{
					System.arraycopy(buffer, begin, temp, 0, end - begin);
				}
				
				begin = 0;
				//重新计算大小
				capacity = mem_len;
				//
				buffer = temp;
			}
			//保存新添加的数据
			end = ((begin + length) % capacity);
			if(end < begin)
			{
				System.arraycopy(data, from, buffer, begin - len, len);
			}
			else 
			{
				first_copy = begin;
				if(first_copy < len)
				{
					second_copy = len - first_copy;
				}
				else 
				{
					second_copy = 0;
					first_copy = len;
				}
				if(first_copy > 0)
					System.arraycopy(data, from + second_copy, buffer, begin - first_copy, first_copy);
				if(second_copy > 0)
					System.arraycopy(data, from, buffer, capacity - second_copy, second_copy);
			}
			
			length = total_len;
			begin = (begin - len + capacity) % capacity;
		}
	}
	
	public void push(byte data[])
	{
		if(data != null)
			push(data, 0, data.length);
	}
	
	public void push(byte data[], int from)
	{
		if(data != null)
			push(data, from, data.length - from);
	}
	
	public void append(byte data[])
	{
		append(data, data.length);
	}
	
	public void append(byte data[], int len)
	{
		if(data == null || len <= 0)
		{
			JWFLog.getLog().logln(JWFLog.WARING, "向缓冲区中添加空的byte数组");
			return;
		}
		synchronized (this) 
		{
			//计算总的数据长度
			int total_len = length + len;
			int first_copy = 0;
			int second_copy = 0;
			int end;
			
			//需要内存扩容
			if(capacity < total_len)
			{
				//计算需要扩容的大小
				int mem_len = resize();
				byte temp[] = null;
				
				
				mem_len = mem_len > total_len ? mem_len : total_len;
				//申请新的空间
				temp = new byte[mem_len];
				//统计数据
				total_capacity += mem_len - capacity;
				//保存原有的数据
				//计算拷贝信息
				end = ((begin + length) % capacity);
				if(length == 0)
				{
					;
				}
				else if(end <= begin)
				{
					System.arraycopy(buffer, begin, temp, 0, capacity - begin);
					System.arraycopy(buffer, 0, temp, capacity - begin, end);
				}
				else
				{
					System.arraycopy(buffer, begin, temp, 0, end - begin);
				}
				
				begin = 0;
				//重新计算大小
				capacity = mem_len;
				//
				buffer = temp;
			}
			//保存新添加的数据
			end = ((begin + length) % capacity);
			if(end < begin)
			{
				System.arraycopy(data, 0, buffer, end, len);
			}
			else 
			{
				first_copy = capacity - end;
				if(first_copy < len)
				{
					second_copy = len - first_copy;
				}
				else 
				{
					second_copy = 0;
					first_copy = len;
				}
				if(first_copy > 0)
					System.arraycopy(data, 0, buffer, end, first_copy);
				if(second_copy > 0)
					System.arraycopy(data, first_copy, buffer, 0, second_copy);
			}
			
			length = total_len;
		}
	}
	
	
	public byte get(int index)
	{
		synchronized (this) 
		{
			//发生越界
			if(index < 0 || index >= length)
			{
				throw new ArrayIndexOutOfBoundsException();
			}
			return buffer[(begin + index) % capacity];
		}
	}
	
	public byte[] toArray()
	{
		synchronized (this)
		{
			byte ret[] = new byte[length];
			copy(ret, length);
			return ret;
		}
	}
	
	public int getLength()
	{
		return length;
	}
	
	/*public int getCapacity()
	{
		return capacity;
	}*/
	
	public void clear()
	{
		length = 0;
		begin = 0;
	}
	
	public void reset()
	{
		total_capacity += 25 - capacity;
		capacity = 25;
		length = 0;
		buffer = new byte[capacity];
	}
	
	/**
	 * 获取并且删除前len byte的数据
	 * @param len
	 * @return
	 */
	public byte[] delHead(int len)
	{
		synchronized (this) {
			byte ret[] = null;
			int end = (begin + length) % capacity;
			
			if(len <= 0 || length <= 0)
				return new byte[0];
			
			if(len > length)
				len = length;
			
			ret = new byte[len];
			//拷贝数据
			copy(ret, len);
			//重新计算内部数据
			 begin = (begin + len) % capacity;
			 length -= len;
			 return ret;
		}
	}
	
	/**
	 * 
	 * @param dest
	 * @param len
	 */
	private void copy(byte dest[], int len)
	{
		int end = (begin + length) % capacity;
		int first_copy;
		int second_copy;
		
		if(length == 0)
			return;
		if(end <= begin)
		{
			first_copy = capacity - begin;
			if(first_copy < len)
			{
				second_copy = len - first_copy;
			}
			else
			{
				second_copy = 0;
				first_copy = len;
			}
			if(first_copy > 0)
				System.arraycopy(buffer, begin, dest, 0, first_copy);
			if(second_copy > 0)
				System.arraycopy(buffer, 0, dest, first_copy, second_copy);
		}
		else 
		{
			System.arraycopy(buffer, begin, dest, 0, len);
		}
	}
	
	//获得总的统计信息
	public static long getStatistics()
	{
		return total_capacity;
	}
	
	public String toString()
	{
		return new String(toArray(), 0, length);
	}
	
	/**
	 * 当空间不够时重新申请的内存大小
	 * @return
	 */
	protected int resize()
	{
		return capacity << 1;
	}
	
	public void print()
	{
		System.out.println("Info " + capacity + " " + length + " " + begin);
	}
	
	//空间的大小
	protected int capacity;
	//已存储的数据的大小
	protected int length;
	//用于存储数据的缓冲区
	protected byte buffer[] = null;
	//开始的存储位置
	protected int begin;
	//统计所有的空间使用量
	private static long total_capacity = 0;
	
	public static void main(String args[])
	{
		JWFBuffer buffer = new JWFBuffer();
		String ss[] = {"hellohello", "jeffjeffff", "iammmmmmmm", "yuriiiiiii"};
		for(int i = 0; i < ss.length; i ++)
		{
			buffer.append(ss[i].getBytes());
			buffer.print();
			System.out.println(buffer);
			
		}
		for(int i = 0; i < 4; i ++)
		{
			System.out.println("del " + new String(buffer.delHead(i + 1)) + " and " + buffer);
			buffer.push(ss[i].getBytes(), 1, 2);
		}
	}
}
