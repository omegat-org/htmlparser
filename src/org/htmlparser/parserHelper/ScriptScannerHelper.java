//HTMLParser Library v1_4_20030921 - A java-based parser for HTML
//Copyright (C) Dec 31, 2000 Somik Raha
//
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
//For any questions or suggestions, you can write to me at :
//Email :somik@industriallogic.com
//
//Postal Address :
//Somik Raha
//Extreme Programmer & Coach
//Industrial Logic, Inc.
//2583 Cedar Street, Berkeley,
//CA 94708, USA
//Website : http://www.industriallogic.com
package org.htmlparser.parserHelper;

import org.htmlparser.*;
import org.htmlparser.scanners.*;
import org.htmlparser.tags.*;
import org.htmlparser.tags.data.*;
import org.htmlparser.util.*;



public class ScriptScannerHelper {

    private int endTagLoc;
    private Tag endTag;
    private Tag startTag;
    private int startingPos;
    private boolean sameLine;
    private boolean endTagFound;
    private NodeReader reader;
    private StringBuffer scriptContents;
    private ScriptScanner scriptScanner;
    private Tag tag;
    private String url;
    private String currLine;

    public ScriptScannerHelper(Tag tag, String url, NodeReader nodeReader, String currLine, ScriptScanner scriptScanner) {
        this.reader = nodeReader;
        this.scriptScanner = scriptScanner;
        this.tag = tag;
        this.url = url;
        this.currLine = currLine;
    }

    public Tag scan() throws ParserException {
        int startLine = reader.getLastLineNumber();
        startTag = tag;
        extractScriptTagFrom(currLine);
        if (isScriptEndTagNotFound()) {
            createScriptEndTag(tag, currLine);
        }
        return createScriptTagUsing(url, currLine, startLine);
    }

    private Tag createScriptTagUsing(String url, String currLine, int startLine) {
        return scriptScanner.createTag(
            new TagData(
                startTag.elementBegin(),
                endTag.elementEnd(),
                startLine,
                reader.getLastLineNumber(),
                startTag.getText(),
                currLine,
                url,
                false
            ), new CompositeTagData(
                startTag,endTag,createChildrenNodeList()
            )
        );
    }

    private NodeList createChildrenNodeList() {
        NodeList childrenNodeList = new NodeList();
        childrenNodeList.add(
            new StringNode(
                scriptContents,
                startTag.elementEnd(),
                endTag.elementBegin()-1
            )
        );
        return childrenNodeList;
    }

    private void createScriptEndTag(Tag tag, String currLine) {
        // If end tag doesn't exist, create one
        String endTagName = tag.getTagName();
        int endTagBegin = reader.getLastReadPosition()+1 ;
        int endTagEnd = endTagBegin + endTagName.length() + 2;
        endTag = new EndTag(
            new TagData(
                endTagBegin,
                endTagEnd,
                endTagName,
                currLine
            )
        );
    }

    private boolean isScriptEndTagNotFound() {
        return endTag == null;
    }

    private void extractScriptTagFrom(String currLine) throws ParserException {
        String line = null;
        scriptContents = new StringBuffer();
        endTagFound = false;

        endTag = null;
        line = currLine;
        sameLine = true;
        startingPos = startTag.elementEnd();
        do {
            doExtractionOfScriptContentsFrom(line);
            if (!endTagFound) {
                line = reader.getNextLine();
                startingPos = 0;
            }
            if (sameLine)
                sameLine = false;
        }
        while (line!=null && !endTagFound);
    }

    private void doExtractionOfScriptContentsFrom(String line) throws ParserException {
        endTagLoc = line.toUpperCase().indexOf(scriptScanner.getEndTag(),startingPos);
        findStartingAndEndingLocations(line);

        if (endTagLoc!=-1) {
            extractEndTagFrom(line);
        } else {
            continueParsing(line);
        }
    }

    private void continueParsing(String line) {
        if (sameLine)
            scriptContents.append(
                line.substring(
                    startTag.elementEnd()+1
                )
            );
        else {
            scriptContents.append(Parser.getLineSeparator());
            scriptContents.append(line);
        }
    }

    private void extractEndTagFrom(String line) throws ParserException {
        endTagFound = true;
        endTag = (EndTag)EndTag.find(line,endTagLoc);
        if (sameLine)
            scriptContents.append(
                getCodeBetweenStartAndEndTags(
                    line,
                    startTag,
                    endTagLoc)
            );
        else {
            scriptContents.append(Parser.getLineSeparator());
            scriptContents.append(line.substring(0,endTagLoc));
        }

        reader.setPosInLine(endTag.elementEnd());
    }

    private void findStartingAndEndingLocations(String line) {
        while (endTagLoc>0 && isThisEndTagLocationFalseMatch(line, endTagLoc)) {
            startingPos = endTagLoc+scriptScanner.getEndTag().length();
            endTagLoc = line.toUpperCase().indexOf(scriptScanner.getEndTag(), startingPos);
        }
    }

    public String getCodeBetweenStartAndEndTags(
        String line,
        Tag startTag,
        int endTagLoc) throws ParserException {
        try {

            return line.substring(
                startTag.elementEnd()+1,
                endTagLoc
            );
        }
        catch (Exception e) {
            StringBuffer msg = new StringBuffer("Error in getCodeBetweenStartAndEndTags():\n");
            msg.append("substring starts at: "+(startTag.elementEnd()+1)).append("\n");
            msg.append("substring ends at: "+(endTagLoc));
            throw new ParserException(msg.toString(),e);
        }
    }

    private boolean isThisEndTagLocationFalseMatch(String line, int endTagLoc) {
        if (endTagLoc+scriptScanner.getEndTag().length() > line.length()-1) return false;
        char charAfterSuspectedEndTag =
            line.charAt(endTagLoc+scriptScanner.getEndTag().length());
        return charAfterSuspectedEndTag=='"' || charAfterSuspectedEndTag=='\'';
    }
}
