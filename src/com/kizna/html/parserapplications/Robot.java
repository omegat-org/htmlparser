// HTMLParser Library v1_2_20021109 - A java-based parser for HTML
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
// Email :somik@kizna.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Email : somik@industriallogic.com
// Website : http://www.industriallogic.com

package com.kizna.html.parserapplications;
import com.kizna.html.*;
import com.kizna.html.tags.*;
import com.kizna.html.util.DefaultHTMLParserFeedback;
import com.kizna.html.util.HTMLEnumeration;
import com.kizna.html.util.HTMLParserException;

import com.kizna.html.scanners.*;
/**
 * The Robot Crawler application will crawl through urls recursively, based on a depth value.
 */
public class Robot {
  private com.kizna.html.HTMLParser parser;
/**
 * Robot crawler - Provide the starting url 
 */
public Robot(String resourceLocation) {
	try {
	  parser = new HTMLParser(resourceLocation,new DefaultHTMLParserFeedback());
	  parser.registerScanners();
	}
	catch (HTMLParserException e) {
		System.err.println("Error, could not create parser object");
		e.printStackTrace();
	}
}
/**
 * Crawl using a given crawl depth.
 * @param crawlDepth Depth of crawling
 */
public void crawl(int crawlDepth) throws HTMLParserException
{
	try {
	  crawl(parser,crawlDepth);
	}
	catch (HTMLParserException e) {
		throw new HTMLParserException("HTMLParserException at crawl("+crawlDepth+")",e);
	}
}
/**
 * Crawl using a given parser object, and a given crawl depth.
 * @param parser HTMLParser object
 * @param crawlDepth Depth of crawling
 */
public void crawl(HTMLParser parser,int crawlDepth) throws HTMLParserException {
  System.out.println(" crawlDepth = "+crawlDepth);
  for (HTMLEnumeration e = parser.elements();e.hasMoreNodes();)
  {
    HTMLNode node = e.nextHTMLNode();
    if (node instanceof HTMLLinkTag)
    {
      HTMLLinkTag linkTag = (HTMLLinkTag)node;
      {
        if (!linkTag.isMailLink())
        {
          if (linkTag.getLink().toUpperCase().indexOf("HTM")!=-1 || 
            linkTag.getLink().toUpperCase().indexOf("COM")!=-1 ||
            linkTag.getLink().toUpperCase().indexOf("ORG")!=-1)
          {
            if (crawlDepth>0)
            {
              HTMLParser newParser = new HTMLParser(linkTag.getLink(),new DefaultHTMLParserFeedback());
              newParser.registerScanners();
              System.out.print("Crawling to "+linkTag.getLink());
              crawl(newParser,crawlDepth-1);
            }
            else System.out.println(linkTag.getLink());
          }
        }
      }
    }
  }
}
/**
 * Insert the method's description here.
 * Creation date: (8/3/2001 2:10:08 AM)
 * @param args java.lang.String[]
 */
public static void main(String[] args) 
{
  System.out.println("Robot Crawler v"+HTMLParser.VERSION_STRING);
  if (args.length<2 || args[0].equals("-help"))
  {
    System.out.println();
    System.out.println("Syntax : java -classpath htmlparser.jar com.kizna.parserapplications.Robot <resourceLocn/website> <depth>");
    System.out.println();
    System.out.println("   <resourceLocn> the name of the file to be parsed (with complete path ");
    System.out.println("                  if not in current directory)");
    System.out.println("   <depth> No of links to be followed from each link");
    System.out.println("   -help This screen");
    System.out.println();
    System.out.println("HTML Parser home page : http://htmlparser.sourceforge.net");
    System.out.println();
    System.out.println("Example : java -classpath htmlparser.jar com.kizna.parserapplications.Robot http://www.google.com 3");
    System.out.println();
    System.out.println("If you have any doubts, please join the HTMLParser mailing list (user/developer) from the HTML Parser home page instead of mailing any of the contributors directly. You will be surprised with the quality of open source support. ");
    System.exit(-1);
  }	
  String resourceLocation="";
  int crawlDepth = 1;
  if (args.length!=0) resourceLocation = args[0];
  if (args.length==2) crawlDepth=Integer.valueOf(args[1]).intValue();
  
    
  Robot robot = new Robot(resourceLocation);	
  System.out.println("Crawling Site "+resourceLocation);
  try {
	  robot.crawl(crawlDepth);
  }
  catch (HTMLParserException e) {
  	e.printStackTrace();
  }
}
}
