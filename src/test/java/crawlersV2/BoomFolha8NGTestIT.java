/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import db.news.NewsArticle;
import java.io.IOException;
import java.util.TreeSet;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 *
 * @author zua
 */
public class BoomFolha8NGTestIT {
    
    public BoomFolha8NGTestIT() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLSA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomFolha8 instance = new BoomFolha8();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomFolha8 instance = new BoomFolha8();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test(dataProvider = "articleLinks")
    public void isArticleLink(String link) {
        BoomFolha8 instance = new BoomFolha8();
        boolean b = instance.isArticleLink(link);
        assertTrue(b);
    }
    
    @Test(dataProvider = "articleLinks")
    public void toArticle(String link) {
        BoomFolha8 instance = new BoomFolha8();
        NewsArticle article = instance.toArticle(link);
        assertNotNull(article);
    }

    @Test(dataProvider = "articleImages")
    public void hasImage(String link, String imageUrl) {
        BoomFolha8 instance = new BoomFolha8();
        NewsArticle article = instance.toArticle(link);
        assertEquals(article.getImageUrl(), imageUrl);
    }

    @DataProvider(name = "articleLinks")
    public static Object[][] articleLinks() {
        return new Object[][]{
            {"https://jornalf8.net/2018/entregues-bicharada-nao-povo-faminto/"},
            {"https://jornalf8.net/2016/assassinos/"},
            {"https://jornalf8.net/2018/padarias-angolanos-estao-passar-historia/"},
            {"https://jornalf8.net/2018/os-vitinhos/"},
            {"https://jornalf8.net/2018/sim-nao-talvez-pelo-contrario-diz-moodys/"}
        };
    }
    
    @DataProvider(name = "articleImages")
    public static Object[][] articleImages() {
        return new Object[][]{
            {"https://jornalf8.net/2018/entregues-bicharada-nao-povo-faminto/", "https://jornalf8.net/wp-content/uploads/2018/05/zedumorais.jpg"},
            {"https://jornalf8.net/2016/assassinos/", "https://jornalf8.net/wp-content/uploads/2016/08/assassinos.jpg"},
            {"https://jornalf8.net/2018/padarias-angolanos-estao-passar-historia/", "https://jornalf8.net/wp-content/uploads/2018/05/padaria.jpg"},
            {"https://jornalf8.net/2018/os-vitinhos/", "https://jornalf8.net/wp-content/uploads/2018/04/vitinho.jpg"},
            {"https://jornalf8.net/2018/sim-nao-talvez-pelo-contrario-diz-moodys/", "https://jornalf8.net/wp-content/uploads/2018/05/rating.jpg"}
        };
    }
}
