// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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

import java.util.Vector;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.RemarkNode;
import org.htmlparser.StringNode;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.nodes.NodeFactory;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * The ScriptScanner handles javascript code.
 * It gathers all interior nodes into one undifferentiated string node.
 */
public class ScriptScanner extends CompositeTagScanner {
    private static final String SCRIPT_END_TAG = "</SCRIPT>";
    private static final String MATCH_NAME [] = {"SCRIPT"};
    private static final String ENDERS [] = {"BODY", "HTML"};

    public ScriptScanner() {
        super("",MATCH_NAME,ENDERS);
    }

    public ScriptScanner(String filter) {
        super(filter,MATCH_NAME,ENDERS);
    }

    public ScriptScanner(String filter, String[] nameOfTagToMatch) {
        super(filter,nameOfTagToMatch,ENDERS);
    }

    public String [] getID() {
        return MATCH_NAME;
    }

    public Tag createTag(
        TagData tagData,
        CompositeTagData compositeTagData) {
        return new ScriptTag(tagData,compositeTagData);
    }

    /**
     * Scan for script.
     * Accumulates nodes returned from the lexer, until &lt;/SCRIPT&gt;,
     * &lt;BODY&gt; or &lt;HTML&gt; is encountered. Replaces the node factory
     * in the lexer with a new Parser to avoid other scanners missing their 
     * end tags and accumulating even the &lt;/SCRIPT&gt;.
     */
    public Tag scan (Tag tag, String url, Lexer lexer)
        throws ParserException
    {
        Node node;
        boolean done;
        int position;
        StringNode last;
        Tag end;
        NodeFactory factory;
        TagData data;
        Tag ret;

        done = false;
        last = null;
        end = null;
        factory = lexer.getNodeFactory ();
        lexer.setNodeFactory (new Parser ()); // no scanners on a new Parser right?
        try
        {
            do
            {
                position = lexer.getPosition ();
                node = lexer.nextNode (true);
                if (null == node)
                    break;
                else
                    if (node instanceof Tag)
                        if (   ((Tag)node).isEndTag ()
                            && ((Tag)node).getTagName ().equals (MATCH_NAME[0]))
                        {
                            end = (Tag)node;
                            done = true;
                        }
                        else if (isTagToBeEndedFor ((Tag)node))
                        {
                            lexer.setPosition (position);
                            done = true;
                        }
                        else
                        {
                            // must be a string, even though it looks like a tag
                            if (null != last)
                                // append it to the previous one
                                last.setEndPosition (node.elementEnd ());
                            else
                                // TODO: need to remove this cast
                                last = (StringNode)lexer.createStringNode (lexer, node.elementBegin (), node.elementEnd ());
                        }
                    else if (node instanceof RemarkNode)
                    {
                        if (null != last)
                            last.setEndPosition (node.getEndPosition ());
                        else
                            // TODO: need to remove this cast
                            last = (StringNode)lexer.createStringNode (lexer, node.elementBegin (), node.elementEnd ());
                    }
                    else // StringNode
                    {
                        if (null != last)
                            last.setEndPosition (node.getEndPosition ());
                        else
                            // TODO: need to remove this cast
                            last = (StringNode)node;
                    }

            }
            while (!done);

            // build new string tag if required
            if (null == last)
                // TODO: need to remove this cast
                last = (StringNode)factory.createStringNode (lexer, position, position);
            // build new end tag if required
            if (null == end)
            {
                data =  new TagData(
                    "/" + tag.getTagName (),
                    tag.getEndPosition (),
                    new Vector (),
                    lexer.getPage ().getUrl (),
                    false);
                end = new Tag (data);
//TODO: use the factory: end = factory.createTagNode (mLexer, last.getEndPosition (), last.getEndPosition () + 
            }
            data =  new TagData(
                lexer.getPage (),
                tag.elementBegin(),
                end.elementEnd(),
                tag.getAttributesEx (),
                lexer.getPage ().getUrl (),
                tag.isEmptyXmlTag ());

            ret = createTag(
                data,
                new CompositeTagData(tag, end, new NodeList (last))
                );
        }
        finally
        {
            lexer.setNodeFactory (factory);
        }

        return (ret);
    }

    /**
     * Gets the end tag that the scanner uses to stop scanning. Subclasses of
     * <code>ScriptScanner</code> you should override this method.
     * @return String containing the end tag to search for, i.e. &lt;/SCRIPT&gt;
     */
    public String getEndTag()
    {
        return SCRIPT_END_TAG;
    }
}
