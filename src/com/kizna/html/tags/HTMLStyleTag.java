package com.kizna.html.tags;

/**
 * A HTMLStyleTag represents a &lt;style&gt; tag
 */
public class HTMLStyleTag extends HTMLTag {
	/**
	 * The HTMLStyleTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagBegin beginning position of the tag
	 * @param tagEnd ending position of the tag
	 * @param styleCode The style code b/w the tags
	 * @param tagLine The current line being parsed, where the tag was found	 
	 */
public HTMLStyleTag(int tagBegin, int tagEnd, String styleCode,String tagLine) {
	super(tagBegin,tagEnd,styleCode,tagLine);
}
/**
 * Get the javascript code in this tag
 * @return java.lang.String
 */
public java.lang.String getStyleCode() {
	return getText();
}
/**
 * Print the contents of the javascript node
 */
public String toString() 
{
	StringBuffer sb = new StringBuffer();	
	sb.append("Style Node : \n");
	sb.append("\n");
	sb.append("Code\n");
	sb.append("****\n");
	sb.append(tagContents+"\n");
	return sb.toString();
}
}
