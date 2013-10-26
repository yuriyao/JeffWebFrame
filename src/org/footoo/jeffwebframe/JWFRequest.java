/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-26 上午8:49:22 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe;

import java.util.Map;


/**
 * @author jeff
 * 存储用户的请求信息
 */
public class JWFRequest 
{
	public JWFRequest()
	{
		
	}
	
	public JWFCookie getCookie() {
		return cookie;
	}

	public void setCookie(JWFCookie cookie) {
		this.cookie = cookie;
	}

	public Map<String, String> getGetKeyValue() {
		return getKeyValue;
	}

	public void setGetKeyValue(Map<String, String> getKeyValue) {
		this.getKeyValue = getKeyValue;
	}

	public Map<String, String> getPostKeyValue() {
		return postKeyValueMap;
	}

	public void setPostKeyValue(Map<String, String> postKeyValueMap) {
		this.postKeyValueMap = postKeyValueMap;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Map<String, String> getHeaderKeyValue() {
		return headerKeyValueMap;
	}

	public void setHeaderKeyValue(Map<String, String> headerKeyValueMap) {
		this.headerKeyValueMap = headerKeyValueMap;
	}

	/**
	 * 存储的cookie数据
	 */
	private JWFCookie cookie = null;
	
	/**
	 * GET的key,value
	 */
	private Map<String, String> getKeyValue = null;
	
	/**
	 * POST的key,value
	 */
	private Map<String, String> postKeyValueMap = null;
	
	/**
	 * 请求的目录
	 */
	private String dir = null;
	
	/**
	 * 请求头的key,value
	 */
	private Map<String, String> headerKeyValueMap = null;
}
