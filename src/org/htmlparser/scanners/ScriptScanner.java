// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2003 Somik Raha
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
import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.scanners.ScriptDecoder;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * The ScriptScanner handles script code.
 * It gathers all interior nodes into one undifferentiated string node.
 */
public class ScriptScanner
    extends
        CompositeTagScanner
{
    /**
     * Create a script scanner.
     */
    public ScriptScanner()
    {
    }

    /**
     * Scan for script.
     * Accumulates nodes returned from the lexer, until &lt;/SCRIPT&gt;,
     * &lt;BODY&gt; or &lt;HTML&gt; is encountered. Replaces the node factory
     * in the lexer with a new (empty) one to avoid other scanners missing their 
     * end tags and accumulating even the &lt;/SCRIPT&gt; tag.
     * @param tag The tag this scanner is responsible for.
     * @param lexer The source of subsequent nodes.
     * @param stack The parse stack, <em>not used</em>.
     */
    public Tag scan (Tag tag, Lexer lexer, NodeList stack)
        throws ParserException
    {
        String language;
        Node node;
        boolean done;
        int position;
        int startpos;
        int endpos;
        Tag end;
        NodeFactory factory;
        Text content;
        CompositeTag ret;

        done = false;
        startpos = lexer.getPosition ();
        endpos = startpos;
        end = null;
        factory = lexer.getNodeFactory ();
        if (tag instanceof ScriptTag)
        {
            language = ((ScriptTag)tag).getLanguage ();
            if ((null != language) &&
                (language.equalsIgnoreCase ("JScript.Encode") ||
                 language.equalsIgnoreCase ("VBScript.Encode")))
            {
                int start = lexer.getPosition ();
                String code = ScriptDecoder.Decode (lexer.getPage (), lexer.getCursor ());
                ((ScriptTag)tag).setScriptCode (code);
                endpos = lexer.getPosition ();
            }
        }
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
                end = new Tag (lexer.getPage (), endpos, endpos, new Vector ());
            ret = (CompositeTag)tag;
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
