// HTMLParser Library v1_3_20030112 - A java-based parser for HTML
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

import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.tags.data.HTMLCompositeTagData;
import org.htmlparser.tags.data.HTMLFormTagData;
import org.htmlparser.tags.data.HTMLTagData;

/**
 * Represents a FORM tag.
 */
public class HTMLFormTag extends CompositeTag
{
	public static final String POST="POST";
	public static final String GET="GET";
	protected String formURL;
   	protected String formName;
   	protected String formMethod;
   	protected Vector formInputVector;

	private Vector textAreaVector;
   	
   	/**
   	 * A form tag - contains information contained in the form tag.
   	 * @param formURL URL to which form data will be sent
   	 * @param formName Name of the form
   	 * @param formMethod GET or POST
   	 * @param formBegin Beginning of the form 
   	 * @param formEnd End of the form
   	 * @param tagLine The tag line where this tag occurred
   	 * @param formInputVector The vector of INPUT elements
   	 * @param allNodesVector The vector of all elements in the FORM
   	 */
	public HTMLFormTag(HTMLTagData tagData, HTMLCompositeTagData compositeTagData, HTMLFormTagData formTagData)
	{
		super(tagData,compositeTagData);
		this.formURL = formTagData.getFormURL();
	    this.formName = formTagData.getFormName();
      	this.formMethod = formTagData.getFormMethod();
      	this.formInputVector = formTagData.getFormInputVector();
      	this.textAreaVector = formTagData.getTextAreaVector();
	}
	
	/**
	 * @return Vector Input elements in the form
	 */
	public Vector getFormInputs()
	{
		return formInputVector;
	}
	
	/**
	 * @return String The url of the form
	 */
	public String getFormLocation()
	{
		return formURL;
	}
	
	/**
	 * Returns the method of the form
	 * @return String The method of the form (GET if nothing is specified)
	 */
	public String getFormMethod() {
      	if(formMethod==null)
      	{	
         	formMethod = "GET";
      	}
		return formMethod;
	}
	
	/**
	 * Get the input tag in the form corresponding to the given name
	 * @param name The name of the input tag to be retrieved
	 * @return HTMLTag The input tag corresponding to the name provided
	 */
	public HTMLInputTag getInputTag(String name) {
		HTMLInputTag inputTag=null;
		boolean found=false;
		for (Enumeration e = formInputVector.elements();e.hasMoreElements() && !found;) {
			inputTag = (HTMLInputTag)e.nextElement();
			String inputTagName = inputTag.getParameter("NAME");
			if (inputTagName!=null && inputTagName.equalsIgnoreCase(name)) {
				found=true;
			}
		}
		if (found)
		return inputTag; else return null;
	}
	
	/**
	 * @return String The name of the form
	 */
	public String getFormName() {
		return formName;
	}
	
	/**
	 * Set the form location. Modification of this element will cause the HTML rendering 
	 * to change as well (in a call to toHTML()).
	 * @param formURL The new FORM location
	 */
	public void setFormLocation(String formURL) {
		attributes.put("ACTION",formURL);
		this.formURL = formURL;
	}

	/**
	 * @return String The contents of the HTMLFormTag
	 */
	public String toString() {
		return "FORM TAG : Form at "+formURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}

	/**
	 * Find the textarea tag matching the given name
	 * @param name Name of the textarea tag to be found within the form
	 */
	public HTMLTextareaTag getTextAreaTag(String name) {
		HTMLTextareaTag textareaTag=null;
		boolean found = false;
		for (Enumeration e=textAreaVector.elements();e.hasMoreElements() && !found;) {
			textareaTag = (HTMLTextareaTag)e.nextElement();
			String textAreaName = textareaTag.getParameter("NAME");
			if (textAreaName!=null && textAreaName.equals(name)) {
				found = true;
			}
		}
		if (found) 
			return textareaTag;
		else
			return null;
	}
	
}
