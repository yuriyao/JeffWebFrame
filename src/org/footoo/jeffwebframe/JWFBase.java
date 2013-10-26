/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-20 下午3:33:55 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

import java.util.Map;

import org.footoo.jeffwebframe.exception.JWFHTTPException;
import org.footoo.jeffwebframe.exception.JWFNoImplementException;

/**
 * @author jeff
 *
 */
public class JWFBase implements JWFBaseInterface
{

	/* 实现的默认的处理方法
	 * @see org.footoo.jeffwebframe.JWFBaseInterface#get()
	 */
	public JWFResult get() throws JWFNoImplementException, JWFHTTPException {
		throw new JWFNoImplementException(GET);
	}

	/* 实现的默认的POST处理方法
	 * @see org.footoo.jeffwebframe.JWFBaseInterface#post()
	 */
	public JWFResult post() throws JWFNoImplementException, JWFHTTPException{
		throw new JWFNoImplementException(POST);
	}
	
	/**
	 * 设置请求信息，由系统自动完成，用户不应该调用这个函数
	 * @param request
	 */
	public void setRequest(JWFRequest request)
	{
		this.request = request;
	}
	
	
	/**
	 * 获取GET请求中，key对应的value的value
	 * @param key
	 * @return 如果key不存在返回null;否则返回对应的value(可能为"",比如（name=jeff&sex=）中sex对应的value)
	 */
	public String getGetValue(String key)
	{
		return request.getGetKeyValue().get(key);
	}
	
	/**
	 * 获取POST请求中，key对应的value的value
	 * @param key
	 * @return 如果key不存在返回null;否则返回对应的value(可能为"",比如（name=jeff&sex=）中sex对应的value)
	 */
	public String getPostValue(String key)
	{
		return request.getPostKeyValue().get(key);
	}
	
	/**
	 * 获取存储GET的键值对的哈希表
	 * @return
	 */
	public Map<String, String> getGetKeyValue()
	{
		return request.getGetKeyValue();
	}
	
	/**
	 * 获取存储POST的键值对的MAP
	 * @return
	 */
	public Map<String, String> getPostKeyValue()
	{
		return request.getPostKeyValue();
	}
	
	
	
	/*请求方式列表,值对应相应的名称的索引*/
	public static final int POST = 0;
	public static final int GET = 1;
	public static final int HEAD = 2;
	public static final int PUT = 3;
	
	public static final int METHOD_MIN = POST;
	public static final int METHOD_MAX = PUT;
	
	public static final String METHOD_NAMES[] = {"POST", "GET", "HEAD", "PUT"};
	
	//用户的请求信息
	private JWFRequest request = null;
}
