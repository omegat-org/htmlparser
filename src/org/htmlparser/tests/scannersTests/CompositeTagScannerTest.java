// HTMLParser Library v1_4_20031109 - A java-based parser for HTML
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

package org.htmlparser.tests.scannersTests;

import java.util.Vector;
import org.htmlparser.AbstractNode;
import org.htmlparser.Node;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.StringNode;
import org.htmlparser.lexer.Page;
import org.htmlparser.scanners.CompositeTagScanner;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.Tag;
import org.htmlparser.tests.ParserTestCase;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class CompositeTagScannerTest extends ParserTestCase {
    static
    {
        System.setProperty ("org.htmlparser.tests.scannersTests.CompositeTagScannerTest", "CompositeTagScannerTest");
    }

    private CompositeTagScanner scanner;
    private String url;

    public CompositeTagScannerTest(String name) {
        super(name);
    }

    protected void setUp() {
        scanner =
            new CompositeTagScanner() {
                String [] arr = {
                    "SOMETHING"
                };
                public Tag createTag(Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
                {
                    return null;
                }
                public String[] getID() {
                    return arr;
                }

            };
    }

    private CustomTag parseCustomTag(int expectedNodeCount) throws ParserException {
        parser.setNodeFactory (new PrototypicalNodeFactory (new CustomTag ()));
        parseAndAssertNodeCount(expectedNodeCount);
        assertType("node",CustomTag.class,node[0]);
        CustomTag customTag = (CustomTag)node[0];
        return customTag;
    }

    public void testEmptyCompositeTag() throws ParserException {
        String html = "<Custom/>";
        createParser(html);
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",0,customTag.getChildCount());
        assertTrue("custom tag should be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",9,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        assertStringEquals("html",html,customTag.toHtml());
    }

    public void testEmptyCompositeTagAnotherStyle() throws ParserException {
        String html = "<Custom></Custom>";
        createParser(html);
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",0,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        assertEquals("html",html,customTag.toHtml());
    }

    public void testCompositeTagWithOneTextChild() throws ParserException {
        String html = 
            "<Custom>" +
                "Hello" +
            "</Custom>";
        createParser(html);
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        Node child = customTag.childAt(0);
        assertType("child",StringNode.class,child);
        assertStringEquals("child text","Hello",child.toPlainTextString());
    }

    public void testCompositeTagWithTagChild() throws ParserException {
        String childtag = "<Hello>";
        createParser(
            "<Custom>" +
                childtag +
            "</Custom>"
        );
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("custom tag starting loc",0,customTag.getStartPosition ());
        assertEquals("custom tag ending loc",24,customTag.getEndTag ().getEndPosition ());

        Node child = customTag.childAt(0);
        assertType("child",Tag.class,child);
        assertStringEquals("child html",childtag,child.toHtml());
    }

    public void testCompositeTagWithAnotherTagChild() throws ParserException {
        String childtag = "<Another/>";
        createParser(
            "<Custom>" +
                 childtag +
            "</Custom>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new CustomTag (),
                    new AnotherTag (true),
                }));
        parseAndAssertNodeCount(1);
        assertType("node",CustomTag.class,node[0]);
        CustomTag customTag = (CustomTag)node[0];
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("custom tag starting loc",0,customTag.getStartPosition ());
        assertEquals("custom tag ending loc",27,customTag.getEndTag ().getEndPosition ());

        Node child = customTag.childAt(0);
        assertType("child",AnotherTag.class,child);
        AnotherTag tag = (AnotherTag)child;
        assertEquals("another tag start pos",8,tag.getStartPosition ());
        assertEquals("another tag ending pos",18,tag.getEndPosition ());

        assertEquals("custom end tag start pos",18,customTag.getEndTag().getStartPosition ());
        assertStringEquals("child html",childtag,child.toHtml());
    }

    public void testParseTwoCompositeTags() throws ParserException {
        createParser(
            "<Custom>" +
            "</Custom>" +
            "<Custom/>"
        );
        parser.setNodeFactory (new PrototypicalNodeFactory (new CustomTag ()));
        parseAndAssertNodeCount(2);
        assertType("tag 1",CustomTag.class,node[0]);
        assertType("tag 2",CustomTag.class,node[1]);
    }

    public void testXmlTypeCompositeTags() throws ParserException {
        createParser(
            "<Custom>" +
                "<Another name=\"subtag\"/>" +
                "<Custom />" +
            "</Custom>" +
            "<Custom/>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(2);
        assertType("first node",CustomTag.class,node[0]);
        assertType("second node",CustomTag.class,node[1]);
        CustomTag customTag = (CustomTag)node[0];
        Node node = customTag.childAt(0);
        assertType("first child",AnotherTag.class,node);
        node = customTag.childAt(1);
        assertType("second child",CustomTag.class,node);
    }

    public void testCompositeTagWithNestedTag() throws ParserException {
        createParser(
            "<Custom>" +
                "<Another>" +
                    "Hello" +
                "</Another>" +
                "<Custom/>" +
            "</Custom>" +
            "<Custom/>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(2);
        assertType("first node",CustomTag.class,node[0]);
        assertType("second node",CustomTag.class,node[1]);
        CustomTag customTag = (CustomTag)node[0];
        Node node = customTag.childAt(0);
        assertType("first child",AnotherTag.class,node);
        AnotherTag anotherTag = (AnotherTag)node;
        assertEquals("another tag children count",1,anotherTag.getChildCount());
        node = anotherTag.childAt(0);
        assertType("nested child",StringNode.class,node);
        StringNode text = (StringNode)node;
        assertEquals("text","Hello",text.toPlainTextString());
    }

    public void testCompositeTagWithTwoNestedTags() throws ParserException {
        createParser(
            "<Custom>" +
                "<Another>" +
                    "Hello" +
                "</Another>" +
                "<unknown>" +
                    "World" +
                "</unknown>" +
                "<Custom/>" +
            "</Custom>" +
            "<Custom/>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(2);
        assertType("first node",CustomTag.class,node[0]);
        assertType("second node",CustomTag.class,node[1]);
        CustomTag customTag = (CustomTag)node[0];
        assertEquals("first custom tag children count",5,customTag.getChildCount());
        Node node = customTag.childAt(0);
        assertType("first child",AnotherTag.class,node);
        AnotherTag anotherTag = (AnotherTag)node;
        assertEquals("another tag children count",1,anotherTag.getChildCount());
        node = anotherTag.childAt(0);
        assertType("nested child",StringNode.class,node);
        StringNode text = (StringNode)node;
        assertEquals("text","Hello",text.toPlainTextString());
    }

    public void testErroneousCompositeTag() throws ParserException {
        String html = "<custom>";
        createParser(html);
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",0,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        assertStringEquals("html",html + "</custom>",customTag.toHtml());
    }

    public void testErroneousCompositeTagWithChildren() throws ParserException {
        String html = "<custom>" + "<firstChild>" + "<secondChild>";
        createParser(html);
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",2,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        assertStringEquals("html",html + "</custom>",customTag.toHtml());
    }

    public void testErroneousCompositeTagWithChildrenAndLineBreak() throws ParserException {
        String html = "<custom>" + "<firstChild>" + "\n" + "<secondChild>";
        createParser(html);
        CustomTag customTag = parseCustomTag(1);
        assertEquals("child count",3,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",1,customTag.getEndTag ().getEndingLineNumber());
        assertStringEquals("html", html + "</custom>", customTag.toHtml()
        );
    }

    public void testTwoConsecutiveErroneousCompositeTags() throws ParserException {
        String tag1 = "<custom>something";
        String tag2 = "<custom></endtag>";
        createParser(tag1 + tag2);
        parser.setNodeFactory (new PrototypicalNodeFactory (new CustomTag (false)));
        parseAndAssertNodeCount(2);
        CustomTag customTag = (CustomTag)node[0];
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("ending loc of custom tag",17,customTag.getEndTag ().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndTag ().getEndingLineNumber());
        assertStringEquals("1st custom tag", tag1 + "</custom>", customTag.toHtml());
        customTag = (CustomTag)node[1];
        assertStringEquals("2nd custom tag", tag2 + "</custom>", customTag.toHtml());
    }

    public void testCompositeTagWithErroneousAnotherTagAndLineBreak() throws ParserException {
        String another = "<another>";
        String custom = "<custom>\n</custom>";
        createParser(
            another +
            custom
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(2);
        AnotherTag anotherTag = (AnotherTag)node[0];
        assertEquals("another tag child count",0,anotherTag.getChildCount());

        CustomTag customTag = (CustomTag)node[1];
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",9,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",17,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",1,customTag.getEndTag ().getEndingLineNumber());
        assertStringEquals("another tag html",another + "</another>",anotherTag.toHtml());
        assertStringEquals("custom tag html",custom,customTag.toHtml());
    }

    public void testCompositeTagWithErroneousAnotherTag() throws ParserException {
        createParser(
            "<custom>" +
                "<another>" +
            "</custom>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new CustomTag (),
                    new AnotherTag (true),
                }));
        parseAndAssertNodeCount(1);
        assertType("node",CustomTag.class,node[0]);
        CustomTag customTag = (CustomTag)node[0];
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        AnotherTag anotherTag = (AnotherTag)customTag.childAt(0);
        assertEquals("another tag ending loc",17,anotherTag.getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        assertStringEquals("html","<custom><another></another></custom>",customTag.toHtml());
    }

    public void testCompositeTagWithDeadlock() throws ParserException {
        createParser(
            "<custom>" +
                "<another>something" +
            "</custom>"+
            "<custom>" +
                "<another>else</another>" +
            "</custom>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new CustomTag (),
                    new AnotherTag (true),
                }));
        parseAndAssertNodeCount(2);
        assertType("node",CustomTag.class,node[0]);
        CustomTag customTag = (CustomTag)node[0];

        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        assertEquals("starting line position",0,customTag.getStartingLineNumber());
        assertEquals("ending line position",0,customTag.getEndingLineNumber());
        AnotherTag anotherTag = (AnotherTag)customTag.childAt(0);
        assertEquals("anotherTag child count",1,anotherTag.getChildCount());
        StringNode stringNode = (StringNode)anotherTag.childAt(0);
        assertStringEquals("anotherTag child text","something",stringNode.toPlainTextString());
        assertStringEquals(
            "first custom tag html",
            "<custom><another>something</another></custom>",
            customTag.toHtml()
        );
        customTag = (CustomTag)node[1];
        assertStringEquals(
            "second custom tag html",
            "<custom><another>else</another></custom>",
            customTag.toHtml()
        );
    }

    public void testCompositeTagCorrectionWithSplitLines() throws ParserException {
        createParser(
            "<custom>" +
                "<another><abcdefg>\n" +
            "</custom>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[]
                {
                    new CustomTag (),
                    new AnotherTag (true),
                }));
        parseAndAssertNodeCount(1);
        assertType("node",CustomTag.class,node[0]);
        CustomTag customTag = (CustomTag)node[0];
        assertEquals("child count",1,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());
        assertEquals("starting loc",0,customTag.getStartTag().getStartPosition ());
        assertEquals("ending loc",8,customTag.getStartTag().getEndPosition ());
        AnotherTag anotherTag = (AnotherTag)customTag.childAt(0);
        assertEquals("anotherTag child count",2,anotherTag.getChildCount());
        assertEquals("anotherTag end loc",27,anotherTag.getEndTag ().getEndPosition ());
        assertEquals("custom end tag begin loc",27,customTag.getEndTag().getStartPosition ());
        assertEquals("custom end tag end loc",36,customTag.getEndTag().getEndPosition ());
    }

    public void testCompositeTagWithSelfChildren() throws ParserException
    {
        String tag1 = "<custom>";
        String tag2 = "<custom>something</custom>";
        String tag3 = "</custom>";
        createParser(tag1 + tag2 + tag3);
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (false),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(3);

        CustomTag customTag = (CustomTag)node[0];
        assertEquals("child count",0,customTag.getChildCount());
        assertFalse("custom tag should not be xml end tag",customTag.isEmptyXmlTag());

        assertStringEquals(
            "first custom tag html",
            tag1 + "</custom>",
            customTag.toHtml()
        );
        customTag = (CustomTag)node[1];
        assertStringEquals(
            "second custom tag html",
            tag2,
            customTag.toHtml()
        );
        Tag endTag = (Tag)node[2];
        assertStringEquals(
            "third custom tag html",
            tag3,
            endTag.toHtml()
        );
    }

    public void testParentConnections() throws ParserException {
        String tag1 = "<custom>";
        String tag2 = "<custom>something</custom>";
        String tag3 = "</custom>";
        createParser(tag1 + tag2 + tag3);
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (false),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(3);

        CustomTag customTag = (CustomTag)node[0];

        assertStringEquals(
            "first custom tag html",
            tag1 + "</custom>",
            customTag.toHtml()
        );
        assertNull(
            "first custom tag should have no parent",
            customTag.getParent()
        );

        customTag = (CustomTag)node[1];
        assertStringEquals(
            "second custom tag html",
            tag2,
            customTag.toHtml()
        );
        assertNull(
            "second custom tag should have no parent",
            customTag.getParent()
        );

        Node firstChild = customTag.childAt(0);
        assertType("firstChild",StringNode.class,firstChild);
        Node parent = firstChild.getParent();
        assertNotNull("first child parent should not be null",parent);
        assertSame("parent and custom tag should be the same",customTag,parent);

        Tag endTag = (Tag)node[2];
        assertStringEquals(
            "third custom tag html",
            tag3,
            endTag.toHtml()
        );
        assertNull(
            "end tag should have no parent",
            endTag.getParent()
        );

    }

    public void testUrlBeingProvidedToCreateTag() throws ParserException {
        createParser("<Custom/>","http://www.yahoo.com");

        parser.setNodeFactory (new PrototypicalNodeFactory (new CustomTag ()));
        parseAndAssertNodeCount(1);
        assertStringEquals("url","http://www.yahoo.com",((AbstractNode)node[0]).getPage ().getUrl ());
    }

    public void testComplexNesting() throws ParserException {
        createParser(
            "<custom>" +
                "<custom>" +
                    "<another>" +
                "</custom>" +
                "<custom>" +
                    "<another>" +
                "</custom>" +
            "</custom>"
        );
        parser.setNodeFactory (
            new PrototypicalNodeFactory (
                new Tag[] {
                    new CustomTag (),
                    new AnotherTag (false),
                }));
        parseAndAssertNodeCount(1);
        assertType("root node",CustomTag.class, node[0]);
        CustomTag root = (CustomTag)node[0];
        assertNodeCount("child count",2,root.getChildrenAsNodeArray());
        Node child = root.childAt(0);
        assertType("child",CustomTag.class,child);
        CustomTag customChild = (CustomTag)child;
        assertNodeCount("grand child count",1,customChild.getChildrenAsNodeArray());
        Node grandchild = customChild.childAt(0);
        assertType("grandchild",AnotherTag.class,grandchild);
    }

    public void testDisallowedChildren() throws ParserException {
        createParser(
            "<custom>\n" +
            "Hello" +
            "<custom>\n" +
            "World" +
            "<custom>\n" +
            "Hey\n" +
            "</custom>"
        );
        parser.setNodeFactory (new PrototypicalNodeFactory (new CustomTag (false)));
        parseAndAssertNodeCount(3);
        for (int i=0;i<nodeCount;i++) {
            assertType("node "+i,CustomTag.class,node[i]);
        }
    }

    public static class CustomScanner extends CompositeTagScanner {
        private static final String MATCH_NAME [] = { "CUSTOM" };
        private boolean selfChildrenAllowed;
        public CustomScanner() {
            this(true);
        }

        public CustomScanner(boolean selfChildrenAllowed) {
//            super("", selfChildrenAllowed ? new String[] {} : MATCH_NAME);
            this.selfChildrenAllowed = selfChildrenAllowed;
        }

        public String[] getID() {
            return MATCH_NAME;
        }

        public Tag createTag (Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
        {
            CustomTag ret;

            ret = new CustomTag (selfChildrenAllowed);
            ret.setPage (page);
            ret.setStartPosition (start);
            ret.setEndPosition (end);
            ret.setAttributesEx (attributes);
            ret.setStartTag (startTag);
            ret.setEndTag (endTag);
            ret.setChildren (children);

            return (ret);
        }
    }

    public static class AnotherScanner extends CompositeTagScanner {
        private static final String MATCH_NAME [] = { "ANOTHER" };
        private boolean acceptCustomTagsButDontAcceptCustomEndTags;
        public AnotherScanner() {
//            super("", new String[] {"CUSTOM"});
            acceptCustomTagsButDontAcceptCustomEndTags = false;
        }

        public AnotherScanner(boolean acceptCustomTagsButDontAcceptCustomEndTags) {
//            super("", new String[] {}, new String[] {"CUSTOM"});
            this.acceptCustomTagsButDontAcceptCustomEndTags = acceptCustomTagsButDontAcceptCustomEndTags;
        }

        public String[] getID() {
            return MATCH_NAME;
        }

        public Tag createTag (Page page, int start, int end, Vector attributes, Tag startTag, Tag endTag, NodeList children) throws ParserException
        {
            AnotherTag ret;

            ret = new AnotherTag (acceptCustomTagsButDontAcceptCustomEndTags);
            ret.setPage (page);
            ret.setStartPosition (start);
            ret.setEndPosition (end);
            ret.setAttributesEx (attributes);
            ret.setStartTag (startTag);
            ret.setEndTag (endTag);
            ret.setChildren (children);

            return (ret);
        }
        protected boolean isBrokenTag() {
            return false;
        }

    }

    public static class CustomTag extends CompositeTag
    {
        /**
         * The set of names handled by this tag.
         */
        private static final String[] mIds = new String[] {"CUSTOM"};

        protected String[] mEnders;

        /**
         * The default scanner for custom tags.
         */
        protected final static CustomScanner mDefaultScanner = new CustomScanner ();

        public CustomTag ()
        {
            this (true);
        }

        public CustomTag (boolean selfChildrenAllowed)
        {
            if (selfChildrenAllowed)
                mEnders = new String[0];
            else
                mEnders = mIds;
            setThisScanner (mDefaultScanner);
        }

        /**
         * Return the set of names handled by this tag.
         * @return The names to be matched that create tags of this type.
         */
        public String[] getIds ()
        {
            return (mIds);
        }

        /**
         * Return the set of tag names that cause this tag to finish.
         * @return The names of following tags that stop further scanning.
         */
        public String[] getEnders ()
        {
            return (mEnders);
        }

        
    }

    public static class AnotherTag extends CompositeTag
    {
        /**
         * The set of names handled by this tag.
         */
        private static final String[] mIds = new String[] {"ANOTHER"};

        /**
         * The set of tag names that indicate the end of this tag.
         */
        private final String[] mEnders;

        /**
         * The set of end tag names that indicate the end of this tag.
         */
        private final String[] mEndTagEnders;

        /**
         * The default scanner for custom tags.
         */
        protected final static AnotherScanner mDefaultScanner = new AnotherScanner ();

        public AnotherTag (boolean acceptCustomTagsButDontAcceptCustomEndTags)
        {
            if (acceptCustomTagsButDontAcceptCustomEndTags)
            {
                mEnders = new String[0];
                mEndTagEnders = new String[] {"CUSTOM"};
            }
            else
            {
                mEnders = new String[] {"CUSTOM"};
                mEndTagEnders = new String[] {"CUSTOM"};
            }
            setThisScanner (mDefaultScanner);
        }

        /**
         * Return the set of names handled by this tag.
         * @return The names to be matched that create tags of this type.
         */
        public String[] getIds ()
        {
            return (mIds);
        }

        /**
         * Return the set of tag names that cause this tag to finish.
         * @return The names of following tags that stop further scanning.
         */
        public String[] getEnders ()
        {
            return (mEnders);
        }

        /**
         * Return the set of end tag names that cause this tag to finish.
         * @return The names of following end tags that stop further scanning.
         */
        public String[] getEndTagEnders ()
        {
            return (mEndTagEnders);
        }
    }
}
