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
public class GlobalVoicesCrawlerAYM extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerAYM() {
    }

    @Override
    protected String getSourceCountry() {
        return "PE";
    }

    @Override
    protected String getSourceLanguage() {
        return "aym";
    }
}
