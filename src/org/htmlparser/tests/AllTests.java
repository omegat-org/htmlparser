// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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
public static void main(String[] args) {
	new junit.awtui.TestRunner().start(new String[] {"org.htmlparser.tests.AllTests"});
}
/**
 * Insert the method's description here.
 * Creation date: (6/17/2001 6:07:15 PM)
 * @return junit.framework.TestSuite
 */
public static TestSuite suite() {
	TestSuite suite = new TestSuite();
	
	suite.addTest(HTMLStringNodeTest.suite());
	suite.addTest(HTMLRemarkNodeTest.suite());
	suite.addTest(HTMLParserTest.suite());
	suite.addTest(org.htmlparser.tests.scannersTests.AllTests.suite());
	suite.addTest(org.htmlparser.tests.utilTests.AllTests.suite());
	suite.addTest(org.htmlparser.tests.tagTests.AllTests.suite());
	return suite;
} 
}