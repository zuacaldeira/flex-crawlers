/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;

import crawlers.Logos;
import db.NewsSource;


/**
 *
 * @author zua
 */

 public class GlobalVoicesCrawlerEO extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerEO() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-eo";
        String name = "Global Voices (EO)";
        String description = "";
        String url = "https://eo.globalvoices.org";
        String category = "general";
        String language = "eo";
        String country = "001";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("global-voices"));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
