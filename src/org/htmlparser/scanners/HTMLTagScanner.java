// HTMLParser Library v1_2_20021125 - A java-based parser for HTML
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
import java.io.IOException;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.HTMLNode;
import org.htmlparser.HTMLReader;
import org.htmlparser.HTMLStringNode;
import org.htmlparser.util.*;
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
public abstract class HTMLTagScanner
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
  protected HTMLParserFeedback feedback;
  /**
   * Default Constructor, automatically registers the scanner into a static array of 
   * scanners inside HTMLTag
   */
  public HTMLTagScanner()
  {
    this.filter="";
  }
  /**
   * This constructor automatically registers the scanner, and sets the filter for this
   * tag. 
   * @param filter The filter which will allow this tag to pass through.
   */
  public HTMLTagScanner(String filter)
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
  public boolean evaluate(String s,HTMLTagScanner previousOpenScanner) {
  	return true;
  }
  public static String extractXMLData(HTMLNode node, String tagName, HTMLReader reader) throws HTMLParserException{
	try {	  
	  String xmlData = "";
	
	  boolean xmlTagFound = isXMLTagFound(node, tagName);
	  if (xmlTagFound) {
	    try{
	      do {
	        node = reader.readElement();
	        if (node!=null) {
	          if (node instanceof HTMLStringNode) {
	            HTMLStringNode stringNode = (HTMLStringNode)node;
	            if (xmlData.length()>0) xmlData+=" ";
	            xmlData += stringNode.getText();
	          } else if (!(node instanceof org.htmlparser.tags.HTMLEndTag))
	            xmlTagFound = false;
	        }
	      }
	      while (node instanceof HTMLStringNode);
	      
	    }
	
	    catch (Exception e) {
	    	throw new HTMLParserException("HTMLTagScanner.extractXMLData() : error while trying to find xml tag",e);
	    }
	  }
	  if (xmlTagFound) {
	      if (node!=null) {
	        if (node instanceof org.htmlparser.tags.HTMLEndTag) {
	          org.htmlparser.tags.HTMLEndTag endTag = (org.htmlparser.tags.HTMLEndTag)node;
	          if (!endTag.getText().equals(tagName)) xmlTagFound = false;		
	        }
	      
	      }
	
	  }
	  if (xmlTagFound) return xmlData; else return null;
	}
	catch (Exception e) {
		throw new HTMLParserException("HTMLTagScanner.extractXMLData() : Error occurred while trying to extract xml tag",e);
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
	public static boolean isXMLTagFound(HTMLNode node, String tagName) {
	  boolean xmlTagFound=false;
	  if (node instanceof HTMLTag) {
	    HTMLTag tag = (HTMLTag)node;
	    if (tag.getText().toUpperCase().indexOf(tagName)==0) {
	      xmlTagFound=true;
	    }
	  }
	  return xmlTagFound;
	}
	public final HTMLTag createScannedNode(HTMLTag tag,String url,HTMLReader reader,String currLine) throws HTMLParserException {
		HTMLTag thisTag = scan(tag,url,reader,currLine);
		thisTag.setThisScanner(this);
		thisTag.setParsed(tag.getParsed());		
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
  public abstract HTMLTag scan(HTMLTag tag,String url,HTMLReader reader,String currLine) throws HTMLParserException;

  public String removeChars(String s,char occur)  {
    StringBuffer newString = new StringBuffer();
    char ch;
    for (int i=0;i<s.length();i++) {
      ch = s.charAt(i);
      if (ch!=occur) newString.append(ch);
    }
    return newString.toString();
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

  public final void setFeedback(HTMLParserFeedback feedback) {
  	this.feedback = feedback;
  }
}
