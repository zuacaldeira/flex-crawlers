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
public class GlobalVoicesCrawlerUR extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerUR() {
    }

    @Override
    protected String getSourceCountry() {
        return "IN";
    }

    @Override
    protected String getSourceLanguage() {
        return "ur";
    }

}
