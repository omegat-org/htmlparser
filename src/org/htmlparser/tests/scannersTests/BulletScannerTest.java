// HTMLParser Library v1_4_20030824 - A java-based parser for HTML
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

import org.htmlparser.Node;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;


public class BulletScannerTest extends ParserTestCase {

	public BulletScannerTest(String name) {
		super(name);
	}

	public void testBulletFound() throws Exception {
		createParser(
			"<LI><A HREF=\"collapseHierarchy.html\">Collapse Hierarchy</A>\n"+
			"</LI>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertType("should be a bullet",Bullet.class,node[0]);
	}
	
	
	public void testOutOfMemoryBug() throws ParserException {
		createParser(
			"<html>" +
			"<head>" +
			"<title>Foo</title>" +
			"</head>" +
			"<body>" +
			"    <ul>" +
			"        <li>" +
			"            <a href=\"http://foo.com/c.html\">bibliographies on:" +
			"                <ul>" +
			"                    <li>chironomidae</li>" +
			"                </ul>" +
			"            </a>" +
			"        </li>" +
			"    </ul>" +
			"" +
			"</body>" +
			"</html>" 
		);
		parser.registerScanners();
		for (NodeIterator i = parser.elements();i.hasMoreNodes();) {
			Node node = i.nextNode();
			System.out.println(node.toHtml());
		}
	}		
	
	public void testNonEndedBullets() throws ParserException {
		createParser(
			"<li>forest practices legislation penalties for non-compliance\n"+
			" (Kwan)  <A HREF=\"/hansard/37th3rd/h21107a.htm#4384\">4384-5</A>\n"+
			"<li>passenger rail service\n"+
			" (MacPhail)  <A HREF=\"/hansard/37th3rd/h21021p.htm#3904\">3904</A>\n"+
			"<li>referendum on principles for treaty negotiations\n"+
			" (MacPhail)  <A HREF=\"/hansard/37th3rd/h20313p.htm#1894\">1894</A>\n"+
			"<li>transportation infrastructure projects\n"+
			" (MacPhail)  <A HREF=\"/hansard/37th3rd/h21022a.htm#3945\">3945-7</A>\n"+
			"<li>tuition fee freeze"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(5);
		for (int i=0;i<nodeCount;i++) {
			assertType("node "+i,Bullet.class,node[i]);
		}
	}
}
