package org.htmlparser.tests;

import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.scanners.HTMLLinkScanner;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.HTMLEnumeration;

public class InstanceofPerformanceTest {
	HTMLNode node;
	public void setUp() throws Exception {
		HTMLParser parser =
			HTMLParser.createParser(
				"<A HREF=\"somelink.html\">sometext</A>"
			);
		parser.addScanner(new HTMLLinkScanner(""));
		HTMLEnumeration e = parser.elements();
		node = e.nextNode();
	}
	public void doInstanceofTest(long [] time,int index, long numTimes) {
		System.out.println("doInstanceofTest("+index+")");
		long start = System.currentTimeMillis();
		for (long i=0;i<numTimes;i++)
			if (node instanceof HTMLTag) {}
			else if (node instanceof HTMLEndTag) {}
			else if (node instanceof HTMLStringNode) {}
		long end = System.currentTimeMillis();
		time[index] = end-start;
	}
	
	public void doGetTypeTest(long [] time,int index, long numTimes) {
		System.out.println("doGetTypeTest("+index+")");
		long start = System.currentTimeMillis();
		for (long i=0;i<numTimes;i++)
			if (node.getType()==HTMLTag.TYPE) {} 
			else if (node.getType()==HTMLEndTag.TYPE) {}
			else if (node.getType()==HTMLStringNode.TYPE) {}
		long end = System.currentTimeMillis();
		time[index] = end-start;
	}

	public void perform() {
		int numTimes = 50;
		long time1[] = new long[numTimes], 
		time2[] = new long[numTimes];
		
		for (int i=0;i<numTimes;i++)
			doInstanceofTest(time1,i,i*10000000);
		
		for (int i=0;i<numTimes;i++)
			doGetTypeTest(time2,i,i*10000000);
		
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
