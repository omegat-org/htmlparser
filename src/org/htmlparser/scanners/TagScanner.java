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

package org.htmlparser.scanners;
//////////////////
// Java Imports //
//////////////////
import java.io.Serializable;
import java.util.Hashtable;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.StringNode;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;
/**
 * HTMLTagScanner is an abstract superclass which is subclassed to create specific 
 * scanners, that operate on a tag's strings, identify it, and can extract data from it.
 * <br>
 * If you wish to write your own scanner, then you must implement scan().
 * You MAY implement evaluate() as well, if your evaluation logic is not based on a simple text match.
 * You MUST implement getID() - which identifies your scanner uniquely in the hashtable of scanners.
 * 
 * <br>
 * Also, you have a feedback object provided to you, should you want to send log messages. This object is
 * instantiated by HTMLParser when a scanner is added to its collection.
 * 
 */
public abstract class TagScanner
    implements
        Serializable
{
	  /**
	   * A filter which is used to associate this tag. The filter contains a string
	   * that is used to match which tags are to be allowed to pass through. This can
	   * be useful when one wishes to dynamically filter out all tags except one type
	   * which may be programmed later than the parser. Is also useful for command line
	   * implementations of the parser.
	   */
	  protected String filter;
	  
	  /**
	   * HTMLParserFeedback object automatically initialized
	   */
	  protected ParserFeedback feedback;
	  /**
	   * Default Constructor, automatically registers the scanner into a static array of 
	   * scanners inside HTMLTag
	   */
	  public TagScanner()
	  {
	    this.filter="";
	  }
	  /**
	   * This constructor automatically registers the scanner, and sets the filter for this
	   * tag. 
	   * @param filter The filter which will allow this tag to pass through.
	   */
	  public TagScanner(String filter)
	  {
	    this.filter=filter;
	  }
		/**
	 * Insert the method's description here.
	 * Creation date: (6/4/2001 11:44:09 AM)
	 * @return java.lang.String
	 * @param c char
	 */
	public String absorb(String s,char c) {
	  int index = s.indexOf(c);
	  if (index!=-1)	s=s.substring(index+1,s.length());
	  return s;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:15:02 AM)
	 * @return java.lang.String
	 */
		public static String absorbLeadingBlanks(String s) 
		{
		  String temp = new String(s);
		  while (temp.length()!=0 && temp.charAt(0)==' ')
		  {
		    temp = temp.substring(1,temp.length());
		  }
		  return temp;
		}
	  /**
	   * This method is used to decide if this scanner can handle this tag type. If the
	   * evaluation returns true, the calling side makes a call to scan().
	   * <strong>This method has to be implemented meaningfully only if a first-word match with 
	   * the scanner id does not imply a match (or extra processing needs to be done).
	   * Default returns true</strong>
	   * @param s The complete text contents of the HTMLTag.
	   * @param previousOpenScanner Indicates any previous scanner which hasnt completed, before the current
	   * scan has begun, and hence allows us to write scanners that can work with dirty html
	   */
	  public boolean evaluate(String s,TagScanner previousOpenScanner) {
	  	return true;
	  }
	  public static String extractXMLData(Node node, String tagName, NodeReader reader) throws ParserException{
		try {	  
		  String xmlData = "";
	
	  boolean xmlTagFound = isXMLTagFound(node, tagName);
	  if (xmlTagFound) {
	    try{
	      do {
	        node = reader.readElement();
	        if (node!=null) {
	          if (node instanceof StringNode) {
	            StringNode stringNode = (StringNode)node;
	            if (xmlData.length()>0) xmlData+=" ";
	        xmlData += stringNode.getText();
	      } else if (!(node instanceof org.htmlparser.tags.EndTag))
	        xmlTagFound = false;
	    }
	  }
	  while (node instanceof StringNode);
	  
	}
	
	catch (Exception e) {
		throw new ParserException("HTMLTagScanner.extractXMLData() : error while trying to find xml tag",e);
	    }
	  }
	  if (xmlTagFound) {
	      if (node!=null) {
	        if (node instanceof org.htmlparser.tags.EndTag) {
	          org.htmlparser.tags.EndTag endTag = (org.htmlparser.tags.EndTag)node;
	          if (!endTag.getText().equals(tagName)) xmlTagFound = false;		
	        }
	      
	      }
	
	  }
	  if (xmlTagFound) return xmlData; else return null;
	}
	catch (Exception e) {
		throw new ParserException("HTMLTagScanner.extractXMLData() : Error occurred while trying to extract xml tag",e);
		}
	  }
	  /**
	   * Get the filter associated with this node.
	   */
	  public String getFilter()
	  {
	    return filter;
	  }
		/**
	 * Insert the method's description here.
	 * Creation date: (10/24/2001 6:27:02 PM)
	 */
	public static boolean isXMLTagFound(Node node, String tagName) {
		boolean xmlTagFound=false;
		if (node instanceof Tag) {
			Tag tag = (Tag)node;
		    if (tag.getText().toUpperCase().indexOf(tagName)==0) {
		      xmlTagFound=true;
		    }
		}
		return xmlTagFound;
	}
	
	public final Tag createScannedNode(Tag tag,String url,NodeReader reader,String currLine) throws ParserException {
		Tag thisTag = scan(tag,url,reader,currLine);
		thisTag.setThisScanner(this);
		thisTag.setAttributes(tag.getAttributes());		
		return thisTag;
	}
	
	/** 
     * Scan the tag and extract the information related to this type. The url of the 
	 * initiating scan has to be provided in case relative links are found. The initial 
	 * url is then prepended to it to give an absolute link.
	 * The HTMLReader is provided in order to do a lookahead operation. We assume that
	 * the identification has already been performed using the evaluate() method.
	 * @param tag HTML Tag to be scanned for identification
	 * @param url The initiating url of the scan (Where the html page lies)
	 * @param reader The reader object responsible for reading the html page
	 */
  	public Tag scan(Tag tag,String url,NodeReader reader,String currLine) throws ParserException {
  		return createTag(new TagData(
			tag.elementBegin(), 
			tag.elementEnd(), 
			tag.getText(),
			currLine
		), tag, url);
  	}
	
	public String removeChars(String s,String occur)  {
	    StringBuffer newString = new StringBuffer();
	    char ch;
	    int index;
	    do {
	    	index = s.indexOf(occur);
	    	if (index!=-1) {
	    		newString.append(s.substring(0,index));
	    		s=s.substring(index+occur.length());
	    	}
	    }
	    while (index!=-1);
	    newString.append(s);
	    return newString.toString();
	}
	  
	public abstract String [] getID();
	
	public final void setFeedback(ParserFeedback feedback) {
	  	this.feedback = feedback;
	}
	
	public static Hashtable adjustScanners(NodeReader pReader) 
	{
		Hashtable tempScanners= new Hashtable();
		tempScanners = pReader.getParser().getScanners();		
		// Remove all existing scanners
		pReader.getParser().flushScanners();
		return tempScanners;
	}
	
	public static void restoreScanners(NodeReader pReader, Hashtable tempScanners)
	{
		// Flush the scanners
		pReader.getParser().setScanners(tempScanners);
	}

	/**
	 * Insert an EndTag in the currentLine, just before the occurence of the provided tag
	 */
	public String insertEndTagBeforeNode(Node node, String currentLine) {
		String newLine = currentLine.substring(0,node.elementBegin());
		newLine += "</A>";
		newLine += currentLine.substring(node.elementBegin(),currentLine.length());
		return newLine;
	}
	
	/**
	 * Override this method to create your own tag type
	 * @param tagData
	 * @param tag
	 * @param url
	 * @return HTMLTag
	 * @throws HTMLParserException
	 */
	protected Tag createTag(TagData tagData, Tag tag, String url) throws ParserException { return null; }  
}