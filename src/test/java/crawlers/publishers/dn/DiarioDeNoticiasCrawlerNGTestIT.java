/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.dn;

import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class DiarioDeNoticiasCrawlerNGTestIT {
    
    public DiarioDeNoticiasCrawlerNGTestIT() {
    }

    /**
     * Test of crawl method, of class DiarioDeNoticiasCrawler.
     */
    @Test
    public void testCrawl() {
        System.out.println("crawl()");
        DiarioDeNoticiasCrawler instance = new DiarioDeNoticiasCrawler();
        instance.crawl();
    }
   
}
