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


package org.htmlparser;

//////////////////
// Java Imports //
//////////////////
import java.io.*;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.EndTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.parserHelper.*;
import org.htmlparser.scanners.*;

/**
 * HTMLReader builds on the BufferedReader, providing methods to read one element
 * at a time
 */
public class NodeReader extends BufferedReader
{
	public static final String DECIPHER_ERROR="HTMLReader.readElement() : Error occurred while trying to decipher the tag using scanners";
	protected int posInLine=-1;
	protected String line;
	protected Node node = null;
	protected TagScanner previousOpenScanner = null;
	protected String url;
	private Parser parser;
	private boolean tagUpgraded=false;
	private int lineCount;
	private String previousLine;
	private StringParser stringParser = new StringParser();
	private RemarkNodeParser remarkNodeParser = new RemarkNodeParser();

	/**
	 * The constructor takes in a reader object, it's length and the url to be read.
	 */
	public NodeReader(Reader in,int len,String url)
	{
		super(in, len);
		this.url = url;
		this.parser = null;		
		this.lineCount = 1;
	}
	/**
	 * This constructor basically overrides the existing constructor in the
	 * BufferedReader class.
     * The URL defaults to an empty string.
     * @see #HTMLReader(Reader,int,String)
	 */

	public NodeReader(Reader in, int len)
	{
		this(in,len,"");
	}
	/**
	 * The constructor takes in a reader object, and the url to be read.
     * The buffer size defaults to 8192.
     * @see #HTMLReader(Reader,int,String)
	 */
	public NodeReader(Reader in,String url)
	{
		this(in, 8192, url);
	}
    
    /**
     * Get the url for this reader.
     * @return The url specified in the constructor;
     */
    public String getURL ()
    {
        return (url);
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
	 * @return org.htmlparser.HTMLParser
	 */
	public Parser getParser() {
		return parser;
	}
	/**
	 * Gets the previousOpenScanner.
	 * @return Returns a HTMLTagScanner
	 */
	public TagScanner getPreviousOpenScanner() {
		return previousOpenScanner;
	}
	/**
	 * Read the next element
	 * @return HTMLNode - The next node
 	 */
	public Node readElement() throws ParserException
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
            
            if ('<' == line.charAt (posInLine))
            {
                node = remarkNodeParser.find(this,line,posInLine);
                if (node!=null) return node;
                node = Tag.find(this,line,posInLine);
                if (node!=null)
                {
                    Tag tag = (Tag)node;
                    try
                    {
                        node = tag.scan(parser.getScanners(),url,this);
                        return node;
                    }
                    catch (Exception e)
                    {			
                        StringBuffer msgBuffer = new StringBuffer();
                        msgBuffer.append(DECIPHER_ERROR+"\n" +                        	"    Tag being processed : "+tag.getTagName()+"\n" +                        	"    Current Tag Line : "+tag.getTagLine()
                        ); 
                        appendLineDetails(msgBuffer);
                        ParserException ex = new ParserException(msgBuffer.toString(),e);

                        parser.getFeedback().error(msgBuffer.toString(),ex);
                        throw ex;
                    }
                }

                node = EndTag.find(line,posInLine);
                if (node!=null) return node;
            }
            else
            {
                node = stringParser.find(this,line,posInLine);
                if (node!=null) return node;
            }
		
			return null;
		}
		catch (Exception e) {
			StringBuffer msgBuffer = new StringBuffer("HTMLReader.readElement() : Error occurred while trying to read the next element,");
			appendLineDetails(msgBuffer);
			ParserException ex = new ParserException(msgBuffer.toString(),e);
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
	public void setParser(Parser newParser) {
		parser = newParser;
	}
	/**
	 * Sets the previousOpenScanner.
	 * @param previousOpenScanner The previousOpenScanner to set
	 */
	public void setPreviousOpenScanner(TagScanner previousOpenScanner) {
		this.previousOpenScanner = previousOpenScanner;
	}
	
	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static void setLineSeparator(String lineSeparator)
	{
		Node.setLineSeparator(lineSeparator);	
	}
	
	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static String getLineSeparator()
	{
		return (Node.getLineSeparator());
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

	/**
	 * Sets the lineCount.
	 * @param lineCount The lineCount to set
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * Sets the posInLine.
	 * @param posInLine The posInLine to set
	 */
	public void setPosInLine(int posInLine) {
		this.posInLine = posInLine;
	}


	public void reset() throws IOException {
		super.reset();
		lineCount = 1;
		posInLine = -1;
	}

	public StringParser getStringParser() {
		return stringParser;
	}
}
