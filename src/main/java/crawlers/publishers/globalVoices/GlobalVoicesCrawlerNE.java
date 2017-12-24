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
public class GlobalVoicesCrawlerNE extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerNE() {
    }

    @Override
    protected String getSourceCountry() {
        return "NP";
    }

    @Override
    protected String getSourceLanguage() {
        return "ne";
    }

}
