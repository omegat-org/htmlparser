/*
 * Zip.java
 * POST zip code to look up cities.
 *
 * Created on April 20, 2003, 11:09 PM
 */

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.beans.StringBean;

/**
 * POST zip code to look up cities.
 * @author Derrick Oswald
 */
public class Zip
{
    String mText; // text extracted from the response to the POST request

    /**
     * Creates a new instance of Zip
     */
    public Zip (String code)
    {
        URL url;
        HttpURLConnection connection;
        StringBuffer buffer;
        PrintWriter out;
        StringBean bean;

        try
        {
            // from the 'action' (relative to the refering page)
            url = new URL ("http://www.usps.com/zip4/zip_response.jsp");
            connection = (HttpURLConnection)url.openConnection ();
            connection.setRequestMethod ("POST");

            connection.setDoOutput (true);
            connection.setDoInput (true);
            connection.setUseCaches (false);
            
            // more or less of these may be required
            // see Request Header Definitions: http://www.ietf.org/rfc/rfc2616.txt
            connection.setRequestProperty ("Accept-Charset", "*");
            connection.setRequestProperty ("Referer", "http://www.usps.com/zip4/citytown.htm");
            connection.setRequestProperty ("User-Agent", "Zip.java/1.0");

            buffer = new StringBuffer (1024);
            // 'input' fields separated by ampersands (&)
            buffer.append ("zipcode=");
            buffer.append (code);
            // buffer.append ("&");
            // etc.
            
            out = new PrintWriter (connection.getOutputStream ());
            out.print (buffer);
            out.close ();

            bean = new StringBean ();
            bean.setConnection (connection);
            mText = bean.getStrings ();
        }
        catch (Exception e)
        {
            mText = e.getMessage ();
        }
        
    }
    
    public String getText ()
    {
        return (mText);
    }

    /**
     * Program mainline.
     * @param args The zip code to look up.
     */
    public static void main (String[] args)
    {
        if (0 >= args.length)
            System.out.println ("Usage:  java Zip <zipcode>");
        else
            System.out.println (new Zip (args[0]).getText ());
    }
}
