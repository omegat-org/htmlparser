// HTMLParser Library v1_4_20030525 - A java-based parser for HTML
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
// HTMLParser Library v1_4_20030525 - A java-based parser for HTML
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
		return suite;
	}

    /**
     * Mainline for all suites of tests.
     * @param args Command line arguments. The following options
     * are understood:
     * <pre>
     * -text  -- use junit.textui.TestRunner
     * -awt   -- use junit.awtui.TestRunner
     * -swing -- use junit.swingui.TestRunner (default)
     * </pre>
     * All other options are passed on to the junit framework.
     */
	public static void main(String[] args)
    {
        String runner;
        int i;
        String arguments[];
        Class cls;

        runner = null;
        for (i = 0; (i < args.length) && (null == runner); i++)
        {
            if (args[i].equalsIgnoreCase ("-text"))
                runner = "junit.textui.TestRunner";
            else if (args[i].equalsIgnoreCase ("-awt"))
                runner = "junit.awtui.TestRunner";
            else if (args[i].equalsIgnoreCase ("-swing"))
                runner = "junit.swingui.TestRunner";
        }
        if (null != runner)
        {
            // remove it from the arguments
            arguments = new String[args.length - 1];
            System.arraycopy (args, 0, arguments, 0, i - 1);
            System.arraycopy (args, i, arguments, i - 1, args.length - i);
            args = arguments;
        }
        else
            runner = "junit.swingui.TestRunner";

        // append the test class
        arguments = new String[args.length + 1];
        System.arraycopy (args, 0, arguments, 0, args.length);
        arguments[args.length] = "org.htmlparser.tests.scannersTests.AllTests";

        // invoke main() of the test runner
        try
        {
            cls = Class.forName (runner);
            java.lang.reflect.Method method = cls.getDeclaredMethod (
                "main", new Class[] { String[].class });
            method.invoke (
                null,
                new Object[] { arguments });
        }
        catch (Throwable t)
        {
            System.err.println (
                "cannot run unit test ("
                + t.getMessage ()
                + ")");
        }
	}
}

