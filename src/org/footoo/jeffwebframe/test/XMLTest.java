/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-25 下午3:11:05 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author jeff
 *
 */
public class XMLTest 
{
	public static void read()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document =  builder.parse("Router.xml");
			Element root = document.getDocumentElement();
			
			if(root == null)
				return;
			System.out.println(root.getNodeName());
			
			Node name = root.getElementsByTagName("ProgramName").item(0);
			if(name != null)
				System.out.println(name.getNodeName() + " " + name.getTextContent());
			
			NodeList nodes = root.getElementsByTagName("Routers");
			Document router = nodes.item(0).getOwnerDocument();
			System.out.println(router.getElementsByTagName("Path").item(0).getTextContent());
			//System.out.println(nodes.item(0).get);
		
			if(nodes == null)
				return;
			for(int i = 0; i < nodes.getLength(); i ++)
			{
				Node node = nodes.item(i);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String args[])
	{
		read();
	}
}
