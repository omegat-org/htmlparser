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

package org.htmlparser.parserHelper;

import java.util.Vector;
import org.htmlparser.Node;
import org.htmlparser.lexer.Cursor;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class CompositeTagScannerHelper {
    private CompositeTagScanner scanner;
    private Tag mTag;
    private Lexer mLexer;
    private Tag endTag;
    private NodeList nodeList;
    private boolean endTagFound;
    private boolean balance_quotes;

    public CompositeTagScannerHelper(
        CompositeTagScanner scanner,
        Tag tag,
        Lexer lexer,
        boolean balance_quotes) {

        this.scanner = scanner;
        mTag = tag;
        mLexer = lexer;
        this.endTag = null;
        this.nodeList = new NodeList();
        this.endTagFound = false;
        this.balance_quotes = balance_quotes;
    }

    public Tag scan() throws ParserException {
        if (shouldCreateEndTagAndExit()) {
            return createEndTagAndRepositionReader();
        }
        scanner.beforeScanningStarts();
        Node currentNode = mTag;

        doEmptyXmlTagCheckOn(currentNode);
        if (!endTagFound) {
            do {
                currentNode = mLexer.nextNode (balance_quotes);
                if (currentNode==null)
                    continue;
                if (currentNode instanceof Tag)
                {
                    Tag possibleEndTag = (Tag)currentNode;
                    if (scanner.isTagToBeEndedFor(possibleEndTag) ||
                        (
                            !(possibleEndTag.isEndTag ()) &&
                            !scanner.isAllowSelfChildren() &&
                            possibleEndTag.getTagName().equals(mTag.getTagName())            
                        ))
                    {
                        createCorrectionEndTagBefore(possibleEndTag.elementBegin());
                        mLexer.setPosition (possibleEndTag.elementBegin ());
                        endTagFound = true;
                    }
                }

                doEmptyXmlTagCheckOn(currentNode);
                if (!endTagFound)
                    doChildAndEndTagCheckOn(currentNode);
            }
            while (currentNode!=null && !endTagFound);
        }
        if (endTag==null)
            createCorrectionEndTagBefore (mLexer.getCursor ().getPosition ());

        return createTag();
    }

    private boolean shouldCreateEndTagAndExit() {
        return scanner.shouldCreateEndTagAndExit();
    }

    private Tag createEndTagAndRepositionReader() {
        createCorrectionEndTagBefore (mTag.elementBegin ());
        mLexer.setPosition (mTag.elementBegin ());
        return endTag;
    }

    private void createCorrectionEndTagBefore(int position)
    {
        String endTagName = "/" + mTag.getRawTagName();
        Vector attributes = new Vector ();
        attributes.addElement (new Attribute (endTagName, (String)null));
        TagData data = new TagData(
                endTagName,
                position,
                attributes,
                mLexer.getPage ().getUrl (),
                false);
        endTag = new Tag (data);
    }

//    private void createCorrectionEndTagBefore(Tag possibleEndTagCauser) {
//        String endTagName = "/" + tag.getTagName();
//        int endTagBegin = possibleEndTagCauser.elementBegin();
//        int endTagEnd = endTagBegin + endTagName.length() + 2;
//        possibleEndTagCauser.setTagBegin(endTagEnd+1);
//        Vector attributes = new Vector ();
//        attributes.addElement (new Attribute (endTagName, (String)null));
//        TagData data = new TagData(
//                endTagName,
//                endTagBegin,
//                attributes,
//                mLexer.getPage ().getUrl (),
//                false);
//
//        endTag = new Tag(data);
//    }

    private Tag createTag() throws ParserException
    {
        TagData data;
        
        data =  new TagData(
            mLexer.getPage (),
            mTag.elementBegin(),
            endTag.elementEnd(),
            mTag.getAttributesEx (),
            mLexer.getPage ().getUrl (),
            mTag.isEmptyXmlTag ());

        CompositeTag newTag = (CompositeTag)scanner.createTag (data,
            new CompositeTagData(
                mTag,endTag,nodeList
            )
        );
        for (int i=0;i<newTag.getChildCount();i++) {
            Node child = newTag.childAt(i);
            child.setParent(newTag);
        }
        return newTag;
    }

    private void doChildAndEndTagCheckOn(Node currentNode)
    {
        Tag tag;

        if (currentNode instanceof Tag)
        {
            tag = (Tag)currentNode;
            if (tag.isEndTag ())
            {
                if (isExpectedEndTag (tag))
                {
                    endTagFound = true;
                    endTag =tag;
                    return;
                }
            }
        }
        nodeList.add(currentNode);
        scanner.childNodeEncountered(currentNode);
    }

    private boolean isExpectedEndTag (TagNode possibleEndTag)
    {
        return (possibleEndTag.getTagName().equals (mTag.getTagName ()));
    }

    private void doEmptyXmlTagCheckOn(Node currentNode) {
        if (currentNode instanceof Tag) {
            Tag possibleEndTag = (Tag)currentNode;
            if (mTag.isEmptyXmlTag ()) {
                endTag = possibleEndTag;
                endTagFound = true;
            }
        }
    }

//    private void doForceCorrectionCheckOn(Tag possibleEndTag) {
//    }

//    private boolean isEndTagMissing(Tag possibleEndTag) {
//        return
//            scanner.isTagToBeEndedFor(possibleEndTag) ||
//            (
//                !(possibleEndTag.isEndTag ()) &&
//                !scanner.isAllowSelfChildren() &&
//                possibleEndTag.getTagName().equals(tag.getTagName())            
//            );
//    }

//    public boolean isXmlEndTag(Tag tag) {
//        String tagText = tag.getText();
//        int lastSlash = tagText.lastIndexOf("/");
//        return (lastSlash == tagText.length()-1 || tag.isEmptyXmlTag()) && tag.getText().indexOf("://")==-1;
//    }
}
