// HTMLParser Library v1_3_20030302 - A java-based parser for HTML
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

package org.htmlparser.scanners;

//////////////////
// Java Imports //
//////////////////
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.FormData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;

/**
 * Scans for the Image Tag. This is a subclass of TagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class FormScanner extends TagScanner
{
	public static final String PREVIOUS_DIRTY_LINK_MESSAGE="Encountered a form tag after an open link tag.\nThere should have been an end tag for the link before the form tag began.\nCorrecting this..";	
	private Vector textAreaVector;
	private boolean linkScannerAlreadyOpen=false;
 	/**
	 * HTMLFormScanner constructor comment.
	 */
	public FormScanner() {
		super();
	}
	/**
	 * Overriding the constructor to accept the filter
	 */
	public FormScanner(String filter)
	{
		super(filter);
	}
  /**
   * Extract the location of the image, given the string to be parsed, and the url
   * of the html page in which this tag exists.
   * @param s String to be parsed
   * @param url URL of web page being parsed
   */
	public String extractFormLocn(Tag tag,String url) throws ParserException
	{
		try {
			String formURL= tag.getAttribute("ACTION");
			if (formURL==null) return ""; else
			return (new LinkProcessor()).extract(formURL, url);
		}
		catch (Exception e) {
			String msg;
			if (tag!=null) msg=  tag.getText(); else msg="";
			throw new ParserException("HTMLFormScanner.extractFormLocn() : Error in extracting form location, tag = "+msg+", url = "+url,e);
		}
	}

	public String extractFormName(Tag tag)
	{
		return tag.getAttribute("NAME");
	}

	public String extractFormMethod(Tag tag)
	{
		String method = tag.getAttribute("METHOD");
		if (method==null) method = FormTag.GET;
		return method.toUpperCase();

	}

	/**
	 * Scan the tag and extract the information related to the <IMG> tag. The url of the
	 * initiating scan has to be provided in case relative links are found. The initial
	 * url is then prepended to it to give an absolute link.
	 * The HTMLReader is provided in order to do a lookahead operation. We assume that
	 * the identification has already been performed using the evaluate() method.
	 * @param tag HTML Tag to be scanned for identification
	 * @param url The initiating url of the scan (Where the html page lies)
	 * @param reader The reader object responsible for reading the html page
	 * @param currentLine The current line (automatically provided by Tag)
	 */
	public Tag scan(Tag tag,String url,NodeReader reader,String currentLine) throws ParserException
	{
		if (linkScannerAlreadyOpen) {
			String newLine = insertEndTagBeforeNode(tag, currentLine);
			reader.changeLine(newLine);
			return new EndTag(
				new TagData(
					tag.elementBegin(),
					tag.elementBegin()+3,
					"A",
					currentLine
				)
			);
		}
		try {
			Node node;
			Tag startFormTag = tag;
			Tag endFormTag = null;
	      	Vector inputVector = new Vector(), textAreaVector = new Vector(), 
	      	nodeVector = new Vector();
			
			String link,name="",method="GET";
			int linkBegin=-1, formEnd=-1;
	
			link = extractFormLocn(tag,url);
			tag.getAttributes().put("ACTION",link);
	    	name = extractFormName(tag);
		    method = extractFormMethod(tag);
			linkBegin = tag.elementBegin();
		    boolean endFlag = false;
			//nodeVector.addElement(tag);
			
			// The following two lines added by Somik Raha, to fix a bug - so as to allow 
			// links inside form tags to be scanned.
			LinkScanner linkScanner = (LinkScanner)reader.getParser().getScanner(LinkScanner.LINK_SCANNER_ID);
			ImageScanner imageScanner = (ImageScanner)reader.getParser().getScanner(ImageScanner.IMAGE_SCANNER_ID);
			// End of modification
			
			Hashtable oldScanners = adjustScanners(reader);
			reader.getParser().addScanner(new InputTagScanner(""));
			reader.getParser().addScanner(new SelectTagScanner(""));
			reader.getParser().addScanner(new OptionTagScanner(""));
			reader.getParser().addScanner(new TextareaTagScanner(""));
			
			// Following two lines added by Somik Raha, to fix the links in forms bug
			reader.getParser().addScanner(linkScanner);
			reader.getParser().addScanner(imageScanner);
			// End of modification
			boolean dontPutTag=false;
		    do
			{
				node = reader.readElement();
				if (node instanceof EndTag)
				{
					EndTag endTag = (EndTag)node;
					if (endTag.getText().toUpperCase().equals("FORM")) {
						endFlag=true;
						formEnd = endTag.elementEnd();
						dontPutTag = true;
						endFormTag = endTag;
					}
				}
				else 
				if (node instanceof InputTag) {
					inputVector.addElement(node);
				} else 
				if (node instanceof TextareaTag) {
					textAreaVector.addElement(node);
				}
				if (!dontPutTag) nodeVector.addElement(node);
			}
			while (endFlag==false && node!=null);
			restoreScanners(reader,oldScanners);
			
			if (node==null && endFlag==false) {
				StringBuffer msg = new StringBuffer();
				for (Enumeration e = inputVector.elements();e.hasMoreElements();) {
					msg.append((Node)e.nextElement()+"\n");
				}
				throw new ParserException("HTMLFormScanner.scan() : Went into a potential infinite loop - tags must be malformed.\n"+
				"Input Vector contents : "+msg.toString());
			}		
			FormTag formTag = new FormTag(
				new TagData(
					linkBegin,
					formEnd,
					"",
					currentLine
				),
				new CompositeTagData(
					startFormTag,
					endFormTag,
					nodeVector
				),
				new FormData(
					link,
					name,
					method,
					inputVector,
					textAreaVector
				)
			);
			return formTag;
		}
		catch (Exception e) {
			throw new ParserException("HTMLFormScanner.scan() : Error while scanning the form tag, current line = "+currentLine,e);
		}
	}

	/**
	 * @see org.htmlparser.scanners.TagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "FORM";
		return ids;
	}

	public boolean evaluate(String s, TagScanner previousOpenScanner) {
		if (previousOpenScanner instanceof LinkScanner) {
			linkScannerAlreadyOpen = true;			
			StringBuffer msg= new StringBuffer();
				msg.append("<");
				msg.append(s);
				msg.append(">");
				msg.append(PREVIOUS_DIRTY_LINK_MESSAGE);
				feedback.warning(msg.toString());
				// This is dirty HTML. Assume the current tag is
				// not a new link tag - but an end tag. This is actually a really wild bug - 
				// Internet Explorer actually parses such tags.
				// So - we shall then proceed to fool the scanner into sending an endtag of type </A>
				// For this - set the dirty flag to true and return
		}
		else 	
			linkScannerAlreadyOpen = false;
		return super.evaluate(s, previousOpenScanner);
	}

}
