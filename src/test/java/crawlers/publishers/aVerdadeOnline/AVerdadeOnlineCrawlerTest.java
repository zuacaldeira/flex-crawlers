/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aVerdadeOnline;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.publishers.exceptions.ImageNotFoundException;
import crawlers.publishers.exceptions.UrlNotFoundException;
import crawlers.publishers.exceptions.TitleNotFoundException;
import db.news.NewsSource;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class AVerdadeOnlineCrawlerTest extends AbstractCrawlerTest {

    public AVerdadeOnlineCrawlerTest() {
    }

    @Test
    @Override
    public void testGetMySource() {
        System.out.println("getMySource");
        FlexNewsCrawler crawler = getCrawler();
        NewsSource source = crawler.getMySource();
        assertEquals("verdade-online", source.getSourceId());
        assertEquals("@Verdade Online", source.getName());
        assertEquals("pt", source.getLanguage());
        assertEquals("MZ", source.getCountry());
        assertEquals("http://www.verdade.co.mz", source.getUrl());
        assertEquals("geral", source.getCategory().getTag());
        assertEquals(Logos.getLogo("verdade-online"), source.getLogoUrl());
    }

    @Override
    protected AVerdadeOnlineCrawler getCrawler() {
        return new AVerdadeOnlineCrawler();
    }

    @Test
    public void testGetUrlValueFail() throws UrlNotFoundException {
        getCrawler().getUrlValue(new Element("div"));
    }
    
    @Test
    public void testGetUrlValue() throws UrlNotFoundException {
        Element article = new Element("div");
        Element tbody = new Element("tbody");
        Element tr = new Element("tr");
        Element td = new Element("td");
        Element a = new Element("a");
        
        td.appendChild(a);
        assertFalse(td.select("a").isEmpty());
        
        tr.appendChild(td);
        assertFalse(tr.select("td").isEmpty());
        
        tbody.appendChild(tr);
        assertFalse(tbody.select("tr").isEmpty());

        article.appendChild(tbody);
        assertFalse(article.select("tbody").isEmpty());
        assertFalse(article.select("tbody > tr > td > a").isEmpty());
        
        System.out.println(article);
        getCrawler().getUrlValue(article);
    }

    @Test
    public void testGetTitleValueFail() throws TitleNotFoundException {
        Document document = new Document("");
        Element article = new Element("div");
        Element tbody = new Element("tbody");
        Element tr = new Element("tr");
        Element td = new Element("td");
        td.addClass("contentheading");
        Element a = new Element("a");
        
        td.appendChild(a);
        assertFalse(td.select("a").isEmpty());
        
        tr.appendChild(td);
        assertFalse(tr.select("td").isEmpty());
        
        tbody.appendChild(tr);
        assertFalse(tbody.select("tr").isEmpty());

        article.appendChild(tbody);
        assertFalse(article.select("tbody").isEmpty());
        document.appendChild(article);
        assertFalse(document.select("td.contentheading").isEmpty());
        
        getCrawler().getTitleValue(document);
    }

    @Test
    public void testGetTitleValue() throws TitleNotFoundException {
        getCrawler().getTitleValue(new Document(""));
    }
    
    @Test
    public void testGetImageValue() throws ImageNotFoundException {
        assertNull(getCrawler().getImageUrlValue(new Document("")));
    }
    
    @Test
    public void testGetImageValueFail() throws ImageNotFoundException {
        Document document = new Document("");
        
        Element p = new Element("p");
        Element img = new Element("img");
        document.appendChild(p);
        p.appendChild(img);
        assertFalse(document.select("p > img").isEmpty());
        
        assertNull(getCrawler().getImageUrlValue(document));
    }
    
    @Test
    public void testGetFullImageUrl() throws ImageNotFoundException {
        String x = getCrawler().getMySource().getUrl()  + "/a";
        assertEquals(x, getCrawler().getFullImageUrl(x));
    }
}
