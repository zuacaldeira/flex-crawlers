/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.iolNews;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import crawlers.exceptions.ContentNotFoundException;
import org.junit.Test;

/**
 *
 * @author zua
 */
public class IOLNewsZACrawlerTestIT extends AbstractCrawlerTestIT {

    public IOLNewsZACrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new IOLNewsZACrawler();
    }
    
    
    @Override
    public void testImportArticle() {
        super.testImportArticle();
    }
    
}
