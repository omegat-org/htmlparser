// HTMLParser Library v1.03 - A java-based parser for HTML
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
// Email :somik@kizna.com
//
// Postal Address :
// Somik Raha
// R&D Team
// Kizna Corporation
// 2-1-17-6F, Sakamoto Bldg., Moto Azabu, Minato ku, Tokyo, 106 0046, JAPAN
package com.kizna.html.scanners;

//////////////////
// Java Imports //
//////////////////
import java.util.Enumeration;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Hashtable;
import java.util.HashMap;

/////////////////////////
// HTML Parser Imports //
/////////////////////////
import com.kizna.html.tags.HTMLTag;
import com.kizna.html.HTMLNode;
import com.kizna.html.HTMLStringNode;
import com.kizna.html.HTMLRemarkNode;
import com.kizna.html.HTMLReader;
import com.kizna.html.HTMLParser;
import com.kizna.html.tags.HTMLEndTag;
import com.kizna.html.tags.HTMLImageTag;
import com.kizna.html.util.HTMLLinkProcessor;
import com.kizna.html.tags.HTMLFormTag;

/**
 * Scans for the Image Tag. This is a subclass of HTMLTagScanner, and is called using a
 * variant of the template method. If the evaluate() method returns true, that means the
 * given string contains an image tag. Extraction is done by the scan method thereafter
 * by the user of this class.
 */
public class HTMLFormScanner extends HTMLTagScanner
{
 
	/**
	 * Overriding the constructor to accept the filter
	 */
	public HTMLFormScanner(String filter)
	{
		super(filter);
	}
	/**
	 * Template Method, used to decide if this scanner can handle the Image tag type. If
	 * the evaluation returns true, the calling side makes a call to scan().
	 * @param s The complete text contents of the HTMLTag.
	 */
	public boolean evaluate(String s, HTMLTagScanner previousOpenScanner)
	{
		// Eat up leading blanks
		s = absorbLeadingBlanks(s);
		if (s.toUpperCase().indexOf("FORM")==0)
		return true; else return false;		
	}
  /**
   * Extract the location of the image, given the string to be parsed, and the url
   * of the html page in which this tag exists.
   * @param s String to be parsed
   * @param url URL of web page being parsed
   */
	public String extractFormLocn(HTMLTag tag,String url)
	{
		Hashtable table = tag.parseParameters();
      //System.out.println("table in form locn  "+table.toString());
      String contentLink = null;
      String formMethod = null;
		String formName=  (String)table.get("NAME");
      String formURL = (String)table.get("ACTION");
      try{
         formMethod = (String)table.get("ACTION");
      }
      catch(Exception e)
      {
         formMethod = "GET";
      }
      //System.out.println("formURL  "+formURL);
		if (formURL==null) return ""; else
		return (new HTMLLinkProcessor()).extract(formURL, url);
	}

	public String extractFormName(HTMLTag tag,String url)
	{
		Hashtable table = tag.parseParameters();
      //System.out.println("table in form locn  "+table.toString());
      String contentLink = null;
		String formName=  (String)table.get("NAME");
		if (formName==null) return ""; else
		return (new HTMLLinkProcessor()).extract(formName, url);
	}

	public String extractFormMethod(HTMLTag tag,String url)
	{
		Hashtable table = tag.parseParameters();
      //System.out.println("table in form locn  "+table.toString());
      String contentLink = null;
      String formMethod = "GET";
      formMethod = (String)table.get("METHOD");
      //System.out.println("formMethod is  "+formMethod);
      if(formMethod!=null)
      {
   		return formMethod;
      }
      else
      {
         return "GET";
      }

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
	 * @param currentLine The current line (automatically provided by HTMLTag)
	 */
	public HTMLNode scan(HTMLTag tag,String url,HTMLReader reader,String currentLine) throws IOException
	{
		HTMLNode node;
      Vector inputVector = new Vector();
		String link,formText="",name="",method="GET";
		int linkBegin, linkEnd;

		// Yes, the tag is a link
		// Extract the link
		//link = extractRefreshLocn(tag.getText(),url);
		String tmp;
		String tagContents =  tag.getText();
		String formContents=""; // Kaarle Kaila 23.10.2001
		// Yes, the tag is a link
		// Extract the link
		link = extractFormLocn(tag,url);
      name = extractFormName(tag,url);
      method = extractFormMethod(tag,url);

		//link = extractLink(tag.getText(),url);
		// Check if its a mailto link
      //System.out.println("form in linkscanner   "+link+" name  "+name+"  method  "+method);

		String accessKey = getAccessKey(tag.getText());
      //System.out.println("accessKey  "+accessKey);
		linkBegin = tag.elementBegin();
		// Get the next element, which is string, till </a> is encountered
		boolean endFlag=false;
		Vector nodeVector = new Vector();
      String input = null;
      String inputName, inputValue, inputSrc = "";
		String inputType = "";
      do
		{
			node = reader.readElement();
         //System.out.println("node reader.readElement()  ");
         //node.print();
         if(node instanceof HTMLTag)
         {
            //System.out.println("its an instance of HTMLTag");

            //String value = getParameter("value");
            HTMLTag tag_1 = (HTMLTag)node;
            Hashtable h = tag_1.parseParameters();
            try{
               input = (String)h.get("$<TAGNAME>$");
            }
            catch(Exception e)
            {
               input = null;
            }
            if(input!=null && input.equalsIgnoreCase("input"))
            {
               HashMap inputHashMap = new HashMap();
               //System.out.println("the tag is of type input h.toString()  "+h.toString());
               try{
                  inputType = (String)h.get("TYPE");
               }
               catch(NullPointerException ne)
               {
                  ne.printStackTrace();
                  inputType = "TEXT";
               }
               //System.out.println("inputType  "+inputType);
                  if(inputType!=null)
                  {
                     //System.out.println("inputType in IF "+inputType);
                  }
                  else
                  {
                     inputType = "TEXT";
                  }
                  if(inputType.equalsIgnoreCase("IMAGE")){
                     inputName = (String)h.get("NAME");
                     inputSrc = (String)h.get("SRC");
                     inputHashMap.put("TYPE", (String)inputType);
                     inputHashMap.put("NAME", (String)inputName);
                     inputHashMap.put("SRC", (String)inputSrc);
                     inputVector.add(inputHashMap);
                  }
                  else
                  {
                     inputName = (String)h.get("NAME");
                     inputValue = (String)h.get("VALUE");
                     inputHashMap.put("TYPE", (String)inputType);
                     inputHashMap.put("NAME", (String)inputName);
                     inputHashMap.put("VALUE", (String)inputValue);
                     inputVector.add(inputHashMap);
                  }
               //System.out.println("inputType   "+inputType+"  inputName  "+inputName);
            }
            //System.out.println("tag_1.parseParameters()  "+h.toString());
            //System.out.println("h.get($<TAGNAME>$)  "+(h.get("$<TAGNAME>$")).toString());
            /*System.out.println("tag.getText() "+tag_1.getText()+"  tag.getParameter()  "+tag_1.getParameter("VALUE")+
            "  tag.getTag()  "+tag_1.getTag()+"  tag.getTagLine()  "+tag_1.getTagLine()+
            "tag_1.TAGNAME  "+tag_1.TAGNAME);*/
         }
         else
         {
            //System.out.println("its not an instance of HTMLTag");
         }

			if (node instanceof HTMLRemarkNode)
			{

				tmp =((HTMLRemarkNode)node).getText();
				formText += tmp;
				formContents += tmp;   // Kaarle Kaila 23.10.2001
            //System.out.println("node in FS HTMLRemarkNode "+tmp);
            //node.print();

			}
			if (node instanceof HTMLStringNode)
			{

				tmp =((HTMLStringNode)node).getText();
				formText += tmp;
				formContents += tmp;   // Kaarle Kaila 23.10.2001
            //System.out.println("node in FS HTMLStringNode "+tmp);
            //node.print();

			}
			if (node instanceof HTMLEndTag)
			{
			    tmp = ((HTMLEndTag)node).getContents();
             //System.out.println("tmp in HTMLEndTag  "+tmp);
			    formContents += "</" + tmp  ;   // Kaarle Kaila 23.10.2001
				//char ch = tmp.charAt(0);
				if (tmp.equalsIgnoreCase("form")) endFlag=true; else endFlag=false;
            //System.out.println("node in FS HTMLEndNode "+tmp);
            ///node.print();
			}
			else nodeVector.addElement(node);
		}
		while (endFlag==false);
		if (node instanceof HTMLEndTag)
		{
			// The link has been completed
			// Create the link object and return it
			// HTMLLinkNode Constructor got one extra parameter
			// Kaarle Kaila 23.10.2001
			linkEnd = node.elementEnd();
         //System.out.println("node in FS HTMLStringNode1 ");
            //node.print();
         /*System.out.println("linkText  "+formText+" linkBegin "+linkBegin+" linkEnd  "+linkEnd+
         "  accessKey  "+accessKey+"  currentLine  "+currentLine+"  tagContents  "+tagContents+"  linkContents  "+formContents);*/
         for(int ii=0;ii<nodeVector.size();ii++)
         {
            //System.out.println("nodeVector(ii)  "+(nodeVector.elementAt(ii)).toString());
         }
			//HTMLLinkTag linkTag = new HTMLLinkTag(link,linkText,linkBegin,linkEnd,accessKey,currentLine,nodeVector,mailLink,tagContents,linkContents);
			//return linkTag;
		}



		linkBegin = tag.elementBegin();
		linkEnd = tag.elementEnd();
		HTMLFormTag formTag = new HTMLFormTag(link,name,method,linkBegin,linkEnd,currentLine,inputVector);
      formTag.setFormName(name);
		formTag.setFormInputs(inputVector);
      formTag.setThisScanner(this);
		return formTag;
	}

	public String getAccessKey(String text)
	{
		// Find the occurence of ACCESSKEY in given
		String sub = "ACCESSKEY";
		String accessKey=null;
		int n = text.toUpperCase().indexOf(sub);
		if (n!=-1)
		{
			n+=sub.length();
			// Parse the = sign
			char ch;
			do
			{
				ch = text.charAt(n);
				n++;
			}
			while (ch!='=');
			// Start parsing for a number
			accessKey = "";
			do
			{
				ch = text.charAt(n);
				if (ch>='0' && ch<='9') accessKey+=ch;
				n++;
			}
			while (ch>='0' && ch<='9' && n<text.length());
			return accessKey;
		} else return null;
	}
}
