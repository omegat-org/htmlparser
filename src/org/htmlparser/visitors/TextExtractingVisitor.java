package org.htmlparser.visitors;

import org.htmlparser.HTMLStringNode;
import org.htmlparser.tags.HTMLEndTag;
import org.htmlparser.tags.HTMLTag;
import org.htmlparser.util.Translate;


/**
 * Extracts text from a web page.
 * Usage:
 * <code>
 * HTMLParser parser = new HTMLParser(...);
 * TextExtractingVisitor visitor = new TextExtractingVisitor();
 * parser.visitAllNodesWith(visitor);
 * String textInPage = visitor.getExtractedText();
 * </code>
 */
public class TextExtractingVisitor extends HTMLVisitor {
	private StringBuffer textAccumulator;
	private boolean preTagBeingProcessed;
	
	public TextExtractingVisitor() {
		textAccumulator = new StringBuffer();
		preTagBeingProcessed = false;
	}

	public String getExtractedText() {
		return textAccumulator.toString();
	}

	public void visitStringNode(HTMLStringNode stringNode) {
		String text = stringNode.getText();
		if (!preTagBeingProcessed) {
			text = Translate.decode(text); 
			text = replaceNonBreakingSpaceWithOrdinarySpace(text);
		}
		textAccumulator.append(text);
	}

	private String replaceNonBreakingSpaceWithOrdinarySpace(String text) {
		return text.replace('\u00a0',' ');
	}

	public void visitEndTag(HTMLEndTag endTag) {
		if (isPreTag(endTag)) 
			preTagBeingProcessed = false;
	}

	public void visitTag(HTMLTag tag) {
		if (isPreTag(tag)) 
			preTagBeingProcessed = true;
	}

	private boolean isPreTag(HTMLTag tag) {
		return tag.getTagName().equals("PRE");
	}

}
