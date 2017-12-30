/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.publishers.exceptions.DocumentNotFoundException;
import crawlers.publishers.exceptions.UrlNotFoundException;
import crawlers.publishers.exceptions.TimeNotFoundException;
import crawlers.publishers.exceptions.TitleNotFoundException;
import crawlers.publishers.exceptions.ImageNotFoundException;
import crawlers.publishers.exceptions.ContentNotFoundException;
import crawlers.publishers.exceptions.ArticlesNotFoundException;
import crawlers.publishers.exceptions.AuthorsNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

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
        //assertNotNull(articles);
    }

    @Test
    public void testGetUrl() throws DocumentNotFoundException, ArticlesNotFoundException, UrlNotFoundException {
        System.out.println("getUrl");
        FlexNewsCrawler crawler = getCrawler();
        Elements articles = getArticles(crawler);
        if (articles != null && !articles.isEmpty()) {
            Element article = articles.first();
            assertNotNull(crawler.getUrl(article));
        }
    }

    @Test
    public void testGetTitle() throws TitleNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getTitle");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            String title = crawler.getTitle(document);
            assertNotNull(title);
        }
    }

    @Test
    public void testGetImageUrl() throws DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getImageUrl");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            try {
                crawler.getImageUrl(document);
            } catch (ImageNotFoundException ex) {
                Logger.getLogger(AbstractCrawlerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testGetContent() throws ContentNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getContent");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            assertNotNull(document);
            String content = crawler.getContent(document);
        }
        //assertNotNull(content);
    }

    @Test
    public void testGetAuthors() throws AuthorsNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getAuthors");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            Set<String> authors = crawler.getAuthors(document);
            assertNotNull(authors);
        }
        //assertFalse(authors.isEmpty());
    }

    @Test
    public void testGetTime() throws TimeNotFoundException, DocumentNotFoundException, ArticlesNotFoundException {
        System.out.println("getTime");
        FlexNewsCrawler crawler = getCrawler();
        Document document = getArticleDocumentPage(crawler);
        if (document != null) {
            String time = crawler.getTimeValue(document);
            assertNotNull(time);
        }
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
        assertNotNull(getCrawler().getMySource());
    }

    protected Elements getArticles(FlexNewsCrawler crawler) throws DocumentNotFoundException, ArticlesNotFoundException {
        Document document = crawler.openDocument(crawler.getMySource().getUrl());
        if (document != null) {
            return crawler.getArticles(document);
        }
        return null;
    }

    protected Document getArticleDocumentPage(FlexNewsCrawler crawler) throws DocumentNotFoundException, ArticlesNotFoundException {
        Elements articles = getArticles(crawler);
        if (articles != null) {
            Iterator<Element> it = articles.iterator();
            while (it.hasNext()) {
                String url = crawler.getUrl(it.next());
                if (url != null) {
                    return crawler.openDocument(url);
                }
            }
        }
        return null;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalgetArticles() throws ArticlesNotFoundException {
        getCrawler().getArticles(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGetAuthorsValue() throws AuthorsNotFoundException {
        getCrawler().getAuthorsValue(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGetUrlValue() throws UrlNotFoundException {
        getCrawler().getUrlValue(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGetTitleValue() throws TitleNotFoundException {
        getCrawler().getTitleValue(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGetImageUrlValue() throws ImageNotFoundException {
        getCrawler().getImageUrlValue(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGetContentValue() throws ContentNotFoundException {
        getCrawler().getContentValue(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGetTimeValue() throws TimeNotFoundException {
        getCrawler().getTimeValue(null);
    }

    @Test(expectedExceptions = ArticlesNotFoundException.class)
    public void testEmptygetArticles() throws ArticlesNotFoundException {
        getCrawler().getArticles(new Document(""));
    }

    @Test
    public void testEmptyGetAuthorsValue() {
        String authorsValue = getCrawler().getAuthorsValue(new Document(""));
        assertEquals(authorsValue, getCrawler().getMySource().getName());
    }

    @Test
    public void testEmptyGetUrlValue() throws UrlNotFoundException {
        String urlValue = getCrawler().getUrlValue(new Element("div"));
        assertTrue(urlValue == null || urlValue.isEmpty());
    }

    @Test
    public void testEmptyGetTitleValue() throws TitleNotFoundException {
        String titleValue = getCrawler().getTitleValue(new Document(""));
        assertTrue(titleValue == null || titleValue.isEmpty());
    }

    @Test
    public void testEmptyGetImageUrlValue() {
        String imageValue = getCrawler().getImageUrlValue(new Document(""));
        assertTrue(imageValue == null || imageValue.isEmpty());
    }

    @Test
    public void testEmptyGetContentValue() throws ContentNotFoundException {
        String contentValue = getCrawler().getContentValue(new Document(""));
        assertTrue(contentValue == null || contentValue.isEmpty());
    }

    @Test
    public void testEmptyGetTimeValue() {
        String timeValue = getCrawler().getTimeValue(new Document(""));
        assertTrue(timeValue == null || timeValue.isEmpty());
    }

    @Test
    public void testGetNewsAuthors() {
        assertTrue(getCrawler().getNewsAuthors(new HashSet<>(), getCrawler().getMySource()).size() == 1);
    }

}
