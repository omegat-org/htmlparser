// HTMLParser Library v1_2 - A java-based parser for HTML
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
import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.StringReader;

import org.htmlparser.*;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.tests.HTMLParserTestCase;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;
import org.htmlparser.scanners.HTMLImageScanner;
import junit.framework.TestSuite;

public class HTMLImageScannerTest extends HTMLParserTestCase
{
	
	public HTMLImageScannerTest(String name) {
		super(name);
	}
	
	public void testDynamicRelativeImageScan() throws HTMLParserException {
		createParser("<IMG SRC=\"../abc/def/mypic.jpg\">","http://www.yahoo.com/ghi?abcdefg");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageURL());
	}
	
	public void testEvaluate() 
	{
		HTMLImageScanner scanner = new HTMLImageScanner("-i",new HTMLLinkProcessor());
		boolean retVal = scanner.evaluate("   img ",null);
		assertEquals("Evaluation of IMG tag",new Boolean(true),new Boolean(retVal));
	}
	
	/**
	 * This is the reproduction of a bug which causes a null pointer exception
	 */
	public void testExtractImageLocnInvertedCommasBug() throws HTMLParserException
	{
		HTMLTag tag = new HTMLTag(0,0,"img width=638 height=53 border=0 usemap=\"#m\" src=http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif alt=Yahoo","");
		String link = "img width=638 height=53 border=0 usemap=\"#m\" src=http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif alt=Yahoo";
		String url = "c:\\cvs\\html\\binaries\\yahoo.htm";
		HTMLImageScanner scanner = new HTMLImageScanner("-i",new HTMLLinkProcessor());
		assertEquals("Extracted Image Locn","http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif",scanner.extractImageLocn(tag,url));
	}
	
	/**
	 * This test has been improved to check for params
	 * in the image tag, based on requirement by Annette Doyle.
	 * Thereby an important bug was detected.
	 */
	public void testPlaceHolderImageScan() throws HTMLParserException {
		createParser("<IMG width=1 height=1 alt=\"a\">","http://www.yahoo.com/ghi?abcdefg");
		
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Image Locn","",imageTag.getImageURL());		
		assertEquals("Image width","1",imageTag.getParameter("WIDTH"));
		assertEquals("Image height","1",imageTag.getParameter("HEIGHT"));
		assertEquals("alt","a",imageTag.getParameter("ALT"));
	}
	
	public void testRelativeImageScan() throws HTMLParserException {
		createParser("<IMG SRC=\"mypic.jpg\">","http://www.yahoo.com");
		
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/mypic.jpg",imageTag.getImageURL());
	}
	
	public void testRelativeImageScan2() throws HTMLParserException {
		createParser("<IMG SRC=\"abc/def/mypic.jpg\">","http://www.yahoo.com");		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageURL());
	}
	
	public void testRelativeImageScan3() throws HTMLParserException {
		createParser("<IMG SRC=\"../abc/def/mypic.jpg\">","http://www.yahoo.com/ghi");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageURL());
	}
	
	/**
	 * Test image url which contains spaces in it.
	 * This was actually a bug reported by Sam Joseph (sam@neurogrid.net)
	 */
	public void testImageWithSpaces() throws HTMLParserException
	{
		createParser("<IMG SRC=\"../abc/def/Hello World.jpg\">","http://www.yahoo.com/ghi");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/Hello World.jpg",imageTag.getImageURL());		
	}
	
	public void testImageWithNewLineChars() throws HTMLParserException
	{
		createParser("<IMG SRC=\"../abc/def/Hello \r\nWorld.jpg\">","http://www.yahoo.com/ghi");
		parser.setLineSeparator("\r\n");
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		String exp = new String("http://www.yahoo.com/abc/def/Hello World.jpg");
		//assertEquals("Length of image",exp.length(),imageTag.getImageLocation().length());
		assertStringEquals("Expected Image",exp,imageTag.getImageURL());		
	}
	
	/**
	 * Test case to reproduce bug reported by Annette
	 */
	public void testImageTagsFromYahoo() throws HTMLParserException
	{
		createParser("<small><a href=s/5926>Air</a>, <a href=s/5927>Hotel</a>, <a href=s/5928>Vacations</a>, <a href=s/5929>Cruises</a></small></td><td align=center><a href=\"http://rd.yahoo.com/M=218794.2020165.3500581.220161/D=yahoo_top/S=2716149:NP/A=1041273/?http://adfarm.mediaplex.com/ad/ck/990-1736-1039-211\" target=\"_top\"><img width=230 height=33 src=\"http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif\" alt=\"\" border=0></a></td><td nowrap align=center width=215>Find your match on<br><a href=s/2734><b>Yahoo! Personals</b></a></td></tr><tr><td colspan=3 align=center><input size=30 name=p>\n"+
		"<input type=submit value=Search> <a href=r/so>advanced search</a></td></tr></table><table border=0 cellspacing=0 cellpadding=3 width=640><tr><td nowrap align=center><table border=0 cellspacing=0 cellpadding=0><tr><td><a href=s/5948><img src=\"http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/eet.gif\" width=20 height=20 border=0></a></td><td> &nbsp; &nbsp; <a href=s/1048><b>Yahooligans!</b></a> - <a href=s/5282>Eet & Ern</a>, <a href=s/5283>Games</a>, <a href=s/5284>Science</a>, <a href=s/5285>Sports</a>, <a href=s/5286>Movies</a>, <a href=s/1048>more</a> &nbsp; &nbsp; </td><td><a href=s/5948><img src=\"http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/ern.gif\" width=20 height=20 border=0></a></td></tr></table></td></tr><tr><td nowrap align=center><small><b>Shop</b>&nbsp;\n","http://www.yahoo.com");
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i",new HTMLLinkProcessor()));
		int i = 0;
		HTMLNode thisNode;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			thisNode = (HTMLNode)e.nextHTMLNode();
			if (thisNode instanceof HTMLImageTag)
			node[i++] = thisNode;
		}	
		assertEquals("Number of nodes identified should be 3",3,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Image","http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif",imageTag.getImageURL());		
		HTMLImageTag imageTag2 = (HTMLImageTag)node[1];
		assertEquals("Expected Image 2","http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/eet.gif",imageTag2.getImageURL());
		HTMLImageTag imageTag3 = (HTMLImageTag)node[2];
		assertEquals("Expected Image 3","http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/ern.gif",imageTag3.getImageURL());	
	}
	
	/**
	 * Test case to reproduce bug reported by Annette
	 */
	public void testImageTagsFromYahooWithAllScannersRegistered() throws HTMLParserException
	{
		createParser("<small><a href=s/5926>Air</a>, <a href=s/5927>Hotel</a>, <a href=s/5928>Vacations</a>, <a href=s/5929>Cruises</a></small></td><td align=center><a href=\"http://rd.yahoo.com/M=218794.2020165.3500581.220161/D=yahoo_top/S=2716149:NP/A=1041273/?http://adfarm.mediaplex.com/ad/ck/990-1736-1039-211\" target=\"_top\"><img width=230 height=33 src=\"http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif\" alt=\"\" border=0></a></td><td nowrap align=center width=215>Find your match on<br><a href=s/2734><b>Yahoo! Personals</b></a></td></tr><tr><td colspan=3 align=center><input size=30 name=p>\n","http://www.yahoo.com",30);
	
		// Register the image scanner
		parser.registerScanners();
		parseAndAssertNodeCount(22);
		assertTrue("Node identified should be HTMLLinkTag",node[11] instanceof HTMLLinkTag);
		HTMLLinkTag linkTag = (HTMLLinkTag)node[11];
		HTMLNode [] node2 = new HTMLNode[10];
		int j = 0;
		for (Enumeration e = linkTag.linkData();e.hasMoreElements();) {
			node2[j++] = (HTMLNode)e.nextElement();
		}
		assertEquals("Number of tags within the link",1,j);
		assertTrue("Tag within link should be an image tag",node2[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node2[0];
		assertEquals("Expected Image","http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif",imageTag.getImageURL());		
	}
	
	/**
	 * This is the reproduction of a bug reported
	 * by Annette Doyle
	 */
	public void testImageTagOnMultipleLines() throws HTMLParserException {
		createParser("  <td rowspan=3><img height=49 \n\n"+
	      "alt=\"Central Intelligence Agency, Director of Central Intelligence\" \n\n"+
	      "src=\"graphics/images_home2/cia_banners_template3_01.gif\" \n\n"+
	      "width=241></td>","http://www.cia.gov"); 
	
		// Register the image scanner
		parser.registerScanners();
		parseAndAssertNodeCount(3);
		assertTrue("Node identified should be HTMLImageTag",node[1] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[1];
		// Get the data from the node
		assertEquals("Image location","http://www.cia.gov/graphics/images_home2/cia_banners_template3_01.gif",imageTag.getImageURL());
		assertEquals("Alt Value","Central Intelligence Agency, Director of Central Intelligence",imageTag.getParameter("ALT"));
		assertEquals("Width","241",imageTag.getParameter("WIDTH"));	
		assertEquals("Height","49",imageTag.getParameter("HEIGHT"));
	}

	public void testDirectRelativeLinks() throws HTMLParserException {
		createParser("<IMG SRC  = \"/images/lines/li065.jpg\">","http://www.cybergeo.presse.fr/REVGEO/ttsavoir/joly.htm"); 
	
		// Register the image scanner
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);	
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Image Location","http://www.cybergeo.presse.fr/images/lines/li065.jpg",imageTag.getImageURL());
		
	}

	/**
	 * Based on a page submitted by Claude Duguay, the image tag has IMG SRC"somefile.jpg" - a missing equal 
	 * to sign
	 */
	public void testMissingEqualTo() throws HTMLParserException {
		createParser("<img src\"/images/spacer.gif\" width=\"1\" height=\"1\" alt=\"\">","http://www.htmlparser.org/subdir1/subdir2");

		// Register the image scanner
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);	
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Image Location","http://www.htmlparser.org/images/spacer.gif",imageTag.getImageURL());
		assertEquals("Width","1",imageTag.getParameter("WIDTH"));
		assertEquals("Height","1",imageTag.getParameter("HEIGHT"));
		assertEquals("Alt","",imageTag.getParameter("ALT"));
	}
}
