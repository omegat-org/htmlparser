package com.kizna.htmlTests.tagTests;

import java.io.*;
import junit.framework.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

public class HTMLInputTagTest extends TestCase {
	
	private String testHTML = new String("<INPUT type=\"text\" name=\"Google\">");
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLInputTagTest.
	 * @param arg0
	 */
	public HTMLInputTagTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLInputTagTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLInputTagTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader);
		node = new HTMLNode[20];

		parser.addScanner(new HTMLInputTagScanner("-i"));
		
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
	}
	
	public void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testToHTML() throws HTMLParserException 
	{
		
		assertEquals("There should be 1 node identified",1,i);	
		assertTrue("Node 1 should be INPUT Tag",node[0] instanceof HTMLInputTag);
		HTMLInputTag InputTag;
		InputTag = (HTMLInputTag) node[0];
		assertEquals("HTML String","<INPUT NAME=\"Google\" TYPE=\"text\">",InputTag.toHTML());
	}
	
	public void testToString() throws HTMLParserException 
	{
		
		assertEquals("There should be 1 node identified",1,i);	
		assertTrue("Node 1 should be INPUT Tag",node[0] instanceof HTMLInputTag);
		HTMLInputTag InputTag;
		InputTag = (HTMLInputTag) node[0];
		assertEquals("HTML Raw String","INPUT TAG\n--------\nNAME : Google\nTYPE : text\n",InputTag.toString());
	}
	
}
