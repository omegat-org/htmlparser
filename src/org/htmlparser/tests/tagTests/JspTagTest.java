// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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

import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.scanners.JspScanner;
import org.htmlparser.tags.JspTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class JspTagTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.JspTagTest", "JspTagTest");
    }

    private static final boolean JSP_TESTS_ENABLED = false;

    public JspTagTest(String name) {
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
    public void testJspTag() throws ParserException
    {
        if (JSP_TESTS_ENABLED)
        {
            String contents = "jsp:useBean id=\"transfer\" scope=\"session\" class=\"com.bank.PageBean\"/";
            String jsp = "<" + contents + ">";
            String contents2 = "%\n"+
                "    org.apache.struts.util.BeanUtils.populate(transfer, request);\n"+
                "    if(request.getParameter(\"marker\") == null)\n"+
                "        // initialize a pseudo-property\n"+
                "        transfer.set(\"days\", java.util.Arrays.asList(\n"+
                "            new String[] {\"1\", \"2\", \"3\", \"4\", \"31\"}));\n"+
                "    else \n"+
                "        if(transfer.validate(request))\n"+
                "            %";
            createParser(
                "<%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %>\n"+
                jsp + "\n" +
                "<" + contents2 + ">\n<jsp:forward page=\"transferConfirm.jsp\"/><%\n"+
                "%>");
            Parser.setLineSeparator("\r\n");
            parser.setNodeFactory (new PrototypicalNodeFactory (new JspTag ()));
            parseAndAssertNodeCount(8);
            // The first node should be an JspTag
            assertTrue("Node 1 should be an JspTag",node[0] instanceof JspTag);
            JspTag tag = (JspTag)node[0];
            assertStringEquals("Contents of the tag","%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %",tag.getText());

            // The second node should be a normal tag
            assertTrue("Node 3 should be a normal Tag",node[2] instanceof Tag);
            Tag htag = (Tag)node[2];
            assertStringEquals("Contents of the tag",contents,htag.getText());
            assertStringEquals("html",jsp,htag.toHtml());
            // The third node should be an JspTag
            assertTrue("Node 5 should be an JspTag",node[4] instanceof JspTag);
            JspTag tag2 = (JspTag)node[4];
            assertStringEquals("Contents of the tag",contents2,tag2.getText());
        }
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
    public void testToHTML() throws ParserException
    {
        if (JSP_TESTS_ENABLED)
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
            Parser.setLineSeparator("\r\n");
            parser.setNodeFactory (new PrototypicalNodeFactory (new JspTag ()));
            parseAndAssertNodeCount(8);
            // The first node should be an JspTag
            assertTrue("Node 1 should be an JspTag",node[0] instanceof JspTag);
            JspTag tag = (JspTag)node[0];
            assertEquals("Raw String of the first JSP tag","<%@ taglib uri=\"/WEB-INF/struts.tld\" prefix=\"struts\" %>",tag.toHtml());


            // The third node should be an JspTag
            assertTrue("Node 5 should be an JspTag",node[5] instanceof JspTag);
            JspTag tag2 = (JspTag)node[8];
            String expected = "<%\r\n"+
                "    org.apache.struts.util.BeanUtils.populate(transfer, request);\r\n"+
                "    if(request.getParameter(\"marker\") == null)\r\n"+
                "        // initialize a pseudo-property\r\n"+
                "        transfer.set(\"days\", java.util.Arrays.asList(\r\n"+
                "            new String[] {\"1\", \"2\", \"3\", \"4\", \"31\"}));\r\n"+
                "    else \r\n"+
                "        if(transfer.validate(request))\r\n"+
                "            %>";
            assertEquals("Raw String of the second JSP tag",expected,tag2.toHtml());
            assertTrue("Node 4 should be an HTMLJspTag",node[4] instanceof JspTag);
            JspTag tag4 = (JspTag)node[4];
            expected = "<%\r\n"+
                "%>";
            assertEquals("Raw String of the fourth JSP tag",expected,tag4.toHtml());
        }
    }

    public void testSpecialCharacters() throws ParserException {
        StringBuffer sb1 = new StringBuffer();
        sb1.append("<% for (i=0;i<j;i++);%>");
        createParser(sb1.toString());
        parser.setNodeFactory (new PrototypicalNodeFactory (new JspTag ()));
        parseAndAssertNodeCount(1);
        //assertTrue("Node should be a jsp tag",node[1] instanceof HTMLJspTag);
        JspTag jspTag = (JspTag)node[0];
        assertEquals("jsp toHTML()","<% for (i=0;i<j;i++);%>",jspTag.toHtml());
    }


    /**
     * See bug #772700 Jsp Tags are not parsed correctly when in quoted attributes.
     */
   public void testJspTagsInUnQuotedAttribes() throws ParserException {
      // this test should pass when none of the attibutes are quoted
      testJspTagsInAttributes("<img alt=<%=altText1%> src=<%=imgUrl1%> border=<%=borderToggle%>>");
   }

    /**
     * See bug #772700 Jsp Tags are not parsed correctly when in quoted attributes.
     */
//   public void testJspTagsInQuotedAttribes() throws ParserException
//   {
//      // this test seems to mess up....
//      testJspTagsInAttributes("<img alt=\"<%=altText1%>\" src=\"<%=imgUrl1%>\" border=\"<%=borderToggle%>\">");
//   }

    private void testJspTagsInAttributes(String html) throws ParserException
    {
        if (JSP_TESTS_ENABLED)
        {
            createParser(html);
            parser.setNodeFactory (new PrototypicalNodeFactory (new JspTag ()));
            parseAndAssertNodeCount(7);

            assertTrue("Should be a Jsp tag but was "+node[1].getClass().getName(),node[1] instanceof JspTag);
            assertTrue("Should be a Jsp tag but was "+node[3].getClass().getName(),node[3] instanceof JspTag);
            assertTrue("Should be a Jsp tag but was "+node[5].getClass().getName(),node[5] instanceof JspTag);

            assertTrue("Text Should be '<%=altText1%>'but was '" + node[1].toHtml() + "'" ,node[1].toHtml().equals("<%=altText1%>"));
            assertTrue("Text Should be '<%=imgUrl1%>' but was '" + node[3].toHtml() + "'" ,node[3].toHtml().equals("<%=imgUrl1%>"));
            assertTrue("Text Should be '<%=borderToggle%>' but was '" + node[5].toHtml() + "'" ,node[5].toHtml().equals("<%=borderToggle%>"));
        }
    }
}
