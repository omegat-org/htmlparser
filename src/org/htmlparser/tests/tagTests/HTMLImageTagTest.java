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
import java.util.Enumeration;
import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.*;
import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLImageTagTest extends HTMLParserTestCase 
{
	public HTMLImageTagTest(String name) {
		super(name);
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testImageTag() throws HTMLParserException
	{
		createParser("<IMG alt=Google height=115 src=\"goo/title_homepage4.gif\" width=305>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("The image locn","http://www.google.com/test/goo/title_homepage4.gif",imageTag.getImageLocation());
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testImageTagBug() throws HTMLParserException
	{
		createParser("<IMG alt=Google height=115 src=\"../goo/title_homepage4.gif\" width=305>","http://www.google.com/test/");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("The image locn","http://www.google.com/goo/title_homepage4.gif",imageTag.getImageLocation());
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testImageTageBug2() throws HTMLParserException
	{
		createParser("<IMG alt=Google height=115 src=\"../../goo/title_homepage4.gif\" width=305>","http://www.google.com/test/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("The image locn","http://www.google.com/goo/title_homepage4.gif",imageTag.getImageLocation());
	}

	/**
	 * This bug occurs when there is a null pointer exception thrown while scanning a tag using HTMLLinkScanner.
	 * Creation date: (7/1/2001 2:42:13 PM)
	 */
	public void testImageTagSingleQuoteBug() throws HTMLParserException
	{
		createParser("<IMG SRC='abcd.jpg'>","http://www.cj.com/");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Image incorrect","http://www.cj.com/abcd.jpg",imageTag.getImageLocation());	
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;A HREF=&gt;Something&lt;A&gt;<BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testNullImageBug() throws HTMLParserException
	{
		createParser("<IMG SRC=>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("The image location","",imageTag.getImageLocation());
	}

	public void testToHTML() throws HTMLParserException {
		createParser("<IMG alt=Google height=115 src=\"../../goo/title_homepage4.gif\" width=305>","http://www.google.com/test/test/index.html");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("The image locn","<IMG alt=Google height=115 src=\"../../goo/title_homepage4.gif\" width=305>",imageTag.toHTML());
		assertEquals("Alt","Google",imageTag.getParameter("alt"));
		assertEquals("Height","115",imageTag.getParameter("height"));
		assertEquals("Width","305",imageTag.getParameter("width"));
	}
}
