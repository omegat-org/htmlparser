// HTMLParser Library v1_4_20031207 - A java-based parser for HTML
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

import org.htmlparser.AbstractNode;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.StringNode;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.RemarkNode;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class FormTagTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.FormTagTest", "FormTagTest");
    }

    public static final String FORM_HTML =
    "<FORM METHOD=\""+FormTag.POST+"\" ACTION=\"do_login.php\" NAME=\"login_form\" onSubmit=\"return CheckData()\">\n"+
        "<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
        "<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>User Name</b></font></TD></TR>\n"+
        "<TR><TD ALIGN=\"center\"><INPUT TYPE=\"text\" NAME=\"name\" SIZE=\"20\"></TD></TR>\n"+
        "<TR><TD ALIGN=\"center\"><FONT face=\"Arial, verdana\" size=2><b>Password</b></font></TD></TR>\n"+
        "<TR><TD ALIGN=\"center\"><INPUT TYPE=\"password\" NAME=\"passwd\" SIZE=\"20\"></TD></TR>\n"+
        "<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
        "<TR><TD ALIGN=\"center\"><INPUT TYPE=\"submit\" NAME=\"submit\" VALUE=\"Login\"></TD></TR>\n"+
        "<TR><TD ALIGN=\"center\">&nbsp;</TD></TR>\n"+
        "<TEXTAREA name=\"Description\" rows=\"15\" cols=\"55\" wrap=\"virtual\" class=\"composef\" tabindex=\"5\">Contents of TextArea</TEXTAREA>\n"+
//      "<TEXTAREA name=\"AnotherDescription\" rows=\"15\" cols=\"55\" wrap=\"virtual\" class=\"composef\" tabindex=\"5\">\n"+
        "<INPUT TYPE=\"hidden\" NAME=\"password\" SIZE=\"20\">\n"+
        "<INPUT TYPE=\"submit\">\n"+
        "</FORM>";

    public FormTagTest(String name) {
        super(name);
    }

    public void assertTypeNameSize(String description,String type,String name,String size,InputTag inputTag)
    {
        assertEquals(description+" type",type,inputTag.getAttribute("TYPE"));
        assertEquals(description+" name",name,inputTag.getAttribute("NAME"));
        assertEquals(description+" size",size,inputTag.getAttribute("SIZE"));
    }

    public void assertTypeNameValue(String description,String type,String name,String value,InputTag inputTag)
    {
        assertEquals(description+" type",type,inputTag.getAttribute("TYPE"));
        assertEquals(description+" name",name,inputTag.getAttribute("NAME"));
        assertEquals(description+" value",value,inputTag.getAttribute("VALUE"));
    }

    public void testScan() throws ParserException
    {
        createParser(FORM_HTML,"http://www.google.com/test/index.html");
        parseAndAssertNodeCount(1);
        assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];
        assertStringEquals("Method",FormTag.POST,formTag.getFormMethod());
        assertStringEquals("Location","http://www.google.com/test/do_login.php",formTag.getFormLocation());
        assertStringEquals("Name","login_form",formTag.getFormName());
        InputTag nameTag = formTag.getInputTag("name");
        InputTag passwdTag = formTag.getInputTag("passwd");
        InputTag submitTag = formTag.getInputTag("submit");
        InputTag dummyTag = formTag.getInputTag("dummy");
        assertNotNull("Input Name Tag should not be null",nameTag);
        assertNotNull("Input Password Tag should not be null",passwdTag);
        assertNotNull("Input Submit Tag should not be null",submitTag);
        assertNull("Input dummy tag should be null",dummyTag);

        assertTypeNameSize("Input Name Tag","text","name","20",nameTag);
        assertTypeNameSize("Input Password Tag","password","passwd","20",passwdTag);
        assertTypeNameValue("Input Submit Tag","submit","submit","Login",submitTag);

        TextareaTag textAreaTag = formTag.getTextAreaTag("Description");
        assertNotNull("Text Area Tag should have been found",textAreaTag);
        assertEquals("Text Area Tag Contents","Contents of TextArea",textAreaTag.getValue());
        assertNull("Should have been null",formTag.getTextAreaTag("junk"));

        assertStringEquals("toHTML",FORM_HTML,formTag.toHtml());
    }

    public void testScanFormWithNoEnding() throws Exception
    {
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

        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new FormTag (),
                    new InputTag (),
                    new TextareaTag (),
                    new SelectTag (),
                    new OptionTag (),
                }));

        parseAndAssertNodeCount(4);
    }

    /**
     * Bug reported by Pavan Podila - forms with links are not being parsed
     * Sample html is from google
     */
    public void testScanFormWithLinks() throws ParserException
    {
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

        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new FormTag (),
                    new InputTag (),
                    new TextareaTag (),
                    new SelectTag (),
                    new OptionTag (),
                    new LinkTag (),
                    new TableTag (),
                }));
        parseAndAssertNodeCount(1);
        assertTrue("Should be a HTMLFormTag",node[0] instanceof FormTag);
        NodeList linkTags = new NodeList ();
        NodeClassFilter filter = new NodeClassFilter (LinkTag.class);
        for (NodeIterator e = ((FormTag)node[0]).children (); e.hasMoreNodes ();)
            e.nextNode ().collectInto (linkTags, filter);
        assertEquals("Link Tag Count",3,linkTags.size ());
        LinkTag[] linkTag = new LinkTag[3];
        linkTags.copyToNodeArray (linkTag);
        assertEquals("First Link Tag Text","Advanced&nbsp;Search",linkTag[0].getLinkText());
        assertEquals("Second Link Tag Text","Preferences",linkTag[1].getLinkText());
        assertEquals("Third Link Tag Text","Language Tools",linkTag[2].getLinkText());
    }
    /**
     * Bug 652674 - forms with comments are not being parsed
     */
    public void testScanFormWithComments() throws ParserException {
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

        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new FormTag (),
                    new InputTag (),
                    new TextareaTag (),
                    new SelectTag (),
                    new OptionTag (),
                    new TableTag (),
                }));
        parseAndAssertNodeCount(1);
        assertTrue("Should be a HTMLFormTag",node[0] instanceof FormTag);
        NodeList remarkNodes = new NodeList ();
        NodeClassFilter filter = new NodeClassFilter (RemarkNode.class);
        for (NodeIterator e = ((FormTag)node[0]).children (); e.hasMoreNodes ();)
            e.nextNode ().collectInto (remarkNodes, filter);
        assertEquals("Remark Node Count",1,remarkNodes.size ());
        assertEquals("First Remark Node"," Hello World ",remarkNodes.elementAt (0).toPlainTextString());
    }
    /**
     * Bug 652674 - forms with comments are not being parsed
     */
    public void testScanFormWithComments2() throws ParserException {
        createParser(
        "<FORM id=\"id\" name=\"name\" action=\"http://some.site/aPage.asp?id=97\" method=\"post\">\n"+
        "   <!--\n"+
        "   Just a Comment\n"+
        "   -->\n"+
        "</FORM>");
        parseAndAssertNodeCount(1);
        assertTrue("Should be a HTMLFormTag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];
        RemarkNode [] remarkNode = new RemarkNode[10];
        int i = 0;
        for (NodeIterator e=formTag.children();e.hasMoreNodes();) {
            Node formNode = (Node)e.nextNode();
            if (formNode instanceof RemarkNode) {
                remarkNode[i++] = (RemarkNode)formNode;
            }
        }
        assertEquals("Remark Node Count",1,i);
    }

    /**
     * Bug 656870 - a form tag with a previously open link causes infinite loop
     * on encounter
     */
    public void testScanFormWithPreviousOpenLink() throws ParserException {
        createParser(
            "<A HREF=\"http://www.oygevalt.org/\">Home</A>\n"+
            "<P>\n"+
            "And now, the good stuff:\n"+
            "<P>\n"+
            "<A HREF=\"http://www.yahoo.com\">Yahoo!\n"+
            "<FORM ACTION=\".\" METHOD=\"GET\">\n"+
                "<INPUT TYPE=\"TEXT\">\n"+
                "<BR>\n"+
                "<A HREF=\"http://www.helpme.com\">Help</A> " +
                "<INPUT TYPE=\"checkbox\">\n"+
                "<P>\n"+
                "<INPUT TYPE=\"SUBMIT\">\n"+
            "</FORM>"
        );
        parseAndAssertNodeCount(8);
        assertTrue("Seventh Node is a link",node[6] instanceof LinkTag);
        LinkTag linkTag = (LinkTag)node[6];
        assertEquals("Link Text","Yahoo!\n",linkTag.getLinkText());
        assertEquals("Link URL","http://www.yahoo.com",linkTag.getLink());
        assertType("Eigth Node",FormTag.class,node[7]);
    }

    /**
     * Bug 713907 reported by Dhaval Udani, erroneous
     * parsing of form tag (even when form scanner is not
     * registered)
     */
    public void testFormScanningShouldNotHappen() throws Exception {
        String testHTML =
            "<HTML><HEAD><TITLE>Test Form Tag</TITLE></HEAD>" +
            "<BODY><FORM name=\"form0\"><INPUT type=\"text\" name=\"text0\"></FORM>" +
            "</BODY></HTML>";
        createParser(
            testHTML
        );
        ((PrototypicalNodeFactory)parser.getNodeFactory ()).unregisterTag (new FormTag ());
        Node [] nodes =
            parser.extractAllNodesThatAre(
                FormTag.class
            );
        assertEquals(
            "shouldnt have found form tag",
            0,
            nodes.length
        );
    }

    /**
     * See bug #745566 StackOverflowError on select with too many unclosed options.
     * Under Windows this throws a stack overflow exception.
     */
    public void testUnclosedOptions () throws ParserException
    {
        String url = "http://htmlparser.sourceforge.net/test/overflowpage.html";
        int i;
        Node[] nodes;

        parser = new Parser(url);
        PrototypicalNodeFactory factory = new PrototypicalNodeFactory ();
        // we want to expose the repetitive tags
        factory.unregisterTag (new Html ());
        factory.unregisterTag (new HeadTag ());
        factory.unregisterTag (new BodyTag ());
        parser.setNodeFactory (factory);
        i = 0;
        nodes = new AbstractNode[50];
        for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
            nodes[i++] = e.nextNode();
        assertEquals ("Expected nodes", 39, i);
    }
    
    public void testSetFormLocation() throws ParserException
    {
        createParser(FORM_HTML);
        parseAndAssertNodeCount(1);
        assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];

        formTag.setFormLocation("http://www.yahoo.com/yahoo/do_not_login.jsp");
        String expected = 
            FORM_HTML.substring (0, FORM_HTML.indexOf ("\"do_login.php\""))
            + "\"http://www.yahoo.com/yahoo/do_not_login.jsp\""
            + FORM_HTML.substring (FORM_HTML.indexOf ("\"do_login.php\"") + 14);
        assertStringEquals("Raw String",expected,formTag.toHtml());
    }

    public void testToPlainTextString() throws ParserException
    {
        createParser(FORM_HTML);
        parseAndAssertNodeCount(1);
        assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];
        assertStringEquals("Form Tag string representation","\n&nbsp;\nUser Name\n\nPassword\n\n&nbsp;\n\n&nbsp;\nContents of TextArea\n\n\n", formTag.toPlainTextString());
    }

    public void testSearchFor() throws ParserException
    {
        createParser(FORM_HTML);

        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new FormTag (),
                    new InputTag (),
                    new TextareaTag (),
                    new SelectTag (),
                    new OptionTag (),
                }));
        parseAndAssertNodeCount(1);
        assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];
        NodeList nodeList = formTag.searchFor("USER NAME");
        assertEquals("Should have found nodes",1,nodeList.size());

        Node[] nodes = nodeList.toNodeArray();

        assertEquals("Number of nodes found",1,nodes.length);
        assertType("search result node",StringNode.class,nodes[0]);
        StringNode stringNode = (StringNode)nodes[0];
        assertEquals("Expected contents of string node","User Name",stringNode.getText());
    }

    public void testSearchForCaseSensitive() throws ParserException
    {
        createParser(FORM_HTML);
        parseAndAssertNodeCount(1);
        assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];
        NodeList nodeList = formTag.searchFor("USER NAME",true);
        assertEquals("Should have not found nodes",0,nodeList.size());

        nodeList = formTag.searchFor("User Name",true);
        assertNotNull("Should have not found nodes",nodeList);
    }


    public void testSearchByName() throws ParserException
    {
        createParser(FORM_HTML);

        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new FormTag (),
                    new InputTag (),
                    new TextareaTag (),
                    new SelectTag (),
                    new OptionTag (),
                }));
        parseAndAssertNodeCount(1);
        assertTrue("Node 0 should be Form Tag",node[0] instanceof FormTag);
        FormTag formTag = (FormTag)node[0];

        Tag tag= formTag.searchByName("passwd");
        assertNotNull("Should have found the password node",tag);
        assertType("tag found",InputTag.class,tag);
    }

    /**
     * Bug 713907 reported by Dhaval Udani, erroneous
     * attributes being reported.
     */
    public void testFormRendering() throws Exception
    {
        String testHTML =
            "<HTML><HEAD><TITLE>Test Form Tag</TITLE></HEAD>" +
            "<BODY><FORM name=\"form0\"><INPUT type=\"text\" name=\"text0\"></FORM>" +
            "</BODY></HTML>";
        createParser(
            testHTML
        );
        FormTag formTag =
            (FormTag)(parser.extractAllNodesThatAre(
                FormTag.class
            )[0]);
        assertNotNull("Should have found a form tag",formTag);
        assertStringEquals("name","form0",formTag.getFormName());
        assertNull("action",formTag.getAttribute("ACTION"));
        assertXmlEquals(
            "html",
            "<FORM NAME=\"form0\">" +
                "<INPUT TYPE=\"text\" NAME=\"text0\">" +
            "</FORM>",
            formTag.toHtml()
        );
    }
}
