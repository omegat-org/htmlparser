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

import java.io.BufferedReader;
import java.io.StringReader;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLFormTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.HTMLFormScanner;
import org.htmlparser.util.HTMLEnumeration;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLFormTagTest extends HTMLParserTestCase {

	public HTMLFormTagTest(String name) {
		super(name);
	}

	public void testSetFormLocation() throws HTMLParserException{
		createParser(
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

		parser.addScanner(new HTMLFormScanner(""));
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];

		formTag.setFormLocation("http://www.yahoo.com/yahoo/do_not_login.jsp");

		String tempString = "<FORM METHOD=\"post\" ACTION=\"http://www.yahoo.com/yahoo/do_not_login.jsp\" NAME=\"login_form\" ONSUBMIT=\"return CheckData()\">\r\n"+
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
		assertStringEquals("Raw String",tempString,formTag.toHTML());
	}
}
