// HTMLParser Library v1_3_20030215 - A java-based parser for HTML
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

import org.htmlparser.tags.HTMLTag;


/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * @author Somik Raha, Kaarle Kaila
 * @version 7 AUG 2001
 */
public class AttributeParser {
    private final String delima = " \t\r\n\f=\"'>";
    private final String delimb = " \t\r\n\f\"'>";
    private final char doubleQuote = '\"';
    private final char singleQuote = '\'';
    private String delim;



	/**
	* Method to break the tag into pieces.
	* @param returns a Hastable with elements containing the
	* pieces of the tag. The tag-name has the value field set to
	* the constant HTMLTag.TAGNAME. In addition the tag-name is
	* stored into the Hashtable with the name HTMLTag.TAGNAME
	* where the value is the name of the tag.
	* Tag parameters without value
	* has the value "". Parameters with value are represented
	* in the Hastable by a name/value pair.
	* As html is case insensitive but Hastable is not are all
	* names converted into UPPERCASE to the Hastable
	* E.g extract the href values from A-tag's and print them
	* <pre>
	*
    *    HTMLTag tag;
	*    Hashtable h;
	*    String tmp;
    *    try {
    *        HTMLReader in = new HTMLReader(new FileReader(path),2048);
    *        HTMLParser p = new HTMLParser(in);
    *        Enumeration en = p.elements();
    *        while (en.hasMoreElements()) {
    *            try {
    *                tag = (HTMLTag)en.nextElement();
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
   public Hashtable parseAttributes(HTMLTag tag){
        Hashtable h = new Hashtable();
        String element,name,value,nextPart=null;
        String empty=null;
        name=null;
        value=null;
        element=null;
        boolean waitingForEqual=false;
        delim=delima;
        StringTokenizer tokenizer = new StringTokenizer(tag.getText(),delim,true);
        while (true) {
            nextPart=getNextPart(tokenizer,delim);
            delim=delima;
            if (element==null && nextPart != null && !nextPart.equals("=")){
                element = nextPart;
                putDataIntoTable(h,element,null,true);
            }
            else {
                if (nextPart != null) {
                    if (name == null) {
                        if (!nextPart.substring(0,1).equals(" ")) {
                            name = nextPart;
                            waitingForEqual=true;
                        }
                    }
                    else {
                        if (waitingForEqual){
                            if (nextPart.equals("=")) {
                                waitingForEqual=false;
                                delim=delimb;
                            }
                            else {
                                 putDataIntoTable(h,name,"",false);
                                 name=nextPart;
                                 value=null;
                            }
                        }
                        if (!waitingForEqual && !nextPart.equals("=")) {
                            value=nextPart;
                            putDataIntoTable(h,name,value,false);
                            name=null;
                            value=null;
                        }
                    }
                }
                else {
                    if (name != null) {
                      if (name.equals("/")) {
                        putDataIntoTable(h,HTMLTag.EMPTYTAG,"",false);
                      } else {
                        putDataIntoTable(h,name,"",false);
                      }
                      name=null;
                      value=null;
                    }
                    break;
                }
            }
        }
        return h;
    }

    private String getNextPart(StringTokenizer tokenizer,String deli){
        String tokenAccumulator=null;
        boolean isDoubleQuote=false;
        boolean isSingleQuote=false;
        boolean isDataReady=false;
        String currentToken;
        while (isDataReady == false && tokenizer.hasMoreTokens()) {
            currentToken = tokenizer.nextToken(deli);
            //
            // First let's combine tokens that are inside "" or ''
            //
            if (isDoubleQuote || isSingleQuote) {
                if (isDoubleQuote && currentToken.charAt(0)==doubleQuote){
                    isDoubleQuote= false;
                    isDataReady=true;
                } else if (isSingleQuote && currentToken.charAt(0)==singleQuote) {
                    isSingleQuote=false;
                    isDataReady=true;
                }else {
                    tokenAccumulator += currentToken;
                    continue;
                }
            } else if (currentToken.charAt(0)==doubleQuote){
                isDoubleQuote= true;
                tokenAccumulator = "";
                continue;
            } else if (currentToken.charAt(0)==singleQuote){
                isSingleQuote=true;
                tokenAccumulator="";
                continue;
            } else tokenAccumulator = currentToken;

            if (tokenAccumulator.equals(currentToken)) {

                if (delim.indexOf(tokenAccumulator)>=0) {
                    if (tokenAccumulator.equals("=")){
                        isDataReady=true;
                    }
                }
                else {

                    isDataReady=true;
                }
            }
            else isDataReady=true;

        }
        return tokenAccumulator;
    }


    private void putDataIntoTable(Hashtable h,String name,String value,boolean isName) {
        if (isName && value == null) value=HTMLTag.TAGNAME;
        else if (value==null) value = ""; // Hashtable does not accept nulls
        if (isName) {
            // store tagname as tag.TAGNAME,tag
            h.put(value,name.toUpperCase());
        }
        else {
            // store tag parameters as NAME, value
            h.put(name.toUpperCase(),value);
        }
    }
}
