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

package org.htmlparser.tests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.scanners.HTMLFormScanner;	
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLFormTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.tests.tagTests.HTMLTagTest;

import junit.extensions.ExceptionTestCase;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLFormScannerTest extends TestCase {
	public HTMLFormScannerTest(String name) {
		super(name);
	}
	public void testEvaluate() {		
		String line1="form method=\"post\" onsubmit=\"return implementsearch()\" name=frmsearch id=form";
		String line2="FORM method=\"post\" onsubmit=\"return implementsearch()\" name=frmsearch id=form";
		String line3="Form method=\"post\" onsubmit=\"return implementsearch()\" name=frmsearch id=form";
		HTMLFormScanner formScanner = new HTMLFormScanner("");
		assertTrue("Line 1",formScanner.evaluate(line1,null));
		assertTrue("Line 2",formScanner.evaluate(line2,null));
		assertTrue("Line 3",formScanner.evaluate(line3,null));
	}
	public void testScan() throws HTMLParserException {
		String testHTML = new String(
		"<FORM METHOD=\"post\" ACTION=\"do_login.php\" NAME=\"login_form\" onSubmit=\"return CheckData()\">\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>User Name</b></font></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"text\" NAME=\"name\" SIZE=\"20\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>Password</b></font></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"password\" NAME=\"passwd\" SIZE=\"20\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"submit\" NAME=\"submit\" VALUE=\"Login\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<INPUT TYPE=\"hidden\" NAME=\"password\" SIZE=\"20\">\n"+
		"</FORM>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];

		parser.addScanner(new HTMLFormScanner(""));
		
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 nodes identified",1,i);	
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		assertEquals("Method","post",formTag.getFormMethod());
		assertEquals("Location","http://www.google.com/test/do_login.php",formTag.getFormLocation());
		assertEquals("Name","login_form",formTag.getFormName());		
		HTMLTag nameTag = formTag.getInputTag("name");
		HTMLTag passwdTag = formTag.getInputTag("passwd");
		HTMLTag submitTag = formTag.getInputTag("submit");
		HTMLTag dummyTag = formTag.getInputTag("dummy");
		assertNotNull("Input Name Tag should not be null",nameTag);
		assertNotNull("Input Password Tag should not be null",passwdTag);
		assertNotNull("Input Submit Tag should not be null",submitTag);
		assertNull("Input dummy tag should be null",dummyTag);
		
		assertEquals("Input Name Tag","<INPUT TYPE=\"text\" NAME=\"name\" SIZE=\"20\">",nameTag.toHTML());
		assertEquals("Input Password Tag","<INPUT TYPE=\"password\" NAME=\"passwd\" SIZE=\"20\">",passwdTag.toHTML());
		assertEquals("Input Submit Tag","<INPUT TYPE=\"submit\" NAME=\"submit\" VALUE=\"Login\">",submitTag.toHTML());
		
		String tempString = "<FORM METHOD=\"post\" ACTION=\"http://www.google.com/test/do_login.php\" NAME=\"login_form\" ONSUBMIT=\"return CheckData()\">\r\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>User Name</b></font></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"text\" NAME=\"name\" SIZE=\"20\"></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>Password</b></font></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"password\" NAME=\"passwd\" SIZE=\"20\"></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"submit\" NAME=\"submit\" VALUE=\"Login\"></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\r\n"+
		"<INPUT TYPE=\"hidden\" NAME=\"password\" SIZE=\"20\">\r\n"+
		"</FORM>";
		assertEquals("Length of string",tempString.length(),formTag.toHTML().length());

		HTMLTagTest.assertStringEquals("Raw String",tempString,formTag.toHTML());
	}
	public void testScanFormWithNoEnding() {
		String testHTML = new String(
		"<TABLE>\n"+
		"<FORM METHOD=\"post\" ACTION=\"do_login.php\" NAME=\"login_form\" onSubmit=\"return CheckData()\">\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>User Name</b></font></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"text\" NAME=\"name\" SIZE=\"20\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>Password</b></font></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"password\" NAME=\"passwd\" SIZE=\"20\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"submit\" NAME=\"submit\" VALUE=\"Login\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<INPUT TYPE=\"hidden\" NAME=\"password\" SIZE=\"20\">\n"+
		"</TABLE>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.google.com/test/index.html");
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];

		parser.addScanner(new HTMLFormScanner(""));
		
		int i = 0;
		try {
			for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
			{
				node[i++] = e.nextHTMLNode();
			}
			assertTrue("Should have thrown an HTMLParserException",false);

		}
		catch (HTMLParserException e) {
		}
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLFormScannerTest.class);
	}
}
