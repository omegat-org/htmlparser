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
}
