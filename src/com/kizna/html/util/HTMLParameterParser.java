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


    private StringTokenizer token;
        
        
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
        String element,name,value,nextPart=null;     
        
        name=null;
        value=null;
        element=null;
        boolean waitingForEqual=false;
        
//System.out.println("k0:|"+tag.getText()+"|");
        token = new StringTokenizer(tag.getText(),delim,true);
        while (true) {
            nextPart=getNextPart();
            
            if (element==null && nextPart != null && !nextPart.equals("=")){
                element = nextPart;
                putDataIntoTable(h,element,null,true);
            }
            else {
                if (nextPart != null) {
                    if (name == null) {
                        name = nextPart;
                        waitingForEqual=true;
                    }
                    else {
                        if (waitingForEqual){
                            if (nextPart.equals("=")) {                                                    
                                waitingForEqual=false;                                            
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
                        putDataIntoTable(h,name,"",false);                                                   
                        name=null;
                        value=null;
                    }
                    break;
                }                
            }
        }
//System.out.println("h:"+h.toString());        
        return h;
    }
    
    private String getNextPart(){
        String tokenAccumulator=null;
        boolean isDoubleQuote=false;
        boolean isSingleQuote=false;        
        boolean isDataReady=false;
        String currentToken;
        while (isDataReady == false && token.hasMoreTokens()) {
            currentToken = token.nextToken();
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
            
//System.out.println("k4|"+tokenAccumulator+"|"+currentToken+"|"+isDataReady);            
            if (tokenAccumulator.equals(currentToken)) {            
//System.out.println("k4a|"+tokenAccumulator+"|"+isDataReady);            
                
                
                if (delim.indexOf(tokenAccumulator)>=0) {
                    if (tokenAccumulator.equals("=")){
//System.out.println("k4b|"+tokenAccumulator+"|"+isDataReady);            
                        
                        isDataReady=true;
                    }
                }
                else {
                    
//System.out.println("k4c|"+tokenAccumulator+"|"+currentToken+"|"+isDataReady);            
                    
                    isDataReady=true;
                }                
            }      
            else isDataReady=true;

        }
System.out.println("k5|"+tokenAccumulator+"|"+isDataReady);
        return tokenAccumulator;                
    }
    
    
    private void putDataIntoTable(Hashtable h,String name,String value,boolean isName) {
        if (isName && value == null) value=HTMLTag.TAGNAME;
        else if (value==null) value = ""; // Hashtable does not accept nulls
 //System.out.println("k1|"+name+"|"+value+"|");
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
