// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package org.htmlparser.htmlTests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.htmlparser.html.HTMLNode;
import org.htmlparser.html.HTMLParser;
import org.htmlparser.html.HTMLReader;
import org.htmlparser.html.scanners.HTMLJspScanner;
import org.htmlparser.html.tags.HTMLJspTag;
import org.htmlparser.html.util.DefaultHTMLParserFeedback;
import org.htmlparser.html.util.HTMLEnumeration;
import org.htmlparser.html.util.HTMLParserException;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLJspScannerTest extends TestCase {

	/**
	 * Constructor for HTMLJspScannerTest.
	 * @param arg0
	 */
	public HTMLJspScannerTest(String arg0) {
		super(arg0);
	}
	/**
	 * In response to bug report 621117, wherein jsp tags
	 * are not recognized if they occur within string nodes.
	 */
	public void testScan() throws HTMLParserException {
		String testHTML = new String(
		"<h1>\n"+
		"This is a <%=object%>\n"+
		"</h1>");
		StringReader sr = new StringReader(testHTML);
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
		HTMLParser parser = new HTMLParser(reader,new DefaultHTMLParserFeedback());
		HTMLNode [] node = new HTMLNode[20];
		// Register the Jsp Scanner
		parser.addScanner(new HTMLJspScanner("-j"));	
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 4 nodes identified",new Integer(4),new Integer(i));
		// The first node should be an HTMLJspTag
		assertTrue("Third should be an HTMLJspTag",node[2] instanceof HTMLJspTag);
		HTMLJspTag tag = (HTMLJspTag)node[2];
		assertEquals("tag contents","=object",tag.getText());
	
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLJspScannerTest.class);
	}
}
