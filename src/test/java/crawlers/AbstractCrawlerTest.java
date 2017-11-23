/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.exceptions.DocumentNotFoundException;
import crawlers.exceptions.UrlNotFoundException;
import crawlers.exceptions.TimeNotFoundException;
import crawlers.exceptions.TitleNotFoundException;
import crawlers.exceptions.ImageNotFoundException;
import crawlers.exceptions.ContentNotFoundException;
import crawlers.exceptions.ArticlesNotFoundException;
import crawlers.exceptions.AuthorsNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author zua
 */
public abstract class AbstractCrawlerTest {

    protected abstract FlexNewsCrawler getCrawler();

    @Test
    public abstract void testGetMySource();

    @Test
    public void testGetArticles() throws DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getArticles");
        FlexNewsCrawler crawler = getCrawler();
        Elements articles = getArticles(crawler);
        assertNotNull(articles);
    }

    @Test
    public void testGetUrl() throws DocumentNotFoundException, ArticlesNotFoundException, UrlNotFoundException {
        System.out.println("getUrl");
        FlexNewsCrawler crawler = getCrawler();
        Elements articles = getArticles(crawler);
        Element article = articles.first();
        assertNotNull(crawler.getUrl(article));
    }

    @Test
    public void testGetTitle() throws TitleNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getTitle");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        String title = crawler.getTitle(document);
        assertNotNull(title);
        assertFalse(title.isEmpty());
    }

    @Test
    public void testGetImageUrl() throws DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getImageUrl");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        try {
            crawler.getImageUrl(document);
        } catch (ImageNotFoundException ex) {
            Logger.getLogger(AbstractCrawlerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetContent() throws ContentNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getContent");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        assertNotNull(document);
        String content = crawler.getContent(document);
        //assertNotNull(content);
    }

    @Test
    public void testGetAuthors() throws AuthorsNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getAuthors");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        Set<String> authors = crawler.getAuthors(document);
        assertNotNull(authors);
        //assertFalse(authors.isEmpty());
    }

    @Test
    public void testGetTime() throws TimeNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getTime");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        String time = crawler.getTimeValue(document);
        assertNotNull(time);
        assertFalse(time.isEmpty());
    }

    @Test
    public void openDocument() throws DocumentNotFoundException {
        System.out.println("openDocument");
        Document document = getCrawler().openDocument(getCrawler().getMySource().getUrl());
    }

    @Test
    public void openDocumentFail() throws DocumentNotFoundException {
        System.out.println("openDocumentFail");
        assertNull(getCrawler().openDocument(""));
    }

    @Test
    public void testGetSource() {
        System.out.println("getSource");
        assertEquals(getCrawler().getMySource(), getCrawler().getSource());
    }

    protected Elements getArticles(FlexNewsCrawler crawler) throws DocumentNotFoundException, ArticlesNotFoundException {
        Document document = crawler.openDocument(crawler.getMySource().getUrl());
        return crawler.getArticles(document);
    }

    protected Document getArticleDocumentPage(FlexNewsCrawler crawler) throws DocumentNotFoundException, ArticlesNotFoundException {
        Elements articles = getArticles(crawler);
        Iterator<Element> it = articles.iterator();
        while (it.hasNext()) {
            String url = crawler.getUrl(it.next());
            if(url != null) {
                return crawler.openDocument(url);
            }
        }
        throw new DocumentNotFoundException();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalgetArticles() throws ArticlesNotFoundException {
        getCrawler().getArticles(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalGetAuthorsValue() throws AuthorsNotFoundException {
        getCrawler().getAuthorsValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalGetUrlValue() throws UrlNotFoundException {
        getCrawler().getUrlValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalGetTitleValue() throws TitleNotFoundException {
        getCrawler().getTitleValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalGetImageUrlValue() throws ImageNotFoundException {
        getCrawler().getImageUrlValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalGetContentValue() throws ContentNotFoundException {
        getCrawler().getContentValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalGetTimeValue() throws TimeNotFoundException {
        getCrawler().getTimeValue(null);
    }

    @Test(expected = ArticlesNotFoundException.class)
    public void testEmptygetArticles() throws ArticlesNotFoundException {
        getCrawler().getArticles(new Document(""));
    }

    @Test
    public void testEmptyGetAuthorsValue() {
        assertEquals(getCrawler().getMySource().getName(), getCrawler().getAuthorsValue(new Document("")));
    }

    @Test
    public void testEmptyGetUrlValue() throws UrlNotFoundException {
        assertNull(getCrawler().getUrlValue(new Element("div")));
    }

    @Test
    public void testEmptyGetTitleValue() throws TitleNotFoundException {
        assertNull(getCrawler().getTitleValue(new Document("")));
    }

    @Test
    public void testEmptyGetImageUrlValue() {
        assertNull(getCrawler().getImageUrlValue(new Document("")));
    }

    @Test
    public void testEmptyGetContentValue() throws ContentNotFoundException {
        assertNull(getCrawler().getContentValue(new Document("")));
    }

    @Test
    public void testEmptyGetTimeValue() {
        assertNull(getCrawler().getTimeValue(new Document("")));
    }

    @Test
    public void testGetNewsAuthors() {
        assertTrue(getCrawler().getNewsAuthors(new HashSet<>(), getCrawler().getSource()).size() == 1);
    }

}
