/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.aVerdadeOnline;

import crawlers.AbstractCrawlerTest;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import crawlers.exceptions.ImageNotFoundException;
import crawlers.exceptions.UrlNotFoundException;
import crawlers.exceptions.TitleNotFoundException;
import db.NewsSource;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

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
        assertEquals("geral", source.getCategory());
        assertEquals(Logos.getLogo("verdade-online"), source.getLogoUrl());
    }

    @Override
    protected AVerdadeOnlineCrawler getCrawler() {
        return new AVerdadeOnlineCrawler();
    }

    @Test(expected=UrlNotFoundException.class)
    public void testGetUrlValueFail() throws UrlNotFoundException {
        getCrawler().getUrlValue(new Element("div"));
    }
    
    @Test(expected=UrlNotFoundException.class)
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

    @Test(expected=TitleNotFoundException.class)
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

    @Test(expected=TitleNotFoundException.class)
    public void testGetTitleValue() throws TitleNotFoundException {
        getCrawler().getTitleValue(new Document(""));
    }
    
    @Test(expected=ImageNotFoundException.class)
    public void testGetImageValue() throws ImageNotFoundException {
        getCrawler().getImageUrlValue(new Document(""));
    }
    
    @Test(expected=ImageNotFoundException.class)
    public void testGetImageValueFail() throws ImageNotFoundException {
        Document document = new Document("");
        
        Element p = new Element("p");
        Element img = new Element("img");
        document.appendChild(p);
        p.appendChild(img);
        assertFalse(document.select("p > img").isEmpty());
        
        getCrawler().getImageUrlValue(document);
    }
    
    @Test
    public void testGetFullImageUrl() throws ImageNotFoundException {
        String x = getCrawler().getMySource().getUrl()  + "/a";
        assertEquals(x, getCrawler().getFullImageUrl(x));
    }
}
