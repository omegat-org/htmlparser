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

// Author of this class : Dhaval Udani
// dhaval.h.udani@orbitech.co.in

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
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
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
