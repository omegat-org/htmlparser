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
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.*;

import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLJspTagTest extends HTMLParserTestCase
{

	public HTMLJspTagTest(String name) {
		super(name);
	}

	/**
	 * Check if the JSP Tag is being correctly recognized.
	 * Our test html is : <BR>
	 * &lt;%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %&gt;<BR>
	 * &lt;jsp:useBean id="transfer" scope="session" class="com.bank.PageBean"/&gt;<BR>
	 * &lt;%<BR>
	 *   org.apache.struts.util.BeanUtils.populate(transfer, request);<BR>
	 *    if(request.getParameter("marker") == null)<BR>
	 *      // initialize a pseudo-property<BR>
	 *        transfer.set("days", java.util.Arrays.asList(<BR>
	 *            new String[] {"1", "2", "3", "4", "31"}));<BR>
	 *    else <BR>
	 *        if(transfer.validate(request))<BR>
	 *            %&gt;&lt;jsp:forward page="transferConfirm.jsp"/&gt;&lt;%
	 * %&gt;
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testJspTag() throws HTMLParserException
	{
		createParser(
			"<%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %>\n"+
			"<jsp:useBean id=\"transfer\" scope=\"session\" class=\"com.bank.PageBean\"/>\n"+
			"<%\n"+
			"    org.apache.struts.util.BeanUtils.populate(transfer, request);\n"+
			"    if(request.getParameter(\"marker\") == null)\n"+
			"        // initialize a pseudo-property\n"+
			"        transfer.set(\"days\", java.util.Arrays.asList(\n"+
			"            new String[] {\"1\", \"2\", \"3\", \"4\", \"31\"}));\n"+
			"    else \n"+
			"        if(transfer.validate(request))\n"+
			"            %><jsp:forward page=\"transferConfirm.jsp\"/><%\n"+ 
			"%>\n");
		parser.setLineSeparator("\r\n");
		// Register the Jsp Scanner
		parser.addScanner(new HTMLJspScanner("-j"));	
		parseAndAssertNodeCount(5);
		// The first node should be an HTMLJspTag
		assertTrue("Node 1 should be an HTMLJspTag",node[0] instanceof HTMLJspTag);
		HTMLJspTag tag = (HTMLJspTag)node[0];
		assertEquals("Contents of the tag","@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" ",tag.getText());
	
		// The second node should be a normal tag
		assertTrue("Node 2 should be an HTMLTag",node[1] instanceof HTMLTag);
		HTMLTag htag = (HTMLTag)node[1];
		assertEquals("Contents of the tag","jsp:useBean id=\"transfer\" scope=\"session\" class=\"com.bank.PageBean\"/",htag.getText());
	
		// The third node should be an HTMLJspTag
		assertTrue("Node 3 should be an HTMLJspTag",node[2] instanceof HTMLJspTag);
		HTMLJspTag tag2 = (HTMLJspTag)node[2];
		String expected = "\r\n"+
			"    org.apache.struts.util.BeanUtils.populate(transfer, request);\r\n"+
			"    if(request.getParameter(\"marker\") == null)\r\n"+
			"        // initialize a pseudo-property\r\n"+
			"        transfer.set(\"days\", java.util.Arrays.asList(\r\n"+
			"            new String[] {\"1\", \"2\", \"3\", \"4\", \"31\"}));\r\n"+
			"    else \r\n"+
			"        if(transfer.validate(request))\r\n"+
			"            ";
		assertEquals("Contents of the tag",expected,tag2.getText());
		
	}
	
	/**
	 * Check if the JSP Tag is being correctly recognized.
	 * Our test html is : <BR>
	 * &lt;%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %&gt;<BR>
	 * &lt;jsp:useBean id="transfer" scope="session" class="com.bank.PageBean"/&gt;<BR>
	 * &lt;%<BR>
	 *   org.apache.struts.util.BeanUtils.populate(transfer, request);<BR>
	 *    if(request.getParameter("marker") == null)<BR>
	 *      // initialize a pseudo-property<BR>
	 *        transfer.set("days", java.util.Arrays.asList(<BR>
	 *            new String[] {"1", "2", "3", "4", "31"}));<BR>
	 *    else <BR>
	 *        if(transfer.validate(request))<BR>
	 *            %&gt;&lt;jsp:forward page="transferConfirm.jsp"/&gt;&lt;%
	 * %&gt;
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testToHTML() throws HTMLParserException
	{
		createParser(
			"<%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %>\n"+
			"<jsp:useBean id=\"transfer\" scope=\"session\" class=\"com.bank.PageBean\"/>\n"+
			"<%\n"+
			"    org.apache.struts.util.BeanUtils.populate(transfer, request);\n"+
			"    if(request.getParameter(\"marker\") == null)\n"+
			"        // initialize a pseudo-property\n"+
			"        transfer.set(\"days\", java.util.Arrays.asList(\n"+
			"            new String[] {\"1\", \"2\", \"3\", \"4\", \"31\"}));\n"+
			"    else \n"+
			"        if(transfer.validate(request))\n"+
			"            %><jsp:forward page=\"transferConfirm.jsp\"/><%\n"+ 
			"%>\n");
		parser.setLineSeparator("\r\n");
		// Register the Jsp Scanner
		parser.addScanner(new HTMLJspScanner("-j"));	
		parseAndAssertNodeCount(5);
		// The first node should be an HTMLJspTag
		assertTrue("Node 1 should be an HTMLJspTag",node[0] instanceof HTMLJspTag);
		HTMLJspTag tag = (HTMLJspTag)node[0];
		assertEquals("Raw String of the first JSP tag","<%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %>",tag.toHTML());
	
	
		// The third node should be an HTMLJspTag
		assertTrue("Node 2 should be an HTMLJspTag",node[2] instanceof HTMLJspTag);
		HTMLJspTag tag2 = (HTMLJspTag)node[2];
		String expected = "<%\r\n"+
			"    org.apache.struts.util.BeanUtils.populate(transfer, request);\r\n"+
			"    if(request.getParameter(\"marker\") == null)\r\n"+
			"        // initialize a pseudo-property\r\n"+
			"        transfer.set(\"days\", java.util.Arrays.asList(\r\n"+
			"            new String[] {\"1\", \"2\", \"3\", \"4\", \"31\"}));\r\n"+
			"    else \r\n"+
			"        if(transfer.validate(request))\r\n"+
			"            %>";
		assertEquals("Raw String of the second JSP tag",expected,tag2.toHTML());
		assertTrue("Node 4 should be an HTMLJspTag",node[4] instanceof HTMLJspTag);
		HTMLJspTag tag4 = (HTMLJspTag)node[4];
		expected = "<%\r\n"+ 
			"%>";
		assertEquals("Raw String of the fourth JSP tag",expected,tag4.toHTML());
		
	}

}
