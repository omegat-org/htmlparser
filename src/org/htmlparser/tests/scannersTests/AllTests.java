// HTMLParser Library v1_3_20030202 - A java-based parser for HTML
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
// HTMLParser Library v1_3_20030202 - A java-based parser for HTML
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

package org.htmlparser.tests.scannersTests;

import junit.framework.TestSuite;

public class AllTests extends junit.framework.TestCase 
{

	public AllTests(String name) {
		super(name);
	}
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("Scanner Tests");
		suite.addTestSuite(HTMLTagScannerTest.class);
		suite.addTestSuite(HTMLAppletScannerTest.class);
		suite.addTestSuite(HTMLScriptScannerTest.class);
		suite.addTestSuite(HTMLImageScannerTest.class);
		suite.addTestSuite(HTMLLinkScannerTest.class);
		suite.addTestSuite(HTMLStyleScannerTest.class);	
		suite.addTestSuite(HTMLMetaTagScannerTest.class);			
		suite.addTestSuite(HTMLTitleScannerTest.class);				
		suite.addTestSuite(HTMLFormScannerTest.class);	
		suite.addTestSuite(HTMLFrameScannerTest.class);	
		suite.addTestSuite(HTMLFrameSetScannerTest.class);
		suite.addTestSuite(HTMLInputTagScannerTest.class);
		suite.addTestSuite(HTMLOptionTagScannerTest.class);
		suite.addTestSuite(HTMLSelectTagScannerTest.class);
		suite.addTestSuite(HTMLTextareaTagScannerTest.class);
		suite.addTestSuite(HTMLBaseHREFScannerTest.class);
		suite.addTestSuite(HTMLJspScannerTest.class);	
		suite.addTestSuite(TableScannerTest.class);	
		suite.addTestSuite(SpanScannerTest.class);	
		suite.addTestSuite(DivScannerTest.class);
		suite.addTestSuite(LabelScannerTest.class);
		return suite;
	}
}
