// HTMLParser Library v1_3_20030405 - A java-based parser for HTML
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
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.scanners.TagScanner;
import org.htmlparser.tags.Tag;

public class ParserUtils
{
	public static boolean evaluateTag(TagScanner pTagScanner, 
										String pTagString, String pTagName)
	{
		pTagString = TagScanner.absorbLeadingBlanks(pTagString);
		if (pTagString.toUpperCase().indexOf(pTagName)==0)
			return true; 
		else 
			return false;
	}
	
	public static String toHTML(Tag tag)
	{
		StringBuffer htmlString = new StringBuffer();
		
		Hashtable attrs = tag.getAttributes();
		String pTagName = tag.getAttribute(Tag.TAGNAME);
		htmlString.append("<").append(pTagName);
		for (Enumeration e = attrs.keys();e.hasMoreElements();)
		{
			String key = (String)e.nextElement();
			String value = (String)attrs.get(key);
			if (!key.equalsIgnoreCase(Tag.TAGNAME) && value.length()>0)
				htmlString.append(" ").append(key).append("=\"").append(value).append("\"");
		}
		htmlString.append(">");
		
		return htmlString.toString();
	}
	
	public static String toString(Tag tag)
	{
		String tagName = tag.getAttribute(Tag.TAGNAME);
		Hashtable attrs = tag.getAttributes();
		
		StringBuffer lString = new StringBuffer(tagName);
		lString.append(" TAG\n");
		lString.append("--------\n");
		
		for (Enumeration e = attrs.keys();e.hasMoreElements();)
		{
			String key = (String)e.nextElement();
			String value = (String)attrs.get(key);
			if (!key.equalsIgnoreCase(Tag.TAGNAME) && value.length()>0)
				lString.append(key).append(" : ").append(value).append("\n");
		}
		
		return lString.toString();
	}
	
	public static Map adjustScanners(NodeReader reader) 
	{
		Map tempScanners= new Hashtable();
		tempScanners = reader.getParser().getScanners();		
		// Remove all existing scanners
		reader.getParser().flushScanners();
		return tempScanners;
	}
	public static void restoreScanners(NodeReader reader, Map tempScanners)
	{
		// Flush the scanners
		reader.getParser().setScanners(tempScanners);
	}

	public static String removeChars(String s,char occur)  {
	    StringBuffer newString = new StringBuffer();
	    char ch;
	    for (int i=0;i<s.length();i++) {
	      ch = s.charAt(i);
	      if (ch!=occur) newString.append(ch);
	    }
	    return newString.toString();
	}

	public static String removeEscapeCharacters(String inputString) {
		inputString = ParserUtils.removeChars(inputString,'\r');
		inputString = ParserUtils.removeChars(inputString,'\n');
		inputString = ParserUtils.removeChars(inputString,'\t');
		return inputString;
	}

	public static String removeLeadingBlanks(String plainText) {
		while (plainText.indexOf(' ')==0) 
			plainText=plainText.substring(1);
		return plainText;
	}

	public static String removeTrailingBlanks(String text) {
		char ch = ' ' ;
		while (ch==' '){
			ch = text.charAt(text.length()-1);
			if (ch==' ') 
				text = text.substring(0,text.length()-1);				
		}
		return text;
	}

	/**
	 * Search given node and pick up any objects of given type, return
	 * Node array.
	 * @param node
	 * @param type
	 * @return Node[]
	 */
	public static Node[] findTypeInNode(Node node, Class type) {
		NodeList nodeList = new NodeList();
		node.collectInto(nodeList,type);
		Node spans [] = nodeList.toNodeArray();
		return spans;
	}	
	
}