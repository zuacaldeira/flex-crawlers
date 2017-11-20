/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.theBugleZa;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.exceptions.ArticlesNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import crawlers.exceptions.DocumentNotFoundException;
import db.NewsSource;
import org.jsoup.nodes.Document;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author zua
 */
public class TheBugleZACrawlerTest extends AbstractCrawlerTest {

    public TheBugleZACrawlerTest() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new TheBugleZACrawler();
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("the-bugle", source.getSourceId());
        assertEquals("The Bugle", source.getName());
        assertEquals("en", source.getLanguage());
        assertEquals("ZA", source.getCountry());
        assertEquals("http://thebugle.co.za/home.php", source.getUrl());
        assertEquals("lifestyle", source.getCategory());
        assertEquals(Logos.getLogo("the-bugle"), source.getLogoUrl());
    }
    
    @Override
    @Test
    public void testGetAuthors() throws AuthorsNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        assertTrue(getCrawler().getAuthors(new Document("")).contains(getCrawler().getMySource().getName()));
    }

}
