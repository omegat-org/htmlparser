package com.kizna.htmlTests.tagTests;

import java.io.*;
import java.util.*;
import com.kizna.html.*;
import com.kizna.html.scanners.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.*;

import junit.framework.*;

public class HTMLSelectTagTest extends TestCase 
{
	private String testHTML = new String(
									"<SELECT name=\"Nominees\">\n"+
									"<option value=\"Spouse\">Spouse"+
									"<option value=\"Father\"></option>\n"+
									"<option value=\"Mother\">Mother\n" +
									"<option value=\"Son\">\nSon\n</option>"+
									"<option value=\"Daughter\">\nDaughter\n"+
									"<option value=\"Nephew\">\nNephew</option>\n"+
									"<option value=\"Niece\">Niece\n" +
									"</select>"
									);
	private HTMLNode[] node;
	private int i;
	
	/**
	 * Constructor for HTMLSelectTagTest.
	 * @param arg0
	 */
	public HTMLSelectTagTest(String name) 
	{
		super(name);
	}
	
	public static TestSuite suite() 
	{
		return new TestSuite(HTMLSelectTagTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HTMLSelectTagTest.class.getName()});
	}
	
	public void setUp() throws Exception
	{
		super.setUp();
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		node = new HTMLNode[20];

		parser.addScanner(new HTMLSelectTagScanner("-s"));
		
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
		
		assertEquals("There should be 1 nodes identified",1,i);	
		assertTrue("Node 1 should be Select Tag",node[0] instanceof HTMLSelectTag);
		HTMLSelectTag SelectTag;
		SelectTag = (HTMLSelectTag) node[0];
		assertEquals("HTML String","<SELECT NAME=\"Nominees\">\r\n"+
									"<OPTION VALUE=\"Spouse\">Spouse</OPTION>\r\n"+
									"<OPTION VALUE=\"Father\"></OPTION>\r\n"+
									"<OPTION VALUE=\"Mother\">Mother\r\n</OPTION>\r\n" +
									"<OPTION VALUE=\"Son\">Son\r\n</OPTION>\r\n"+
									"<OPTION VALUE=\"Daughter\">Daughter\r\n</OPTION>\r\n"+
									"<OPTION VALUE=\"Nephew\">Nephew</OPTION>\r\n"+
									"<OPTION VALUE=\"Niece\">Niece\r\n</OPTION>\r\n"+
									"</SELECT>",
									SelectTag.toHTML());
	}	
	
	
	public void testToString() throws HTMLParserException 
	{
		
		assertEquals("There should be 1 nodes identified",1,i);	
		assertTrue("Node 1 should be Select Tag",node[0] instanceof HTMLSelectTag);
		HTMLSelectTag SelectTag;
		SelectTag = (HTMLSelectTag) node[0];
		HTMLTagTest.assertStringEquals("HTML Raw String","SELECT TAG\n--------\nNAME : Nominees\n" +
								"OPTION TAG\n--------\nVALUE : Spouse\nTEXT : Spouse\n\n" +
								"OPTION TAG\n--------\nVALUE : Father\nTEXT : \n\n" +
								"OPTION TAG\n--------\nVALUE : Mother\nTEXT : Mother\r\n\n\n" +
								"OPTION TAG\n--------\nVALUE : Son\nTEXT : Son\r\n\n\n" +
								"OPTION TAG\n--------\nVALUE : Daughter\nTEXT : Daughter\r\n\n\n" +
								"OPTION TAG\n--------\nVALUE : Nephew\nTEXT : Nephew\n\n" +
								"OPTION TAG\n--------\nVALUE : Niece\nTEXT : Niece\r\n\n\n",
							SelectTag.toString());

	}
	
}
