// HTMLParser Library v1_4_20030921 - A java-based parser for HTML
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

package org.htmlparser.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple command like parser/handler.
 * A dashed argument is one preceded by a dash character.
 * In a sequence of arguments:
 * 1) If a dashed argument starts with a command character
 *    the rest of the argument, if any, is assume to be a value.
 * 2) If a dashed argument is followed by a non-dashed
 *    argument value. The value is assumed to be associated
 *    with the preceding dashed argument name.
 * 2) If an argument with a dash prefix is not followed by
 *    a non-dashed value, and does not use a command character,
 *    it is assumed to be a flag.
 * 3) If none of the above is true, the argument is a name.
 *
 * Command characters can be added with the addCommand method.
 * Values can be retrieved with the getValue method.
 * Flag states can be retrieved with the getFlag method.
 * Names can be retieved with the getNameCount and getName methods.
 *
 * @author Claude Duguay
**/

public class CommandLine
{
  public static boolean VERBOSE = false;

  protected List commands = new ArrayList();

  protected List flags = new ArrayList();
  protected List names = new ArrayList();
  protected Map values = new HashMap();

  public CommandLine(String chars, String[] args)
  {
    for (int i = 0; i < chars.length(); i++)
    {
      addCommand(chars.charAt(i));
    }
    parse(args);
  }

  public CommandLine(String[] args)
  {
    parse(args);
  }

  protected void parse(String[] args)
  {
    for (int i = 0; i < args.length; i++)
    {
      String thisArg = args[i];
      String nextArg = null;
      if (i < args.length - 1)
      {
        nextArg = args[i + 1];
      }

      if (thisArg.startsWith("-"))
      {
        if (thisArg.length() > 2)
        {
          Character chr = new Character(thisArg.charAt(1));
          if (commands.contains(chr))
          {
            String key = chr.toString();
            String val = thisArg.substring(2);
            if (VERBOSE)
            {
              System.out.println("Value " + key + "=" + val);
            }
            values.put(key, val);
          }
        }
        if (nextArg != null &&
            !nextArg.startsWith("-"))
        {
          String key = thisArg.substring(1);
          String val = nextArg;
          if (VERBOSE)
          {
            System.out.println("Value " + key + "=" + val);
          }
          values.put(key, val);
          i++;
        }
        else
        {
          String flag = thisArg.substring(1);
          flags.add(flag);
          if (VERBOSE)
          {
            System.out.println("Flag " + flag);
          }
        }
      }
      else
      {
        if (VERBOSE)
        {
          System.out.println("Name " + thisArg);
        }
        names.add(thisArg);
      }
    }
  }

  public void addCommand(char command)
  {
    commands.add(new Character(command));
  }

  public boolean hasValue(String key)
  {
    return values.containsKey(key);
  }

  public String getValue(String key)
  {
    return (String)values.get(key);
  }

  public boolean getFlag(String key)
  {
    return flags.contains(key);
  }

  public int getNameCount()
  {
    return names.size();
  }

  public String getName(int index)
  {
    return (String)names.get(index);
  }

  public static void main(String[] args)
  {
    CommandLine cmd = new CommandLine("f", args);
  }
}
