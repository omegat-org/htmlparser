/*
 * Created on Apr 27, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.htmlparser.tests.scannersTests;

import org.htmlparser.Node;
import org.htmlparser.StringNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author Somik Raha
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BulletListScannerTest extends ParserTestCase {

	public BulletListScannerTest(String name) {
		super(name);
	}

	public void testScan() throws ParserException {
		createParser(
			"<ul TYPE=DISC>" +				"<ul TYPE=\"DISC\"><li>Energy supply\n"+
					" (Campbell)  <A HREF=\"/hansard/37th3rd/h20307p.htm#1646\">1646</A>\n"+
					" (MacPhail)  <A HREF=\"/hansard/37th3rd/h20307p.htm#1646\">1646</A>\n"+
				"</ul><A NAME=\"calpinecorp\"></A><B>Calpine Corp.</B>\n"+
				"<ul TYPE=\"DISC\"><li>Power plant projects\n"+
					" (Neufeld)  <A HREF=\"/hansard/37th3rd/h20314p.htm#1985\">1985</A>\n"+
				"</ul>" +			"</ul>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		
		NodeList nestedBulletLists = 
			((CompositeTag)node[0]).searchFor(
				BulletList.class
			); 
		assertEquals(
			"bullets in first list",
			2,
			nestedBulletLists.size()
		);
		BulletList firstList =
			(BulletList)nestedBulletLists.elementAt(0);
		Bullet firstBullet = 
			(Bullet)firstList.childAt(0);
		Node firstNodeInFirstBullet =
			firstBullet.childAt(0);
		assertType(
			"first child in bullet",
			StringNode.class,
			firstNodeInFirstBullet
		);
		assertStringEquals(
			"expected text",
			"Energy supply\r\n" +			" (Campbell)  ",
			firstNodeInFirstBullet.toPlainTextString()
		);
	}
}
