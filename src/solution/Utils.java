package solution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * utitlity class
 * @author Oak
 *
 */
public class Utils {
	
	private static final String STOPWORDS_FILE = "./stopwords.txt";

	/**
	 * http://stackoverflow.com/questions/8429516/stop-words-removal-in-java
	 * @return a set of stopWords -> to find if a string is in the list, use stopWords.contain(word)
	 * @throws IOException 
	 */
	public static Set<String> getStopWords() throws IOException {
		Set<String> stopWords = new LinkedHashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(STOPWORDS_FILE));
		for(String line; (line = br.readLine()) != null; )
		   stopWords.add(line.trim());
		br.close();
		return stopWords;
	}
}
