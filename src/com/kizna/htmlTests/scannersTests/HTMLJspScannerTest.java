package com.kizna.htmlTests.scannersTests;

import java.io.BufferedReader;
import java.io.StringReader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.HTMLReader;
import com.kizna.html.scanners.HTMLJspScanner;
import com.kizna.html.tags.HTMLJspTag;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

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
