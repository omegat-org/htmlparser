// HTMLParser Library v1_4_20031207 - A java-based parser for HTML
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
//////////////////
// Java Imports //
//////////////////
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.htmlparser.AbstractNode;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.StringNode;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;

/**
 * TagScanner is an abstract superclass which is subclassed to create specific
 * scanners.
 * This isn't much use other than creating a specific tag type since scanning
 * is mostly done by the lexer level. If you want to match end tags and 
 * handle special syntax between tags, then you'll probably want to subclass
 * {@link CompositeTagScanner} instead. Use TagScanner when you have meta task
 * to do like setting the BASE url for the page when a BASE tag is encountered.
 * <br>
 * If you wish to write your own scanner, then you must implement scan().
 * You MAY implement evaluate() as well, if your evaluation logic is not based
 * on a match of the tag name.
 * You MUST implement getID() - which identifies your scanner uniquely in the hashtable of scanners.
 *
 * <br>
 * Also, you have a feedback object provided to you, should you want to send log messages. This object is
 * instantiated by Parser when a scanner is added to its collection.
 *
 */
public class TagScanner
    implements
        Serializable
{
    /**
     * A filter which is used to associate this tag. The filter contains a string
     * that is used to match which tags are to be allowed to pass through. This can
     * be useful when one wishes to dynamically filter out all tags except one type
     * which may be programmed later than the parser. Is also useful for command line
     * implementations of the parser.
     */
    protected String filter;
    
    /**
     * Default Constructor, automatically registers the scanner into a static array of
     * scanners inside Tag
     */
    public TagScanner ()
    {
        this ("");
    }

    /**
     * This constructor automatically registers the scanner, and sets the filter for this
     * tag.
     * @param filter The filter which will allow this tag to pass through.
     */
    public TagScanner (String filter)
    {
        this.filter=filter;
    }

    /**
     * This method is used to decide if this scanner can handle this tag type. If the
     * evaluation returns true, the calling side makes a call to scan().
     * <strong>This method has to be implemented meaningfully only if a first-word match with
     * the scanner id does not imply a match (or extra processing needs to be done).
     * Default returns true</strong>
     * @param tag The tag with a name that matches a value from {@link #getID}.
     * @param previousOpenScanner Indicates any previous scanner which hasn't
     * completed, before the current scan has begun, and hence allows us to
     * write scanners that can work with dirty html.
     */
    public boolean evaluate (Tag tag, TagScanner previousOpenScanner)
    {
        return (true);
    }
    
    public String getFilter()
    {
        return filter;
    }

    /**
     * Scan the tag and extract the information related to this type. The url of the
     * initiating scan has to be provided in case relative links are found. The initial
     * url is then prepended to it to give an absolute link.
     * The Lexer is provided in order to do a lookahead operation. We assume that
     * the identification has already been performed using the evaluate() method.
     * @param tag HTML Tag to be scanned for identification.
     * @param url The initiating url of the scan (Where the html page lies).
     * @param lexer Provides html page access.
     * @return The resultant tag (may be unchanged).
     */
    public Tag scan (Tag tag, String url, Lexer lexer) throws ParserException
    {
        Tag ret;
        
        ret = tag;
        ret.doSemanticAction ();

        return (ret);
    }

    public String [] getID ()
    {
        return (new String[0]);
    }
}
