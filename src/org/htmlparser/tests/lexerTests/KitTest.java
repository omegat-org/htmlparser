/*
 * KitTest.java
 *
 * Created on August 16, 2003, 2:16 PM
 */

package org.htmlparser.tests.lexerTests;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.lexer.nodes.Attribute;
import org.htmlparser.lexer.nodes.TagNode;
import org.htmlparser.util.ParserException;

/**
 *
 * @author  derrick
 */
public class KitTest extends ParserCallback
{
    Vector mNodes;
    int mIndex;
    
    /** Creates a new instance of KitTest */
    public KitTest (Vector nodes)
    {
        mNodes = nodes;
        mIndex = 0;
    }

    public void handleText (char[] data, int pos)
    {
        StringBuffer sb;
        String theirs;
        Node node;
        int match;
        String ours;

        sb = new StringBuffer (data.length);
        for (int i = 0; i < data.length; i++)
        {
            if (160 == (int)data[i])
                sb.append ("&nbsp;");
            else
                sb.append (data[i]);
        }
        theirs = sb.toString ();
        match = -1;
        for (int i = mIndex; i < Math.min (mIndex + 25, mNodes.size ()); i++)
        {
            node = (Node)mNodes.elementAt (i);
            ours = node.getText ();
            if (theirs.equalsIgnoreCase (ours))
            {
                match = i;
                break;
            }
        }
        if (-1 == match)
        {
            node = (Node)mNodes.elementAt (mIndex);
            ours = node.getText ();
            System.out.println ("theirs: " + theirs);
            System.out.println ("  ours: " + ours);
            mIndex++;
        }
        else
        {
//            System.out.println (" match: " + theirs);
            mIndex = match + 1;
        }
    }
    
    public void handleComment (char[] data, int pos)
    {
        StringBuffer sb;
        String theirs;
        Node node;
        int match;
        String ours;

        sb = new StringBuffer (data.length);
        sb.append (data);
        theirs = sb.toString ();
        match = -1;
        for (int i = mIndex; i < Math.min (mIndex + 25, mNodes.size ()); i++)
        {
            node = (Node)mNodes.elementAt (i);
            ours = node.getText ();
            if (theirs.equalsIgnoreCase (ours))
            {
                match = i;
                break;
            }
        }
        if (-1 == match)
        {
            node = (Node)mNodes.elementAt (mIndex);
            ours = node.getText ();
            System.out.println ("theirs: " + theirs);
            System.out.println ("  ours: " + ours);
            mIndex++;
        }
        else
        {
//            System.out.println (" match: " + theirs);
            mIndex = match + 1;
        }
    }
    
    public void handleStartTag (HTML.Tag t, MutableAttributeSet a, int pos)
    {
        StringBuffer sb;
        String theirs;
        Node node;
        int match;
        String ours;

        theirs = t.toString ();
        match = -1;
        for (int i = mIndex; i < Math.min (mIndex + 25, mNodes.size ()); i++)
        {
            node = (Node)mNodes.elementAt (i);
            if (node instanceof TagNode)
            {
                ours = ((Attribute)(((TagNode)node).getAttributesEx ().elementAt (0))).getName ();
                if (theirs.equalsIgnoreCase (ours))
                {
                    match = i;
                    break;
                }
            }
        }
        if (-1 == match)
        {
            node = (Node)mNodes.elementAt (mIndex);
            ours = node.getText ();
            System.out.println ("theirs: " + theirs);
            System.out.println ("  ours: " + ours);
            mIndex++;
        }
        else
        {
//            System.out.println (" match: " + theirs);
            mIndex = match + 1;
        }
    }
    
    public void handleEndTag (HTML.Tag t, int pos)
    {
        StringBuffer sb;
        String theirs;
        Node node;
        int match;
        String ours;

        theirs = t.toString ();
        match = -1;
        for (int i = mIndex; i < Math.min (mIndex + 25, mNodes.size ()); i++)
        {
            node = (Node)mNodes.elementAt (i);
            if (node instanceof TagNode)
            {
                ours = ((Attribute)(((TagNode)node).getAttributesEx ().elementAt (0))).getName ().substring (1);
                if (theirs.equalsIgnoreCase (ours))
                {
                    match = i;
                    break;
                }
            }
        }
        if (-1 == match)
        {
            node = (Node)mNodes.elementAt (mIndex);
            ours = node.getText ();
            System.out.println ("theirs: " + theirs);
            System.out.println ("  ours: " + ours);
            mIndex++;
        }
        else
        {
//            System.out.println (" match: " + theirs);
            mIndex = match + 1;
        }
    }
    
    public void handleSimpleTag (HTML.Tag t, MutableAttributeSet a, int pos)
    {
        StringBuffer sb;
        String theirs;
        Node node;
        int match;
        String ours;

        theirs = t.toString ();
        match = -1;
        for (int i = mIndex; i < Math.min (mIndex + 25, mNodes.size ()); i++)
        {
            node = (Node)mNodes.elementAt (i);
            if (node instanceof TagNode)
            {
                ours = ((Attribute)(((TagNode)node).getAttributesEx ().elementAt (0))).getName ();
                if (theirs.equalsIgnoreCase (ours))
                {
                    match = i;
                    break;
                }
                else if (theirs.equalsIgnoreCase (ours.substring (1)))
                {
                    match = i;
                    break;
                }
            }
        }
        if (-1 == match)
        {
            node = (Node)mNodes.elementAt (mIndex);
            ours = node.getText ();
            System.out.println ("theirs: " + theirs);
            System.out.println ("  ours: " + ours);
            mIndex++;
        }
        else
        {
//            System.out.println (" match: " + theirs);
            mIndex = match + 1;
        }
    }

    
    public void handleError (String errorMsg, int pos)
    {
//        System.out.println ("******* error @" + pos + " ******** " + errorMsg);
    }
    
    public void flush () throws BadLocationException
    {
    }
    
    /**
     * This is invoked after the stream has been parsed, but before
     * <code>flush</code>. <code>eol</code> will be one of \n, \r
     * or \r\n, which ever is encountered the most in parsing the
     * stream.
     *
     * @since 1.3
     */
    public void handleEndOfLineString (String eol)
    {
    }

//    /**
//     * Get the document data from the URL.
//     * @param rd The reader to read bytes from.
//     * @return The parsed HTML document.
//     */
//    protected static Element[] getData (Reader rd) throws IOException
//    {
//        EditorKit kit;
//        Document doc;
//        Element[] ret;
//                                                                                                                                
//        ret = null;
//                                                                                                                                
//        // need this because HTMLEditorKit is not thread safe apparently
//        synchronized (Boolean.TRUE)
//        {
//            kit = new HTMLEditorKit ();
//            doc = kit.createDefaultDocument ();
//            // the Document class does not yet handle charset's properly
//            doc.putProperty ("IgnoreCharsetDirective", Boolean.TRUE);
//                                                                                                                                
//            try
//            {
//                // parse the HTML
//                kit.read (rd, doc, 0);
//            }
//            catch (BadLocationException ble)
//            {
//                throw new IOException ("parse error " + ble.getMessage ());
//            }
//                                                                                                                                
//            ret = doc.getRootElements ();
//        }
//                                                                                                                                
//        return (ret);
//    }

//    public static void scanElements (Element element) throws BadLocationException
//    {
//        int start;
//        int end;
//        String string;
//        ElementIterator it;
//        Element child;
//        
//        if (element.isLeaf ())
//        {
//            start = element.getStartOffset ();
//            end = element.getEndOffset ();
//            string = element.getDocument ().getText (start, end - start);
//            System.out.println (string);
//        }
//        else
//            // iterate through the elements of the element
//            for (int i = 0; i < element.getElementCount (); i++)
//            {
//                child = element.getElement (i);
//                scanElements (child);
//            }
//    }

    class MyKit extends HTMLEditorKit
    {
        public MyKit ()
        {
        }

        public HTMLEditorKit.Parser getParser () 
        {
            return (super.getParser ());
        }
    }
    
    public MyKit getKit ()
    {
        return (new MyKit ());
    }

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) throws ParserException, IOException
    {
        Lexer lexer;
        Node node;
        Vector nodes;
        KitTest test;
        MyKit kit;
        Parser parser;
        
        Element[] elements;

        // pass through it once to read the entire page
        URL url = new URL ("http://sourceforge.net/projects/htmlparser");
        lexer = new Lexer (url.openConnection ());
        nodes = new Vector ();
        while (null != (node = lexer.nextNode ()))
            nodes.addElement (node);

        // reset the reader
        lexer.getPage ().getSource ().reset ();
        test = new KitTest (nodes);
        kit = test.getKit ();
        parser = kit.getParser ();
        parser.parse ((Reader)lexer.getPage ().getSource (), (ParserCallback)test, true);
    }
    
}
