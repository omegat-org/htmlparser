package com.kizna.html.tags;

import com.kizna.html.HTMLNode; 
/**
 * The HTML Document Declaration Tag can identify &lt;!DOCTYPE&gt; tags
 */
public class HTMLDoctypeTag extends HTMLTag
{
	/**
	 * The HTMLDecTag is constructed by providing the beginning posn, ending posn
	 * and the tag contents.
	 * @param tagBegin beginning position of the tag
	 * @param tagEnd ending position of the tag
	 * @param tagContents contents of the remark tag
	 */
	public HTMLDoctypeTag(int tagBegin, int tagEnd, String tagContents,String tagLine)
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
		/**
	 * Print the contents of the remark tag.
	 */
	public String toString()
	{
		return "Doctype Tag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
