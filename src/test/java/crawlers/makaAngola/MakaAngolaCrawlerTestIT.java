/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.makaAngola;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;
import crawlers.makaAngola.MakaAngolaCrawler;

/**
 *
 * @author zua
 */
public class MakaAngolaCrawlerTestIT extends AbstractCrawlerTestIT {

    public MakaAngolaCrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new MakaAngolaCrawler();
    }
}
