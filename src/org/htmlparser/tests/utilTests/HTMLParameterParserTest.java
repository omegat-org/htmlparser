// HTMLParser Library v1_2_20021201 - A java-based parser for HTML
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

package org.htmlparser.tests.utilTests;

import java.util.Hashtable;

import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.HTMLParameterParser;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLParameterParserTest extends TestCase {
	private HTMLParameterParser parser;
	private HTMLTag tag;
	private Hashtable table;
	
	public HTMLParameterParserTest(String name) {
		super(name);
	}

	protected void setUp() {
		parser = new HTMLParameterParser();
	}
	
	public void getParameterTableFor(String tagContents) {
		tag = new HTMLTag(0,0,tagContents,"");
		table = parser.parseParameters(tag);
		
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
        assertEquals("Name of Tag","INPUT",table.get(HTMLTag.TAGNAME));
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
		String key2 = HTMLTag.TAGNAME;
		assertEquals("Expected Value 2","TEXTAREA",table.get(key2));
    }

    public void testNullTag(){
        getParameterTableFor("INPUT type=");
        assertEquals("Name of Tag","INPUT",table.get(HTMLTag.TAGNAME));
        assertEquals("Type","",table.get("TYPE"));                

    }
}
