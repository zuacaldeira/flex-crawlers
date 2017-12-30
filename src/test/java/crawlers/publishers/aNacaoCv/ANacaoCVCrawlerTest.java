/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aNacaoCv;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.elements.TimeElement;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.DocumentNotFoundException;
import crawlers.publishers.exceptions.TimeNotFoundException;
import crawlers.publishers.exceptions.TitleNotFoundException;
import db.news.NewsSource;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
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

    @Test
    @Override
    public void testGetTime() throws TimeNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getTime");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            TimeElement time = crawler.getTimeElement(document);
            assertNotNull(time);
            assertNull(time.getDate());
        }
    }
    
    @Test
    @Override
    public void testGetTitle() throws TitleNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getTitle");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            String title = crawler.getTitle(document);
            //assertNotNull(title);
        }
    }
    
    @Test
    public void testEmptygetArticles() throws ArticlesNotFoundException {
        Elements articles = getCrawler().getArticles(new Document(""));
    }
    
    

}
