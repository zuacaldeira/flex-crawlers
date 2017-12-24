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

 public class GlobalVoicesCrawlerSR extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerSR() {
    }

    @Override
    protected String getSourceCountry() {
        return "RS";
    }

    @Override
    protected String getSourceLanguage() {
        return "sr";
    }
}
