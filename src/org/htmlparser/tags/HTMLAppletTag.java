// HTMLParser Library v1_2 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.tags;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
/**
 * HTMLAppletTag represents an &lt;Applet&gt; tag
 */
public class HTMLAppletTag extends HTMLCompositeTag 
{
	private java.lang.String codeBase;
	private java.lang.String archive;
	private java.lang.String appletClass;
	private Hashtable appletParams;

	/**
	 * HTMLAppletTag constructor comment.
	 * @param nodeBegin int
	 * @param nodeEnd int
	 * @param tagContents java.lang.String
	 * @param tagLine java.lang.String
	 */
	public HTMLAppletTag(int tagBegin, int tagEnd, String tagContents, String tagLine, String appletClass,String archive,String codeBase,Hashtable appletParams,Vector childNodeVector,HTMLTag startTag, HTMLTag endTag) 
	{
		super(tagBegin, tagEnd, tagContents, tagLine, childNodeVector,startTag, endTag);
		this.appletClass = appletClass;
		this.codeBase = codeBase;
		this.archive = archive;
		this.appletParams = appletParams;
	}
	
	public java.lang.String getAppletClass() {
		return appletClass;
	}
	
	public Hashtable getAppletParams() {
		return appletParams;
	}
	
	public java.lang.String getArchive() {
		return archive;
	}
	
	public java.lang.String getCodeBase() {
		return codeBase;
	}
	
	public String getParameter(String key)
	{
		return (String)appletParams.get(key);
	}
	
	public Enumeration getParameterNames() 
	{
		return appletParams.keys();	
	}
	
	public void setAppletClass(java.lang.String newAppletClass) {
		appletClass = newAppletClass;
	}
	
	public void setAppletParams(Hashtable newAppletParams) {
		appletParams = newAppletParams;
	}
	
	public void setArchive(java.lang.String newArchive) {
		archive = newArchive;
	}
	
	public void setCodeBase(java.lang.String newCodeBase) {
		codeBase = newCodeBase;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Applet Tag\n");
		sb.append("**********\n");
		sb.append("Class Name = "+appletClass+"\n");
		sb.append("Archive = "+archive+"\n");
		sb.append("Codebase = "+codeBase+"\n");
		Enumeration params = appletParams.keys();
		if (params==null)
		sb.append("No Params found.\n");
		else
		{
			int cnt = 0;
			for (;params.hasMoreElements();)
			{
				String paramName = (String)params.nextElement();
				String paramValue = (String)appletParams.get(paramName);
				sb.append((cnt++)+": Parameter name = "+paramName+", Parameter value = "+paramValue+"\n");
			}
		}
		if (children()==null)
		sb.append("No Miscellaneous items\n"); else
		{
			sb.append("Miscellaneous items :\n");
			for (Enumeration e = children();e.hasMoreElements();)
			{
				((HTMLTag)e.nextElement()).print();
			}
		}
		sb.append("End of Applet Tag\n");
		sb.append("*****************\n");
		return sb.toString();
	}
}
