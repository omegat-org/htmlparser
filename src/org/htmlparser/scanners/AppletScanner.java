// HTMLParser Library v1_3_20030223 - A java-based parser for HTML
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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.tags.AppletTag;
import org.htmlparser.tags.EndTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;
/**
 * Used to scan for applet tags.
 */
public class AppletScanner extends TagScanner {
	private java.lang.String className;
	private java.lang.String archive;
	private java.lang.String codebase;
	/**
	 * HTMLAppletScanner constructor comment.
	 */
	public AppletScanner() {
		super();
	}
	/**
	 * HTMLAppletScanner constructor comment.
	 * @param filter java.lang.String
	 */
	public AppletScanner(String filter) {
		super(filter);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:31:42 AM)
	 * @param text java.lang.String
	 */
	public void extractFields(Tag tag) 
	{
	    className = tag.getAttribute("CODE");
	    archive = tag.getAttribute("ARCHIVE");
	    codebase = tag.getAttribute("CODEBASE");
	}
	
	public String getArchive() {
		return archive;
	}
	
	private String getClassName() {
		return className;
	}
	
	private String getCodebase() {
		return codebase;
	}
	
	/** 
	 * Scan the tag and extract the information related to this type. The url of the 
	 * initiating scan has to be provided in case relative links are found. The initial 
	 * url is then prepended to it to give an absolute link.
	 * The HTMLReader is provided in order to do a lookahead operation. We assume that
	 * the identification has already been performed using the evaluate() method.
	 * @param tag HTML Tag to be scanned for identification
	 * @param url The initiating url of the scan (Where the html page lies)
	 * @param reader The reader object responsible for reading the html page
	 */
	public Tag scan(Tag tag, String url, org.htmlparser.NodeReader reader, String currLine) throws ParserException
	{
		try {
			String tagContents = tag.getText();
			// From the tagContents, get the class name, archive, codebase
			extractFields(tag);
		
			String line;
			Tag startTag = tag;
			Tag endTag=null;
			Node node = null;
			boolean endScriptFound=false;
			Vector childrenVector=new Vector();
			Hashtable table = new Hashtable();
			do {
				node = reader.readElement();
				if (node instanceof EndTag) {
					endTag = (EndTag)node;
					if (endTag.getText().toUpperCase().equals("APPLET")) 
					{
						endScriptFound = true;
					}
				}
				else if (node instanceof Tag) {
					Tag htag = (Tag)node;
					String paramName = htag.getAttribute("NAME");
					if (paramName!=null && paramName.length()!=0)
					{
						String paramValue = htag.getAttribute("VALUE");
						table.put(paramName,paramValue);
					}
				}
				if (!endScriptFound) childrenVector.addElement(node);
			}
			while (!endScriptFound && node!=null);
			if (node==null && endScriptFound==false) {
				StringBuffer msg = new StringBuffer();
				for (Enumeration e = table.elements();e.hasMoreElements();) {
					String key = (String)e.nextElement();
					msg.append(key+"="+table.get(key)+"\n");
				}
				throw new ParserException("HTMLAppletScanner.scan() : Went into a potential infinite loop - tags must be malformed.\n"+
				"Table contents : "+msg.toString());
			}	
			AppletTag appTag = new AppletTag(
				new TagData(
					node.elementBegin(),
					node.elementEnd(),
					tag.getText(),
					currLine
				),
				new CompositeTagData(
					startTag,
					endTag,
					childrenVector
				),
				className,
				archive,
				codebase,
				table);
			return appTag;
		}
		catch (Exception e) {
			throw new ParserException("HTMLAppletScannet.scan(): Error in scanning applet tag, current line = "+currLine,e);
		}
		
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:34:38 AM)
	 * @param newArchive java.lang.String
	 */
	public void setArchive(java.lang.String newArchive) {
		archive = newArchive;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:33:08 AM)
	 * @param newClassName java.lang.String
	 */
	public void setClassName(java.lang.String newClassName) {
		className = newClassName;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/18/2001 2:34:53 AM)
	 * @param newCodebase java.lang.String
	 */
	public void setCodebase(java.lang.String newCodebase) {
		codebase = newCodebase;
	}
	
	/**
	 * @see org.htmlparser.scanners.TagScanner#getID()
	 */
	public String [] getID() {
		String [] ids = new String[1];
		ids[0] = "APPLET";
		return ids;
	}

}
