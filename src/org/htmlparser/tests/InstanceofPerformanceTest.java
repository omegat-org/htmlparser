// HTMLParser Library v1_4_20030713 - A java-based parser for HTML
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

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tests.scannersTests.FormScannerTest;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.SimpleNodeIterator;

public class InstanceofPerformanceTest {
	FormTag formTag;
	Vector formChildren;
	public void setUp() throws Exception {
		Parser parser =
			Parser.createParser(
				FormScannerTest.FORM_HTML
			);
		parser.registerScanners();
		NodeIterator e = parser.elements();
		Node node = e.nextNode();
		formTag = (FormTag)node;
		formChildren = new Vector();
		for (SimpleNodeIterator se = formTag.children();se.hasMoreNodes();) {
			formChildren.addElement(se.nextNode());
		}
	}
	
	public void doInstanceofTest(long [] time,int index, long numTimes) {
		System.out.println("doInstanceofTest("+index+")");
		long start = System.currentTimeMillis();
		for (long i=0;i<numTimes;i++) {
			for (Enumeration e = formChildren.elements();e.hasMoreElements();) {
				Node node = (Node)e.nextElement();				
			}
		}
		long end = System.currentTimeMillis();
		time[index] = end-start;
	}
	
	public void doGetTypeTest(long [] time,int index, long numTimes) {
		System.out.println("doGetTypeTest("+index+")");
		long start = System.currentTimeMillis();
		for (long i=0;i<numTimes;i++) {
			for (SimpleNodeIterator e = formTag.children();e.hasMoreNodes();) {
				Node node = e.nextNode();
			}
		}
		long end = System.currentTimeMillis();
		time[index] = end-start;
	}

	public void perform() {
		int numTimes = 30;
		long time1[] = new long[numTimes], 
		time2[] = new long[numTimes];
		
		for (int i=0;i<numTimes;i++)
			doInstanceofTest(time1,i,i*10000);
		
		for (int i=0;i<numTimes;i++)
			doGetTypeTest(time2,i,i*10000);
		
		print(time1,time2);
	}

	public void print(long [] time1, long [] time2) {
		for (int i=0;i<time1.length;i++) {
			System.out.println(i*1000000+":"+","+time1[i]+"  "+time2[i]);
		}
	}
	public static void main(String [] args) throws Exception {
		InstanceofPerformanceTest test = 
			new InstanceofPerformanceTest();
		test.setUp();
		test.perform();
	}
}
