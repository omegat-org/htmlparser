package org.htmlparser;

import java.io.Serializable;

import org.htmlparser.nodeDecorators.DecodingNode;
import org.htmlparser.nodeDecorators.EscapeCharacterRemovingNode;
import org.htmlparser.nodeDecorators.NonBreakingSpaceConvertingNode;

public class StringNodeFactory implements Serializable {
	
	/**
	 * Flag to tell the parser to decode strings returned by StringNode's toPlainTextString.  
	 * Decoding occurs via the method, org.htmlparser.util.Translate.decode()
	 */
	private boolean shouldDecodeNodes = false;


	/**
	 * Flag to tell the parser to remove escape characters, like \n and \t, returned by StringNode's toPlainTextString.  
	 * Escape character removal occurs via the method, org.htmlparser.util.ParserUtils.removeEscapeCharacters()
	 */
	private boolean shouldRemoveEscapeCharacters = false;
	
	/**
	 * Flag to tell the parser to convert non breaking space 
	 * (i.e. \u00a0) to a space (" ").  If true, this will happen inside StringNode's toPlainTextString.  
	 */
	private boolean shouldConvertNonBreakingSpace = false;

	public Node createStringNode(
		StringBuffer textBuffer,
		int textBegin,
		int textEnd) {
		Node newNode = new StringNode(textBuffer, textBegin, textEnd);
		if (shouldDecodeNodes())
			newNode = new DecodingNode(newNode);
		if (shouldRemoveEscapeCharacters())
			newNode = new EscapeCharacterRemovingNode(newNode);
		if (shouldConvertNonBreakingSpace())
			newNode = new NonBreakingSpaceConvertingNode(newNode);
		return newNode;
	}
	
	/**
	 * Tells the parser to decode nodes using org.htmlparser.util.Translate.decode()
	 */
	public void setNodeDecoding(boolean shouldDecodeNodes) {
			this.shouldDecodeNodes = shouldDecodeNodes;
		}

	public boolean shouldDecodeNodes() {
		return shouldDecodeNodes;
	}

	public void setEscapeCharacterRemoval(boolean shouldRemoveEscapeCharacters) {
		this.shouldRemoveEscapeCharacters = shouldRemoveEscapeCharacters;
	}

	public boolean shouldRemoveEscapeCharacters() {
		return shouldRemoveEscapeCharacters;
	}

	public void setNonBreakSpaceConversion(boolean shouldConvertNonBreakSpace) {
		this.shouldConvertNonBreakingSpace = shouldConvertNonBreakSpace;
	}
	
	public boolean shouldConvertNonBreakingSpace() {
		return shouldConvertNonBreakingSpace;
	}	
}
