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
import java.util.Vector;

import org.htmlparser.util.*;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLLinkProcessorTest extends junit.framework.TestCase {
	private HTMLLinkProcessor lp;

	public HTMLLinkProcessorTest(String name) {
		super(name);
	}

	protected void setUp() {
		lp = new HTMLLinkProcessor();
	}

	public void testRemoveFirstSlashIfFound() {
		String testString= "/abcdefg";
		String expected = "abcdefg";
		String result = lp.removeFirstSlashIfFound(testString);
		assertEquals("Ordinary Test",expected,result);
	
		testString = null;
		result = lp.removeFirstSlashIfFound(testString);
		assertNull("Null test, result should be null",result);
	}

	public void testRemoveFirstSlashIfFound2() {
		String testString= "";
		String result = lp.removeFirstSlashIfFound(testString);
		assertNull("Result should have been null",result);
	}
	
	public void testCheckIfLinkIsRelative() {
		String link1 = "ftp://geocities.com/someplace/something.zip";
		String link2 = "gopher://geocities.com/someplace/something.zip";
		String link3 = "myprotocol://geocities.com/someplace/something.zip";
		String link4 = "https://www.geocities.com/someplace/something.zip";
		String url = "http://www.geocities.com";
		assertEquals("Shouldnt be a relative link","ftp://geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link1,url));
		assertEquals("Shouldnt be a relative link","gopher://geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link2,url));
		assertEquals("Shouldnt be a relative link","myprotocol://geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link3,url));
		assertEquals("Shouldnt be a relative link","https://www.geocities.com/someplace/something.zip",lp.checkIfLinkIsRelative(link4,url));		
	}

	public void testCheckIfLinkIsRelative2() {
		String link = "newpage.html";
		String url = "http://www.mysite.com/books/some.asp";
		assertEquals("Should be a relative link","http://www.mysite.com/books/newpage.html",lp.checkIfLinkIsRelative(link,url));
	}	

	public void testIsURL() {
		String resourceLoc1 = "http://someurl.com";
		String resourceLoc2 = "myfilehttp.dat";		
		assertTrue(resourceLoc1+" should be a url",HTMLLinkProcessor.isURL(resourceLoc1));
		assertTrue(resourceLoc2+" should not be a url",!HTMLLinkProcessor.isURL(resourceLoc2));	
		String resourceLoc3 = "file://localhost/D:/java/jdk1.3/docs/api/overview-summary.html";
		assertTrue(resourceLoc3+" should be a url",HTMLLinkProcessor.isURL(resourceLoc3));
		
	}

	public void testCheckIfLinkIsRelativeDoubleSlashBug() {
		String link = "/someplace/somepage.html";
		String url = "http://www.google.com/someDirectory/";
		String url2 = "www.google.com/someDirectory/";
		assertEquals("relative link","http://www.google.com/someplace/somepage.html",lp.checkIfLinkIsRelative(link,url));
		assertEquals("relative link (2)","www.google.com/someplace/somepage.html",lp.checkIfLinkIsRelative(link,url2));		
	}	
	
	public void testSlashIsFirstChar() {
		String link = "/someplace/somepage.html";
		String url = "http://www.google.com/someDirectory/";
		String url2 = "www.google.com/someDirectory/";
		assertEquals("relative link","http://www.google.com/someplace/somepage.html",lp.processSlashIsFirstChar(link,url));
		assertEquals("relative link(2)","www.google.com/someplace/somepage.html",lp.processSlashIsFirstChar(link,url2));		
		
	}

	public void testAddDirectoriesToVector() {
		String url = "http://www.mysite.com/books/some.asp";
		Vector directories = new Vector();
		lp.addDirectoriesToVector(directories,url);
		assertEquals("Size of Vector",4,directories.size());
		String first = (String)directories.elementAt(0);
		assertEquals("First Element","http:/",first);
		String second = (String)directories.elementAt(1);
		assertEquals("Second Element","/",second);
		String third = (String)directories.elementAt(2);
		assertEquals("Third Element","www.mysite.com/",third);
		String fourth = (String)directories.elementAt(3);
		assertEquals("Fourth Element","books/",fourth);
	}

	public void testFixSpaces() {
		String url = "http://htmlparser.sourceforge.net/test/This is a Test Page.html";
		String fixedURL = lp.fixSpaces(url);
		int index = fixedURL.indexOf(" ");
		assertEquals("Expected","http://htmlparser.sourceforge.net/test/This%20is%20a%20Test%20Page.html",fixedURL);
	}
}
