/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.publishers.globalVoices;

/**
 *
 * @author zua
 */
public class GlobalVoicesCrawlerSW extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerSW() {
    }

    @Override
    protected String getSourceCountry() {
        return "TZ";
    }

    @Override
    protected String getSourceLanguage() {
        return "sw";
    }

}
