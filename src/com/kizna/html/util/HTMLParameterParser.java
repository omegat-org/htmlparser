package com.kizna.html.util;

import java.util.Hashtable;
import java.util.StringTokenizer;

import com.kizna.html.tags.HTMLTag;

/**
 * @author Somik Raha
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HTMLParameterParser {
	private final String delim = " \t\r\n\f=\"'>";
	private final char doubleQuote = '\"';
	private final char singleQuote = '\'';
	private final char equalTo = '=';
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
	* @author Kaarle Kaila
	* @version 7 AUG 2001
	*/
    
   public Hashtable parseParameters(HTMLTag tag){
        Hashtable h = new Hashtable();
        String name,value,tokenAccumulator,currentToken;     
        boolean isDoubleQuote=false;
        boolean isSingleQuote=false;
        boolean isValue=false;
        boolean isValueInited=false;
        boolean isName=true;
        boolean waitingForValue = false;
        name=null;
        value=null;
        tokenAccumulator=null;
        waitingForValue=false;          
        StringTokenizer token = new StringTokenizer(tag.getText() + " ",delim,true);
        while (token.hasMoreTokens()) {
            currentToken = token.nextToken();
            
            //
            // First let's combine tokens that are inside "" or ''
            //
            if (isDoubleQuote || isSingleQuote) {
                if (isDoubleQuote && currentToken.charAt(0)==doubleQuote){                
                    isDoubleQuote= false;                                   
                } else if (isSingleQuote && currentToken.charAt(0)==singleQuote) {
                    isSingleQuote=false;
                }else {               
                    tokenAccumulator += currentToken;   
                    continue;
                }
            } else if (currentToken.charAt(0)==doubleQuote){                
                isDoubleQuote= true;
                tokenAccumulator = "";
                isValueInited=true;
                continue;
            } else if (currentToken.charAt(0)==singleQuote){
                isSingleQuote=true;
                tokenAccumulator="";
                isValueInited=true;
                continue;                
            } else tokenAccumulator = currentToken;
            
            // above leaves tokenAccumulator with
            // - a delimter
            // - a name of a parameter or the tag itself
            // - a value of a parameter
            if (!isValueInited && delim.indexOf(tokenAccumulator)>=0) {
                // tokenAccumulator was a delimiter
                //System.out.println("k:" + tokenAccumulator  + ":m");
                if ( waitingForValue) {
                  if (tokenAccumulator.charAt(0)==equalTo) {
                        // here set to receive next value of parameter
                        waitingForValue=false;
                        isValue=true;
                        value="";
                    }                   
                } 
            }
            else {                
     
                if (isValue) {
                    value=tokenAccumulator;
                    isValue=false;
                }
                else {
                    if (name==null) {
                        name=tokenAccumulator;
                        if (isName) {
                            waitingForValue=false;
                        }
                        else {
                            waitingForValue=true;
                        }
                    }
                    else {

                        h.put(name.toUpperCase(),"");  
                        isName=false;
                        value=null;
                        name=tokenAccumulator;
                        waitingForValue=true;
                        isValueInited=false;

                    }
                }
                
                if (!waitingForValue) {
                    if (name != null && isValue==false){
                      	putDataIntoTable(h, name, value, isName);
                        isName=false;
                        name=null;
                        name = null;
                        value=null;
                        isValueInited=false;
                    }                
                    waitingForValue=false;
                }           
            }
        }
        return h;
    }
	public void putDataIntoTable(Hashtable h,String name,String value,boolean isName) {
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
