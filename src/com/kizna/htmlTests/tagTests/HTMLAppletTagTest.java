package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.tags.HTMLAppletTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLAppletTagTest extends TestCase {

	/**
	 * Constructor for HTMLAppletTagTest.
	 * @param arg0
	 */
	public HTMLAppletTagTest(String name) {
		super(name);
	}
	public void testToHTML() {
		String [][]paramsData = {{"Param1","Value1"},{"Name","Somik"},{"Age","23"}};
		Hashtable paramsMap = new Hashtable();
		String testHTML = new String("<APPLET CODE=Myclass.class ARCHIVE=test.jar CODEBASE=www.kizna.com>\n");
		for (int i = 0;i<paramsData.length;i++)
		{
			testHTML+="<PARAM NAME=\""+paramsData[i][0]+"\" VALUE=\""+paramsData[i][1]+"\">\n";
			paramsMap.put(paramsData[i][0],paramsData[i][1]);
		}
		testHTML+=
			"</APPLET>\n"+
			"</HTML>";
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.registerScanners();
			
		int i = 0;
		for (Enumeration e = parser.elements();e.hasMoreElements();)
		{
			node[i++] = (HTMLNode)e.nextElement();
		}
		assertEquals("There should be 2 node identified",new Integer(2),new Integer(i));	
		assertTrue("Node should be an applet tag",node[0] instanceof HTMLAppletTag);
		// Check the data in the applet tag
		HTMLAppletTag appletTag = (HTMLAppletTag)node[0];
		String expectedRawString = 
		"<APPLET CODE=Myclass.class ARCHIVE=test.jar CODEBASE=www.kizna.com>\n"+
		"<PARAM NAME=\"Name\" VALUE=\"Somik\">\n"+
		"<PARAM NAME=\"Param1\" VALUE=\"Value1\">\n"+
		"<PARAM NAME=\"Age\" VALUE=\"23\">\n"+
		"</APPLET>";
		assertEquals("Raw String",expectedRawString,appletTag.toHTML());				
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLAppletTagTest.class);
	}
}
