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
 * <li>Preventing more tags of its own type to appear as children
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
 * be corrected. The default behavior is to allow nesting.
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "FORM" };
 *   private static final String [] ENDERS = {};
 *   private static final String [] END_TAG_ENDERS = { "BODY", "HTML" };
 *   MyScanner() {
 *      super(MATCH_IDS, ENDERS,END_TAG_ENDERS, false);
 *   }
 *   ...
 * }
 * </pre>
 * Inside the scanner, use createTag() to specify what tag needs to be created.
 */
public abstract class CompositeTagScanner extends TagScanner
{
    protected String [] nameOfTagToMatch;
    private boolean allowSelfChildren;
    protected Set tagEnderSet;
    private Set endTagEnderSet;
    private boolean balance_quotes;

    public CompositeTagScanner(String [] nameOfTagToMatch)
    {
        this(nameOfTagToMatch,new String[] {});
    }

    public CompositeTagScanner(String [] nameOfTagToMatch, String [] tagEnders)
    {
        this("",nameOfTagToMatch,tagEnders);
    }

    public CompositeTagScanner(String [] nameOfTagToMatch, String [] tagEnders, boolean allowSelfChildren)
    {
        this("",nameOfTagToMatch,tagEnders,allowSelfChildren);
    }

    public CompositeTagScanner(String filter, String [] nameOfTagToMatch)
    {
        this(filter,nameOfTagToMatch,new String [] {},true);
    }

    public CompositeTagScanner(String filter, String [] nameOfTagToMatch, String [] tagEnders)
    {
        this(filter,nameOfTagToMatch,tagEnders,true);
    }

    public CompositeTagScanner(
        String filter,
        String [] nameOfTagToMatch,
        String [] tagEnders,
        boolean allowSelfChildren) 
    {
        this(filter,nameOfTagToMatch,tagEnders,new String[] {}, allowSelfChildren);
    }

    public CompositeTagScanner(
        String filter,
        String [] nameOfTagToMatch,
        String [] tagEnders,
        String [] endTagEnders,
        boolean allowSelfChildren)
    {
        this(filter,nameOfTagToMatch,tagEnders,endTagEnders, allowSelfChildren, false);
    }

   /**
    * Constructor specifying all member fields.
    * @param filter A string that is used to match which tags are to be allowed
    * to pass through. This can be useful when one wishes to dynamically filter
    * out all tags except one type which may be programmed later than the parser.
    * @param nameOfTagToMatch The tag names recognized by this scanner.
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
        String [] nameOfTagToMatch,
        String [] tagEnders,
        String [] endTagEnders,
        boolean allowSelfChildren,
        boolean balance_quotes) 
    {
        super(filter);
        this.nameOfTagToMatch = nameOfTagToMatch;
        this.allowSelfChildren = allowSelfChildren;
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
     * Performs an immediate call to {@link #shouldCreateEndTagAndExit} to
     * allow subclasses to override the scan is a primitive way. If
     * <code>true</code>, returns a virtual end tag and repositions the lexer
     * to re-read that same tag.<p>
     * Otherwise, calls {@link #beforeScanningStarts} and begins scanning.
     * An initial test is performed for an empty XML tag, in which case
     * the start tag and end tag of the returned tag are the same and it has
     * no children.<p>
     * If it's not an empty XML tag, the lexer is repeatedly asked for
     * subsequent nodes until an end tag is found or a node is encountered
     * that matches the tag ender set or end tag ender set, or a node of
     * the same type is found and {@link #isAllowSelfChildren} returns
     * <code>false</code>. In all but the first case, a virtual end tag
     * is created. Each node found that is not the end tag is added to
     * the list of children and a call made to {@link #childNodeEncountered}.<p>
     * The scanner's {@link #createTag} method is called with details about
     * the start tag, end tag and children. The attributes from the start tag
     * will wind up duplicated in the newly created tag, so the start tag is
     * kind of redundant (and may be removed in subsequent refactoring).
     * @param tag The tag this scanner is responsible for. This will be the
     * start (and possibly end) tag passed to {@link #createTag}.
     * @param url The url for the page the tag is discovered on.
     * @param lexer The source of subsequent nodes.
     * @return The scanner specific tag from the call to {@link #createTag}.,
     * or the virtual end tag if {@link #shouldCreateEndTagAndExit} returned
     * <code>true</code>.
     */
    public Tag scan (Tag tag, String url, Lexer lexer) throws ParserException
    {
        Node node;
        NodeList nodeList;
        Tag endTag;
        CompositeTag composite;
        Tag ret;
        
        if (shouldCreateEndTagAndExit ())
        {
            ret = createVirtualEndTag (tag, lexer.getPage (), tag.elementBegin ());
            lexer.setPosition (tag.elementBegin ());
        }
        else
        {
            beforeScanningStarts ();
            nodeList = new NodeList ();
            endTag = null;
            
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
                            Tag end = (Tag)node;
                            // check for normal end tag
                            if (end.isEndTag () && end.getTagName ().equals (tag.getTagName ()))
                            {
                                endTag = end;
                                node = null;
                            }
                            else if (isTagToBeEndedFor (end) || // check DTD
                                (   // check for child of same name not allowed
                                    !(end.isEndTag ()) &&
                                    !isAllowSelfChildren () &&
                                    end.getTagName ().equals (tag.getTagName ())
                                ))
                            {
                                endTag = createVirtualEndTag (tag, lexer.getPage (), end.elementBegin ());
                                lexer.setPosition (end.elementBegin ());
                                node = null;
                            }
                        }
                        
                        if (null != node)
                        {
                            nodeList.add (node);
                            childNodeEncountered (node);
                        }
                    }
                }
            while (null != node);
            
            if (null == endTag)
                endTag = createVirtualEndTag (tag, lexer.getPage (), lexer.getCursor ().getPosition ());
            
            composite = (CompositeTag)createTag (lexer.getPage (), tag.elementBegin (), endTag.elementEnd (), tag.getAttributesEx (), tag, endTag, nodeList);
            for (int i = 0; i < composite.getChildCount (); i++)
                composite.childAt (i).setParent (composite);
            ret = composite;
        }
        
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
     * Override this method if you wish to create any data structures or do anything
     * before the start of the scan. This is just after a tag has triggered the scanner
     * but before the scanner begins its processing.
     */
    public void beforeScanningStarts() 
    {
    }

    /**
     * This method is called everytime a child to the composite is found. It is useful when we
     * need to store special children seperately. Though, all children are collected anyway into a node list.
     */
    public void childNodeEncountered(Node node) 
    {
    }

    /**
     * For composite tags this shouldn't be used and hence throws an exception.
     */
    protected Tag createTag (Page page, int start, int end, Vector attributes, Tag tag, String url) throws ParserException
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
    public abstract Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException;

    public final boolean isTagToBeEndedFor(Tag tag)
    {
        String name;
        boolean ret;

        ret = false;
        name = tag.getTagName ();
        if (tag.isEndTag ())
            ret = endTagEnderSet.contains (name);
        else
            ret = tagEnderSet.contains (name);
        
        return (ret);
    }

    public final boolean isAllowSelfChildren() {
        return allowSelfChildren;
    }

    /**
     * Override this method to implement scanner logic that determines if the current scanner is
     * to be allowed. This is useful when there are rules which dont allow recursive tags of the same
     * type. @see BulletScanner
     * @return boolean true/false
     */
    public boolean shouldCreateEndTagAndExit() {
        return false;
    }
}
