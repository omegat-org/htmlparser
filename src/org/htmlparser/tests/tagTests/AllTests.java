// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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
		TestSuite suite = new TestSuite("Tag Tests");
		suite.addTestSuite(JspTagTest.class);
		suite.addTestSuite(ScriptTagTest.class);
		suite.addTestSuite(ImageTagTest.class);
		suite.addTestSuite(LinkTagTest.class);		
		suite.addTestSuite(TagTest.class);
		suite.addTestSuite(TitleTagTest.class);
		suite.addTestSuite(DoctypeTagTest.class);
		suite.addTestSuite(EndTagTest.class);
		suite.addTestSuite(MetaTagTest.class);
		suite.addTestSuite(StyleTagTest.class);
		suite.addTestSuite(AppletTagTest.class);
		suite.addTestSuite(FrameTagTest.class);
		suite.addTestSuite(FrameSetTagTest.class);	
		suite.addTestSuite(InputTagTest.class);
		suite.addTestSuite(OptionTagTest.class);
		suite.addTestSuite(SelectTagTest.class);
		suite.addTestSuite(TextareaTagTest.class);
		suite.addTestSuite(FormTagTest.class);
		suite.addTestSuite(BaseHREFTagTest.class);
		suite.addTestSuite(ObjectCollectionTest.class);
		suite.addTestSuite(BodyTagTest.class);
		return suite; 
	}
}
