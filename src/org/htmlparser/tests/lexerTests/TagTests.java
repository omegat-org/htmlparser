// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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
// Industrial Logic, Inc.
// 2583 Cedar Street, Berkeley,
// CA 94708, USA
// Website : http://www.industriallogic.com

// ---- IMPORTANT: This class has failing tests ----
// Original Location: org.htmlparser.tests.parserHelperTests;
// Pls rememeber to add test back to org.htmlparser.tests.parserHelperTests.AllTests.suite()
// and delete these comments when you're done.
// ----   NEEDS FIXING                          ----

package org.htmlparser.tests.lexerTests;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class TagTests extends ParserTestCase {
    private static final String TEST_HTML = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">" +
        "<!-- Server: sf-web2 -->" +
        "<html lang=\"en\">" +
        "  <head><link rel=\"stylesheet\" type=\"text/css\" href=\"http://sourceforge.net/cssdef.php\">" +
        "   <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" +
        "    <TITLE>SourceForge.net: Modify: 711073 - HTMLTagParser not threadsafe as a static variable in Tag</TITLE>" +
        "   <SCRIPT language=\"JavaScript\" type=\"text/javascript\">" +
        "   <!--" +
        "   function help_window(helpurl) {" +
        "       HelpWin = window.open( 'http://sourceforge.net' + helpurl,'HelpWindow','scrollbars=yes,resizable=yes,toolbar=no,height=400,width=400');" +
        "   }" +
        "   // -->" +
        "   </SCRIPT>" +
        "       <link rel=\"SHORTCUT ICON\" href=\"/images/favicon.ico\">" +
        "<!-- This is temp javascript for the jump button. If we could actually have a jump script on the server side that would be ideal -->" +
        "<script language=\"JavaScript\" type=\"text/javascript\">" +
        "<!--" +
        "   function jump(targ,selObj,restore){ //v3.0" +
        "   if (selObj.options[selObj.selectedIndex].value) " +
        "       eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");" +
        "   if (restore) selObj.selectedIndex=0;" +
        "   }" +
        "   //-->" +
        "</script>" +
        "<a href=\"http://normallink.com/sometext.html\">" +
        "<style type=\"text/css\">" +
        "<!--" +
        "A:link { text-decoration:none }" +
        "A:visited { text-decoration:none }" +
        "A:active { text-decoration:none }" +
        "A:hover { text-decoration:underline; color:#0066FF; }" +
        "-->" +
        "</style>" +
        "</head>" +
        "<body bgcolor=\"#FFFFFF\" text=\"#000000\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" link=\"#003399\" vlink=\"#003399\" alink=\"#003399\">";
    private Map results;
    private int testProgress;

    public TagTests (String name) {
        super(name);
    }

    public void testTagWithQuotes() throws Exception {
        String testHtml =
        "<img src=\"http://g-images.amazon.com/images/G/01/merchants/logos/marshall-fields-logo-20.gif\" width=87 height=20 border=0 alt=\"Marshall Field's\">";

        createParser(testHtml);
        parseAndAssertNodeCount(1);
        assertType("should be Tag",Tag.class,node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals("alt","Marshall Field's",tag.getAttribute("ALT"));
        assertStringEquals(
            "html",
            testHtml,
            tag.toHtml()
        );
    }

    public void testEmptyTag() throws Exception {
        createParser("<custom/>");
        parseAndAssertNodeCount(1);
        assertType("should be Tag",Tag.class,node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals("tag name","CUSTOM",tag.getTagName());
        assertTrue("empty tag",tag.isEmptyXmlTag());
        assertStringEquals(
            "html",
            "<CUSTOM/>",
            tag.toHtml()
        );
    }

    public void testTagWithCloseTagSymbolInAttribute() throws ParserException {
        createParser("<tag att=\"a>b\">");
        parseAndAssertNodeCount(1);
        assertType("should be Tag",Tag.class,node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals("attribute","a>b",tag.getAttribute("att"));
    }

    public void testTagWithOpenTagSymbolInAttribute() throws ParserException {
        createParser("<tag att=\"a<b\">");
        parseAndAssertNodeCount(1);
        assertType("should be Tag",Tag.class,node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals("attribute","a<b",tag.getAttribute("att"));
    }

    public void testTagWithSingleQuote() throws ParserException {
        String html = "<tag att=\'a<b\'>";
        createParser(html);
        parseAndAssertNodeCount(1);
        assertType("should be Tag",Tag.class,node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals("html",html,tag.toHtml());
        assertStringEquals("attribute","a<b",tag.getAttribute("att"));
    }

    /**
     * The following multi line test cases are from
     * bug #725749 Parser does not handle < and > in multi-line attributes
     * submitted by Joe Robins (zorblak)
     */
    public void testMultiLine1 () throws ParserException
    {
        String html = "<meta name=\"foo\" content=\"foo<bar>\">";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html, tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","foo<bar>", attribute2);
    }

    public void testMultiLine2 () throws ParserException
    {
        String html = "<meta name=\"foo\" content=\"foo<bar\">";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html, tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","foo<bar", attribute2);
    }

    public void testMultiLine3 () throws ParserException
    {
        String html = "<meta name=\"foo\" content=\"foobar>\">";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html, tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","foobar>", attribute2);
    }

    public void testMultiLine4 () throws ParserException
    {
        String html = "<meta name=\"foo\" content=\"foo\nbar>\">";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html, tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","foo\nbar>", attribute2);
    }

    /**
     * Test multiline tag like attribute.
     * See feature request #725749 Handle < and > in multi-line attributes.
     */
    public void testMultiLine5 () throws ParserException
    {
        // <meta name="foo" content="<foo>
        // bar">
        String html = "<meta name=\"foo\" content=\"<foo>\nbar\">";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html, tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","<foo>\nbar", attribute2);
    }

    /**
     * Test multiline broken tag like attribute.
     * See feature request #725749 Handle < and > in multi-line attributes.
     */
    public void testMultiLine6 () throws ParserException
    {
        // <meta name="foo" content="foo>
        // bar">
        String html = "<meta name=\"foo\" content=\"foo>\nbar\">";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html, tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","foo>\nbar", attribute2);
    }

    /**
     * Test multiline split tag like attribute.
     * See feature request #725749 Handle < and > in multi-line attributes.
     */
    public void testMultiLine7 () throws ParserException
    {
        // <meta name="foo" content="<foo
        // bar">
        String html = "<meta name=\"foo\" content=\"<foo\nbar\"";
        createParser(html);
        parseAndAssertNodeCount (1);
        assertType ("should be MetaTag", MetaTag.class, node[0]);
        Tag tag = (Tag)node[0];
        assertStringEquals ("html",html + ">", tag.toHtml ());
        String attribute1 = tag.getAttribute ("NAME");
        assertStringEquals ("attribute 1","foo", attribute1);
        String attribute2 = tag.getAttribute ("CONTENT");
        assertStringEquals ("attribute 2","<foo\nbar", attribute2);
    }

    /**
     * End of multi line test cases.
     */

    /**
     * Test multiple threads running against the parser.
     * See feature request #736144 Handle multi-threaded operation.
     */
    public void testThreadSafety() throws Exception
    {
        createParser("<html></html>");
        String testHtml1 = "<a HREF=\"/cgi-bin/view_search?query_text=postdate>20020701&txt_clr=White&bg_clr=Red&url=http://localhost/Testing/Report1.html\">20020702 Report 1</A>" +
                            TEST_HTML;

        String testHtml2 = "<a href=\"http://normallink.com/sometext.html\">" +
                            TEST_HTML;
        ParsingThread parsingThread [] =
            new ParsingThread[100];
        results = new HashMap();
        testProgress = 0;
        for (int i=0;i<parsingThread.length;i++) {
            if (i<parsingThread.length/2)
                parsingThread[i] =
                    new ParsingThread(i,testHtml1,parsingThread.length);
                else
                    parsingThread[i] =
                        new ParsingThread(i,testHtml2,parsingThread.length);

            Thread thread = new Thread(parsingThread[i]);
            thread.start();
        }

        int completionValue = computeCompletionValue(parsingThread.length);

        do {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
            }
        }
        while (testProgress!=completionValue);
        for (int i=0;i<parsingThread.length;i++) {
            if (!parsingThread[i].passed()) {
                assertNotNull("Thread "+i+" link 1",parsingThread[i].getLink1());
                assertNotNull("Thread "+i+" link 2",parsingThread[i].getLink2());
                if (i<parsingThread.length/2) {
                    assertStringEquals(
                        "Thread "+i+", link 1:",
                        "/cgi-bin/view_search?query_text=postdate>20020701&txt_clr=White&bg_clr=Red&url=http://localhost/Testing/Report1.html",
                        parsingThread[i].getLink1().getLink()
                    );
                    assertStringEquals(
                        "Thread "+i+", link 2:",
                        "http://normallink.com/sometext.html",
                        parsingThread[i].getLink2().getLink()
                    );
                } else {
                    assertStringEquals(
                        "Thread "+i+", link 1:",
                        "http://normallink.com/sometext.html",
                        parsingThread[i].getLink1().getLink()
                    );
                    assertNotNull("Thread "+i+" link 2",parsingThread[i].getLink2());
                    assertStringEquals(
                        "Thread "+i+", link 2:",
                        "/cgi-bin/view_search?query_text=postdate>20020701&txt_clr=White&bg_clr=Red&url=http://localhost/Testing/Report1.html",
                        parsingThread[i].getLink2().getLink()
                    );
                }
            }
        }
    }

    private int computeCompletionValue(int numThreads) {
        return numThreads * (numThreads - 1) / 2;
    }

    class ParsingThread implements Runnable {
        Parser parser;
        int id;
        LinkTag link1, link2;
        boolean result;
        int max;

        ParsingThread(int id, String testHtml, int max) {
            this.id = id;
            this.max = max;
            this.parser = 
                Parser.createParser(testHtml);
            parser.registerScanners();
        }

        public void run() {
            try {
                result = false;
                Node linkTag [] = parser.extractAllNodesThatAre(LinkTag.class);
                link1 = (LinkTag)linkTag[0];
                link2 = (LinkTag)linkTag[1];
                if (id<max/2) {
                    if (link1.getLink().equals("/cgi-bin/view_search?query_text=postdate>20020701&txt_clr=White&bg_clr=Red&url=http://localhost/Testing/Report1.html") &&
                        link2.getLink().equals("http://normallink.com/sometext.html"))
                        result = true;
                } else {
                    if (link1.getLink().equals("http://normallink.com/sometext.html") &&
                        link2.getLink().equals("http://normallink.com/sometext.html"))
                        result = true;
                }
            }
            catch (ParserException e) {
                System.err.println("Parser Exception");
                e.printStackTrace();
            }
            finally {
                testProgress += id;
            }
        }

        public LinkTag getLink1() {
            return link1;
        }

        public LinkTag getLink2() {
            return link2;
        }

        public boolean passed() {
            return result;
        }
    }

    /**
     * Test the toHTML method for a standalone attribute.
     */
    public void testStandAloneToHTML () throws ParserException
    {
        createParser("<input disabled>");
        parseAndAssertNodeCount (1);
        assertType ("should be Tag", Tag.class, node[0]);
        Tag tag = (Tag)node[0];
        String html = tag.toHtml ();
        assertStringEquals ("html","<INPUT DISABLED>", html);
    }

    /**
     * Test the toHTML method for a missing value attribute.
     */
    public void testMissingValueToHTML () throws ParserException
    {
        createParser("<input disabled=>");
        parseAndAssertNodeCount (1);
        assertType ("should be Tag", Tag.class, node[0]);
        Tag tag = (Tag)node[0];
        String html = tag.toHtml ();
        assertStringEquals ("html","<INPUT DISABLED=>", html);
    }

    /**
     * Mainline for all suites of tests.
     * @param args Command line arguments. The following options
     * are understood:
     * <pre>
     * -text  -- use junit.textui.TestRunner
     * -awt   -- use junit.awtui.TestRunner
     * -swing -- use junit.swingui.TestRunner (default)
     * </pre>
     * All other options are passed on to the junit framework.
     */
    public static void main(String[] args)
    {
        String runner;
        int i;
        String arguments[];
        Class cls;

        System.out.println (System.getProperty ("testclass"));
        runner = null;
        for (i = 0; (i < args.length) && (null == runner); i++)
        {
            if (args[i].equalsIgnoreCase ("-text"))
                runner = "junit.textui.TestRunner";
            else if (args[i].equalsIgnoreCase ("-awt"))
                runner = "junit.awtui.TestRunner";
            else if (args[i].equalsIgnoreCase ("-swing"))
                runner = "junit.swingui.TestRunner";
        }
        if (null != runner)
        {
            // remove it from the arguments
            arguments = new String[args.length - 1];
            System.arraycopy (args, 0, arguments, 0, i - 1);
            System.arraycopy (args, i, arguments, i - 1, args.length - i);
            args = arguments;
        }
        else
            runner = "junit.swingui.TestRunner";

        // append the test class
        arguments = new String[args.length + 1];
        System.arraycopy (args, 0, arguments, 0, args.length);
        arguments[args.length] = "org.htmlparser.tests.lexerTests.TagTests";

        // invoke main() of the test runner
        try
        {
            cls = Class.forName (runner);
            java.lang.reflect.Method method = cls.getDeclaredMethod (
                "main", new Class[] { String[].class });
            method.invoke (
                null,
                new Object[] { arguments });
        }
        catch (Throwable t)
        {
            System.err.println (
                "cannot run unit test ("
                + t.getMessage ()
                + ")");
        }
    }
}