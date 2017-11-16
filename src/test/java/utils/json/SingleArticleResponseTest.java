/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.json;

import db.NewsAuthor;
import db.NewsSource;
import java.text.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zua
 */
public class SingleArticleResponseTest {
    
    public SingleArticleResponseTest() {
    }

    /**
     * Test of getAuthor method, of class SingleArticleResponse.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getAuthor());
    }

    /**
     * Test of setAuthor method, of class SingleArticleResponse.
     */
    @Test
    public void testSetAuthor() {
        System.out.println("setAuthor");
        String author = "author";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setAuthor(author);
        assertEquals(author, instance.getAuthor());
    }

    /**
     * Test of getTitle method, of class SingleArticleResponse.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getTitle());
    }

    /**
     * Test of setTitle method, of class SingleArticleResponse.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        String title = "";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setTitle(title);
        assertEquals(title, instance.getTitle());
    }

    /**
     * Test of getDescription method, of class SingleArticleResponse.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getDescription());
    }

    /**
     * Test of setDescription method, of class SingleArticleResponse.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "description";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setDescription(description);
        assertEquals(description, instance.getDescription());
    }

    /**
     * Test of getUrl method, of class SingleArticleResponse.
     */
    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getUrl());
    }

    /**
     * Test of setUrl method, of class SingleArticleResponse.
     */
    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        String url = "url";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setUrl(url);
        assertEquals(url, instance.getUrl());
    }

    /**
     * Test of getUrlToImage method, of class SingleArticleResponse.
     */
    @Test
    public void testGetUrlToImage() {
        System.out.println("getUrlToImage");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getUrlToImage());
    }

    /**
     * Test of setUrlToImage method, of class SingleArticleResponse.
     */
    @Test
    public void testSetUrlToImage() {
        System.out.println("setUrlToImage");
        String urlToImage = "url";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setUrlToImage(urlToImage);
        assertEquals(urlToImage, instance.getUrlToImage());
    }

    /**
     * Test of getPublishedAt method, of class SingleArticleResponse.
     */
    @Test
    public void testGetPublishedAt() {
        System.out.println("getPublishedAt");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getPublishedAt());
    }

    /**
     * Test of setPublishedAt method, of class SingleArticleResponse.
     */
    @Test
    public void testSetPublishedAt() {
        System.out.println("setPublishedAt");
        String publishedAt = "ddd";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setPublishedAt(publishedAt);
        assertEquals(publishedAt, instance.getPublishedAt());
    }

    /**
     * Test of getSource method, of class SingleArticleResponse.
     */
    @Test
    public void testGetSource() {
        System.out.println("getSource");
        SingleArticleResponse instance = new SingleArticleResponse();
        assertNull(instance.getSource());
    }

    /**
     * Test of setSource method, of class SingleArticleResponse.
     */
    @Test
    public void testSetSource() {
        System.out.println("setSource");
        String source = "source";
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setSource(source);
        assertEquals(source, instance.getSource());
    }

    /**
     * Test of convert2NewsArticle method, of class SingleArticleResponse.
     */
    @Test
    public void testConvert2NewsArticle() throws ParseException {
        System.out.println("convert2NewsArticle");
        SingleArticleResponse instance = new SingleArticleResponse();
        instance.setAuthor("author");
        instance.setDescription("description");
        instance.setPublishedAt("13 Out 2017");
        instance.setSource("my-source");
        instance.setTitle("title");
        instance.setUrl("url");
        instance.setUrlToImage("urlToImage");
        NewsSource source = new NewsSource();
        source.setLanguage("pt");
        source.setCountry("PT");
        source.setSourceId("my-source");
        
        assertEquals("title", instance.convert2NewsArticle(source).getTitle());
        assertEquals("description", instance.convert2NewsArticle(source).getDescription());
        assertEquals("my-source", instance.convert2NewsArticle(source).getSourceId());
    }

    /**
     * Test of convert2NewsAuthor method, of class SingleArticleResponse.
     */
    @Test
    public void testConvert2NewsAuthor() {
        System.out.println("convert2NewsAuthor");
        SingleArticleResponse instance = new SingleArticleResponse();
        NewsSource source = new NewsSource();
        assertEquals(new NewsAuthor(source.getName()), instance.convert2NewsAuthor(source));
    }
    
}
