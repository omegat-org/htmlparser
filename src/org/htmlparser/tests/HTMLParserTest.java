// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

public class HTMLParserTest extends HTMLParserTestCase {

	public HTMLParserTest(String name) {
		super(name);
	}
	public void testElements() throws Exception {
		StringBuffer hugeData = new StringBuffer();
		for (int i=0;i<5001;i++) hugeData.append('a');
		createParser(hugeData.toString());
		int i = 0;
		for (NodeIterator e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextNode();
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
			throw new ParserException("You must be offline! This test needs you to be connected to the internet.",e);
		}
		parser.getReader().mark(5000);

		HTMLNode [] node = new HTMLNode[500];
		int i = 0;
		for (NodeIterator e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextNode();
		}
		int cnt = i;
		parser.getReader().reset();
		// Now try getting the elements again
		i = 0;
		for (NodeIterator e = parser.elements();e.hasMoreNodes();)
		{
			node[i++] = e.nextNode();
		}
		assertEquals("There should be "+cnt+" nodes identified (second call to parser.elements())",cnt,i);
	}	
	
	/**
     * Test the HTMLParser(URLConnection) constructor.
	 * This testcase needs you to be online.
     * Based on the form at Canada Post <code>http://www.canadapost.ca/tools/pcl/bin/default-e.asp</code>:
     * <pre>
     * &lt;form NAME="SearchQuick" method="POST" action="cp_search_response-e.asp"
     * 	onSubmit="return runSubmit();"&gt;
     * 
     * &lt;!-- begin test hidden field code --&gt;
     *   &lt;input TYPE="Hidden" NAME="app_language" value="english"&gt;
     * 
     *   &lt;input TYPE="Hidden" NAME="app_response_start_row_number" value="1"&gt;
     *   &lt;input TYPE="Hidden" NAME="app_response_rows_max" value="9"&gt;
     * 
     *   &lt;input TYPE="Hidden" NAME="app_source" value="quick"&gt;
     *   &lt;input TYPE="Hidden" NAME="query_source" value="q"&gt;
     * 
     *   &lt;input TYPE="Hidden" NAME="name" value&gt;
     *   &lt;input TYPE="Hidden" NAME="postal_code" value&gt;
     *   &lt;input TYPE="Hidden" NAME="directory_area_name" value&gt;
     * 
     *   &lt;input TYPE="Hidden" NAME="delivery_mode" value&gt;
     *   &lt;input TYPE="Hidden" NAME="Suffix" value&gt;
     * 
     *   &lt;input TYPE="Hidden" NAME="street_direction" value&gt;
     *   &lt;input TYPE="Hidden" NAME="installation_type" value&gt;
     *   &lt;input TYPE="Hidden" NAME="delivery_number" value&gt;
     *   &lt;input TYPE="Hidden" NAME="installation_name" value&gt;
     *   &lt;input TYPE="Hidden" NAME="unit_number" value&gt;
     * 
     *   &lt;input TYPE="Hidden" NAME="app_state" value="production"&gt;
     * &lt;!-- end test hidden field code --&gt;
     * 
     * &lt;p&gt;
     *   &lt;table border="0" cellpadding="0" width="90%" cellspacing="0"&gt;
     * 
     *     &lt;tr&gt;
     *       &lt;td  class="tbltitle"&gt; Street Number: &lt;/td&gt;
     *       &lt;td class="tbltitle"&gt; Street Name: &lt;/td&gt;
     *       &lt;td class="tbltitle"&gt; Street Type:&lt;/td&gt;
     *     &lt;/tr&gt;
     *     &lt;tr&gt;
     * 
     *       &lt;td&gt;
     *         &lt;input type="text" name="street_number" size="10" maxlength="10"&gt;
     *       &lt;/td&gt;
     *       &lt;td&gt;
     *         &lt;input type="text" name="street_name" size="30" maxlength="40"&gt;
     *         &lt;input type="hidden" name="street_type" size="30"&gt;
     *       &lt;/td&gt;
     *       &lt;td&gt;&lt;input type="text" name="test" size="10" maxlength="30"&gt;&lt;/td&gt;
     *     &lt;/tr&gt;
     * 
     *   &lt;/table&gt;
     * &lt;p&gt;
     *   &lt;table border="0" cellpadding="0" width="90%" cellspacing="0"&gt;
     *     &lt;tr&gt;
     *       &lt;td class="tbltitle"&gt;
     *         Municipality (City, Town, etc.):
     *       &lt;/td&gt;
     *       &lt;td class="tbltitle"&gt;
     *         Province:
     *       &lt;/td&gt;
     * 
     *     &lt;/tr&gt;
     *     &lt;tr&gt;
     *       &lt;td&gt;
     *         &lt;input type="text" name="city" size="30" maxlength="30"&gt;
     *       &lt;/td&gt;
     *       &lt;td&gt;
     *         &lt;select size="1" name="prov"&gt;
     *           &lt;option selected value="NULL"&gt;Select&lt;/option&gt;&lt;option value="AB"&gt;AB - Alberta&lt;/option&gt;&lt;option value="BC"&gt;BC - British Columbia&lt;/option&gt;&lt;option value="MB"&gt;MB - Manitoba&lt;/option&gt;&lt;option value="NB"&gt;NB - New Brunswick&lt;/option&gt;&lt;option value="NL"&gt;NL - Newfoundland and Labrador&lt;/option&gt;&lt;option value="NS"&gt;NS - Nova Scotia&lt;/option&gt;&lt;option value="NT"&gt;NT - Northwest Territories&lt;/option&gt;&lt;option value="NU"&gt;NU - Nunavut&lt;/option&gt;&lt;option value="ON"&gt;ON - Ontario&lt;/option&gt;&lt;option value="PE"&gt;PE - Prince Edward Island&lt;/option&gt;&lt;option value="QC"&gt;QC - Quebec&lt;/option&gt;&lt;option value="SK"&gt;SK - Saskatchewan&lt;/option&gt;&lt;option value="YT"&gt;YT - Yukon&lt;/option&gt;
     * 
     *         &lt;/select&gt;
     *       &lt;/td&gt;
     *     &lt;/tr&gt;
     *     &lt;tr&gt;
     *       &lt;td height="10"&gt;&amp;nbsp;&lt;/td&gt;
     *       &lt;td&gt;&amp;nbsp;&lt;/td&gt;
     *     &lt;/tr&gt;
     *     &lt;tr&gt;
     *       &lt;td colspan="2" align="right" nowrap&gt;
     * 	   &lt;input type="image" src="images/bb_submit-e.gif" name="Search" border="0" WIDTH="88" HEIGHT="23"&gt;
     *         &amp;nbsp; &lt;a href="#" onclick="javascript:fClearAllFields();"&gt;&lt;img src="images/bb_clear_form-e.gif" name="Clear" border="0" WIDTH="88" HEIGHT="23"&gt;&lt;/a&gt;
     * 	  &lt;/td&gt;
     *     &lt;/tr&gt;
     *   &lt;/table&gt;
     * &lt;p&gt;
     * &lt;/form&gt;
     * </pre>
     * Sumbits the POST and verifies the returned HTML contains an expected value.
	 */
	public void _testPOST() throws Exception
    {   // the form data:
        final String number = "2708";
        final String street = "Kelly";
        final String type = "Avenue";
        final String city = "Ottawa";
        final String province = "ON";
        // the correct answer
        final String postal_code = "K2B 7V4";

		HTMLParser parser;
        URL url;
        HttpURLConnection connection;
        StringBuffer buffer;
        PrintWriter out;
        boolean pass;
        NodeIterator enumeration;
        HTMLNode node;
        HTMLStringNode string;

        try
        {
            url = new URL ("http://www.canadapost.ca/tools/pcl/bin/cp_search_response-e.asp");
             connection = (HttpURLConnection)url.openConnection ();
            connection.setRequestMethod ("POST");
            connection.setRequestProperty ("Referer", "http://www.canadapost.ca/tools/pcl/bin/default-e.asp");
            connection.setDoOutput (true);
            connection.setDoInput (true);
            connection.setUseCaches (false);
            buffer = new StringBuffer (1024);
            buffer.append ("app_language=");
            buffer.append ("english");
            buffer.append ("&");
            buffer.append ("app_response_start_row_number=");
            buffer.append ("1");
            buffer.append ("&");
            buffer.append ("app_response_rows_max=");
            buffer.append ("9");
            buffer.append ("&");
            buffer.append ("app_source=");
            buffer.append ("quick");
            buffer.append ("&");
            buffer.append ("query_source=");
            buffer.append ("q");
            buffer.append ("&");
            buffer.append ("name=");
            buffer.append ("&");
            buffer.append ("postal_code=");
            buffer.append ("&");
            buffer.append ("directory_area_name=");
            buffer.append ("&");
            buffer.append ("delivery_mode=");
            buffer.append ("&");
            buffer.append ("Suffix=");
            buffer.append ("&");
            buffer.append ("street_direction=");
            buffer.append ("&");
            buffer.append ("installation_type=");
            buffer.append ("&");
            buffer.append ("delivery_number=");
            buffer.append ("&");
            buffer.append ("installation_name=");
            buffer.append ("&");
            buffer.append ("unit_numbere=");
            buffer.append ("&");
            buffer.append ("app_state=");
            buffer.append ("production");
            buffer.append ("&");
            buffer.append ("street_number=");
            buffer.append (number);
            buffer.append ("&");
            buffer.append ("street_name=");
            buffer.append (street);
            buffer.append ("&");
            buffer.append ("street_type=");
            buffer.append (type);
            buffer.append ("&");
            buffer.append ("test=");
            buffer.append ("&");
            buffer.append ("city=");
            buffer.append (city);
            buffer.append ("&");
            buffer.append ("prov=");
            buffer.append (province);
            buffer.append ("&");
            buffer.append ("Search=");
            out = new PrintWriter (connection.getOutputStream ());
            out.print (buffer);
            out.close ();
			parser = new HTMLParser (connection);
		}
		catch (Exception e)
        {
			throw new ParserException ("You must be offline! This test needs you to be connected to the internet.", e);
		}

        pass = false;
		for (enumeration = parser.elements (); enumeration.hasMoreNodes ();)
		{
            node = enumeration.nextNode ();
            if (node instanceof HTMLStringNode)
            {
                string = (HTMLStringNode)node;
                if (-1 != string.getText ().indexOf (postal_code))
                    pass = true;
            }
		}
		assertTrue("POST operation failed.", pass);
	}	

    /**
     * Tests the 'from file' HTMLParser constructor.
     */
    public void testFile ()
    {
        String path;
        File file;
        PrintWriter out;
        HTMLParser parser;
        HTMLNode nodes[];
        int i;
        NodeIterator enumeration;
        
        path = System.getProperty ("user.dir");
        if (!path.endsWith (File.separator))
            path += File.separator;
        file = new File (path + "delete_me.html");
        try
        {
            out = new PrintWriter (new FileWriter (file));
            out.println ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println ("<html>");
            out.println ("<head>");
            out.println ("<title>test</title>");
            out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
            out.println ("</head>");
            out.println ("<body>");
            out.println ("This is a test page ");
            out.println ("</body>");
            out.println ("</html>");
            out.close ();
            parser = new HTMLParser (file.getAbsolutePath ());
            nodes = new HTMLNode[30];
            i = 0;
            for (enumeration = parser.elements (); enumeration.hasMoreNodes ();)
            {
                nodes[i] = enumeration.nextNode ();
                i++;
            }
            assertEquals("Expected nodes",12,i);
        }
        catch (Exception e)
        {
            fail (e.toString ());
        }
        finally
        {
            file.delete ();
        }
    }

    /**
     * Test with a HTTP header with a valid charset parameter.
     * Here, ibm.co.jp is an example of a HTTP server that correctly sets the
     * charset in the header to match the content encoding.
     */
    public void _testHTTPCharset ()
    {
		HTMLParser parser;
		try
        {
			parser = new HTMLParser("http://www.ibm.com/jp/", HTMLParser.noFeedback);
			assertTrue("Character set should be Shift_JIS", parser.getEncoding ().equalsIgnoreCase ("Shift_JIS"));
		}
		catch (ParserException e)
        {
            fail ("could not open http://www.ibm.com/jp/");
		}
    }

    /**
     * Test with a HTML header with a charset parameter not matching the HTTP header.
     * Here, www.sony.co.jp is an example of a HTTP server that does not set the
     * charset in the header to match the content encoding. We check that after
     * the enumeration is created, that the charset has changed to the correct value.
     */
    public void _testHTMLCharset ()
    {
		HTMLParser parser;
        NodeIterator enumeration;
        
		try
        {
			parser = new HTMLParser("http://www.sony.co.jp", HTMLParser.noFeedback);
			assertEquals("Character set by default is ISO-8859-1", "ISO-8859-1", parser.getEncoding ());
            enumeration = parser.elements();
			assertTrue("Character set should be Shift_JIS", parser.getEncoding ().equalsIgnoreCase ("Shift_JIS"));
		}
		catch (ParserException e)
        {
            fail ("could not open http://www.sony.co.jp");
		}
    }

	public void testNullUrl() {
		HTMLParser parser;
		try {
			parser = new HTMLParser("http://someoneexisting.com", HTMLParser.noFeedback);
			assertTrue("Should have thrown an exception!",false);
		}
		catch (ParserException e) {
			
		}
	}
	
	public void testURLWithSpaces() throws ParserException{
		HTMLParser parser;
		String url = "http://htmlparser.sourceforge.net/test/This is a Test Page.html";
		
		parser = new HTMLParser(url);
		HTMLNode node [] = new HTMLNode[30];
		int i = 0;
		for (NodeIterator e = parser.elements();e.hasMoreNodes();) {
			node[i] = e.nextNode();
			i++;
			
		}
		assertEquals("Expected nodes",12,i);
	}
	public void testLinkCollection() throws ParserException {
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
		NodeList collectionList = new NodeList();

		for (NodeIterator e = parser.elements();e.hasMoreNodes();) {
			HTMLNode node = e.nextNode();
			node.collectInto(collectionList,LinkTag.LINK_TAG_FILTER);
		}
		assertEquals("Size of collection vector should be 11",11,collectionList.size());
		// All items in collection vector should be links
		for (SimpleNodeIterator e = collectionList.elements();e.hasMoreNodes();) {
			HTMLNode node = e.nextNode();
			assertTrue("Only links should have been parsed",node instanceof LinkTag);
		}
	}
	public void testImageCollection() throws ParserException {
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
		NodeList collectionList = new NodeList();

		for (NodeIterator e = parser.elements();e.hasMoreNodes();) {
			HTMLNode node = e.nextNode();
			node.collectInto(collectionList,ImageTag.IMAGE_TAG_FILTER);
		}
		assertEquals("Size of collection vector should be 5",5,collectionList.size());
		// All items in collection vector should be links
		for (SimpleNodeIterator e = collectionList.elements();e.hasMoreNodes();) {
			HTMLNode node = e.nextNode();
			assertTrue("Only images should have been parsed",node instanceof ImageTag);
		}
	}
	
}
