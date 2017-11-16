/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.theBugleZa;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import crawlers.exceptions.AuthorsNotFoundException;
import org.junit.Test;

/**
 *
 * @author zua
 */
public class TheBugleZACrawlerTestIT extends AbstractCrawlerTestIT {

    public TheBugleZACrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new TheBugleZACrawler();
    }
    
    @Override
    public void testImportArticle() {
        super.testImportArticle();
    }
    
}
