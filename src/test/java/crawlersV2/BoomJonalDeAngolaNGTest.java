/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import db.news.NewsArticle;
import java.io.IOException;
import java.util.TreeSet;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 *
 * @author zua
 */
public class BoomJonalDeAngolaNGTest {
    
    public BoomJonalDeAngolaNGTest() {
    }

    /**
     * Test of loadLinks method, of class BoomIOLSA.
     */
    @Test
    public void testLoadLinks() throws IOException {
        System.out.println("loadLinks");
        BoomJornalDeAngola instance = new BoomJornalDeAngola();
        TreeSet<String> expResult = new TreeSet<>();
        TreeSet<String> result = instance.loadLinks();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }
    
    @Test
    public void testLoadArticles() throws IOException {
        System.out.println("loadArticles");
        BoomJornalDeAngola instance = new BoomJornalDeAngola();
        TreeSet<NewsArticle> expResult = new TreeSet<>();
        TreeSet<NewsArticle> result = instance.loadArticles();
        assertTrue(result.size() >= expResult.size());
        TestHelper.print("Links", result);
    }

    @Test(dataProvider = "articleLinks")
    public void isArticleLink(String link) {
        BoomJornalDeAngola instance = new BoomJornalDeAngola();
        boolean b = instance.isArticleLink(link);
        assertTrue(b);
    }
    
    @DataProvider(name = "articleLinks")
    public static Object[][] articleLinks() {
        return new Object[][]{
            {"http://jornaldeangola.sapo.ao/sociedade/catedraticos_divergem_nas_opinioes_sobre_eleicoes_nas_universidades"},
            {"http://jornaldeangola.sapo.ao/sociedade/moradores_assustados_com_o_aumento_de_crimes_no_papa_simao"},
            {"http://jornaldeangola.sapo.ao/politica/kiteculo_pede_maior__abertura_a_imprensa"},
            {"http://jornaldeangola.sapo.ao/politica/ministra_defende_mais_proteccao_a_crianca"},
            {"http://jornaldeangola.sapo.ao/provincias/namibe/exploracao_desregrada_de_inertes"}
        };
    }
    
}
