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

import java.util.Vector;

import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.tags.BaseHrefTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;

public class BaseHrefTagTest extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.tagTests.BaseHrefTagTest", "BaseHrefTagTest");
    }

    public BaseHrefTagTest(String name) {
        super(name);
    }

    public void testConstruction() {
        BaseHrefTag baseRefTag = new BaseHrefTag ();
        baseRefTag.setBaseUrl ("http://www.abc.com");
        assertEquals("Expected Base URL","http://www.abc.com",baseRefTag.getBaseUrl());
    }

    public void testRemoveLastSlash() {
        String url1 = "http://www.yahoo.com/";
        String url2 = "http://www.google.com";
        String modifiedUrl1 = LinkProcessor.removeLastSlash(url1);
        String modifiedUrl2 = LinkProcessor.removeLastSlash(url2);
        assertEquals("Url1","http://www.yahoo.com",modifiedUrl1);
        assertEquals("Url2","http://www.google.com",modifiedUrl2);
    }

    public void testScan() throws ParserException{
        createParser("<html><head><TITLE>test page</TITLE><BASE HREF=\"http://www.abc.com/\"><a href=\"home.cfm\">Home</a>...</html>","http://www.google.com/test/index.html");
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new TitleTag (),
                    new LinkTag (),
                    new BaseHrefTag (),
                }));
        parseAndAssertNodeCount(7);
        assertTrue("Base href tag should be the 4th tag", node[3] instanceof BaseHrefTag);
        BaseHrefTag baseRefTag = (BaseHrefTag)node[3];
        assertEquals("Base HREF Url","http://www.abc.com",baseRefTag.getBaseUrl());
    }

    public void testNotHREFBaseTag() throws ParserException
    {
        String html = "<base target=\"_top\">";
        createParser(html);
        parseAndAssertNodeCount(1);
        assertTrue("Should be a base tag but was "+node[0].getClass().getName(),node[0] instanceof BaseHrefTag);
        BaseHrefTag baseTag = (BaseHrefTag)node[0];
        assertStringEquals("Base Tag HTML", html, baseTag.toHtml());
    }

}
