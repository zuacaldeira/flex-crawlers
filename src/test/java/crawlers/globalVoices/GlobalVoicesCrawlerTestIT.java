/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;

import crawlers.AbstractCrawlerTestIT;
import crawlers.FlexNewsCrawler;

/**
 *
 * @author zua
 */
public class GlobalVoicesCrawlerTestIT extends AbstractCrawlerTestIT {

    public GlobalVoicesCrawlerTestIT() {
    }

    @Override
    protected FlexNewsCrawler getCrawler() {
        return new GlobalVoicesCrawlerEN();
    }
}
