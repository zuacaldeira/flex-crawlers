/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.json;

import crawlers.json.SingleSourceResponse;
import db.news.NewsSource;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zua
 */
public class SingleSourceResponseTest {
    
    public SingleSourceResponseTest() {
    }

    /**
     * Test of getId method, of class SingleSourceResponse.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setId("myId");
        assertEquals("myId", response.getId());
    }

    /**
     * Test of setId method, of class SingleSourceResponse.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        testGetId();
    }

    /**
     * Test of getName method, of class SingleSourceResponse.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setName("myName");
        assertEquals("myName", response.getName());
    }

    /**
     * Test of setName method, of class SingleSourceResponse.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        testGetName();
    }

    /**
     * Test of getDescription method, of class SingleSourceResponse.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setDescription("myDescription");
        assertEquals("myDescription", response.getDescription());
    }

    /**
     * Test of setDescription method, of class SingleSourceResponse.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        testGetDescription();
    }

    /**
     * Test of getUrl method, of class SingleSourceResponse.
     */
    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setUrl("myUrl");
        assertEquals("myUrl", response.getUrl());
    }

    /**
     * Test of setUrl method, of class SingleSourceResponse.
     */
    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        testGetUrl();
    }

    /**
     * Test of getCategory method, of class SingleSourceResponse.
     */
    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setCategory("myCategory");
        assertEquals("myCategory", response.getCategory());
    }

    /**
     * Test of setCategory method, of class SingleSourceResponse.
     */
    @Test
    public void testSetCategory() {
        System.out.println("setCategory");
        testGetCategory();
    }

    /**
     * Test of getLanguage method, of class SingleSourceResponse.
     */
    @Test
    public void testGetLanguage() {
        System.out.println("getLanguage");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setLanguage("myLanguage");
        assertEquals("myLanguage", response.getLanguage());
    }

    /**
     * Test of setLanguage method, of class SingleSourceResponse.
     */
    @Test
    public void testSetLanguage() {
        System.out.println("setLanguage");
        testGetLanguage();
    }

    /**
     * Test of getCountry method, of class SingleSourceResponse.
     */
    @Test
    public void testGetCountry() {
        System.out.println("getCountry");
        SingleSourceResponse response = new SingleSourceResponse();
        response.setCountry("myCountry");
        assertEquals("myCountry", response.getCountry());
    }

    /**
     * Test of setCountry method, of class SingleSourceResponse.
     */
    @Test
    public void testSetCountry() {
        System.out.println("setCountry");
        testGetCountry();
    }

    /**
     * Test of getUrlsToLogos method, of class SingleSourceResponse.
     */
    @Test
    public void testGetUrlsToLogos() {
        System.out.println("getUrlsToLogos");
        SingleSourceResponse response = new SingleSourceResponse();
        
        Map<String, String> map = new HashMap<>();
        
        response.setUrlsToLogos(map);
        assertEquals(map, response.getUrlsToLogos());
    }

    /**
     * Test of setUrlsToLogos method, of class SingleSourceResponse.
     */
    @Test
    public void testSetUrlsToLogos() {
        System.out.println("setUrlsToLogos");
        testGetUrlsToLogos();
    }

    /**
     * Test of getSortBysAvailable method, of class SingleSourceResponse.
     */
    @Test
    public void testGetSortBysAvailable() {
        System.out.println("getSortBysAvailable");
        SingleSourceResponse response = new SingleSourceResponse();
        
        response.getSortBysAvailable().add("top");
        assertTrue(response.getSortBysAvailable().contains("top"));
    }

    /**
     * Test of setSortBysAvailable method, of class SingleSourceResponse.
     */
    @Test
    public void testSetSortBysAvailable() {
        System.out.println("setSortBysAvailable");
        testGetSortBysAvailable();
    }

    /**
     * Test of convert2NewsSource method, of class SingleSourceResponse.
     */
    @Test
    public void testConvert2NewsSource() {
        System.out.println("convert2NewsSource");
        SingleSourceResponse response = new SingleSourceResponse();
        NewsSource source = response.convert2NewsSource();
        assertEquals(source.getSourceId(), response.getId());
    }
    
}
