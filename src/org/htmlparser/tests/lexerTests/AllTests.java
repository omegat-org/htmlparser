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

package org.htmlparser.tests.lexerTests;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase
{
    public AllTests (String name)
    {
        super (name);
    }

    public static TestSuite suite ()
    {
        TestSuite suite = new TestSuite ("Lexer Tests");
        suite.addTestSuite (StreamTests.class);
        suite.addTestSuite (SourceTests.class);
        suite.addTestSuite (PageTests.class);
        suite.addTestSuite (PageIndexTests.class);
        suite.addTestSuite (LexerTests.class);
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

        /*
         * from http://www.mail-archive.com/commons-user%40jakarta.apache.org/msg02958.html
         *
         * The problem is within the UI test runners of JUnit. They bring
         * with them a custom classloader, which causes the
         * LogConfigurationException. Unfortunately Log4j doesn't work
         * either.
         *
         * Solution: Disable "Reload classes every run" or start JUnit with
         * command line option -noloading before the name of the Testsuite.
         */

        // append the test class
        arguments = new String[args.length + 2];
        System.arraycopy (args, 0, arguments, 0, args.length);
        arguments[arguments.length - 2] = "-noloading";
        arguments[arguments.length - 1] = "org.htmlparser.tests.lexerTests.AllTests";

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
