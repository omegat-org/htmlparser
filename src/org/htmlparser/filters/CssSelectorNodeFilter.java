// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Rogers George
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.filters;

import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

/**
 * A NodeFilter that accepts nodes based on whether they match a CSS2 selector.
 * Refer to <a href="http://www.w3.org/TR/REC-CSS2/selector.html">
 * http://www.w3.org/TR/REC-CSS2/selector.html</a> for syntax.
 * <p>
 * Todo: more thorough testing, any relevant pseudo-classes, css3 features
 */
public class CssSelectorNodeFilter implements NodeFilter
{
    private static Pattern tokens =
        Pattern.compile("("
            + "/\\*.*?\\*/"             // comments
            + ") | ("
            + "   \".*?[^\"]\""   // double quoted string
            + " | \'.*?[^\']\'"   // single quoted string
            + " | \"\" | \'\' "     // empty quoted string
            + ") | ("
            + " [\\~\\*\\$\\^]? = " // attrib-val relations
            + ") | ("
            + " [a-zA-Z_\\*](?:[a-zA-Z0-9_-]|\\\\.)* " // bare name
            + ") | \\s*("
            + " [+>~\\s] "        // combinators
            + ")\\s* | ("
            + " [\\.\\[\\]\\#\\:)(] "       // class/id/attr/param delims
            + ") | ("
            + " [\\,] "                     // comma
            + ") | ( . )"                   // everything else (bogus)
            ,
            Pattern.CASE_INSENSITIVE
            |Pattern.DOTALL
            |Pattern.COMMENTS);

    private static final int COMMENT = 1, QUOTEDSTRING = 2, RELATION = 3,
        NAME = 4, COMBINATOR = 5, DELIM = 6, COMMA = 7;

    private NodeFilter therule;

    public CssSelectorNodeFilter(String selector)
    {
        m = tokens.matcher(selector);
        if (nextToken())
            therule = parse();
    }

    public boolean accept(Node n)
    {
        return therule.accept(n);
    }

    private Matcher m = null;
    private int tokentype = 0;
    private String token = null;

    private boolean nextToken()
    {
        if (m != null && m.find())
            for (int i = 1; i < m.groupCount(); i++)
                if (m.group(i) != null)
                {
                    tokentype = i;
                    token = m.group(i);
                    return true;
                }
        tokentype = 0;
        token = null;
        return false;
    }

    private NodeFilter parse()
    {
        NodeFilter n = null;
        do
        {
            switch (tokentype)
            {
                case COMMENT:
                case NAME:
                case DELIM:
                    if (n == null)
                        n = parseSimple();
                    else
                        n = new AndFilter(n, parseSimple());
                    break;
                case COMBINATOR:
                    switch (token.charAt(0))
                    {
                        case '+':
                            n = new AdjacentFilter(n);
                            break;
                        case '>':
                            n = new HasParentFilter(n);
                            break;
                        default: // whitespace
                            n = new HasAncestorFilter(n);
                    }
                    nextToken();
                    break;
                case COMMA:
                    n = new OrFilter(n, parse());
                    nextToken();
                    break;
            }
        }
        while (token != null);
        return n;
    }

    private NodeFilter parseSimple()
    {
        boolean done = false;
        NodeFilter n = null;

        if (token != null)
            do
            {
                switch (tokentype)
                {
                    case COMMENT:
                        nextToken();
                        break;
                    case NAME:
                        if ("*".equals(token))
                            n = new YesFilter();
                        else if (n == null)
                            n = new TagNameFilter(unescape(token));
                        else
                            n = new AndFilter(n, new TagNameFilter(unescape(token)));
                        nextToken();
                        break;
                    case DELIM:
                        switch (token.charAt(0))
                        {
                            case '.':
                                nextToken();
                                if (tokentype != NAME)
                                    throw new IllegalArgumentException("Syntax error at " + token);
                                if (n == null)
                                    n = new HasAttributeFilter("class", unescape(token));
                                else
                                    n
                                    = new AndFilter(n, new HasAttributeFilter("class", unescape(token)));
                                break;
                            case '#':
                                nextToken();
                                if (tokentype != NAME)
                                    throw new IllegalArgumentException("Syntax error at " + token);
                                if (n == null)
                                    n = new HasAttributeFilter("id", unescape(token));
                                else
                                    n = new AndFilter(n, new HasAttributeFilter("id", unescape(token)));
                                break;
                            case ':':
                                nextToken();
                                if (n == null)
                                    n = parsePseudoClass();
                                else
                                    n = new AndFilter(n, parsePseudoClass());
                                break;
                            case '[':
                                nextToken();
                                if (n == null)
                                    n = parseAttributeExp();
                                else
                                    n = new AndFilter(n, parseAttributeExp());
                                break;
                        }
                        nextToken();
                        break;
                    default:
                        done = true;
                }
            }
            while (!done && token != null);
        return n;
    }

    private NodeFilter parsePseudoClass()
    {
        throw new IllegalArgumentException("pseudoclasses not implemented yet");
    }

    private NodeFilter parseAttributeExp()
    {
        NodeFilter n = null;
        if (tokentype == NAME)
        {
            String attrib = token;
            nextToken();
            if ("]".equals(token))
                n = new HasAttributeFilter(unescape(attrib));
            else if (tokentype == RELATION)
            {
                String val = null, rel = token;
                nextToken();
                if (tokentype == QUOTEDSTRING)
                    val = unescape(token.substring(1, token.length() - 1));
                else if (tokentype == NAME)
                    val = unescape(token);
                if ("~=".equals(rel) && val != null)
                    n = new AttribMatchFilter(unescape(attrib),
                                                                        "\\b"
                                                                        + val.replaceAll("([^a-zA-Z0-9])", "\\\\$1")
                                                                        + "\\b");
                else if ("=".equals(rel) && val != null)
                    n = new HasAttributeFilter(attrib, val);
            }
        }
        if (n == null)
            throw new IllegalArgumentException("Syntax error at " + token + tokentype);

        nextToken();
        return n;
    }

    public static String unescape(String escaped)
    {
        StringBuffer result = new StringBuffer(escaped.length());
        Matcher m = Pattern.compile("\\\\(?:([a-fA-F0-9]{2,6})|(.))").matcher(
                        escaped);
        while (m.find())
        {
            if (m.group(1) != null)
                m.appendReplacement(result,
                                                        String.valueOf((char)Integer.parseInt(m.group(1), 16)));
            else if (m.group(2) != null)
                m.appendReplacement(result, m.group(2));
        }
        m.appendTail(result);

        return result.toString();
    }

    private static class HasAncestorFilter implements NodeFilter
    {
        private NodeFilter atest;

        public HasAncestorFilter(NodeFilter n)
        {
            atest = n;
        }

        public boolean accept(Node n)
        {
            while (n != null)
            {
                n = n.getParent();
                if (atest.accept(n))
                    return true;
            }
            return false;
        }
    }

    private static class AdjacentFilter implements NodeFilter
    {
        private NodeFilter sibtest;

        public AdjacentFilter(NodeFilter n)
        {
            sibtest = n;
        }

        public boolean accept(Node n)
        {
            if (n.getParent() != null)
            {
                NodeList l = n.getParent().getChildren();
                for (int i = 0; i < l.size(); i++)
                    if (l.elementAt(i) == n && i > 0)
                        return (sibtest.accept(l.elementAt(i - 1)));
            }
            return false;
        }
    }

    private static class YesFilter implements NodeFilter
    {
        public boolean accept(Node n)
        {return true;}
    }

    private static class AttribMatchFilter implements NodeFilter
    {
        private Pattern rel;
        private String attrib;

        public AttribMatchFilter(String attrib, String regex)
        {
            rel = Pattern.compile(regex);
            this.attrib = attrib;
        }

        public boolean accept(Node node)
        {
            if (node instanceof Tag && ((Tag)node).getAttribute(attrib) != null)
                if (rel != null
                        && !rel.matcher(((Tag)node).getAttribute(attrib)).find())
                    return false;
                else
                    return true;
            else
                return false;
        }
    }
}
