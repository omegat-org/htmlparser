package com.kizna.htmlTests;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

public class PerformanceTest {
	private int numTimes;
	private String file;
	/**
	 * Try to parse the given page the given no of times
	 * Print average time taken
	 * @param file File to be parsed
	 * @param numTimes number of times the test should be repeated
	 */
	public PerformanceTest(String file, int numTimes) {
		this.file = file;
		this.numTimes = numTimes;		
	}
	public void beginTestWithoutScanners() throws HTMLParserException {
		HTMLParser parser;
		long sumTimes=0;
		double avg=0;
		System.out.println("***************************************");
		System.out.println("*  Test Without Scanners Registered   *");
		System.out.println("***************************************");				
		for (int i=0;i<=numTimes;i++) {
			// Create the parser object
			parser = new HTMLParser(file,new DefaultHTMLParserFeedback());
			HTMLNode node;
			long start=System.currentTimeMillis();
			for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
				node = e.nextHTMLNode();
			}
			long elapsedTime=System.currentTimeMillis()-start;
			if (i!=0)
			sumTimes += elapsedTime;
			System.out.print("Iteration "+i);
			if (i==0) System.out.print(" (not counted)");
			System.out.println(" : time taken = "+elapsedTime+" ms");
		}
		avg = sumTimes/(float)numTimes;
		System.out.println("***************************************");
		System.out.println("Average Time : "+avg+" ms");
		System.out.println("***************************************");
	}
	public void beginTestWithScanners() throws HTMLParserException {
		HTMLParser parser;
		long sumTimes=0;
		double avg=0;
		System.out.println("***************************************");
		System.out.println("*    Test With Scanners Registered    *");
		System.out.println("***************************************");					
		for (int i=0;i<=numTimes;i++) {
			// Create the parser object
			parser = new HTMLParser(file,new DefaultHTMLParserFeedback());
			parser.registerScanners();
			HTMLNode node;
			long start=System.currentTimeMillis();
			for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();) {
				node = e.nextHTMLNode();
			}
			long elapsedTime=System.currentTimeMillis()-start;
			if (i!=0)
			sumTimes += elapsedTime;
			System.out.print("Iteration "+i);
			if (i==0) System.out.print(" (not counted)");
			System.out.println(" : time taken = "+elapsedTime+" ms");
		}
		avg = sumTimes/(float)numTimes;
		System.out.println("***************************************");
		System.out.println("Average Time : "+avg+" ms");
		System.out.println("***************************************");
	}
	
	public static void main(String[] args) {
		if (args.length<2) {
			System.err.println("Syntax Error.");
			System.err.println("Params needed for test : <file/url to be parsed> <number of iterations>");
			System.exit(-1);
		}
		String file = args[0];
		String numTimesString = args[1];
		int numTimes = Integer.decode(numTimesString).intValue();
		PerformanceTest pt = new PerformanceTest(file,numTimes);
		try {
			pt.beginTestWithoutScanners();
			pt.beginTestWithScanners();
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
	}
}

