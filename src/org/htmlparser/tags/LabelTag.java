package org.htmlparser.tags;

import org.htmlparser.tags.data.CompositeTagData;
import org.htmlparser.tags.data.TagData;

public class LabelTag extends CompositeTag {

	public LabelTag(TagData tagData, CompositeTagData compositeTagData) {
		super(tagData, compositeTagData);
	}
	
	public String getLabel() {
		return toPlainTextString();
	}
	
	public String toString() {
		return "LABEL: "+getLabel();
	}
}
