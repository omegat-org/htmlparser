// HTMLParser Library v1_3_20030413 - A java-based parser for HTML
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

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeReader;
import org.htmlparser.parserHelper.CompositeTagScannerHelper;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;

/**
 * To create your own scanner that can hold children, create a subclass of this class.
 * The composite tag scanner can be configured with:<br>
 * <ul>
 * <li>Tags which will trigger a match</li>
 * <li>Tags which when encountered before a legal end tag, should force a correction</li>
 * <li>Preventing more tags of its own type to appear as children 
 * </ul> 
 * Here are examples of each:<BR>
 * <B>Tags which will trigger a match</B>
 * If we wish to recognize &lt;mytag&gt;,
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "MYTAG" };
 *	 MyScanner() {
 *		super(MATCH_IDS);
 *	 }
 *	 ...
 * }
 * </pre>
 * <B>Tags which force correction</B>
 * If we wish to insert end tags if we get a </BODY> or </HTML> without recieving
 * &lt;/mytag&gt;
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "MYTAG" };
 *   private static final String [] ENDERS = { "BODY", "HTML" };
 *	 MyScanner() {
 *		super(MATCH_IDS, ENDERS);
 *	 }
 *	 ...
 * }
 * </pre>
 * <B>Preventing children of same type</B>
 * This is useful when you know that a certain tag can never hold children of its own type.
 * e.g. &lt;FORM&gt; can never have more form tags within it. If it does, it is an error and should 
 * be corrected. The default behavior is to allow nesting.
 * <pre>
 * MyScanner extends CompositeTagScanner {
 *   private static final String [] MATCH_IDS = { "FORM" };
 *   private static final String [] ENDERS = { "BODY", "HTML" };
 *	 MyScanner() {
 *		super(MATCH_IDS, ENDERS,false);
 *	 }
 *	 ...
 * }
 * </pre>
 * Inside the scanner, use createTag() to specify what tag needs to be created.
 */
public abstract class CompositeTagScanner extends TagScanner {
	protected String [] nameOfTagToMatch;
	private boolean allowSelfChildren;
	private Set enderSet;
		
	public CompositeTagScanner(String [] nameOfTagToMatch) {
		this(nameOfTagToMatch,new String[] {});
	}

	public CompositeTagScanner(String [] nameOfTagToMatch, String [] enders) {
		this("",nameOfTagToMatch,enders);
	}

	public CompositeTagScanner(String [] nameOfTagToMatch, String [] enders, boolean allowSelfChildren) {
		this("",nameOfTagToMatch,enders,allowSelfChildren);
	}

	public CompositeTagScanner(String filter, String [] nameOfTagToMatch) {
		this(filter,nameOfTagToMatch,new String [] {},true);
	}

	public CompositeTagScanner(String filter, String [] nameOfTagToMatch, String [] enders) {
		this(filter,nameOfTagToMatch,enders,true);
	}

	public CompositeTagScanner(
		String filter, 
		String [] nameOfTagToMatch, 
		String [] enders, 
		boolean allowSelfChildren) {
		super(filter);
		this.nameOfTagToMatch = nameOfTagToMatch;
		this.allowSelfChildren = allowSelfChildren;
		this.enderSet = new HashSet();
		for (int i=0;i<enders.length;i++)
			enderSet.add(enders[i]);
			
	}
	
	public Tag scan(Tag tag, String url, NodeReader reader,String currLine) throws ParserException {
		CompositeTagScannerHelper helper = 
			new CompositeTagScannerHelper(this,tag,url,reader,currLine);
		return helper.scan();
	}

	/**
	 * Override this method if you wish to create any data structures or do anything
	 * before the start of the scan. This is just after a tag has triggered the scanner
	 * but before the scanner begins its processing. 
	 */
	public void beforeScanningStarts() {
	}
	
	/**
	 * This method is called everytime a child to the composite is found. It is useful when we 
	 * need to store special children seperately. Though, all children are collected anyway into a node list.
	 */
	public void childNodeEncountered(Node node) {
	}

	/**
 	 * You must override this method to create the tag of your choice upon successful parsing. Data required
 	 * for construction of your tag can be found within tagData and compositeTagData
	 */
	public abstract Tag createTag(TagData tagData, CompositeTagData compositeTagData) throws ParserException;

	
	public final boolean isTagToBeEndedFor(String tagName) {
		return enderSet.contains(tagName);
	}

	public final boolean isAllowSelfChildren() {
		return allowSelfChildren;
	}

}
