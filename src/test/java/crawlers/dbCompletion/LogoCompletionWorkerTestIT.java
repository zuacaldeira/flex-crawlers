/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.dbCompletion;

import db.news.NewsSource;
import backend.services.news.NewsSourceService;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class LogoCompletionWorkerTestIT {


    public LogoCompletionWorkerTestIT() {
    }
    
    /**
     * Test of crawlWebsite method, of class TheBugleZACrawler.
     */
    @Test
    public void testCrawl() throws Exception {
        System.out.println("crawl");
        NewsSourceService service = new NewsSourceService();
        NewsSource source = new NewsSource();
        source.setSourceId("maka-angola");
        service.save(source);
        assertNotNull(service.find(source.getSourceId()));
        LogoCompletionWorker crawler = new LogoCompletionWorker();
        crawler.crawl();
        assertEquals(0, new NewsSourceService().findSourcesWithoutLogo().size());
    }




}
