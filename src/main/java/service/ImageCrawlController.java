package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ImageCrawlController {
	private static final Logger logger = LoggerFactory.getLogger(ImageCrawlController.class);

    public static void main(String[] args) throws Exception {

        String rootFolder = "~downloads/image";
        int numberOfCrawlers = 2;
        String storageFolder = "downloads/image/temp2";

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(rootFolder);
        config.setMaxDepthOfCrawling(3);
        config.setPolitenessDelay(20);

    /*
     * Since images are binary content, we need to set this parameter to
     * true to make sure they are included in the crawl.
     */
        config.setIncludeBinaryContentInCrawling(true);

        String[] crawlDomains = {"https://www.dcard.tw/f/sex"};

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        for (String domain : crawlDomains) {
            controller.addSeed(domain);
        }

        MyCrawler.configure(crawlDomains, storageFolder);

        controller.start(MyCrawler.class, numberOfCrawlers);
    }
}
