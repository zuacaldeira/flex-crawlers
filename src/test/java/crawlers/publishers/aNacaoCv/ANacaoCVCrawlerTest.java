/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aNacaoCv;

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
public class ANacaoCVCrawlerTest extends AbstractCrawlerTest {

    public ANacaoCVCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("a-nacao", source.getSourceId());
        assertEquals("A Nação", source.getName());
        assertEquals("pt", source.getLanguage());
        assertEquals("CV", source.getCountry());
        assertEquals("http://anacao.cv", source.getUrl());
        assertEquals("geral", source.getCategory().getTag().getTag());
        assertEquals(Logos.getLogo("a-nacao"), source.getLogoUrl());
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new ANacaoCVCrawler();
    }


}
