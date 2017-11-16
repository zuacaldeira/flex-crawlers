/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.jornalDeAngola;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import org.junit.Ignore;

/**
 *
 * @author zua
 */
@Ignore(value = "Due to slowness")
public class JornalDeAngolaCrawlerTestIT extends AbstractCrawlerTestIT {

    public JornalDeAngolaCrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new JornalDeAngolaCrawler();
    }
}
