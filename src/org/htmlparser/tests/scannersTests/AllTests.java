// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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
// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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

import org.htmlparser.tests.ParserTestCase;

public class AllTests extends ParserTestCase
{
    static
    {
        System.setProperty ("org.htmlparser.tests.scannersTests.AllTests", "AllTests");
    }

    public AllTests(String name) {
        super(name);
    }

    public static TestSuite suite() {
        TestSuite suite = new TestSuite("Scanner Tests");
        suite.addTestSuite(TagScannerTest.class);
        suite.addTestSuite(AppletScannerTest.class);
        suite.addTestSuite(ScriptScannerTest.class);
        suite.addTestSuite(ImageScannerTest.class);
        suite.addTestSuite(LinkScannerTest.class);
        suite.addTestSuite(StyleScannerTest.class);
        suite.addTestSuite(MetaTagScannerTest.class);
        suite.addTestSuite(TitleScannerTest.class);
        suite.addTestSuite(FormScannerTest.class);
        suite.addTestSuite(FrameScannerTest.class);
        suite.addTestSuite(FrameSetScannerTest.class);
        suite.addTestSuite(InputTagScannerTest.class);
        suite.addTestSuite(OptionTagScannerTest.class);
        suite.addTestSuite(SelectTagScannerTest.class);
        suite.addTestSuite(TextareaTagScannerTest.class);
        suite.addTestSuite(BaseHREFScannerTest.class);
        suite.addTestSuite(JspScannerTest.class);
        suite.addTestSuite(TableScannerTest.class);
        suite.addTestSuite(SpanScannerTest.class);
        suite.addTestSuite(DivScannerTest.class);
        suite.addTestSuite(LabelScannerTest.class);
        suite.addTestSuite(BodyScannerTest.class);
        suite.addTestSuite(CompositeTagScannerTest.class);
        suite.addTestSuite(HeadScannerTest.class);
        suite.addTestSuite(BulletListScannerTest.class);
        suite.addTestSuite(BulletScannerTest.class);
        suite.addTestSuite(HtmlTest.class);
        suite.addTestSuite(XmlEndTagScanningTest.class);
        return suite;
    }
}

