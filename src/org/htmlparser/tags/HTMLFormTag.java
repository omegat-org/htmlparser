// HTMLParser Library v1_2 - A java-based parser for HTML
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

import org.htmlparser.HTMLNode;

/**
 * Represents a FORM tag.
 */
public class HTMLFormTag extends HTMLCompositeTag
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
	public HTMLFormTag(String formURL,String formName, String formMethod, int formBegin, int formEnd,
                      String tagLine, Vector formInputVector, Vector textAreaVector, Vector allNodesVector,
                      HTMLTag startTag, HTMLTag endTag)
	{
		super(formBegin,formEnd,"",tagLine,allNodesVector,startTag, endTag);
		this.formURL = formURL;
	    this.formName = formName;
      	this.formMethod = formMethod;
      	this.formInputVector = formInputVector;
      	this.textAreaVector = textAreaVector;
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
	 * @param name The name of the input tag to be retrieved
	 * @return HTMLTag The input tag corresponding to the name provided
	 */
	public HTMLInputTag getInputTag(String name) {
		HTMLInputTag inputTag=null;
		boolean found=false;
		for (Enumeration e = formInputVector.elements();e.hasMoreElements() && !found;) {
			inputTag = (HTMLInputTag)e.nextElement();
			String inputTagName = inputTag.getParameter("NAME");
			if (inputTagName!=null && inputTagName.equals(name)) {
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
	 * Set the form inputs
	 * @param formInputVector The vector of input tags
	 */
	public void setFormInputs(Vector formInputVector)
	{
		this.formInputVector = formInputVector;
	}
	
	/**
	 * Set the form location. Modification of this element will cause the HTML rendering 
	 * to change as well (in a call to toHTML()).
	 * @param formURL The new FORM location
	 */
	public void setFormLocation(String formURL)
	{
		attributes.put("ACTION",formURL);
		this.formURL = formURL;
	}
	/**
	 * Set the form method
	 * @param formMethod The new method of sending data
	 */
	public void setFormMethod(String formMethod)
	{
		attributes.put("METHOD",formMethod);
		this.formMethod = formMethod;
	}
	/**
	 * Set the form name
	 * @param formName The name of the form
	 */
   	public void setFormName(String formName)
   	{
      	attributes.put("NAME",formName);
      	this.formName = formName;
   	}
	/**
	 * @return String The contents of the HTMLFormTag
	 */
	public String toString()
	{
		return "FORM TAG : Form at "+formURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}

	public void collectInto(Vector collectionVector, String filter) {
		super.collectInto(collectionVector, filter);
		HTMLNode node;
		for (Enumeration e = children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			node.collectInto(collectionVector,filter);
		}
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
	
	/** 
	 * Case insensitive search
	 * @param searchString
	 * @return Vector
	 */
	public Vector searchFor(String searchString) {
		return searchFor(searchString, false);
	}
	
	
	public Vector searchFor(String searchString, boolean caseSensitive) {
		Vector foundVector = new Vector();
		HTMLNode node;
		if (!caseSensitive) searchString = searchString.toUpperCase();
		for (Enumeration e = children();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			String nodeTextString = node.toPlainTextString(); 
			if (!caseSensitive) nodeTextString=nodeTextString.toUpperCase();
			if (nodeTextString.indexOf(searchString)!=-1) {
				foundVector.addElement(node);
			}	
		}
		return foundVector;
	}
	public HTMLTag searchByName(String name) {
		HTMLNode node;
		HTMLTag tag=null;
		boolean found = false;
		for (Enumeration e = children();e.hasMoreElements() && !found;) {
			node = (HTMLNode)e.nextElement();
			if (node instanceof HTMLTag) {
				tag = (HTMLTag)node;
				String nameAttribute = tag.getParameter("NAME");
				if (nameAttribute!=null && nameAttribute.equals(name)) found=true;
			}
		}
		if (found) 
			return tag;
		else
			return null;
	}

}
