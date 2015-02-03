package solution;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class Solver {
	
	final static String MAPPING_FILENAME = "map.txt";
	
	public static void main(String[] args) throws Exception {
		String rootDirName = "./data/crawl/root";
		
    	//Q2: Compute unique URLS
//    	computeUniqueDomains();
    	
    	//Q3: Compute subdomain
//    	computeSubdomain();
    	
    	// Q4:
//		Map<String, String> map = getFileMapping(rootDirName);
//		String longestPage = getLongestPage(rootDirName, map);
		
		// Q5:
//		int maxMostCommonWords = 100;
//		List<Map.Entry<Token, Integer>> commonWords = getMostCommonTokens(rootDirName, map, maxMostCommonWords);
//		for(int i=0; i<maxMostCommonWords; i++) {
//			Map.Entry<Token, Integer> entry = commonWords.get(i);
//			System.out.println(entry.getKey().getWord() + " : " + entry.getValue());
//		}
    	
    	//Q6
//    	computeCommon2Grams(rootDirName);
	}
	
	/**
	 * TODO: might have to use trie if out of memory. 
	 * right now just use hashmap, assuming not too many unique words
	 * ASSUME topK < number of unique words
	 * @param folderName
	 * @param map
	 * @param topK
	 * @return
	 * @throws IOException 
	 */
	public static List<Map.Entry<Token, Integer>> getMostCommonTokens(String folderName, Map<String, String> map, int topK) throws IOException {
		// first read all files
		Map<Token, Integer> tokenFreq = new HashMap<Token, Integer>();
		File[] listOfFiles = new File(folderName).listFiles();
		
		// get a list of all stop words
		Set<String> stopWords = Utils.getStopWords();
		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			
			// check if its valid file
			if (file.isFile() && !file.getName().equals(MAPPING_FILENAME)) {
			    List<Token> tokens = Token.tokenizeFile(folderName + "/" + file.getName());
			    
			    // update all tokens occurrences
			    int numTokens = tokens.size();
			    for(int tokenIdx=0; tokenIdx<numTokens; tokenIdx++) {
			    	Token token = tokens.get(tokenIdx);
			    	
			    	// check if its a stop word
			    	if(stopWords.contains(token.getWord())) continue;
			    	
			    	if(tokenFreq.containsKey(token)) tokenFreq.put(token, tokenFreq.get(token)+1);
			    	else tokenFreq.put(token, 1);
			    }
			} 
		}
		
		// sort map in descending order - ref: http://www.mkyong.com/java/how-to-sort-a-map-in-java/
		List<Map.Entry<Token, Integer>> list = new LinkedList<Map.Entry<Token, Integer> >(tokenFreq.entrySet());
	 
		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Token, Integer>>() {
			public int compare(Map.Entry<Token, Integer> o1,
                                           Map.Entry<Token, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue()); // -> descending order
			}
		});
		
		// only return topK most common words
		List<Map.Entry<Token, Integer>> topKTokens = new ArrayList<Map.Entry<Token, Integer>>();
		for(int i=0; i<topK; i++) topKTokens.add(list.get(i));
		
		return topKTokens;
		
	}
	
	/**
	 * load map file and return it
	 * @param folderName
	 * @return map of filename and url
	 * @throws Exception
	 */
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
	
	/**
	 * find the longest page
	 * @param folderName
	 * @param map
	 * @return longest page's url
	 * @throws IOException
	 */
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
    	//Configurations
    	String targetedFile = "data/crawl/root/map.txt";
    	
		//Read data
    	List<String> lines = new ArrayList<String>();
    	
		try{
			File file = new File(targetedFile);
			lines = FileUtils.readLines(file, "UTF-8");
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
    	
		List<String> domainsList = new ArrayList<String>();
		String regex = "(\\d+)(_)(\\d+)";
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
    	String textResultHeader = new String();
    	String textResultFooter = new String();
    	
    	//Create file
    	File file = new File("answers/result-q2.txt");
    	
    	//Print the size of unique domains
	    textResultHeader = "### List of unique URLs ###\n\n";
	    textResultHeader += "Total size of unique URLs: " + computedDomains.size() + "\n\n";
        try{
        	FileUtils.writeStringToFile(file, textResultHeader, false);
    	}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
    	
    	//Print all unique domains
        Iterator<String> sortedDomain = computedDomains.iterator();
        int j = 1;
        while ( sortedDomain.hasNext() ){
        	String currentProcessDomain = sortedDomain.next();
            System.out.println(j + " - " + currentProcessDomain);
            j++;
            try{
            	FileUtils.writeStringToFile(file, currentProcessDomain + "\n", true);
        	}catch (IOException e){
    			System.err.println("Caught IOException: " + e.getMessage());
    		}
        }
        
        //Print footer to file
        textResultFooter = "\n" + "----- END OF RESULT -----";
        try{
        	FileUtils.writeStringToFile(file, textResultFooter, true);
    	}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
    }
    
    public static void computeSubdomain(){
    	//Configurations
    	String targetedFile = "answers/result-q2.txt";
    	
		//Read domain list
    	List<String> lines = new ArrayList<String>();
		try{
			File file = new File(targetedFile);
			lines = FileUtils.readLines(file, "UTF-8");
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
		
		//Process subdomain of ics.uci.edu
		List<String> domainsList = new ArrayList<String>();
		String regex = "^https?://[a-z0-9-_.]*ics[.]uci[.]edu(.*)";
		Iterator<String> it = lines.iterator();
	    while ( it.hasNext() ){
	    	String currentIt = it.next();
	    	if(currentIt.matches(regex)){
	    		currentIt = currentIt.split("ics.uci.edu")[0] + "ics.uci.edu";
	    		domainsList.add(currentIt);
	    	}
	    }
	    
	    //Process frequency of subdomain
	    Map<String, Integer> domainsFrequencies = new HashMap<String, Integer>();
		for(int i = 0; i < domainsList.size(); i++){
			String currentDomain = (String) domainsList.get(i);
			if(domainsFrequencies.containsKey(currentDomain)){
				int currentFrequency = (int) domainsFrequencies.get(currentDomain);
				currentFrequency++;
				domainsFrequencies.put(currentDomain, currentFrequency);
			}else{
				//If there is no token in the hashmap, add new
				domainsFrequencies.put(currentDomain, 1);
			}
		}
		
		sortByFrequency("Subdomains", domainsFrequencies);
	    
    }

    public static void computeCommon2Grams(String folderName){
		File[] listOfFiles = new File(folderName).listFiles();
		List<String> twoGramsList = new ArrayList<String>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile() && !file.getName().endsWith(".txt")) {
			    //Produce 2-gram words from each file
				List<Token> tokens = Token.tokenizeFile(folderName + "/" + file.getName());
				for(int j = 0; j < tokens.size() - 1; j++){
					twoGramsList.add( tokens.get(j).getWord() + " " + tokens.get(j+1).getWord() );
				}
			} 
		}

		//Compute token and its frequency
		Map<String, Integer> twoGramsFrequencies = new HashMap<String, Integer>();
		for(int i = 0; i < twoGramsList.size(); i++){
			if(twoGramsFrequencies.containsKey(twoGramsList.get(i))){
				int currentFrequency = (int) twoGramsFrequencies.get(twoGramsList.get(i));
				currentFrequency++;
				twoGramsFrequencies.put(twoGramsList.get(i), currentFrequency);
			}else{
				//If there is no 2 grams in the hashmap, add new
				twoGramsFrequencies.put(twoGramsList.get(i), 1);
			}
		}
		
		sortByFrequency("2-Grams Frequencies", twoGramsFrequencies);
    }
    
	//*** Supporting methods ***
	@SuppressWarnings("unchecked")
	private static void sortByFrequency(String title, Map<String, Integer> frequenciesMap) { 
		//Convert Map to Array
		System.out.println("S01 - Start converting Map to Array at " + getCurrentTime());
	    Object[] frequenciesArray = frequenciesMap.entrySet().toArray();
	    System.out.println("S01 - End converting Map to Array at " + getCurrentTime());
	    
	    System.out.println("S02 - Start sorting by frequency at " + getCurrentTime());
	    Arrays.sort(frequenciesArray, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
	        	Entry<String, Integer> entry1 = (Map.Entry<String, Integer>) o1;
				Entry<String, Integer> entry2 = (Map.Entry<String, Integer>) o2;
				return entry2.getValue().compareTo(entry1.getValue());
	        }
	    });
	    System.out.println("S02 - End sorting by frequency at " + getCurrentTime());
	    
	    //Create text result variable
	    String textResultHeader = new String();
	    String textResultFooter = new String();
	    
    	//Create file
        File file = null;
	    if(title.equals("Subdomains")){
	    	file = new File("answers/Subdomains.txt");
	    }else if(title.equals("2-Grams Frequencies")){
	    	file = new File("answers/2GramsFrequencies.txt");
	    }
    	
    	//Print the size of unique subdomains
	    System.out.println("S03 - Start printing result header at " + getCurrentTime());
	    textResultHeader = "### Result of " + title + " by highest to lowest frequency ###\n\n";
	    textResultHeader += "Total unique size of " + title + ": " + frequenciesMap.size() + "\n\n";
		try{
        	FileUtils.writeStringToFile(file, textResultHeader, false);
    	}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
        System.out.println("S03 - End printing result header at " + getCurrentTime());
	    
	    //Print each subdomain
        System.out.println("S04 - Start printing result body at " + getCurrentTime());
	    for (Object d : frequenciesArray) {
	        Entry<String, Integer> entry = (Map.Entry<String, Integer>) d;
	        String currentSubdomain = entry.getKey() + ", " + entry.getValue() + "\n";
            try{
            	FileUtils.writeStringToFile(file, currentSubdomain, true);
        	}catch (IOException e){
    			System.err.println("Caught IOException: " + e.getMessage());
    		}
	    }
	    System.out.println("S04 - End printing result body at " + getCurrentTime());
	    
        //Print footer to file
	    System.out.println("S05 - Start printing result footer at " + getCurrentTime());
        textResultFooter = "\n" + "----- END OF RESULT -----";
        try{
        	FileUtils.writeStringToFile(file, textResultFooter, true);
        	System.out.println("S05 - End printing result footer at " + getCurrentTime());
    	}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
	}
	
	private static String getCurrentTime(){
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
}
