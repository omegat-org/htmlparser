// HTMLParser Library v1_2_20021016 - A java-based parser for HTML
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
// R&D Team
// Kizna Corporation
// Hiroo ON Bldg. 2F, 5-19-9 Hiroo,
// Shibuya-ku, Tokyo, 
// 150-0012, 
// JAPAN
// Tel  :  +81-3-54752646
// Fax : +81-3-5449-4870
// Website : www.kizna.com

package com.kizna.html.util;

/**
 * Support for chained exceptions in code that predates Java 1.4.
 * A chained exception can use a Throwable argument to reference
 * a lower level exception. The chained exception provides a
 * stack trace that includes the message and any throwable
 * exception included as an argument in the chain.
 *
 * For example:
 *
 *   ApplicationException: Application problem encountered;
 *   ProcessException: Unable to process document;
 *   java.io.IOException: Unable to open 'filename.ext'
 *     at ChainedExceptionTest.openFile(ChainedExceptionTest.java:19)
 *     at ChainedExceptionTest.processFile(ChainedExceptionTest.java:27)
 *     at ChainedExceptionTest.application(ChainedExceptionTest.java:40)
 *     at ChainedExceptionTest.main(ChainedExceptionTest.java:52)
 *
 * Represents the output from two nested exceptions. The outside
 * exception is a subclass of ChainedException called
 * ApplicationException, which includes a throwable reference.
 * The throwable reference is also a subclass of ChainedException,
 * called ProcessException, which in turn includes a reference to
 * a standard IOException. In each case, the message is increasingly
 * specific about the nature of the problem. The end user may only
 * see the application exception, but debugging is greatly
 * enhanced by having more details in the stack trace.
 *
 * @author Claude Duguay
 **/

import java.io.*;
import java.util.*;

public class ChainedException
  extends Exception
{
  protected Throwable throwable;

  public ChainedException() {}

  public ChainedException(String message)
  {
    super(message);
  }
  
  public ChainedException(Throwable throwable)
  {
    this.throwable = throwable;
  }
  
  public ChainedException(String message, Throwable throwable)
  {
    super(message);
    this.throwable = throwable;
  }
  
  public String[] getMessageChain()
  {
    List list = getMessageList();
    String[] chain = new String[list.size()];
    for (int i = 0; i < list.size(); i++)
    {
      chain[i] = (String)list.get(i);
    }
    return chain;
  }
  
  public List getMessageList()
  {
    ArrayList list = new ArrayList();
    list.add(getMessage());
    if (throwable != null)
    {
      if (throwable instanceof ChainedException)
      {
        ChainedException chain = (ChainedException)throwable;
        list.addAll(chain.getMessageList());
      }
      else
      {
        String message = throwable.getMessage();
        if (message != null && !message.equals(""))
        {
          list.add(message);
        }
      }
    }
    return list;
  }
  
  public Throwable getThrowable()
  {
    return throwable;
  }
  
  public void printStackTrace()
  {
    printStackTrace(System.err);
  }
  
  public void printStackTrace(PrintStream out)
  {
    synchronized (out)
    {
      if (throwable != null)
      {
        out.println(getClass().getName() +
          ": " + getMessage() + ";");
        throwable.printStackTrace(out);
      }
      else
      {
        super.printStackTrace(out);
      }
    }
  }

  public void printStackTrace(PrintWriter out)
  {
    synchronized (out)
    {
      if (throwable != null)
      {
        out.println(getClass().getName() +
          ": " + getMessage() + ";");
        throwable.printStackTrace(out);
      }
      else
      {
        super.printStackTrace(out);
      }
    }
  }
}

