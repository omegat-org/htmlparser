package org.htmlparser.scanners;

import java.util.Stack;

import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Tag;
import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;
import org.htmlparser.util.ParserException;

/**
 * This scanner is created by BulletListScanner. It shares a stack to maintain the parent-child relationship
 * with BulletListScanner. The rules implemented are :<br>
 * [1] A &lt;ul&gt; can have &lt;li&gt; under it<br>
 * [2] A &lt;li&gt; can have &lt;ul&gt; under it<br>
 * [3] A &lt;li&gt; cannot have &lt;li&gt; under it<br> 
 * <p>
 * These rules are implemented easily through the shared stack. 
 */
public class BulletScanner extends CompositeTagScanner {
	private static final String [] MATCH_STRING = {"LI"};
	private final static String ENDERS [] = { "BODY", "HTML" };
	private final static String END_TAG_ENDERS [] = { "UL" };
	private Stack ulli;
	
	public BulletScanner(Stack ulli) {
		this("",ulli);
	}

	public BulletScanner(String filter, Stack ulli) {
		super(filter, MATCH_STRING, ENDERS, END_TAG_ENDERS, false);
		this.ulli = ulli;
	}

	public Tag createTag(TagData tagData, CompositeTagData compositeTagData)
		throws ParserException {
		return new Bullet(tagData,compositeTagData);
	}

	public String[] getID() {
		return MATCH_STRING;
	}
	
	/**
	 * This is the logic that decides when a bullet tag can be allowed
	 */
	public boolean shouldCreateEndTagAndExit() {
		if (ulli.size()==0) return false;
		CompositeTagScanner parentScanner = (CompositeTagScanner)ulli.peek();
		if (parentScanner == this) {
			ulli.pop();
			return true;
		} else 
			return false;
	}

	public void beforeScanningStarts() {
		ulli.push(this);
	}

}
