package org.htmlparser.tests.codeMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

public class LineCounter {
	
	public int count(File file) {
		System.out.println("Handling "+file.getName());
		int count = 0;
		// Get all files in current directory
		if (file.isDirectory()) {
			// Get the listing in this directory
			count = recurseDirectory(file, count);
		} else {
			// It is a file
			count = countLinesIn(file);
		}
		return count;
	}

	/** 
	 * Counts code excluding comments and blank lines in the given file
	 * @param file
	 * @return int
	 */
	public int countLinesIn(File file) {
		int count = 0;
		System.out.println("Counting "+file.getName());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line = null;
			do {
				line = reader.readLine();
				if (line!=null && 
					line.indexOf("*")==-1 && 
					line.indexOf("//")==-1 && 
					line.length()>0
				) count++; 
			}
			while (line!=null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int recurseDirectory(File file, int count) {
		File [] files = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				if (file.getName().indexOf(".java")!=-1 || file.isDirectory()) {
					return true; 
				} else {
					return false;
				}
			}
		});
		for (int i=0;i<files.length;i++) {
			count += count(files[i]);
		}
		return count;
	}
	
	public static void main(String [] args) {
		LineCounter lc = new LineCounter();
		System.out.println("Line Count = "+lc.count(new File(args[0])));
	}
}
