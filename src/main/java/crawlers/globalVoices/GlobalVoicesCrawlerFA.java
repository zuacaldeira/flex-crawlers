/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;

import crawlers.Logos;
import db.NewsSource;
import javax.ejb.Stateless;

/**
 *
 * @author zua
 */

@Stateless public class GlobalVoicesCrawlerFA extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerFA() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-fa";
        String name = "Global Voices (FA)";
        String description = "";
        String url = "https://fa.globalvoices.org";
        String category = "general";
        String language = "fa";
        String country = "IR";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("global-voices"));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
