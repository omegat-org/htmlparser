// HTMLParser Library v1_2_20020630 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com
package com.kizna.htmlTests.scannersTests;

import java.io.BufferedReader;
import java.util.Hashtable;
import com.kizna.html.tags.HTMLScriptTag;
import com.kizna.html.tags.HTMLTag;
import java.util.Enumeration;
import com.kizna.html.*;
import java.io.StringReader;
import com.kizna.html.scanners.HTMLScriptScanner;
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
	new junit.awtui.TestRunner().start(new String[] {"com.kizna.htmlTests.scannersTests.HTMLScriptScannerTest"});
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
public void testScan()
{
	String testHTML = new String("<SCRIPT>document.write(d+\".com\")</SCRIPT>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLScriptScanner("-s"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
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
public void testScanBug()
{
	String testHTML = new String("<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"../js/DetermineBrowser.js\"></SCRIPT>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLScriptScanner("-s"));
		
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
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
public void testScanBugWG() 
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
	HTMLParser parser = new HTMLParser(reader); 
	HTMLNode [] node = new HTMLNode[10]; 
	// Register the image scanner 
	parser.addScanner(new HTMLScriptScanner("-s")); 
	
	int i = 0; 
	for (Enumeration e = parser.elements 
	();e.hasMoreElements();) 
	{ 
	node[i++] = (HTMLNode)e.nextElement(); 
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
}
