package com.kizna.htmlTests;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLParser;
import com.kizna.html.scanners.HTMLImageScanner;
import com.kizna.html.tags.HTMLImageTag;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FunctionalTests extends TestCase {

	/**
	 * Constructor for FunctionalTests.
	 * @param arg0
	 */
	public FunctionalTests(String arg0) {
		super(arg0);
	}
	/**
	 * Based on a suspected bug report by Annette Doyle,
	 * to check if the no of image tags are correctly 
	 * identified by the parser
	 */
	public void testNumImageTagsInYahooWithoutRegisteringScanners() {
		// First count the image tags as is
		int imgTagCount;
		imgTagCount = findImageTagCount();
		//System.out.println("Counted  "+imgTagCount+" image tags regularly");
		int parserImgTagCount = countImageTagsWithHTMLParser();
		//System.out.println("Counted "+parserImgTagCount+" image tags thru HTML Parser");
		assertEquals("Image Tag Count",imgTagCount,parserImgTagCount);	
	}
	public void testNumImageTagsInYahooWithRegisteredScanners() {
		// First count the image tags as is
		int imgTagCount;
		imgTagCount = findImageTagCount();
		//System.out.println("Counted  "+imgTagCount+" image tags regularly");
		int parserImgTagCount = countImageTagsWithHTMLParserScannersRegistered();
		//System.out.println("Counted "+parserImgTagCount+" image tags thru HTML Parser");
		assertEquals("Image Tag Count",imgTagCount,parserImgTagCount);	
	}	
	public int findImageTagCount() {
		int imgTagCount = 0;
		try {
			URL url = new URL("http://www.yahoo.com");
			InputStream is = url.openStream();
			//System.out.println("Opened Input Stream");
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(is));	
			imgTagCount = countImageTagsWithoutHTMLParser(reader);
			is.close();
		}
		catch (MalformedURLException e) {
			System.err.println("URL was malformed!");
		}
		catch (IOException e) {
			System.err.println("IO Exception occurred while trying to open stream");
		}
		return imgTagCount;
	}
	public int countImageTagsWithHTMLParser() {
		HTMLParser parser = new HTMLParser("http://www.yahoo.com");
		parser.addScanner(new HTMLImageScanner("-i"));
		int parserImgTagCount = 0;
		HTMLNode node;
		for (Enumeration e= parser.elements();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			if (node instanceof HTMLImageTag) {
				parserImgTagCount++;				
				//System.out.println(((HTMLImageTag)node).getTagLine());
			}		
		}
		return parserImgTagCount;
	}
	public int countImageTagsWithHTMLParserScannersRegistered() {
		HTMLParser parser = new HTMLParser("http://www.yahoo.com");
		parser.registerScanners();
		int parserImgTagCount = 0;
		HTMLNode node;
		for (Enumeration e= parser.elements();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			if (node instanceof HTMLImageTag) {
				parserImgTagCount++;				
				//System.out.println(((HTMLImageTag)node).getTagLine());
			}		
		}
		return parserImgTagCount;
	}

	public int countImageTagsWithoutHTMLParser(BufferedReader reader) throws IOException {
		String line;
		int imgTagCount = 0;
		do {
			line = reader.readLine();
			//System.out.println(line);
			if (line!=null) {
				// Check the line for image tags
				String newline = line.toUpperCase();
				int fromIndex = -1;
				do {
					fromIndex = newline.indexOf("<IMG",fromIndex+1);
					if (fromIndex!=-1) {
						imgTagCount++;
						//System.out.println(line);
					}
				}
				while (fromIndex!=-1);
			}
		}
		while (line!=null);
		return imgTagCount;
	}
	public static TestSuite suite() {
		return new TestSuite(FunctionalTests.class);
	}
}
