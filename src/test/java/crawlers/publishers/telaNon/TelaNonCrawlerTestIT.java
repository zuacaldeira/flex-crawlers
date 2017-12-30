/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.telaNon;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import crawlers.publishers.exceptions.DocumentNotFoundException;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class TelaNonCrawlerTestIT extends AbstractCrawlerTestIT {

    public TelaNonCrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new TelaNonCrawler();
    }
    
    @Override
    @Test(expectedExceptions = DocumentNotFoundException.class)
    public void testImportArticle() {
        super.testImportArticle();
    }
}
