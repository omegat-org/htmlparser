package com.kizna.htmlTests;

import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;

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
	public void beginTestWithoutScanners() {
		HTMLParser parser;
		long sumTimes=0;
		double avg=0;
		System.out.println("***************************************");
		System.out.println("*  Test Without Scanners Registered   *");
		System.out.println("***************************************");				
		for (int i=0;i<numTimes;i++) {
			// Create the parser object
			parser = new HTMLParser(file);
			HTMLNode node;
			long start=System.currentTimeMillis();
			for (Enumeration e = parser.elements();e.hasMoreElements();) {
				node = (HTMLNode)e.nextElement();
			}
			long elapsedTime=System.currentTimeMillis()-start;
			sumTimes += elapsedTime;
			System.out.println("Iteration "+i+" : time taken = "+elapsedTime+" ms");
		}
		avg = sumTimes/(float)numTimes;
		System.out.println("***************************************");
		System.out.println("Average Time : "+avg+" ms");
		System.out.println("***************************************");
	}
	public void beginTestWithScanners() {
		HTMLParser parser;
		long sumTimes=0;
		double avg=0;
		System.out.println("***************************************");
		System.out.println("*    Test With Scanners Registered    *");
		System.out.println("***************************************");					
		for (int i=0;i<numTimes;i++) {
			// Create the parser object
			parser = new HTMLParser(file);
			parser.registerScanners();
			HTMLNode node;
			long start=System.currentTimeMillis();
			for (Enumeration e = parser.elements();e.hasMoreElements();) {
				node = (HTMLNode)e.nextElement();
			}
			long elapsedTime=System.currentTimeMillis()-start;
			sumTimes += elapsedTime;
			System.out.println("Iteration "+i+" : time taken = "+elapsedTime+" ms");
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
		pt.beginTestWithoutScanners();
		pt.beginTestWithScanners();
	}
}

