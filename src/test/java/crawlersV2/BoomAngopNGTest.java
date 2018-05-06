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
public class BoomAngopNGTest {
    
    public BoomAngopNGTest() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLSA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomAngop instance = new BoomAngop();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomAngop instance = new BoomAngop();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test(dataProvider = "articleLinks")
    public void isArticleLink(String link) {
        BoomAngop instance = new BoomAngop();
        boolean b = instance.isArticleLink(link);
        assertTrue(b);
    }
    
    @Test(dataProvider = "articleLinks")
    public void toArticle(String link) {
        BoomAngop instance = new BoomAngop();
        NewsArticle article = instance.toArticle(link);
        assertNotNull(article);
    }

    @DataProvider(name = "articleLinks")
    public static Object[][] articleLinks() {
        return new Object[][]{
            {"http://www.angop.ao/angola/pt_pt/noticias/politica/2018/4/18/Zambia-Feira-lusofonia-potencia-divulgacao-expansao-lingua-portuguesa,b411fc4c-3b1f-491b-9484-0425d649bf72.html"},
            {"http://www.angop.ao/angola/pt_pt/noticias/economia/2018/4/18/Producao-cafe-ganha-189-milhoes-euros,53d6f91a-a792-49b8-a9b7-ca6443d5b320.html"},
            {"http://www.angop.ao/angola/pt_pt/noticias/sociedade/2018/4/18/Comissao-Gestao-CVA-reage-indicacao-presidente-interino,09e0d9b8-6c39-4c77-9b6f-8df5d7f3983e.html"},
            {"http://www.angop.ao/angola/pt_pt/noticias/sociedade/2018/4/18/Jornalistas-precisam-Carteira-Profissional-diz-Presidente-ERCA,59473d5b-e249-42ee-966c-c88d5ae9f9e2.html"},
            {"http://www.angop.ao/angola/pt_pt/noticias/desporto/2018/4/18/Girabola2018-Kabuscorp-perde-seis-pontos-classificacao,7505c1dc-635a-477b-9d86-b84d16aef745.html"}
        };
    }
    
}
