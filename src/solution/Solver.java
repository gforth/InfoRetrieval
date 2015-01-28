package solution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Solver {
	
	public static void main(String[] args) throws Exception {
    	//Q2: Compute unique URLS
    	computeUniqueDomains();
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
