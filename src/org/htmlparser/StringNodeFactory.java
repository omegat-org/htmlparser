// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
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

package org.htmlparser;

import java.io.Serializable;
import org.htmlparser.lexer.Page;

import org.htmlparser.nodeDecorators.DecodingNode;
import org.htmlparser.nodeDecorators.EscapeCharacterRemovingNode;
import org.htmlparser.nodeDecorators.NonBreakingSpaceConvertingNode;

public class StringNodeFactory
    extends
        PrototypicalNodeFactory
    implements
        Serializable
{
    /**
     * Flag to tell the parser to decode strings returned by StringNode's toPlainTextString.
     * Decoding occurs via the method, org.htmlparser.util.Translate.decode()
     */
    protected boolean mDecode;


    /**
     * Flag to tell the parser to remove escape characters, like \n and \t, returned by StringNode's toPlainTextString.
     * Escape character removal occurs via the method, org.htmlparser.util.ParserUtils.removeEscapeCharacters()
     */
    protected boolean mRemoveEscapes;

    /**
     * Flag to tell the parser to convert non breaking space (from \u00a0 to a space " ").
     * If true, this will happen inside StringNode's toPlainTextString.
     */
    protected boolean mConvertNonBreakingSpaces;
    
    public StringNodeFactory ()
    {
        mDecode = false;
        mRemoveEscapes = false;
        mConvertNonBreakingSpaces = false;
    }

    //
    // NodeFactory interface override
    //

    /**
     * Create a new string node.
     * @param page The page the node is on.
     * @param start The beginning position of the string.
     * @param end The ending positiong of the string.
     */
    public Node createStringNode (Page page, int start, int end)
    {
        Node ret;
        
        ret = super.createStringNode (page, start, end);
        if (getDecode ())
            ret = new DecodingNode (ret);
        if (getRemoveEscapes ())
            ret = new EscapeCharacterRemovingNode (ret);
        if (getConvertNonBreakingSpaces ())
            ret = new NonBreakingSpaceConvertingNode (ret);

        return (ret);
    }

    /**
     * Set the decoding state.
     * @param decode If <code>true</code>, string nodes decode text using {@link org.htmlparser.util.Translate#decode}.
     */
    public void setDecode (boolean decode)
    {
        mDecode = decode;
    }

    /**
     * Get the decoding state.
     * @return <code>true</code> if string nodes decode text.
     */
    public boolean getDecode ()
    {
        return (mDecode);
    }

    /**
     * Set the escape removing state.
     * @param remove If <code>true</code>, string nodes remove escape characters.
     */
    public void setRemoveEscapes (boolean remove)
    {
        mRemoveEscapes = remove;
    }

    /**
     * Get the escape removing state.
     * @return The removing state.
     */
    public boolean getRemoveEscapes ()
    {
        return (mRemoveEscapes);
    }

    /**
     * Set the non-breaking space replacing state.
     * @param convert If <code>true</code>, string nodes replace &semi;nbsp; characters with spaces.
     */
    public void setConvertNonBreakingSpaces (boolean convert)
    {
        mConvertNonBreakingSpaces = convert;
    }

    /**
     * Get the non-breaking space replacing state.
     * @return The replacing state.
     */
    public boolean getConvertNonBreakingSpaces ()
    {
        return (mConvertNonBreakingSpaces);
    }
}
