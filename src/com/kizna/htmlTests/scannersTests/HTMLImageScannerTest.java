// HTMLParser Library v1_2_20020728 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.htmlTests.scannersTests;
import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.StringReader;

import com.kizna.html.*;
import com.kizna.html.tags.HTMLImageTag;
import com.kizna.html.tags.HTMLLinkTag;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;
import com.kizna.html.scanners.HTMLImageScanner;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/18/2001 2:20:43 AM)
 * @author: Administrator
 */
public class HTMLImageScannerTest extends junit.framework.TestCase 
{
	/**
	 * HTMLAppletScannerTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLImageScannerTest(String name) {
		super(name);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:22:36 AM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() 
	{
		TestSuite suite = new TestSuite(HTMLImageScannerTest.class);
		return suite;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testDynamicRelativeImageScan() throws HTMLParserException {
		String testHTML = "<IMG SRC=\"../abc/def/mypic.jpg\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi?abcdefg");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-l"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageLocation());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:23:14 AM)
	 */
	public void testEvaluate() 
	{
		HTMLImageScanner scanner = new HTMLImageScanner("-i");
		boolean retVal = scanner.evaluate("   img ",null);
		assertEquals("Evaluation of IMG tag",new Boolean(true),new Boolean(retVal));
	}
	/**
	 * This is the reproduction of a bug which causes a null pointer exception
	 * Creation date: (6/18/2001 2:26:41 AM)
	 */
	public void testExtractImageLocnInvertedCommasBug()
	{
		HTMLTag tag = new HTMLTag(0,0,"img width=638 height=53 border=0 usemap=\"#m\" src=http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif alt=Yahoo","");
		String link = "img width=638 height=53 border=0 usemap=\"#m\" src=http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif alt=Yahoo";
		String url = "c:\\cvs\\html\\binaries\\yahoo.htm";
		HTMLImageScanner scanner = new HTMLImageScanner("-i");
		assertEquals("Extracted Image Locn","http://us.a1.yimg.com/us.yimg.com/i/ww/m5v5.gif",scanner.extractImageLocn(tag,url));
	}
	/**
	 * This test has been improved to check for params
	 * in the image tag, based on requirement by Annette Doyle.
	 * Thereby an important bug was detected.
	 */
	public void testPlaceHolderImageScan() throws HTMLParserException {
		String testHTML = "<IMG width=1 height=1 alt=\"a\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi?abcdefg");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Image Locn","",imageTag.getImageLocation());		
		assertEquals("Image width","1",imageTag.getParameter("WIDTH"));
		assertEquals("Image height","1",imageTag.getParameter("HEIGHT"));
		assertEquals("alt","a",imageTag.getParameter("ALT"));
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testRelativeImageScan() throws HTMLParserException {
		String testHTML = "<IMG SRC=\"mypic.jpg\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-l"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/mypic.jpg",imageTag.getImageLocation());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testRelativeImageScan2() throws HTMLParserException {
		String testHTML = "<IMG SRC=\"abc/def/mypic.jpg\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-l"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageLocation());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:02:50 PM)
	 */
	public void testRelativeImageScan3() throws HTMLParserException {
		String testHTML = "<IMG SRC=\"../abc/def/mypic.jpg\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-l"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/mypic.jpg",imageTag.getImageLocation());
	}
	/**
	 * Test image url which contains spaces in it.
	 * This was actually a bug reported by Sam Joseph (sam@neurogrid.net)
	 */
	public void testImageWithSpaces() throws HTMLParserException
	{
		String testHTML = "<IMG SRC=\"../abc/def/Hello World.jpg\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-l"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Link","http://www.yahoo.com/abc/def/Hello World.jpg",imageTag.getImageLocation());		
	}
	public void testImageWithNewLineChars() throws HTMLParserException
	{
		String testHTML = "<IMG SRC=\"../abc/def/Hello \nWorld.jpg\">";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com/ghi");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-l"));
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLTag)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Expected Image","http://www.yahoo.com/abc/def/Hello World.jpg",imageTag.getImageLocation());		
	}
	/**
	 * Test case to reproduce bug reported by Annette
	 */
	public void testImageTagsFromYahoo() throws HTMLParserException
	{
		String testHTML = "<small><a href=s/5926>Air</a>, <a href=s/5927>Hotel</a>, <a href=s/5928>Vacations</a>, <a href=s/5929>Cruises</a></small></td><td align=center><a href=\"http://rd.yahoo.com/M=218794.2020165.3500581.220161/D=yahoo_top/S=2716149:NP/A=1041273/?http://adfarm.mediaplex.com/ad/ck/990-1736-1039-211\" target=\"_top\"><img width=230 height=33 src=\"http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif\" alt=\"\" border=0></a></td><td nowrap align=center width=215>Find your match on<br><a href=s/2734><b>Yahoo! Personals</b></a></td></tr><tr><td colspan=3 align=center><input size=30 name=p>\n"+
		"<input type=submit value=Search> <a href=r/so>advanced search</a></td></tr></table><table border=0 cellspacing=0 cellpadding=3 width=640><tr><td nowrap align=center><table border=0 cellspacing=0 cellpadding=0><tr><td><a href=s/5948><img src=\"http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/eet.gif\" width=20 height=20 border=0></a></td><td> &nbsp; &nbsp; <a href=s/1048><b>Yahooligans!</b></a> - <a href=s/5282>Eet & Ern</a>, <a href=s/5283>Games</a>, <a href=s/5284>Science</a>, <a href=s/5285>Sports</a>, <a href=s/5286>Movies</a>, <a href=s/1048>more</a> &nbsp; &nbsp; </td><td><a href=s/5948><img src=\"http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/ern.gif\" width=20 height=20 border=0></a></td></tr></table></td></tr><tr><td nowrap align=center><small><b>Shop</b>&nbsp;\n";
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[10];
		// Register the image scanner
		parser.addScanner(new HTMLImageScanner("-i"));
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
		assertEquals("Expected Image","http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif",imageTag.getImageLocation());		
		HTMLImageTag imageTag2 = (HTMLImageTag)node[1];
		assertEquals("Expected Image 2","http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/eet.gif",imageTag2.getImageLocation());
		HTMLImageTag imageTag3 = (HTMLImageTag)node[2];
		assertEquals("Expected Image 3","http://us.i1.yimg.com/us.yimg.com/i/ligans/klgs/ern.gif",imageTag3.getImageLocation());	
	}
	/**
	 * Test case to reproduce bug reported by Annette
	 */
	public void testImageTagsFromYahooWithAllScannersRegistered() throws HTMLParserException
	{
		String testHTML = "<small><a href=s/5926>Air</a>, <a href=s/5927>Hotel</a>, <a href=s/5928>Vacations</a>, <a href=s/5929>Cruises</a></small></td><td align=center><a href=\"http://rd.yahoo.com/M=218794.2020165.3500581.220161/D=yahoo_top/S=2716149:NP/A=1041273/?http://adfarm.mediaplex.com/ad/ck/990-1736-1039-211\" target=\"_top\"><img width=230 height=33 src=\"http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif\" alt=\"\" border=0></a></td><td nowrap align=center width=215>Find your match on<br><a href=s/2734><b>Yahoo! Personals</b></a></td></tr><tr><td colspan=3 align=center><input size=30 name=p>\n";
	
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.yahoo.com");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[100];
		// Register the image scanner
		parser.registerScanners();
		int i = 0;
		HTMLNode thisNode;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLNode)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 22",22,i);
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
		assertEquals("Expected Image","http://us.a1.yimg.com/us.yimg.com/a/co/columbiahouse/4for49Freesh_230x33_redx2.gif",imageTag.getImageLocation());		
	}
	/**
	 * This is the reproduction of a bug reported
	 * by Annette Doyle
	 */
	public void testImageTagOnMultipleLines() throws HTMLParserException {
		String testHTML = "  <td rowspan=3><img height=49 \n\n"+
	      "alt=\"Central Intelligence Agency, Director of Central Intelligence\" \n\n"+
	      "src=\"graphics/images_home2/cia_banners_template3_01.gif\" \n\n"+
	      "width=241></td>"; 
	
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cia.gov");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[100];
		// Register the image scanner
		parser.registerScanners();
		int i = 0;
		HTMLNode thisNode;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLNode)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 3",3,i);
		assertTrue("Node identified should be HTMLImageTag",node[1] instanceof HTMLImageTag);
		HTMLImageTag imageTag = (HTMLImageTag)node[1];
		// Get the data from the node
		assertEquals("Image location","http://www.cia.gov/graphics/images_home2/cia_banners_template3_01.gif",imageTag.getImageLocation());
		assertEquals("Alt Value","Central Intelligence Agency, Director of Central Intelligence",imageTag.getParameter("ALT"));
		assertEquals("Width","241",imageTag.getParameter("WIDTH"));	
		assertEquals("Height","49",imageTag.getParameter("HEIGHT"));
	}
	public void testDirectRelativeLinks() throws HTMLParserException {
		String testHTML = "<IMG SRC  = \"/images/lines/li065.jpg\">"; 
	
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cybergeo.presse.fr/REVGEO/ttsavoir/joly.htm"); 

		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[100];
		// Register the image scanner
		parser.registerScanners();
		int i = 0;
		HTMLNode thisNode;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i++] = (HTMLNode)e.nextHTMLNode();
		}	
		assertEquals("Number of nodes identified should be 1",1,i);
		assertTrue("Node identified should be HTMLImageTag",node[0] instanceof HTMLImageTag);	
		HTMLImageTag imageTag = (HTMLImageTag)node[0];
		assertEquals("Image Location","http://www.cybergeo.presse.fr/images/lines/li065.jpg",imageTag.getImageLocation());
		
	}
}
