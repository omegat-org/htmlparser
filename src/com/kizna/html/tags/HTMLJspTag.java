package com.kizna.html.tags;

import com.kizna.html.HTMLNode; 
/**
 * The JSP/ASP tags like &lt;%...%&gt; can be identified by this class.
 */
public class HTMLJspTag extends HTMLTag
{
	/**
	 * The HTMLJspTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagBegin beginning position of the tag
	 * @param tagEnd ending position of the tag
	 * @param tagContents contents of the remark tag
	 */
	public HTMLJspTag(int tagBegin, int tagEnd, String tagContents, String tagLine)
	{
		super(tagBegin,tagEnd,tagContents,tagLine);
	}
	/**
	 * Returns the beginning position of the tag.
	 */
	public int elementBegin()
	{
		return tagBegin;
	}
	/**
	 * Returns the ending position fo the tag
	 */
	public int elementEnd()
	{
		return tagEnd;
	}
	public String toRawString() {
		return "<%"+tagContents+"%>";
	}
		/**
	 * Print the contents of the remark tag.
	 */
	public String toString()
	{
		return "JSP/ASP Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
