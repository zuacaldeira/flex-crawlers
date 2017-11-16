/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.iolNews;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.iolNews.IOLNewsZACrawler;
import db.NewsSource;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author zua
 */
public class IOLNewsZACrawlerTest extends AbstractCrawlerTest {

    public IOLNewsZACrawlerTest() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new IOLNewsZACrawler();
    }

    @Test
    @Override
    public void testGetMySource() {
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("iol-news-za", source.getSourceId());
        assertEquals("https://www.iol.co.za/news", source.getUrl());
        assertEquals("en", source.getLanguage());
        assertEquals("ZA", source.getCountry());
        assertEquals(Logos.getLogo("iol-news-za"), source.getLogoUrl());
    }

}
