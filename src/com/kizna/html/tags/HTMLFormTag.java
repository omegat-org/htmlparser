// HTMLParser Library v1.2(20020503) - A java-based parser for HTML
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
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.html.tags;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Identifies an image tag
 */
public class HTMLFormTag extends HTMLTag
{
	/**
	 * The URL where the image is stored.
	 */
	protected String formURL;
   protected String formName;
   protected String formMethod;
   protected Vector formInputVector;
	/**
	 * Constructor creates an HTMLImageNode object, which stores the location
	 * where the image is to be found.
	 * @imageURL location of the image
	 * @imageBegin Beginning position of the image tag
	 * @imageEnd Ending position of the image tag
	 */
	public HTMLFormTag(String formURL,int formBegin, int formEnd,String tagLine)
	{
		super(formBegin,formEnd,"",tagLine);
		this.formURL = formURL;
	}
	public HTMLFormTag(String formURL,String formName, String formMethod, int formBegin, int formEnd,
                      String tagLine, Vector formInputVector)
	{
		super(formBegin,formEnd,"",tagLine);
		this.formURL = formURL;
      this.formName = formName;
      this.formMethod = formMethod;
      this.formInputVector = formInputVector;
	}
	public Vector getFormInputs()
	{
		return formInputVector;
	}
	/**
	 * Returns the location of the image
	 */
	public String getFormLocation()
	{
		return formURL;
	}
	public String getFormMethod() {
      if(formMethod==null)
      {
         formMethod = "GET";
      }
      //System.out.println("formMethod   "+formMethod);
		return formMethod;
	}
	public HTMLTag getInputTag(String name) {
		HTMLTag inputTag=null;
		boolean found=false;
		for (Enumeration e = formInputVector.elements();e.hasMoreElements() && !found;) {
			inputTag = (HTMLTag)e.nextElement();
			if (inputTag.getParameter("NAME").equals(name)) {
				found=true;
			}
		}
		if (found)
		return inputTag; else return null;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormInputs(Vector formInputVector)
	{
		this.formInputVector = formInputVector;
	}
	public void setFormLocation(String formUrl)
	{
		this.formURL = formURL;
	}
	public void setFormMethod(String formMethod)
	{
		this.formMethod = formMethod;
	}
   public void setFormName(String formName)
   {
      this.formName = formName;
   }
	/**
	 * Print the contents of the HTMLFormTag
	 */
	public String toString()
	{
		return "FORM TAG : Form at "+formURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
}
