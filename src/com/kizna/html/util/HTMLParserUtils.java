package com.kizna.html.util;

import java.util.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.scanners.*;

public class HTMLParserUtils
{
	public static boolean evaluateTag(HTMLTagScanner pTagScanner, 
										String pTagString, String pTagName)
	{
		pTagString = pTagScanner.absorbLeadingBlanks(pTagString);
		if (pTagString.toUpperCase().indexOf(pTagName)==0)
			return true; 
		else 
			return false;
	}
	
	public static String toHTML(HTMLTag tag)
	{
		StringBuffer htmlString = new StringBuffer();
		
		Hashtable attrs = tag.getParsed();
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
		Hashtable attrs = tag.getParsed();
		
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