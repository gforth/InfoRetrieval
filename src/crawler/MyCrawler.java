package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static int fileNum = 0;
    private final static String MAPPING_FILENAME = "map.txt";
    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            return !FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu") 
            		&& !href.startsWith("http://calendar.ics.uci.edu")
            		&& !href.startsWith("http://archive.ics.uci.edu")
            		&& !href.startsWith("http://djp3-pc2.ics.uci.edu")
            		&& !href.startsWith("http://wics.ics.uci.edu/events")
            		&& !href.startsWith("http://wics.ics.uci.edu/news")
            		&& !href.startsWith("http://drzaius.ics.uci.edu/cgi-bin/cvsweb.cgi");
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
//            int domainStartIdx = url.indexOf("//") + 2;
//            int domainEndIdx = url.indexOf('/', domainStartIdx);
//            String subDomain = url.substring(domainStartIdx, domainEndIdx);
            System.out.println("URL: " + url);

            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    
                    // store url into mapping file
                    String currentFileNum = fileNum + "_" + this.getMyId();
                    fileNum++;
                    String mapPath = this.getMyController().getConfig().getCrawlStorageFolder() + "/" + MAPPING_FILENAME;
					String filePath = this.getMyController().getConfig().getCrawlStorageFolder() + "/" + currentFileNum;
                    FileWriter fw;
					try {
						fw = new FileWriter(mapPath, true);
	                    fw.write(currentFileNum + "\n" + url + '\n');
	                    fw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(-1);
					}
					
					// save content
					try {
						fw = new FileWriter(filePath);
						fw.write(text);
						fw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(-1);
					}
//                    String html = htmlParseData.getHtml();
//                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    
//                    System.out.println("Text length: " + text.length());
//                    System.out.println("Html length: " + html.length());
//                    System.out.println("Number of outgoing links: " + links.size());
            }
    }
}
