// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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

import java.io.*;
import org.htmlparser.*;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.*;

import junit.framework.*;

public class HTMLTextareaTagTest extends TestCase 
{
	private String testHTML = new String(
									"<TEXTAREA name=\"Remarks\" >The intervention by the UN proved beneficial</TEXTAREA>" +
									"<TEXTAREA>The capture of the Somali warloard was elusive</TEXTAREA>" +
									"<TEXTAREA></TEXTAREA>" +
									"<TEXTAREA name=\"Remarks\">The death threats of the organization\n" +
									"refused to intimidate the soldiers</TEXTAREA>" +
									"<TEXTAREA name=\"Remarks\">The death threats of the LTTE\n" +
									"refused to intimidate the Tamilians\n</TEXTAREA>"
									);
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLTextareaTagTest.
	 * @param arg0
	 */
	public HTMLTextareaTagTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLTextareaTagTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLTextareaTagTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];

		parser.addScanner(new HTMLTextareaTagScanner("-t"));
		
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
	
	public void testGetSetValue()
	{
		assertEquals("There should be 5 nodes identified",5,i);
		assertTrue("Node 1 should be Textarea Tag",node[0] instanceof HTMLTextareaTag);
		HTMLTextareaTag TextareaTag;
		TextareaTag = (HTMLTextareaTag) node[0];
		TextareaTag.setValue("The IPKF played a major role");
		assertEquals("Set-Get test","The IPKF played a major role",TextareaTag.getValue());
	}
	
	public void testToHTML() throws HTMLParserException 
	{
		
		assertEquals("There should be 5 nodes identified",5,i);	
		assertTrue("Node 1 should be Textarea Tag",node[0] instanceof HTMLTextareaTag);
		assertTrue("Node 2 should be Textarea Tag",node[1] instanceof HTMLTextareaTag);
		assertTrue("Node 3 should be Textarea Tag",node[2] instanceof HTMLTextareaTag);
		assertTrue("Node 4 should be Textarea Tag",node[3] instanceof HTMLTextareaTag);
		assertTrue("Node 5 should be Textarea Tag",node[4] instanceof HTMLTextareaTag);
		HTMLTextareaTag TextareaTag;
		TextareaTag = (HTMLTextareaTag) node[0];
		HTMLTagTest.assertStringEquals("HTML String 1","<TEXTAREA NAME=\"Remarks\">The intervention by the UN proved beneficial</TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[1];
		HTMLTagTest.assertStringEquals("HTML String 2","<TEXTAREA>The capture of the Somali warloard was elusive</TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[2];
		HTMLTagTest.assertStringEquals("HTML String 3","<TEXTAREA></TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[3];
		HTMLTagTest.assertStringEquals("HTML String 4","<TEXTAREA NAME=\"Remarks\">\r\nThe death threats of the organization\r\n"+
									"refused to intimidate the soldiers</TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[4];
		HTMLTagTest.assertStringEquals("HTML String 5","<TEXTAREA NAME=\"Remarks\">\r\nThe death threats of the LTTE\r\n" +
									"refused to intimidate the Tamilians\r\n</TEXTAREA>",TextareaTag.toHTML());

	}	
	
	
	public void testToString() throws HTMLParserException 
	{
		
		assertEquals("There should be 4 nodes identified",5,i);	
		assertTrue("Node 1 should be Textarea Tag",node[0] instanceof HTMLTextareaTag);
		assertTrue("Node 2 should be Textarea Tag",node[1] instanceof HTMLTextareaTag);
		assertTrue("Node 3 should be Textarea Tag",node[2] instanceof HTMLTextareaTag);
		assertTrue("Node 4 should be Textarea Tag",node[3] instanceof HTMLTextareaTag);
		assertTrue("Node 5 should be Textarea Tag",node[4] instanceof HTMLTextareaTag);
		HTMLTextareaTag TextareaTag;
		TextareaTag = (HTMLTextareaTag) node[0];
		assertEquals("HTML Raw String 1","TEXTAREA TAG\n--------\nNAME : Remarks\nVALUE : The intervention by the UN proved beneficial\n",TextareaTag.toString());
		TextareaTag = (HTMLTextareaTag) node[1];
		assertEquals("HTML Raw String 2","TEXTAREA TAG\n--------\nVALUE : The capture of the Somali warloard was elusive\n",TextareaTag.toString());
		TextareaTag = (HTMLTextareaTag) node[2];
		assertEquals("HTML Raw String 3","TEXTAREA TAG\n--------\nVALUE : \n",TextareaTag.toString());
		TextareaTag = (HTMLTextareaTag) node[3];
		assertEquals("HTML Raw String 4","TEXTAREA TAG\n--------\nNAME : Remarks\nVALUE : \r\nThe death threats of the organization\r\n"+
										"refused to intimidate the soldiers\n",TextareaTag.toString());
		TextareaTag = (HTMLTextareaTag) node[4];
		assertEquals("HTML Raw String 5","TEXTAREA TAG\n--------\nNAME : Remarks\nVALUE : \r\nThe death threats of the LTTE\r\n"+
										"refused to intimidate the Tamilians\r\n\n",TextareaTag.toString());
	}
	
}
