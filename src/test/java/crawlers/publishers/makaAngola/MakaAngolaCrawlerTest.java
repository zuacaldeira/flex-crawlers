/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.makaAngola;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import db.news.NewsSource;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class MakaAngolaCrawlerTest extends AbstractCrawlerTest {

    public MakaAngolaCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("maka-angola", source.getSourceId());
        assertEquals("Maka Angola", source.getName());
        assertEquals("pt", source.getLanguage());
        assertEquals("AO", source.getCountry());
        assertEquals("https://www.makaangola.org", source.getUrl());
        assertEquals(Logos.getLogo("maka-angola"), source.getLogoUrl());
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new MakaAngolaCrawler();
    }

}
