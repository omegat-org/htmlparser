package com.kizna.htmlTests.scannersTests;

import com.kizna.html.scanners.HTMLFormScanner;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLFormScannerTest extends TestCase {
	public HTMLFormScannerTest(String name) {
		super(name);
	}
	public void testEvaluate() {		
		String line1="form method=\"post\" onsubmit=\"return implementsearch()\" name=frmsearch id=form";
		String line2="FORM method=\"post\" onsubmit=\"return implementsearch()\" name=frmsearch id=form";
		String line3="Form method=\"post\" onsubmit=\"return implementsearch()\" name=frmsearch id=form";
		HTMLFormScanner formScanner = new HTMLFormScanner("");
		assertTrue("Line 1",formScanner.evaluate(line1,null));
		assertTrue("Line 2",formScanner.evaluate(line2,null));
		assertTrue("Line 3",formScanner.evaluate(line3,null));
	}
	public static TestSuite suite() {
		return new TestSuite(HTMLFormScannerTest.class);
	}
}
