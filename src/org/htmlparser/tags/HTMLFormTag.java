// HTMLParser Library v1_2_20021208 - A java-based parser for HTML
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
import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.HTMLNode;

/**
 * Represents a FORM tag.
 */
public class HTMLFormTag extends HTMLTag
{
	protected String formURL;
   	protected String formName;
   	protected String formMethod;
   	protected Vector formInputVector;
   	protected Vector allNodesVector;
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
                      String tagLine, Vector formInputVector, Vector allNodesVector)
	{
		super(formBegin,formEnd,"",tagLine);
		this.formURL = formURL;
	    this.formName = formName;
      	this.formMethod = formMethod;
      	this.formInputVector = formInputVector;
      	this.allNodesVector = allNodesVector;
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
			if (inputTag.getParameter("NAME").equals(name)) {
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
		this.formURL = formURL;
	}
	/**
	 * Set the form method
	 * @param formMethod The new method of sending data
	 */
	public void setFormMethod(String formMethod)
	{
		this.formMethod = formMethod;
	}
	/**
	 * Set the form name
	 * @param formName The name of the form
	 */
   	public void setFormName(String formName)
   	{
      	this.formName = formName;
   	}
	/**
	 * @return String The contents of the HTMLFormTag
	 */
	public String toString()
	{
		return "FORM TAG : Form at "+formURL+"; begins at : "+elementBegin()+"; ends at : "+elementEnd();
	}
	/**
	 * Returns the allNodesVector - all nodes in the form.
	 * @return Vector
	 */
	public Vector getAllNodesVector() {
		return allNodesVector;
	}

	/**
	 * Sets the allNodesVector.
	 * @param allNodesVector The allNodesVector to set
	 */
	public void setAllNodesVector(Vector allNodesVector) {
		this.allNodesVector = allNodesVector;
	}
	/**
	 * The HTML Rendering of the Form tag
	 */
	public String toHTML() {
		StringBuffer rawBuffer = new StringBuffer();
		HTMLNode node,prevNode=null;
		rawBuffer.append("<FORM METHOD=\""+formMethod+"\" ACTION=\""+formURL+"\"");
		if (formName!=null && formName.length()>0) rawBuffer.append(" NAME=\""+formName+"\"");
		Enumeration e = allNodesVector.elements();
		node = (HTMLNode)e.nextElement();
		HTMLTag tag = (HTMLTag)node;
		Hashtable table = tag.getParsed();
		String key,value;
		for (Enumeration en = table.keys();en.hasMoreElements();) {
			key=(String)en.nextElement();
			if (!(key.equals("METHOD") || key.equals("ACTION") || key.equals("NAME") || key.equals(HTMLTag.TAGNAME))) {
				value = (String)table.get(key);		
				rawBuffer.append(" "+key+"="+"\""+value+"\"");
			}
		}
		rawBuffer.append(">");
		rawBuffer.append(lineSeparator);
		for (;e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			if (prevNode!=null) {
				if (prevNode.elementEnd()>node.elementBegin()) {
					// Its a new line
					rawBuffer.append(lineSeparator);					
				}
			}
			rawBuffer.append(node.toHTML());
			prevNode=node;
		}
		return rawBuffer.toString();		
	}
	public String toPlainTextString() {
		StringBuffer stringRepresentation = new StringBuffer();
		HTMLNode node;
		for (Enumeration e=getAllNodesVector().elements();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();		
			stringRepresentation.append(node.toPlainTextString());
		}
		return stringRepresentation.toString();
	}

	public void collectInto(Vector collectionVector, String filter) {
		super.collectInto(collectionVector, filter);
		HTMLNode node;
		for (Enumeration e = allNodesVector.elements();e.hasMoreElements();) {
			node = (HTMLNode)e.nextElement();
			node.collectInto(collectionVector,filter);
		}
	}

}
