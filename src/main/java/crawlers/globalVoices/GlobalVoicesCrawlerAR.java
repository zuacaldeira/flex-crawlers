/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.globalVoices;
import javax.ejb.Stateless;

import crawlers.Logos;
import db.NewsSource;

/**
 *
 * @author zua
 */

@Stateless public class GlobalVoicesCrawlerAR extends GlobalVoicesAbstractCrawler {

    public GlobalVoicesCrawlerAR() {
    }

    @Override
    public NewsSource getMySource() {
        String sourceId = "global-voices-ar";
        String name = "Global Voices (AR)";
        String description = "";
        String url = "https://ar.globalvoices.org";
        String category = "general";
        String language = "ar";
        String country = "SD";

        NewsSource source = new NewsSource(sourceId, name, description, url, category, language, country);
        source.setLogoUrl(Logos.getLogo("global-voices"));

        return source;
    }

    @Override
    
    public void crawl() {
        super.crawl(); //To change body of generated methods, choose Tools | Templates.
    }
}
