package com.kizna.htmlTests.scannersTests;

// HTMLParser Library v1.04 - A java-based parser for HTML
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

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.StringReader;

import com.kizna.html.*;
import com.kizna.html.tags.HTMLImageTag;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.scanners.HTMLImageScanner;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/18/2001 2:20:43 AM)
 * @author: Administrator
 */
public class HTMLImageScannerTest extends junit.framework.TestCase 
{
/**
 * HTMLAppletScannerTest constructor comment.
 * @param name java.lang.String
 */
public HTMLImageScannerTest(String name) {
	super(name);
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:36 AM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() 
{
	TestSuite suite = new TestSuite(HTMLImageScannerTest.class);
	return suite;
}
/**
 * Insert the method's description here.
 * Creation date: (12/25/2001 12:02:50 PM)
 */
public void testDynamicRelativeImageScan() {
	String testHTML = "<IMG SRC=\"../abc/def/mypic.jpg\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi?abcdefg");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-l"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageLocation());
}
/**
 * Insert the method's description here.
 * Creation date: (6/18/2001 2:23:14 AM)
 */
public void testEvaluate() 
{
	HTMLImageScanner scanner = new HTMLImageScanner("-i");
	boolean retVal = scanner.evaluate("   img ",null);
	assertEquals("Evaluation of IMG tag",new Boolean(true),new Boolean(retVal));
}
/**
 * This is the reproduction of a bug which causes a null pointer exception
 * Creation date: (6/18/2001 2:26:41 AM)
 */
public void testExtractImageLocnInvertedCommasBug()
{
	System.out.println("testExtractImageLocnBug()");
	HTMLTag tag = new HTMLTag(0,0,"img width=638 height=53 border=0 usemap=\"#m\" src=http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif alt=Yahoo","");
	String link = "img width=638 height=53 border=0 usemap=\"#m\" src=http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif alt=Yahoo";
	String url = "c:\\cvs\\html\\binaries\\yahoo.htm";
	HTMLImageScanner scanner = new HTMLImageScanner("-i");
	assertEquals("Extracted Image Locn","http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif",scanner.extractImageLocn(tag,url));
}
/**
 * Insert the method's description here.
 * Creation date: (12/25/2001 12:45:41 PM)
 */
public void testPlaceHolderImageScan() {
	String testHTML = "<IMG width=1 height=1 alt=\"\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi?abcdefg");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-i"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Image Locn","",imageTag.getImageLocation());		
}
/**
 * Insert the method's description here.
 * Creation date: (12/25/2001 12:02:50 PM)
 */
public void testRelativeImageScan() {
	String testHTML = "<IMG SRC=\"mypic.jpg\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-l"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Link","http://www.yahoo.com/mypic.jpg",imageTag.getImageLocation());
}
/**
 * Insert the method's description here.
 * Creation date: (12/25/2001 12:02:50 PM)
 */
public void testRelativeImageScan2() {
	String testHTML = "<IMG SRC=\"abc/def/mypic.jpg\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-l"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageLocation());
}
/**
 * Insert the method's description here.
 * Creation date: (12/25/2001 12:02:50 PM)
 */
public void testRelativeImageScan3() {
	String testHTML = "<IMG SRC=\"../abc/def/mypic.jpg\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-l"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageLocation());
}
/**
 * Test image url which contains spaces in it.
 * This was actually a bug reported by Sam Joseph (sam@neurogrid.net)
 */
public void testImageWithSpaces() 
{
	String testHTML = "<IMG SRC=\"../abc/def/Hello World.jpg\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-l"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Link","http://www.yahoo.com/abc/def/Hello World.jpg",imageTag.getImageLocation());		
}
public void testImageWithNewLineChars() 
{
	String testHTML = "<IMG SRC=\"../abc/def/Hello \nWorld.jpg\">";
	StringReader sr = new StringReader(testHTML); 
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
	// Register the image scanner
	parser.addScanner(new HTMLImageScanner("-l"));
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();) {
		node[i++] = (HTMLTag)e.nextElement();
	}	
	assertEquals("Number of nodes identified should be 1",1,i);
	assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
	HTMLImageTag imageTag = (HTMLImageTag)node[0];
	assertEquals("Expected Link","http://www.yahoo.com/abc/def/Hello World.jpg",imageTag.getImageLocation());		
}
}
