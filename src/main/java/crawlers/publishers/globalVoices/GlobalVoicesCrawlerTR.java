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
public class GlobalVoicesCrawlerTR extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerTR() {
    }

    @Override
    protected String getSourceCountry() {
        return "TR";
    }

    @Override
    protected String getSourceLanguage() {
        return "tr";
    }

}
