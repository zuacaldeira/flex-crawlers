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
public class GlobalVoicesCrawlerZHT extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerZHT() {
    }

    @Override
    protected String getSourceCountry() {
        return "CN";
    }

    @Override
    protected String getSourceLanguage() {
        return "zh";
    }

    @Override
    protected String getSubdomain() {
        return "zht";
    }

}
