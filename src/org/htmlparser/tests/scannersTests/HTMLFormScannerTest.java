// HTMLParser Library v1_2_20021215 - A java-based parser for HTML
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
import org.htmlparser.HTMLRemarkNode;
import org.htmlparser.scanners.HTMLFormScanner;	
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLFormTag;
import org.htmlparser.tags.HTMLInputTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.tests.tagTests.HTMLTagTest;

import junit.extensions.ExceptionTestCase;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLFormScannerTest extends HTMLParserTestCase {
	public static final String FORM_HTML =
	"<FORM METHOD=\""+HTMLFormTag.POST+"\" ACTION=\"do_login.php\" NAME=\"login_form\" onSubmit=\"return CheckData()\">\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>User Name</b></font></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"text\" NAME=\"name\" SIZE=\"20\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>Password</b></font></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"password\" NAME=\"passwd\" SIZE=\"20\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<TR><TD ALIGN=\"center\"><INPUT TYPE=\"submit\" NAME=\"submit\" VALUE=\"Login\"></TD></TR>\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
		"<INPUT TYPE=\"hidden\" NAME=\"password\" SIZE=\"20\">\n"+
		"<INPUT TYPE=\"submit\">\n"+
		"</FORM>";
	
	public static final String EXPECTED_FORM_HTML_FORMLINE="<FORM METHOD=\""+HTMLFormTag.POST+"\" ACTION=\"http://www.google.com/test/do_login.php\" NAME=\"login_form\" ONSUBMIT=\"return CheckData()\">\r\n";
	public static final String EXPECTED_FORM_HTML_REST_OF_FORM=	
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>User Name</b></font></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><INPUT NAME=\"name\" SIZE=\"20\" TYPE=\"text\"></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>Password</b></font></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><INPUT NAME=\"passwd\" SIZE=\"20\" TYPE=\"password\"></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\"><INPUT VALUE=\"Login\" NAME=\"submit\" TYPE=\"submit\"></TD></TR>\r\n"+
		"<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\r\n"+
		"<INPUT NAME=\"password\" SIZE=\"20\" TYPE=\"hidden\">\r\n"+
		"<INPUT TYPE=\"submit\">\r\n"+
		"</FORM>";
	public static final String EXPECTED_FORM_HTML = EXPECTED_FORM_HTML_FORMLINE+EXPECTED_FORM_HTML_REST_OF_FORM;
			
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
	
	public void assertTypeNameSize(String description,String type,String name,String size,HTMLInputTag inputTag) {
		assertEquals(description+" type",type,inputTag.getParameter("TYPE"));
		assertEquals(description+" name",name,inputTag.getParameter("NAME"));
		assertEquals(description+" size",size,inputTag.getParameter("SIZE"));
	}
	public void assertTypeNameValue(String description,String type,String name,String value,HTMLInputTag inputTag) {
		assertEquals(description+" type",type,inputTag.getParameter("TYPE"));
		assertEquals(description+" name",name,inputTag.getParameter("NAME"));
		assertEquals(description+" value",value,inputTag.getParameter("VALUE"));
	}	
	public void testScan() throws HTMLParserException {
		createParser(FORM_HTML,"http://www.google.com/test/index.html");
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node 0 should be Form Tag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		assertEquals("Method",HTMLFormTag.POST,formTag.getFormMethod());
		assertEquals("Location","http://www.google.com/test/do_login.php",formTag.getFormLocation());
		assertEquals("Name","login_form",formTag.getFormName());		
		HTMLInputTag nameTag = formTag.getInputTag("name");
		HTMLInputTag passwdTag = formTag.getInputTag("passwd");
		HTMLInputTag submitTag = formTag.getInputTag("submit");
		HTMLInputTag dummyTag = formTag.getInputTag("dummy");
		assertNotNull("Input Name Tag should not be null",nameTag);
		assertNotNull("Input Password Tag should not be null",passwdTag);
		assertNotNull("Input Submit Tag should not be null",submitTag);
		assertNull("Input dummy tag should be null",dummyTag);
		
		assertTypeNameSize("Input Name Tag","text","name","20",nameTag);
		assertTypeNameSize("Input Password Tag","password","passwd","20",passwdTag);
		assertTypeNameValue("Input Submit Tag","submit","submit","Login",submitTag);
		
		
		assertEquals("Length of string",EXPECTED_FORM_HTML.length(),formTag.toHTML().length());

		assertStringEquals("Raw String",EXPECTED_FORM_HTML,formTag.toHTML());
	}
	
	public void testScanFormWithNoEnding() {
		createParser(
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
		"</TABLE>","http://www.google.com/test/index.html");
		
		parser.addScanner(new HTMLFormScanner(""));
		
		try {
			parseAndAssertNodeCount(1);
			assertTrue("Should have thrown an HTMLParserException",false);
		}
		catch (HTMLParserException e) {
		}
	}
	/** 
	 * Bug reported by Pavan Podila - forms with links are not being parsed
	 * Sample html is from google
	 */
	public void testScanFormWithLinks() throws HTMLParserException {
		createParser(
		"<form action=\"/search\" name=f><table cellspacing=0 cellpadding=0><tr><td width=75>&nbsp;"+
		"</td><td align=center><input type=hidden name=hl value=en><input type=hidden name=ie "+
		"value=\"UTF-8\"><input type=hidden name=oe value=\"UTF-8\"><input maxLength=256 size=55"+
		" name=q value=\"\"><br><input type=submit value=\"Google Search\" name=btnG><input type="+
		"submit value=\"I'm Feeling Lucky\" name=btnI></td><td valign=top nowrap><font size=-2>"+
		"&nbsp;&#8226;&nbsp;<a href=/advanced_search?hl=en>Advanced&nbsp;Search</a><br>&nbsp;&#8226;"+
		"&nbsp;<a href=/preferences?hl=en>Preferences</a><br>&nbsp;&#8226;&nbsp;<a href=/"+
		"language_tools?hl=en>Language Tools</a></font></td></tr></table></form>"
		);	
		
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Should be a HTMLFormTag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		HTMLLinkTag [] linkTag = new HTMLLinkTag[10];
		int i = 0;
		for (Enumeration e=formTag.getAllNodesVector().elements();e.hasMoreElements();) {
			HTMLNode formNode = (HTMLNode)e.nextElement();
			if (formNode instanceof HTMLLinkTag) {
				linkTag[i++] = (HTMLLinkTag)formNode;		
			}
		}
		assertEquals("Link Tag Count",3,i);
		assertEquals("First Link Tag Text","Advanced&nbsp;Search",linkTag[0].getLinkText());
		assertEquals("Second Link Tag Text","Preferences",linkTag[1].getLinkText());
		assertEquals("Third Link Tag Text","Language Tools",linkTag[2].getLinkText());
	}
	/** 
	 * Bug 652674 - forms with comments are not being parsed
	 */
	public void testScanFormWithComments() throws HTMLParserException {
		createParser(
		"<form action=\"/search\" name=f><table cellspacing=0 cellpadding=0><tr><td width=75>&nbsp;"+
		"</td><td align=center><input type=hidden name=hl value=en><input type=hidden name=ie "+
		"value=\"UTF-8\"><input type=hidden name=oe value=\"UTF-8\"><!-- Hello World -->"+
		"<input maxLength=256 size=55"+
		" name=q value=\"\"><br><input type=submit value=\"Google Search\" name=btnG><input type="+
		"submit value=\"I'm Feeling Lucky\" name=btnI></td><td valign=top nowrap><font size=-2>"+
		"&nbsp;&#8226;&nbsp;<a href=/advanced_search?hl=en>Advanced&nbsp;Search</a><br>&nbsp;&#8226;"+
		"&nbsp;<a href=/preferences?hl=en>Preferences</a><br>&nbsp;&#8226;&nbsp;<a href=/"+
		"language_tools?hl=en>Language Tools</a></font></td></tr></table></form>"
		);	
		
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Should be a HTMLFormTag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		HTMLRemarkNode [] remarkNode = new HTMLRemarkNode[10];
		int i = 0;
		for (Enumeration e=formTag.getAllNodesVector().elements();e.hasMoreElements();) {
			HTMLNode formNode = (HTMLNode)e.nextElement();
			if (formNode instanceof HTMLRemarkNode) {
				remarkNode[i++] = (HTMLRemarkNode)formNode;		
			}
		}
		assertEquals("Remark Node Count",1,i);
		assertEquals("First Remark Node"," Hello World ",remarkNode[0].toPlainTextString());
	}	
	/** 
	 * Bug 652674 - forms with comments are not being parsed
	 */
	public void testScanFormWithComments2() throws HTMLParserException {
		createParser(
		"<FORM id=\"id\" name=\"name\" action=\"http://some.site/aPage.asp?id=97\" method=\"post\">\n"+			
		"	<!--\n"+
		"	Just a Comment\n"+
		"	-->\n"+
		"</FORM>");
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Should be a HTMLFormTag",node[0] instanceof HTMLFormTag);
		HTMLFormTag formTag = (HTMLFormTag)node[0];
		HTMLRemarkNode [] remarkNode = new HTMLRemarkNode[10];
		int i = 0;
		for (Enumeration e=formTag.getAllNodesVector().elements();e.hasMoreElements();) {
			HTMLNode formNode = (HTMLNode)e.nextElement();
			if (formNode instanceof HTMLRemarkNode) {
				remarkNode[i++] = (HTMLRemarkNode)formNode;		
			}
		}
		assertEquals("Remark Node Count",1,i);
	}		
}
