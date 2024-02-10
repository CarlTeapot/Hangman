/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.ArrayList;

public class HangmanLexicon {
	BufferedReader reader;
	private ArrayList<String> words;
	RandomGenerator rgen = RandomGenerator.getInstance();

	/**
	 * Constructor initializes the arraylist and fills it with the words read from the lexicon file with BufferedReader
	 */
	public HangmanLexicon() throws IOException {
		words = new ArrayList<>();
		reader = new BufferedReader(new FileReader("src/HangmanLexicon.txt"));
		while (reader.readLine() != null)
			words.add(reader.readLine());
	}

	/**
	 * Returns the number of words in the lexicon.
	 */
	public int getWordCount() {
		return words.size();
	}

	/**
	 * Returns the word at the specified index.
	 */
	public String getWord(int index) {
		return words.get(index);
	}

	;
}
