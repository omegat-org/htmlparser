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
	
	public static String toHTML(HTMLTag pTag)
	{
		StringBuffer lHTMLString = new StringBuffer();
		
		Hashtable lAttrs = pTag.getParsed();
		String pTagName = pTag.getParameter(HTMLTag.TAGNAME);
		lHTMLString.append("<").append(pTagName);
		Enumeration e = lAttrs.keys();
		while (e.hasMoreElements())
		{
			String lKey = (String)e.nextElement();
			String lValue = (String)lAttrs.get(lKey);
			if (!lKey.equalsIgnoreCase(HTMLTag.TAGNAME))
				lHTMLString.append(" ").append(lKey).append("=\"").append(lValue).append("\"");
		}
		lHTMLString.append(">");
		
		return lHTMLString.toString();
	}
	
	public static String toString(HTMLTag pTag)
	{
		String pTagName = pTag.getParameter(HTMLTag.TAGNAME);
		Hashtable lAttrs = pTag.getParsed();
		
		StringBuffer lString = new StringBuffer(pTagName);
		lString.append(" TAG\n");
		lString.append("--------\n");
			
		Enumeration e = lAttrs.keys();
		while (e.hasMoreElements())
		{
			String lKey = (String)e.nextElement();
			String lValue = (String)lAttrs.get(lKey);
			if (!lKey.equalsIgnoreCase(HTMLTag.TAGNAME))
				lString.append(lKey).append(" : ").append(lValue).append("\n");
		}
		
		return lString.toString();
	}
	
	public static Vector adjustScanners(HTMLReader pReader) 
	{
		Vector lScannerVector = new Vector();
		for (Enumeration e=pReader.getParser().getScanners();e.hasMoreElements();) 
		{
			lScannerVector.addElement(e.nextElement());
		}
		
		// Remove all existing scanners
		pReader.getParser().flushScanners();
		return lScannerVector;
	}
	
	public static void restoreScanners(HTMLReader pReader, Vector pScannerVector)
	{
		// Flush the scanners
		pReader.getParser().flushScanners();
	
		// Add all the original scanners back
		for (Enumeration e = pScannerVector.elements();e.hasMoreElements();)
		{
			pReader.getParser().addScanner((HTMLTagScanner)e.nextElement());
		}
	}	
}