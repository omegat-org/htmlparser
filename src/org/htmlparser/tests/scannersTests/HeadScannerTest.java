// HTMLParser Library v1_4_20030713 - A java-based parser for HTML
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
// 
// This class was contributed by Dhaval Udani
// dhaval.h.udani@orbitech.co.in

package org.htmlparser.tests.scannersTests;

import junit.framework.TestSuite;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class HeadScannerTest extends ParserTestCase {

	public HeadScannerTest(String name) {
		super(name);
	}

	public void testSimpleHead() throws ParserException {
		createParser("<HTML><HEAD></HEAD></HTML>");
		HeadScanner headScanner = new HeadScanner();
		parser.registerDomScanners();
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof Html);
		Html htmlTag = (Html)node[0];
		assertTrue(htmlTag.getChild(0) instanceof HeadTag);
	}
	
	public void testSimpleHeadWithoutEndTag() throws ParserException {
		createParser("<HTML><HEAD></HTML>");
		HeadScanner headScanner = new HeadScanner();
		parser.registerDomScanners();
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof Html);
		Html htmlTag = (Html)node[0];
		assertTrue(htmlTag.getChild(0) instanceof HeadTag);
		HeadTag headTag = (HeadTag)htmlTag.getChild(0);
		assertEquals("toHtml()","<HEAD></HEAD>",headTag.toHtml());
		assertEquals("toHtml()","<HTML><HEAD></HEAD></HTML>",htmlTag.toHtml());
	}
	
	public void testSimpleHeadWithBody() throws ParserException {
		createParser("<HTML><HEAD><BODY></HTML>");
		HeadScanner headScanner = new HeadScanner();
		parser.registerDomScanners();
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof Html);
		Html htmlTag = (Html)node[0];
		assertTrue(htmlTag.getChild(0) instanceof HeadTag);
		//assertTrue(htmlTag.getChild(1) instanceof BodyTag);
		HeadTag headTag = (HeadTag)htmlTag.getChild(0);
		assertEquals("toHtml()","<HEAD></HEAD>",headTag.toHtml());
		assertEquals("toHtml()","<HTML><HEAD></HEAD><BODY></BODY></HTML>",htmlTag.toHtml());
	}
	
		
	public static TestSuite suite() {
		return new TestSuite(HeadScannerTest.class);
	}
	
	public static void main(String[] args) 
	{
		new junit.awtui.TestRunner().start(new String[] {HeadScannerTest.class.getName()});
	}

}
