// HTMLParser Library v1_4_20031026 - A java-based parser for HTML
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

import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

/**
 * Represents a FORM tag.
 * @author ili
 */
public class FormTag extends CompositeTag
{
    public static final String POST="POST";
    public static final String GET="GET";

    public FormTag ()
    {
        setTagName ("FORM");
    }

    /**
     * Get the list of input fields.
     * @return Input elements in the form.
     */
    public NodeList getFormInputs()
    {
        return (getChildren().searchFor(InputTag.class, true));
    }

    /**
     * Get the list of text areas.
     * @return Textarea elements in the form.
     */
    public NodeList getFormTextareas()
    {
        return (getChildren().searchFor(TextareaTag.class, true));
    }

    /**
     * Get the value of the action attribute.
     * @return The submit url of the form.
     */
    public String getFormLocation()
    {
        return (getAttribute("ACTION"));
    }

    /**
     * Set the form location. Modification of this element will cause the HTML rendering
     * to change as well (in a call to toHTML()).
     * @param url The new FORM location
     */
    public void setFormLocation(String url)
    {
        setAttribute ("ACTION", url);
    }

    /**
     * Returns the method of the form, GET or POST.
     * @return String The method of the form (GET if nothing is specified).
     */
    public String getFormMethod()
    {
        String ret;
        
        ret = getAttribute("METHOD");
        if (null == ret)
            ret = GET;

        return (ret);
    }

    /**
     * Get the input tag in the form corresponding to the given name
     * @param name The name of the input tag to be retrieved
     * @return Tag The input tag corresponding to the name provided
     */
    public InputTag getInputTag (String name)
    {
        InputTag inputTag;
        boolean found;
        String inputTagName;
        
        inputTag = null;
        found = false;
        for (SimpleNodeIterator e = getFormInputs().elements();e.hasMoreNodes() && !found;)
        {
            inputTag = (InputTag)e.nextNode();
            inputTagName = inputTag.getAttribute("NAME");
            if (inputTagName!=null && inputTagName.equalsIgnoreCase(name))
                found=true;
        }
        if (found)
            return (inputTag);
        else
            return (null);
    }

    /**
     * Get the value of the name attribute.
     * @return String The name of the form
     */
    public String getFormName()
    {
        return (getAttribute("NAME"));
    }

    /**
     * Find the textarea tag matching the given name
     * @param name Name of the textarea tag to be found within the form
     */
    public TextareaTag getTextAreaTag(String name)
    {
        TextareaTag textareaTag=null;
        boolean found = false;
        for (SimpleNodeIterator e=getFormTextareas ().elements();e.hasMoreNodes() && !found;)
        {
            textareaTag = (TextareaTag)e.nextNode();
            String textAreaName = textareaTag.getAttribute("NAME");
            if (textAreaName!=null && textAreaName.equals(name))
                found = true;
        }
        if (found)
            return (textareaTag);
        else
            return (null);
    }

    /**
     * @return A textual representation of the form tag.
     */
    public String toString()
    {
        return "FORM TAG : Form at "+getFormLocation()+"; begins at : "+getStartPosition ()+"; ends at : "+getEndPosition ();
    }
    
    /**
     * Extract the location of the image, given the tag, and the url
     * of the html page in which this tag exists.
     * @param tag The form tag with the 'ACTION' attribute.
     * @param url URL of web page being parsed.
     */
    public String extractFormLocn(String url)// throws ParserException
    {
        String formURL;
        
        formURL = getAttribute("ACTION");
        if (null == formURL)
            return "";
        else
            return (getPage ().getLinkProcessor ().extract (formURL, url));
    }

    /**
     * Override this because we need a trigger to set the ACTION attribute.
     * NOTE: setting of the children is the last thing done on the tag
     * after creation.
     * @param children The new list of children this node contains.
     */
    public void setChildren (NodeList children)
    {
        String url;

        super.setChildren (children);

        // ... is it true that without an ACTION the default is to send it back to the same page?
        url = extractFormLocn(getPage ().getUrl ());
        if (null != url && 0 < url.length())
            setAttribute ("ACTION",url);
    }
}
