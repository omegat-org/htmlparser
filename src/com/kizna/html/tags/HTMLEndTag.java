package com.kizna.html.tags;

import com.kizna.html.HTMLNode; 
/**
 * HTMLEndTag can identify closing tags, like &lt;/A&gt;, &lt;/FORM&gt;, etc.
 */
public class HTMLEndTag extends HTMLNode
{
	/**
	 * Holds the contents of the end tag
	 */
	String tagContents;
	/**
	 * Holds the beginning position of the tag
	 */
	int tagBegin;
	/**
	 * Holds the end position of the tag
	 */
	int tagEnd;
	/**
	 * Constructor takes 3 arguments to construct an HTMLEndTag object.
	 * @param tagBegin Beginning position of the end tag
	 * @param tagEnd Ending position of the end tag
	 * @param tagContents Text contents of the tag
	 */
	public HTMLEndTag(int tagBegin, int tagEnd, String tagContents)
	{
		// Initialize member variables with data provided inside the constructor
		this.tagBegin = tagBegin;
		this.tagEnd = tagEnd;
		this.tagContents = tagContents;
	}
	/**
	 * Return the beginning position of the tag
	 */
	public int elementBegin()
	{
		return tagBegin;
	}
	/**
	 * Return the ending position of the tag
	 */
	public int elementEnd()
	{
		return tagEnd;
	}
	/**
	 * Locate the end tag withing the input string, by parsing from the given position
	 * @param input Input String
	 * @param position Position to start parsing from
	 */
	public static HTMLNode find(String input,int position)
	{
		int state = 0;
		//String tagContents = "";
		StringBuffer tagContents = new StringBuffer();
		int tagBegin=0;
		int tagEnd=0;
		int inputLen = input.length();
		char ch;
		for (int i=position;(i<inputLen&& state!=3);i++)
		{
			ch = input.charAt(i);
			if (ch=='>' && state==2)
			{
				state = 3;
				tagEnd = i;
			}				
			if (state==2)
			{
				//tagContents+=input.charAt(i);		
				tagContents.append(ch);
			}
			if (state==1)
			{
				if (ch=='/')			
				{
					state = 2;
				}
				else return null;
			}
			if (state==4)
			{
				//tagContents="";
				tagContents.setLength(0);
			}
			if (ch=='<' && (state==0 || state==4))
			{
				// Transition from State 0 to State 1 - Record data till > is encountered
				tagBegin = i;
				state = 1;
			}
			
		}
		if (state==3)
		return new HTMLEndTag(tagBegin,tagEnd,tagContents.toString());
		else return null;	
	}
	/**
	 * Return the contents of the tag text
	 */
	public String getContents()
	{
		return tagContents;
	}
	public String toPlainTextString() {
		return "";
	}
	public String toRawString() {
		return "</"+tagContents+">";
	}
public String toString() {
	return "EndTag : "+tagContents+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
}
}
