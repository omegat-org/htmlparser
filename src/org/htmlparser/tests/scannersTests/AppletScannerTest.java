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

package org.htmlparser.tests.scannersTests;
import java.util.Enumeration;
import java.util.Hashtable;

import org.htmlparser.scanners.AppletScanner;
import org.htmlparser.tags.AppletTag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.ParserException;

public class AppletScannerTest extends ParserTestCase
{

	public AppletScannerTest(String name) {
		super(name);
	}
	
	public void testEvaluate() 
	{
		AppletScanner scanner = new AppletScanner("-a");
		boolean retVal = scanner.evaluate("   Applet ",null);
		assertEquals("Evaluation of APPLET tag",new Boolean(true),new Boolean(retVal));
	}

	public void testScan() throws ParserException
	{
		String [][]paramsData = {{"Param1","Value1"},{"Name","Somik"},{"Age","23"}};
		Hashtable paramsMap = new Hashtable();
		String testHTML = new String("<APPLET CODE=Myclass.class ARCHIVE=test.jar CODEBASE=www.kizna.com>\n");
		for (int i = 0;i<paramsData.length;i++)
		{
			testHTML+="<PARAM NAME=\""+paramsData[i][0]+"\" VALUE=\""+paramsData[i][1]+"\">\n";
			paramsMap.put(paramsData[i][0],paramsData[i][1]);
		}
		testHTML+=
			"</APPLET>\n"+
			"</HTML>";
		createParser(testHTML);
		
		// Register the applet scanner
		parser.addScanner(new AppletScanner("-a"));
			
		parseAndAssertNodeCount(2);
		assertTrue("Node should be an applet tag",node[0] instanceof AppletTag);
		// Check the data in the applet tag
		AppletTag appletTag = (AppletTag)node[0];
		assertEquals("Class Name","Myclass.class",appletTag.getAppletClass());
		assertEquals("Archive","test.jar",appletTag.getArchive());
		assertEquals("Codebase","www.kizna.com",appletTag.getCodeBase());
		// Check the params data
		int cnt = 0;
		for (Enumeration e = appletTag.getParameterNames();e.hasMoreElements();)
		{
			String paramName = (String)e.nextElement();
			String paramValue = appletTag.getAttribute(paramName);
			assertEquals("Param "+cnt+" value",paramsMap.get(paramName),paramValue);
			cnt++;
		}
		assertEquals("Number of params",new Integer(paramsData.length),new Integer(cnt));
	}
}