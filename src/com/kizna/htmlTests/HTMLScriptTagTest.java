package com.kizna.htmlTests;

import java.util.*;
import java.io.*;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.scanners.*;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Insert the type's description here.
 * Creation date: (6/4/2001 11:21:07 AM)
 * @author: Somik Raha - Indraprastha
 */
public class HTMLScriptTagTest extends TestCase{
	private HTMLScriptScanner scriptScanner;
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:01 AM)
 */
public HTMLScriptTagTest(String name) 
{
	super(name);	
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:24:33 AM)
 */
protected void setUp() 
{
	scriptScanner = new HTMLScriptScanner();	
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:36 AM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() {
	TestSuite suite = new TestSuite(HTMLScriptTagTest.class);
	return suite;
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
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:24 AM)
 */
public void testEvaluate() 
{
	boolean result = scriptScanner.evaluate("script language=\"JavaScript\"",null);
	assertEquals("Script Tag Evaluation #1",new Boolean(true),new Boolean(result));
	
	result = scriptScanner.evaluate("SCRIPT TYPE=\"text/javascript\"",null);
	assertEquals("Script Tag Evaluation #2",new Boolean(true),new Boolean(result));

	result = scriptScanner.evaluate("META content=\"KIZNA Corporation, offers one stop solution for wireless community and collaboration Web sites.\" name=description",null);
	assertEquals("Script Tag Evaluation #3",new Boolean(false),new Boolean(result));	
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:51:06 AM)
 */
public void testExtractLanguage() 
{
	scriptScanner.extractLanguage(new HTMLTag(10,10,"script language=\"JavaScript\"",""));
	assertEquals("JavaScript",scriptScanner.getLanguage());

	scriptScanner.extractLanguage(new HTMLTag(10,10,"SCRIPT TYPE=\"text/javascript\"",""));
	assertEquals("",scriptScanner.getLanguage());
	
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 12:19:43 PM)
 */
public void testExtractType() 
{
	scriptScanner.extractType(new HTMLTag(10,10,"script language=\"JavaScript\"",""));
	assertEquals("",scriptScanner.getType());

	scriptScanner.extractType(new HTMLTag(10,10,"SCRIPT TYPE=\"text/javascript\"",""));
	assertEquals("text/javascript",scriptScanner.getType());	
}
	public void testToRawString() {
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
		assertEquals("Expected Raw String","<SCRIPT>document.write(d+\".com\")</SCRIPT>",scriptTag.toRawString());
	}
}
