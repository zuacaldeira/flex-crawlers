/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.dn;

import backend.utils.MyDateUtils;
import crawlers.FlexNewsCrawler;
import crawlers.Logos;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class DiarioDeNoticiasCrawlerNGTest {

    public DiarioDeNoticiasCrawlerNGTest() {
    }

    public FlexNewsCrawler getCrawler() {
        return new DiarioDeNoticiasCrawler();
    }

    @Test
    public void testGetMySource() {
        System.out.println("testGetMySource()");
        assertEquals(getCrawler().getMySource().getSourceId(), "diario-de-noticias-pt");
        assertEquals(getCrawler().getMySource().getName(), "Diário de Notícias");
        assertEquals(getCrawler().getMySource().getUrl(), "https://www.dn.pt");
        assertEquals(getCrawler().getMySource().getLogoUrl(), Logos.getLogo("diario-de-noticias-pt"));
    }

    @Test
    public void openDnUrl() {
        System.out.println("openDnUrl");
        Document document = getCrawler().openDocument(getCrawler().getMySource().getUrl());
        assertNotNull(document);
    }

    @Test
    public void findLinks() {
        System.out.println("findLinks()");
        Document document = getCrawler().openDocument(getCrawler().getMySource().getUrl());
        assertNotNull(document);
        assertNotNull(getCrawler().getArticles(document));
    }

    @Test
    public void followLink() {
        System.out.println("followLink()");
        Document document = getCrawler().openDocument(getCrawler().getMySource().getUrl());
        assertNotNull(document);

        Elements articles = getCrawler().getArticles(document);
        assertTrue(articles.size() > 0);

        Element article = articles.first();
        assertNotNull(article);

        String url = getCrawler().getUrl(article);

        assertNotNull(url);
        assertFalse(url.isEmpty());

        Document articleDocument = getCrawler().openDocument(url);
        assertNotNull(articleDocument);
        System.out.println("INSIDE -->" + url);
    }

    @Test
    public void findArticle() throws Exception{
        System.out.println("findArticle()");
        Document document = getCrawler().openDocument(getCrawler().getMySource().getUrl());
        assertNotNull(document);

        Elements articles = getCrawler().getArticles(document);
        assertTrue(articles.size() > 0);

        boolean found = false;
        for (Element article : articles) {
            assertNotNull(article);

            String url = getCrawler().getUrl(article);
            //System.out.println("URL -->" + url);
            assertNotNull(url);

            Document articleDocument = getCrawler().openDocument(url);
            if (articleDocument != null) {
                String title = getCrawler().getTitle(articleDocument);
                String imageUrl = getCrawler().getImageUrl(articleDocument);
                String content = getCrawler().getContent(articleDocument);
                String date = getCrawler().getTimeElement(articleDocument).getValue();
                Set<String> authors = getCrawler().getAuthors(articleDocument);
                if (
                        title != null && !title.isEmpty() 
                        && imageUrl != null && !imageUrl.isEmpty() 
                        && content != null && !content.isEmpty()
                        && date != null && !date.isEmpty()
                        && authors != null && !authors.isEmpty()) {
                    System.out.println("URL -->" + url);
                    System.out.println("TITLE -->" + title);
                    System.out.println("IMAGE -->" + imageUrl);
                    System.out.println("CONTENT -->" + content);
                    System.out.println("DATE 1 -->" + date);
                    System.out.println("DATE 2 -->" + MyDateUtils.parseDate("2017-12-30T01:22"));
                    System.out.println("DATE 3 -->" + MyDateUtils.parseDate(transform(date)));
                    System.out.println("AUTHORS -->" + authors.iterator().next());
                    found = true;
                    break;
                }
            }
        }
        assertTrue(found);
    }

    private String transform(String string) {
        System.out.println("CONTAINS WHITESPACE " + string.contains("\u00a0"));
        System.out.println("BEFORE " + string);
        String result = string.replace("\u00a0", "T");
        System.out.println("AFTER " + result);
        return result;
    }

}
