// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2003 Derrick Oswald
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.tests.filterTests;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.Text;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Test the operation of filters.
 */
public class FilterTest extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.filterTests.FilterTest", "FilterTest");
    }

    public FilterTest (String name)
    {
        super (name);
    }

    /**
     * Test node class filtering.
     */
    public void testNodeClass () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the time for all good men..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (new NodeClassFilter (BodyTag.class));
        assertEquals ("only one element", 1, list.size ());
        assertType ("should be BodyTag", BodyTag.class, list.elementAt (0));
        BodyTag body = (BodyTag)list.elementAt (0);
        assertEquals ("only one child", 1, body.getChildCount ());
        assertSuperType ("should be Text", Text.class, body.getChildren ().elementAt (0));
        assertStringEquals("html", guts, body.toHtml ());
    }


    /**
     * Test tag name filtering.
     */
    public void testTagName () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<booty>Now is the time for all good men..</booty>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (new TagNameFilter ("booty"));
        assertEquals ("only one element", 1, list.size ());
        assertSuperType ("should be Tag", Tag.class, list.elementAt (0));
        assertStringEquals("name", "BOOTY", ((Tag)(list.elementAt (0))).getTagName ());
    }

    /**
     * Test string filtering.
     */
    public void testString () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the <a id=target><b>time</b></a> for all good <time>men</time>..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (new StringFilter ("Time"));
        assertEquals ("only one element", 1, list.size ());
        assertSuperType ("should be String", Text.class, list.elementAt (0));
        assertStringEquals("name", "time", ((Text)list.elementAt (0)).getText ());
        // test case sensitivity
        list = parser.extractAllNodesThatMatch (new StringFilter ("Time", true));
        assertEquals ("should be no elements", 0, list.size ());
    }

    /**
     * Test child filtering.
     */
    public void testChild () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the <a id=target><b>time</b></a> for all good <a href=http://bongo.com>men</a>..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (new HasChildFilter (new TagNameFilter ("b")));
        assertEquals ("only one element", 1, list.size ());
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (0));
        LinkTag link = (LinkTag)list.elementAt (0);
        assertEquals ("three children", 3, link.getChildCount ());
        assertSuperType ("should be TagNode", Tag.class, link.getChildren ().elementAt (0));
        Tag tag = (Tag)link.getChildren ().elementAt (0);
        assertStringEquals("name", "B", tag.getTagName ());
    }

    /**
     * Test attribute filtering.
     */
    public void testAttribute () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the <a id=target><b>time</b></a> for all good <a href=http://bongo.com>men</a>..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (new HasAttributeFilter ("id"));
        assertEquals ("only one element", 1, list.size ());
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (0));
        LinkTag link = (LinkTag)list.elementAt (0);
        assertEquals ("attribute value", "target", link.getAttribute ("id"));
    }

    /**
     * Test and filtering.
     */
    public void testAnd () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the <a id=one><b>time</b></a> for all good <a id=two><b>men</b></a>..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (
            new AndFilter (
                new HasChildFilter (
                    new TagNameFilter ("b")),
                new HasChildFilter (
                    new StringFilter ("men")))
                );
        assertEquals ("only one element", 1, list.size ());
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (0));
        LinkTag link = (LinkTag)list.elementAt (0);
        assertEquals ("attribute value", "two", link.getAttribute ("id"));
    }

    /**
     * Test or filtering.
     */
    public void testOr () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the <a id=one><b>time</b></a> for <a id=two><b>all</b></a> good <a id=three><b>men</b></a>..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (
            new OrFilter (
                new HasChildFilter (
                    new StringFilter ("time")),
                new HasChildFilter (
                    new StringFilter ("men")))
                );
        assertEquals ("two elements", 2, list.size ());
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (0));
        LinkTag link = (LinkTag)list.elementAt (0);
        assertEquals ("attribute value", "one", link.getAttribute ("id"));
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (1));
        link = (LinkTag)list.elementAt (1);
        assertEquals ("attribute value", "three", link.getAttribute ("id"));
    }

    /**
     * Test not filtering.
     */
    public void testNot () throws ParserException
    {
        String guts;
        String html;
        NodeList list;

        guts = "<body>Now is the <a id=one><b>time</b></a> for <a id=two><b>all</b></a> good <a id=three><b>men</b></a>..</body>";
        html = "<html>" + guts + "</html>";
        createParser (html);
        list = parser.extractAllNodesThatMatch (
            new AndFilter (
                new HasChildFilter (
                    new TagNameFilter ("b")),
                new NotFilter (
                    new HasChildFilter (
                        new StringFilter ("all"))))
                );
        assertEquals ("two elements", 2, list.size ());
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (0));
        LinkTag link = (LinkTag)list.elementAt (0);
        assertEquals ("attribute value", "one", link.getAttribute ("id"));
        assertType ("should be LinkTag", LinkTag.class, list.elementAt (1));
        link = (LinkTag)list.elementAt (1);
        assertEquals ("attribute value", "three", link.getAttribute ("id"));
    }

    public void testEscape() throws Exception
    {
        assertEquals ("douchebag", CssSelectorNodeFilter.unescape ("doucheba\\g").toString ());
    }

    public void testSelectors() throws Exception
    {
        String html = "<html><head><title>sample title</title></head><body inserterr=\"true\" yomama=\"false\"><h3 id=\"heading\">big </invalid>heading</h3><ul id=\"things\"><li><br word=\"broken\"/>&gt;moocow<li><applet/>doohickey<li class=\"last\"><b class=\"item\">final<br>item</b></ul></body></html>";
        Lexer l;
        Parser p;
        CssSelectorNodeFilter it;
        NodeIterator i;
        int count;

        l = new Lexer (html);
        p = new Parser (l);
        it = new CssSelectorNodeFilter ("li + li");
        count = 0;
        for (i = p.extractAllNodesThatMatch (it).elements (); i.hasMoreNodes ();)
        {
            assertEquals ("tag name wrong", "LI", ((Tag)i.nextNode()).getTagName());
            count++;
        }
        assertEquals ("wrong count", 2, count);
    }
}

