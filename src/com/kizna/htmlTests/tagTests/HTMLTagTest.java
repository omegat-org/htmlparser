// HTMLParser Library v1_2_20020623 - A java-based parser for HTML
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

package com.kizna.htmlTests.tagTests;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.Hashtable;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.scanners.*;

import java.io.StringReader;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/17/2001 3:59:52 PM)
 * @author: Administrator
 */
public class HTMLTagTest extends TestCase 
{
/**
 * HTMLStringNodeTest constructor comment.
 * @param name java.lang.String
 */
public HTMLTagTest(String name) {
	super(name);
}
public static void main(String[] args) {
	new junit.awtui.TestRunner().start(new String[] {"com.kizna.htmlTests.HTMLTagTest"});
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 11:22:36 AM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() 
{
	TestSuite suite = new TestSuite(HTMLTagTest.class);
	return suite;
}
/**
 * The bug being reproduced is this : <BR>
 * &lt;BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000 <BR>
 * vLink=#551a8b&gt;
 * The above line is incorrectly parsed in that, the BODY tag is not identified.
 * Creation date: (6/17/2001 4:01:06 PM)
 */
public void testBodyTagBug1() 
{
	String testHTML = new String("<BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000\nvLink=#551a8b>");
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];
 	
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLTag
	assertTrue("Node should be a HTMLTag",node[0] instanceof HTMLTag);
	HTMLTag tag = (HTMLTag)node[0];
	assertEquals("Contents of the tag","BODY aLink=#ff0000 bgColor=#ffffff link=#0000cc onload=setfocus() text=#000000\nvLink=#551a8b",tag.getText());
}
    public void testCheckValidity() {
    	HTMLTag tag = new HTMLTag(0,20,"font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"","<font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\">");
    	int state = HTMLTag.checkValidity(HTMLTag.TAG_IGNORE_DATA_STATE,tag);
    	assertEquals("Expected State",HTMLTag.TAG_FINISHED_PARSING_STATE,state);
    }
    public void testCorrectTag() {
    	HTMLTag tag = new HTMLTag(0,20,"font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"","<font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\">");
		HTMLTag.correctTag(tag);
		assertEquals("Corrected Tag","font face=Arial,helvetica, sans-serif=sans-serif size=2 color=#FFFFFF",tag.getText());
    }
    public void testExtractWord() {
    	String line = "Abc DEF GHHI";
    	String word = HTMLTag.extractWord(line);
    	assertEquals("Word expected","ABC",HTMLTag.extractWord(line));
    }
/**
 * The following should be identified as a tag : <BR>
 * 	&lt;MYTAG abcd\n"+
 *		"efgh\n"+
 *		"ijkl\n"+
 *		"mnop&gt;	
 * Creation date: (6/17/2001 5:27:42 PM)
 */
public void testLargeTagBug() 
{
	String testHTML = new String(
		"<MYTAG abcd\n"+
		"efgh\n"+
		"ijkl\n"+
		"mnop>"
	);
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];

			
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 1 node identified",new Integer(1),new Integer(i));
	// The node should be an HTMLTag
	assertTrue("Node should be a HTMLTag",node[0] instanceof HTMLTag);
	HTMLTag tag = (HTMLTag)node[0];
	assertEquals("Contents of the tag","MYTAG abcd\nefgh\nijkl\nmnop",tag.getText());
	
		
}
/**
 * Bug reported by Gordon Deudney 2002-03-15
 * Nested JSP Tags were not working
 */
public void testNestedTags() {
	HTMLEndTag etag;
	String s = "input type=\"text\" value=\"<%=\"test\"%>\" name=\"text\"";
	String line = "<"+s+">";
    StringReader sr = new StringReader(line);
    HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader);
  	HTMLNode [] node = new HTMLNode[10];
	int i = 0;
  	for (Enumeration e = parser.elements();e.hasMoreElements(); ) {
    	node[i++] = (HTMLNode)e.nextElement();
	}	
   	// Check the no of nodes found (should be only one)
   	assertEquals("Number of nodes found", 1,i);
	assertTrue("The node found should have been an HTMLTag",node[0] instanceof HTMLTag);
	HTMLTag tag = (HTMLTag) node[0];
	assertEquals("Tag Contents",s,tag.getText());
}
    /**
    * Test parseParameter method
    * Created by Kaarle Kaila (august 2001)
    * the tag name is here G
    */
    public void testParseParameter3(){
        HTMLTag tag;
        HTMLEndTag etag;
        HTMLStringNode snode;
        Object o=null;
        String lin1 = "<DIV class=\"userData\" id=\"oLayout\" name=\"oLayout\"></DIV>";
       	StringReader sr = new StringReader(lin1);
    	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	    HTMLParser parser = new HTMLParser(reader);
        Enumeration en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;      
        
        try {

            if (en.hasMoreElements()) {
                o = en.nextElement();        
                
                tag = (HTMLTag)o;     
                h = tag.parseParameters();
				String classValue= (String)h.get("CLASS");
                assertEquals ("The class value should be ","userData",classValue);
            }
            
        } catch (ClassCastException ce) {
            fail("Bad class element = " + o.getClass().getName());
        }
    }
    /**
    * Test parseParameter method
    * Created by Kaarle Kaila (august 2001)
    * the tag name is here A (and should be eaten up by linkScanner)
    */
    public void testParseParameterA(){
        HTMLTag tag;
        HTMLEndTag etag;
        HTMLStringNode snode;
        Object o=null;
        String lin1 = "<A href=\"http://www.iki.fi/kaila\" myParameter yourParameter=\"Kaarle\">Kaarle's homepage</A><p>Paragraph</p>";
       	StringReader sr = new StringReader(lin1);
    	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	    HTMLParser parser = new HTMLParser(reader);
        Enumeration en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;      
        
        try {

            if (en.hasMoreElements()) {
                o = en.nextElement();        
                
                tag = (HTMLTag)o;     
                h = tag.parseParameters();
                a = (String)h.get(tag.TAGNAME);                
                href = (String)h.get("HREF");
                myValue = (String)h.get("MYPARAMETER");
                nice = (String)h.get("YOURPARAMETER");
                assertEquals ("Link tag (A)",a,"A");
                assertEquals ("href value",href,"http://www.iki.fi/kaila");
                assertEquals ("myparameter value",myValue,"");
                assertEquals ("yourparameter value",nice,"Kaarle");
//                }
            }
            if (!(o instanceof HTMLLinkTag)) {
                // linkscanner has eaten up this piece
                if ( en.hasMoreElements()) {
                    o = en.nextElement();        
                    snode = (HTMLStringNode)o;   
                    assertEquals("Value of element",snode.getText(),"Kaarle's homepage");
                }

                if (en.hasMoreElements()) {
                    o = en.nextElement();        
                    etag = (HTMLEndTag)o;        
                    assertEquals("endtag of link",etag.getText(),"A");
                }
            }
            // testing rest
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                
                tag = (HTMLTag)o;        
                assertEquals("following paragraph begins",tag.getText(),"p");
            }
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                snode = (HTMLStringNode)o;   
                assertEquals("paragraph contents",snode.getText(),"Paragraph");
            }            
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                etag = (HTMLEndTag)o;        
                assertEquals("paragrapg endtag",etag.getText(),"p");
            }
            
        } catch (ClassCastException ce) {
            fail("Bad class element = " + o.getClass().getName());
        }
    }
    /**
    * Test parseParameter method
    * Created by Kaarle Kaila (august 2001)
    * the tag name is here G
    */
    public void testParseParameterG(){
        HTMLTag tag;
        HTMLEndTag etag;
        HTMLStringNode snode;
        Object o=null;
        String lin1 = "<G href=\"http://www.iki.fi/kaila\" myParameter yourParameter=\"Kaila\">Kaarle's homepage</G><p>Paragraph</p>";
       	StringReader sr = new StringReader(lin1);
    	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	    HTMLParser parser = new HTMLParser(reader);
        Enumeration en = parser.elements();
        Hashtable h;
        boolean testEnd=true;  // test end of first part
        String a,href,myPara,myValue,nice;      
        
        try {

            if (en.hasMoreElements()) {
                o = en.nextElement();        
                
                tag = (HTMLTag)o;     
                h = tag.parseParameters();
                a = (String)h.get(tag.TAGNAME);                
                href = (String)h.get("HREF");
                myValue = (String)h.get("MYPARAMETER");
                nice = (String)h.get("YOURPARAMETER");
                assertEquals ("The tagname should be G",a,"G");
                assertEquals ("Check the http address",href,"http://www.iki.fi/kaila");
                assertEquals ("myValue is empty",myValue,"");
                assertEquals ("The second parameter value",nice,"Kaila");
//                }
            }
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                snode = (HTMLStringNode)o;   
                assertEquals("The text of the element",snode.getText(),"Kaarle's homepage");
            }

            if (en.hasMoreElements()) {
                o = en.nextElement();        
                etag = (HTMLEndTag)o;        
                assertEquals("Endtag is G",etag.getText(),"G");
            }
            // testing rest
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                
                tag = (HTMLTag)o;        
                assertEquals("Follow up by p-tag",tag.getText(),"p");
            }
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                snode = (HTMLStringNode)o;   
                assertEquals("Verify the paragraph text",snode.getText(),"Paragraph");
            }            
            if (en.hasMoreElements()) {
                o = en.nextElement();        
                etag = (HTMLEndTag)o;        
                assertEquals("Still patragraph endtag",etag.getText(),"p");
            }
            
        } catch (ClassCastException ce) {
            fail("Bad class element = " + o.getClass().getName());
        }
    }
    /**
     * Reproduction of a bug reported by Annette Doyle
     * This is actually a pretty good example of dirty html - we are in a fix 
     * here, bcos the font tag (the first one) has an erroneous inverted comma. In HTMLTag,
     * we ignore anything in inverted commas, and dont if its outside. This kind of messes
     * up our parsing almost completely.
     * 
     */
    public void testStrictParsing() {
		String testHTML = "<div align=\"center\"><font face=\"Arial,\"helvetica,\" sans-serif=\"sans-serif\" size=\"2\" color=\"#FFFFFF\"><a href=\"/index.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Home</font></a>\n"+ 
        "<a href=\"/cia/notices.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Notices</font></a>\n"+
        "<a href=\"/cia/notices.html#priv\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Privacy</font></a>\n"+
        "<a href=\"/cia/notices.html#sec\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Security</font></a>\n"+
        "<a href=\"/cia/contact.htm\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Contact Us</font></a>\n"+
        "<a href=\"/cia/sitemap.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Site Map</font></a>\n"+
        "<a href=\"/cia/siteindex.html\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Index</font></a>\n"+
        "<a href=\"/search\" link=\"#000000\" vlink=\"#000000\"><font color=\"#FFFFFF\">Search</font></a>\n"+
        "</font></div>"; 
	
		StringReader sr = new StringReader(testHTML); 
		HTMLReader reader =  new HTMLReader(new BufferedReader(sr),"http://www.cia.gov");
		HTMLParser parser = new HTMLParser(reader);
		HTMLNode [] node = new HTMLNode[100];
		// Register the image scanner
		parser.registerScanners();
		int i = 0;
		HTMLNode thisNode;
		for (Enumeration e = parser.elements();e.hasMoreElements();) {
			node[i++] = (HTMLNode)e.nextElement();
		}	    	
		assertEquals("Number of nodes found",12,i);
		// Check the tags
		HTMLTag tag = (HTMLTag)node[0];
		assertEquals("DIV Tag expected","div align=\"center\"",tag.getText());		
		tag = (HTMLTag)node[1];
		assertEquals("Second tag should be corrected","font face=Arial,helvetica, sans-serif=sans-serif size=2 color=#FFFFFF",tag.getText());
		// Try to parse the parameters from this tag.
		Hashtable table = tag.parseParameters();
		assertNotNull("Parameters table",table);
		assertEquals("font sans-serif parameter","sans-serif",table.get("SANS-SERIF"));
		assertEquals("font face parameter","Arial,helvetica,",table.get("FACE"));
    }
public void testToHTML() {
	String testHTML = new String(
		"<MYTAG abcd\n"+
		"efgh\n"+
		"ijkl\n"+
		"mnop>\n"+
		"<TITLE>Hello</TITLE>\n"+
		"<A HREF=\"Hello.html\">Hey</A>"
	);
	StringReader sr = new StringReader(testHTML);
	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	HTMLParser parser = new HTMLParser(reader);
	HTMLNode [] node = new HTMLNode[10];

			
	int i = 0;
	for (Enumeration e = parser.elements();e.hasMoreElements();)
	{
		node[i++] = (HTMLNode)e.nextElement();
	}
	assertEquals("There should be 4 node identified",new Integer(7),new Integer(i));
	// The node should be an HTMLTag
	assertTrue("1st Node should be a HTMLTag",node[0] instanceof HTMLTag);
	HTMLTag tag = (HTMLTag)node[0];
	assertEquals("Raw String of the tag","<MYTAG abcd\nefgh\nijkl\nmnop>",tag.toHTML());
	assertTrue("2nd Node should be a HTMLTag",node[1] instanceof HTMLTag);
	assertTrue("5th Node should be a HTMLTag",node[4] instanceof HTMLTag);
	tag = (HTMLTag)node[1];
	assertEquals("Raw String of the tag","<TITLE>",tag.toHTML());
	tag = (HTMLTag)node[4];
	assertEquals("Raw String of the tag","<A HREF=\"Hello.html\">",tag.toHTML());
	
}
    public void testWithLink(){
        String data = "<P>To download a document: click on a link. ";
        data += "Once the .pdf file opens, use the &#34;Save As&#34; ";
        data += "button on the toolbar to save the document.</P>";
        data += "<P><B>Please note</B>, to view these documents, you ";
        data += "must have <A href='www.adobe.com'>Adobe Acrobat Reader</A> ";
        data += "installed on your computer. If you do not &#160;have Adobe Acrobat ";
        data += "Reader, go to: <A href='http://www.adobe.com/products/acrobat/readstep.htm'>";
        data += "www.adobe.com/products/acrobat/readstep.htm</A>";
        data += " and follow the instructions on how to install the program.</P>";
        HTMLTag tag;
        HTMLEndTag etag;
        HTMLStringNode snode;
        Object o=null;
       	StringReader sr = new StringReader(data);
    	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	    HTMLParser parser = new HTMLParser(reader);
        Enumeration en = parser.elements();
        String result="";
        try {
            while (en.hasMoreElements()) {                
                o = en.nextElement();                        
                HTMLNode node = (HTMLNode)o;
                result += node.toHTML();
            }
            assertStringEquals("Check collected contents to original",result,data);            
        } catch (ClassCastException ce) {
            fail("Bad class element = " + o.getClass().getName());
        }

    }
	public void assertStringEquals(String message,String s1,String s2) {
		for (int i=0;i<s1.length();i++) {
			if (s1.charAt(i)!=s2.charAt(i)) {
				assertTrue(message+
					" \nMismatch of strings at char posn "+i+
					" \nString 1 upto mismatch = "+s1.substring(0,i)+
					" \nString 2 upto mismatch = "+s2.substring(0,i)+
					" \nString 1 mismatch character = "+s1.charAt(i)+", code = "+(int)s1.charAt(i)+
					" \nString 2 mismatch character = "+s2.charAt(i)+", code = "+(int)s2.charAt(i),false);
			}
		}
	}    
    /**
    * Test parseParameter method
    * Created by Kaarle Kaila (22 Oct 2001)
    * This test just wants the text in the element
    */
    public void testWithoutParseParameter(){
        HTMLTag tag;
        HTMLEndTag etag;
        HTMLStringNode snode;
        Object o=null;
        String lin1 = "<A href=\"http://www.iki.fi/kaila\" myParameter yourParameter=\"Kaarle\">Kaarle's homepage</A><p>Paragraph</p>";
       	StringReader sr = new StringReader(lin1);
    	HTMLReader reader =  new HTMLReader(new BufferedReader(sr),5000);
	    HTMLParser parser = new HTMLParser(reader);
        Enumeration en = parser.elements();
        String result="";
        try {
            while (en.hasMoreElements()) {                
                o = en.nextElement();   
                HTMLNode node = (HTMLNode)o;                     
                result += node.toHTML();
            }
            assertEquals("Check collected contents to original",result,lin1);            
        } catch (ClassCastException ce) {
            fail("Bad class element = " + o.getClass().getName());
        }
    }
    public void testStyleSheetTag(){
     	String testHTML1 = new String("<link rel src=\"af.css\"/>"); 
		
		StringReader sr = new StringReader(testHTML1); 
		HTMLReader reader = new HTMLReader(new 
		BufferedReader(sr),"http://www.google.com/test/index.html"); 
		HTMLParser parser = new HTMLParser(reader); 
		HTMLNode [] node = new HTMLNode[10]; 
		
		
		int i = 0; 
		for (Enumeration e = parser.elements();e.hasMoreElements();) 
		{ 
			node[i++] = (HTMLNode)e.nextElement(); 
		} 
		
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i)); 
		assertTrue("Node should be a tag",node[0] instanceof HTMLTag);
		HTMLTag tag = (HTMLTag)node[0];
		assertEquals("StyleSheet Source","af.css",tag.getParameter("src"));

    }    
    /**
     * Bug report by Cedric Rosa, causing null pointer exceptions when encountering a broken tag,
     * and if this has no further lines to parse
     */
    public void testBrokenTag() {
     	String testHTML1 = new String("<br"); 
		
		StringReader sr = new StringReader(testHTML1); 
		HTMLReader reader = new HTMLReader(new 
		BufferedReader(sr),"http://www.google.com/test/index.html"); 
		HTMLParser parser = new HTMLParser(reader); 
		HTMLNode [] node = new HTMLNode[10]; 
				
		int i = 0; 
		for (Enumeration e = parser.elements();e.hasMoreElements();) 
		{ 
			node[i++] = (HTMLNode)e.nextElement(); 
		} 
		
		assertEquals("There should be 1 node identified",new Integer(1),new Integer(i)); 
		assertTrue("Node should be a tag",node[0] instanceof HTMLTag);
		HTMLTag tag = (HTMLTag)node[0];
		assertEquals("Node contents","br",tag.getText());    	
    }
}
