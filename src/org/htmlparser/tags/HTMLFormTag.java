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

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.FormData;
import org.htmlparser.tags.data.TagData;

/**
 * @author ili
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
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
	 * Constructor takes in tagData, compositeTagData, formTagData
	 * @param tagData
	 * @param compositeTagData
	 * @param formTagData
	 */
	public HTMLFormTag(TagData tagData, CompositeTagData compositeTagData, FormData formData)
	{
		super(tagData,compositeTagData);
		this.formURL = formData.getFormURL();
	    this.formName = formData.getFormName();
      	this.formMethod = formData.getFormMethod();
      	this.formInputVector = formData.getFormInputVector();
      	this.textAreaVector = formData.getTextAreaVector();
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
			String inputTagName = inputTag.getAttribute("NAME");
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
			String textAreaName = textareaTag.getAttribute("NAME");
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
