// HTMLParser Library v1_3_20030504 - A java-based parser for HTML
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

package org.htmlparser.tests.parserHelperTests;

import java.util.Hashtable;

import org.htmlparser.parserHelper.AttributeParser;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.tests.ParserTestCase;

public class AttributeParserTest extends ParserTestCase {
	private AttributeParser parser;
	private Tag tag;
	private Hashtable table;
	
	public AttributeParserTest(String name) {
		super(name);
	}

	protected void setUp() {
		parser = new AttributeParser();
	}
	
	public void getParameterTableFor(String tagContents) {
		tag = new Tag(new TagData(0,0,tagContents,""));
		table = parser.parseAttributes(tag);
		
	}
	
	public void testParseParameters() {
		getParameterTableFor("a b = \"c\"");
		assertEquals("Value","c",table.get("B"));
	}

	public void testParseTokenValues() {
		getParameterTableFor("a b = \"'\"");
		assertEquals("Value","'",table.get("B"));
	}
        
	public void testParseEmptyValues() {
		getParameterTableFor("a b = \"\"");
		assertEquals("Value","",table.get("B"));		
	}

	public void testParseMissingEqual() {
		getParameterTableFor("a b\"c\"");
		assertEquals("ValueB","",table.get("B"));
                
	}
        
    public void testTwoParams(){
		getParameterTableFor("PARAM NAME=\"Param1\" VALUE=\"Somik\">\n");
		assertEquals("Param1","Param1",table.get("NAME"));
		assertEquals("Somik","Somik",table.get("VALUE"));                
    }

    public void testPlainParams(){
        getParameterTableFor("PARAM NAME=Param1 VALUE=Somik");
        assertEquals("Param1","Param1",table.get("NAME"));
        assertEquals("Somik","Somik",table.get("VALUE"));                
    }
    
    public void testValueMissing() {
        getParameterTableFor("INPUT type=\"checkbox\" name=\"Authorize\" value=\"Y\" checked");
        assertEquals("Name of Tag","INPUT",table.get(Tag.TAGNAME));
        assertEquals("Type","checkbox",table.get("TYPE"));                
        assertEquals("Name","Authorize",table.get("NAME"));
        assertEquals("Value","Y",table.get("VALUE"));
        assertEquals("Checked","",table.get("CHECKED"));
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
		String key2 = Tag.TAGNAME;
		assertEquals("Expected Value 2","TEXTAREA",table.get(key2));
    }

    public void testNullTag(){
        getParameterTableFor("INPUT type=");
        assertEquals("Name of Tag","INPUT",table.get(Tag.TAGNAME));
        assertEquals("Type","",table.get("TYPE"));                
    }
    
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
     * attributes are not parsed correctly, when they contain
     * scriptlets.
     * Submitted by Cory Seefurth
     */
    public void testJspWithinAttributes() {
		getParameterTableFor(
			"a href=\"<%=Application(\"sURL\")%>/literature/index.htm"
        );
		assertStringEquals(
			"href",
			"<%=Application(\"sURL\")%>/literature/index.htm",
			(String)table.get("HREF")
		);
    }
    
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
			(String)table.get(Tag.TAGNAME)
		);
    }

    /**
     * Believe it or not Moi (vincent_aumont) wants htmlparser to parse a text file
     * containing something that looks nearly like a tag:
     * <pre>
     * "                        basic_string&lt;char, string_char_traits&lt;char&gt;, &lt;&gt;&gt;::basic_string()"
     * </pre>
     * This was throwing a null pointer exception when the empty &lt;&gt; was encountered.
     * Bug #725420 NPE in StringBean.visitTag
     **/
    public void testEmptyTag () {
		getParameterTableFor("");
		assertNotNull ("No Tag.TAGNAME",table.get(Tag.TAGNAME));
    }

    /**
     * Case of script in attributes.
     */
    public void testScriptedTag () {
		getParameterTableFor("body onLoad=defaultStatus=''");
        String name = (String)table.get(Tag.TAGNAME);
		assertNotNull ("No Tag.TAGNAME", name);
        assertStringEquals("tag name parsed incorrectly", "BODY", name);
        String value = (String)table.get ("ONLOAD");
        assertStringEquals ("parameter parsed incorrectly", "defaultStatus=''", value);
    }
}
