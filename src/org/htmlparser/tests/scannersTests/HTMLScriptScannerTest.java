// HTMLParser Library v1_2 - A java-based parser for HTML
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

package org.htmlparser.tests.scannersTests;

import java.util.Hashtable;

import org.htmlparser.HTMLParser;
import org.htmlparser.scanners.HTMLScriptScanner;
import org.htmlparser.tags.HTMLScriptTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class HTMLScriptScannerTest extends HTMLParserTestCase 
{

	public HTMLScriptScannerTest(String name) {
		super(name);
	}

	public void testEvaluate() 
	{
		HTMLScriptScanner scanner = new HTMLScriptScanner("-s");
		boolean retVal = scanner.evaluate("   script ",null);
		assertEquals("Evaluation of SCRIPT tag",new Boolean(true),new Boolean(retVal));
	}

	public void testScan() throws HTMLParserException
	{
		createParser("<SCRIPT>document.write(d+\".com\")</SCRIPT>","http://www.google.com/test/index.html");
		// Register the script scanner
		parser.addScanner(new HTMLScriptScanner("-s"));
		parseAndAssertNodeCount(1);
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
	public void testScanBug() throws HTMLParserException
	{
		createParser("<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"../js/DetermineBrowser.js\"></SCRIPT>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLScriptScanner("-s"));
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] instanceof HTMLScriptTag);
		// Check the data in the applet tag
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		Hashtable table = scriptTag.getAttributes();
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
	public void testScanBugWG() throws HTMLParserException
	{ 
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<body><script language=\"javascript\">\r\n"); 
		sb1.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb1.append(" document.write ('xxx');\r\n"); 
		sb1.append("else\r\n"); 
		sb1.append(" document.write ('yyy');\r\n"); 
		sb1.append("</script>\r\n"); 
		String testHTML1 = new String(sb1.toString()); 
		
		createParser(testHTML1,"http://www.google.com/test/index.html"); 
		HTMLParser.setLineSeparator("\r\n");
		// Register the image scanner 
		parser.addScanner(new HTMLScriptScanner("-s")); 
		
		parseAndAssertNodeCount(2);
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("\r\n"); // !!! CRLF from the TAG Line 
		sb2.append("if(navigator.appName.indexOf(\"Netscape\") != -1)\r\n"); 
		sb2.append(" document.write ('xxx');\r\n"); 
		sb2.append("else\r\n"); 
		sb2.append(" document.write ('yyy');\r\n"); 
		String testHTML2 = new String(sb2.toString()); 

		assertTrue("Node should be a script tag",node[1] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[1];
		assertStringEquals("Expected Script Code",testHTML2,scriptTag.getScriptCode()); 
	}
	
	public void testScanScriptWithLinks() throws HTMLParserException
	{ 
		StringBuffer sb1 = new StringBuffer(); 
		sb1.append("<script type=\"text/javascript\">\r\n"+
			"<A HREF=\"http://thisisabadlink.com\">\r\n"+
			"</script>\r\n");
		String testHTML1 = new String(sb1.toString()); 
		
		createParser(testHTML1,"http://www.hardwareextreme.com/"); 
		// Register the image scanner 
		parser.registerScanners();
		//parser.addScanner(new HTMLScriptScanner("-s")); 
		
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		//assertStringEquals("Expected Script Code",testHTML2,scriptTag.getScriptCode()); 
	}
	
	public void testScanScriptWithComments() throws HTMLParserException {
		createParser("<SCRIPT Language=\"JavaScript\">\n"+
						  "<!--\n"+
						  "  function validateForm()\n"+
						  "  {\n"+
						  "     var i = 10;\n"+
						  "     if(i < 5)\n"+
						  "     i = i - 1 ; \n"+
						  "     return true;\n"+
						  "  }\n"+
						  "// -->\n"+
						  "</SCRIPT>","http://www.hardwareextreme.com/");
		// Register the image scanner 
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a script tag",node[0] 
		instanceof HTMLScriptTag); 
		// Check the data in the applet tag 
		HTMLScriptTag scriptTag = (HTMLScriptTag)node[0];
		String scriptCode = scriptTag.getScriptCode();	  
		String expectedCode = "\r\n<!--\r\n\r\n"+
						  "  function validateForm()\r\n"+
						  "  {\r\n"+
						  "     var i = 10;\r\n"+
						  "     if(i < 5)\r\n"+
						  "     i = i - 1 ; \r\n"+
						  "     return true;\r\n"+
						  "  }\r\n"+
						  "// \r\n-->";
		assertStringEquals("Expected Code",expectedCode,scriptCode);
	}
}
