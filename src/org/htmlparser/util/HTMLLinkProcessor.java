// HTMLParser Library v1_2_20021120 - A java-based parser for HTML
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

package org.htmlparser.util;
import java.util.Enumeration;
import java.util.Vector;
/**
 * Processor class for links, is present basically as a utility class.
 */
public class HTMLLinkProcessor {
	private String baseUrl;
	/**
	 * HTMLExtractor constructor comment.
	 */
	public HTMLLinkProcessor() {
		this.baseUrl=null;
	}
	/**
	 * Parse a string and add all the directories found into a vector
	 * @directories Directory vector
	 * @temp String to be parsed
	 */ 
	public void addDirectoriesToVector(Vector directories, String temp) {
		int n;			
		do
		{
			n = temp.indexOf("/");
			if (n!=-1)
			{
				directories.addElement(temp.substring(0,n+1));
				temp=temp.substring(n+1,temp.length());
			}
		}
		while (n!=-1);
		// Check if remaining value of temp, has any .htm in it. If not, add it to the directories
		if (temp.length()>0)
		{
			int dotPos =  temp.toUpperCase().indexOf(".");
			int wwwPos = temp.toUpperCase().indexOf("WWW");
			if (dotPos==-1 || wwwPos!=-1)
			directories.addElement(temp+'/');
		}
	}
	/**
	 * Check if a given link is relative, if yes, add the 
	 * base url to it and return, else return unmodified.
	 * @param link The link to be checked
	 * @param url The base url
	 * @return String Absolute URL
	 */
	public String checkIfLinkIsRelative(String link, String url) {
		if (link==null || link.length()==0) return "";
		int slashLoc = link.indexOf("://");
		int queryLoc = link.indexOf('?');
		if ((slashLoc==-1 || (queryLoc!=-1 && queryLoc<slashLoc)) && (link.indexOf("javascript:") == -1) && link.indexOf("mailto:")==-1 && url != null)
		{
			// Bug fix by Karem for relative links that
			// begin with /
			if (link.charAt(0)=='/') { 
				link = processSlashIsFirstChar(link, url);
			} else {
				if (baseUrl!=null) link = baseUrl+"/"+link; else
				link = processAppendRelativeLink(link, url);	
			}
		}
		return link;
	}
	public boolean doesQueryComeBeforeSlash(int slashLoc, int queryLoc) {
		return (queryLoc!=-1 && queryLoc<slashLoc);
	}
	public String processAppendRelativeLink(String link, String url) {
		String temp=url;
		Vector directories = new Vector();
		addDirectoriesToVector(directories, temp);
		link = handleRelationalPath(directories, link);
		String dir = createDirectory(directories);
		link = removeFirstSlashIfFound(link);
		link=dir+link;	
		return link;
	}
	public String processSlashIsFirstChar(String link, String url) {
		int i = url.indexOf("/",7); 
		if (i==-1) { 
			link = url+link; 
		} else { 
			link = url.substring(0,i)+link; 
		} 
		return link;
	}
	/** 
	 * Create the directory by summing all the contents
	 * of the directory vector
	 * @param directories
	 * @return String The Directory string
	 */
	public String createDirectory(Vector directories) {
		String dir="";			
		for (Enumeration e = directories.elements();e.hasMoreElements();)
		{
			dir += (String)e.nextElement();
		}
		return dir;
	}
	
	public String extract(String link,String url) throws HTMLParserException {
		try {
			link = checkIfLinkIsRelative(link, url);
			
			
			// Check if there are any escape characters to be filtered out (we are
			// currently removing #38;
			link=removeEscapeCharacters(link);
			return link;
		}
		catch (Exception e) {
			throw new HTMLParserException("HTMLLinkProcessor.extract() : Error in extraction, link = "+link+", url = "+url,e);
		}
	}
	public String handleRelationalPath(Vector directories, String link) {
		int dotLoc = link.indexOf("..");
		while (dotLoc==0)
		{
			link = link.substring(3,link.length());
			if (directories.size()>1)
			directories.removeElementAt(directories.size()-1);
			dotLoc=link.indexOf("..");
		}
		return link;
	}
	public static String fixSpaces(String url) {
		int index = url.indexOf(' ');
		if (index==-1) return url; else {
			StringBuffer returnURL=new StringBuffer(url.substring(0,index));
			char ch;
			for (int i=index;i<url.length();i++) {
				ch = url.charAt(i);
				if (ch==' ') returnURL.append("%20"); else
				returnURL.append(ch);
			}
			return returnURL.toString();
		}
	}
		public static String removeEscapeCharacters(String link)
		{
			int state = 0;
			String temp = "",retVal="";
			for (int i=0;i<link.length();i++)
			{
				char ch = link.charAt(i);
				if (state==4) 
				{
					state=0;
				}
				if (ch=='#' && state==0) 
				{
					state=1;
					continue;
				}
				if (state==1)
				{
					if (ch=='3')
					{
						state=2; 
						continue;
					}
					else
					{
						state=0;
						retVal+=temp;
					}
				}		
				if (state==2)
				{
					if (ch=='8')
					{
					 	state=3;
						continue;
					}
					else
					{
						state=0;
						retVal+=temp;
					}
				}	
				if (state==3)
				{
					if (ch==';')
					{
						state=4;
						continue;
					}
					else
					{
						state=0;
						retVal+=temp;
					}
				}				
				if (state==0) retVal+=ch; else temp+=ch;
			}
			return retVal;
		}
	public String removeFirstSlashIfFound(String link) {
		if (link==null || link.length()==0) return null;
		if (link.charAt(0)=='/' || link.charAt(0)=='\\')
		link=link.substring(1,link.length());
		return link;
	}
	public static boolean isURL(String resourceLocn) {
		return resourceLocn.indexOf("http")==0 || resourceLocn.indexOf("www.")==0 || resourceLocn.indexOf("file://")==0;
	}	
	/**
	 * Returns the baseUrl.
	 * @return String
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the baseUrl.
	 * @param baseUrl The baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
