package org.htmlparser.tests;

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.tags.HTMLFormTag;
import org.htmlparser.tests.scannersTests.HTMLFormScannerTest;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.SimpleEnumeration;

public class InstanceofPerformanceTest {
	HTMLFormTag formTag;
	Vector formChildren;
	public void setUp() throws Exception {
		HTMLParser parser =
			HTMLParser.createParser(
				HTMLFormScannerTest.FORM_HTML
			);
		parser.registerScanners();
		HTMLEnumeration e = parser.elements();
		HTMLNode node = e.nextNode();
		formTag = (HTMLFormTag)node;
		formChildren = new Vector();
		for (SimpleEnumeration se = formTag.children();se.hasMoreNodes();) {
			formChildren.addElement(se.nextNode());
		}
	}
	
	public void doInstanceofTest(long [] time,int index, long numTimes) {
		System.out.println("doInstanceofTest("+index+")");
		long start = System.currentTimeMillis();
		for (long i=0;i<numTimes;i++) {
			for (Enumeration e = formChildren.elements();e.hasMoreElements();) {
				HTMLNode node = (HTMLNode)e.nextElement();				
			}
		}
		long end = System.currentTimeMillis();
		time[index] = end-start;
	}
	
	public void doGetTypeTest(long [] time,int index, long numTimes) {
		System.out.println("doGetTypeTest("+index+")");
		long start = System.currentTimeMillis();
		for (long i=0;i<numTimes;i++) {
			for (SimpleEnumeration e = formTag.children();e.hasMoreNodes();) {
				HTMLNode node = e.nextNode();
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
