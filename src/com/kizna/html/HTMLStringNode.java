package com.kizna.html;

/**
 * Normal text in the html document is identified and represented by this class.
 */
public class HTMLStringNode extends HTMLNode
{
	/**
	 * The text of the string.
   */	
	protected String text;
  /** 
	 * The beginning position of the tag in the line
	 */	
	protected int textBegin;
	/**
	 * The ending position of the tag in the line
	 */	
	protected int textEnd;
	/** 
	 * Constructor takes in the text string, beginning and ending posns.
	 * @param text The contents of the string line
	 * @param textBegin The beginning position of the string
	 * @param textEnd The ending positiong of the string
	 */
	public HTMLStringNode(String text,int textBegin,int textEnd)
	{
		this.text = text;
		this.textBegin = textBegin;
		this.textEnd = textEnd;
	}
	/**
	 * Returns the beginning position of the string.
	 */	
	public int elementBegin()
	{
		return textBegin;
	}
	/**
	 * Returns the ending position fo the tag
	 */	
	public int elementEnd()
	{
		return textEnd;
	}
	/**
	 * Locate the StringNode within the input string, by parsing from the given position
	 * @param reader HTML reader to be provided so as to allow reading of next line
	 * @param input Input String
	 * @param position Position to start parsing from
	 */		
	public static HTMLNode find(HTMLReader reader,String input,int position)
	{
		String text = "";
		int state = 0;
		int textBegin=position;
		int textEnd=position;
		for (int i=position;(i<input.length() && state!=2);i++)
		{
			// When the input has ended but no text is found, we end up returning null
			if (input.charAt(i)=='<' && state==0)
			{
				return null;
			}
			// The following conditionals are a bug fix
			// done by Roger Sollberger. They correspond to a
			// testcase in HTMLStringNodeTest (testTagCharsInStringNode)
			if ((input.charAt(i)=='<') &&
			   (((i+1)<input.length()) &&  // test if next char available
			    (((input.charAt(i+1)>='A') && (input.charAt(i+1)<='Z')) || // next char must be A-Z 
			     ((input.charAt(i+1)>='a') && (input.charAt(i+1)<='z')) || // next char must be a-z
			     (input.charAt(i+1)=='/'))))   // or next char is a '/' 
			{
				state = 2;
				textEnd=i-1;
			}
			if (state==0)
			{
				if (input.charAt(i)!=' ') state=1;
				else text+=input.charAt(i);
			}
			if (state==1)
			{
				text+=input.charAt(i);
			}				
			if (state==1 && i==input.length()-1)
			{
				state=2;
				textEnd=i;
			}
		}
		if (textBegin<=textEnd) return new HTMLStringNode(text,textBegin,textEnd);
		else return null;
	}
	/**
	 * Returns the text of the string line
	 */
	public String getText()
	{
		return text;
	}
public String toString() {
	return "Text = "+text+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
}
}
