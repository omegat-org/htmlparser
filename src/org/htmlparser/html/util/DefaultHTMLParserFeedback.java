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
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.html.util;

/**
 * Default implementation of the HTMLParserFeedback interface.
 * This implementation prints output to the console but users
 * can implement their own classes to support alternate behavior.
 *
 * @author Claude Duguay
 * @see HTMLParserFeedback, FeedbackManager
**/

public class DefaultHTMLParserFeedback
  implements HTMLParserFeedback
{
  public void info(String message)
  {
    System.out.println("INFO: " + message);
  }

  public void warning(String message)
  {
    System.out.println("WARNING: " + message);
  }

  public void error(String message, HTMLParserException e)
  {
    System.out.println("ERROR: " + message);
    e.printStackTrace();
  }
}

