package solution;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
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
		
    	//Q2: Compute unique URLS
    	computeUniqueDomains();
    	
    	// Q4:
		Map<String, String> map = getFileMapping(rootDirName);
		String longestPage = getLongestPage(rootDirName, map);
	}
	
	public static Map<String, String> getFileMapping(String folderName) throws Exception {
		File mapFile = new File(folderName + "/" + MAPPING_FILENAME);
		List<String> lines = FileUtils.readLines(mapFile);
		Map<String, String> output = new HashMap<String, String>();
		for(int i=0; i<lines.size(); i+=2) {
			// file ID
			String key = lines.get(i);
			String val = lines.get(i+1);
			if(output.containsKey(key)) {
				System.out.println(key);
				throw new Exception("Duplicate file ID");
			}
			
			output.put(key, val);
		}
		return output;
	}
	
	public static String getLongestPage(String folderName, Map<String, String> map) throws IOException {
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
		
		System.out.println("Longest page id is " + longestPage + " ( " + map.get(longestPage) + " ) with " + maxWords + " words");
		return longestPage;
	}
	
    public static void computeUniqueDomains(){
		//Read data
    	List lines = new ArrayList();
    	
		try{
			File file = new File("data/crawl/root/map.txt");
			lines = FileUtils.readLines(file, "UTF-8");
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
    	
		List domainsList = new ArrayList();
		String regex = "\\d+";
		Iterator<String> it = lines.iterator();
	    while ( it.hasNext() ){
	    	String currentIt = it.next();
	    	if(!currentIt.matches(regex)){
	    		domainsList.add(currentIt);
	    	}
	    }
		
    	//Compute Unique Domains
    	Set<String> computedDomains = new HashSet<String>();
    	for(int i = 0; i < domainsList.size(); i++){
    		computedDomains.add((String) domainsList.get(i));
    	}
    	
    	//Create result data
    	String textResult = new String();
    	
    	//Print the size of unique domains
	    textResult = "### List of unique URLs ###\n\n";
	    textResult += "Total size of unique URLs: " + computedDomains.size() + "\n\n";
    	
    	//Print all unique domains
        Iterator<String> sortedDomain = computedDomains.iterator();
        while ( sortedDomain.hasNext() ){
            textResult += sortedDomain.next() + "\n";
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
