// HTMLParser Library v1_3_20030330 - A java-based parser for HTML
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

import java.io.Serializable;

/**
 * Default implementation of the HTMLParserFeedback interface.
 * This implementation prints output to the console but users
 * can implement their own classes to support alternate behavior.
 *
 * @author Claude Duguay
 * @see HTMLParserFeedback
 * @see FeedbackManager
**/
public class DefaultParserFeedback
    implements
        ParserFeedback,
        Serializable
{
    /**
     * Constructor argument for a quiet feedback.
     */
    public static final int QUIET = 0;

    /**
     * Constructor argument for a normal feedback.
     */
    public static final int NORMAL = 1;

    /**
     * Constructor argument for a debugging feedback.
     */
    public static final int DEBUG = 2;

    /**
     * Verbosity level.
     * Corresponds to constructor arguments:
     * <pre>
     *   DEBUG = 2;
     *   NORMAL = 1;
     *   QUIET = 0;
     * </pre>
     */
    protected int mode;

    /**
     * Construct a feedback object of the given type.
     * @param mode The type of feedback:
     * <pre>
     *   DEBUG - verbose debugging with stack traces
     *   NORMAL - normal messages
     *   QUIET - no messages
     * </pre>
     */
    public DefaultParserFeedback (int mode)
    {
		if (mode<QUIET||mode>DEBUG) 
            throw new IllegalArgumentException ("illegal mode (" + mode + "), must be one of: QUIET, NORMAL, DEBUG");
        this.mode = mode;
    }

    /**
     * Construct a NORMAL feedback object.
     */
    public DefaultParserFeedback ()
    {
        this (NORMAL);
    }

    /**
     * Print an info message.
     * @param message The message to print.
     */
    public void info (String message)
    {
        if (mode!=QUIET)
            System.out.println ("INFO: " + message);
    }
    
    /**
     * Print an warning message.
     * @param message The message to print.
     */
    public void warning (String message)
    {
        if (mode!=QUIET)
            System.out.println ("WARNING: " + message);
    }
    
    /**
     * Print an error message.
     * @param message The message to print.
     * @param exception The exception for stack tracing.
     */
    public void error (String message, ParserException exception)
    {
        if (mode!=QUIET)
        {
            System.out.println ("ERROR: " + message);
            if (mode == DEBUG && (exception!=null))
                exception.printStackTrace ();
        }
    }
}

