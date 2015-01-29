package solution;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Solver {
	
	final static String MAPPING_FILENAME = "map.txt";
	
	public static void main(String[] args) throws Exception {
		String rootDirName = "./data/crawl/root";
//    	Q2: Compute unique URLS
//    	computeUniqueDomains(domainsList);
		Map<Integer, String> map = getFileMapping(rootDirName);
		String longestPage = getLongestPage(rootDirName, map);
	}
	
	public static Map<Integer, String> getFileMapping(String folderName) throws Exception {
		File mapFile = new File(folderName + "/" + MAPPING_FILENAME);
		List<String> lines = FileUtils.readLines(mapFile);
		Map<Integer, String> output = new HashMap<Integer, String>();
		for(int i=0; i<lines.size()-1; i+=2) {
			if(i%2 == 0) {
				// file ID
				if(output.containsKey(lines.get(i))) throw new Exception("Duplicate file ID");
				
				output.put(i, lines.get(i+1));
			}
		}
		return output;
	}
	
	public static String getLongestPage(String folderName, Map<Integer, String> map) throws IOException {
		int maxWords = -1;
		String longestPage = null;
		File[] listOfFiles = new File(folderName).listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile() && !file.getName().endsWith(".txt")) {
			    List<Token> tokens = Token.tokenizeFile(folderName + "/" + file.getName());
			    if(tokens.size() > maxWords) {
			    	maxWords = tokens.size();
			    	longestPage = file.getName();
			    }
			} 
		}
		
		System.out.println("Longest page id is " + map.get(Integer.parseInt(longestPage)) + " with " + maxWords + " words");
		return longestPage;
	}
	
    public static void computeUniqueDomains(List<String> domainsList){
    	//Compute Unique Domains
    	Set<String> computedDomains = new HashSet<String>();
    	for(int i = 0; i < domainsList.size(); i++){
    		computedDomains.add(domainsList.get(i));
    	}
    	
    	//Create result data
    	String textResult = new String();
    	
    	//Print the size of unique domains
	    textResult = "### List of unique URLs ###\n\n";
	    textResult += "Total size of unique URLs: " + computedDomains.size() + "\n\n";
    	
    	//Print all unique domains
        Iterator<String> it = computedDomains.iterator();
        while ( it.hasNext() ){
            textResult += it.next() + "\n";
        }
        textResult += "\n" + "----- END OF RESULT -----";
        
		//Write result to text file
		try{
			File file = new File("answers/result-q2.txt");
			FileUtils.writeStringToFile(file, textResult);
			System.out.println("Result for Q2 is ready!");
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}	
    }

}
