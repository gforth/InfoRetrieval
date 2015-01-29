package solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Token {

	private String word;
	
	@SuppressWarnings("resource")
	public static List<Token> tokenizeFile(String fileName) {
		File textFile = new File(fileName);
		List<Token> out = new ArrayList<Token>();
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(textFile);
		} catch (FileNotFoundException e) {
			return out;
		}
		while (fileScanner.hasNextLine()) {
	         String line = fileScanner.nextLine();
	         Scanner wordScanner = new Scanner(line);
	         
	         while(wordScanner.hasNext()) {
	        	 String word;
        		 word = wordScanner.next();
	        	 String[] words = word.split("[^a-zA-Z0-9]");
	        	 for(int i=0; i<words.length; i++) {
	        		 if(words[i].isEmpty()) continue;
	        		 out.add(new Token(words[i]));
	        	 }
	         }
		}
		return out;
		
	}
	
	public Token(String w) {
		word = w.toLowerCase().replaceAll("[^A-Za-z0-9]", "");
	}
	
	public String getWord() {
		return word;
	}
	
	public boolean equals(Object other) {
    	if (other instanceof Token) {
    		Token otherToken = (Token) other;
    		return (word.equals(otherToken.getWord()));
    	}

    	return false;
    }
	
	public int hashCode() {
    	return word.hashCode();
    }
}
