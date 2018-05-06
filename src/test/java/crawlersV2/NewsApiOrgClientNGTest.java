/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlersV2;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author zua
 */
public class NewsApiOrgClientNGTest {
    
    public NewsApiOrgClientNGTest() {
    }

    /**
     * Test of getSources method, of class NewsApiOrgClient.
     */
    @Test
    public void testGetSources() {
        System.out.println("getSources");
        NewsApiOrgClient instance = new NewsApiOrgClient();
        assertNotNull(instance.requestApiSources());
    }
    
}
