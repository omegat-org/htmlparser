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

package org.htmlparser.tests.tagTests;

import junit.framework.TestSuite;

public class AllTests extends junit.framework.TestCase 
{
	public AllTests(String name) {
		super(name);
	}

	public static TestSuite suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(HTMLJspTagTest.class);
		suite.addTestSuite(HTMLScriptTagTest.class);
		suite.addTestSuite(HTMLImageTagTest.class);
		suite.addTestSuite(HTMLLinkTagTest.class);		
		suite.addTestSuite(HTMLTagTest.class);
		suite.addTestSuite(HTMLTitleTagTest.class);
		suite.addTestSuite(HTMLDoctypeTagTest.class);
		suite.addTestSuite(HTMLEndTagTest.class);
		suite.addTestSuite(HTMLMetaTagTest.class);
		suite.addTestSuite(HTMLStyleTagTest.class);
		suite.addTestSuite(HTMLAppletTagTest.class);
		suite.addTestSuite(HTMLFrameTagTest.class);
		suite.addTestSuite(HTMLFrameSetTagTest.class);	
		suite.addTestSuite(HTMLInputTagTest.class);
		suite.addTestSuite(HTMLOptionTagTest.class);
		suite.addTestSuite(HTMLSelectTagTest.class);
		suite.addTestSuite(HTMLTextareaTagTest.class);
		suite.addTestSuite(HTMLFormTagTest.class);
		suite.addTestSuite(HTMLBaseHREFTagTest.class);
		return suite; 
	}
}
