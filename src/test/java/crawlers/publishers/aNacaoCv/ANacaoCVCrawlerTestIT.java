/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.aNacaoCv;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import org.testng.annotations.Ignore;

/**
 *
 * @author zua
 */
public class ANacaoCVCrawlerTestIT extends AbstractCrawlerTestIT {

    public ANacaoCVCrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new ANacaoCVCrawler();
    }

    @Override
    //@Test(expected = ContentNotFoundException.class)
    @Ignore
    public void testImportArticle() {
        super.testImportArticle();
    }
    
    
}
