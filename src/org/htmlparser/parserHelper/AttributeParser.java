// HTMLParser Library v1_4_20030907 - A java-based parser for HTML
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

import java.util.Hashtable;
import java.util.StringTokenizer;

import org.htmlparser.tags.Tag;


/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * @author Somik Raha, Kaarle Kaila
 * @version 7 AUG 2001
 */
public class AttributeParser {
    private final String DELIMETERS = " \t\r\n\f=\"'>";
    private final String DELIMETERS_WITHOUT_EQUALS = " \t\r\n\f\"'>";
    private final char DOUBLE_QUOTE = '\"';
    private final char SINGLE_QUOTE = '\'';
    private String delim;



    private Hashtable attributeTable;
    private String element;
    private String name;
    private String value;
    private String part;
    private String empty;
    private boolean equal;
    private StringTokenizer tokenizer;
    private boolean doubleQuote;
    private boolean singleQuote;
    private boolean ready;
    private String currentToken;
    private String tokenAccumulator;
    /**
    * Method to break the tag into pieces.
    * @param text All the text within the tag inside &lt; and &gt;.
    * @return A Hastable with elements containing the
    * pieces of the tag. The tag-name has the value field set to
    * the constant Tag.TAGNAME. In addition the tag-name is
    * stored into the Hashtable with the name Tag.TAGNAME
    * where the value is the name of the tag.
    * Tag parameters without value
    * has the value "". Parameters with value are represented
    * in the Hastable by a name/value pair.
    * As html is case insensitive but Hastable is not are all
    * names converted into UPPERCASE to the Hastable
    * E.g extract the href values from A-tag's and print them
    * <pre>
    *
    *    Tag tag;
    *    Hashtable h;
    *    String tmp;
    *    try {
    *        NodeReader in = new NodeReader(new FileReader(path),2048);
    *        Parser p = new Parser(in);
    *        Enumeration en = p.elements();
    *        while (en.hasMoreElements()) {
    *            try {
    *                tag = (Tag)en.nextElement();
    *                h = tag.parseParameters();
    *                tmp = (String)h.get(tag.TAGNAME);
    *                if (tmp != null && tmp.equalsIgnoreCase("A")) {;
    *                    System.out.println("URL is :" + h.get("HREF"));
    *                }
    *            } catch (ClassCastException ce){}
    *        }
    *    }
    *    catch (IOException ie) {
    *        ie.printStackTrace();
    *    }
    * </pre>
    *
    */
   public Hashtable parseAttributes (String text) {
        attributeTable = new SpecialHashtable();
        part = null;
        empty = null;
        name=null;
        value=null;
        element=null;
        equal = false;
        delim=DELIMETERS;
        tokenizer = new StringTokenizer(text,delim,true);
        while (true) {
            part=getNextPartUsing(delim);
            delim=DELIMETERS;
            if (element==null && part != null && !part.equals("=")){
                element = part;
                putDataIntoTable(attributeTable,element,null,true);
            }
            else {
                if (isValid(part)) {
                    process(part);
                }
                else {
                    processInvalidPart();
                    if (!tokenizer.hasMoreTokens ())
                        break;
                }
            }
        }
        if (null == element) // handle no contents
            putDataIntoTable(attributeTable,"",null,true);
        return attributeTable;
    }

    private void processInvalidPart ()
    {
        if (name != null)
        {
            if (name.equals ("/"))
                putDataIntoTable (attributeTable,Tag.EMPTYTAG,"",false);
            else
            {
                if (null == part)
                    if ((null != value) && value == Tag.NOTHING)
                        putDataIntoTable (attributeTable,name,Tag.NOTHING,false);
                    else
                        putDataIntoTable (attributeTable,name,Tag.NULLVALUE,false);
                else
                    putDataIntoTable (attributeTable,name,"",false);
            }
            name=null;
            value=null;
            equal=false;
        }
    }

    private boolean isValid(String part) {
        return part != null && (0 < part.length ());
    }

    private void process(String part) {
        if (name == null) {
            if (!part.substring(0,1).equals(" ")) {
                name = part;
                equal=true;
            }
        }
        else {
            if (equal){
                if (part.equals("=")) {
                    equal=false;
                    delim=DELIMETERS_WITHOUT_EQUALS;
                    value=Tag.NOTHING;
                }
                else {
                     putDataIntoTable(attributeTable,name,Tag.NULLVALUE,false);
                     name=part;
                     value=null;
                }
            }
            if (!equal && !part.equals("=")) {
                value=part;
                putDataIntoTable(attributeTable,name,value,false);
                name=null;
                value=null;
            }
        }
    }

    private String getNextPartUsing(String delimiter) {
        tokenAccumulator = null;
        doubleQuote = false;
        singleQuote = false;
        ready = false;
        while (ready == false && tokenizer.hasMoreTokens()) {
            currentToken = tokenizer.nextToken(delimiter);

            if (doubleQuote || singleQuote) {
                combineTokensInsideSingleOrDoubleQuotes();
            } else if (isCurrentTokenDoubleQuote()){
                doubleQuote= true;
                tokenAccumulator = "";
            } else if (isCurrentTokenSingleQuote()){
                singleQuote=true;
                tokenAccumulator="";
            } else {
                tokenAccumulator = currentToken;
                ready = isReadyWithNextPart(currentToken);
            }
        }
        return tokenAccumulator;
    }

    private boolean isReadyWithNextPart(String currentToken) {
        boolean ready = false;
        if (isDelimeter(currentToken)) {
            if (currentToken.equals("=")){
                ready=true;
            }
        }
        else {
            ready=true;
        }
        return ready;
    }

    private boolean isDelimeter(String token) {
        return delim.indexOf(tokenAccumulator)>=0;
    }

    private boolean isCurrentTokenSingleQuote() {
        return currentToken.charAt(0)==SINGLE_QUOTE;
    }

    private boolean isCurrentTokenDoubleQuote() {
        return currentToken.charAt(0)==DOUBLE_QUOTE;
    }

    private void combineTokensInsideSingleOrDoubleQuotes() {
        if (doubleQuote && currentToken.charAt(0)==DOUBLE_QUOTE){
            doubleQuote= false;
            ready=true;
        } else if (singleQuote && currentToken.charAt(0)==SINGLE_QUOTE) {
            singleQuote=false;
            ready=true;
        }else {
            tokenAccumulator += currentToken;
        }
    }


    private void putDataIntoTable(Hashtable h,String name,String value,boolean isName) {
        if (isName && value == null) value=Tag.TAGNAME;
        else if (value==null) value = ""; // Hashtable does not accept nulls
        if (isName) {
            // store tagname as tag.TAGNAME,tag
            h.put(value,new String[] {value, name.toUpperCase()});
        }
        else {
            // store tag parameters as NAME, value
            h.put(name.toUpperCase(),new String[] {name, value });
        }
    }
}
