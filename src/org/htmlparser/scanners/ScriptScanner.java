// HTMLParser Library v1_4_20030601 - A java-based parser for HTML
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

package org.htmlparser.scanners;
/////////////////////////
// HTML Parser Imports //
/////////////////////////
import org.htmlparser.*;
import org.htmlparser.parserHelper.*;
import org.htmlparser.tags.*;
import org.htmlparser.tags.data.*;
import org.htmlparser.util.*;
/**
 * The HTMLScriptScanner identifies javascript code
 */

public class ScriptScanner extends CompositeTagScanner {
	private static final String SCRIPT_END_TAG = "</SCRIPT>";
	private static final String MATCH_NAME [] = {"SCRIPT"};
	private static final String ENDERS [] = {"BODY", "HTML"};

	

	public ScriptScanner() {
		super("",MATCH_NAME,ENDERS);
	}

	public ScriptScanner(String filter) {
		super(filter,MATCH_NAME,ENDERS);
	}

	public ScriptScanner(String filter, String[] nameOfTagToMatch) {
		super(filter,nameOfTagToMatch,ENDERS);
	}
	
	public String [] getID() {
		return MATCH_NAME;
	}

	public Tag createTag(
		TagData tagData,
		CompositeTagData compositeTagData) {
		return new ScriptTag(tagData,compositeTagData);
	}

	public Tag scan(Tag tag, String url, NodeReader nodeReader, String currLine)
		throws ParserException {
		try {
			ScriptScannerHelper helper = 
				new ScriptScannerHelper(tag,url,nodeReader,currLine, this);
			return helper.scan();
			
		}
		catch (Exception e) {
			throw new ParserException("Error in ScriptScanner: ",e);
		}
	}


	/**
	 * Gets the end tag that the scanner uses to stop scanning. Subclasses of
	 * <code>ScriptScanner</code> you should override this method.
	 * @return String containing the end tag to search for, i.e. &lt;/SCRIPT&gt;
	 */ 
	public String getEndTag() {
		return SCRIPT_END_TAG;
	}
	


}
