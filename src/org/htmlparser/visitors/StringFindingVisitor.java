// HTMLParser Library $Name$ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Joshua Kerievsky
//
// Revision Control Information
//
// $Source$
// $Author$
// $Date$
// $Revision$
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.visitors;

import org.htmlparser.StringNode;

public class StringFindingVisitor extends NodeVisitor {
    private boolean stringFound = false;
    private String stringToFind;
    private int foundCount;
    private boolean multipleSearchesWithinStrings;

    public StringFindingVisitor(String stringToFind) {
        this.stringToFind = stringToFind.toUpperCase();
        foundCount = 0;
        multipleSearchesWithinStrings = false;
    }

    public void doMultipleSearchesWithinStrings() {
        multipleSearchesWithinStrings = true;
    }

    public void visitStringNode(StringNode stringNode) {
        String stringToBeSearched = stringNode.getText().toUpperCase();
        if (!multipleSearchesWithinStrings &&
            stringToBeSearched.indexOf(stringToFind) != -1) {
            stringFound = true;
            foundCount++;
        } else if (multipleSearchesWithinStrings) {
            int index = -1;
            do {
                index = stringToBeSearched.indexOf(stringToFind, index+1);
                if (index!=-1)
                    foundCount++;
            } while (index != -1);
        }
    }

    public boolean stringWasFound() {
        return stringFound;
    }

    public int stringFoundCount() {
        return foundCount;
    }

}
