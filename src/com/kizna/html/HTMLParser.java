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
import java.net.*;
import java.util.*;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import com.kizna.html.scanners.*;
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.util.ChainedException;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLLinkProcessor;
import com.kizna.html.util.HTMLParserException;
import com.kizna.html.util.HTMLParserFeedback;
import com.kizna.html.util.HTMLTagParser;
/**
 * This is the class that the user will use, either to get an iterator into 
 * the html page or to directly parse the page and print the results
 * <BR>
 * Typical usage of the parser is as follows : <BR>
 * [1] Create a parser object - passing the URL and a feedback object to the parser<BR>
 * [2] Register the common scanners. See {@link #registerScanners()} <BR>
 * You wouldnt do this if you want to configure a custom lightweight parser. In that case, 
 * you would add the scanners of your choice using {@link #addScanner(HTMLTagScanner)}<BR>
 * [3] Enumerate through the elements from the parser object <BR>
 * It is important to note that the parsing occurs when you enumerate, ON DEMAND. This is a thread-safe way, 
 * and you only get the control back after a particular element is parsed and returned.
 * 
 * <BR>
 * Below is some sample code to parse Yahoo.com and print all the tags.
 * <pre>
 * HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * // In this example, we are registering all the common scanners
 * parser.registerScanners(); 
 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
 *    HTMLNode node = (HTMLNode)e.nextElement();
 * 	  node.print();
 * }
 * </pre>
 * Below is some sample code to parse Yahoo.com and print only the text information. This scanning
 * will run faster, as there are no scanners registered here.
 * <pre>
 * HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * // In this example, none of the scanners need to be registered
 * // as a string node is not a tag to be scanned for.
 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
 *    HTMLNode node = (HTMLNode)e.nextElement();
 *    if (node instanceof HTMLStringNode) {
 *        HTMLStringNode stringNode = (HTMLStringNode)node;
 *        System.out.println(stringNode.getText());
 *    }
 * }
 * </pre>
 * The above snippet will print out only the text contents in the html document.<br>
 * Here's another snippet that will only print out the link urls in a document. 
 * This is an example of adding a link scanner.
 * <pre>
 * HTMLParser parser = new HTMLParser("http://www.yahoo.com",new DefaultHTMLParserFeedback());
 * parser.addScanner(new HTMLLinkScanner("-l"));
 * for (Enumeration e = parser.elements();e.hasMoreElements();) {
 *    HTMLNode node = (HTMLNode)e.nextElement();
 *    if (node instanceof HTMLLinkTag) {
 *        HTMLLinkTag linkTag = (HTMLLinkTag)node;
 *        System.out.println(linkTag.getLink());
 *    }
 * }
 * </pre>
 *  @see HTMLParser#elements() 
 */
public class HTMLParser
{
	/**
	 * The URL or filename to be parsed.
	 */
	protected String resourceLocn;
	/** 
	 * The html reader associated with this parser
	 */
	protected HTMLReader reader;
	/**
	 * The last read HTML node.
	 */
	protected HTMLNode node;
	/**
	 * Keeps track of whether the first reading has been performed.
	 */
	protected boolean readFlag = false;
	private Hashtable scanners = new Hashtable();
	/**
	 * Keeps track if a connection was opened to a file or url
	 */
	private boolean connectionOpened=true;
	/**
	 * Feedback object
	 */
	private HTMLParserFeedback feedback;
	
	// Please dont change the formatting of the VERSION_STRING
	// below. This is done so as to facilitate the ant script
	public final static java.lang.String 
	VERSION_STRING="1.2 (Integration Build Oct 31, 2002)"
	;
	// End of formatting
	/**
	 * This constructor enables the construction of test cases, with readers
	 * associated with test string buffers. 
	 * @param reader com.kizna.html.HTMLReader
	 * @param feedback HTMLParserFeedback
	 */
	public HTMLParser(HTMLReader reader,HTMLParserFeedback feedback) 
	{
		this.reader = reader;
		this.feedback = feedback;
		HTMLTag.setTagParser(new HTMLTagParser(feedback));		
		reader.setParser(this);
		markBeginningOfStream(reader);
		connectionOpened=false;
	}


	public void markBeginningOfStream(HTMLReader reader) {
		try {
			reader.mark(5000);
		}
		catch (IOException e) {
			feedback.error("Could not mark the current location of the reader",new HTMLParserException(e));
		}
	}
	
	
	/**
	 * Creates a HTMLParser object with the location of the resource (URL or file)
	 * You would typicall create a DefaultHTMLParserFeedback object and pass it in
	 * @param resourceLocn Either the URL or the filename (autodetects)
	 * @param feedback HTMLParserFeedback
	 */
	public HTMLParser(String resourceLocn,HTMLParserFeedback feedback) throws HTMLParserException
	{
		try {
			this.resourceLocn = resourceLocn;
			this.feedback = feedback;
			HTMLTag.setTagParser(new HTMLTagParser(feedback));
			openConnection();
		}
		catch (Exception e) {
			String msg="Error in constructing the parser object for resource "+resourceLocn;
			HTMLParserException ex = new HTMLParserException(msg,e);
			feedback.error(msg,ex);
			throw ex;
		}
	}

	/**
	 * Creates a HTMLParser object with the location of the resource (URL or file)
	 * You would typicall create a DefaultHTMLParserFeedback object and pass it in
	 * @param resourceLocn Either the URL or the filename (autodetects)
	 */
	public HTMLParser(String resourceLocn) throws HTMLParserException
	{
		this(resourceLocn,new DefaultHTMLParserFeedback());
	}
	
	public HTMLParser(HTMLReader reader) 
	{
		this(reader,new DefaultHTMLParserFeedback());	
	}	
		
	/**
	 * Add a new Tag Scanner.
	 * In typical situations where you require a no-frills parser, use the registerScanners() method to add the most
	 * common parsers. But when you wish to either compose a parser with only certain scanners registered, use this method.
	 * It is advantageous to register only the scanners you want, in order to achieve faster parsing speed. This method 
	 * would also be of use when you have developed custom scanners, and need to register them into the parser.
	 * @param scanner HTMLTagScanner object (or derivative) to be added to the list of registered scanners
	 */
	public void addScanner(HTMLTagScanner scanner) {
		String ids[] = scanner.getID();
		for (int i=0;i<ids.length;i++) {
			scanners.put(ids[i],scanner);
		}
		scanner.setFeedback(feedback);
	}
	private String checkEnding(String link)
	{
		// Check if the link ends in html, htm, or /. If not, add a slash
		int l1 = link.indexOf("html");
		int l2 = link.indexOf("htm");
		int l3 = link.indexOf("php");
		int l4 = link.indexOf("jsp");
		return link;
	}
	/**
	 * Returns an iterator (enumeration) to the html nodes. Each node can be a tag/endtag/
	 * string/link/image<br>
	 * This is perhaps the most important method of this class. In typical situations, you will need to use
	 * the parser like this :
	 * <pre>
	 * HTMLParser parser = new HTMLParser("http://www.yahoo.com");
	 * parser.registerScanners();
	 * for (HTMLEnumeration e = parser.elements();e.hasMoreElements();) {
	 *    HTMLNode node = e.nextHTMLNode();
	 *    if (node instanceof HTMLStringNode) {
	 *      // Downcasting to HTMLStringNode
	 *      HTMLStringNode stringNode = (HTMLStringNode)node;
	 *      // Do whatever processing you want with the string node
	 *      System.out.println(stringNode.getText());
	 *    }
	 *    // Check for the node or tag that you want
	 *    if (node instanceof ...) {
	 *      // Downcast, and process
	 *    }
	 * }
	 * </pre>
	 */
	public HTMLEnumeration elements()
	{
		return new HTMLEnumeration()
		{
			public boolean hasMoreNodes() throws HTMLParserException
			{
				if (reader==null) return false;
				try
				{
					node = reader.readElement();
					readFlag=true;
				   if (node==null) {
				   		// Parser has completed. 
				   		// Re-initialize
				   		if (!connectionOpened) resetReader(); else openConnection();
						return false;
					}
					else
						return true;
				}
				catch (Exception e) {
					StringBuffer msgBuffer = new StringBuffer();
					msgBuffer.append("Unexpected Exception occurred in HTMLParser.hasMoreNodes()");
					msgBuffer.append(resourceLocn);
					msgBuffer.append(", in nextHTMLNode");
					reader.appendLineDetails(msgBuffer);
					HTMLParserException ex = new HTMLParserException(msgBuffer.toString(),e);
					feedback.error(msgBuffer.toString(),ex);
					throw ex;
				}

			}
			public void resetReader() throws HTMLParserException, IOException {
				if (!reader.markSupported()) throw new HTMLParserException("Mark is not supported!");
				reader.reset();
				reader.setLineCount(1);
				reader.setPosInLine(-1);
			}
			public HTMLNode nextHTMLNode() throws HTMLParserException
			{
				try
				{
					if (!readFlag) node = reader.readElement();
					return node;
				}
				catch (Exception e) {
					StringBuffer msgBuffer = new StringBuffer();
					msgBuffer.append("Unexpected Exception occurred while reading ");
					msgBuffer.append(resourceLocn);
					msgBuffer.append(", in nextHTMLNode");
					reader.appendLineDetails(msgBuffer);
					HTMLParserException ex = new HTMLParserException(msgBuffer.toString(),e);
					feedback.error(msgBuffer.toString(),ex);
					throw ex;
				}
			}

		};
	}
	/**
	 * Flush the current scanners registered. The registered scanners list becomes empty with this call.
	 */
	public void flushScanners() {
		scanners = new Hashtable();	
	}
	/**
	 * Get the number of scanners registered currently in the scanner.
	 * @return int number of scanners registered
	 */
	public int getNumScanners() {
		return scanners.size();	
	}
	/**
	 * Get an enumeration of scanners registered currently in the parser
	 * @return Enumeration of scanners currently registered in the parser
	 */
	public Hashtable getScanners() {
		return scanners;
	}
	/*
	 * The main program, which can be executed from the command line
	 */
	public static void main(String [] args)
	{
		System.out.println("HTMLParser v"+VERSION_STRING);
		if (args.length<1 || args[0].equals("-help"))
		{
			System.out.println();
			System.out.println("Syntax : java -jar htmlparser.jar <resourceLocn/website> -l");
			System.out.println("   <resourceLocn> the name of the file to be parsed (with complete path if not in current directory)");
			System.out.println("   -l Show only the link tags extracted from the document");
			System.out.println("   -i Show only the image tags extracted from the document");
			System.out.println("   -s Show only the Javascript code extracted from the document");
			System.out.println("   -t Show only the Style code extracted from the document");
			System.out.println("   -a Show only the Applet tag extracted from the document");
			System.out.println("   -j Parse JSP tags");	
			System.out.println("   -m Parse Meta tags");		
			System.out.println("   -T Extract the Title");
			System.out.println("   -f Extract forms");
			System.out.println("   -r Extract frameset");
			System.out.println("   -help This screen");
			System.out.println();
			System.out.println("HTML Parser home page : http://htmlparser.sourceforge.net");
			System.out.println();
			System.out.println("Example : java -jar htmlparser.jar http://www.yahoo.com");
			System.out.println();
			System.out.println("If you have any doubts, please join the HTMLParser mailing list (user/developer) from the HTML Parser home page instead of mailing any of the contributors directly. You will be surprised with the quality of open source support. ");
			System.exit(-1);
		}
		if (args[0].indexOf("http")!=-1 || args[0].indexOf("www.")!=-1)
			System.out.println("Parsing website "+args[0]);
		else	
		System.out.println("Parsing file "+args[0]+"...");
		try {
			HTMLParser parser = new HTMLParser(args[0],new DefaultHTMLParserFeedback());
			parser.registerScanners();
			try {
				if (args.length==2)
				{
					parser.parse(args[1]);
				} else
				parser.parse(null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (HTMLParserException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Opens the connection with the resource to begin reading, by creating a HTML reader
	 * object.
	 */
	private void openConnection() throws HTMLParserException
	{
		try
		{
			if (HTMLLinkProcessor.isURL(resourceLocn))
			{ 
				reader = openURLConnection();
			}
			else 
			reader = openFileConnection();
			reader.setParser(this);
		}
		catch (Exception e)
		{
			String msg="HTMLParser.openConnection() : Error in opening a connection to "+resourceLocn;
			HTMLParserException ex = new HTMLParserException(msg,e);
			feedback.error(msg,ex);
			throw ex;
		}
	}
	private HTMLReader openFileConnection() throws HTMLParserException {
		try {
			return new HTMLReader(new BufferedReader(new FileReader(resourceLocn)),resourceLocn);
		}
		catch (Exception e) {
			String msg="HTMLParser.openFileConnection() : Error in opening a file connection to "+resourceLocn;
			HTMLParserException ex = new HTMLParserException(msg,e);
			feedback.error(msg,ex);
			throw ex;
		}
	}
	private HTMLReader openURLConnection()	throws HTMLParserException {
		try {
			// Its a web address
			resourceLocn=HTMLLinkProcessor.removeEscapeCharacters(resourceLocn);
			resourceLocn=checkEnding(resourceLocn);
			URL url = new URL(resourceLocn);
			URLConnection uc = url.openConnection();
			return new HTMLReader(new BufferedReader(new InputStreamReader(uc.getInputStream(),"8859_4")),resourceLocn);
		}
		catch (Exception e) {
			String msg="HTMLParser.openURLConnection() : Error in opening a URL connection to "+resourceLocn;
			HTMLParserException ex = new HTMLParserException(msg,e);
			feedback.error(msg,ex);
			throw ex;
		}
	}
	/**
	 * Parse the given resource, using the filter provided
	 */
	public void parse(String filter) throws Exception
	{
		HTMLNode node;
		for (HTMLEnumeration e=elements();e.hasMoreNodes();)
		{
			node = e.nextHTMLNode();
	  	  	if (node!=null)
			{
			 	if (filter==null)
				node.print(); 
				else
				{
					// There is a filter. Find if the associated filter of this node
					// matches the specified filter
					if (!(node instanceof HTMLTag)) continue;
					HTMLTag tag=(HTMLTag)node;
					HTMLTagScanner scanner = tag.getThisScanner();
					if (scanner==null) continue;
					String tagFilter = scanner.getFilter();
					if (tagFilter==null) continue;
					if (tagFilter.equals(filter))
							node.print();
				}		
			}
			else System.out.println("Node is null");
		}

	}
/**
	 * This method should be invoked in order to register some common scanners. The scanners that get added are : <br>
	 * HTMLLinkScanner    (filter key "-l")<br>
	 * HTMLImageScanner   (filter key "-i")<br>
	 * HTMLScriptScanner  (filter key "-s") <br>
	 * HTMLStyleScanner   (filter key "-t") <br>
	 * HTMLJspScanner     (filter key "-j") <br>
	 * HTMLAppletScanner  (filter key "-a") <br>
	 * HTMLMetaTagScanner (filter key "-m") <br>
	 * HTMLTitleScanner   (filter key "-t") <br>
	 * HTMLDoctypeScanner (filter key "-d") <br>
	 * HTMLFormScanner    (filter key "-f") <br>
	 * HTMLFrameSetScanner(filter key "-r") <br>
	 * HTMLBaseHREFScanner(filter key "-b") <br>
	 * <br>
	 * Call this method after creating the HTMLParser object. e.g. <BR>
	 * <pre>
	 * HTMLParser parser = new HTMLParser("http://www.yahoo.com");
	 * parser.registerScanners();
	 * </pre>
	 */ 
	public void registerScanners() {
		if (scanners.size()>0) {
			System.err.println("registerScanners() should be called first, when no other scanner has been registered.");
			System.err.println("Other scanners already exist, hence this method call wont have any effect");
			return;
		}
		HTMLLinkScanner linkScanner = new HTMLLinkScanner("-l");
		// Note - The BaseHREF and Image scanners share the same
		// link processor - internally linked up with the factory
		// method in the link scanner class
		addScanner(linkScanner);
		addScanner(linkScanner.createImageScanner("-i"));
		addScanner(new HTMLScriptScanner("-s"));
		addScanner(new HTMLStyleScanner("-t"));
		addScanner(new HTMLJspScanner("-j"));
		addScanner(new HTMLAppletScanner("-a"));
		addScanner(new HTMLMetaTagScanner("-m"));
		addScanner(new HTMLTitleScanner("-T"));
		addScanner(new HTMLDoctypeScanner("-d"));
		addScanner(new HTMLFormScanner("-f"));
		addScanner(new HTMLFrameSetScanner("-r"));	
		addScanner(linkScanner.createBaseHREFScanner("-b"));
	}
	/**
	 * Removes a specified scanner object.
	 * @param scanner HTMLTagScanner object to be removed from the list of registered scanners
	 */
	public void removeScanner(HTMLTagScanner scanner) {
		scanners.remove(scanner);
	}
	/**
	 * This method is to be used to change the set of scanners in the current parser.
	 * @param newScanners Vector holding scanner objects to be used during the parsing process.
	 */
	public void setScanners(Hashtable newScanners) {
		scanners = newScanners;
	}
	
	/**
	 * @param lineSeparator New Line separator to be used
	 */
	public static void setLineSeparator(String lineSeparator)
	{
		HTMLNode.setLineSeparator(lineSeparator);	
	}
	
	/**
	 * Returns the feedback.
	 * @return HTMLParserFeedback
	 */
	public HTMLParserFeedback getFeedback() {
		return feedback;
	}

}
