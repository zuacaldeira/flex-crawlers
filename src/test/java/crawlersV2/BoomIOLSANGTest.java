/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import db.news.NewsArticle;
import java.io.IOException;
import java.util.TreeSet;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class BoomIOLSANGTest {
    
    public BoomIOLSANGTest() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLZA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomIOLZA instance = new BoomIOLZA();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomIOLZA instance = new BoomIOLZA();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test
    public void isArticleLink() {
        BoomIOLZA instance = new BoomIOLZA();
        boolean b = instance.isArticleLink("https://www.iol.co.za/travel/south-africa/eastern-cape/eastern-cape-tourism-is-ready-for-indaba2018-14744595");
        assertTrue(b);
    }
    
}
