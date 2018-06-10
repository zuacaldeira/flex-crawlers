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
public class BoomNovoJornalNGTestIT {
    
    public BoomNovoJornalNGTestIT() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLSA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomNovoJornal instance = new BoomNovoJornal();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomNovoJornal instance = new BoomNovoJornal();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test(dataProvider = "articleLinks")
    public void isArticleLink(String link) {
        BoomNovoJornal instance = new BoomNovoJornal();
        boolean b = instance.isArticleLink(link);
        assertTrue(b);
    }
    
    @Test(dataProvider = "articleLinks")
    public void toArticle(String link) {
        BoomNovoJornal instance = new BoomNovoJornal();
        NewsArticle article = instance.toArticle(link);
        assertNotNull(article);
    }

    //@Test(dataProvider = "articleImages")
    public void hasImage(String link, String imageUrl) {
        BoomNovoJornal instance = new BoomNovoJornal();
        NewsArticle article = instance.toArticle(link);
        assertEquals(article.getImageUrl(), imageUrl);
    }

    @DataProvider(name = "articleLinks")
    public static Object[][] articleLinks() {
        return new Object[][]{
            {"http://novojornal.co.ao/sociedade/interior/luanda-dez-parques-infantis-com-condicoes-numa-capital-com-mais-de-6-milhoes-de-habitantes-53035.html"},
            {"http://novojornal.co.ao/economia/interior/3-mil-milhoes-usd-do-fundo-soberano-blindados-pela-justica-britanica-zenu-e-bastos-e-morais-obrigados-a-declarar-bens-52946.html"},
        };
    }
    
    @DataProvider(name = "articleImages")
    public static Object[][] articleImages() {
        return new Object[][]{
            {"http://novojornal.co.ao/sociedade/interior/luanda-dez-parques-infantis-com-condicoes-numa-capital-com-mais-de-6-milhoes-de-habitantes-53035.html", 
                "http://ngx-image-nv.azurewebsites.net/image.ashx?type=generate&id=53035&source=ng1026835"},
            {"http://novojornal.co.ao/economia/interior/3-mil-milhoes-usd-do-fundo-soberano-blindados-pela-justica-britanica-zenu-e-bastos-e-morais-obrigados-a-declarar-bens-52946.html", 
                "http://ngx-image-nv.azurewebsites.net/image.ashx?type=generate&id=52946&source=ng1026729"}
        };
    }
}
