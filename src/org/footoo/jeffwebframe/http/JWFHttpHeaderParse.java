/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午9:44:38 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.http;

import java.util.Hashtable;
import java.util.Map;

import org.footoo.jeffwebframe.JWFBase;
import org.footoo.jeffwebframe.JWFCookie;
import org.footoo.jeffwebframe.JWFRequest;
import org.footoo.jeffwebframe.datastruct.JWFByteCompare;
import org.footoo.jeffwebframe.exception.JWFInvalidHTTP;
import org.footoo.jeffwebframe.util.JWFLog;
import org.footoo.jeffwebframe.util.JWFUtil;

import com.sun.org.apache.bcel.internal.generic.NEW;


/**
 * @author jeff
 * 解析HTTP头
 */
public class JWFHttpHeaderParse 
{
	public JWFHttpHeaderParse(byte header[])
	{
		this.header = header;
	}
	
	public void parse() throws JWFInvalidHTTP
	{
		//解析请求行
		JWFByteCompare compare = new JWFByteCompare(header);
		int i = 0;
		int headerIndex = compare.indexOf("\r\n\r\n".getBytes());
		
		int index = 0;
		int index2 = 0;
		
		String contentLenString;
		
		//如果出现这种情况，就是出现了BUG
		if(headerIndex < 0)
		{
			JWFLog.getLog().logln(JWFLog.BUG, "出现了软件bug" + new Throwable().getStackTrace()[1].getLineNumber());
			JWFUtil.exit(-1);
		}
		//获取请求方式
		for(; i < JWFBase.METHOD_NAMES.length; i ++)
		{
			if(compare.startWithNoCase(JWFBase.METHOD_NAMES[i].getBytes()))
			{
				request_method = i;
				break;
			}
		}
		/*不支持的方式*/
		if(i == JWFBase.METHOD_NAMES.length)
		{
			throw new JWFInvalidHTTP("不支持的请求方式:" + new String(header, 0, 20 > header.length ? header.length : 20) + "...");
		}
		/*解析请求的目录和GET的键值对*/
		//获取目录路径的范围
		index = compare.indexOf(" ".getBytes());
		if(index < 0 || index > headerIndex)
			throw new JWFInvalidHTTP("无法解析请求行");
		index2 = compare.indexOf(index + 1, " ".getBytes());
		if(index2 < 0 || index2 > headerIndex)
			throw new JWFInvalidHTTP("无法解析请求行");
		parseDir(index + 1, index2, compare);
		//解析头部键值对
		index = compare.indexOf("\r\n".getBytes());
		if(index < headerIndex)
		{
			parseHeaderKeyValue(index + 2, headerIndex, compare);
		}
		//获取内容长度
		if((contentLenString = headerKeyValue.get("content-length")) != null)
		{
			i = 0;
			contentLen = 0;
			//去掉所有的空格
			while(i < contentLenString.length() && contentLenString.charAt(i) == ' ')
				i ++;
			for(; i < contentLenString.length(); i ++)
			{
				char c = contentLenString.charAt(i);
				if(c <= '9' && c >= '0')
					contentLen = contentLen * 10 + c - '0';
				else 
					break;
			}
			//有POST数据
			if(contentLen > 0 && contentLen <= header.length - headerIndex - 4)
			{
				parseKeyValue(headerIndex + 4, headerIndex + 4 + contentLen, postKeyValue, compare);
			}
			
		}
		
		//解析cookie
		
		//设置请求信息
		request.setDir(dirString);
		request.setGetKeyValue(getKeyValue);
		request.setPostKeyValue(postKeyValue);
		request.setHeaderKeyValue(headerKeyValue);
		request.setCookie(cookie);
	}
	
	//解析请求行的目录和GET键值对
	private void parseDir(int index1, int index2, JWFByteCompare compare)
	{
		//确认是否包含GET键值
		//获取问号的位置
		int question = compare.indexOf(index1, "?".getBytes());
		int dirIndexEnd = index2;
		//获取GET的键值对
		if(question > 0 && question < index2)
		{
			dirIndexEnd = question;
			parseKeyValue(question + 1, index2, getKeyValue, compare);
		}
		//
		dirString = new String(header, index1, dirIndexEnd - index1);
	}
	
	//解析GET或者POST键值对
	private void parseKeyValue(int begin, int end, Map<String, String> ret, JWFByteCompare compare)
	{ 
		/*&的索引*/
		int andIndex = compare.indexOf(begin, "&".getBytes());
		int itemEndIndex = end;
		int equalIndex;
		String key, value;
		
		if(andIndex > 0 && andIndex < end)
		{
			itemEndIndex = andIndex;
			parseKeyValue(andIndex + 1, end, ret, compare);
		}
		
		equalIndex = compare.indexOf(begin, "=".getBytes());
		if(equalIndex < 0 || equalIndex >= itemEndIndex - 1)
		{
			int len = itemEndIndex - begin;
			//去掉末尾的等号
			if(equalIndex == itemEndIndex - 1)
				len --;
			key = new String(header, begin, len);
			value = "";
		}
		else 
		{
			key = new String(header, begin, equalIndex - begin);
			value = new String(header, equalIndex + 1, itemEndIndex - equalIndex - 1);
		}
		ret.put(key, value);
		JWFLog.getLog().logln(JWFLog.DEBUG, "Get:key:" + key + " value:" + value);
	}
	
	//解析头部键值对
	private void parseHeaderKeyValue(int begin, int end, JWFByteCompare compare)
	{
		//获得\r\n的位置
		int _r_n_index = compare.indexOf(begin, "\r\n".getBytes());
		int itemEndIndex = end;
		int colonIndex;
		String key, value;
		
		if(_r_n_index > 0 && _r_n_index < end)
		{
			itemEndIndex = _r_n_index;
			parseHeaderKeyValue(_r_n_index + 2, end, compare);
		}
		
		colonIndex = compare.indexOf(begin, ":".getBytes());
		if(colonIndex < 0 || colonIndex >= itemEndIndex - 1)
		{
			int len = itemEndIndex - begin;
			if(colonIndex == itemEndIndex - 1)
				len --;
			value = "";
			key = new String(header, begin, len).toLowerCase();
		}
		else 
		{
			key = new String(header, begin, colonIndex - begin).toLowerCase();
			colonIndex ++;
			//去掉所有的空格
			while(colonIndex < itemEndIndex && header[colonIndex] == ' ')
				colonIndex ++;
			value = new String(header, colonIndex, itemEndIndex - colonIndex);
		}
		
		JWFLog.getLog().logln(JWFLog.DEBUG, "Header: key:" + key + "value:" + value);
		headerKeyValue.put(key, value);
	}
	
	public JWFRequest getRequest()
	{
		return request;
	}
	
	public String getDir()
	{
		return dirString;
	}
	
	public int getRequestMethod()
	{
		return request_method;
	}
	
	
	//用于解析的原请求头数据
	private byte header[] = null;
	//请求的目录
	private String dirString = "/";
	//请求方式
	private int request_method = JWFBase.GET;
	//GET的键值对
	private Map<String, String> getKeyValue = new Hashtable<String, String>();
	//Post的键值对
	private Map<String, String> postKeyValue = new Hashtable<String, String>();
	//请求头的键值对
	private Map<String, String> headerKeyValue = new Hashtable<String, String>();
	//cookie信息
	private JWFCookie cookie = new JWFCookie();
	//所有的请求信息
	private JWFRequest request = new JWFRequest();
	//
	private int contentLen = 0;
	
	//HTTP版本号
	public static final int HTTP_VERSION_1_1 = 1;
	public static final int HTTP_VERSION_1_0 = 0;
}
