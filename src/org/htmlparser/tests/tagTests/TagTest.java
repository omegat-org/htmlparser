// HTMLParser Library v1_3_20030316 - A java-based parser for HTML
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

package org.htmlparser.tests.tagTests;

import java.util.Hashtable;

import org.htmlparser.Node;
import org.htmlparser.StringNode;
import org.htmlparser.scanners.DivScanner;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class TagTest extends ParserTestCase
{
	public TagTest(String name) {
		super(name);
	}

	/**
	 * The bug being reproduced is this : <BR>
	 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
	 * vLink=#551a8b&gt;
	 * The above line is incorrectly parsed in that, the BODY tag is not identified.
	 */
	public void testBodyTagBug1() throws ParserException {
		createParser("<BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000\nvLink=#551a8b>");
		parseAndAssertNodeCount(1);
		// The node should be an HTMLTag
		assertTrue("Node should be a HTMLTag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertEquals("Contents of the tag","BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000\r\nvLink=#551a8b",tag.getText());
	}

	/**
	 * The following should be identified as a tag : <BR>
	 * 	&lt;MYTAG abcd\n"+
	 *		"efgh\n"+
	 *		"ijkl\n"+
	 *		"mnop&gt;
	 * Creation date: (6/17/2001 5:27:42 PM)
	 */
	public void testLargeTagBug() throws ParserException {
		createParser(
			"<MYTAG abcd\n"+
			"efgh\n"+
			"ijkl\n"+
			"mnop>"
		);
		parseAndAssertNodeCount(1);
		// The node should be an HTMLTag
		assertTrue("Node should be a HTMLTag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertEquals("Contents of the tag","MYTAG abcd\r\nefgh\r\nijkl\r\nmnop",tag.getText());


	}
	/**
	 * Bug reported by Gordon Deudney 2002-03-15
	 * Nested JSP Tags were not working
	 */
	public void testNestedTags() throws ParserException {
		EndTag etag;
		String s = "input type=\"text\" value=\"<%=\"test\"%>\" name=\"text\"";
		String line = "<"+s+">";
	    createParser(line);
		parseAndAssertNodeCount(1);
		assertTrue("The node found should have been an HTMLTag",node[0] instanceof Tag);
		Tag tag = (Tag) node[0];
		assertEquals("Tag Contents",s,tag.getText());
	}

    /**
     * Test parseParameter method
     * Created by Kaarle Kaila (august 2001)
     * the tag name is here G
     */
    public void testParseParameter3() throws ParserException {
        Tag tag;
        EndTag etag;
        StringNode snode;
        Node node=null;
        String lin1 = "<DIV class=\"userData\" id=\"oLayout\" name=\"oLayout\"></DIV>";
       	createParser(lin1);
    	NodeIterator en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;

        try {

            if (en.hasMoreNodes()) {
                node = en.nextNode();

                tag = (Tag)node;
                h = tag.getAttributes();
				String classValue= (String)h.get("CLASS");
                assertEquals ("The class value should be ","userData",classValue);
            }

        }
        catch (ClassCastException ce) {
            fail("Bad class element = " + node.getClass().getName());
        }
    }

    /**
     * Test parseParameter method
     * Created by Kaarle Kaila (august 2001)
     * the tag name is here A (and should be eaten up by linkScanner)
     */
    public void testParseParameterA() throws ParserException {
        Tag tag;
        EndTag etag;
        StringNode snode;
		Node node=null;
        String lin1 = "<A href=\"http://www.iki.fi/kaila\" myParameter yourParameter=\"Kaarle Kaaila\">Kaarle's homepage</A><p>Paragraph</p>";
       	createParser(lin1);
        NodeIterator en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;

        try {

            if (en.hasMoreNodes()) {
                node = en.nextNode();

                tag = (Tag)node;
                h = tag.getAttributes();
                a = (String)h.get(Tag.TAGNAME);
                href = (String)h.get("HREF");
                myValue = (String)h.get("MYPARAMETER");
                nice = (String)h.get("YOURPARAMETER");
                assertEquals ("Link tag (A)","A",a);
                assertEquals ("href value","http://www.iki.fi/kaila",href);
                assertEquals ("myparameter value","",myValue);
                assertEquals ("yourparameter value","Kaarle Kaaila",nice);
            }
            if (!(node instanceof LinkTag)) {
                // linkscanner has eaten up this piece
                if ( en.hasMoreNodes()) {
                    node = en.nextNode();
                    snode = (StringNode)node;
                    assertEquals("Value of element",snode.getText(),"Kaarle's homepage");
                }

                if (en.hasMoreNodes()) {
                    node = en.nextNode();
                    etag = (EndTag)node;
                    assertEquals("endtag of link",etag.getText(),"A");
                }
            }
            // testing rest
            if (en.hasMoreNodes()) {
                node = en.nextNode();

                tag = (Tag)node;
                assertEquals("following paragraph begins",tag.getText(),"p");
            }
            if (en.hasMoreNodes()) {
                node = en.nextNode();
                snode = (StringNode)node;
                assertEquals("paragraph contents",snode.getText(),"Paragraph");
            }
            if (en.hasMoreNodes()) {
                node = en.nextNode();
                etag = (EndTag)node;
                assertEquals("paragrapg endtag",etag.getText(),"p");
            }

        }
        catch (ClassCastException ce) {
            fail("Bad class element = " + node.getClass().getName());
        }
    }

    /**
     * Test parseParameter method
     * Created by Kaarle Kaila (august 2001)
     * the tag name is here G
     */
    public void testParseParameterG() throws ParserException{
        Tag tag;
        EndTag etag;
        StringNode snode;
        Node node=null;
        String lin1 = "<G href=\"http://www.iki.fi/kaila\" myParameter yourParameter=\"Kaila\">Kaarle's homepage</G><p>Paragraph</p>";
       	createParser(lin1);
        NodeIterator en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;

        try {

            if (en.hasMoreNodes()) {
                node = en.nextNode();

                tag = (Tag)node;
                h = tag.getAttributes();
                a = (String)h.get(Tag.TAGNAME);
                href = (String)h.get("HREF");
                myValue = (String)h.get("MYPARAMETER");
                nice = (String)h.get("YOURPARAMETER");
                assertEquals ("The tagname should be G",a,"G");
                assertEquals ("Check the http address",href,"http://www.iki.fi/kaila");
                assertEquals ("myValue is empty",myValue,"");
                assertEquals ("The second parameter value",nice,"Kaila");
            }
            if (en.hasMoreNodes()) {
                node = en.nextNode();
                snode = (StringNode)node;
                assertEquals("The text of the element",snode.getText(),"Kaarle's homepage");
            }

            if (en.hasMoreNodes()) {
                node = en.nextNode();
                etag = (EndTag)node;
                assertEquals("Endtag is G",etag.getText(),"G");
            }
            // testing rest
            if (en.hasMoreNodes()) {
                node = en.nextNode();

                tag = (Tag)node;
                assertEquals("Follow up by p-tag",tag.getText(),"p");
            }
            if (en.hasMoreNodes()) {
                node = en.nextNode();
                snode = (StringNode)node;
                assertEquals("Verify the paragraph text",snode.getText(),"Paragraph");
            }
            if (en.hasMoreNodes()) {
                node = en.nextNode();
                etag = (EndTag)node;
                assertEquals("Still patragraph endtag",etag.getText(),"p");
            }

        } catch (ClassCastException ce) {
            fail("Bad class element = " + node.getClass().getName());
        }
    }


   /**
    * Test parseParameter method
    * Created by Kaarle Kaila (august 2002)
    * the tag name is here A (and should be eaten up by linkScanner)
    * Tests elements where = sign is surrounded by spaces
    */
    public void testParseParameterSpace() throws ParserException{
        Tag tag;
        EndTag etag;
        StringNode snode;
        Node node=null;
        String lin1 = "<A yourParameter = \"Kaarle\">Kaarle's homepage</A>";
       	createParser(lin1);
        NodeIterator en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;

        try {

            if (en.hasMoreNodes()) {
                node = en.nextNode();

                tag = (Tag)node;
                h = tag.getAttributes();
                a = (String)h.get(Tag.TAGNAME);
                nice = (String)h.get("YOURPARAMETER");
                assertEquals ("Link tag (A)",a,"A");
                assertEquals ("yourParameter value","Kaarle",nice);
            }
            if (!(node instanceof LinkTag)) {
                // linkscanner has eaten up this piece
                if ( en.hasMoreNodes()) {
                    node = en.nextNode();
                    snode = (StringNode)node;
                    assertEquals("Value of element",snode.getText(),"Kaarle's homepage");
                }

                if (en.hasMoreNodes()) {
                    node = en.nextNode();
                    etag = (EndTag)node;
                    assertEquals("Still patragraph endtag",etag.getText(),"A");
                }
            }
            // testing rest

        } catch (ClassCastException ce) {
            fail("Bad class element = " + node.getClass().getName());
        }
    }

    /**
     * Reproduction of a bug reported by Annette Doyle
     * This is actually a pretty good example of dirty html - we are in a fix
     * here, bcos the font tag (the first one) has an erroneous inverted comma. In Tag,
     * we ignore anything in inverted commas, and dont if its outside. This kind of messes
     * up our parsing almost completely.
     */
    public void testStrictParsing() throws ParserException {
		String testHTML = "<div align=\"center\"><font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"><a href=\"/index.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Home</font></a>\n"+
        "<a href=\"/cia/notices.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Notices</font></a>\n"+
        "<a href=\"/cia/notices.html#priv\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Privacy</font></a>\n"+
        "<a href=\"/cia/notices.html#sec\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Security</font></a>\n"+
        "<a href=\"/cia/contact.htm\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Contact Us</font></a>\n"+
        "<a href=\"/cia/sitemap.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Site Map</font></a>\n"+
        "<a href=\"/cia/siteindex.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Index</font></a>\n"+
        "<a href=\"/search\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Search</font></a>\n"+
        "</font></div>";

		createParser(testHTML,"http://www.cia.gov");
		// Register the image scanner
		parser.registerScanners();
		parser.addScanner(new DivScanner());
		parseAndAssertNodeCount(1);
		// Check the tags
		Tag tag = (Tag)node[0];
		assertEquals("DIV Tag expected","div align=\"center\"",tag.getText());
		Div div = (Div)tag;
		Tag fontTag = (Tag)div.children().nextNode();
		assertEquals("Second tag should be corrected","font face=\"Arial,helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"",fontTag.getText());
		// Try to parse the parameters from this tag.
		Hashtable table = fontTag.getAttributes();
		assertNotNull("Parameters table",table);
		assertEquals("font sans-serif parameter","sans-serif",table.get("SANS-SERIF"));
		assertEquals("font face parameter","Arial,helvetica,",table.get("FACE"));
    }

	public void testToHTML() throws ParserException {
		String testHTML = new String(
			"<MYTAG abcd\n"+
			"efgh\n"+
			"ijkl\n"+
			"mnop>\n"+
			"<TITLE>Hello</TITLE>\n"+
			"<A HREF=\"Hello.html\">Hey</A>"
		);
		createParser(testHTML);
		parseAndAssertNodeCount(7);
		// The node should be an HTMLTag
		assertTrue("1st Node should be a HTMLTag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertStringEquals("toHTML()","<MYTAG EFGH=\"\" ABCD=\"\" MNOP=\"\" IJKL=\"\">",tag.toHtml());
		assertTrue("2nd Node should be a HTMLTag",node[1] instanceof Tag);
		assertTrue("5th Node should be a HTMLTag",node[4] instanceof Tag);
		tag = (Tag)node[1];
		assertEquals("Raw String of the tag","<TITLE>",tag.toHtml());
		tag = (Tag)node[4];
		assertEquals("Raw String of the tag","<A HREF=\"Hello.html\">",tag.toHtml());
	}


    /**
     * Test parseParameter method
     * Created by Kaarle Kaila (22 Oct 2001)
     * This test just wants the text in the element
     */
    public void testWithoutParseParameter() throws ParserException{
        Tag tag;
        EndTag etag;
        StringNode snode;
        Node node=null;
        String testHTML = "<A href=\"http://www.iki.fi/kaila\" myParameter yourParameter=\"Kaarle\">Kaarle's homepage</A><p>Paragraph</p>";
       	createParser(testHTML);
        NodeIterator en = parser.elements();
        String result="";
        try {
            while (en.hasMoreNodes()) {
                node = en.nextNode();
                result += node.toHtml();
            }
            String expected = "<A YOURPARAMETER=\"Kaarle\" MYPARAMETER=\"\" HREF=\"http://www.iki.fi/kaila\">Kaarle's homepage</A><P>Paragraph</P>";
            assertStringEquals("Check collected contents to original", expected, result);
        } catch (ClassCastException ce) {
            fail("Bad class element = " + node.getClass().getName());
        }
    }

    /**
    * Test parseParameter method
    * Created by Kaarle Kaila (09 Jan 2003)
    * This test just wants the text in the element
    */
   public void testEmptyTagParseParameter() throws ParserException{
       Tag tag;
       EndTag etag;
       StringNode snode;
       Node node=null;
       String testHTML = "<INPUT name=\"foo\" value=\"foobar\" type=\"text\" />";

       createParser(testHTML);
       NodeIterator en = parser.elements();
       String result="";
       try {
           while (en.hasMoreNodes()) {
               node = en.nextNode();
               result = node.toHtml();
           }
           String expected = "<INPUT VALUE=\"foobar\" NAME=\"foo\" TYPE=\"text\"/>";
           assertStringEquals("Check collected contents to original", expected, result);
       } catch (ClassCastException ce) {

           fail("Bad class element = " + node.getClass().getName());
       }
    }


    public void testStyleSheetTag() throws ParserException{
     	String testHTML1 = new String("<link rel src=\"af.css\"/>");
		createParser(testHTML1,"http://www.google.com/test/index.html");
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a tag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertEquals("StyleSheet Source","af.css",tag.getAttribute("src"));
    }

    /**
     * Bug report by Cedric Rosa, causing null pointer exceptions when encountering a broken tag,
     * and if this has no further lines to parse
     */
    public void testBrokenTag() throws ParserException{
     	String testHTML1 = new String("<br");
		createParser(testHTML1);
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a tag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertEquals("Node contents","br",tag.getText());
    }

    public void testTagInsideTag() throws ParserException {
    	String testHTML = new String("<META name=\"Hello\" value=\"World </I>\">");
		createParser(testHTML);
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a tag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertEquals("Node contents","META name=\"Hello\" value=\"World </I>\"",tag.getText());
		assertEquals("Meta Content","World </I>",tag.getAttribute("value"));

    }

    public void testIncorrectInvertedCommas() throws ParserException {
    	String testHTML = new String("<META NAME=\"Author\" CONTENT = \"DORIER-APPRILL E., GERVAIS-LAMBONY P., MORICONI-EBRARD F., NAVEZ-BOUCHANINE F.\"\">");
		createParser(testHTML);
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a tag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertStringEquals("Node contents","META NAME=\"Author\" CONTENT=\"DORIER-APPRILL E., GERVAIS-LAMBONY P., MORICONI-EBRARD F., NAVEZ-BOUCHANINE F.\"",tag.getText());
		Hashtable table = tag.getAttributes();
		assertEquals("Meta Content","DORIER-APPRILL E., GERVAIS-LAMBONY P., MORICONI-EBRARD F., NAVEZ-BOUCHANINE F.",tag.getAttribute("CONTENT"));

    }

    public void testIncorrectInvertedCommas2() throws ParserException {
    	String testHTML = new String("<META NAME=\"Keywords\" CONTENT=Moscou, modernisation, politique urbaine, spécificités culturelles, municipalité, Moscou, modernisation, urban politics, cultural specificities, municipality\">");
		createParser(testHTML);
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a tag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertStringEquals("Node contents","META NAME=\"Keywords\" CONTENT=\"Moscou, modernisation, politique urbaine, spécificités culturelles, municipalité, Moscou, modernisation, urban politics, cultural specificities, municipality\"",tag.getText());
    }

 	public void testIncorrectInvertedCommas3() throws ParserException {
    	String testHTML = new String("<meta name=\"description\" content=\"Une base de données sur les thèses de g\"ographie soutenues en France \">");
		createParser(testHTML);
		parseAndAssertNodeCount(1);
		assertTrue("Node should be a tag",node[0] instanceof Tag);
		Tag tag = (Tag)node[0];
		assertEquals("Node contents","meta name=\"description\" content=\"Une base de données sur les thèses de gographie soutenues en France\"",tag.getText());
    }

	/**
	 * Bug reported by John Zook, if there is an empty tag,
	 * then Tag shouldnt pass it down to the scanners.
	 */
	public void testEmptyTag() throws ParserException {
		String testHTML = "<html><body><>text</body></html>";
		createParser(testHTML);
		parser.registerScanners();
		parseAndAssertNodeCount(6);
		assertTrue("Third node should be an HTMLtag",node[2] instanceof Tag);
		Tag htmlTag = (Tag)node[2];
		assertEquals("Third node should be empty","",htmlTag.getText());
	}

	/**
	 * Bug reported by John Zook, if there is an empty tag,
	 * then Tag shouldnt pass it down to the scanners.
	 */
	public void testEmptyTag2() throws ParserException {
		String testHTML = "<html><body>text<></body></html>";
		createParser(testHTML);
		parser.registerScanners();
		parseAndAssertNodeCount(6);
		assertTrue("Fourth node should be an HTMLtag",node[3] instanceof Tag);
		Tag htmlTag = (Tag)node[3];
		assertEquals("Fourth node should be empty","",htmlTag.getText());
	}

	public void testAttributesReconstruction() throws ParserException {
		String testHTML = "<TEXTAREA name=\"JohnDoe\" ></TEXTAREA>";
		createParser(testHTML);
		parseAndAssertNodeCount(2);
		assertTrue("First node should be an HTMLtag",node[0] instanceof Tag);
		Tag htmlTag = (Tag)node[0];
		String expectedHTML = "<TEXTAREA NAME=\"JohnDoe\">";
		assertStringEquals("Expected HTML",expectedHTML,htmlTag.toHtml());
	}
	public void testIgnoreState() throws ParserException {
		String testHTML = "<A \n"+
		"HREF=\"/a?b=c>d&e=f&g=h&i=http://localhost/Testing/Report1.html\">20020702 Report 1</A>";
		createParser(testHTML);
		Node node = Tag.find(parser.getReader(),testHTML,0);
		assertTrue("Node should be a tag",node instanceof Tag);
		Tag tag = (Tag)node;
		String href = tag.getAttribute("HREF");
		assertStringEquals("Resolved Link","/a?b=c>d&e=f&g=h&i=http://localhost/Testing/Report1.html",href);

	}
    public void testExtractWord() {
    	String line = "Abc DEF GHHI";
    	String word = Tag.extractWord(line);
    	assertEquals("Word expected","ABC",Tag.extractWord(line));
    	String line2= "%\n ";
    	assertEquals("Word expected for line 2","%",Tag.extractWord(line2));
    	String line3 = "%\n%>";
		assertEquals("Word expected for line 3","%",Tag.extractWord(line3));
    	String line4 = "%=abc%>";
		assertEquals("Word expected for line 4","%",Tag.extractWord(line4));
		String line5 = "OPTION";
		assertEquals("Word expected for line 5","OPTION",Tag.extractWord(line5));

    }
}
