// HTMLParser Library v1_3_20030125_20021207 - A java-based parser for HTML
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

package org.htmlparser.tests.utilTests;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Vector;

import junit.framework.TestCase;

import org.htmlparser.HTMLParser;
import org.htmlparser.HTMLNode;
import org.htmlparser.beans.StringBean;
import org.htmlparser.beans.LinkBean;
import org.htmlparser.util.HTMLEnumeration;
import org.htmlparser.util.HTMLParserException;

public class BeanTest extends TestCase
{
	private static final String RED_GREEN_WEBSITE_HTML =
	"<html>" +	"<head>" +	"<title>The Red Green Show</title>" +	"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" +	"<style type=\"text/css\"><!--" +	"body {font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px}" +	"table {font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px}" +	"td {font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px}" +	"a:link {text-decoration:none; color: #FFEFD5;}" +	"a:visited {text-decoration:none; color: #009933}" +	"a:active {color: Lime}" +	"a:hover {text-decoration:underline; color:#FFEFD5}" +	"div {font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11px}" +	"-->" +	"</style>" +	"<script language=\"JavaScript\">" +	"<!--" +	"function MM_preloadImages() { //v3.0" +	"  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();" +	"    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)" +	"    if (a[i].indexOf(\"#\")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}" +	"}" +	"" +	"function MM_swapImgRestore() { //v3.0" +	"  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;" +	"}" +	"" +	"function MM_findObj(n, d) { //v4.0" +	"  var p,i,x;  if(!d) d=document; if((p=n.indexOf(\"?\"))>0&&parent.frames.length) {" +	"    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}" +	"  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];" +	"  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);" +	"  if(!x && document.getElementById) x=document.getElementById(n); return x;" +	"}" +	"" +	"function MM_swapImage() { //v3.0" +	"  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)" +	"   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}" +	"}" +	"//-->" +	"</script>" +	"<script language=\"JavaScript\">" +	"<!--" +	"<!--" +	"function MM_reloadPage(init) {  //reloads the window if Nav4 resized" +	"  if (init==true) with (navigator) {if ((appName==\"Netscape\")&&(parseInt(appVersion)==4)) {" +	"    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}" +	"  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();" +	"}" +	"MM_reloadPage(true);" +	"// -->" +	"" +	"function MM_controlSound(x, _sndObj, sndFile) { //v3.0" +	"  var i, method = \"\", sndObj = eval(_sndObj);" +	"  if (sndObj != null) {" +	"    if (navigator.appName == 'Netscape') method = \"play\";" +	"    else {" +	"      if (window.MM_WMP == null) {" +	"        window.MM_WMP = false;" +	"        for(i in sndObj) if (i == \"ActiveMovie\") {" +	"          window.MM_WMP = true; break;" +	"      } }" +	"      if (window.MM_WMP) method = \"play\";" +	"      else if (sndObj.FileName) method = \"run\";" +	"  } }" +	"  if (method) eval(_sndObj+\".\"+method+\"()\");" +	"  else window.location = sndFile;" +	"}" +	"//-->" +	"</script>" +	"</head>" +	"<body bgcolor=#009933 background=\"images/menu/bkgd_red_green.gif\" onLoad=\"MM_preloadImages('images/index/ssp_logo-over.jpg','images/index/door-over.gif')\">" +	"<table width=568 border=0 cellpadding=0 cellspacing=0>" +	"  <tr> " +	"    <td colspan=3> <img src=\"images/index/top_frame.gif\" width=568 height=22></td>" +	"  </tr>" +	"  <tr> " +	"    <td colspan=2><img name=\"redgreen_sign\" src=\"images/index/redgreen_sign.jpg\" width=295 height=172></td>" +	"    <td rowspan=3><a href=\"menu.htm\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('door','','images/index/door-over.gif',1)\"><img name=\"door\" src=\"images/index/door2.jpg\" width=273 height=370 border=0></a></td>" +	"  </tr>" +	"  <tr> " +	"    <td rowspan=2><img name=\"possum_sign\" src=\"images/index/possum_sign.jpg\" width=162 height=198></td>" +	"    <td><img name=\"door_fill\" src=\"images/index/door_fill.jpg\" width=133 height=105></td>" +	"  </tr>" +	"  <tr> " +	"    <td><a href=\"http://www.ssp.ca\" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('ssp_toolbox','','images/index/ssp_logo-over.jpg',1)\"><img name=\"ssp_toolbox\" src=\"images/index/ssp_logo.jpg\" width=133 height=93 border=0></a></td>" +	"  </tr>" +	"</table>" +	" </body>" +	"</html>";
	
	public BeanTest (String name)
    {
		super (name);
	}

    protected byte[] pickle (Object object)
        throws
            IOException
    {
        ByteArrayOutputStream bos;
        ObjectOutputStream oos;
        byte[] ret;
        
        bos = new ByteArrayOutputStream ();
        oos = new ObjectOutputStream (bos);
        oos.writeObject (object);
        oos.close ();
        ret = bos.toByteArray ();

        return (ret);
    }
    
    protected Object unpickle (byte[] data)
        throws
            IOException,
            ClassNotFoundException
    {
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        Object ret;

        bis = new ByteArrayInputStream (data);
        ois = new ObjectInputStream (bis);
        ret = ois.readObject ();
        ois.close ();
        
        return (ret);
    }

    public void testZeroArgConstructor ()
        throws
            IOException,
            ClassNotFoundException,
            HTMLParserException
    {
        HTMLParser parser;
        byte[] data;
        
        parser = new HTMLParser ();
        data = pickle (parser);
        parser = (HTMLParser)unpickle (data);
    }
        
    public void testSerializable ()
        throws
            IOException,
            ClassNotFoundException,
            HTMLParserException
    {
        HTMLParser parser;
        Vector vector;
        HTMLEnumeration enumeration;
        byte[] data;
        
        parser = HTMLParser.createParser(RED_GREEN_WEBSITE_HTML);
        enumeration = parser.elements ();
        vector = new Vector (50);
        while (enumeration.hasMoreNodes ())
            vector.addElement (enumeration.nextNode ());

        data = pickle (parser);
        parser = (HTMLParser)unpickle (data);

        enumeration = parser.elements ();
        while (enumeration.hasMoreNodes ())
            assertEquals (
                "Nodes before and after serialization differ",
                ((HTMLNode)vector.remove (0)).toHTML (),
                ((HTMLNode)enumeration.nextNode ()).toHTML ());
    }
    
    public void testSerializableScanners ()
        throws
            IOException,
            ClassNotFoundException,
            HTMLParserException
    {
        HTMLParser parser;
        Vector vector;
        HTMLEnumeration enumeration;
        byte[] data;
        
        parser = HTMLParser.createParser(RED_GREEN_WEBSITE_HTML);
        parser.registerScanners ();
        enumeration = parser.elements ();
        vector = new Vector (50);
        while (enumeration.hasMoreNodes ())
            vector.addElement (enumeration.nextNode ());

        data = pickle (parser);
        parser = (HTMLParser)unpickle (data);

        enumeration = parser.elements ();
        while (enumeration.hasMoreNodes ())
            assertEquals (
                "Nodes before and after serialization differ",
                ((HTMLNode)vector.remove (0)).toHTML (),
                ((HTMLNode)enumeration.nextNode ()).toHTML ());
    }

    public void testSerializableStringBean ()
        throws
            IOException,
            ClassNotFoundException,
            HTMLParserException
    {
        StringBean sb;
        String text;
        byte[] data;
        
        sb = new StringBean ();
        sb.setURL ("http://slashdot.org");
        text = sb.getStrings ();

        data = pickle (sb);
        sb = (StringBean)unpickle (data);

        assertEquals (
            "Strings before and after serialization differ",
            text,
            sb.getStrings ());
    }

    public void testSerializableLinkBean ()
        throws
            IOException,
            ClassNotFoundException,
            HTMLParserException
    {
        LinkBean lb;
        URL[] links;
        byte[] data;
        URL[] links2;
        
        lb = new LinkBean ();
        lb.setURL ("http://slashdot.org");
        links = lb.getLinks ();

        data = pickle (lb);
        lb = (LinkBean)unpickle (data);

        links2 = lb.getLinks ();
        assertEquals ("Number of links after serialization differs", links.length, links2.length);
        for (int i = 0; i < links.length; i++)
        {
            assertEquals (
                "Links before and after serialization differ",
                links[i],
                links2[i]);
        }
    }
    
    public void testStringBeanListener ()
    {
        final StringBean sb;
        final Boolean hit[] = new Boolean[1];
        
        sb = new StringBean ();
        hit[0] = Boolean.FALSE;
        sb.addPropertyChangeListener (
            new PropertyChangeListener ()
            {
                public void propertyChange (PropertyChangeEvent event)
                {
                    if (event.getSource ().equals (sb))
                        if (event.getPropertyName ().equals (StringBean.PROP_STRINGS_PROPERTY))
                            hit[0] = Boolean.TRUE;
                }
            });

        hit[0] = Boolean.FALSE;
        sb.setURL ("http://slashdot.org");
        assertTrue (
            "Strings property change not fired for URL change",
            hit[0].booleanValue ());

        hit[0] = Boolean.FALSE;
        sb.setLinks (true);
        assertTrue (
            "Strings property change not fired for links change",
            hit[0].booleanValue ());
    }

    public void testLinkBeanListener ()
    {
        final LinkBean lb;
        final Boolean hit[] = new Boolean[1];
        
        lb = new LinkBean ();
        hit[0] = Boolean.FALSE;
        lb.addPropertyChangeListener (
            new PropertyChangeListener ()
            {
                public void propertyChange (PropertyChangeEvent event)
                {
                    if (event.getSource ().equals (lb))
                        if (event.getPropertyName ().equals (LinkBean.PROP_LINKS_PROPERTY))
                            hit[0] = Boolean.TRUE;
                }
            });

        hit[0] = Boolean.FALSE;
        lb.setURL ("http://slashdot.org");
        assertTrue (
            "Links property change not fired for URL change",
            hit[0].booleanValue ());
    }
}

