// HTMLParser Library v1_3_20030504 - A java-based parser for HTML
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
import java.util.Enumeration;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;


/**
 * MailRipper will rip out all the mail addresses from a given web page
 * Pass a web site (or html file on your local disk) as an argument.
 */
public class MailRipper {
  private org.htmlparser.Parser parser;
	/**
	 * MailRipper c'tor takes the url to be ripped
	 * @param resourceLocation url to be ripped
	 */
	public MailRipper(String resourceLocation) {
		try {
		  parser = new Parser(resourceLocation,new DefaultParserFeedback());
		  parser.registerScanners();
		}
		catch (ParserException e) {
			System.err.println("Could not create parser object");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	  System.out.println("Mail Ripper v"+Parser.VERSION_STRING);
	  if (args.length<1 || args[0].equals("-help"))
	  {
	    System.out.println();
	    System.out.println("Syntax : java -classpath htmlparser.jar org.htmlparser.parserapplications.MailRipper <resourceLocn/website>");
	    System.out.println();
	    System.out.println("   <resourceLocn> the name of the file to be parsed (with complete path ");
	    System.out.println("                  if not in current directory)");
	    System.out.println("   -help This screen");
	    System.out.println();
	    System.out.println("HTML Parser home page : http://htmlparser.sourceforge.net");
	    System.out.println();
	    System.out.println("Example : java -classpath htmlparser.jar com.kizna.parserapplications.MailRipper http://htmlparser.sourceforge.net");
	    System.out.println();
	    System.out.println("If you have any doubts, please join the HTMLParser mailing list (user/developer) from the HTML Parser home page instead of mailing any of the contributors directly. You will be surprised with the quality of open source support. ");
	    System.exit(-1);
	  }		
	  String resourceLocation = "http://htmlparser.sourceforge.net";
	  if (args.length!=0) resourceLocation = args[0];
	
	  MailRipper ripper = new MailRipper(resourceLocation);	
	  System.out.println("Ripping Site "+resourceLocation);
	  try {
		  for (Enumeration e=ripper.rip();e.hasMoreElements();) {
		    LinkTag tag = (LinkTag)e.nextElement();
		    System.out.println("Ripped mail address : "+tag.getLink());
		  }
	  }
	  catch (ParserException e) {
	  	e.printStackTrace();
	  }
	}
	/**
	 * Rip all mail addresses from the given url, and return an enumeration of such mail addresses.
	 * @return Enumeration of mail addresses (a vector of LinkTag)
	 */
	public Enumeration rip() throws ParserException {
	  Node node;
	  Vector mailAddresses = new Vector();
	  for (NodeIterator e = parser.elements();e.hasMoreNodes();)
	  {
	    node = e.nextNode();
	    if (node instanceof LinkTag)
	    {
	      LinkTag linkTag = (LinkTag)node;
	      if (linkTag.isMailLink()) mailAddresses.addElement(linkTag);
	    }
	  }
	  return mailAddresses.elements();	
	}
}
