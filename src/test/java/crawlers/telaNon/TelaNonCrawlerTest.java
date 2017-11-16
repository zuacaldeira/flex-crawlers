/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.telaNon;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.exceptions.ArticlesNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import crawlers.exceptions.DocumentNotFoundException;
import crawlers.exceptions.JsoupElementNotFoundException;
import db.NewsSource;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

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
        assertEquals("geral", source.getCategory());
        assertEquals(Logos.getLogo("tela-non"), source.getLogoUrl());
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new TelaNonCrawler();
    }

    @Override
    @Test(expected = JsoupElementNotFoundException.class)
    public void testGetAuthors() throws AuthorsNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        super.testGetAuthors();
    }

}
