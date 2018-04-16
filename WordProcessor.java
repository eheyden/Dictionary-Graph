import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Assignment: Exercise4, Due: 04/16/18, Other Sources: Cracking the Code
 * 
 * Word Processor helps take in a stream of words, edits them and checks if one
 * word is adjacent to the other.
 * 
 * @author Ribhav Hora (ribhav.hora@wisc.edu)
 */
public class WordProcessor {

	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 * 
	 * @see <a href=
	 *      "http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8
	 *      stream blog</a>
	 * 
	 * @param filepath
	 *            file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException
	 *             exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		return Files.lines(Paths.get(filepath)).map(String::trim).filter(x -> x != null && !x.equals("")).map(String::toUpperCase);
	}

	/**
	 * Adjacency between word1 and word2 is defined by: if the difference between
	 * word1 and word2 is of 1 char replacement 1 char addition 1 char deletion then
	 * word1 and word2 are adjacent else word1 and word2 are not adjacent
	 * 
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {
		if (word1.length() == word2.length()) {
			return replaceOne(word1, word2);
		} else if (word1.length() + 1 == word2.length()) {
			return singleEdit(word1, word2);
		} else if (word1.length() - 1 == word2.length()) {
			return singleEdit(word2, word1);
		}

		return false;
	}

	/**
	 * Checks if the words have only a single difference
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return true if word1 and word2 are one char difference else false
	 */
	private static boolean replaceOne(String word1, String word2) {
		boolean found = false;
		for (int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				if (found) {
					return false;
				}
				found = true;
			}
		}
		return true;
	}

	/**
	 * Checks if the words are one edit away
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return true if word1 and word2 are one edit away else false
	 */
	private static boolean singleEdit(String word1, String word2) {
		int firstIndex = 0;
		int secondIndex = 0;
		while (secondIndex < word2.length() && firstIndex < word1.length()) {
			if (word1.charAt(firstIndex) != word2.charAt(secondIndex)) {
				if (firstIndex != secondIndex) {
					return false;
				}
				secondIndex++;
			} else {
				firstIndex++;
				secondIndex++;
			}
		}
		return true;
	}

}
