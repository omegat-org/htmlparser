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

package org.htmlparser.util;

import java.util.Enumeration;
import java.util.Hashtable;

import org.htmlparser.HTMLReader;
import org.htmlparser.scanners.HTMLTagScanner;
import org.htmlparser.tags.HTMLTag;

public class HTMLParserUtils
{
	public static boolean evaluateTag(HTMLTagScanner pTagScanner, 
										String pTagString, String pTagName)
	{
		pTagString = HTMLTagScanner.absorbLeadingBlanks(pTagString);
		if (pTagString.toUpperCase().indexOf(pTagName)==0)
			return true; 
		else 
			return false;
	}
	
	public static String toHTML(HTMLTag tag)
	{
		StringBuffer htmlString = new StringBuffer();
		
		Hashtable attrs = tag.getAttributes();
		String pTagName = tag.getParameter(HTMLTag.TAGNAME);
		htmlString.append("<").append(pTagName);
		for (Enumeration e = attrs.keys();e.hasMoreElements();)
		{
			String key = (String)e.nextElement();
			String value = (String)attrs.get(key);
			if (!key.equalsIgnoreCase(HTMLTag.TAGNAME) && value.length()>0)
				htmlString.append(" ").append(key).append("=\"").append(value).append("\"");
		}
		htmlString.append(">");
		
		return htmlString.toString();
	}
	
	public static String toString(HTMLTag tag)
	{
		String tagName = tag.getParameter(HTMLTag.TAGNAME);
		Hashtable attrs = tag.getAttributes();
		
		StringBuffer lString = new StringBuffer(tagName);
		lString.append(" TAG\n");
		lString.append("--------\n");
		
		for (Enumeration e = attrs.keys();e.hasMoreElements();)
		{
			String key = (String)e.nextElement();
			String value = (String)attrs.get(key);
			if (!key.equalsIgnoreCase(HTMLTag.TAGNAME) && value.length()>0)
				lString.append(key).append(" : ").append(value).append("\n");
		}
		
		return lString.toString();
	}
	
	public static Hashtable adjustScanners(HTMLReader pReader) 
	{
		Hashtable tempScanners= new Hashtable();
		tempScanners = pReader.getParser().getScanners();		
		// Remove all existing scanners
		pReader.getParser().flushScanners();
		return tempScanners;
	}
	public static void restoreScanners(HTMLReader pReader, Hashtable tempScanners)
	{
		// Flush the scanners
		pReader.getParser().setScanners(tempScanners);
	}
}