package org.htmlparser.tests;

import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class DecodingNodeTest extends ParserTestCase {

	public DecodingNodeTest(String name) {
		super(name);
	}

	private String parseToObtainDecodedResult(String STRING_TO_DECODE)
		throws ParserException {
		StringBuffer decodedContent = new StringBuffer();
		createParser(STRING_TO_DECODE);
		parser.setNodeDecoding(true);  // tell parser to decode StringNodes
		NodeIterator nodes = parser.elements();
		
		while (nodes.hasMoreNodes()) 
			decodedContent.append(nodes.nextNode().toPlainTextString());			

		return decodedContent.toString();
	}

	public void testAmpersand() throws Exception {
		String ENCODED_WORKSHOP_TITLE =
			"The Testing &amp; Refactoring Workshop";
			
		String DECODED_WORKSHOP_TITLE =
			"The Testing & Refactoring Workshop";

		assertEquals(
			"ampersand in string",
			DECODED_WORKSHOP_TITLE,
			parseToObtainDecodedResult(ENCODED_WORKSHOP_TITLE));
	}

	public void testNumericReference() throws Exception {
		String ENCODED_DIVISION_SIGN =
			"&#247; is the division sign.";
			
		String DECODED_DIVISION_SIGN =
			"÷ is the division sign.";
			
		assertEquals(
			"numeric reference for division sign",
			DECODED_DIVISION_SIGN,
			parseToObtainDecodedResult(ENCODED_DIVISION_SIGN));
	}
	
	
	public void testReferencesInString () throws Exception {
		String ENCODED_REFERENCE_IN_STRING =
			"Thus, the character entity reference &divide; is a more convenient" +
			" form than &#247; for obtaining the division sign (÷)";
		
		String DECODED_REFERENCE_IN_STRING =
			"Thus, the character entity reference ÷ is a more convenient" +
			" form than ÷ for obtaining the division sign (÷)";
		
		assertEquals (
			"character references within a string",
			DECODED_REFERENCE_IN_STRING,
			parseToObtainDecodedResult(ENCODED_REFERENCE_IN_STRING));
	}

	public void testBogusCharacterEntityReference() throws Exception {
		
		String ENCODED_BOGUS_CHARACTER_ENTITY = 
			"The character entity reference &divode; is bogus";
			
		String DECODED_BOGUS_CHARACTER_ENTITY =
			"The character entity reference &divode; is bogus";
		
		assertEquals (
			"bogus character entity reference",
			DECODED_BOGUS_CHARACTER_ENTITY,
			parseToObtainDecodedResult(ENCODED_BOGUS_CHARACTER_ENTITY));
	}
	
	

}
