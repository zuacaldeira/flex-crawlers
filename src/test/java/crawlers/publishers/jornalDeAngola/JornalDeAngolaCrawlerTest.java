/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.jornalDeAngola;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.AuthorsNotFoundException;
import crawlers.publishers.exceptions.DocumentNotFoundException;
import db.news.NewsSource;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class JornalDeAngolaCrawlerTest extends AbstractCrawlerTest {

    public JornalDeAngolaCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("jornal-de-angola", source.getSourceId());
        assertEquals("Jornal de Angola", source.getName());
        assertEquals("pt", source.getLanguage());
        assertEquals("AO", source.getCountry());
        assertEquals("http://jornaldeangola.sapo.ao", source.getUrl());
        assertEquals("geral", source.getCategory().getTag().getTag());
        assertEquals(Logos.getLogo("jornal-de-angola"), source.getLogoUrl());
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new JornalDeAngolaCrawler();
    }

    @Override
    //@Test(expected = AuthorsNotFoundException.class)
    public void testGetAuthors() throws AuthorsNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        super.testGetAuthors();
    }
}
