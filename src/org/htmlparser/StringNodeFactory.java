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

package org.htmlparser;

import java.io.Serializable;

import org.htmlparser.nodeDecorators.DecodingNode;
import org.htmlparser.nodeDecorators.EscapeCharacterRemovingNode;
import org.htmlparser.nodeDecorators.NonBreakingSpaceConvertingNode;

public class StringNodeFactory implements Serializable {

    /**
     * Flag to tell the parser to decode strings returned by StringNode's toPlainTextString.
     * Decoding occurs via the method, org.htmlparser.util.Translate.decode()
     */
    private boolean shouldDecodeNodes = false;


    /**
     * Flag to tell the parser to remove escape characters, like \n and \t, returned by StringNode's toPlainTextString.
     * Escape character removal occurs via the method, org.htmlparser.util.ParserUtils.removeEscapeCharacters()
     */
    private boolean shouldRemoveEscapeCharacters = false;

    /**
     * Flag to tell the parser to convert non breaking space
     * (i.e. \u00a0) to a space (" ").  If true, this will happen inside StringNode's toPlainTextString.
     */
    private boolean shouldConvertNonBreakingSpace = false;

    public Node createStringNode(
        StringBuffer textBuffer,
        int textBegin,
        int textEnd) {
        Node newNode = new StringNode(textBuffer, textBegin, textEnd);
        if (shouldDecodeNodes())
            newNode = new DecodingNode(newNode);
        if (shouldRemoveEscapeCharacters())
            newNode = new EscapeCharacterRemovingNode(newNode);
        if (shouldConvertNonBreakingSpace())
            newNode = new NonBreakingSpaceConvertingNode(newNode);
        return newNode;
    }

    /**
     * Tells the parser to decode nodes using org.htmlparser.util.Translate.decode()
     */
    public void setNodeDecoding(boolean shouldDecodeNodes) {
            this.shouldDecodeNodes = shouldDecodeNodes;
        }

    public boolean shouldDecodeNodes() {
        return shouldDecodeNodes;
    }

    public void setEscapeCharacterRemoval(boolean shouldRemoveEscapeCharacters) {
        this.shouldRemoveEscapeCharacters = shouldRemoveEscapeCharacters;
    }

    public boolean shouldRemoveEscapeCharacters() {
        return shouldRemoveEscapeCharacters;
    }

    public void setNonBreakSpaceConversion(boolean shouldConvertNonBreakSpace) {
        this.shouldConvertNonBreakingSpace = shouldConvertNonBreakSpace;
    }

    public boolean shouldConvertNonBreakingSpace() {
        return shouldConvertNonBreakingSpace;
    }
}
