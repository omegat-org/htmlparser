// HTMLParser Library v1_2 - A java-based parser for HTML
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
import org.htmlparser.util.HTMLLinkProcessor;
import org.htmlparser.util.HTMLParserException;

public class HTMLLinkProcessorTest extends junit.framework.TestCase {
	private HTMLLinkProcessor lp;

	public HTMLLinkProcessorTest(String name) {
		super(name);
	}

	protected void setUp() {
		lp = new HTMLLinkProcessor();
	}

	public void testIsURL() {
		String resourceLoc1 = "http://someurl.com";
		String resourceLoc2 = "myfilehttp.dat";		
		assertTrue(resourceLoc1+" should be a url",HTMLLinkProcessor.isURL(resourceLoc1));
		assertTrue(resourceLoc2+" should not be a url",!HTMLLinkProcessor.isURL(resourceLoc2));	
		String resourceLoc3 = "file://localhost/D:/java/jdk1.3/docs/api/overview-summary.html";
		assertTrue(resourceLoc3+" should be a url",HTMLLinkProcessor.isURL(resourceLoc3));
		
	}

	public void testFixSpaces() {
		String url = "http://htmlparser.sourceforge.net/test/This is a Test Page.html";
		String fixedURL = HTMLLinkProcessor.fixSpaces(url);
		int index = fixedURL.indexOf(" ");
		assertEquals("Expected","http://htmlparser.sourceforge.net/test/This%20is%20a%20Test%20Page.html",fixedURL);
	}

    //
    // Tests from Appendix C Examples of Resolving Relative URI References
    // RFC 2396 Uniform Resource Identifiers (URI): Generic Syntax
    // T. Berners-Lee et al.
    // http://www.ietf.org/rfc/rfc2396.txt

    // Within an object with a well-defined base URI of
    static final String baseURI = "http://a/b/c/d;p?q";
    // the relative URI would be resolved as follows:
    
    // C.1.  Normal Examples
    //  g:h           =  g:h
    //  g             =  http://a/b/c/g
    //  ./g           =  http://a/b/c/g
    //  g/            =  http://a/b/c/g/
    //  /g            =  http://a/g
    //  //g           =  http://g
    //  ?y            =  http://a/b/c/?y
    //  g?y           =  http://a/b/c/g?y
    //  #s            =  (current document)#s
    //  g#s           =  http://a/b/c/g#s
    //  g?y#s         =  http://a/b/c/g?y#s
    //  ;x            =  http://a/b/c/;x
    //  g;x           =  http://a/b/c/g;x
    //  g;x?y#s       =  http://a/b/c/g;x?y#s
    //  .             =  http://a/b/c/
    //  ./            =  http://a/b/c/
    //  ..            =  http://a/b/
    //  ../           =  http://a/b/
    //  ../g          =  http://a/b/g
    //  ../..         =  http://a/
    //  ../../        =  http://a/
    //  ../../g       =  http://a/g

    public void test1 () throws HTMLParserException
    {
        assertEquals ("test1 failed", "https:h", (new HTMLLinkProcessor ()).extract ("https:h", baseURI));
    }
    public void test2 () throws HTMLParserException
    {
        assertEquals ("test2 failed", "http://a/b/c/g", (new HTMLLinkProcessor ()).extract ("g", baseURI));
    }
    public void test3 () throws HTMLParserException
    {
        assertEquals ("test3 failed", "http://a/b/c/g", (new HTMLLinkProcessor ()).extract ("./g", baseURI));
    }
    public void test4 () throws HTMLParserException
    {
        assertEquals ("test4 failed", "http://a/b/c/g/", (new HTMLLinkProcessor ()).extract ("g/", baseURI));
    }
    public void test5 () throws HTMLParserException
    {
        assertEquals ("test5 failed", "http://a/g", (new HTMLLinkProcessor ()).extract ("/g", baseURI));
    }
    public void test6 () throws HTMLParserException
    {
        assertEquals ("test6 failed", "http://g", (new HTMLLinkProcessor ()).extract ("//g", baseURI));
    }
    public void test7 () throws HTMLParserException
    {
        assertEquals ("test7 failed", "http://a/b/c/?y", (new HTMLLinkProcessor ()).extract ("?y", baseURI));
    }
    public void test8 () throws HTMLParserException
    {
        assertEquals ("test8 failed", "http://a/b/c/g?y", (new HTMLLinkProcessor ()).extract ("g?y", baseURI));
    }
    public void test9 () throws HTMLParserException
    {
        assertEquals ("test9 failed", "https:h", (new HTMLLinkProcessor ()).extract ("https:h", baseURI));
    }
    public void test10 () throws HTMLParserException
    {
        assertEquals ("test10 failed", "https:h", (new HTMLLinkProcessor ()).extract ("https:h", baseURI));
    }
    //  #s            =  (current document)#s
    public void test11 () throws HTMLParserException
    {
        assertEquals ("test11 failed", "http://a/b/c/g#s", (new HTMLLinkProcessor ()).extract ("g#s", baseURI));
    }
    public void test12 () throws HTMLParserException
    {
        assertEquals ("test12 failed", "http://a/b/c/g?y#s", (new HTMLLinkProcessor ()).extract ("g?y#s", baseURI));
    }
    public void test13 () throws HTMLParserException
    {
        assertEquals ("test13 failed", "http://a/b/c/;x", (new HTMLLinkProcessor ()).extract (";x", baseURI));
    }
    public void test14 () throws HTMLParserException
    {
        assertEquals ("test14 failed", "http://a/b/c/g;x", (new HTMLLinkProcessor ()).extract ("g;x", baseURI));
    }
    public void test15 () throws HTMLParserException
    {
        assertEquals ("test15 failed", "http://a/b/c/g;x?y#s", (new HTMLLinkProcessor ()).extract ("g;x?y#s", baseURI));
    }
    public void test16 () throws HTMLParserException
    {
        assertEquals ("test16 failed", "http://a/b/c/", (new HTMLLinkProcessor ()).extract (".", baseURI));
    }
    public void test17 () throws HTMLParserException
    {
        assertEquals ("test17 failed", "http://a/b/c/", (new HTMLLinkProcessor ()).extract ("./", baseURI));
    }
    public void test18 () throws HTMLParserException
    {
        assertEquals ("test18 failed", "http://a/b/", (new HTMLLinkProcessor ()).extract ("..", baseURI));
    }
    public void test19 () throws HTMLParserException
    {
        assertEquals ("test19 failed", "http://a/b/", (new HTMLLinkProcessor ()).extract ("../", baseURI));
    }
    public void test20 () throws HTMLParserException
    {
        assertEquals ("test20 failed", "http://a/b/g", (new HTMLLinkProcessor ()).extract ("../g", baseURI));
    }
    public void test21 () throws HTMLParserException
    {
        assertEquals ("test21 failed", "http://a/", (new HTMLLinkProcessor ()).extract ("../..", baseURI));
    }
    public void test22 () throws HTMLParserException
    {
        assertEquals ("test22 failed", "http://a/g", (new HTMLLinkProcessor ()).extract ("../../g", baseURI));
    }
    
    // C.2.  Abnormal Examples
    //   Although the following abnormal examples are unlikely to occur in
    //   normal practice, all URI parsers should be capable of resolving them
    //   consistently.  Each example uses the same base as above.
    //
    //   An empty reference refers to the start of the current document.
    //
    //      <>            =  (current document)
    //
    //   Parsers must be careful in handling the case where there are more
    //   relative path ".." segments than there are hierarchical levels in the
    //   base URI's path.  Note that the ".." syntax cannot be used to change
    //   the authority component of a URI.
    //
    //      ../../../g    =  http://a/../g
    //      ../../../../g =  http://a/../../g
    //
    //   In practice, some implementations strip leading relative symbolic
    //   elements (".", "..") after applying a relative URI calculation, based
    //   on the theory that compensating for obvious author errors is better
    //   than allowing the request to fail.  Thus, the above two references
    //   will be interpreted as "http://a/g" by some implementations.
    //
    //   Similarly, parsers must avoid treating "." and ".." as special when
    //   they are not complete components of a relative path.
    //
    //      /./g          =  http://a/./g
    //      /../g         =  http://a/../g
    //      g.            =  http://a/b/c/g.
    //      .g            =  http://a/b/c/.g
    //      g..           =  http://a/b/c/g..
    //      ..g           =  http://a/b/c/..g
    //
    //   Less likely are cases where the relative URI uses unnecessary or
    //   nonsensical forms of the "." and ".." complete path segments.
    //
    //      ./../g        =  http://a/b/g
    //      ./g/.         =  http://a/b/c/g/
    //      g/./h         =  http://a/b/c/g/h
    //      g/../h        =  http://a/b/c/h
    //      g;x=1/./y     =  http://a/b/c/g;x=1/y
    //      g;x=1/../y    =  http://a/b/c/y
    //
    //   All client applications remove the query component from the base URI
    //   before resolving relative URI.  However, some applications fail to
    //   separate the reference's query and/or fragment components from a
    //   relative path before merging it with the base path.  This error is
    //   rarely noticed, since typical usage of a fragment never includes the
    //   hierarchy ("/") character, and the query component is not normally
    //   used within relative references.
    //
    //      g?y/./x       =  http://a/b/c/g?y/./x
    //      g?y/../x      =  http://a/b/c/g?y/../x
    //      g#s/./x       =  http://a/b/c/g#s/./x
    //      g#s/../x      =  http://a/b/c/g#s/../x
    //
    //   Some parsers allow the scheme name to be present in a relative URI if
    //   it is the same as the base URI scheme.  This is considered to be a
    //   loophole in prior specifications of partial URI [RFC1630]. Its use
    //   should be avoided.
    //
    //      http:g        =  http:g           ; for validating parsers
    //                    |  http://a/b/c/g   ; for backwards compatibility

//    public void test23 () throws HTMLParserException
//    {
//        assertEquals ("test23 failed", "http://a/../g", (new HTMLLinkProcessor ()).extract ("../../../g", baseURI));
//    }
//    public void test24 () throws HTMLParserException
//    {
//        assertEquals ("test24 failed", "http://a/../../g", (new HTMLLinkProcessor ()).extract ("../../../../g", baseURI));
//    }
    public void test23 () throws HTMLParserException
    {
        assertEquals ("test23 failed", "http://a/g", (new HTMLLinkProcessor ()).extract ("../../../g", baseURI));
    }
    public void test24 () throws HTMLParserException
    {
        assertEquals ("test24 failed", "http://a/g", (new HTMLLinkProcessor ()).extract ("../../../../g", baseURI));
    }
    public void test25 () throws HTMLParserException
    {
        assertEquals ("test25 failed", "http://a/./g", (new HTMLLinkProcessor ()).extract ("/./g", baseURI));
    }
    public void test26 () throws HTMLParserException
    {
        assertEquals ("test26 failed", "http://a/../g", (new HTMLLinkProcessor ()).extract ("/../g", baseURI));
    }
    public void test27 () throws HTMLParserException
    {
        assertEquals ("test27 failed", "http://a/b/c/g.", (new HTMLLinkProcessor ()).extract ("g.", baseURI));
    }
    public void test28 () throws HTMLParserException
    {
        assertEquals ("test28 failed", "http://a/b/c/.g", (new HTMLLinkProcessor ()).extract (".g", baseURI));
    }
    public void test29 () throws HTMLParserException
    {
        assertEquals ("test29 failed", "http://a/b/c/g..", (new HTMLLinkProcessor ()).extract ("g..", baseURI));
    }
    public void test30 () throws HTMLParserException
    {
        assertEquals ("test30 failed", "http://a/b/c/..g", (new HTMLLinkProcessor ()).extract ("..g", baseURI));
    }
    public void test31 () throws HTMLParserException
    {
        assertEquals ("test31 failed", "http://a/b/g", (new HTMLLinkProcessor ()).extract ("./../g", baseURI));
    }
    public void test32 () throws HTMLParserException
    {
        assertEquals ("test32 failed", "http://a/b/c/g/", (new HTMLLinkProcessor ()).extract ("./g/.", baseURI));
    }
    public void test33 () throws HTMLParserException
    {
        assertEquals ("test33 failed", "http://a/b/c/g/h", (new HTMLLinkProcessor ()).extract ("g/./h", baseURI));
    }
    public void test34 () throws HTMLParserException
    {
        assertEquals ("test34 failed", "http://a/b/c/h", (new HTMLLinkProcessor ()).extract ("g/../h", baseURI));
    }
    public void test35 () throws HTMLParserException
    {
        assertEquals ("test35 failed", "http://a/b/c/g;x=1/y", (new HTMLLinkProcessor ()).extract ("g;x=1/./y", baseURI));
    }
    public void test36 () throws HTMLParserException
    {
        assertEquals ("test36 failed", "http://a/b/c/y", (new HTMLLinkProcessor ()).extract ("g;x=1/../y", baseURI));
    }
    public void test37 () throws HTMLParserException
    {
        assertEquals ("test37 failed", "http://a/b/c/g?y/./x", (new HTMLLinkProcessor ()).extract ("g?y/./x", baseURI));
    }
    public void test38 () throws HTMLParserException
    {
        assertEquals ("test38 failed", "http://a/b/c/g?y/../x", (new HTMLLinkProcessor ()).extract ("g?y/../x", baseURI));
    }
    public void test39 () throws HTMLParserException
    {
        assertEquals ("test39 failed", "http://a/b/c/g#s/./x", (new HTMLLinkProcessor ()).extract ("g#s/./x", baseURI));
    }
    public void test40 () throws HTMLParserException
    {
        assertEquals ("test40 failed", "http://a/b/c/g#s/../x", (new HTMLLinkProcessor ()).extract ("g#s/../x", baseURI));
    }
//    public void test41 () throws HTMLParserException
//    {
//        assertEquals ("test41 failed", "http:g", (new HTMLLinkProcessor ()).extract ("http:g", baseURI));
//    }
    public void test41 () throws HTMLParserException
    {
        assertEquals ("test41 failed", "http://a/b/c/g", (new HTMLLinkProcessor ()).extract ("http:g", baseURI));
    }
}
