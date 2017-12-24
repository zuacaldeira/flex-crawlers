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
public class GlobalVoicesCrawlerMG extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerMG() {
    }

    @Override
    protected String getSourceCountry() {
        return "MG";
    }

    @Override
    protected String getSourceLanguage() {
        return "mg";
    }

}
