// HTMLParser Library v1_2_20021016 - A java-based parser for HTML
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

package com.kizna.htmlTests.utilTests;
import com.kizna.html.util.*;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (12/25/2001 12:48:52 PM)
 * @author: Administrator
 */
public class HTMLLinkProcessorTest extends junit.framework.TestCase {
	private HTMLLinkProcessor linkProcessor;
	/**
	 * HTMLExtractorTest constructor comment.
	 * @param name java.lang.String
	 */
	public HTMLLinkProcessorTest(String name) {
		super(name);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:50:03 PM)
	 */
	protected void setUp() {
		linkProcessor = new HTMLLinkProcessor();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:49:09 PM)
	 * @return junit.framework.TestSuite
	 */
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(HTMLLinkProcessorTest.class);
		return suite;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:49:43 PM)
	 */
	public void testRemoveFirstSlashIfFound() {
		String testString= "/abcdefg";
		String expected = "abcdefg";
		String result = linkProcessor.removeFirstSlashIfFound(testString);
		assertEquals("Ordinary Test",expected,result);
	
		testString = null;
		result = linkProcessor.removeFirstSlashIfFound(testString);
		assertNull("Null test, result should be null",result);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/25/2001 12:49:43 PM)
	 */
	public void testRemoveFirstSlashIfFound2() {
		String testString= "";
		String result = linkProcessor.removeFirstSlashIfFound(testString);
		assertNull("Result should have been null",result);
	}
	
	/**
	 * This testcase is based on a bug reported by Kimberly Berry
	 * If a link contains a protocol other than http, it should not be considered relative.
	 */
	public void testCheckIfLinkIsRelative() {
		String link1 = "ftp://geocities.com/someplace/something.zip";
		String link2 = "gopher://geocities.com/someplace/something.zip";
		String link3 = "myprotocol://geocities.com/someplace/something.zip";
		String link4 = "https://www.geocities.com/someplace/something.zip";
		String url = "http://www.geocities.com";
		HTMLLinkProcessor lp = new HTMLLinkProcessor();
		assertEquals("Shouldnt be a relative link","ftp://geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link1,url));
		assertEquals("Shouldnt be a relative link","gopher://geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link2,url));
		assertEquals("Shouldnt be a relative link","myprotocol://geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link3,url));
		assertEquals("Shouldnt be a relative link","https://www.geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link4,url));		
	}
	public void testIsURL() {
		String resourceLoc1 = "http://someurl.com";
		String resourceLoc2 = "myfilehttp.dat";		
		assertTrue(resourceLoc1+" should be a url",HTMLLinkProcessor.isURL(resourceLoc1));
		assertTrue(resourceLoc2+" should not be a url",!HTMLLinkProcessor.isURL(resourceLoc2));	
		
		String resourceLoc3 = "file://localhost/D:/java/jdk1.3/docs/api/overview-summary.html";
		assertTrue(resourceLoc3+" should be a url",HTMLLinkProcessor.isURL(resourceLoc3));
		
	}
	/**
	 * This testcase is based on a bug reported by Kamen.
	 * If a relative link starts with a slash, it should be directly appended to the base url,
	 * and directories of the base url should be omitted.
	 */
	public void testCheckIfLinkIsRelativeDoubleSlashBug() {
		String link = "/someplace/somepage.html";
		HTMLLinkProcessor lp = new HTMLLinkProcessor();
		String url = "http://www.google.com/someDirectory/";
		String url2 = "www.google.com/someDirectory/";
		assertEquals("relative link","http://www.google.com/someplace/somepage.html",lp.checkIfLinkIsRelative(link,url));
		assertEquals("relative link (2)","www.google.com/someplace/somepage.html",lp.checkIfLinkIsRelative(link,url2));		
	}	
	
	public void testSlashIsFirstChar() {
		String link = "/someplace/somepage.html";
		HTMLLinkProcessor lp = new HTMLLinkProcessor();
		String url = "http://www.google.com/someDirectory/";
		String url2 = "www.google.com/someDirectory/";
		assertEquals("relative link","http://www.google.com/someplace/somepage.html",lp.processSlashIsFirstChar(link,url));
		assertEquals("relative link(2)","www.google.com/someplace/somepage.html",lp.processSlashIsFirstChar(link,url2));		
		
	}
}
