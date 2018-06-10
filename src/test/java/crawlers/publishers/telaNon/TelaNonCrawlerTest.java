/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.telaNon;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.AuthorsNotFoundException;
import crawlers.publishers.exceptions.DocumentNotFoundException;
import crawlers.publishers.exceptions.JsoupElementNotFoundException;
import db.news.NewsSource;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
@Ignore
public class TelaNonCrawlerTest extends AbstractCrawlerTest {

    public TelaNonCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("tela-non", source.getSourceId());
        assertEquals("Téla Nón", source.getName());
        assertEquals("pt", source.getLanguage());
        assertEquals("ST", source.getCountry());
        assertEquals("http://www.telanon.info/", source.getUrl());
        assertEquals(Logos.getLogo("tela-non"), source.getLogoUrl());
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new TelaNonCrawler();
    }

    @Override
    @Test(expectedExceptions = JsoupElementNotFoundException.class)
    public void testGetAuthors() throws AuthorsNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        super.testGetAuthors();
    }

}
