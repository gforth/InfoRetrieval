package crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
            String crawlStorageFolder = "./data/crawl/root";
            int numberOfCrawlers = 7;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setUserAgentString("");
            config.setMaxDepthOfCrawling(-1);
            config.setMaxPagesToFetch(-1);
            config.setPolitenessDelay(300);

            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            controller.addSeed("http://www.ics.uci.edu/");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(MyCrawler.class, numberOfCrawlers);    
    }
    
    public static void computeUniqueDomains(List<String> domainsList){
    	//Compute Unique Domains
    	Set<String> computedDomains = new HashSet<String>();
    	for(int i = 0; i < domainsList.size(); i++){
    		computedDomains.add(domainsList.get(i));
    	}
    	
    	//Print the size of unique domains
    	System.out.println(computedDomains.size());
    	
    	//Print all unique domains
        Iterator<String> it = computedDomains.iterator();
        while ( it.hasNext() ){
            System.out.println(it.next());
        }
    }
}

