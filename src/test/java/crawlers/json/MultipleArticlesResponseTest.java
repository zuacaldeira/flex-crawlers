/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.json;

import crawlers.json.MultipleArticlesResponse;
import crawlers.json.SingleArticleResponse;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zua
 */
public class MultipleArticlesResponseTest {
    
    public MultipleArticlesResponseTest() {
    }

    /**
     * Test of getStatus method, of class MultipleArticlesResponse.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        MultipleArticlesResponse response = new MultipleArticlesResponse();
        String status = "status";
        response.setStatus(status);
        assertEquals(status, response.getStatus());
    }

    /**
     * Test of setStatus method, of class MultipleArticlesResponse.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        testGetStatus();
    }

    /**
     * Test of getSource method, of class MultipleArticlesResponse.
     */
    @Test
    public void testGetSource() {
        System.out.println("getSource");
        MultipleArticlesResponse response = new MultipleArticlesResponse();
        String source = "source";
        response.setSource(source);
        assertEquals(source, response.getSource());
    }

    /**
     * Test of setSource method, of class MultipleArticlesResponse.
     */
    @Test
    public void testSetSource() {
        System.out.println("setSource");
        testGetSource();
    }

    /**
     * Test of getSortBy method, of class MultipleArticlesResponse.
     */
    @Test
    public void testGetSortBy() {
        System.out.println("getSortBy");
        MultipleArticlesResponse response = new MultipleArticlesResponse();
        String sortBy = "sortBy";
        response.setSortBy(sortBy);
        assertEquals(sortBy, response.getSortBy());
    }

    /**
     * Test of setSortBy method, of class MultipleArticlesResponse.
     */
    @Test
    public void testSetSortBy() {
        System.out.println("setSortBy");
        testGetSortBy();
    }

    /**
     * Test of getArticles method, of class MultipleArticlesResponse.
     */
    @Test
    public void testGetArticles() {
        System.out.println("getArticles");
        MultipleArticlesResponse response = new MultipleArticlesResponse();
        
        LinkedList<SingleArticleResponse> articles = new LinkedList<>();
        SingleArticleResponse sar = new SingleArticleResponse();
        articles.add(sar);
        
        response.setArticles(articles);
        assertTrue(response.getArticles().contains(sar));
    }

    /**
     * Test of setArticles method, of class MultipleArticlesResponse.
     */
    @Test
    public void testSetArticles() {
        System.out.println("setArticles");
        testGetArticles();
    }
    
}
