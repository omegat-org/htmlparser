package org.htmlparser.tests;

import org.htmlparser.HTMLParser;
import org.htmlparser.visitors.TagFindingVisitor;

public class BadTagIdentifier {

	public BadTagIdentifier() {
		super();
	}

	public static void main(String[] args) 
		throws Exception {
		BadTagIdentifier badTags = 
			new BadTagIdentifier();
		badTags.identify("http://www.amazon.com");
	}
	
	private void identify(String string) 
		throws Exception{
		String [] tagsBeingChecked = 
		{"TABLE","DIV","SPAN"};
		
		HTMLParser parser =
			new HTMLParser("http://www.amazon.com");
		TagFindingVisitor tagFinder =
			new TagFindingVisitor(tagsBeingChecked, true);
		parser.visitAllNodesWith(tagFinder);
		for (int i=0;i<tagsBeingChecked.length;i++) {
			System.out.println(
				"Number of "+tagsBeingChecked[i]+" begin tags = "+
			tagFinder.getTagCount(i));
			System.out.println(
				"Number of "+tagsBeingChecked[i]+" end tags = "+
				tagFinder.getEndTagCount(i));
		}
		
	}
}
