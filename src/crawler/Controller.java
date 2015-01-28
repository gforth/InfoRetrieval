package crawler;

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
            config.setUserAgentString("UCI WebCrawler 18407504");
            config.setMaxDepthOfCrawling(-1);
            config.setMaxPagesToFetch(10);
            config.setPolitenessDelay(300);
            config.setConnectionTimeout(1000); // set timeout to 1 min

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
            final long startTime = System.currentTimeMillis();
            controller.start(MyCrawler.class, numberOfCrawlers);  
            final long endTime = System.currentTimeMillis();

            System.out.println("Total execution time: " + (endTime - startTime)/1000 + " seconds" );
    }
}

