// HTMLParser Library v1_2_20021207 - A java-based parser for HTML
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

package org.htmlparser.tests.tagTests;

import java.util.*;
import java.io.*;
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.*;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLScriptTagTest extends HTMLParserTestCase{
	private HTMLScriptScanner scriptScanner;

	public HTMLScriptTagTest(String name) 
	{
		super(name);	
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		scriptScanner = new HTMLScriptScanner();	
	}

	public void testCreation() {
		HTMLScriptTag scriptTag = new HTMLScriptTag(0,10,"Tag Contents","Script Code","english","text","tagline");
		assertNotNull("Script Tag object creation",scriptTag);
		assertEquals("Script Tag Begin",0,scriptTag.elementBegin());
		assertEquals("Script Tag End",10,scriptTag.elementEnd());
		assertEquals("Script Tag Language","english",scriptTag.getLanguage());		
		assertEquals("Script Tag Contents","Tag Contents",scriptTag.getText());
		assertEquals("Script Tag Code","Script Code",scriptTag.getScriptCode());
		assertEquals("Script Tag Type","text",scriptTag.getType());
		assertEquals("Script Tag Line","tagline",scriptTag.getTagLine());
	}

	public void testExtractLanguage() 
	{
		scriptScanner.extractLanguage(new HTMLTag(10,10,"script language=\"JavaScript\"",""));
		assertEquals("JavaScript",scriptScanner.getLanguage());
	
		scriptScanner.extractLanguage(new HTMLTag(10,10,"SCRIPT TYPE=\"text/javascript\"",""));
		assertEquals("",scriptScanner.getLanguage());
		
	}

	public void testExtractType() 
	{
		scriptScanner.extractType(new HTMLTag(10,10,"script language=\"JavaScript\"",""));
		assertEquals("",scriptScanner.getType());
	
		scriptScanner.extractType(new HTMLTag(10,10,"SCRIPT TYPE=\"text/javascript\"",""));
		assertEquals("text/javascript",scriptScanner.getType());	
	}

	public void testToHTML() throws HTMLParserException {
		createParser("<SCRIPT>document.write(d+\".com\")</SCRIPT>");
		// Register the image scanner
		parser.addScanner(new HTMLScriptScanner("-s"));
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof HTMLScriptTag);
		// Check the data in the applet tag
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		assertEquals("Expected Raw String","<SCRIPT>document.write(d+\".com\")</SCRIPT>",scriptTag.toHTML());
	}
	
	/** 
	* Bug check by Wolfgang Germund 2002-06-02 
	* Upon parsing : 
	* &lt;script language="javascript"&gt; 
	* if(navigator.appName.indexOf("Netscape") != -1) 
	* document.write ('xxx'); 
	* else 
	* document.write ('yyy'); 
	* &lt;/script&gt; 
	* check toRawString(). 
	*/ 
	public void testToHTMLWG() throws HTMLParserException
	{ 
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<body><script language=\"javascript\">\r\n"); 
		sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb1.append(" document.write ('xxx');\r\n"); 
		sb1.append("else\r\n"); 
		sb1.append(" document.write ('yyy');\r\n"); 
		sb1.append("</script>\r\n"); 
		String testHTML1 = new String(sb1.toString()); 
		
		createParser(testHTML1); 
		parser.setLineSeparator("\r\n");
		// Register the image scanner 
		parser.addScanner(new HTMLScriptScanner("-s")); 
		
		
		StringBuffer sb2 = new StringBuffer(); 
		sb2.append("<script language=\"javascript\">\r\n"); 
		sb2.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb2.append(" document.write ('xxx');\r\n"); 
		sb2.append("else\r\n"); 
		sb2.append(" document.write ('yyy');\r\n"); 
		sb2.append("</SCRIPT>"); 
		String testHTML2 = new String(sb2.toString()); 
		
		parseAndAssertNodeCount(2);
		assertTrue("Node should be a script tag",node[1] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node 
		[1]; 
		assertEquals("Expected Script Code",testHTML2,scriptTag.toHTML()); 
	} 
	
	public void testParamExtraction() throws HTMLParserException {
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<script src=\"/adb.js\" language=\"javascript\">\r\n"); 
		sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb1.append(" document.write ('xxx');\r\n"); 
		sb1.append("else\r\n"); 
		sb1.append(" document.write ('yyy');\r\n"); 
		sb1.append("</script>\r\n"); 
		createParser(sb1.toString()); 
		
		// Register the image scanner 
		parser.addScanner(new HTMLScriptScanner("-s")); 
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof HTMLScriptTag);
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0]; 
		assertEquals("Script Src","/adb.js",scriptTag.getParameter("src"));
		assertEquals("Script Language","javascript",scriptTag.getParameter("language"));		
	}
}
