// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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

package org.htmlparser.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.scanners.ImageScanner;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.LinkProcessor;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class FunctionalTests extends ParserTestCase {

    static
    {
        System.setProperty ("org.htmlparser.tests.FunctionalTests", "FunctionalTests");
    }

    public FunctionalTests(String arg0) {
        super(arg0);
    }

    /**
     * Based on a suspected bug report by Annette Doyle,
     * to check if the no of image tags are correctly
     * identified by the parser
     */
    public void testNumImageTagsInYahooWithoutRegisteringScanners() throws ParserException {
        // First count the image tags as is
        int imgTagCount;
        int parserImgTagCount = countImageTagsWithHTMLParser();
        imgTagCount = findImageTagCount(getParser ());
        assertEquals("Image Tag Count",imgTagCount,parserImgTagCount);
    }

    public int findImageTagCount(Parser parser) {
        int imgTagCount = 0;
        parser.reset ();
        try
        {
            imgTagCount = countImageTagsWithoutHTMLParser(parser);
        }
        catch (IOException e)
        {
            System.err.println ("IO Exception occurred while counting tags");
        }
        return imgTagCount;
    }

    public int countImageTagsWithHTMLParser() throws ParserException {
        Parser parser = new Parser("http://education.yahoo.com/",new DefaultParserFeedback());
        parser.addScanner(new ImageScanner("-i"));
        setParser (parser);
        int parserImgTagCount = 0;
        Node node;
        for (NodeIterator e= parser.elements();e.hasMoreNodes();) {
            node = (Node)e.nextNode();
            if (node instanceof ImageTag) {
                parserImgTagCount++;
            }
        }
        return parserImgTagCount;
    }

    public int countImageTagsWithoutHTMLParser (Parser parser) throws IOException
    {
        BufferedReader lines;
        String line;
        int imgTagCount;
        
        imgTagCount = 0;
        lines = new BufferedReader (parser.getLexer ().getPage ().getSource ());
        do {
            line = lines.readLine();
            if (line!=null) {
                // Check the line for image tags
                String newline = line.toUpperCase();
                int fromIndex = -1;
                do {
                    fromIndex = newline.indexOf("<IMG",fromIndex+1);
                    if (fromIndex!=-1) {
                        imgTagCount++;
                    }
                }
                while (fromIndex!=-1);
            }
        }
        while (line!=null);
        return imgTagCount;
    }

    public static TestSuite suite() {
        return new TestSuite(FunctionalTests.class);
    }
}
