package com.kizna.htmlTests.tagTests;

import java.io.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

import junit.framework.*;

public class HTMLTextareaTagTest extends TestCase 
{
	private String testHTML = new String(
									"<TEXTAREA name=\"Remarks\">The intervention by the UN proved beneficial</TEXTAREA>" +
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
		HTMLParser parser = new HTMLParser(reader);
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
		assertEquals("HTML String 1","<TEXTAREA NAME=\"Remarks\">The intervention by the UN proved beneficial</TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[1];
		assertEquals("HTML String 2","<TEXTAREA>The capture of the Somali warloard was elusive</TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[2];
		assertEquals("HTML String 3","<TEXTAREA></TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[3];
		assertEquals("HTML String 4","<TEXTAREA NAME=\"Remarks\">\r\nThe death threats of the organization\r\n"+
									"refused to intimidate the soldiers</TEXTAREA>",TextareaTag.toHTML());
		TextareaTag = (HTMLTextareaTag) node[4];
		assertEquals("HTML String 5","<TEXTAREA NAME=\"Remarks\">\r\nThe death threats of the LTTE\r\n" +
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
