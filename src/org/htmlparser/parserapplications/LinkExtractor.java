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

package org.htmlparser.parserapplications;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;

/**
 * LinkExtractor extracts all the links from the given webpage
 * and prints them on standard output.
 */
public class LinkExtractor {
    private String location;
    private Parser parser;
    public LinkExtractor(String location) {
        this.location = location;
        try {
            this.parser   = new Parser(location); // Create the parser object
        }
        catch (ParserException e) {
            e.printStackTrace();
        }

    }
    public void extractLinks() throws ParserException {
        System.out.println("Parsing "+location+" for links...");
        Node [] links = parser.extractAllNodesThatAre(LinkTag.class);
        for (int i = 0;i < links.length;i++) {
            LinkTag linkTag = (LinkTag)links[i];
            // To extract only mail addresses, uncomment the following line
            // if (linkTag.isMailLink())
            System.out.println(linkTag.getLink());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Syntax Error : Please provide the location(URL or file) to parse");
            System.exit(-1);
        }
        LinkExtractor linkExtractor = new LinkExtractor(args[0]);
        try {
            linkExtractor.extractLinks();
        }
        catch (ParserException e) {
            e.printStackTrace();
        }
    }
}

