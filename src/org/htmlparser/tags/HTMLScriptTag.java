// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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

package org.htmlparser.tags;

/**
 * A HTMLScriptTag represents a JavaScript node
 */
public class HTMLScriptTag extends HTMLTag {
	private java.lang.String language;
	private java.lang.String type;
	private String scriptCode;
	/**
	 * The HTMLScriptTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagBegin beginning position of the tag
	 * @param tagEnd ending position of the tag
	 * @param tagContents The contents of the Script Tag (should be kept the same as that of the original HTMLTag contents)
	 * @param scriptCode The Javascript code b/w the tags
	 * @param language The language parameter
	 * @param type The type parameter
	 * @param tagLine The current line being parsed, where the tag was found	 
	 */
public HTMLScriptTag(int tagBegin, int tagEnd, String tagContents, String scriptCode,String language,String type,String tagLine) 
{
	super(tagBegin,tagEnd,tagContents,tagLine);
	this.scriptCode = scriptCode;
	this.language = language; 
	this.type = type;
}
/**
 * Get the language of the script
 * Creation date: (6/4/2001 1:09:34 PM)
 * @return java.lang.String
 */
public java.lang.String getLanguage() {
	return language;
}
/**
 * Get the javascript code in this tag
 * @return java.lang.String
 */
public java.lang.String getScriptCode() {
	return scriptCode;
}
/**
 * Insert the method's description here.
 * Creation date: (6/4/2001 1:09:34 PM)
 * @return java.lang.String
 */
public java.lang.String getType() {
	return type;
}
/**
 * Set the language of the javascript tag
 * @param newLanguage java.lang.String
 */
public void setLanguage(java.lang.String newLanguage) {
	language = newLanguage;
}
/**
 * Set the type of the javascript node
 * @param newType java.lang.String
 */
public void setType(java.lang.String newType) {
	type = newType;
}
public String toHTML() {
	StringBuffer sb = new StringBuffer();
	sb.append("<"+tagContents+">");
	if (isEndOfLineCharState()) sb.append(lineSeparator);	
	sb.append(scriptCode);
	sb.append("</SCRIPT>");

	return sb.toString();
}
/**
 * Print the contents of the javascript node
 */
public String toString() 
{
	StringBuffer sb = new StringBuffer();
	sb.append("Script Node : \n");
	if (language.length()!=0 || type.length()!=0)
	{
		sb.append("Properties -->\n");
		if (language.length()!=0) sb.append("[Language : "+language+"]\n");
		if (type.length()!=0) sb.append("[Type : "+type+"]\n");
	}
	sb.append("\n");
	sb.append("Code\n");
	sb.append("****\n");
	sb.append(getScriptCode()+"\n");
	return sb.toString();
}
}