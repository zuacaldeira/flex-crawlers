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
public class GlobalVoicesCrawlerJA extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerJA() {
    }

    @Override
    protected String getSourceCountry() {
        return "JP";
    }

    @Override
    protected String getSourceLanguage() {
        return "ja";
    }

    @Override
    protected String getSubdomain() {
        return "jp";
    }
    
    

}
