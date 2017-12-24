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
public class GlobalVoicesCrawlerPS extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerPS() {
    }

    @Override
    protected String getSourceCountry() {
        return "AF";
    }

    @Override
    protected String getSourceLanguage() {
        return "ps";
    }

}
