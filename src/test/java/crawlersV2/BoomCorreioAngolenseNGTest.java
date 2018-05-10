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
public class BoomCorreioAngolenseNGTest {
    
    public BoomCorreioAngolenseNGTest() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLSA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomCorreioAngolense instance = new BoomCorreioAngolense();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomCorreioAngolense instance = new BoomCorreioAngolense();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test(dataProvider = "articleLinks")
    public void isArticleLink(String link) {
        BoomCorreioAngolense instance = new BoomCorreioAngolense();
        boolean b = instance.isArticleLink(link);
        assertTrue(b);
    }
    
    @Test(dataProvider = "articleLinks")
    public void toArticle(String link) {
        BoomCorreioAngolense instance = new BoomCorreioAngolense();
        NewsArticle article = instance.toArticle(link);
        assertNotNull(article);
    }

    //@Test(dataProvider = "articleImages")
    public void hasImage(String link, String imageUrl) {
        BoomCorreioAngolense instance = new BoomCorreioAngolense();
        NewsArticle article = instance.toArticle(link);
        assertEquals(article.getImageUrl(), imageUrl);
    }

    @DataProvider(name = "articleLinks")
    public static Object[][] articleLinks() {
        return new Object[][]{
            {"https://www.correioangolense.com/artigo/africa-series-repor-a-relacao-africa-europa"},
            {"https://www.correioangolense.com/artigo/politica/obrigacoes-impostas-pela-partida-do-homem-do-leme"},
        };
    }
    
    @DataProvider(name = "articleImages")
    public static Object[][] articleImages() {
        return new Object[][]{
            {"https://www.correioangolense.com/artigo/africa-series-repor-a-relacao-africa-europa", 
                "https://pro-bee-user-content-eu-west-1.s3.amazonaws.com/public/users/Integrators/b99ad300-c627-4db2-8810-4b32cff172e3/editor-correioangolense/editor_images/WhatsApp%20Image%202017-12-07%20at%2017.04.33.jpeg"},
            {"https://www.correioangolense.com/artigo/politica/obrigacoes-impostas-pela-partida-do-homem-do-leme", 
                "https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/b99ad300-c627-4db2-8810-4b32cff172e3/editor-correioangolense/editor_images/interior.png"}
        };
    }
}
