package org.htmlparser.tests.scannersTests;

import org.htmlparser.tags.Bullet;
import org.htmlparser.tests.ParserTestCase;


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
}
