// HTMLParser Library v1_3_20030511 - A java-based parser for HTML
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

package org.htmlparser.tests.scannersTests;
import org.htmlparser.scanners.StyleScanner;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class StyleScannerTest extends ParserTestCase
{

	public StyleScannerTest(String name) {
		super(name);
	}

	public void testEvaluate() 
	{
		StyleScanner scanner = new StyleScanner("-s");
		boolean retVal = scanner.evaluate("style ",null);
		assertEquals("Evaluation of STYLE tag",new Boolean(true),new Boolean(retVal));
	}

	public void testScan() {
		createParser("<STYLE TYPE=\"text/css\"><!--\n\n"+
		"</STYLE>","http://www.yle.fi/");
		parser.addScanner(new StyleScanner("-s"));
		try {
		 	parseAndAssertNodeCount(1);
			assertTrue("Should've thrown exception",false);
		}
		catch (ParserException e) {

		}	
	}

	public void testScanBug() throws ParserException {
		createParser("<html><head><title>Yahoo!</title><base href=http://www.yahoo.com/ target=_top><meta http-equiv=\"PICS-Label\" content='(PICS-1.1 \"http://www.icra.org/ratingsv02.html\" l r (cz 1 lz 1 nz 1 oz 1 vz 1) gen true for \"http://www.yahoo.com\" r (cz 1 lz 1 nz 1 oz 1 vz 1) \"http://www.rsac.org/ratingsv01.html\" l r (n 0 s 0 v 0 l 0) gen true for \"http://www.yahoo.com\" r (n 0 s 0 v 0 l 0))'><style>a.h{background-color:#ffee99}</style></head>",
		"http://www.google.com/test/index.html");
		parser.registerScanners();
	 	parseAndAssertNodeCount(7);
	 	assertTrue("Second last node should be a style tag",node[5] instanceof StyleTag);
	 	StyleTag styleTag = (StyleTag)node[5];
		assertEquals("Style Code","a.h{background-color:#ffee99}",styleTag.getStyleCode());
	}
	
	/**
	 * This is a bug reported by Kaarle Kaaila. 
	 */
	public void testScanBug2() throws ParserException {
		createParser("<STYLE TYPE=\"text/css\"><!--\n\n"+
		"input{font-family: arial, helvetica, sans-serif; font-size:11px;}\n\n"+
		"i {font-family: times; font-size:10pt; font-weight:normal;}\n\n"+
		".ruuhka {font-family: arial, helvetica, sans-serif; font-size:11px;}\n\n"+
		".paalinkit {font-family: arial, helvetica, sans-serif; font-size:12px;}\n\n"+
		".shortselect{font-family: arial, helvetica, sans-serif; font-size:12px; width:130;}\n\n"+
		".cityselect{font-family: arial, helvetica, sans-serif; font-size:11px; width:100;}\n\n"+
		".longselect{font-family: arial, helvetica, sans-serif; font-size:12px;}\n\n"+
		"---></STYLE>","http://www.yle.fi/");
		parser.addScanner(new StyleScanner("-s"));
	 	parseAndAssertNodeCount(1);
		assertTrue(node[0] instanceof StyleTag);
	}

	/**
	 * This is a bug reported by Dr. Wes Munsil, with the parser crashing on Google
	 */
	public void testScanBug3() throws ParserException {
		createParser("<html><head><META HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=ISO-8859-1\"><title>Google</title><style><!--\n"+
		"body,td,a,p,.h{font-family:arial,sans-serif;} .h{font-size: 20px;} .h{color:} .q{text-decoration:none; color:#0000cc;}\n"+
		"//--></style>","http://www.yle.fi/");
		parser.registerScanners();
	 	parseAndAssertNodeCount(5);
		assertTrue(node[4] instanceof StyleTag);
		StyleTag styleTag = (StyleTag)node[4];
		String expectedCode = "<!--\r\n"+"body,td,a,p,.h{font-family:arial,sans-serif;} .h{font-size: 20px;} .h{color:} .q{text-decoration:none; color:#0000cc;}\r\n"+
		"//-->";
		assertStringEquals("Expected Style Code",expectedCode,styleTag.getStyleCode());
	}
	
}
