package org.htmlparser.tests.tagTests;

import org.htmlparser.*;
import org.htmlparser.tags.*;
import org.htmlparser.tests.*;
import org.htmlparser.util.*;


public class CompositeTagTest extends ParserTestCase {

	public CompositeTagTest(String name) {
		super(name);
	}

	public void testDigupStringNode() throws ParserException {
		createParser(
			"<table>" +				"<table>" +					"<tr>" +					"<td>" +					"Hello World" +					"</td>" +					"</tr>" +				"</table>" +			"</table>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		TableTag tableTag = (TableTag)node[0];
		StringNode [] stringNode = 
			tableTag.digupStringNode("Hello World");
			
		assertEquals("number of string nodes",1,stringNode.length);
		assertNotNull("should have found string node",stringNode);
		CompositeTag parent = stringNode[0].getParent();
		assertType("should be column",TableColumn.class,parent);
		parent = parent.getParent();
		assertType("should be row",TableRow.class,parent);
		parent = parent.getParent();
		assertType("should be table",TableTag.class,parent);
		parent = parent.getParent();
		assertType("should be table again",TableTag.class,parent);
		assertSame("should be original table",tableTag,parent);
	}
	
	public void testFindPositionOf() throws ParserException {
		createParser(
			"<table>" +
				"<table>" +
					"<tr>" +
					"<td>" +					"Hi There<a><b>sdsd</b>" +
					"Hello World" +
					"</td>" +
					"</tr>" +
				"</table>" +
			"</table>"
		);
		parser.registerScanners();
		parseAndAssertNodeCount(1);
		TableTag tableTag = (TableTag)node[0];
		StringNode [] stringNode = 
			tableTag.digupStringNode("Hello World");
			
		assertEquals("number of string nodes",1,stringNode.length);
		assertNotNull("should have found string node",stringNode);
		CompositeTag parent = stringNode[0].getParent();
		int pos = parent.findPositionOf(stringNode[0]);
		assertEquals("position",5,pos);
	}	
}
