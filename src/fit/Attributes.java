package fit;

import java.util.*;

import org.htmlparser.parserHelper.*;
import org.htmlparser.tags.*;

public class Attributes extends ColumnFixture {
	private AttributeParser attParser = new AttributeParser();
	public String key;
	private Map table;

	public String tagContents;

	public String value() {
		return (String) table.get(key);
	}


	public String name() {
		return (String) table.get(Tag.TAGNAME);
	}

	public int attributeCount() {
		return table.size() - 1;
	}

	public void execute() throws Exception {
		table = attParser.parseAttributes(tagContents);
	}

	public void wrong (Parse cell, String actual) {
		actual = escape(actual);
		wrong(cell);
		cell.addToBody(label("expected") + "<hr>" + actual.replaceAll("\n","<BR>") + label("actual"));
	}
}