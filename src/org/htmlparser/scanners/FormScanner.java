// HTMLParser Library v1_3_20030419 - A java-based parser for HTML
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
import java.util.Vector;

import org.htmlparser.Parser;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.ParserException;

/**
 * Scans for the Image Tag. This is a subclass of TagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class FormScanner extends CompositeTagScanner
{
	private static final String [] MATCH_ID = { "FORM" };
	public static final String PREVIOUS_DIRTY_LINK_MESSAGE="Encountered a form tag after an open link tag.\nThere should have been an end tag for the link before the form tag began.\nCorrecting this..";	
	private Vector textAreaVector;
	private boolean linkScannerAlreadyOpen=false;
	private static final String [] formTagEnders = {"HTML","BODY"
	};
 	/**
	 * HTMLFormScanner constructor comment.
	 */
	public FormScanner(Parser parser) {
		this("", parser);
	}
	/**
	 * Overriding the constructor to accept the filter
	 */
	public FormScanner(String filter, Parser parser)
	{
		super(filter,MATCH_ID,formTagEnders,false);
		parser.addScanner(new InputTagScanner("-i"));
		parser.addScanner(new TextareaTagScanner("-t"));
		parser.addScanner(new SelectTagScanner("-select"));
		parser.addScanner(new OptionTagScanner("-option"));
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
	 * The NodeReader is provided in order to do a lookahead operation. We assume that
	 * the identification has already been performed using the evaluate() method.
	 * @param tag HTML Tag to be scanned for identification
	 * @param url The initiating url of the scan (Where the html page lies)
	 * @param reader The reader object responsible for reading the html page
	 * @param currentLine The current line (automatically provided by Tag)
	 */
//	public Tag scan(Tag tag,String url,NodeReader reader,String currentLine) throws ParserException
//	{
//		if (linkScannerAlreadyOpen) {
//			String newLine = insertEndTagBeforeNode(tag, currentLine);
//			reader.changeLine(newLine);
//			return new EndTag(
//				new TagData(
//					tag.elementBegin(),
//					tag.elementBegin()+3,
//					"A",
//					currentLine
//				)
//			);
//		}
//		return super.scan(tag,url,reader,currentLine);
//	}


	/**
	 * @see org.htmlparser.scanners.TagScanner#getID()
	 */
	public String [] getID() {
		return MATCH_ID;
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

	public Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		String formUrl = extractFormLocn(compositeTagData.getStartTag(),tagData.getUrlBeingParsed());
		if (formUrl!=null && formUrl.length()>0) 
			compositeTagData.getStartTag().setAttribute("ACTION",formUrl);
		return new FormTag(tagData, compositeTagData);
	}


}
