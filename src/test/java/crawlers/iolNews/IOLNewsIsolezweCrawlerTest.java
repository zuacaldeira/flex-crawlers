/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.iolNews;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.iolNews.IOLNewsIsolezweCrawler;
import db.NewsSource;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author zua
 */
public class IOLNewsIsolezweCrawlerTest extends AbstractCrawlerTest {

    public IOLNewsIsolezweCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("iol-news-isolezwe", source.getSourceId());
        assertEquals("https://www.iol.co.za/isolezwe", source.getUrl());
        assertEquals("sw", source.getLanguage());
        assertEquals("ZA", source.getCountry());
        assertEquals(Logos.getLogo("iol-news-za"), source.getLogoUrl());
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new IOLNewsIsolezweCrawler();
    }

}
