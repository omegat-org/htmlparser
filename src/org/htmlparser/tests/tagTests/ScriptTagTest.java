// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import java.util.Vector;

import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.scanners.ScriptScanner;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.ParserException;

public class ScriptTagTest extends HTMLParserTestCase{
	private ScriptScanner scriptScanner;

	public ScriptTagTest(String name) 
	{
		super(name);	
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		scriptScanner = new ScriptScanner();	
	}

	public void testCreation() {
		HTMLStringNode stringNode = 
			new HTMLStringNode(new StringBuffer("Script Code"),0,0);
		Vector childVector = new Vector();
		childVector.add(stringNode);
		ScriptTag scriptTag = 
		new ScriptTag(
			new TagData(0,10,"Tag Contents","tagline"),
			new CompositeTagData(null,null,childVector)
		);
		
		assertNotNull("Script Tag object creation",scriptTag);
		assertEquals("Script Tag Begin",0,scriptTag.elementBegin());
		assertEquals("Script Tag End",10,scriptTag.elementEnd());
		assertEquals("Script Tag Contents","Tag Contents",scriptTag.getText());
		assertEquals("Script Tag Code","Script Code",scriptTag.getScriptCode());
		assertEquals("Script Tag Line","tagline",scriptTag.getTagLine());
	}


	public void testToHTML() throws ParserException {
		createParser("<SCRIPT>document.write(d+\".com\")</SCRIPT>");
		// Register the image scanner
		parser.addScanner(new ScriptScanner("-s"));
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
		// Check the data in the applet tag
		ScriptTag scriptTag = (ScriptTag)node[0];
		assertEquals("Expected Raw String","<SCRIPT>document.write(d+\".com\")</SCRIPT>",scriptTag.toHTML());
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
	* check toRawString(). 
	*/ 
	public void testToHTMLWG() throws ParserException
	{ 
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<body><script language=\"javascript\">\r\n"); 
		sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb1.append(" document.write ('xxx');\r\n"); 
		sb1.append("else\r\n"); 
		sb1.append(" document.write ('yyy');\r\n"); 
		sb1.append("</script>\r\n"); 
		String testHTML1 = new String(sb1.toString()); 
		
		createParser(testHTML1); 
		HTMLParser.setLineSeparator("\r\n");
		// Register the image scanner 
		parser.addScanner(new ScriptScanner("-s")); 
		
		
		StringBuffer sb2 = new StringBuffer(); 
		sb2.append("<SCRIPT LANGUAGE=\"javascript\">\r\n"); 
		sb2.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb2.append(" document.write ('xxx');\r\n"); 
		sb2.append("else\r\n"); 
		sb2.append(" document.write ('yyy');\r\n"); 
		sb2.append("</SCRIPT>"); 
		String expectedHTML = new String(sb2.toString()); 
		
		parseAndAssertNodeCount(2);
		assertTrue("Node should be a script tag",node[1] 
		instanceof ScriptTag); 
		// Check the data in the applet tag 
		ScriptTag scriptTag = (ScriptTag)node 
		[1]; 
		assertStringEquals("Expected Script Code",expectedHTML,scriptTag.toHTML()); 
	} 
	
	public void testParamExtraction() throws ParserException {
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<script src=\"/adb.js\" language=\"javascript\">\r\n"); 
		sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb1.append(" document.write ('xxx');\r\n"); 
		sb1.append("else\r\n"); 
		sb1.append(" document.write ('yyy');\r\n"); 
		sb1.append("</script>\r\n"); 
		createParser(sb1.toString()); 
		
		// Register the image scanner 
		parser.addScanner(new ScriptScanner("-s")); 
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
		ScriptTag scriptTag = (ScriptTag)node[0]; 
		assertEquals("Script Src","/adb.js",scriptTag.getAttribute("src"));
		assertEquals("Script Language","javascript",scriptTag.getAttribute("language"));		
	}

	public void testVariableDeclarations() throws ParserException {
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<script language=\"javascript\">\n"); 
		sb1.append("var lower = '<%=lowerValue%>';\n"); 
		sb1.append("</script>\n"); 
		createParser(sb1.toString()); 
		
		// Register the image scanner 
		parser.addScanner(new ScriptScanner("-s")); 
		//parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
		ScriptTag scriptTag = (ScriptTag)node[0]; 
		assertStringEquals("Script toHTML()","<SCRIPT LANGUAGE=\"javascript\">\r\nvar lower = '<%=lowerValue%>';\r\n</SCRIPT>",scriptTag.toHTML());
	}	

	public void testSingleApostropheParsingBug() throws ParserException {
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<script src='<%=sourceFileName%>'></script>"); 
		createParser(sb1.toString()); 
		
		// Register the image scanner 
		parser.addScanner(new ScriptScanner("-s")); 
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof ScriptTag);
		ScriptTag scriptTag = (ScriptTag)node[0]; 
		assertStringEquals("Script toHTML()","<SCRIPT SRC=\"<%=sourceFileName%>\"></SCRIPT>",scriptTag.toHTML());
	}
	
}
