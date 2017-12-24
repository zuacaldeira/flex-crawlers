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
public class GlobalVoicesCrawlerPL extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerPL() {
    }

    @Override
    protected String getSourceCountry() {
        return "PL";
    }

    @Override
    protected String getSourceLanguage() {
        return "pl";
    }

}
