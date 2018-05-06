/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import db.news.NewsArticle;
import java.io.IOException;
import java.util.TreeSet;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 *
 * @author zua
 */
public class BoomAngonoticiasNGTest {
    
    public BoomAngonoticiasNGTest() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLSA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomAngonoticias instance = new BoomAngonoticias();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomAngonoticias instance = new BoomAngonoticias();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test(dataProvider = "articleLinks")
    public void isArticleLink(String link) {
        BoomAngonoticias instance = new BoomAngonoticias();
        boolean b = instance.isArticleLink(link);
        assertTrue(b);
    }
    
    @Test(dataProvider = "articleLinks")
    public void toArticle(String link) {
        BoomAngonoticias instance = new BoomAngonoticias();
        NewsArticle article = instance.toArticle(link);
        assertNotNull(article);
    }

    @DataProvider(name = "articleLinks")
    public static Object[][] articleLinks() {
        return new Object[][]{
            {"http://www.angonoticias.com/Artigos/item/57876/angola-endividou-se-em-3-mil-milhoes-de-dolares-com-juros-de-825-por-cento-e-937-por-cento-"},
            {"http://www.angonoticias.com/Artigos/item/57874/bna-liquidara-divida-de-50-milhoes-de-dolares-namibianos"},
            {"http://www.angonoticias.com/Artigos/item/57840/basquetebol-libolo-e-1-de-agosto-defrontam-final-da-taca-de-angola"},
            {"http://www.angonoticias.com/Artigos/item/57333/luanda-e-desastre-arquitectonico"},
            {"http://www.angonoticias.com/Artigos/item/54341/costa-rica-angolana-conquista-segundo-lugar-no-mundial-de-pesca-desportiva"}
        };
    }
    
}
