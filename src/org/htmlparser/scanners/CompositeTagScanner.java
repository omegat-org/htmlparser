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

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * To create your own scanner that can create tags tht hold children, create a subclass of this class.
 * The composite tag scanner can be configured with:<br>
 * <ul>
 * <li>Tags which will trigger a match</li>
 * <li>Tags which when encountered before a legal end tag, should force a correction</li>
 * </ul>
 * Here are examples of each:<BR>
 * <B>Tags which will trigger a match</B>
 * If we wish to recognize &lt;mytag&gt;,
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "MYTAG" };
 *   MyScanner() {
 *      super(MATCH_IDS);
 *   }
 *   ...
 * }
 * </pre>
 * <B>Tags which force correction</B>
 * If we wish to insert end tags if we get a </BODY> or </HTML> without recieving
 * &lt;/mytag&gt;
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "MYTAG" };
 *   private static final String [] ENDERS = {};
 *   private static final String [] END_TAG_ENDERS = { "BODY", "HTML" };
 *   MyScanner() {
 *      super(MATCH_IDS, ENDERS, END_TAG_ENDERS, true);
 *   }
 *   ...
 * }
 * </pre>
 * <B>Preventing children of same type</B>
 * This is useful when you know that a certain tag can never hold children of its own type.
 * e.g. &lt;FORM&gt; can never have more form tags within it. If it does, it is an error and should
 * be corrected. Specify the tagEnders set to contain (at least) the match ids.
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "FORM" };
 *   private static final String [] END_TAG_ENDERS = { "BODY", "HTML" };
 *   MyScanner() {
 *      super(MATCH_IDS, MATCH_IDS, END_TAG_ENDERS, false);
 *   }
 *   ...
 * }
 * </pre>
 * Inside the scanner, use createTag() to specify what tag needs to be created.
 */
public class CompositeTagScanner extends TagScanner
{
    protected Set tagEnderSet;
    private Set endTagEnderSet;
    private boolean balance_quotes;

    public CompositeTagScanner()
    {
        this(new String[] {});
    }

    public CompositeTagScanner(String [] tagEnders)
    {
        this("",tagEnders);
    }

    public CompositeTagScanner(String filter)
    {
        this(filter,new String [] {});
    }

    public CompositeTagScanner(
        String filter,
        String [] tagEnders) 
    {
        this(filter,tagEnders,new String[] {});
    }

    public CompositeTagScanner(
        String filter,
        String [] tagEnders,
        String [] endTagEnders)
    {
        this(filter,tagEnders,endTagEnders, false);
    }

   /**
    * Constructor specifying all member fields.
    * @param filter A string that is used to match which tags are to be allowed
    * to pass through. This can be useful when one wishes to dynamically filter
    * out all tags except one type which may be programmed later than the parser.
    * @param tagEnders The non-endtag tag names which signal that no closing
    * end tag was found. For example, encountering &lt;FORM&gt; while
    * scanning a &lt;A&gt; link tag would mean that no &lt;/A&gt; was found
    * and needs to be corrected.
    * @param endTagEnders The endtag names which signal that no closing end
    * tag was found. For example, encountering &lt;/HTML&gt; while
    * scanning a &lt;BODY&gt; tag would mean that no &lt;/BODY&gt; was found
    * and needs to be corrected. These items are not prefixed by a '/'.
    * @param allowSelfChildren If <code>true</code> a tag of the same name is
    * allowed within this tag. Used to determine when an endtag is missing.
    * @param balance_quotes <code>true</code> if scanning string nodes needs to
    * honour quotes. For example, ScriptScanner defines this <code>true</code>
    * so that text within &lt;SCRIPT&gt;&lt;/SCRIPT&gt; ignores tag-like text
    * within quotes.
    */
    public CompositeTagScanner(
        String filter,
        String [] tagEnders,
        String [] endTagEnders,
        boolean balance_quotes) 
    {
        super(filter);
        this.balance_quotes = balance_quotes;
        this.tagEnderSet = new HashSet();
        for (int i=0;i<tagEnders.length;i++)
            tagEnderSet.add(tagEnders[i]);
        this.endTagEnderSet = new HashSet();
        for (int i=0;i<endTagEnders.length;i++)
            endTagEnderSet.add(endTagEnders[i]);
    }

    /**
     * Collect the children.
     * An initial test is performed for an empty XML tag, in which case
     * the start tag and end tag of the returned tag are the same and it has
     * no children.<p>
     * If it's not an empty XML tag, the lexer is repeatedly asked for
     * subsequent nodes until an end tag is found or a node is encountered
     * that matches the tag ender set or end tag ender set.
     * In the latter case, a virtual end tag is created.
     * Each node found that is not the end tag is added to
     * the list of children.<p>
     * The scanner's {@link #createTag} method is called with details about
     * the start tag, end tag and children. The attributes from the start tag
     * will wind up duplicated in the newly created tag, so the start tag is
     * kind of redundant (and may be removed in subsequent refactoring).
     * @param tag The tag this scanner is responsible for. This will be the
     * start (and possibly end) tag passed to {@link #createTag}.
     * @param url The url for the page the tag is discovered on.
     * @param lexer The source of subsequent nodes.
     * @return The scanner specific tag from the call to {@link #createTag}.
     */
    public Tag scan (Tag tag, String url, Lexer lexer) throws ParserException
    {
        Node node;
        NodeList nodeList;
        Tag endTag;
        String match;
        String name;
        TagScanner scanner;
        CompositeTag ret;
        
        nodeList = new NodeList ();
        endTag = null;
        match = tag.getTagName ();

        if (tag.isEmptyXmlTag ())
            endTag = tag;
        else
            do
            {
                node = lexer.nextNode (balance_quotes);
                if (null != node)
                {
                    if (node instanceof Tag)
                    {
                        Tag next = (Tag)node;
                        name = next.getTagName ();
                        // check for normal end tag
                        if (next.isEndTag () && name.equals (match))
                        {
                            endTag = next;
                            node = null;
                        }
                        else if (isTagToBeEndedFor (tag, next)) // check DTD
                        {
                            // insert a virtual end tag and backup one node
                            endTag = createVirtualEndTag (tag, lexer.getPage (), next.getStartPosition ());
                            lexer.setPosition (next.getStartPosition ());
                            node = null;
                        }
                        else if (!next.isEndTag ())
                        {
                            // now recurse if there is a scanner for this type of tag
                            scanner = next.getThisScanner ();
                            if ((null != scanner) && scanner.evaluate (next, null))
                                node = scanner.scan (next, lexer.getPage ().getUrl (), lexer);
                        }
                    }

                    if (null != node)
                        nodeList.add (node);
                }
            }
            while (null != node);

        if (null == endTag)
            endTag = createVirtualEndTag (tag, lexer.getPage (), lexer.getCursor ().getPosition ());

        ret = (CompositeTag)tag;
        ret.setEndTag (endTag);
        ret.setChildren (nodeList);
        for (int i = 0; i < ret.getChildCount (); i++)
            ret.childAt (i).setParent (ret);
        endTag.setParent (ret);
        ret.doSemanticAction ();

        return (ret);
    }

    /**
     * Creates an end tag with the same name as the given tag.
     * NOTE: This does not call the {@link #createTag} method, but may in the
     * future after refactoring.
     * @param tag The tag to end.
     * @param page The page the tag is on (virtually).
     * @param position The offset into the page at which the tag is to
     * be anchored.
     * @return An end tag with the name "/" + tag.getTagName() and a start
     * and end position at the given position. The fact these are equal may
     * be used to distinguish it as a virtual tag.
     */
    protected Tag createVirtualEndTag (Tag tag, Page page, int position)
    {
        Tag ret;
        String name;
        Vector attributes;
        
        name = "/" + tag.getRawTagName();
        attributes = new Vector ();
        attributes.addElement (new Attribute (name, (String)null));
        ret = new Tag (page, position, position, attributes);
        
        return (ret);
    }

    /**
     * For composite tags this shouldn't be used and hence throws an exception.
     */
    public Tag createTag (Page page, int start, int end, Vector attributes, Tag tag, String url) throws ParserException
    {
        throw new ParserException ("composite tags shouldn't be using this");
    }

    /**
     * You must override this method to create the tag of your choice upon successful parsing.
     * This method is called after the scanner has completed the scan.
     * The first four arguments are standard tag constructor arguments.
     * The last three are for the composite tag construction.
     * @param page The page the tag is found on.
     * @param start The starting offset in the page of the tag.
     * @param end The ending offset in the page of the tag.
     * @param attributes The contents of the tag as a list of {@list Attribute} objects.
     * @param startTag The tag that begins the composite tag.
     * @param endTag The tag that ends the composite tag. Note this could be a
     * virtual tag created to satisfy the scanner (check if it's starting and
     * ending position are the same).
     * @param children The list of nodes contained within the ebgin end tag pair.
     */
    public Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
    {
        CompositeTag ret;

        ret = new CompositeTag ();
        ret.setPage (page);
        ret.setStartPosition (start);
        ret.setEndPosition (end);
        ret.setAttributesEx (attributes);
        ret.setStartTag (startTag);
        ret.setEndTag (endTag);
        ret.setChildren (children);

        return (ret);        
    }

    public final boolean isTagToBeEndedFor (Tag current, Tag tag)
    {
        String name;
        String[] ends;
        boolean ret;

        ret = false;

        name = tag.getTagName ();
        if (tag.isEndTag ())
            ends = current.getEndTagEnders ();
        else
            ends = current.getEnders ();
        for (int i = 0; i < ends.length; i++)
            if (name.equalsIgnoreCase (ends[i]))
            {
                ret = true;
                break;
            }
        
        return (ret);
    }
}
