// HTMLParser Library v1_3_20030504 - A java-based parser for HTML
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

import org.htmlparser.scanners.ImageScanner;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;

public class ImageTagTest extends ParserTestCase 
{
	public ImageTagTest(String name) {
		super(name);
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testImageTag() throws ParserException
	{
		createParser("<IMG alt=Google height=115 src=\"goo/title_homepage4.gif\" width=305>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new ImageScanner("-i",new LinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof ImageTag);
		ImageTag imageTag = (ImageTag)node[0];
		assertEquals("The image locn","http://www.google.com/test/goo/title_homepage4.gif",imageTag.getImageURL());
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testImageTagBug() throws ParserException
	{
		createParser("<IMG alt=Google height=115 src=\"../goo/title_homepage4.gif\" width=305>","http://www.google.com/test/");
		// Register the image scanner
		parser.addScanner(new ImageScanner("-i",new LinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof ImageTag);
		ImageTag imageTag = (ImageTag)node[0];
		assertEquals("The image locn","http://www.google.com/goo/title_homepage4.gif",imageTag.getImageURL());
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testImageTageBug2() throws ParserException
	{
		createParser("<IMG alt=Google height=115 src=\"../../goo/title_homepage4.gif\" width=305>","http://www.google.com/test/test/index.html");
		// Register the image scanner
		parser.addScanner(new ImageScanner("-i",new LinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof ImageTag);
		ImageTag imageTag = (ImageTag)node[0];
		assertEquals("The image locn","http://www.google.com/goo/title_homepage4.gif",imageTag.getImageURL());
	}

	/**
	 * This bug occurs when there is a null pointer exception thrown while scanning a tag using LinkScanner.
	 * Creation date: (7/1/2001 2:42:13 PM)
	 */
	public void testImageTagSingleQuoteBug() throws ParserException
	{
		createParser("<IMG SRC='abcd.jpg'>","http://www.cj.com/");
		// Register the image scanner
		parser.addScanner(new ImageScanner("-i",new LinkProcessor()));
			
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof ImageTag);
		ImageTag imageTag = (ImageTag)node[0];
		assertEquals("Image incorrect","http://www.cj.com/abcd.jpg",imageTag.getImageURL());	
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;A HREF=&gt;Something&lt;A&gt;<BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 * Creation date: (6/17/2001 4:01:06 PM)
	 */
	public void testNullImageBug() throws ParserException
	{
		createParser("<IMG SRC=>","http://www.google.com/test/index.html");
		// Register the image scanner
		parser.addScanner(new ImageScanner("-i",new LinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLLinkTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof ImageTag);
		ImageTag imageTag = (ImageTag)node[0];
		assertStringEquals("The image location","",imageTag.getImageURL());
	}

	public void testToHTML() throws ParserException {
		createParser("<IMG alt=Google height=115 src=\"../../goo/title_homepage4.gif\" width=305>","http://www.google.com/test/test/index.html");
		// Register the image scanner
		parser.addScanner(new ImageScanner("-i",new LinkProcessor()));
			
		parseAndAssertNodeCount(1);
		// The node should be an HTMLImageTag
		assertTrue("Node should be a HTMLImageTag",node[0] instanceof ImageTag);
		ImageTag imageTag = (ImageTag)node[0];
		assertStringEquals("The image locn","<IMG WIDTH=\"305\" ALT=\"Google\" SRC=\"../../goo/title_homepage4.gif\" HEIGHT=\"115\">",imageTag.toHtml());
		assertEquals("Alt","Google",imageTag.getAttribute("alt"));
		assertEquals("Height","115",imageTag.getAttribute("height"));
		assertEquals("Width","305",imageTag.getAttribute("width"));
	}
}
