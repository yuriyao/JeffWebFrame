/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-23 下午10:19:38 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.route;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.footoo.jeffwebframe.JWFBase;
import org.footoo.jeffwebframe.JWFResult;
import org.footoo.jeffwebframe.epoll.JWFNIO.JWFIOEvent;
import org.footoo.jeffwebframe.exception.JWFHTTPException;
import org.footoo.jeffwebframe.exception.JWFRouteException;
import org.footoo.jeffwebframe.http.JWFHttpHeaderParse;
import org.footoo.jeffwebframe.util.JWFLog;
import org.footoo.jeffwebframe.util.JWFUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author jeff
 *
 */
public class JWFRoute 
{
	//获取路由
	public static JWFRoute getRoute()
	{
		return router;
	}
	//设置路由表的路径
	public void setRoutePath(String path) throws FileNotFoundException
	{
		//重复设置路由表路径
		if(routeFile != null)
		{
			JWFLog.getLog().logln(JWFLog.WARING, "重复设置路由表路径");
			return;
		}
		routeFile = new FileInputStream(path);
		//解析路由信息的
		ir = new IntenalRoute(routeFile, path);
		ir.generateRoutes();
	}
	
	//进行路由
	public void route(JWFHttpHeaderParse parse, JWFIOEvent info)
	{
		RouteInfo routeInfo = ir.getRouteInfo(parse.getDir());
		JWFResult result = new JWFResult();
		
		if(routeInfo == null)
		{
			//返回404信息
			result.setStatus(404);
			result.send(info);
			JWFLog.getLog().logln(JWFLog.ERROR, "无法匹配路径" + parse.getDir());
			return;
		}
		try {
			//利用反射执行相应的类的相应的函数
			@SuppressWarnings("unchecked")
			Class<JWFBase> cls = (Class<JWFBase>) Class.forName(routeInfo.className);
			JWFBase jwfBase = cls.newInstance();
			//设置请求信息
			jwfBase.setRequest(parse.getRequest());
			//初始化一些信息
			JWFLog.getLog().logln(JWFLog.DEBUG, "开始调用函数");
			result.clear();
			//调用GET方式
			if(parse.getRequestMethod() == JWFBase.GET)
			{
				result = jwfBase.get();
			}
			//POST方式
			else if(parse.getRequestMethod() == JWFBase.POST)
			{
				result = jwfBase.get();
			}
			else 
			{
				result.setStatus(404);
			}
			
		} 
		catch (ClassNotFoundException e) 
		{
			result.clear();
			//返回404信息
			result.setStatus(404);
			JWFLog.getLog().logln(JWFLog.ERROR, "无法找到类" + routeInfo.className);
		}
		catch(JWFHTTPException e)
		{
			e.setResult(result);
			JWFLog.getLog().logln(JWFLog.ERROR, e.toString());
		}
		catch (Exception e) {
			JWFLog.getLog().logln(JWFLog.ERROR, e.toString());
			result.clear();
			result.setStatus(404);
			e.printStackTrace();
		} 
		//将结果发给客户端
		result.send(info);
		
	}
	
	private JWFRoute()
	{
		
	}
	
	//每一个路由表项的信息
	private class RouteInfo
	{
		//路由的路径，可能是正则表达式
		public String path;
		//用于处理的类的名字
		public String className;
		//正则信息
		public Pattern pattern = null;
	}
	
	//实际的路由信息处理器
	private class IntenalRoute
	{
		/**
		 * 
		 */
		public IntenalRoute(FileInputStream file, String fileName) 
		{
			input = file;
			this.fileName = fileName;
			
		}
		
		/**
		 * 由请求的路径获取路由信息
		 * @param dir
		 * @return
		 */
		public RouteInfo getRouteInfo(String dir)
		{
			//遍历，获取合适的url
			for(int i = 0; i < routes.size(); i ++)
			{
				RouteInfo route = routes.get(i);
				if(route.pattern == null)
				{
					JWFLog.getLog().logln(JWFLog.WARING, "出现空的路由的信息的正则");
					continue;
				}
				//匹配了
				if(route.pattern.matcher(dir).matches())
					return route;
			}
			return null;
		}
		
		/**
		 * 生成路由信息表
		 */
		public void generateRoutes()
		{
			routes.clear();
			/*是xml文件*/
			if(fileName.toLowerCase().endsWith("xml"))
			{
				parseXML();
			}
			/*是json文件*/
			else if(fileName.toLowerCase().endsWith("json"))
			{
				
			}
			/*是普通文件*/
			else 
			{
				
			}
		}
		
		/*
		 * 解析xml文件
		 */
		private void parseXML()
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try 
			{
				DocumentBuilder builder = dbf.newDocumentBuilder();
				Document doc = builder.parse(input);
				
				//获取根节点
				Element root = doc.getDocumentElement();
				if(root == null)
				{
					throw new JWFRouteException("xml根节点为空");
				}
				if(root.getTagName().compareTo("JWFRouter") != 0)
				{
					throw new JWFRouteException("xml的根节点Tag不是JWFRouter");
				}
				//获取程序名
				NodeList nodes = root.getElementsByTagName("ProgramName");
				if(nodes == null || nodes.getLength() == 0)
					JWFLog.getLog().logln(JWFLog.WARING, "Route的xml文件中没有指定ProgramName");
				else
					programName = nodes.item(nodes.getLength() - 1).getTextContent();
				JWFLog.getLog().logln(JWFLog.DEBUG, "ProgramName is " + programName);
				//获取路由信息
				nodes = root.getElementsByTagName("Routers");
				if(nodes == null || nodes.getLength() == 0)
					throw new JWFRouteException("xml文件中没有Routers节点");
				//JWFLog.getLog().logln(JWFLog.DEBUG, "Routers " + nodes.getLength());
				for(int i = 0; i < nodes.getLength(); i ++)
				{
					NodeList entityNodes = nodes.item(i).getChildNodes();
					//JWFLog.getLog().logln(JWFLog.DEBUG, "Entity " + entityNodes.getLength());
					//获取所有的Entity
					for(int k = 0; k < entityNodes.getLength(); k ++)
					{
						if(entityNodes.item(k).getNodeName() != "Entity")
							continue;
						NodeList entity = entityNodes.item(k).getChildNodes();
						String path = null;
						String cls = null;
						
						for(int j = 0; j < entity.getLength(); j ++)
						{
							if(entity.item(j).getNodeName() == "Path")
								path = entity.item(j).getTextContent();
							else if(entity.item(j).getNodeName() == "Class")
								cls = entity.item(j).getTextContent();
						}
						
						if(path == null)
							JWFLog.getLog().logln(JWFLog.WARING, "发现Entity没有指定Path");
						else if(cls == null)
							JWFLog.getLog().logln(JWFLog.WARING, "发现Entity没有指定Class");
						else 
						{
							JWFRoute.RouteInfo info = new JWFRoute.RouteInfo();
							info.path = path;
							info.className = cls;
							info.pattern = Pattern.compile(info.path);
							routes.add(info);
						}
					}
				}
				//检查是否一项合法的路由项都没有
				if(routes.size() == 0)
					throw new JWFRouteException("没有合法的路由项");
				//进行debug
				for(int i = 0; i < routes.size(); i ++)
				{
					RouteInfo info = routes.get(i);
					JWFLog.getLog().logln(JWFLog.DEBUG, info.path + " " + info.className);
				}
				
			} 
			catch (Exception e) 
			{
				JWFLog.getLog().logln(JWFLog.ERROR, "致命错误:解析路由表出现错误" + e);
				JWFUtil.exit(-1);
			}
		}
		
		/**
		 * 
		 */
		private final FileInputStream input;
		/**
		 * 文件名
		 */
		private final String fileName;
		/**
		 * 路由信息表
		 */
		private ArrayList<JWFRoute.RouteInfo> routes = new ArrayList<JWFRoute.RouteInfo>();
		/**
		 * 程序名
		 */
		private String programName = null;
		
	}
	
	//route文件
	private static  FileInputStream routeFile = null;
	//单例
	private static final JWFRoute router = new JWFRoute();
	//内部路由
	private IntenalRoute ir = null;
	
	
	public static void main(String args[]) throws FileNotFoundException
	{
		JWFRoute route = JWFRoute.getRoute();
		route.setRoutePath("Router.xml");
	}
}
