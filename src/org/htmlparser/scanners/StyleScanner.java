// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Derrick Oswald
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.scanners;

import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFactory;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.Remark;
import org.htmlparser.Text;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.Tag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * The StyleScanner handles style elements.
 * It gathers all interior nodes into one undifferentiated string node.
 */
public class StyleScanner extends CompositeTagScanner
{
    /**
     * Create a style scanner.
     */
    public StyleScanner ()
    {
    }

    /**
     * Scan for style definitions.
     * Accumulates nodes returned from the lexer, until &lt;/STYLE&gt;,
     * &lt;BODY&gt; or &lt;HTML&gt; is encountered. Replaces the node factory
     * in the lexer with a new (empty) one to avoid other scanners missing their 
     * end tags and accumulating even the &lt;/STYLE&gt; tag.
     * @param tag The tag this scanner is responsible for.
     * @param lexer The source of subsequent nodes.
     * @param stack The parse stack, <em>not used</em>.
     */
    public Tag scan (Tag tag, Lexer lexer, NodeList stack)
        throws ParserException
    {
        Node node;
        boolean done;
        int position;
        int startpos;
        int endpos;
        Tag end;
        NodeFactory factory;
        Text content;
        Tag ret;

        done = false;
        startpos = lexer.getPosition ();
        endpos = startpos;
        end = null;
        factory = lexer.getNodeFactory ();
        lexer.setNodeFactory (new PrototypicalNodeFactory (true));
        try
        {
            do
            {
                position = lexer.getPosition ();
                node = lexer.nextNode (true);
                if (null == node)
                    done = true;
                else
                    if (node instanceof Tag)
                        if (   ((Tag)node).isEndTag ()
                            && ((Tag)node).getTagName ().equals (tag.getIds ()[0]))
                        {
                            end = (Tag)node;
                            done = true;
                        }
                        else if (isTagToBeEndedFor (tag, (Tag)node))
                        {
                            lexer.setPosition (position);
                            done = true;
                        }
                        else
                            // must be a string, even though it looks like a tag
                            endpos = node.getEndPosition ();
                    else if (node instanceof Remark)
                        endpos = node.getEndPosition ();
                    else // Text
                        endpos = node.getEndPosition ();

            }
            while (!done);

            content = factory.createStringNode (lexer.getPage (), startpos, endpos);
            // build new end tag if required
            if (null == end)
                end = (Tag)lexer.getNodeFactory ().createTagNode (
                    lexer.getPage (), endpos, endpos, new Vector ());
            ret = tag;
            ret.setEndTag (end);
            ret.setChildren (new NodeList (content));
            content.setParent (ret);
            end.setParent (ret);
            ret.doSemanticAction ();
        }
        finally
        {
            lexer.setNodeFactory (factory);
        }

        return (ret);
    }
}
