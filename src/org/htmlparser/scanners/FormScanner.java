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

package org.htmlparser.scanners;

import java.util.Stack;
import java.util.Vector;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Scanner for form tags.
 */
public class FormScanner extends CompositeTagScanner
{
    private static final String [] MATCH_ID = { "FORM" };
    public static final String PREVIOUS_DIRTY_LINK_MESSAGE="Encountered a form tag after an open link tag.\nThere should have been an end tag for the link before the form tag began.\nCorrecting this..";
    private boolean linkScannerAlreadyOpen=false;
    private static final String [] formTagEnders = {"HTML","BODY"};
    
    private Stack stack = new Stack();

    /**
     * Constructs a form scanner.
     * Adds input, textarea, select and option scanners to the parser's
     * scanner list.
     */
    public FormScanner(Parser parser)
    {
        this("", parser);
    }

    /**
     * Overriding the constructor to accept the filter.
     */
    public FormScanner(String filter, Parser parser)
    {
        super(filter,MATCH_ID,formTagEnders,false);
        parser.addScanner(new InputTagScanner("-i"));
        parser.addScanner(new TextareaTagScanner("-t",stack));
        parser.addScanner(new SelectTagScanner("-select", stack));
        parser.addScanner(new OptionTagScanner("-option",stack));
    }

    /**
     * Extract the location of the image, given the tag, and the url
     * of the html page in which this tag exists.
     * @param tag The form tag with the 'ACTION' attribute.
     * @param url URL of web page being parsed.
     */
    public String extractFormLocn(Tag tag,String url) throws ParserException
    {
        try {
            String formURL= tag.getAttribute("ACTION");
            if (formURL==null) return ""; else
            return (new LinkProcessor()).extract(formURL, url);
        }
        catch (Exception e) {
            String msg;
            if (tag!=null) msg=  tag.getText(); else msg="";
            throw new ParserException("HTMLFormScanner.extractFormLocn() : Error in extracting form location, tag = "+msg+", url = "+url,e);
        }
    }

    public String extractFormName(Tag tag)
    {
        return tag.getAttribute("NAME");
    }

    public String extractFormMethod(Tag tag)
    {
        String method = tag.getAttribute("METHOD");
        if (method==null) method = FormTag.GET;
        return method.toUpperCase();

    }

    /**
     * @see org.htmlparser.scanners.TagScanner#getID()
     */
    public String [] getID()
    {
        return MATCH_ID;
    }

    public boolean evaluate(Tag tag, TagScanner previousOpenScanner)
    {
        if (previousOpenScanner instanceof LinkScanner)
        {
            linkScannerAlreadyOpen = true;
            StringBuffer msg= new StringBuffer();
                msg.append(tag.toHtml ());
                msg.append(PREVIOUS_DIRTY_LINK_MESSAGE);
                feedback.warning(msg.toString());
                // This is dirty HTML. Assume the current tag is
                // not a new link tag - but an end tag. This is actually a really wild bug -
                // Internet Explorer actually parses such tags.
                // So - we shall then proceed to fool the scanner into sending an endtag of type </A>
                // For this - set the dirty flag to true and return
        }
        else
            linkScannerAlreadyOpen = false;
        return super.evaluate(tag, previousOpenScanner);
    }

    public Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
    {
        FormTag ret;

        // special step here...
        // not sure why the recursion is tracked this way,
        // rather than using the ENDERS and END_TAG_ENDERS arrays...
        if (!stack.empty () && (this == stack.peek ()))
            stack.pop ();

        ret = new FormTag ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);
        ret.setStartTag (startTag);
        ret.setEndTag (endTag);
        ret.setChildren (children);

        // special step here...
        // ... is it true that without an ACTION the default is to send it back to the same page?
        String formUrl = extractFormLocn(startTag, page.getUrl ());
        if (formUrl!=null && formUrl.length()>0)
            startTag.setAttribute("ACTION",formUrl);

        return (ret);
    }

    public void beforeScanningStarts()
    {
        stack.push(this);
    }
}
