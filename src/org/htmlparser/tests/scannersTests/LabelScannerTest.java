// HTMLParser Library v1_3_20030202 - A java-based parser for HTML
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

import org.htmlparser.scanners.LabelScanner;
import org.htmlparser.tags.LabelTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.HTMLParserException;

public class LabelScannerTest extends HTMLParserTestCase {

	public LabelScannerTest(String name) {
		super(name);
	}
	public void testSimpleLabels() throws HTMLParserException {
		createParser("<label>This is a label tag</label>");
		LabelScanner labelScanner = new LabelScanner("-l");
		parser.addScanner(labelScanner);
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof LabelTag);
		//  check the title node
		LabelTag labelTag = (LabelTag) node[0];
		assertEquals("Label","This is a label tag",labelTag.getLabel());
		assertStringEquals("Label","<LABEL>This is a label tag</LABEL>",labelTag.toHTML());
		assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
	}
	
	public void testLabelWithJspTag() throws HTMLParserException {
		createParser("<label><%=labelValue%></label>");
		parser.registerScanners();
		LabelScanner labelScanner = new LabelScanner("-l");
		parser.addScanner(labelScanner);
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof LabelTag);
		//  check the title node
		LabelTag labelTag = (LabelTag) node[0];
		assertStringEquals("Label","<LABEL><%=labelValue%></LABEL>",labelTag.toHTML());
		assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
	}
	
	public void testLabelWithOtherTags() throws HTMLParserException {
		createParser("<label><span>Span within label</span></label>");
		parser.registerScanners();
		LabelScanner labelScanner = new LabelScanner("-l");
		parser.addScanner(labelScanner);
		parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof LabelTag);
		//  check the title node
		LabelTag labelTag = (LabelTag) node[0];
		assertEquals("Label value","Span within label",labelTag.getLabel());
		assertStringEquals("Label","<LABEL><SPAN>Span within label</SPAN></LABEL>",labelTag.toHTML());
		assertEquals("Label Scanner",labelScanner,labelTag.getThisScanner());
	}
	
	public static TestSuite suite() {
		return new TestSuite(LabelScannerTest.class);
	}
}
