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

package com.kizna.htmlTests.scannersTests;

import junit.framework.TestSuite;
/**
 * Insert the type's description here.
 * Creation date: (6/17/2001 6:07:04 PM)
 * @author: Administrator
 */
public class AllTests extends junit.framework.TestCase 
{
/**
 * AllTests constructor comment.
 * @param name java.lang.String
 */
public AllTests(String name) {
	super(name);
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2001 6:07:15 PM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() {
	TestSuite suite = new TestSuite();
	suite.addTest(HTMLTagScannerTest.suite());
	suite.addTest(HTMLAppletScannerTest.suite());
	suite.addTest(HTMLScriptScannerTest.suite());
	suite.addTest(HTMLImageScannerTest.suite());
	suite.addTest(HTMLLinkScannerTest.suite());
	suite.addTest(HTMLStyleScannerTest.suite());	
	suite.addTest(HTMLMetaTagScannerTest.suite());			
	suite.addTest(HTMLTitleScannerTest.suite());				
	suite.addTest(HTMLFormScannerTest.suite());	
	suite.addTest(HTMLFrameScannerTest.suite());	
	suite.addTest(HTMLFrameSetScannerTest.suite());
	suite.addTest(HTMLInputTagScannerTest.suite());
	suite.addTest(HTMLOptionTagScannerTest.suite());
	suite.addTest(HTMLSelectTagScannerTest.suite());
	suite.addTest(HTMLTextareaTagScannerTest.suite());
	suite.addTest(HTMLBaseHREFScannerTest.suite());
	suite.addTest(HTMLJspScannerTest.suite());	
	return suite;
}
}
