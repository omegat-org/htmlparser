// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package org.htmlparser.tests.scannersTests;

import java.io.BufferedReader;
import java.util.Hashtable;
import org.htmlparser.tags.HTMLScriptTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

import java.util.Enumeration;
import org.htmlparser.*;
import java.io.StringReader;
import org.htmlparser.scanners.HTMLScriptScanner;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/18/2001 2:20:43 AM)
 * @author: Administrator
 */
public class HTMLScriptScannerTest extends junit.framework.TestCase 
{
	/**
	 * HTMLAppletScannerTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLScriptScannerTest(String name) {
		super(name);
	}
	public void assertStringEquals(String message,String s1,String s2) {
		for (int i=0;i<s1.length();i++) {
			if (s1.charAt(i)!=s2.charAt(i)) {
				assertTrue(message+
					" \nMismatch of strings at char posn "+i+
					" \nString 1 upto mismatch = "+s1.substring(0,i)+
					" \nString 2 upto mismatch = "+s2.substring(0,i)+
					" \nString 1 mismatch character = "+s1.charAt(i)+", code = "+(int)s1.charAt(i)+
					" \nString 2 mismatch character = "+s2.charAt(i)+", code = "+(int)s2.charAt(i),false);
			}
		}
	}
	public static void main(String[] args) {
		new junit.awtui.TestRunner().start(new String[] {"org.htmlparser.tests.scannersTests.HTMLScriptScannerTest"});
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:22:36 AM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLScriptScannerTest.class);
		return suite;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:23:14 AM)
	 */
	public void testEvaluate() 
	{
		HTMLScriptScanner scanner = new HTMLScriptScanner("-s");
		boolean retVal = scanner.evaluate("   script ",null);
		assertEquals("Evaluation of SCRIPT tag",new Boolean(true),new Boolean(retVal));
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:26:41 AM)
	 */
	public void testScan() throws HTMLParserException
	{
		String testHTML = new String("<SCRIPT>document.write(d+\".com\")</SCRIPT>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLScriptScanner("-s"));
			
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));	
		assertTrue("Node should be a script tag",node[0] instanceof HTMLScriptTag);
		// Check the data in the applet tag
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		assertEquals("Expected Script Code","document.write(d+\".com\")",scriptTag.getScriptCode());
	}
	/**
	 * Bug reported by Gordon Deudney 2002-03-27
	 * Upon parsing :
	 * &lt;SCRIPT LANGUAGE="JavaScript" 
	 * SRC="../js/DetermineBrowser.js"&gt;&lt;/SCRIPT&gt;
	 * the SRC data cannot be retrieved.
	 */
	public void testScanBug() throws HTMLParserException
	{
		String testHTML = new String("<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"../js/DetermineBrowser.js\"></SCRIPT>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLScriptScanner("-s"));
			
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));	
		assertTrue("Node should be a script tag",node[0] instanceof HTMLScriptTag);
		// Check the data in the applet tag
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		Hashtable table = scriptTag.parseParameters();
		String srcExpected = (String)table.get("SRC");
		assertEquals("Expected SRC value","../js/DetermineBrowser.js",srcExpected);
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
	* check getScriptCode(). 
	*/ 
	public void testScanBugWG() throws HTMLParserException
	{ 
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<body><script language=\"javascript\">\r\n"); 
		sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb1.append(" document.write ('xxx');\r\n"); 
		sb1.append("else\r\n"); 
		sb1.append(" document.write ('yyy');\r\n"); 
		sb1.append("</script>\r\n"); 
		String testHTML1 = new String(sb1.toString()); 
		
		StringReader sr = new StringReader(testHTML1); 
		HTMLReader reader = new HTMLReader(new 
		BufferedReader(sr),"http://www.google.com/test/index.html"); 
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback()); 
		parser.setLineSeparator("\r\n");
		HTMLNode [] node = new HTMLNode[10]; 
		// Register the image scanner 
		parser.addScanner(new HTMLScriptScanner("-s")); 
		
		int i = 0; 
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) 
		{ 
			node[i++] = e.nextHTMLNode(); 
		} 
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("\r\n"); // !!! CRLF from the TAG Line 
		sb2.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb2.append(" document.write ('xxx');\r\n"); 
		sb2.append("else\r\n"); 
		sb2.append(" document.write ('yyy');\r\n"); 
		String testHTML2 = new String(sb2.toString()); 
		
		assertEquals("There should be 1 node identified",new 
		Integer(2),new Integer(i)); 
		assertTrue("Node should be a script tag",node[1] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[1];
		assertStringEquals("Expected Script Code",testHTML2,scriptTag.getScriptCode()); 
	}
	public void testScanScriptWithLinks() throws HTMLParserException
	{ 
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<script type=\"text/javascript\">\r\n"+
			"<A HREF=\"http://thisisabadlink.com\">\r\n"+
			"</script>\r\n");
		String testHTML1 = new String(sb1.toString()); 
		
		StringReader sr = new StringReader(testHTML1); 
		HTMLReader reader = new HTMLReader(new 
		BufferedReader(sr),"http://www.hardwareextreme.com/"); 
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback()); 
		HTMLNode [] node = new HTMLNode[10]; 
		// Register the image scanner 
		parser.registerScanners();
		//parser.addScanner(new HTMLScriptScanner("-s")); 
		
		int i = 0; 
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) 
		{ 
			node[i++] = e.nextHTMLNode(); 
		} 
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i)); 
		assertTrue("Node should be a script tag",node[0] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		//assertStringEquals("Expected Script Code",testHTML2,scriptTag.getScriptCode()); 
	}
	public void testScanScriptWithComments() throws HTMLParserException {
		String testHTML = "<SCRIPT Language=\"JavaScript\">\n"+
						  "<!--\n"+
						  "  function validateForm()\n"+
						  "  {\n"+
						  "     var i = 10;\n"+
						  "     if(i < 5)\n"+
						  "     i = i - 1 ; \n"+
						  "     return true;\n"+
						  "  }\n"+
						  "// -->\n"+
						  "</SCRIPT>";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader = new HTMLReader(new 
		BufferedReader(sr),"http://www.hardwareextreme.com/"); 
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback()); 
		HTMLNode [] node = new HTMLNode[10]; 
		// Register the image scanner 
		parser.registerScanners();
		//parser.addScanner(new HTMLScriptScanner("-s")); 
		
		int i = 0; 
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) 
		{ 
			node[i++] = e.nextHTMLNode(); 
		} 
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i)); 
		assertTrue("Node should be a script tag",node[0] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		String scriptCode = scriptTag.getScriptCode();	  
		String expectedCode = "\r\n<!--\r\n\r\n"+
						  "  function validateForm()\r\n"+
						  "  {\r\n"+
						  "     var i = 10;\r\n"+
						  "     if(i < 5)\r\n"+
						  "     i = i - 1 ; \r\n"+
						  "     return true;\r\n"+
						  "  }\r\n"+
						  "// \r\n-->";
		assertStringEquals("Expected Code",expectedCode,scriptCode);
	}
}
