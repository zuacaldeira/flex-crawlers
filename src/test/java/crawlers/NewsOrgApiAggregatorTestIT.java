/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers;

import crawlers.publishers.exceptions.ApiCallException;
import java.io.IOException;
import java.text.ParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class NewsOrgApiAggregatorTestIT {

    public NewsOrgApiAggregatorTestIT() {
    }

    @DataProvider
    public static Object[][] sourceQueries() {
        return new Object[][]{
            {"", "", "", "http://newsapi.org/v1/sources?apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {null, null, null, "http://newsapi.org/v1/sources?apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"cat", "", "", "http://newsapi.org/v1/sources?category=cat&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"", "en", "", "http://newsapi.org/v1/sources?language=en&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"", "", "gb", "http://newsapi.org/v1/sources?country=gb&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories

            {"cat", "en", "", "http://newsapi.org/v1/sources?category=cat&language=en&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"cat", "", "gb", "http://newsapi.org/v1/sources?category=cat&country=gb&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"", "en", "gb", "http://newsapi.org/v1/sources?language=en&country=gb&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories

            {"cat", "en", "gb", "http://newsapi.org/v1/sources?category=cat&language=en&country=gb&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
        };
    }

    @DataProvider
    public static Object[][] articlesQueries() {
        return new Object[][]{
            {"", "http://newsapi.org/v1/articles?apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"bbc-news", "http://newsapi.org/v1/articles?source=bbc-news&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
        };
    }

    @DataProvider
    public static Object[][] apiCalls() {
        return new Object[][]{
            {"http://newsapi.org/v1/articles?source=bloomberg&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
            {"http://newsapi.org/v1/articles?source=bloomberg&apiKey=5b4e00f3046843138d8368211777a4f2"}, // all categories
        };
    }

    @DataProvider
    public static Object[][] apiFailCalls() {
        return new Object[][]{
            {"http://newsapi.org/v1/"}, // all categories
            {null}, // all categories
        };
    }

    @Test(dataProvider = "sourceQueries")
    public void testGetSourceQuery(String category, String language2Letter, String country, String expected) {
        System.out.println("getSourcesQuery");
        NewsOrgApiAggregator mapper = new NewsOrgApiAggregator();
        assertEquals(expected, mapper.getSourcesQuery(category, language2Letter, country));
    }

    @Test(dataProvider = "articlesQueries")
    public void testGetArticlesQuery(String source, String expected) {
        System.out.println("getArticlesQuery");
        NewsOrgApiAggregator mapper = new NewsOrgApiAggregator();
        assertEquals(expected, mapper.getArticlesQuery(source));
    }

    /**
     * Test of makeApiCall method, of class NewsOrgApiAggregator.
     *
     * @param apiCall An string with the web service call.
     */
    @Test(dataProvider = "apiCalls")
    public void testMakeApiCall(String apiCall) throws ApiCallException {
        System.out.println("makeApiCall");
        NewsOrgApiAggregator instance = new NewsOrgApiAggregator();
        assertNotNull(instance.makeApiCall(apiCall));
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "apiFailCalls")
    public void testMakeApiCallFail(String apiCall) throws ApiCallException {
        System.out.println("makeApiCall");
        NewsOrgApiAggregator instance = new NewsOrgApiAggregator();
        assertNotNull(instance.makeApiCall(apiCall));
    }

    /**
     * Test of loadAllData method, of class NewsOrgApiAggregator.
     */
    @Test
    public void testLoadAllData() throws IOException, ApiCallException {
        System.out.println("loadAllData");
        NewsOrgApiAggregator instance = new NewsOrgApiAggregator();
        instance.loadAllData();
        assertTrue(true);
    }
    
    @Test(expectedExceptions = IOException.class)
    public void testLoadAllIOException() throws IOException, ApiCallException {
        System.out.println("loadAllData");
        NewsOrgApiAggregator instance = new NewsOrgApiAggregator();
        instance.read("");
    }
    


    @Test
    public void testCrawl() throws IOException, ApiCallException, ParseException {
        System.out.println("loadAllData");
        NewsOrgApiAggregator instance = new NewsOrgApiAggregator();
        instance.aggregate();
    }

}
