// HTMLParser Library v1_2_20021215 - A java-based parser for HTML
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

package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLReader;
import org.htmlparser.tags.HTMLImageTag;
import org.htmlparser.tags.HTMLLinkTag;
import org.htmlparser.util.DefaultHTMLParserFeedback;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLParserTest extends HTMLParserTestCase {

	public HTMLParserTest(String name) {
		super(name);
	}
	public void testElements() throws Exception {
		StringBuffer hugeData = new StringBuffer();
		for (int i=0;i<5001;i++) hugeData.append('a');
		createParser(hugeData.toString());
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be 1 node identified",1,i);
		// Now try getting the elements again
//		i = 0;
//		reader.reset();
//		reader.setLineCount(1);
//		reader.setPosInLine(-1);
//		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
//		{
//			node[i++] = e.nextHTMLNode();
//		}
//		assertEquals("There should be 1 node identified (second call to parser.elements())",1,i);
	}

	/**
	 * This testcase needs you to be online.
	 */
	public void testElementsFromWeb() throws Exception {
		HTMLParser parser;
		try {
			parser = new HTMLParser("http://www.google.com");
		}
		catch (Exception e ){
			throw new HTMLParserException("You must be offline! This test needs you to be connected to the internet.",e);
		}
		parser.getReader().mark(5000);

		HTMLNode [] node = new HTMLNode[500];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		int cnt = i;
		parser.getReader().reset();
		// Now try getting the elements again
		i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextHTMLNode();
		}
		assertEquals("There should be "+cnt+" nodes identified (second call to parser.elements())",cnt,i);
	}	
	
	public void testNullUrl() {
		HTMLParser parser;
		try {
			parser = new HTMLParser("http://someoneexisting.com");
			assertTrue("Should have thrown an exception!",false);
		}
		catch (HTMLParserException e) {
			
		}
	}
	
	public void testURLWithSpaces() throws HTMLParserException{
		HTMLParser parser;
		String url = "http://htmlparser.sourceforge.net/test/This is a Test Page.html";
		
		parser = new HTMLParser(url);
		HTMLNode node [] = new HTMLNode[30];
		int i = 0;
		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			node[i] = e.nextHTMLNode();
			i++;
			
		}
		assertEquals("Expected nodes",12,i);
	}
	public void testLinkCollection() throws HTMLParserException {
		createParser(
		"<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><title>Google</title><style><!--\n"+
		"body,td,a,p,.h{font-family:arial,sans-serif;} .h{font-size: 20px;} .h{color:} .q{text-decoration:none; color:#0000cc;}\n"+
		"//--></style>\n"+
		"<script>\n"+
		"<!--\n"+
		"function sf(){document.f.q.focus();}\n"+
		"function c(p){var f=document.f;if (f.action) {f.action = 'http://'+p;f.submit();return false;}return true;}\n"+
		"// -->\n"+
		"</script>\n"+
		"</head><body bgcolor=#ffffff text=#000000 link=#0000cc vlink=#551a8b alink=#ff0000 onLoad=sf()><center><table border=0 cellspacing=0 cellpadding=0><tr><td><img src=\"images/logo.gif\" width=276 height=110 alt=\"Google\"></td></tr></table><br>\n"+
		"<table border=0 cellspacing=0 cellpadding=0><tr><td width=15>&nbsp;</td><td id=0 bgcolor=#3366cc align=center width=95 nowrap><font color=#ffffff size=-1><b>Web</b></font></td><td width=15>&nbsp;</td><td id=1 bgcolor=#efefef align=center width=95 nowrap onClick=\"return c('www.google.com/imghp');\" style=cursor:pointer;cursor:hand;><a id=1a class=q href=\"/imghp?hl=en&ie=UTF-8&oe=UTF-8\" onClick=\"return c('www.google.com/imghp');\"><font size=-1>Images</font></a></td><td width=15>&nbsp;</td><td id=2 bgcolor=#efefef align=center width=95 nowrap onClick=\"return c('www.google.com/grphp');\" style=cursor:pointer;cursor:hand;><a id=2a class=q href=\"/grphp?hl=en&ie=UTF-8&oe=UTF-8\" onClick=\"return c('www.google.com/grphp');\"><font size=-1>Groups</font></a></td><td width=15>&nbsp;</td><td id=3 bgcolor=#efefef align=center width=95 nowrap onClick=\"return c('www.google.com/dirhp');\" style=cursor:pointer;cursor:hand;><a id=3a class=q href=\"/dirhp?hl=en&ie=UTF-8&oe=UTF-8\" onClick=\"return c('www.google.com/dirhp');\"><font size=-1>Directory</font></a></td><td width=15>&nbsp;</td><td id=4 bgcolor=#efefef align=center width=95 nowrap onClick=\"return c('www.google.com/nwshp');\" style=cursor:pointer;cursor:hand;><a id=4a class=q href=\"/nwshp?hl=en&ie=UTF-8&oe=UTF-8\" onClick=\"return c('www.google.com/nwshp');\"><font size=-1><nobr>News-<font	color=red>New!</font></nobr></font></a></td><td width=15>&nbsp;</td></tr><tr><td colspan=12 bgcolor=#3366cc><img width=1 height=1 alt=\"\"></td></tr></table><br><form action=\"/search\" name=f><table cellspacing=0 cellpadding=0><tr><td width=75>&nbsp;</td><td align=center><input type=hidden name=hl value=en><input type=hidden name=ie value=\"UTF-8\"><input type=hidden name=oe value=\"UTF-8\"><input maxLength=256 size=55 name=q value=\"\"><br><input type=submit value=\"Google Search\" name=btnG><input type=submit value=\"I'm Feeling Lucky\" name=btnI></td><td valign=top nowrap><font size=-2>&nbsp;&#8226;&nbsp;<a href=/advanced_search?hl=en>Advanced&nbsp;Search</a><br>&nbsp;&#8226;&nbsp;<a href=/preferences?hl=en>Preferences</a><br>&nbsp;&#8226;&nbsp;<a href=/language_tools?hl=en>Language Tools</a></font></td></tr></table></form><br>\n"+
		"<br><font size=-1><a href=\"/ads/\">Advertise&nbsp;with&nbsp;Us</a> - <a href=\"/services/\">Search&nbsp;Solutions</a> - <a href=\"/options/\">Services&nbsp;&amp;&nbsp;Tools</a> - <a href=/about.html>Jobs,&nbsp;Press,&nbsp;&amp;&nbsp;Help</a><span id=hp style=\"behavior:url(#default#homepage)\"></span>\n"+
		"<script>\n"+
		"if (!hp.isHomePage('http://www.google.com/')) {document.write(\"<p><a href=\"/mgyhp.html\" onClick=\"style.behavior='url(#default#homepage)';setHomePage('http://www.google.com/');\">Make Google Your Homepage!</a>\");}\n"+
		"</script></font>\n"+
		"<p><font size=-2>&copy;2002 Google</font><font size=-2> - Searching 3,083,324,652 web pages</font></center></body></html>\n"
		);		
		parser.registerScanners();
		int i = 0;
		Vector collectionVector = new Vector();

		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			HTMLNode node = e.nextHTMLNode();
			node.collectInto(collectionVector,HTMLLinkTag.LINK_TAG_FILTER);
		}
		assertEquals("Size of collection vector should be 11",11,collectionVector.size());
		// All items in collection vector should be links
		for (Enumeration e = collectionVector.elements();e.hasMoreElements();) {
			HTMLNode node = (HTMLNode)e.nextElement();
			assertTrue("Only links should have been parsed",node instanceof HTMLLinkTag);
		}
	}
	public void testImageCollection() throws HTMLParserException {
		createParser(
		"<html>\n"+
		"<head>\n"+
			"<meta name=\"generator\" content=\"Created Using Yahoo! PageBuilder 2.60.24\">\n"+
		"</head>\n"+
		"<body bgcolor=\"#FFFFFF\" link=\"#0000FF\" vlink=\"#FF0000\" text=\"#000000\"\n"+
		" onLoad=\"window.onresize=new Function('if (navigator.appVersion==\'Netscape\') history.go(0);');\">\n"+
		"<div id=\"layer0\" style=\"position:absolute;left:218;top:40;width:240;height:26;\">\n"+
		"<table width=240 height=26 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><b><font size=\"+2\"><span style=\"font-size:24\">NISHI-HONGWAN-JI</span></font></b></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer1\" style=\"position:absolute;left:75;top:88;width:542;height:83;\">\n"+
		"<table width=542 height=83 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><span style=\"font-size:14\">The Nihi Hongwanj-ji temple is very traditional, very old, and very beautiful. This is the place that we stayed on our first night in Kyoto. We then attended the morning prayer ceremony, at 6:30 am. Staying here costed us 7,500 yen, which was inclusive of dinner and breakfast, and usage of the o-furo (public bath). Felt more like a luxury hotel than a temple.</span></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer2\" style=\"position:absolute;left:144;top:287;width:128;height:96;\">\n"+
		"<table width=128 height=96 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><a href=\"nishi-hongwanji1.html\"><img height=96 width=128 src=\"nishi-hongwanji1-thumb.jpg\" border=0 ></a></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer3\" style=\"position:absolute;left:415;top:285;width:128;height:96;\">\n"+
		"<table width=128 height=96 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><a href=\"nishi-hongwanji3.html\"><img height=96 width=128 src=\"nishi-hongwanji2-thumb.jpg\" border=0 ></a></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer4\" style=\"position:absolute;left:414;top:182;width:128;height:96;\">\n"+
		"<table width=128 height=96 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><a href=\"higashi-hongwanji.html\"><img height=96 width=128 src=\"higashi-hongwanji-thumb.jpg\" border=0 ></a></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer5\" style=\"position:absolute;left:78;top:396;width:530;height:49;\">\n"+
		"<table width=530 height=49 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><span style=\"font-size:14\">Click on the pictures to see the full-sized versions. The picture at the top right corner is taken in Higashi-Hongwanji. Nishi means west, and Higashi means east. These two temples are adjacent to each other and represent two different Buddhist sects.</span></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer6\" style=\"position:absolute;left:143;top:180;width:128;height:102;\">\n"+
		"<table width=128 height=102 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><a href=\"nishi-hongwanji4.html\"><img height=102 width=128 src=\"nishi-hongwanji4-thumb.jpg\" border=0 ></a></td>\n"+
		"</tr></table></div>\n"+
		"<div id=\"layer7\" style=\"position:absolute;left:280;top:235;width:124;height:99;\">\n"+
		"<table width=124 height=99 border=0 cellpadding=0 cellspacing=0><tr valign=\"top\">\n"+
		"<td><a href=\"nishi-hongwanji-lodging.html\"><img height=99 width=124 src=\"nishi-hongwanji-lodging-thumb.jpg\" border=0 ></a></td>\n"+
		"</tr></table></div>\n"+
		"</body>\n"+
		"</html>");		
		parser.registerScanners();
		int i = 0;
		Vector collectionVector = new Vector();

		for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
			HTMLNode node = e.nextHTMLNode();
			node.collectInto(collectionVector,HTMLImageTag.IMAGE_TAG_FILTER);
		}
		assertEquals("Size of collection vector should be 5",5,collectionVector.size());
		// All items in collection vector should be links
		for (Enumeration e = collectionVector.elements();e.hasMoreElements();) {
			HTMLNode node = (HTMLNode)e.nextElement();
			assertTrue("Only images should have been parsed",node instanceof HTMLImageTag);
		}
	}
	
}
