// HTMLParser Library v1_2_20021031 - A java-based parser for HTML
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
// Postal Address : 
// Somik Raha
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com
package com.kizna.html;

//////////////////
// Java Imports //
//////////////////
import java.io.*;
import java.util.*;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.tags.HTMLEndTag;
import com.kizna.html.util.HTMLParserException;
import com.kizna.html.scanners.*;

/**
 * HTMLReader builds on the BufferedReader, providing methods to read one element
 * at a time
 */
public class HTMLReader extends BufferedReader
{
	public static final String DECIPHER_ERROR="HTMLReader.readElement() : Error occurred while trying to decipher the tag using scanners";
	protected int posInLine=-1;
	protected String line;
	protected HTMLNode node = null;
	protected HTMLTagScanner previousOpenScanner = null;
	protected String url;
	private HTMLParser parser;
	private boolean tagUpgraded=false;
	private int lineCount;
	private String previousLine;
	/**
	 * This constructor basically overrides the existing constructor in the
	 * BufferedReader class.
	 */

	public HTMLReader(Reader in, int len)
	{
		super(in,len);
		this.parser = null;
		this.lineCount = 1;
	}
	/**
	 * The constructor takes in a reader object, and the url to be read.
	 */
	public HTMLReader(Reader in,String url)
	{
		super(in);
		this.url = url;
		this.parser = null;		
		this.lineCount = 1;
	}
	/**
	 * This method is intended to be called only by scanners, when a situation of dirty html has arisen, 
	 * and action has been taken to correct the parsed tags. For e.g. if we have html of the form :
	 * <pre>
	 * <a href="somelink.html"><img src=...><td><tr><a href="someotherlink.html">...</a>
	 * </pre>
	 * Now to salvage the first link, we'd probably like to insert an end tag somewhere (typically before the
	 * second begin link tag). So that the parsing continues uninterrupted, we will need to change the existing
	 * line being parsed, to contain the end tag in it. 
	 */
	public void changeLine(String line) {
		this.line = line;
	}
	public String getCurrentLine() {
		return line;
	}
	/**
	 * This method is useful when designing your own scanners. You might need to find out what is the location where the
	 * reader has stopped last.
	 * @return int Last position read by the reader
	 */
	public int getLastReadPosition() {
		if (node!=null) return node.elementEnd(); else
		return 0;
	}
	/*
	 * Read the next line
	 * @return String containing the line
	 */
	public String getNextLine()
	{
		try
		{
			previousLine = line;
			line = readLine();
			lineCount++;
			posInLine = 0;
			return line;
		}
		catch (IOException e)
		{
			System.err.println("I/O Exception occurred while reading!");
		}
		return null;
	}
	/**
	 * Returns the parser object for which this reader exists
	 * @return com.kizna.html.HTMLParser
	 */
	public HTMLParser getParser() {
		return parser;
	}
	/**
	 * Gets the previousOpenScanner.
	 * @return Returns a HTMLTagScanner
	 */
	public HTMLTagScanner getPreviousOpenScanner() {
		return previousOpenScanner;
	}
	/**
	 * Read the next element
	 * @return HTMLNode - The next node
 	 */
	public HTMLNode readElement() throws HTMLParserException
	{
		try {
			if (readNextLine())
			{
				do
				{
					line = getNextLine();
				}
				while (line!=null && line.length()==0);
	
			} else
			posInLine=node.elementEnd()+1;
			if (line==null) return null;
			node = HTMLRemarkNode.find(this,line,posInLine);
			if (node!=null) return node;
	
			node = HTMLStringNode.find(this,line,posInLine);
			if (node!=null) return node;
			
			node = HTMLTag.find(this,line,posInLine);
			if (node!=null)
			{
				HTMLTag tag = (HTMLTag)node;
				try
				{
					node = tag.scan(parser.getScanners(),url,this);
					return node;
				}
				catch (Exception e)
				{			
					StringBuffer msgBuffer = new StringBuffer();
					msgBuffer.append(DECIPHER_ERROR);
					appendLineDetails(msgBuffer);
					HTMLParserException ex = new HTMLParserException(msgBuffer.toString(),e);
					
					parser.getFeedback().error(msgBuffer.toString(),ex);
					throw ex;
				}
			}
	
			// If we couldnt get a string, then it is probably an end tag
			
			node = HTMLEndTag.find(line,posInLine);
			if (node!=null) return node;
	
		
			return null;
		}
		catch (Exception e) {
			StringBuffer msgBuffer = new StringBuffer("HTMLReader.readElement() : Error occurred while trying to read the next element,");
			appendLineDetails(msgBuffer);
			HTMLParserException ex = new HTMLParserException(msgBuffer.toString(),e);
			parser.getFeedback().error(msgBuffer.toString(),ex);
			throw ex;			
		}
	}
	public void appendLineDetails(StringBuffer msgBuffer) {
		msgBuffer.append("\nat Line ");
		msgBuffer.append(getLineCount());
		msgBuffer.append(" : ");
		msgBuffer.append(getLine());
		msgBuffer.append("\nPrevious Line ").append(getLineCount()-1);
		msgBuffer.append(" : ").append(getPreviousLine());
	}
	/**
	 * Do we need to read the next line ?
	 * @return true - yes/ false - no
	 */
	protected boolean readNextLine()
	{
		if (posInLine==-1 || (line!=null && node.elementEnd()+1>=line.length()))
				return true;
		else return false;
	}
	/**
	 * The setParser method is used by the parser to put its own object into the reader. This happens internally,
	 * so this method is not generally for use by the developer or the user.
	 */
	public void setParser(HTMLParser newParser) {
		parser = newParser;
	}
	/**
	 * Sets the previousOpenScanner.
	 * @param previousOpenScanner The previousOpenScanner to set
	 */
	public void setPreviousOpenScanner(HTMLTagScanner previousOpenScanner) {
		this.previousOpenScanner = previousOpenScanner;
	}
	
	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static void setLineSeparator(String lineSeparator)
	{
		HTMLNode.setLineSeparator(lineSeparator);	
	}
	
	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static String getLineSeparator()
	{
		return (HTMLNode.getLineSeparator());
	}
	/**
	 * Returns the lineCount.
	 * @return int
	 */
	public int getLineCount() {
		return lineCount;
	}

	/**
	 * Returns the previousLine.
	 * @return String
	 */
	public String getPreviousLine() {
		return previousLine;
	}

	/**
	 * Returns the line.
	 * @return String
	 */
	public String getLine() {
		return line;
	}

}
