// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Derrick Oswald
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.tests.lexerTests;

import java.util.Hashtable;
import java.util.Vector;
import org.htmlparser.Node;

import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.lexer.nodes.PageAttribute;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SpecialHashtable;

public class AttributeTests extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.lexerTests.AttributeTests", "AttributeTests");
    }

    private static final boolean JSP_TESTS_ENABLED = false;
    private Tag tag;
    private Hashtable table;

    public AttributeTests (String name) {
        super(name);
    }

    public void getParameterTableFor(String tagContents)
    {
        getParameterTableFor (tagContents, false);
    }

    public void getParameterTableFor(String tagContents, boolean dump)
    {
        String html;
        NodeIterator iterator;
        Node node;
        Vector attributes;

        html = "<" + tagContents + ">";
        createParser (html);
        parser.setNodeFactory (new PrototypicalNodeFactory (true));
        try
        {
            iterator = parser.elements ();
            node = iterator.nextNode ();
            if (node instanceof Tag)
            {
                tag = (Tag)node;
                attributes = tag.getAttributesEx ();
                if (dump)
                {
                    for (int i = 0; i < attributes.size (); i++)
                    {
                        System.out.print ("Attribute #" + i);
                        Attribute attribute = (Attribute)attributes.elementAt (i);
                        if (null != attribute.getName ())
                            System.out.print (" Name: '" + attribute.getName () + "'");
                        if (null != attribute.getAssignment ())
                            System.out.print (" Assignment: '" + attribute.getAssignment () + "'");
                        if (0 != attribute.getQuote ())
                            System.out.print (" Quote: " + attribute.getQuote ());
                        if (null != attribute.getValue ())
                            System.out.print (" Value: '" + attribute.getValue () + "'");
                        System.out.println ();
                    }
                    System.out.println ();
                }
                table = tag.getAttributes ();
            }
            else
                table = null;
            String string = node.toHtml ();
            assertEquals ("toHtml differs", html, string);
            assertTrue ("shouldn't be any more nodes", !iterator.hasMoreNodes ());
        }
        catch (ParserException pe)
        {
            fail (pe.getMessage ());
        }
    }

    /**
     * Test constructors.
     */
    public void testConstructors ()
    {
        Vector attributes;
        Tag tag;
        String html;

        attributes = new Vector ();
         // String, null
        attributes.add (new Attribute ("wombat", null));
        // String
        attributes.add (new Attribute (" "));
        // String, String
        attributes.add (new Attribute ("label", "The civil war."));
        attributes.add (new Attribute (" "));
        // String, String, String
        attributes.add (new Attribute ("frameborder", "= ", "no"));
        attributes.add (new Attribute (" "));
        // String String, String, char
        attributes.add (new Attribute ("name", "=", "topFrame", '"'));
        tag = new Tag (null, 0, 0, attributes);
        html = "<wombat label=\"The civil war.\" frameborder= no name=\"topFrame\">";
        assertStringEquals ("tag contents", html, tag.toHtml ());
    }

    /**
     * Test bean properties.
     */
    public void testProperties ()
    {
        Attribute attribute;
        Attribute space;
        Vector attributes;
        Tag tag;
        String html;

        attributes = new Vector ();
        attribute = new Attribute ();
        attribute.setName ("wombat");
        assertTrue ("should be standalone", attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should not be valued", !attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
        attributes.add (attribute);
        space = new Attribute ();
        space.setValue (" ");
        assertTrue ("should not be standalone", !space.isStandAlone ());
        assertTrue ("should be whitespace", space.isWhitespace ());
        assertTrue ("should be valued", space.isValued ());
        assertTrue ("should not be empty", !space.isEmpty ());
        attributes.add (space);
        attribute = new Attribute ();
        attribute.setName ("label");
        attribute.setAssignment ("=");
        attribute.setRawValue ("The civil war.");
        assertTrue ("should not be standalone", !attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should be valued", attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
        attributes.add (attribute);
        attributes.add (space);
        attribute = new Attribute ();
        attribute.setName ("frameborder");
        attribute.setAssignment ("= ");
        attribute.setRawValue ("no");
        attributes.add (attribute);
        attributes.add (space);
        attribute = new Attribute ();
        attribute.setName ("name");
        attribute.setAssignment ("=");
        attribute.setValue ("topFrame");
        attribute.setQuote ('"');
        assertTrue ("should not be standalone", !attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should be valued", attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
        attributes.add (attribute);
        tag = new Tag (null, 0, 0, attributes);
        html = "<wombat label=\"The civil war.\" frameborder= no name=\"topFrame\">";
        assertStringEquals ("tag contents", html, tag.toHtml ());
    }

    /**
     * Test constructors.
     */
    public void testConstructors2 ()
    {
        Vector attributes;
        Tag tag;
        String html;

        attributes = new Vector ();
         // String, null
        attributes.add (new PageAttribute ("wombat", null));
        // String
        attributes.add (new PageAttribute (" "));
        // String, String
        attributes.add (new PageAttribute ("label", "The civil war."));
        attributes.add (new PageAttribute (" "));
        // String, String, String
        attributes.add (new PageAttribute ("frameborder", "= ", "no"));
        attributes.add (new PageAttribute (" "));
        // String String, String, char
        attributes.add (new PageAttribute ("name", "=", "topFrame", '"'));
        tag = new Tag (null, 0, 0, attributes);
        html = "<wombat label=\"The civil war.\" frameborder= no name=\"topFrame\">";
        assertStringEquals ("tag contents", html, tag.toHtml ());
    }

    /**
     * Test bean properties.
     */
    public void testProperties2 ()
    {
        Attribute attribute;
        Attribute space;
        Vector attributes;
        Tag tag;
        String html;

        attributes = new Vector ();
        attribute = new PageAttribute ();
        attribute.setName ("wombat");
        assertTrue ("should be standalone", attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should not be valued", !attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
        attributes.add (attribute);
        space = new PageAttribute ();
        space.setValue (" ");
        assertTrue ("should not be standalone", !space.isStandAlone ());
        assertTrue ("should be whitespace", space.isWhitespace ());
        assertTrue ("should be valued", space.isValued ());
        assertTrue ("should not be empty", !space.isEmpty ());
        attributes.add (space);
        attribute = new PageAttribute ();
        attribute.setName ("label");
        attribute.setAssignment ("=");
        attribute.setRawValue ("The civil war.");
        assertTrue ("should not be standalone", !attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should be valued", attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
        attributes.add (attribute);
        attributes.add (space);
        attribute = new PageAttribute ();
        attribute.setName ("frameborder");
        attribute.setAssignment ("= ");
        attribute.setRawValue ("no");
        assertTrue ("should not be standalone", !attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should be valued", attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
         attributes.add (attribute);
        attributes.add (space);
        attribute = new PageAttribute ();
        attribute.setName ("name");
        attribute.setAssignment ("=");
        attribute.setValue ("topFrame");
        attribute.setQuote ('"');
        assertTrue ("should not be standalone", !attribute.isStandAlone ());
        assertTrue ("should not be whitespace", !attribute.isWhitespace ());
        assertTrue ("should be valued", attribute.isValued ());
        assertTrue ("should not be empty", !attribute.isEmpty ());
         attributes.add (attribute);
        tag = new Tag (null, 0, 0, attributes);
        html = "<wombat label=\"The civil war.\" frameborder= no name=\"topFrame\">";
        assertStringEquals ("tag contents", html, tag.toHtml ());
    }

    /**
     * Test simple value.
     */
    public void testParseParameters() {
        getParameterTableFor("a b = \"c\"");
        assertEquals("Value","c",table.get("B"));
    }

    /**
     * Test quote value.
     */
    public void testParseTokenValues() {
        getParameterTableFor("a b = \"'\"");
        assertEquals("Value","'",table.get("B"));
    }

    /**
     * Test empty value.
     */
    public void testParseEmptyValues() {
        getParameterTableFor("a b = \"\"");
        assertEquals("Value","",table.get("B"));
    }

    /**
     * Test no equals or whitespace.
     * This might be reason for another rule, since another interpretation
     * would have an attribute called B with a value of "C".
     */
    public void testParseMissingEqual() {
        getParameterTableFor("a b\"c\"");
        assertEquals("ValueB",null,table.get("B"));
        assertTrue("NameC",table.containsKey("B\"C\""));
    }

    /**
     * Test multiple attributes.
     */
    public void testTwoParams(){
        getParameterTableFor("PARAM NAME=\"Param1\" VALUE=\"Somik\"");
        assertEquals("Param1","Param1",table.get("NAME"));
        assertEquals("Somik","Somik",table.get("VALUE"));
    }

    /**
     * Test unquoted attributes.
     */
    public void testPlainParams(){
        getParameterTableFor("PARAM NAME=Param1 VALUE=Somik");
        assertEquals("Param1","Param1",table.get("NAME"));
        assertEquals("Somik","Somik",table.get("VALUE"));
    }

    /**
     * Test standalone attribute.
     */
    public void testValueMissing() {
        getParameterTableFor("INPUT type=\"checkbox\" name=\"Authorize\" value=\"Y\" checked");
        assertEquals("Name of Tag","INPUT",table.get(SpecialHashtable.TAGNAME));
        assertEquals("Type","checkbox",table.get("TYPE"));
        assertEquals("Name","Authorize",table.get("NAME"));
        assertEquals("Value","Y",table.get("VALUE"));
        assertEquals("Checked",null,table.get("CHECKED"));
    }

    /**
     * This is a simulation of a bug reported by Dhaval Udani - wherein
     * a space before the end of the tag causes a problem - there is a key
     * in the table with just a space in it and an empty value
     */
    public void testIncorrectSpaceKeyBug() {
        getParameterTableFor("TEXTAREA name=\"Remarks\" ");
        // There should only be two keys..
        assertEquals("There should only be two keys",2,table.size());
        // The first key is name
        String key1 = "NAME";
        String value1 = (String)table.get(key1);
        assertEquals("Expected value 1", "Remarks",value1);
        String key2 = SpecialHashtable.TAGNAME;
        assertEquals("Expected Value 2","TEXTAREA",table.get(key2));
    }

    /**
     * Test empty attribute.
     */
    public void testNullTag(){
        getParameterTableFor("INPUT type=");
        assertEquals("Name of Tag","INPUT",table.get(SpecialHashtable.TAGNAME));
        assertEquals("Type","",table.get("TYPE"));
    }

    /**
     * Test attribute containing an equals sign.
     */
    public void testAttributeWithSpuriousEqualTo() {
        getParameterTableFor(
            "a class=rlbA href=/news/866201.asp?0sl=-32"
        );
        assertStringEquals(
            "href",
            "/news/866201.asp?0sl=-32",
            (String)table.get("HREF")
        );
    }

    /**
     * Test attribute containing a question mark.
     */
    public void testQuestionMarksInAttributes() {
        getParameterTableFor(
            "a href=\"mailto:sam@neurogrid.com?subject=Site Comments\""
        );
        assertStringEquals(
            "href",
            "mailto:sam@neurogrid.com?subject=Site Comments",
            (String)table.get("HREF")
        );
        assertStringEquals(
            "tag name",
            "A",
            (String)table.get(SpecialHashtable.TAGNAME)
        );
    }

    /**
     * Check that an empty tag is considered a string node.
     * Believe it or not Moi (vincent_aumont) wants htmlparser to parse a text file
     * containing something that looks nearly like a tag:
     * <pre>
     * "basic_string&lt;char, string_char_traits&lt;char&gt;, &lt;&gt;&gt;::basic_string()"
     * </pre>
     * This was throwing a null pointer exception when the empty &lt;&gt; was encountered.
     * Bug #725420 NPE in StringBean.visitTag
     **/
    public void testEmptyTag () {
        getParameterTableFor("");
        assertNull ("<> is not a tag",table);
    }

    /**
     * Test attributes when they contain scriptlets.
     * Submitted by Cory Seefurth
     * See also feature request #725376 Handle script in attributes.
     */
    public void testJspWithinAttributes() {
        if (JSP_TESTS_ENABLED)
        {
            getParameterTableFor(
                "a href=\"<%=Application(\"sURL\")%>/literature/index.htm"
            );
            assertStringEquals(
                "href",
                "<%=Application(\"sURL\")%>/literature/index.htm",
                (String)table.get("HREF")
            );
        }
    }

    /**
     * Test Script in attributes.
     * See feature request #725376 Handle script in attributes.
     */
    public void testScriptedTag () {
        getParameterTableFor("body onLoad=defaultStatus=''");
        String name = (String)table.get(SpecialHashtable.TAGNAME);
        assertNotNull ("No Tag.TAGNAME", name);
        assertStringEquals("tag name parsed incorrectly", "BODY", name);
        String value = (String)table.get ("ONLOAD");
        assertStringEquals ("parameter parsed incorrectly", "defaultStatus=''", value);
    }

    /**
     * Test that stand-alone attributes are kept that way, rather than being
     * given empty values.
     * -Joe Robins, 6/19/03
     */
    public void testStandaloneAttribute ()
    {
        getParameterTableFor ("INPUT DISABLED");
        assertTrue ("Standalone attribute has no entry in table keyset",table.containsKey("DISABLED"));
        assertNull ("Standalone attribute has non-null value",(String)table.get("DISABLED"));
    }

    /**
     * Test missing value.
     */
    public void testMissingAttribute ()
    {
        getParameterTableFor ("INPUT DISABLED=");
        assertTrue ("Attribute has no entry in table keyset",table.containsKey("DISABLED"));
        assertEquals ("Attribute has non-blank value","",(String)table.get("DISABLED"));
    }

    /**
     * Test Rule 1.
     * See discussion in Bug#891058 Bug in lexer. regarding alternate interpretations.
     */
    public void testRule1 ()
    {
        getParameterTableFor ("tag att = other=fred");
        assertTrue ("Attribute missing", table.containsKey ("ATT"));
        assertEquals ("Attribute has wrong value", "other=fred", (String)table.get ("ATT"));
        assertTrue ("No attribute should be called equal sign", !table.containsKey ("="));
    }

    /**
     * Test Rule 2.
     */
    public void testRule2 ()
    {
        getParameterTableFor ("tag att =value other=fred");
        assertTrue ("Attribute missing", table.containsKey ("ATT"));
        assertEquals ("Attribute has wrong value", "value", (String)table.get ("ATT"));
        assertTrue ("No attribute should be called =value", !table.containsKey ("=VALUE"));
        assertTrue ("Attribute missing", table.containsKey ("OTHER"));
        assertEquals ("Attribute has wrong value", "fred", (String)table.get ("OTHER"));
    }

    /**
     * Test Rule 3.
     */
    public void testRule3 ()
    {
        getParameterTableFor ("tag att= \"value\" other=fred");
        assertTrue ("Attribute missing", table.containsKey ("ATT"));
        assertEquals ("Attribute has wrong value", "value", (String)table.get ("ATT"));
        assertTrue ("No attribute should be called \"value\"", !table.containsKey ("\"VALUE\""));
        assertTrue ("Attribute missing", table.containsKey ("OTHER"));
        assertEquals ("Attribute has wrong value", "fred", (String)table.get ("OTHER"));
    }

    /**
     * Test Rule 4.
     * See discussion in Bug#891058 Bug in lexer. regarding alternate interpretations.
     */
    public void testRule4 ()
    {
        getParameterTableFor ("tag att=\"va\"lue\" other=fred");
        assertTrue ("Attribute missing", table.containsKey ("ATT"));
        assertEquals ("Attribute has wrong value", "va", (String)table.get ("ATT"));
        assertTrue ("No attribute should be called va\"lue", !table.containsKey ("VA\"LUE"));
        assertTrue ("Attribute missing", table.containsKey ("LUE\""));
        assertNull ("Attribute has wrong value", table.get ("LUE\""));
        assertTrue ("Attribute missing", table.containsKey ("OTHER"));
        assertEquals ("Attribute has wrong value", "fred", (String)table.get ("OTHER"));
    }

    /**
     * Test Rule 5.
     * See discussion in Bug#891058 Bug in lexer. regarding alternate interpretations.
     */
    public void testRule5 ()
    {
        getParameterTableFor ("tag att='va'lue' other=fred");
        assertTrue ("Attribute missing", table.containsKey ("ATT"));
        assertEquals ("Attribute has wrong value", "va", (String)table.get ("ATT"));
        assertTrue ("No attribute should be called va'lue", !table.containsKey ("VA'LUE"));
        assertTrue ("Attribute missing", table.containsKey ("LUE'"));
        assertNull ("Attribute has wrong value", table.get ("LUE'"));
        assertTrue ("Attribute missing", table.containsKey ("OTHER"));
        assertEquals ("Attribute has wrong value", "fred", (String)table.get ("OTHER"));
    }
    
    /**
     * see bug #778781 SRC-attribute suppression in IMG-tags
     * & #753012 IMG SRC not parsed v1.3 & v1.4
     * & #755929 Empty string attr. value causes attr parsing to be stopped
     * & #778781 SRC-attribute suppression in IMG-tags
     * & #832530 empty attribute causes parser to fail
     * & #851882 zero length alt tag causes bug in ImageScanner
     *
     *    HTML before parse:
     *    <img src="images/first" alt="first">"
     *    <img src="images/second" alt="">
     *    <img alt="third" src="images/third">
     *    <img alt="" src="images/fourth">
     *
     *    HTML after parse:
     *    <IMG ALT="first" SRC="images/first">
     *    <IMG ALT="" SRC="images/second">
     *    <IMG ALT="third" SRC="images/third">
     *    <IMG ALT="">
     */
    public void testSrcAndAlt () throws ParserException
    {
        String html = "<img src=\"images/first\" alt=\"first\">";

        createParser (html);
        parseAndAssertNodeCount (1);
        assertTrue ("Node should be an ImageTag", node[0] instanceof ImageTag);
        ImageTag img = (ImageTag)node[0];
        assertTrue ("bad source", "images/first".equals (img.getImageURL ()));
        assertTrue ("bad alt", "first".equals (img.getAttribute ("alt")));
        assertStringEquals ("toHtml()", html, img.toHtml ());
    }

    /**
     * see bug #778781 SRC-attribute suppression in IMG-tags
     */
    public void testSrcAndEmptyAlt () throws ParserException
    {
        String html = "<img src=\"images/second\" alt=\"\">";

        createParser (html);
        parseAndAssertNodeCount (1);
        assertTrue ("Node should be an ImageTag", node[0] instanceof ImageTag);
        ImageTag img = (ImageTag)node[0];
        assertTrue ("bad source", "images/second".equals (img.getImageURL ()));
        assertTrue ("bad alt", "".equals (img.getAttribute ("alt")));
        assertStringEquals ("toHtml()", html, img.toHtml ());
    }

    /**
     * see bug #778781 SRC-attribute suppression in IMG-tags
     */
    public void testAltAndSrc () throws ParserException
    {
        String html = "<img alt=\"third\" src=\"images/third\">";

        createParser (html);
        parseAndAssertNodeCount (1);
        assertTrue ("Node should be an ImageTag", node[0] instanceof ImageTag);
        ImageTag img = (ImageTag)node[0];
        assertTrue ("bad source", "images/third".equals (img.getImageURL ()));
        assertTrue ("bad alt", "third".equals (img.getAttribute ("alt")));
        assertStringEquals ("toHtml()", html, img.toHtml ());
    }

    /**
     * see bug #778781 SRC-attribute suppression in IMG-tags
     */
    public void testEmptyAltAndSrc () throws ParserException
    {
        String html = "<img alt=\"\" src=\"images/third\">";

        createParser (html);
        parseAndAssertNodeCount (1);
        assertTrue ("Node should be an ImageTag", node[0] instanceof ImageTag);
        ImageTag img = (ImageTag)node[0];
        assertTrue ("bad source", "images/third".equals (img.getImageURL ()));
        assertTrue ("bad alt", "".equals (img.getAttribute ("alt")));
        assertStringEquals ("toHtml()", html, img.toHtml ());
    }
}
